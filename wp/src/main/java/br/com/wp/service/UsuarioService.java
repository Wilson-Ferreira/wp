/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.jpa.Transactional;
import br.com.wp.modelo.Usuario;
import br.com.wp.repositorio.UsuarioRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
public class UsuarioService implements Serializable {

    @Inject
    private UsuarioRepositorio usuarioRepositorio;

    public Usuario autenticarUsuario(String login, String senha)throws Exception {

        return usuarioRepositorio.autenticarUsuario(login, senha);

    }

    public List<Usuario> buscarTodosUsuarios() throws Exception {
        return usuarioRepositorio.buscarTodosUsuarios();

    }

    @Transactional
    public void salvarAlterarUsuario(Usuario usuario) throws Exception {
        usuarioRepositorio.salvarAlterarUsuario(usuario);

    }

    @Transactional
    public void excluirUsuario(Usuario usuario) throws Exception {
        usuarioRepositorio.excluirUsuario(usuario);

    }

    public Usuario buscarUsuarioPorId(Long id) throws Exception {
        return usuarioRepositorio.buscarUsuarioPorId(id);

    }

    public Usuario autenticarUsuarioAndroid(Usuario usuario) {
      return null;
        
    }
}
