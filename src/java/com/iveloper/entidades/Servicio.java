/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.entidades;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Alex
 */
public class Servicio {

    int id;
    String descripcion;
    String tipo;
    BigDecimal precio;
    BigDecimal iva;
    BigDecimal total;
    Date fechacreacion;
    Date ultimamodificacion;
    int preciovariable;

    public Servicio() {
    }

    public Servicio(int id, String descripcion, String tipo, BigDecimal precio, BigDecimal iva, BigDecimal total, Date fechacreacion, Date ultimamodificacion, int preciovariable) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.iva = iva;
        this.total = total;
        this.fechacreacion = fechacreacion;
        this.ultimamodificacion = ultimamodificacion;
        this.preciovariable = preciovariable;        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }
    
    public String getInicialTipo() {
        return tipo.substring(0,1).toUpperCase();
    }    

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio.setScale(2, RoundingMode.HALF_EVEN);;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva=iva.setScale(2, RoundingMode.HALF_EVEN);;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total=total.setScale(2, RoundingMode.HALF_EVEN);;
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

    public int isPreciovariable() {
        return preciovariable;
    }

    public void setPreciovariable(int preciovariable) {
        this.preciovariable = preciovariable;
    }

    public JSONObject toJSONObjectForCombo() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", this.id);
            json.put("descripcion", this.descripcion);
        } catch (JSONException ex) {
            System.out.println("" + ex);
        }
        return json;
    }
}
