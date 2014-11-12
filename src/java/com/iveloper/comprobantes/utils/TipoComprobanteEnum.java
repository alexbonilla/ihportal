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
public class TipoComprobanteEnum {
    
    public static TipoComprobanteEnum[] values() {
        return (TipoComprobanteEnum[]) $VALUES.clone();
    }

//    public static TipoComprobanteEnum valueOf(String name) {
//        return (TipoComprobanteEnum)Enum.valueOf(ec/gob/sri/comprobantes/util/TipoComprobanteEnum, name);
//    }
    private TipoComprobanteEnum(String s, int i, String code, String xsd, String descripcion) {
//        super(s, i);
        this.code = code;
        this.xsd = xsd;
        this.descripcion = descripcion;
    }

    public String getCode() {
        return code;
    }

    public String getXsd() {
        return xsd;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static String retornaCodigo(String valor) {
        String codigo = null;
        if (valor.equals(FACTURA.getDescripcion())) {
            codigo = FACTURA.getCode();
        } else if (valor.equals(NOTA_DE_DEBITO.getDescripcion())) {
            codigo = NOTA_DE_DEBITO.getCode();
        } else if (valor.equals(NOTA_DE_CREDITO.getDescripcion())) {
            codigo = NOTA_DE_CREDITO.getCode();
        } else if (valor.equals(COMPROBANTE_DE_RETENCION.getDescripcion())) {
            codigo = COMPROBANTE_DE_RETENCION.getCode();
        } else if (valor.equals(GUIA_DE_REMISION.getDescripcion())) {
            codigo = GUIA_DE_REMISION.getCode();
        } else if (valor.equals(LIQUIDACION_DE_COMPRAS.getDescripcion())) {
            codigo = LIQUIDACION_DE_COMPRAS.getCode();
        }
        return codigo;
    }
    public static final TipoComprobanteEnum LOTE;
    public static final TipoComprobanteEnum FACTURA;
    public static final TipoComprobanteEnum NOTA_DE_CREDITO;
    public static final TipoComprobanteEnum NOTA_DE_DEBITO;
    public static final TipoComprobanteEnum GUIA_DE_REMISION;
    public static final TipoComprobanteEnum COMPROBANTE_DE_RETENCION;
    public static final TipoComprobanteEnum LIQUIDACION_DE_COMPRAS;
    private String code;
    private String xsd;
    private String descripcion;
    private static final TipoComprobanteEnum $VALUES[];

    static {
        LOTE = new TipoComprobanteEnum("LOTE", 0, "00", "lote.xsd", "");
        FACTURA = new TipoComprobanteEnum("FACTURA", 1, "01", "factura.xsd", "FACTURA");
        NOTA_DE_CREDITO = new TipoComprobanteEnum("NOTA_DE_CREDITO", 2, "04", "notaCredito.xsd", "NOTA DE CREDITO");
        NOTA_DE_DEBITO = new TipoComprobanteEnum("NOTA_DE_DEBITO", 3, "05", "notaDebito.xsd", "NOTA DE DEBITO");
        GUIA_DE_REMISION = new TipoComprobanteEnum("GUIA_DE_REMISION", 4, "06", "guiaRemision.xsd", "GUIA DE REMISION");
        COMPROBANTE_DE_RETENCION = new TipoComprobanteEnum("COMPROBANTE_DE_RETENCION", 5, "07", "comprobanteRetencion.xsd", "COMPROBANTE DE RETENCION");
        LIQUIDACION_DE_COMPRAS = new TipoComprobanteEnum("LIQUIDACION_DE_COMPRAS", 6, "03", "", "LIQ.DE COMPRAS");
        $VALUES = (new TipoComprobanteEnum[]{
            LOTE, FACTURA, NOTA_DE_CREDITO, NOTA_DE_DEBITO, GUIA_DE_REMISION, COMPROBANTE_DE_RETENCION, LIQUIDACION_DE_COMPRAS
        });
    }
}
