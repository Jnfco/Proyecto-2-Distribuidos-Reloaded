/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distribuidor2;

import java.io.IOException;
import java.net.URL;
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
        this.estacion1.wrapTextProperty().setValue(true);
        //this.estacion2.wrapTextProperty().setValue(true);
        this.tablaEst2 = new TableView(p2.mostrarDatos1(tablaEst2));
        //this.tablaEst2 = new TableView(p2.mostrarDatos2(tablaEst2));
    }    
    
    @FXML
    private void botonEstacion1Handler(ActionEvent event)
    {
        System.out.println("e1");
    }
    
    @FXML
    private void botonEstacion2Handler(ActionEvent event)
    {
        System.out.println("e2");
    }
    
    @FXML
    private void agregarVenta1Handler(ActionEvent event)   
    {
        Venta newVenta = new Venta(3);
        p2.ingresarVenta1(newVenta);
        p2.data1.removeAll(p2.data1);
        this.tablaEst2 = new TableView(p2.mostrarDatos1(tablaEst2));
    }
    
    
}