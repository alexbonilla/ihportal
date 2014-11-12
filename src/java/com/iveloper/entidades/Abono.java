/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.entidades;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Alex
 */
public class Abono {
    int idabono;
    BigDecimal valor;
    Date fechaabono;
    int idtramite;

    public int getIdabono() {
        return idabono;
    }

    public void setIdabono(int idabono) {
        this.idabono = idabono;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getFechaabono() {
        return fechaabono;
    }

    public void setFechaabono(Date fechaabono) {
        this.fechaabono = fechaabono;
    }

    public int getIdtramite() {
        return idtramite;
    }

    public void setIdtramite(int idtramite) {
        this.idtramite = idtramite;
    }    
}
