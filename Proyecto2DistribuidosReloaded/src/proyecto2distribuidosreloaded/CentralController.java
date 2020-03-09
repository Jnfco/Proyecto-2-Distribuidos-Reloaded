/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2distribuidosreloaded;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author jnfco
 */
public class CentralController implements Initializable {

//    private double precioK;
//    private double precioD;
//    private double precio93;
//    private double precio95;
//    private double precio97;
    
    private Central central = new Central();
    
    
    @FXML
    private Button botonCancelar;

    @FXML
    private TableView tablaReporte;

    @FXML
    private Button botonActualizar;
    
    @FXML
    private TextField fieldKerosene;
    
    @FXML
    private TextField fieldDiesel;
    
    @FXML
    private TextField field93;
    
    @FXML
    private TextField field95;
    
    @FXML
    private TextField field97;
    
    @FXML
    private Button actualizarPrecios;
    
    @FXML
    private Label lblAviso;

    Connection connection;
    String urlDB1 = "jdbc:postgresql://localhost:5432/Distribuidor1";
    String urlDB2 = "jdbc:postgresql://localhost:5432/Distribuidor2";
    String user = "postgres";
    String password = "Distribuidos1234";
    private ObservableList<ObservableList> data1;
    private ObservableList<ObservableList> data2;
    private Connection c1;
    private Connection c2;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        obtenerPreciosActuales();
        
        this.field93.setText("" + central.getPrecio93());
        this.field95.setText("" + central.getPrecio95());
        this.field97.setText("" + central.getPrecio97());
        this.fieldDiesel.setText("" + central.getPrecioD());
        this.fieldKerosene.setText("" + central.getPrecioK());
        
