/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.enumeracao;

/**
 *
 * @author Wilson F Florindo
 */

public enum NumeroCartao {
   
    ZERO("000");
    
    private final String numero;
   
    NumeroCartao(String numero) {
        this.numero = numero;
    }

    public String numero() {
        return numero;
    }
}
