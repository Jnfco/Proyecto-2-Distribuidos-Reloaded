/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distribuidor2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 *
 * @author jnfco
 */
public class FXMLDocumentController implements Initializable 
{
    Proyecto2DistribuidosReloaded p2 = new Proyecto2DistribuidosReloaded();
    private int surtidorSeleccionado;
    @FXML
    private Button centralButton;
    
    @FXML
    private Button estacion1;
    
    @FXML
    private Button estacion2;
    
    @FXML
    private TableView tablaEst1;
    
    @FXML
    private TableView tablaEst2;
    
    @FXML
    private Button agregarVenta1;
    
    @FXML
    private Button agregarVenta2;
    
    @FXML
    private Button buttonKerosene;
    
    @FXML
    private Button buttonDiesel;
    
    @FXML
    private Button button93;
    
    @FXML
    private Button button95;
    
    @FXML
    private Button button97;
     
    @FXML
    private Button caida;
    
    @FXML
    private Button reconectar;
    
    @FXML
    public void handleButtonK(ActionEvent event)
    {
        this.surtidorSeleccionado =1;
        p2.data1.removeAll(p2.data1);
        this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1,1));
    }
    @FXML
    public void handleButtonD(ActionEvent event)
    {
        this.surtidorSeleccionado =2;
        p2.data1.removeAll(p2.data1);
        this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1,2));
    }
    @FXML
    public void handleButton93(ActionEvent event)
    {
        this.surtidorSeleccionado =3;
        p2.data1.removeAll(p2.data1);
        this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1,3));
    }
    @FXML
    public void handleButton95(ActionEvent event)
    {
        this.surtidorSeleccionado =4;
        p2.data1.removeAll(p2.data1);
        this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1,4));
    }
    @FXML
    public void handleButton97(ActionEvent event)
    {
        this.surtidorSeleccionado =5;
        p2.data1.removeAll(p2.data1);
        this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1,5));
    }
    
    
    
    @FXML
    public void handleButtonCaida(ActionEvent event)
    {
        p2.setUrl("");
    }
    
    @FXML
    public void handleButtonReconectar(ActionEvent event)
    {
        p2.setUrl("jdbc:postgresql://localhost:5432/Distribuidor1");
    }
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException 
    {
        //Central central = new Central();
        //System.out.println("Botón Central");
        /*
         Stage stage = new Stage(); 
        Parent root = FXMLLoader.load(getClass().getResource("Central.fxml")); 
       // stage.setTitle("Register"); 
        stage.setScene(new Scene(root,600,500));
        stage.setResizable(false);
        stage.show(); */
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        this.estacion2.wrapTextProperty().setValue(true);
        //this.estacion2.wrapTextProperty().setValue(true);
        //this.tablaEst2 = new TableView(p2.mostrarDatos1(tablaEst2));
        //this.tablaEst2 = new TableView(p2.mostrarDatos2(tablaEst2));
    }    
    
    
    @FXML
    private void botonEstacion2Handler(ActionEvent event) throws SQLException
    {
        Connection c = p2.dbConnectionDB1();
        
        String tran = "begin;\n"
                + "update surtidor\n"
                + "set preciolitro = (select preciokerosene from distribuidor) * ( 1 + ((select factorutilidad from distribuidor where iddistribuidor = 1) / 100))\n"
                + "where tipocombustible = 'Kerosene';\n"
                + "update surtidor\n"
                + "set preciolitro = (select preciodiesel from distribuidor) * ( 1 + ((select factorutilidad from distribuidor where iddistribuidor = 1) / 100))\n"
                + "where tipocombustible = 'Diesel';\n"
                + "update surtidor\n"
                + "set preciolitro = (select precio93 from distribuidor) * ( 1 + ((select factorutilidad from distribuidor where iddistribuidor = 1) / 100))\n"
                + "where tipocombustible = '93';\n"
                + "update surtidor\n"
                + "set preciolitro = (select precio95 from distribuidor) * ( 1 + ((select factorutilidad from distribuidor where iddistribuidor = 1) / 100))\n"
                + "where tipocombustible = '95';\n"
                + "update surtidor\n"
                + "set preciolitro = (select precio97 from distribuidor) * ( 1 + ((select factorutilidad from distribuidor where iddistribuidor = 1) / 100))\n"
                + "where tipocombustible = '97';\n"
                + "commit;";
        Statement st = c.createStatement();
        //PreparedStatement pst = c.prepareStatement(tran);
        c.setAutoCommit(false);
        System.out.println("Prepared statement: "+st);
        System.out.println("exec"+st.execute(tran));
        //st.execute(tran);
        
        c.commit();
        c.setAutoCommit(true);
        
    }
    
     @FXML
    private void agregarVenta1Handler(ActionEvent event) throws IOException   
    {
        System.out.println("Venta del surtidor: "+ this.surtidorSeleccionado);
        //Venta newVenta = new Venta(3);
        //p2.ingresarVenta1(newVenta);
        //p2.data1.removeAll(p2.data1);
        //this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1));
        
        if(!(this.surtidorSeleccionado == 0))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfazVenta.fxml")); //Se crea el fxmloader para la interfaz de venta
            Stage stage = new Stage(); 
            Parent root = (Parent)fxmlLoader.load();
            InterfazVentaController ventaController = fxmlLoader.<InterfazVentaController>getController();//llamamos al controlador para la interfaz venta
            ventaController.setIdSurtidor(surtidorSeleccionado);// seteamos el id del surtidor marcado por le boton al controlador de la venta, asi llega con el id especifico

            //interfaz.setIdSurtidor(surtidorSeleccionado);
            // stage.setTitle("Register"); 
            stage.setScene(new Scene(root,448,300));
            stage.setResizable(false);
            stage.show();
        }
         
    }
    
    
    
}