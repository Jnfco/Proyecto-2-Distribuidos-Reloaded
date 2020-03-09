/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2distribuidosreloaded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 *
 * @author jnfco
 */
public class Proyecto2DistribuidosReloaded extends Application {
    
    public ObservableList<ObservableList> data1 = FXCollections.observableArrayList();
    public ObservableList<ObservableList> data2 = FXCollections.observableArrayList();
    private float precioKerosene;
    private float precioDiesel;
    private float precio93;
    private float precio95;
    private float precio97;
    
    Connection connection;
        String url1 = "jdbc:postgresql://localhost:5432/Distribuidor1";
        String url2 = "jdbc:postgresql://localhost:5432/Distribuidor2";
        String user = "postgres";
        String password = "Distribuidos1234";
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public  void conexion ()
    {
        ServerSocket servidor1 = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;
        try
        {
            servidor1 = new ServerSocket(5000);
            System.out.println("Servidor iniciado");
            
            while(true)
            {
                sc = servidor1.accept();
                System.out.println("Cliente conectado");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
                
               // Thread t = new Central(); 
  
                // Invoking the start() method 
                //t.start(); 
                String mensaje = in.readUTF();
                
                System.out.println(mensaje);
                out.writeUTF("PATAS SERVER !");
                sc.close();
                System.out.println("Cliente desconectado");
                
            }
            
        } catch (IOException ex)
        {
            Logger.getLogger(Proyecto2DistribuidosReloaded.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Proyecto2DistribuidosReloaded db = new Proyecto2DistribuidosReloaded();
        db.dbConnectionDB1();
        db.dbConnectionDB2();
        
        
        
        launch(args);
        
        
        
        
        
         
    }
    
    public Connection dbConnectionDB1() {
            //conexion();
        
       
        try {

        Class.forName("org.postgresql.Driver");

        } catch(ClassNotFoundException e ){
                  e.getMessage();
        }

        try {
           connection = DriverManager.getConnection(url1, user, password);
            //JOptionPane.showMessageDialog(null, "Connected");
        } catch (SQLException ex) {
            Logger.getLogger(Proyecto2DistribuidosReloaded.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed To Connect");
        }

        return connection;
    }
    
    
    public ObservableList<ObservableList> mostrarDatos1(TableView tablaEst1)
    {
        try
        {
            Connection c = dbConnectionDB1();
            String sql = "SELECT idventa, idsurtidor, cantidadlitros, precioactual, valorventa, fecha FROM venta order by fecha desc;";
            
            ResultSet rs = c.createStatement().executeQuery(sql);
            
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                tablaEst1.getColumns().addAll(col); 
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

            //FINALLY ADDED TO TableView
            tablaEst1.setItems(data1);
            
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        
        return data1;
    }
    
    public Connection dbConnectionDB2()
    {
        
        try {

        Class.forName("org.postgresql.Driver");

        } catch(ClassNotFoundException e ){
                  e.getMessage();
        }

        try {
           connection = DriverManager.getConnection(url2, user, password);
            //JOptionPane.showMessageDialog(null, "Connected");
        } catch (SQLException ex) {
            Logger.getLogger(Proyecto2DistribuidosReloaded.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed To Connect");
        }

        return connection;
    }
    
    public ObservableList<ObservableList> mostrarDatos2(TableView tablaEst2)
    {
        try
        {
            Connection c = dbConnectionDB2();
            String sql = "SELECT idventa, idsurtidor, cantidadlitros, precioactual, valorventa, fecha FROM venta order by fecha desc;";
            
            ResultSet rs = c.createStatement().executeQuery(sql);
            
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                tablaEst2.getColumns().addAll(col); 
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
                data2.add(row);

            }
            
            //FINALLY ADDED TO TableView
            tablaEst2.setItems(data2);
            
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        
        return data2;
    }
    
    public long ingresarVenta1(Venta venta)
    {
        String sql = "INSERT INTO venta (idsurtidor, cantidadlitros, valorventa, precioactual, fecha) "
                + "VALUES (?, ?, ?, ?, current_timestamp);";
        
        long id = 0;
        
        try (Connection c = dbConnectionDB1();
                PreparedStatement pstmt = c.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, venta.getIdSurtidor());
            pstmt.setFloat(2, venta.getCantidadLitros());
            pstmt.setFloat(3, venta.getValorVenta());
            pstmt.setFloat(4, venta.getPrecioActual());
            
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
    
    
    public long ingresarVenta2(Venta venta)
    {
        String sql = "INSERT INTO venta (idsurtidor, cantidadlitros, valorventa, precioactual, fecha) "
                + "VALUES (?, ?, ?, ?, current_timestamp);";
        
        long id = 0;
        
        try (Connection c = dbConnectionDB2();
                PreparedStatement pstmt = c.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, venta.getIdSurtidor());
            pstmt.setFloat(2, venta.getCantidadLitros());
            pstmt.setFloat(3, venta.getValorVenta());
            pstmt.setFloat(4, venta.getPrecioActual());
            
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
    
  
    
    
}
