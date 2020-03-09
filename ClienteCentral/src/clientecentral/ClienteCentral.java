/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecentral;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jnfco
 */
public class ClienteCentral extends Application {
    
    private static final Central central = new Central();
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Central.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        central.setPuerto(1313);
        central.setSql("Select surtidor.idsurtidor,distribuidor.iddistribuidor,surtidor.tipocombustible ,round(Avg(venta.cantidadlitros),2 )as promediolitros ,round(Avg(venta.valorventa),2) as promedioventa\n"
                + "  From  distribuidor, surtidor, venta\n"
                + "  where distribuidor.iddistribuidor = surtidor.iddistribuidor and surtidor.idsurtidor = venta.idsurtidor \n"
                + "  Group By surtidor.idsurtidor, distribuidor.iddistribuidor;");
        Thread cThread = new Thread(central);
         cThread.start();
         cThread = new Thread(central);
        central.setPuerto(5000);
        cThread.start();
        launch(args);
    }
    
}
