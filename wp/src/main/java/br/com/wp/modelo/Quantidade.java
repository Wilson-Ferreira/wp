/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Wilson F Florindo
 */
@Entity
public class Quantidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "quantidade", precision = 3, scale = 1)
    private BigDecimal quantidade;
   
    @Column(name = "quantString", unique = true, length = 3)
    private String quantString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/*
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
*/
    public String getQuantString() {
        return quantString;
    }

    public void setQuantString(String quantString) {
        this.quantString = quantString;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final Quantidade other = (Quantidade) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Quantidade{" + "id=" + id + ", quantidade=" + getQuantidade() + ", quantString=" + quantString + '}';
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
    

}
