/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.usuario;

import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Autorizacao;
import br.com.wp.modelo.Funcionario;
import br.com.wp.modelo.Usuario;
import br.com.wp.modelo.UsuarioAutorizacao;
import br.com.wp.service.AutorizacaoService;
import br.com.wp.service.FuncionarioService;
import br.com.wp.service.UsuarioService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Wilson F Florindo
 */
@Named
@ViewScoped
public class ListarUsuarioBean implements Serializable {

    @Inject
    private UsuarioService usuarioService;
    @Inject
    private AutorizacaoService autorizacaoService;
    @Inject
    private FuncionarioService funcionarioService;
    @Inject
    private Usuario usuario;
    @Inject
    private JsfUtil jsfUtil;
    private List<Usuario> listaUsuarios;
    private List<Autorizacao> listaAutorizacoesUsuarioTem;
    private List<Autorizacao> listaAutorizacoesUsuarioNaoTem;
    private List<Funcionario> listaFuncionarios;
    private List<UsuarioAutorizacao> listaUsuarioAutorizacao = new ArrayList<>();
    private DualListModel<Autorizacao> listaModelAutorizacoes;

    

    public void cancelar() {

        instanciarUsuario();
        jsfUtil.listar();
    }

    public void instanciarUsuario() {

        usuario = new Usuario();
        listaFuncionarios = new ArrayList<>();

    }

    public void buscarAutorizacoesDoSistemaEDoUsuario() {

        try {

            listaAutorizacoesUsuarioTem = autorizacaoService.buscarAutorizacoesUsuarioTem(usuario);
            listaAutorizacoesUsuarioNaoTem = autorizacaoService.buscarAutorizacoesUsuarioNaoTem(usuario);

            listaModelAutorizacoes = new DualListModel<>(listaAutorizacoesUsuarioNaoTem, listaAutorizacoesUsuarioTem);

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

    public void buscarTodosUsuarios() {

        try {

            listaUsuarios = usuarioService.buscarTodosUsuarios();

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

    public void buscarFuncionariosNaoSaoUsuarios() {

        try {

            listaFuncionarios = funcionarioService.buscarFuncionariosNaoSaoUsuarios();

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public DualListModel<Autorizacao> getListaModelAutorizacoes() {
        return listaModelAutorizacoes;
    }

    public void setListaModelAutorizacoes(DualListModel<Autorizacao> listaModelAutorizacoes) {
        this.listaModelAutorizacoes = listaModelAutorizacoes;
    }

    public List<UsuarioAutorizacao> getListaUsuarioAutorizacao() {

        return listaUsuarioAutorizacao;
    }

    public void setListaUsuarioAutorizacao(List<UsuarioAutorizacao> listaUsuarioAutorizacao) {

        this.listaUsuarioAutorizacao = listaUsuarioAutorizacao;
    }

}
