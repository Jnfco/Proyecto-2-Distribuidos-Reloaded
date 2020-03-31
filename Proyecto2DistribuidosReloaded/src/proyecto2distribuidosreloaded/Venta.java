/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2distribuidosreloaded;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto2distribuidosreloaded.Proyecto2DistribuidosReloaded;

/**
 *
 * @author Nicolás Hervias
 */
public class Venta {

    private int idSurtidor;
    private float cantidadLitros;
    private float precioActual;
    private float valorVenta;
    Connection connection;
    private Connection c1;
    private Connection c2;
    private float valorActual;
    private float factorutilidad;

    String urlDB1 = "jdbc:postgresql://localhost:5432/Distribuidor1";
    //String urlDB2 = "jdbc:postgresql://localhost:5432/Distribuidor2Resp";
    String user = "postgres";
    String password = "Distribuidos1234";
    
    public Venta()
    {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            c1 = DriverManager.getConnection(urlDB1, user, password);
            //c2 = DriverManager.getConnection(urlDB2, user, password);
            
            //this.factorutilidad = 
            //this.idSurtidor = (int) ((Math.random() * ((5 - 1) + 1)) + 1);
            //this.cantidadLitros = (float) ((Math.random() * ((20 - 5) + 1)) + 5);
            //this.precioActual = (float) ((Math.random() * ((950 - 600) + 1)) + 600);
            //this.valorVenta = this.cantidadLitros * this.precioActual;
        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Venta(float factorutilidad) {
        this.factorutilidad=factorutilidad/100;
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            c1 = DriverManager.getConnection(urlDB1, user, password);
            //c2 = DriverManager.getConnection(urlDB2, user, password);
            
            this.idSurtidor = (int) ((Math.random() * ((5 - 1) + 1)) + 1);
            this.cantidadLitros = (float) ((Math.random() * ((20 - 5) + 1)) + 5);
            this.precioActual = (float) ((Math.random() * ((950 - 600) + 1)) + 600);
            this.valorVenta = this.cantidadLitros * this.precioActual;
        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public float  getPrecioActual() throws SQLException{
        String sql = "select preciolitro from surtidor where idsurtidor ="+this.idSurtidor;

        Proyecto2DistribuidosReloaded p2 = new Proyecto2DistribuidosReloaded();
        Connection c = p2.dbConnectionDB1();
        
        
        ResultSet rs = c.createStatement().executeQuery(sql);
        while (rs.next())
        {
            this.valorActual = (float) (Float.parseFloat(rs.getString(1))/* * (1 + (getFactorUtilidad() / 100.0))*/) ;
        }

        return this.valorActual;
    }
    
    public float getFactorUtilidad() throws SQLException
    {
        String sql = "SELECT factorutilidad from distribuidor where iddistribuidor = 1;";
        
        Proyecto2DistribuidosReloaded p2 = new Proyecto2DistribuidosReloaded();
        Connection c = p2.dbConnectionDB1();
        
        ResultSet rs = c.createStatement().executeQuery(sql);
        
        while(rs.next())
        {
            this.factorutilidad = Float.parseFloat(rs.getString(1));
        }
        return this.factorutilidad;
    }
    

    /**
     * @return the idSurtidor
     */
    public int getIdSurtidor() {
        return idSurtidor;
    }

    /**
     * @param idSurtidor the idSurtidor to set
     */
    public void setIdSurtidor(int idSurtidor) {
        this.idSurtidor = idSurtidor;
    }

    /**
     * @return the cantidadLitros
     */
    public float getCantidadLitros() {
        return cantidadLitros;
    }

    /**
     * @param cantidadLitros the cantidadLitros to set
     */
    public void setCantidadLitros(float cantidadLitros) {
        this.cantidadLitros = cantidadLitros;
    }


    /**
     * @param precioActual the precioActual to set
     */
    public void setPrecioActual(float precioActual) {
        this.precioActual = precioActual;
    }

    /**
     * @return the valorVenta
     */
    public float getValorVenta() {
        return valorVenta;
    }

    /**
     * @param valorVenta the valorVenta to set
     */
    public void setValorVenta(float valorVenta) {
        this.valorVenta = valorVenta;
    }

}
