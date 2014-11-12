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
public class TramiteItems {
    int idtramite;
    int idservicio;    
    String descripcion;
    BigDecimal precio;
    BigDecimal iva;
    int cantidad;
    BigDecimal subtotal;
    BigDecimal total;    

    public TramiteItems(int idtramite, int idservicio, String descripcion, BigDecimal precio, BigDecimal iva, int cantidad, BigDecimal subtotal, BigDecimal total) {
        this.idtramite = idtramite;
        this.idservicio = idservicio;
        this.descripcion = descripcion;
        this.precio = precio;
        this.iva = iva;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.total = total;
    }

    public int getIdtramite() {
        return idtramite;
    }

    public void setIdtramite(int idtramite) {
        this.idtramite = idtramite;
    }

    public int getIdservicio() {
        return idservicio;
    }

    public void setIdservicio(int idservicio) {
        this.idservicio = idservicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    
}
