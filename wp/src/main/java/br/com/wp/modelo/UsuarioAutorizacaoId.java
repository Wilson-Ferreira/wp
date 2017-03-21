/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author  Wilson F Florindo
 */
@Embeddable
public class UsuarioAutorizacaoId implements Serializable {
    private static final long serialVersionUID = 1L;
  
    @NotNull(message = "Campo usuário é obrigatório!")
    @ManyToOne
    private Usuario usuario;
    
    @NotNull(message = "Campo autorização é obrigatório!")
    @ManyToOne
    private Autorizacao autorizacao;

   
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Autorizacao getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(Autorizacao autorizacao) {
        this.autorizacao = autorizacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.usuario);
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
        final UsuarioAutorizacaoId other = (UsuarioAutorizacaoId) obj;
        return Objects.equals(this.usuario, other.usuario);
    }

    @Override
    public String toString() {
        return "UsuarioAutorizacaoPk{" + "usuario=" + usuario + '}';
    }

  
}
