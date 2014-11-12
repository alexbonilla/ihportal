/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.comprobantes.utils;

/**
 *
 * @author Alex
 */
public enum AutoridadesCertificantes {

//    ANF("ANF EC 1", "ANF Autoridad Intermedia", "ANF AC", "EC", "1.3.6.1.4.1.37442"), BANCO_CENTRAL("AC BANCO CENTRAL DEL ECUADOR", "ENTIDAD DE CERTIFICACION DE INFORMACION-ECIBCE", "BANCO CENTRAL DEL ECUADOR", "EC", "1.3.6.1.4.1.37947"), SECURITY_DATA("AUTORIDAD DE CERTIFICACION SUB SECURITY DATA", "ENTIDAD DE CERTIFICACION DE INFORMACION", "SECURITY DATA S.A.", "EC", "1.3.6.1.4.1.37746");
    ANF("ANF EC 1", "ANF Autoridad Intermedia", "ANF AC", "EC", "1.3.6.1.4.1.37442"), BANCO_CENTRAL("AUTORIDAD DE CERTIFICACION RAIZ DEL BANCO CENTRAL DEL ECUADOR", "ENTIDAD DE CERTIFICACION DE INFORMACION-ECIBCE", "BANCO CENTRAL DEL ECUADOR", "EC", "1.3.6.1.4.1.37947"), SECURITY_DATA("AUTORIDAD DE CERTIFICACION SUB SECURITY DATA", "ENTIDAD DE CERTIFICACION DE INFORMACION", "SECURITY DATA S.A.", "EC", "1.3.6.1.4.1.37746");
    private final String cn;
    private final String ou;
    private final String o;
    private final String c;
    private final String oid;

    private AutoridadesCertificantes(String cn, String ou, String o, String c, String oid) {
        this.c = c;
        this.o = o;
        this.cn = cn;
        this.ou = ou;
        this.oid = oid;
    }

    public String getC() {
        return this.c;
    }

    public String getCn() {
        return this.cn;
    }

    public String getO() {
        return this.o;
    }

    public String getOu() {
        return this.ou;
    }

    public String getOid() {
        return this.oid;
    }
}
