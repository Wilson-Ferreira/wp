/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.usuario;

import br.com.wp.autenticacao.CriptografarSenha;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.service.UsuarioService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@RequestScoped
public class AlterarSenhaUsuarioBean implements Serializable {

    @Inject
    private ListarUsuarioBean listarUsuarioBean;
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private CriptografarSenha criptografarSenha;
    @Inject
    private JsfUtil jsfUtil;
    

    public void preparaAlterarSenhaUsuario() {

        jsfUtil.alterar();
    }

    public void alterarSenhaUsuario() {

        try {

            listarUsuarioBean.getUsuario().setSenha(criptografarSenha.criptografarSenha(listarUsuarioBean.getUsuario().getSenha()));

            usuarioService.salvarAlterarUsuario(listarUsuarioBean.getUsuario());

            jsfUtil.addMensagemInfo("Senha alterada com sucesso");

            listarUsuarioBean.instanciarUsuario();
            
            jsfUtil.listar();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
            }
        }
    }
}
