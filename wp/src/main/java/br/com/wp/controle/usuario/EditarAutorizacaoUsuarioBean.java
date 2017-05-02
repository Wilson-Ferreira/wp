/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.usuario;

import br.com.wp.autenticacao.CriptografarSenha;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Autorizacao;
import br.com.wp.modelo.Funcionario;
import br.com.wp.modelo.Usuario;
import br.com.wp.modelo.UsuarioAutorizacao;
import br.com.wp.modelo.UsuarioAutorizacaoId;
import br.com.wp.service.AutorizacaoService;
import br.com.wp.service.FuncionarioService;
import br.com.wp.service.UsuarioAutorizacaoService;
import br.com.wp.service.UsuarioService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@RequestScoped
public class EditarAutorizacaoUsuarioBean implements Serializable {

    @Inject
    private ListarUsuarioBean listarUsuarioBean;
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private CriptografarSenha criptografarSenha;
    @Inject
    private UsuarioAutorizacao usuarioAutorizacao;
    @Inject
    private UsuarioAutorizacaoId usuarioAutorizacaoId;
    @Inject
    private UsuarioAutorizacaoService usuarioAutorizacaoService;
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
    private DualListModel<Autorizacao> listaModelAutorizacoes;
    private List<UsuarioAutorizacao> listaUsuarioAutorizacao;

    public void preparaEditarAutorizacoesUsuario() {

        listarUsuarioBean.buscarAutorizacoesDoSistemaEDoUsuario();
        jsfUtil.preparaEditarAutorizacoes();
    }

    public void atualizarAutorizacoesUsuario() throws Exception {

        listaUsuarioAutorizacao = new ArrayList<>();

        try {
        for (Autorizacao autorizacao : listarUsuarioBean.getListaModelAutorizacoes().getTarget()) {

            System.out.println("aut " + autorizacao.getNomeFantazia() + " usu " + listarUsuarioBean.getUsuario().getFuncionario().getNome());
            usuarioAutorizacaoId = new UsuarioAutorizacaoId();
            usuarioAutorizacao = new UsuarioAutorizacao();

            usuarioAutorizacaoId.setAutorizacao(autorizacao);
            usuarioAutorizacaoId.setUsuario(listarUsuarioBean.getUsuario());
            usuarioAutorizacao.setId(usuarioAutorizacaoId);

            listaUsuarioAutorizacao.add(usuarioAutorizacao);
        }

        usuarioAutorizacaoService.salvarAlterarUsuarioAutorizacao(listaUsuarioAutorizacao, listarUsuarioBean.getUsuario());

         } catch (Exception ex) {

         UltimaExcepion ultimaException = new UltimaExcepion();
         Throwable th = ultimaException.encontrarUltimaException(ex);

         if (th instanceof SQLException) {

         jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

         } else {

         jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!" + ex);
         }
         }
        
        jsfUtil.addMensagemInfo("Autorizações atualizadas com sucesso.");
        jsfUtil.listar();
        listarUsuarioBean.cancelar();

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

    public UsuarioAutorizacao getUsuarioAutorizacao() {
        return usuarioAutorizacao;
    }

    public void setUsuarioAutorizacao(UsuarioAutorizacao usuarioAutorizacao) {
        this.usuarioAutorizacao = usuarioAutorizacao;
    }

    public UsuarioAutorizacaoId getUsuarioAutorizacaoId() {
        return usuarioAutorizacaoId;
    }

    public void setUsuarioAutorizacaoId(UsuarioAutorizacaoId usuarioAutorizacaoId) {
        this.usuarioAutorizacaoId = usuarioAutorizacaoId;
    }

    public DualListModel<Autorizacao> getListaModelAutorizacoes() {
        return listaModelAutorizacoes;
    }

    public void setListaModelAutorizacoes(DualListModel<Autorizacao> listaModelAutorizacoes) {
        this.listaModelAutorizacoes = listaModelAutorizacoes;
    }

    public List<Autorizacao> getListaAutorizacoesUsuarioTem() {
        return listaAutorizacoesUsuarioTem;
    }

    public List<Autorizacao> getListaAutorizacoesUsuarioNaoTem() {
        return listaAutorizacoesUsuarioNaoTem;
    }

}
