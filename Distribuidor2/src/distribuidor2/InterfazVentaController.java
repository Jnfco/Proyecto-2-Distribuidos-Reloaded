/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distribuidor2;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    private Connection c;
    private Connection c2;
    private String url1;
    
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
    private void handleButtonAceptar(ActionEvent event) throws SQLException {

        Venta v = new Venta();
        v.setIdSurtidor(this.idSurtidor);
        v.setCantidadLitros(Integer.parseInt(this.litrosField.getText()));
        v.setValorVenta(v.getPrecioActual() * v.getCantidadLitros());
        
        //Aca poner el codigo para llamar a la venta!!!
        System.out.println("url: " + getUrl1());
        if(getUrl1().equals(""))
        {
            ingresarVentaBackup(v);
        }
        else
        {
           ingresarVenta1(v);
           ingresarVentaBackup(v);
        }
        
        
        
        
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
    
    
    public long ingresarVenta1(Venta venta) {
        String sql = "INSERT INTO venta (idsurtidor, cantidadlitros, valorventa, precioactual, fecha) "
                + "VALUES (?, ?, ?, ?, current_timestamp);";

        long id = 0;
        

        try ( PreparedStatement pstmt = this.c.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, venta.getIdSurtidor());
            pstmt.setFloat(2, venta.getCantidadLitros());
            pstmt.setFloat(3, venta.getValorVenta());
            pstmt.setFloat(4, venta.getPrecioActual());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
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
    
    public long ingresarVentaBackup(Venta venta) {
        String sql = "INSERT INTO venta (idsurtidor, cantidadlitros, valorventa, precioactual, fecha) "
                + "VALUES (?, ?, ?, ?, current_timestamp);";

        long id = 0;
        

        try ( PreparedStatement pstmt = this.getC2().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, venta.getIdSurtidor());
            pstmt.setFloat(2, venta.getCantidadLitros());
            pstmt.setFloat(3, venta.getValorVenta());
            pstmt.setFloat(4, venta.getPrecioActual());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
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

    
    public Connection getConnection()
    {
        return c;
    }

    
    public void setConnection(Connection c)
    {
        this.c = c;
    }

    /**
     * @return the url1
     */
    public String getUrl1()
    {
        return url1;
    }

    /**
     * @param url1 the url1 to set
     */
    public void setUrl1(String url1)
    {
        this.url1 = url1;
    }

    /**
     * @return the c2
     */
    public Connection getC2()
    {
        return c2;
    }

    /**
     * @param c2 the c2 to set
     */
    public void setC2(Connection c2)
    {
        this.c2 = c2;
    }

}
