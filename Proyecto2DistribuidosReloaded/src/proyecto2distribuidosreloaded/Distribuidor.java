/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2distribuidosreloaded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 *
 * @author Nicolás Hervias
 */
public class Distribuidor extends Application implements Runnable
{
    private float precio93;
    private float precio95;
    private float precio97;
    private float precioK;
    private float precioD;
    private float factorUtilidad;
    private int puerto;
    String urlDB1 = "jdbc:postgresql://localhost:5432/Distribuidor1";
    String urlDB2 = "jdbc:postgresql://localhost:5432/Distribuidor2";
    String user = "postgres";
    String password = "Distribuidos1234";
    private Connection c1;
    private Connection c2;
    
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override
    public void run()
    {
        // implementar Socket Server
        ServerSocket servidor1 = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;
        
        
        try
        {
            servidor1 = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");
            
            while(true)
            {
                sc = servidor1.accept();
                System.out.println("Cliente conectado");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
                String mensaje = in.readUTF();
                
                if(mensaje.equals("actualizar")){
                    actualizarPrecios(c1,c2);
                }
                System.out.println(mensaje);
                out.writeUTF(" SERVER !");
                sc.close();
                System.out.println("Cliente desconectado");
            }
            
        } catch (IOException ex)
        {
            Logger.getLogger(Proyecto2DistribuidosReloaded.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the precio93
     */
    public float getPrecio93()
    {
        return precio93;
    }
     public long  actualizarPrecios(Connection c1 ,Connection c2){
         System.out.println("Entro!");
        String sql = "UPDATE distribuidor\n" +"SET preciodiesel = ? , preciokerosene = ? ,precio93= ? , precio95=? ,precio97 =?" + "WHERE iddistribuidor =1;";
        long id = 0;
        
        try (
                PreparedStatement pstmt = c1.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS))
        {
            pstmt.setFloat(1, this.getPrecioD());
            pstmt.setFloat(2, this.getPrecioK());
            pstmt.setFloat(3, this.getPrecio93());
            pstmt.setFloat(4, this.getPrecio95());
            pstmt.setFloat(5, this.getPrecio97());
            
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
            pstmt.setFloat(1, this.getPrecioD());
            pstmt.setFloat(2, this.getPrecioK());
            pstmt.setFloat(3, this.getPrecio93());
            pstmt.setFloat(4, this.getPrecio95());
            pstmt.setFloat(5, this.getPrecio97());
            
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

    /**
     * @param precio93 the precio93 to set
     */
    public void setPrecio93(float precio93)
    {
        this.precio93 = precio93;
    }

    /**
     * @return the precio95
     */
    public float getPrecio95()
    {
        return precio95;
    }

    /**
     * @param precio95 the precio95 to set
     */
    public void setPrecio95(float precio95)
    {
        this.precio95 = precio95;
    }

    /**
     * @return the precio97
     */
    public float getPrecio97()
    {
        return precio97;
    }

    /**
     * @param precio97 the precio97 to set
     */
    public void setPrecio97(float precio97)
    {
        this.precio97 = precio97;
    }

    /**
     * @return the precioK
     */
    public float getPrecioK()
    {
        return precioK;
    }

    /**
     * @param precioK the precioK to set
     */
    public void setPrecioK(float precioK)
    {
        this.precioK = precioK;
    }

    /**
     * @return the precioD
     */
    public float getPrecioD()
    {
        return precioD;
    }

    /**
     * @param precioD the precioD to set
     */
    public void setPrecioD(float precioD)
    {
        this.precioD = precioD;
    }

    /**
     * @return the factorUtilidad
     */
    public float getFactorUtilidad()
    {
        return factorUtilidad;
    }

    /**
     * @param factorUtilidad the factorUtilidad to set
     */
    public void setFactorUtilidad(float factorUtilidad)
    {
        this.factorUtilidad = factorUtilidad;
    }
    
    public void setPuerto(int puerto)
    {
        this.puerto = puerto;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}