        this.lblAviso.setVisible(false);
        
        
        try {
            //Connection c = db1Connection();
            //Connection c2 = db2Connection();
            DriverManager.registerDriver(new org.postgresql.Driver());
            c1 = DriverManager.getConnection(urlDB1, user, password);
            c2 = DriverManager.getConnection(urlDB2, user, password);

            data1 = FXCollections.observableArrayList();
            data2 = FXCollections.observableArrayList();

            //this.tablaReporte = new TableView(mostrarReporte(tablaReporte));
            //this.tablaReporte.setItems(mostrarReporte2(tablaReporte));
            this.tablaReporte = new TableView(mostrarReporte(tablaReporte,data1,data2));
            
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(CentralController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleCancelarCentral(ActionEvent event) throws IOException {
        System.out.println("Cancelar");
    }

    @FXML
    public void handleActualizar() throws SQLException {

        //System.out.println("Actualizando");
        data1.removeAll(data1);
        data2.removeAll(data2);
      
        
        this.tablaReporte = new TableView(mostrarReporte(tablaReporte,data1,data2));
        this.tablaReporte.refresh();

    }
    
    @FXML
    public void handleKerosene (){
        
        
    }
    @FXML
    public void handleDiesel (){
        
    }
    @FXML
    public void handle93 (){
        
    }
    @FXML
    public void handle95 (){
        
    }
    @FXML
    public void handle97 (){
        
    }
    
    @FXML
    public void handleActualizarPrecios ()
    {
        this.central.setPrecio93(obtenerNuevoPrecio(this.field93.getText(), this.central.getPrecio93()));
        this.central.setPrecio95(obtenerNuevoPrecio(this.field95.getText(), this.central.getPrecio95()));
        this.central.setPrecio97(obtenerNuevoPrecio(this.field97.getText(), this.central.getPrecio97()));
        this.central.setPrecioK(obtenerNuevoPrecio(this.fieldKerosene.getText(), this.central.getPrecioK()));
        this.central.setPrecioD(obtenerNuevoPrecio(this.fieldDiesel.getText(), this.central.getPrecioD()));
        this.lblAviso.setText("Precios actualizados");
        this.lblAviso.setVisible(true);

        guardarPrecio();
        actualizarPrecios(c1,c2);
        this.central.setMensaje("Actualizar");

    }

    public long  actualizarPrecios(Connection c1 ,Connection c2){
         System.out.println("Entro!");
        String sql = "UPDATE distribuidor\n" +"SET preciodiesel = ? , preciokerosene = ? ,precio93= ? , precio95=? ,precio97 =?" + "WHERE iddistribuidor =1;";
        long id = 0;
        
        try (
                PreparedStatement pstmt = c1.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS))
        {
            pstmt.setFloat(1, this.central.getPrecioD());
            pstmt.setFloat(2, this.central.getPrecioK());
            pstmt.setFloat(3, this.central.getPrecio93());
            pstmt.setFloat(4, this.central.getPrecio95());
            pstmt.setFloat(5, this.central.getPrecio97());
            
            int affectedRows = pstmt.executeUpdate();
            
            if(affectedRows > 0)
            {
                try (ResultSet rs = pstmt.getGeneratedKeys())
                {
                    if(rs.next())
                    {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
            
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        try (
                PreparedStatement pstmt = c2.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS))
        {
            pstmt.setFloat(1, this.central.getPrecioD());
            pstmt.setFloat(2, this.central.getPrecioK());
            pstmt.setFloat(3, this.central.getPrecio93());
            pstmt.setFloat(4, this.central.getPrecio95());
            pstmt.setFloat(5, this.central.getPrecio97());
            
            int affectedRows = pstmt.executeUpdate();
            
            if(affectedRows > 0)
            {
                try (ResultSet rs = pstmt.getGeneratedKeys())
                {
                    if(rs.next())
                    {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
            
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return id;
    }
   
    
    public ObservableList<ObservableList> mostrarReporte(TableView tablaEst1,ObservableList<ObservableList> data1,ObservableList<ObservableList> data2) throws SQLException {

        try {

            String sql = "Select surtidor.idsurtidor,distribuidor.iddistribuidor,surtidor.tipocombustible ,round(Avg(venta.cantidadlitros),2 )as promediolitros ,round(Avg(venta.valorventa),2) as promedioventa\n"
                    + "  From  distribuidor, surtidor, venta\n"
                    + "  where distribuidor.iddistribuidor = surtidor.iddistribuidor and surtidor.idsurtidor = venta.idsurtidor \n"
                    + "  Group By surtidor.idsurtidor, distribuidor.iddistribuidor;";

            ResultSet rs = c1.createStatement().executeQuery(sql);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tablaEst1.getColumns().addAll(col);
                //System.out.println("Column [" + i + "] ");
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                //System.out.println("Row [1] added " + row);
                data1.add(row);

            }

            //FINALLY ADDED TO TableView
            tablaEst1.setItems(data1);
            
            
            //BD2
            
            ResultSet rs2 = c2.createStatement().executeQuery(sql);

            

            while (rs2.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs2.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs2.getString(i));
                }
                //System.out.println("Row [1] added " + row);
                data1.add(row);

            }

            //FINALLY ADDED TO TableView
            tablaEst1.setItems(data1);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
       return data1;
    }

    public ObservableList<ObservableList> mostrarReporte2(TableView tablaEst1) throws SQLException {

        try {

            String sql = "Select surtidor.idsurtidor,distribuidor.iddistribuidor,surtidor.tipocombustible ,round(Avg(venta.cantidadlitros),2 )as promediolitros ,round(Avg(venta.valorventa),2) as promedioventa\n"
                    + "  From  distribuidor, surtidor, venta\n"
                    + "  where distribuidor.iddistribuidor = surtidor.iddistribuidor and surtidor.idsurtidor = venta.idsurtidor \n"
                    + "  Group By surtidor.idsurtidor, distribuidor.iddistribuidor;";

            ResultSet rs = c2.createStatement().executeQuery(sql);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tablaEst1.getColumns().addAll(col);
                //System.out.println("Column [" + i + "] ");
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                //System.out.println("Row [1] added " + row);
                data2.add(row);

            }

            //FINALLY ADDED TO TableView
            tablaEst1.setItems(data2);

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error on Building Data");
        }

        return data2;
    }
    
    private long guardarPrecio()
    {
        String sql = "INSERT INTO central (precio93, precio95, precio97, precioD, precioK, fecha) "
                + "VALUES (?, ?, ?, ?, ?, current_timestamp);";
        
        long id = 0;
        
        try (Connection c = dbConnectionDBC();
                PreparedStatement pstmt = c.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS))
        {
            pstmt.setFloat(1, central.getPrecio93());
            pstmt.setFloat(2, central.getPrecio95());
            pstmt.setFloat(3, central.getPrecio97());
            pstmt.setFloat(4, central.getPrecioD());
            pstmt.setFloat(5, central.getPrecioK());
            
            int affectedRows = pstmt.executeUpdate();
            
            if(affectedRows > 0)
            {
                try (ResultSet rs = pstmt.getGeneratedKeys())
                {
                    if(rs.next())
                    {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
            
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return id;
    }
    
    public Connection dbConnectionDBC()
    {
        String url = "jdbc:postgresql://localhost:5432/Central";
        String user = "postgres";
        String password = "Distribuidos1234";
        try {

        Class.forName("org.postgresql.Driver");

        } catch(ClassNotFoundException e ){
                  e.getMessage();
        }

        try {
           connection = DriverManager.getConnection(url, user, password);
            //JOptionPane.showMessageDialog(null, "Connected");
        } catch (SQLException ex) {
            Logger.getLogger(Proyecto2DistribuidosReloaded.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed To Connect");
        }

        return connection;
    }

    public void obtenerPreciosActuales()
    {
        ObservableList<ObservableList> data1 = FXCollections.observableArrayList();
        try
        {
            Connection c = dbConnectionDBC();
            String sql = "SELECT precio93, precio95, precio97, precioK, precioD FROM central order by fecha desc limit 1;";
            
            ResultSet rs = c.createStatement().executeQuery(sql);
            
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                //System.out.println("Column ["+i+"] ");
            }
            
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                //System.out.println("Row [1] added "+row );
                data1.add(row);

            }
            
            central.setPrecio93(Float.parseFloat(data1.get(0).get(0).toString()));
            central.setPrecio95(Float.parseFloat(data1.get(0).get(1).toString()));
            central.setPrecio97(Float.parseFloat(data1.get(0).get(2).toString()));
            central.setPrecioK(Float.parseFloat(data1.get(0).get(3).toString()));
            central.setPrecioD(Float.parseFloat(data1.get(0).get(4).toString()));

            
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    
    public float obtenerNuevoPrecio(String precioEntrada, float precioActual)
    {
        if(precioEntrada.equals(""))
        {
            return precioActual;
        }
        else 
        {
            return Float.parseFloat(precioEntrada);
        }
    }
    
}
