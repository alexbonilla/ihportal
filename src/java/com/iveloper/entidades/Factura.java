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
public class Factura {

    private String idFactura;
    private GuiaRemision guiaRemision;
    private String fechaEmision;
    private String autorizacion;
    private boolean firmada;
    private BigDecimal subtotal;
    private BigDecimal total;
    private BigDecimal totalDcto;
    private BigDecimal propina;
    private List<ImpuestoFactura> impuestosFactura;
    private PuntoEmision puntoEmision;
    private Cliente cliente;
    private List<ItemFactura> items;
    private List<DatoAdicionalFactura> datosAdicionales;

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public GuiaRemision getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public boolean isFirmada() {
        return firmada;
    }

    public void setFirmada(boolean firmada) {
        this.firmada = firmada;
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

    public BigDecimal getTotalDcto() {
        return totalDcto;
    }

    public void setTotalDcto(BigDecimal totalDcto) {
        this.totalDcto = totalDcto;
    }

    public BigDecimal getPropina() {
        return propina;
    }

    public void setPropina(BigDecimal propina) {
        this.propina = propina;
    }

    public PuntoEmision getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(PuntoEmision puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }

    public List<DatoAdicionalFactura> getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(List<DatoAdicionalFactura> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }

    public List<ImpuestoFactura> getImpuestosFactura() {
        return impuestosFactura;
    }

    public void setImpuestosFactura(List<ImpuestoFactura> impuestosFactura) {
        this.impuestosFactura = impuestosFactura;
    }

}
