/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author User
 */
public class UsuarioRepositorio implements Serializable {

    @Inject
    private EntityManager em;

    public Usuario autenticarUsuario(String login, String senha) throws Exception {
        System.out.println("repositorio " + login + " senha " + senha);
        Usuario usuario = (Usuario) em.createQuery("Select u From Usuario u where u.funcionario.email= :login and u.senha= :senha")
                .setParameter("login", login).setParameter("senha", senha).getSingleResult();

        if (usuario != null) {

            return usuario;

        } else {

            return null;
        }
    }

    public List<Usuario> buscarTodosUsuarios() throws Exception {

        return em.createQuery("Select distinct(u) From Usuario u left join fetch u.usuarioAutorizacao").getResultList();
    }

    public void salvarAlterarUsuario(Usuario usuario) throws Exception {

        if (usuario.getId() == null) {

            em.persist(usuario);

        } else {

            em.merge(usuario);
        }
    }

    public void excluirUsuario(Usuario usuario) throws Exception {
        
        Usuario usuarioParaExcluir = em.find(Usuario.class, usuario.getId());
        em.remove(usuarioParaExcluir);
    }

    public Usuario buscarUsuarioPorId(Long id) throws Exception {

        return em.find(Usuario.class, id);
    }

}
