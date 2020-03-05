/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2distribuidosreloaded;

/**
 *
 * @author Nicolás Hervias
 */
public class Venta
{
    private int idSurtidor;
    private float cantidadLitros;
    private float precioActual;
    private float valorVenta;
    
    public Venta()
    {
        this.idSurtidor = (int) ((Math.random() * ((5 - 1) + 1)) + 1);
        this.cantidadLitros = (float) ((Math.random() * ((20 - 5) + 1)) + 5);
        this.precioActual = (float) ((Math.random() * ((950 - 600) + 1)) + 600);
        this.valorVenta = this.cantidadLitros * this.precioActual;
    }

    /**
     * @return the idSurtidor
     */
    public int getIdSurtidor()
    {
        return idSurtidor;
    }

    /**
     * @param idSurtidor the idSurtidor to set
     */
    public void setIdSurtidor(int idSurtidor)
    {
        this.idSurtidor = idSurtidor;
    }

    /**
     * @return the cantidadLitros
     */
    public float getCantidadLitros()
    {
        return cantidadLitros;
    }

    /**
     * @param cantidadLitros the cantidadLitros to set
     */
    public void setCantidadLitros(float cantidadLitros)
    {
        this.cantidadLitros = cantidadLitros;
    }

    /**
     * @return the precioActual
     */
    public float getPrecioActual()
    {
        return precioActual;
    }

    /**
     * @param precioActual the precioActual to set
     */
    public void setPrecioActual(float precioActual)
    {
        this.precioActual = precioActual;
    }

    /**
     * @return the valorVenta
     */
    public float getValorVenta()
    {
        return valorVenta;
    }

    /**
     * @param valorVenta the valorVenta to set
     */
    public void setValorVenta(float valorVenta)
    {
        this.valorVenta = valorVenta;
    }
    
}
