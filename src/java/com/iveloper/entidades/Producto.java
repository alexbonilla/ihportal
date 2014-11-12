/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.entidades;

import java.util.List;

/**
 *
 * @author Alex
 */
public class Producto {
    private String codPrincipal;
    private String codAuxiliar;
    private String tipoProducto;
    private String nombre;
    private String valorUnitario;
    private List<ProductoInfoAdicional> infoAdicional;

    public String getCodPrincipal() {
        return codPrincipal;
    }

    public void setCodPrincipal(String codPrincipal) {
        this.codPrincipal = codPrincipal;
    }

    public String getCodAuxiliar() {
        return codAuxiliar;
    }

    public void setCodAuxiliar(String codAuxiliar) {
        this.codAuxiliar = codAuxiliar;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public List<ProductoInfoAdicional> getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(List<ProductoInfoAdicional> infoAdicional) {
        this.infoAdicional = infoAdicional;
    }


}
