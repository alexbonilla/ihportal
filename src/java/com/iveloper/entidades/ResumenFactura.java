/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.entidades;

import java.math.BigDecimal;

/**
 *
 * @author Alex
 */
public class ResumenFactura {
    String claveacceso;
    String numfactura;
    String fechaemision;
    String idcliente;
    BigDecimal importetotal;

    public String getClaveacceso() {
        return claveacceso;
    }

    public void setClaveacceso(String claveacceso) {
        this.claveacceso = claveacceso;
    }

    public String getNumfactura() {
        return numfactura;
    }

    public void setNumfactura(String numfactura) {
        this.numfactura = numfactura;
    }

    public String getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(String fechaemision) {
        this.fechaemision = fechaemision;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public BigDecimal getImportetotal() {
        return importetotal;
    }

    public void setImportetotal(BigDecimal importetotal) {
        this.importetotal = importetotal;
    }
    
    
}
