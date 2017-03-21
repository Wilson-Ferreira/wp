/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Wilson F Florindo
 */
@Entity
public class Autorizacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Autorização é obrigatório!")
    @Column(name="tipo" ,unique=true,length = 40)
    private String tipo;
    
    @NotEmpty(message = "Nome fantazia é obrigatório!")
    @Column(name="nomeFantazia",unique=true,length = 40)
    private String nomeFantazia;
    
    @OneToMany(mappedBy = "id.autorizacao",cascade = {CascadeType.PERSIST, CascadeType.REMOVE })
    private List<UsuarioAutorizacao> usuarioAutorizacao = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autorizacao)) {
            return false;
        }
        Autorizacao other = (Autorizacao) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "br.com.wp.modelo.Autorizacao[ id=" + id + " ]";
    }

    public List<UsuarioAutorizacao> getUsuarioAutorizacao() {
        return usuarioAutorizacao;
    }

    public void setUsuarioAutorizacao(List<UsuarioAutorizacao> usuarioAutorizacao) {
        this.usuarioAutorizacao = usuarioAutorizacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeFantazia() {
        return nomeFantazia;
    }

    public void setNomeFantazia(String nomeFantazia) {
        this.nomeFantazia = nomeFantazia;
    }
    

}
