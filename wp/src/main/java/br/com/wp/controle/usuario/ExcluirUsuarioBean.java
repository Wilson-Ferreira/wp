/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.usuario;

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
public class ExcluirUsuarioBean implements Serializable {

    @Inject
    private ListarUsuarioBean listarUsuarioBean;
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private JsfUtil jsfUtil;

    public void preparaExcluirUsuario() {

        jsfUtil.abrirFecharDialog("PF('dialogConfirmaExclusao').show();");
    }

    public void excluirUsuario() {

        try {

            usuarioService.excluirUsuario(listarUsuarioBean.getUsuario());

            listarUsuarioBean.getListaUsuarios().remove(listarUsuarioBean.getUsuario());

            jsfUtil.addMensagemInfo("Usuário excluido com sucesso");

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
