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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author jnfco
 */
public class CentralController implements Initializable {

    @FXML
    private Button botonCancelar;

    Connection connection;
    String urlDB1 = "jdbc:postgresql://localhost:5432/Distribuidor1";
    String urlDB2 = "jdbc:postgresql://localhost:5432/Distribuidor2";
    String user = "postgres";
    String password = "Distribuidos1234";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
            JOptionPane.showMessageDialog(null, "Connected");
        } catch (SQLException ex) {
            Logger.getLogger(Proyecto2DistribuidosReloaded.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Failed To Connect");
        }

        return connection;
    }

    public long obtenerDistribuidor1() {
        long id = 0;
        String SQL = "SELECT * FROM distribuidor";
        try ( Connection conn = db1Connection();  PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {

            //pstmt.getFloat(1, distribuidor.getPrecioDiesel());
           // pstmt.setFloat(2, distribuidor.getPrecioKerosene());
            //pstmt.setFloat(3, distribuidor.getPrecio93());
            //pstmt.setFloat(4, distribuidor.getPrecio95());
            //pstmt.setFloat(5, distribuidor.getPrecio97());
            //pstmt.setFloat(6, distribuidor.getFactorUtilidad());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try ( ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;

    }

}