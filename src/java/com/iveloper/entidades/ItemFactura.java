/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.entidades;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Alex
 */
public class ItemFactura {

    private Producto producto;
    private int cantidad;
    private BigDecimal valorUnitario;
    private BigDecimal descuento;
    private BigDecimal valorTotal;
    private BigDecimal valorICE;
    private String accion;
    private List<ImpuestoItemFactura> impuestosItem;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorICE() {
        return valorICE;
    }

    public void setValorICE(BigDecimal valorICE) {
        this.valorICE = valorICE;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public List<ImpuestoItemFactura> getImpuestosItem() {
        return impuestosItem;
    }

    public void setImpuestosItem(List<ImpuestoItemFactura> impuestosItem) {
        this.impuestosItem = impuestosItem;
    }

}
