/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Column(name="chave_data")
    private String chave_data;
    
    @Column(name="chave_tempo")
    private String chave_tempo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChave_data() {
        return chave_data;
    }

    public void setChave_data(String chave_data) {
        this.chave_data = chave_data;
    }

    public String getChave_tempo() {
        return chave_tempo;
    }

    @Override
    public String toString() {
        return "Contrato{" + "id=" + id + ", chave_data=" + chave_data + ", chave_tempo=" + chave_tempo + '}';
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contrato other = (Contrato) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    public void setChave_tempo(String chave_tempo) {
        this.chave_tempo = chave_tempo;
    }
    
  
     
}
