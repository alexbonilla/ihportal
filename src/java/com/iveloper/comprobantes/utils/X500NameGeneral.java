/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iveloper.comprobantes.utils;

import java.util.StringTokenizer;

/**
 *
 * @author Alex
 */
public class X500NameGeneral {

    private String CN = null;
    private String OU = null;
    private String O = null;
    private String L = null;
    private String ST = null;
    private String C = null;

    public X500NameGeneral(String name) {
        StringTokenizer st = new StringTokenizer(name, ",");
        while (st.hasMoreTokens()) {
            String token = st.nextToken().trim();
            int idx = token.indexOf("=");
            if (idx >= 0) {
                String label = token.substring(0, idx);
                String value = token.substring(idx + 1);
                if ("CN".equals(label)) {
                    this.CN = value;
                } else if ("OU".equals(label)) {
                    this.OU = value;
                } else if ("O".equals(label)) {
                    this.O = value;
                } else if ("C".equals(label)) {
                    this.C = value;
                } else if ("L".equals(label)) {
                    this.L = value;
                } else if ("ST".equals(label)) {
                    this.ST = value;
                }
            }
        }
    }

    public String getC() {
        return this.C;
    }

    public String getCN() {
        return this.CN;
    }

    public String getL() {
        return this.L;
    }

    public String getO() {
        return this.O;
    }

    public String getOU() {
        return this.OU;
    }

    public String getST() {
        return this.ST;
    }
}
