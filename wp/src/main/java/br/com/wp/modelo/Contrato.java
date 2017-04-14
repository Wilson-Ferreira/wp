/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author  Wilson F Florindo
 */

@Entity
public class Contrato implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="chave")
    private String chave;
  
    @Transient
    private String strDataVencContrato;
    
    @Transient
    private int intTempoContrato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getStrDataVencContrato() {
        return strDataVencContrato;
    }

    public void setStrDataVencContrato(String strDataVencContrato) {
        this.strDataVencContrato = strDataVencContrato;
    }

    public int getIntTempoContrato() {
        return intTempoContrato;
    }

    public void setIntTempoContrato(int intTempoContrato) {
        this.intTempoContrato = intTempoContrato;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
  
}
