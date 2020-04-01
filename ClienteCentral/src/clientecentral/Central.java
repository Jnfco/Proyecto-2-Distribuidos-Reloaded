/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecentral;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
public class Central extends Application implements Runnable
{
    private float precio93;
    private float precio95;
    private float precio97;
    private float precioK;
    private float precioD;
    private int puerto;
    private String sql=" ";
    private String mensaje="";
    private String url1 = "jdbc:postgresql://localhost:5432/Distribuidor1";
    private String url2 ="jdbc:postgresql://localhost:5432/Distribuidor2";
    
  
    
    @Override
    public void run()
    {
        // implementar socket
        //DataInputStream in;
        DataOutputStream out;
        
        Socket sc1;
        Socket sc2;
        try
        {
            sc1 = new Socket("localhost", 1313);
            sc2 = new Socket("localhost", 1314);
            //in = new DataInputStream(sc.getInputStream());
            
            out = new DataOutputStream(sc1.getOutputStream());
            out.writeUTF(mensaje);
            if(mensaje.equals("Actualizar")){
                
                DataOutputStream precioK = new DataOutputStream(sc1.getOutputStream());
            DataOutputStream precioD = new DataOutputStream(sc1.getOutputStream());
            DataOutputStream precio93 = new DataOutputStream(sc1.getOutputStream());
            DataOutputStream precio95 = new DataOutputStream(sc1.getOutputStream());
            DataOutputStream precio97 = new DataOutputStream(sc1.getOutputStream());
            
            out.writeFloat(this.precioK);
            out.writeFloat(this.precioD);
            out.writeFloat(this.precio93);
            out.writeFloat(this.precio95);
            out.writeFloat(this.precio97);
            
            //String mensaje = in.readUTF();
            System.out.println("Se recibio el mensaje: "+mensaje);
            sc1.close();
            
            out = new DataOutputStream(sc2.getOutputStream());
            out.writeUTF(mensaje);
            precioK = new DataOutputStream(sc2.getOutputStream());
            precioD = new DataOutputStream(sc2.getOutputStream());
            precio93 = new DataOutputStream(sc2.getOutputStream());
            precio95 = new DataOutputStream(sc2.getOutputStream());
            precio97 = new DataOutputStream(sc2.getOutputStream());
            
            out.writeFloat(this.precioK);
            out.writeFloat(this.precioD);
            out.writeFloat(this.precio93);
            out.writeFloat(this.precio95);
            out.writeFloat(this.precio97);
            
            //String mensaje = in.readUTF();
            System.out.println("Se recibio el mensaje: "+mensaje);
            sc2.close();
                
            }
            if(mensaje.equals("Reporte")){
                DataInputStream url1 = new DataInputStream(sc1.getInputStream());
                setUrl1(url1.readUTF());
                sc1.close();
                
                DataInputStream url2 = new DataInputStream(sc2.getInputStream());
                setUrl2(url2.readUTF());
                sc2.close();
                
            }
            
            
        } catch (IOException ex)
        {
            Logger.getLogger(Central.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the precio93
     */
    public float getPrecio93()
    {
        return precio93;
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
    
    public void setPuerto(int puerto)
    {
        this.puerto = puerto;
    }
    
    public void setSql(String sql){
        this.sql=sql;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setMensaje(String mensaje){
        this.mensaje=mensaje;
    }

    
    public String getUrl1 (){
        return this.url1;
    }
    
    public String getUrl2(){
        return this.url2;
    }
    
    public void setUrl1(String url1){
        this.url1=url1;
    }
    
    public void setUrl2(String url2){
        this.url2=url2;
    }
}
