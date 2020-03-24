/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2distribuidosreloaded;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jnfco
 */
public class InterfazVentaController implements Initializable {

    @FXML
    private Button aceptar;

    @FXML
    private Button cancelar;

    @FXML
    private TextField litrosField;

    @FXML
    private int idSurtidor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.idSurtidor = 0;

        // TODO
    }

    /**
     * Metodo del boton aceptar, acá se deberia llamar a la venta y luego cerrar
     * la ventana
     *
     * @param event
     */
    @FXML
    private void handleButtonAceptar(ActionEvent event) {

        //Aca poner el codigo para llamar a la venta!!!
        
        
        //Cerrar ventana
        Stage stage = (Stage) aceptar.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void handleButtonCancelar(ActionEvent event) {
        Stage stage = (Stage) cancelar.getScene().getWindow();

        stage.close();

    }

    /**
     * Metodo para setear la id del surtidor que hará la venta
     *
     * @param idSurtidor
     */
    public void setIdSurtidor(int idSurtidor) {

        this.idSurtidor = idSurtidor;
        litrosField.setText(String.valueOf(this.idSurtidor));
    }

}
