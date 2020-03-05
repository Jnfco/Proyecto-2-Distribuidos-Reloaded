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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author jnfco
 */
public class CentralController implements Initializable {

    @FXML
    private Button botonCancelar;

    @FXML
    private TableView tablaReporte;

    @FXML
    private Button botonActualizar;

    Connection connection;
    String urlDB1 = "jdbc:postgresql://localhost:5432/Distribuidor1";
    String urlDB2 = "jdbc:postgresql://localhost:5432/Distribuidor2";
    String user = "postgres";
    String password = "Distribuidos1234";
    private ObservableList<ObservableList> data1;
    private ObservableList<ObservableList> data2;
    private ObservableList<ObservableList> data3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data1 = FXCollections.observableArrayList();
        data2 = FXCollections.observableArrayList();
        data3 = FXCollections.observableArrayList();
        
        //this.tablaReporte = new TableView(mostrarReporte(tablaReporte));
        data3.add(mostrarReporte(tablaReporte));
        data3.add(mostrarReporte2(tablaReporte));
        this.tablaReporte= new TableView(data3);
        // TODO
    }

    @FXML
    private void handleCancelarCentral(ActionEvent event) throws IOException {
        System.out.println("Cancelar");
    }

    public Connection db1Connection() {

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.getMessage();
        }

        try {
            connection = DriverManager.getConnection(urlDB1, user, password);
            //JOptionPane.showMessageDialog(null, "Connected");
        } catch (SQLException ex) {
            Logger.getLogger(Proyecto2DistribuidosReloaded.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed To Connect");
        }

        return connection;
    }
     public Connection db2Connection() {

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.getMessage();
        }

        try {
            connection = DriverManager.getConnection(urlDB2, user, password);
            //JOptionPane.showMessageDialog(null, "Connected");
        } catch (SQLException ex) {
            Logger.getLogger(Proyecto2DistribuidosReloaded.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed To Connect");
        }

        return connection;
    }

    @FXML
    public void handleActualizar() {
        System.out.println("Actualizando");
        data1.removeAll(data1);
        data2.removeAll(data2);
        this.tablaReporte = new TableView(mostrarReporte(tablaReporte));
        this.tablaReporte.setItems(mostrarReporte2(tablaReporte));
        
    }


    public ObservableList<ObservableList> mostrarReporte(TableView tablaEst1) {
        
        try {
            Connection c = db1Connection();
            String sql = "Select surtidor.idsurtidor,distribuidor.iddistribuidor,surtidor.tipocombustible ,round(Avg(venta.cantidadlitros),2 )as promediolitros ,round(Avg(venta.valorventa),2) as promedioventa\n"
                    + "  From  distribuidor, surtidor, venta\n"
                    + "  where distribuidor.iddistribuidor = surtidor.iddistribuidor and surtidor.idsurtidor = venta.idsurtidor \n"
                    + "  Group By surtidor.idsurtidor, distribuidor.iddistribuidor;";

            ResultSet rs = c.createStatement().executeQuery(sql);

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
                System.out.println("Column [" + i + "] ");
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
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
    
     public ObservableList<ObservableList> mostrarReporte2(TableView tablaEst1) {
        
        try {
            Connection c = db2Connection();
            String sql = "Select surtidor.idsurtidor,distribuidor.iddistribuidor,surtidor.tipocombustible ,round(Avg(venta.cantidadlitros),2 )as promediolitros ,round(Avg(venta.valorventa),2) as promedioventa\n"
                    + "  From  distribuidor, surtidor, venta\n"
                    + "  where distribuidor.iddistribuidor = surtidor.iddistribuidor and surtidor.idsurtidor = venta.idsurtidor \n"
                    + "  Group By surtidor.idsurtidor, distribuidor.iddistribuidor;";

            ResultSet rs = c.createStatement().executeQuery(sql);

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
                System.out.println("Column [" + i + "] ");
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data2.add(row);

            }

            //FINALLY ADDED TO TableView
            tablaEst1.setItems(data2);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        
        return data2;
    }

}
