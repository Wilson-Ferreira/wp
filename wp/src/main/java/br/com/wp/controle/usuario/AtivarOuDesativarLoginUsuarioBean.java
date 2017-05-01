/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.usuario;

import br.com.wp.enumeracao.StatusLogin;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.service.UsuarioService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@ViewScoped
public class AtivarOuDesativarLoginUsuarioBean implements Serializable {

    @Inject
    private ListarUsuarioBean listarUsuarioBean;
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private JsfUtil jsfUtil;

    public void ativarOuDesativarLoginUsuario() {

        try {

            if (listarUsuarioBean.getUsuario().getStatusLogin().toString().equalsIgnoreCase(StatusLogin.ATIVO.toString())) {

                listarUsuarioBean.getUsuario().setStatusLogin(StatusLogin.INATIVO);

            } else {

                listarUsuarioBean.getUsuario().setStatusLogin(StatusLogin.ATIVO);
            }

            usuarioService.salvarAlterarUsuario(listarUsuarioBean.getUsuario());

            listarUsuarioBean.instanciarUsuario();

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
