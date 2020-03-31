/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2distribuidosreloaded;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author jnfco
 */
public class FXMLDocumentController implements Initializable {

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
    private void handleButtonAction(ActionEvent event) throws IOException {
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

    @FXML
    public void handleButtonK(ActionEvent event) {
        this.surtidorSeleccionado = 1;
        p2.data1.removeAll(p2.data1);
        this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1, 1));
    }

    @FXML
    public void handleButtonD(ActionEvent event) {
        this.surtidorSeleccionado = 2;
        p2.data1.removeAll(p2.data1);
        this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1, 2));
    }

    @FXML
    public void handleButton93(ActionEvent event) {
        this.surtidorSeleccionado = 3;
        p2.data1.removeAll(p2.data1);
        this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1, 3));
    }

    @FXML
    public void handleButton95(ActionEvent event) {
        this.surtidorSeleccionado = 4;
        p2.data1.removeAll(p2.data1);
        this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1, 4));
    }

    @FXML
    public void handleButton97(ActionEvent event) {
        this.surtidorSeleccionado = 5;
        p2.data1.removeAll(p2.data1);
        this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1, 5));
    }

    @FXML
    public void handleButtonCaida(ActionEvent event) {
        p2.setUrl("");
        p2.setTimestamp();
        this.reconectar.setDisable(false);
        this.caida.setDisable(true);
        p2.setUrlDistribuidor();
    }

    @FXML
    public void handleButtonReconectar(ActionEvent event) throws SQLException {
        JOptionPane.showMessageDialog(null, "Conectado a la base de datos principal.");
        p2.setUrl("jdbc:postgresql://localhost:5432/Distribuidor1");
        p2.setUrlDistribuidor();

        Connection c2 = p2.dbConnectionDB2();
        Connection c1 = p2.dbConnectionDB1();

        String sqlSelect = "SELECT * FROM venta WHERE fecha > '" + p2.ts + "';";
        ResultSet rs = c2.createStatement().executeQuery(sqlSelect);

        //ArrayList<Venta> ventas = new ArrayList<>();
        while (rs.next()) {
            Venta v = new Venta();
            //Iterate Row
            //ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                switch (i) {
                    case 1:
                        v.setIdSurtidor(Integer.parseInt(rs.getString(i)));
                        break;
                    case 2:
                        v.setCantidadLitros(Float.parseFloat(rs.getString(i)));
                        break;
                    case 3:
                        v.setValorVenta(Float.parseFloat(rs.getString(i)));
                        break;
                    case 4:
                        break;
                    case 5:
                        v.setPrecioActual(Float.parseFloat(rs.getString(i)));
                        break;
                    case 6:
                        v.setFecha(rs.getTimestamp(i));
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            //ventas.add(v);

            String sqlInsert = "INSERT INTO venta (idsurtidor, cantidadlitros, valorventa, precioactual, fecha) "
                    + "VALUES (?, ?, ?, ?, ?);";

            long id = 0;

            try ( PreparedStatement pstmt = c1.prepareStatement(sqlInsert,
                    Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, v.getIdSurtidor());
                pstmt.setFloat(2, v.getCantidadLitros());
                pstmt.setFloat(3, v.getValorVenta());
                pstmt.setFloat(4, v.getPrecioActual());
                pstmt.setTimestamp(5, v.getFecha());

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try ( ResultSet rs2 = pstmt.getGeneratedKeys()) {
                        if (rs2.next()) {
                            id = rs2.getLong(1);
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }
        p2.resetTimestamp();

        this.reconectar.setDisable(true);
        this.caida.setDisable(false);

        String sqlDist = "SELECT preciokerosene,preciodiesel,precio93,precio95,precio97,factorutilidad FROM distribuidor;";

        ResultSet rs2 = c2.createStatement().executeQuery(sqlDist);
        Distribuidor d = new Distribuidor();

        while (rs2.next()) {
            for (int i = 1; i <= rs2.getMetaData().getColumnCount(); i++) {
                switch (i) {

                    case 1:
                        d.setPrecioK(Float.parseFloat(rs2.getString(i)));
                        break;
                    case 2:
                        d.setPrecioD(Float.parseFloat(rs2.getString(i)));
                        break;
                    case 3:
                        d.setPrecio93(Float.parseFloat(rs2.getString(i)));
                        break;
                    case 4:
                        d.setPrecio95(Float.parseFloat(rs2.getString(i)));
                        break;
                    case 5:
                        d.setPrecio97(Float.parseFloat(rs2.getString(i)));
                        break;
                    case 6:
                        d.setFactorUtilidad(Float.parseFloat(rs2.getString(i)));
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }

        String sqlupdate = "UPDATE distribuidor SET preciodiesel = ?"
                + ", preciokerosene = ?"
                + ", precio93 = ?"
                + ", precio95 = ?"
                + ", precio97 = ?"
                + ", factorutilidad = ?"
                + ";";

        long id = 0;

        try (
                 PreparedStatement pstmt = c1.prepareStatement(sqlupdate,
                        Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setFloat(1, d.getPrecioD());
            pstmt.setFloat(2, d.getPrecioK());
            pstmt.setFloat(3, d.getPrecio93());
            pstmt.setFloat(4, d.getPrecio95());
            pstmt.setFloat(5, d.getPrecio97());
            pstmt.setFloat(6, d.getFactorUtilidad());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try ( ResultSet rs3 = pstmt.getGeneratedKeys()) {
                    if (rs3.next()) {
                        id = rs3.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.surtidorSeleccionado = 0;
        // TODO
        this.estacion1.wrapTextProperty().setValue(true);
        //this.estacion2.wrapTextProperty().setValue(true);

        //this.tablaEst2 = new TableView(p2.mostrarDatos2(tablaEst2));
        this.reconectar.setDisable(true);
    }

    @FXML
    private void botonEstacion1Handler(ActionEvent event) throws SQLException {

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

        if (p2.getUrl1().equals("")) {
            Connection c2 = p2.dbConnectionDB2();
            Statement st2 = c2.createStatement();
            c2.setAutoCommit(false);
            st2.execute(tran);
            c2.commit();
            c2.setAutoCommit(true);
        } else {
            Connection c = p2.dbConnectionDB1();
            Connection c2 = p2.dbConnectionDB2();
            Statement st = c.createStatement();
            Statement st2 = c2.createStatement();
            c.setAutoCommit(false);
            c2.setAutoCommit(false);
            st.execute(tran);
            st2.execute(tran);
            c.commit();
            c2.commit();
            c.setAutoCommit(true);
            c2.setAutoCommit(true);
        }

        // transacción actualiza el preciolitro del surtidor aplicándole el factor de utilidad
        //PreparedStatement pst = c.prepareStatement(tran);
        //System.out.println("Prepared statement: "+st);
        //System.out.println("exec"+st.execute(tran));
    }

    @FXML
    private void botonEstacion2Handler(ActionEvent event) {
        System.out.println("e2");
    }

    @FXML
    private void agregarVenta1Handler(ActionEvent event) throws IOException, SQLException {
        System.out.println("Venta del surtidor: " + this.surtidorSeleccionado);
        //Venta newVenta = new Venta(3);
        //p2.ingresarVenta1(newVenta);
        //p2.data1.removeAll(p2.data1);
        //this.tablaEst1 = new TableView(p2.mostrarDatos1(tablaEst1));

        if (!(this.surtidorSeleccionado == 0)) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfazVenta.fxml")); //Se crea el fxmloader para la interfaz de venta
            Stage stage = new Stage();
            Parent root = (Parent) fxmlLoader.load();
            InterfazVentaController ventaController = fxmlLoader.<InterfazVentaController>getController();//llamamos al controlador para la interfaz venta
            ventaController.setIdSurtidor(surtidorSeleccionado);// seteamos el id del surtidor marcado por le boton al controlador de la venta, asi llega con el id especifico

            ventaController.setConnection(p2.dbConnectionDB1());
            ventaController.setC2(p2.dbConnectionDB2());

            ventaController.setUrl1(p2.getUrl1());

            //interfaz.setIdSurtidor(surtidorSeleccionado);
            // stage.setTitle("Register"); 
            stage.setScene(new Scene(root, 448, 300));
            stage.setResizable(false);
            stage.show();
        }
    }

}
