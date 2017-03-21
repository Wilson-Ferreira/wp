/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.jpa.Transactional;
import br.com.wp.modelo.Autorizacao;
import br.com.wp.modelo.Usuario;
import br.com.wp.modelo.UsuarioAutorizacao;
import br.com.wp.repositorio.UsuarioAutorizacaoRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */

public class UsuarioAutorizacaoService implements Serializable {

    @Inject
    private UsuarioAutorizacaoRepositorio usuarioAutorizacaoRepositorio;

    @Transactional
    public void salvarAlterarUsuarioAutorizacao(List<UsuarioAutorizacao> lista, Usuario usuario) throws Exception {

        usuarioAutorizacaoRepositorio.salvarAlterarUsuarioAutorizacao(lista,usuario);
    }
}
