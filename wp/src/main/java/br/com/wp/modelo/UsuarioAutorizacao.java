/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.modelo;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author  Wilson F Florindo
 */
@Entity
public class UsuarioAutorizacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UsuarioAutorizacaoId id;

    public UsuarioAutorizacaoId getId() {
        return id;
    }

    public void setId(UsuarioAutorizacaoId id) {
        this.id = id;
    }

}
