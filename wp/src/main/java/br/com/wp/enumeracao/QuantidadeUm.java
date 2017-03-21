/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.enumeracao;

import java.math.BigDecimal;

/**
 *
 * @author Wilson F Florindo
 */
public enum QuantidadeUm {
    
     UM(new BigDecimal("01.00"));
    
    private final BigDecimal qtde;
   
    QuantidadeUm(BigDecimal qtde) {
        this.qtde = qtde;
    }

    public BigDecimal qtde() {
        return qtde;
    }
}
