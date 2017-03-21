/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Usuario;
import br.com.wp.modelo.UsuarioAutorizacao;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */

public class UsuarioAutorizacaoRepositorio implements Serializable {

    @Inject
    private EntityManager em;

    public void salvarAlterarUsuarioAutorizacao(List<UsuarioAutorizacao> lista, Usuario usuario) {

       em.createQuery("Delete From UsuarioAutorizacao ua where ua.id.usuario= :usuario")
               .setParameter("usuario", usuario).executeUpdate();
       
       lista.stream().forEach((ua) -> {
           em.persist(ua);
        });   
    }
}
