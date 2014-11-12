/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.entidades;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author Alex
 */
public class Tramite {

    int idtramite;
    int cancelado;
    Date fechacreacion;
    Date ultimamodificacion;
    int cantdoc;
    BigDecimal subtotal;
    BigDecimal iva;
    BigDecimal total;
    Cliente cliente;
    ArrayList<Documento> documentos;
    ArrayList<Abono> abonos;
    String usuario;
    BigDecimal totaladicional;
    int facturado;
    int numfactura;
    int anulado;

    public int getIdtramite() {
        return idtramite;
    }

    public void setIdtramite(int idtramite) {
        this.idtramite = idtramite;
    }

    public int getCancelado() {
        return cancelado;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Date getUltimamodificacion() {
        return ultimamodificacion;
    }

    public void setUltimamodificacion(Date ultimamodificacion) {
        this.ultimamodificacion = ultimamodificacion;
    }

    public int getCantdoc() {
        return cantdoc;
    }

    public void setCantdoc(int cantdoc) {
        this.cantdoc = cantdoc;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal.setScale(2, RoundingMode.HALF_EVEN);;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva.setScale(2, RoundingMode.HALF_EVEN);;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total.setScale(2, RoundingMode.HALF_EVEN);;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(ArrayList<Documento> documentos) {
        this.documentos = documentos;
    }

    public ArrayList<Abono> getAbonos() {
        return abonos;
    }

    public void setAbonos(ArrayList<Abono> abonos) {
        this.abonos = abonos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getTotaladicional() {
        return totaladicional;
    }

    public void setTotaladicional(BigDecimal totaladicional) {
        this.totaladicional = totaladicional;
    }

    public int getFacturado() {
        return facturado;
    }

    public void setFacturado(int facturado) {
        this.facturado = facturado;
    }

    public int getNumfactura() {
        return numfactura;
    }

    public void setNumfactura(int numfactura) {
        this.numfactura = numfactura;
    }        

    public int getAnulado() {
        return anulado;
    }

    public void setAnulado(int anulado) {
        this.anulado = anulado;
    }

    public BigDecimal totalAbonos() {
        BigDecimal totalAbonos = BigDecimal.ZERO;

        Iterator<Abono> abonosItr = abonos.iterator();

        while (abonosItr.hasNext()) {
            Abono abono = abonosItr.next();
            totalAbonos = totalAbonos.add(abono.getValor());
        }

        totalAbonos = totalAbonos.setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("Total de abonos para tramite " + this.idtramite + " es " + totalAbonos);
        return totalAbonos;
    }
}
