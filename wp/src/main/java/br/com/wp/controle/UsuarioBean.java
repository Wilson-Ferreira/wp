/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle;

import br.com.wp.autenticacao.CriptografarSenha;
import br.com.wp.enumeracao.StatusLogin;
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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Wilson F Florindo
 */
@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

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

    public void cancelar() {

        instanciarUsuario();
        jsfUtil.listar();
    }

    public void instanciarUsuario() {

        usuario = new Usuario();
        usuarioAutorizacaoId = new UsuarioAutorizacaoId();
        usuarioAutorizacao = new UsuarioAutorizacao();
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

    public void preparaAlterarSenhaUsuario() {

        jsfUtil.alterar();
    }

    public void preparaSalvarUsuario() {

        instanciarUsuario();
        buscarFuncionariosNaoSaoUsuarios();
        jsfUtil.salvar();
    }

    public void alterarSenhaUsuario() {

        try {
            usuario.setSenha(criptografarSenha.criptografarSenha(usuario.getSenha()));

            usuarioService.salvarAlterarUsuario(usuario);
            
            jsfUtil.addMensagemInfo("Senha alterada com sucesso");
            instanciarUsuario();
            jsfUtil.listar();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!" + ex);
            }
        }
    }

    public void salvarUsuario() {

        try {

            usuario.setSenha(criptografarSenha.criptografarSenha(usuario.getSenha()));

            usuario.setStatusLogin(StatusLogin.ATIVO);
            
            usuarioService.salvarAlterarUsuario(usuario);

            if (!listaUsuarios.contains(usuario)) {

                listaUsuarios.add(usuario);
                jsfUtil.addMensagemInfo("Usuário salvo com sucesso");
            } 

            instanciarUsuario();
            jsfUtil.listar();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!" + ex);
            }
        }
    }

    public void preparaExcluirUsuario() {

        jsfUtil.abrirFecharDialog("PF('dialogConfirmaExclusao').show();");
    }

    public void excluirUsuario() {

        try {

            usuarioService.excluirUsuario(usuario);
            listaUsuarios.remove(usuario);
            jsfUtil.addMensagemInfo("Usuário excluido com sucesso");
            instanciarUsuario();

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

    public void habilitarDesabilitarLoginUsuario() {

        try {

            if (usuario.getStatusLogin().toString().equalsIgnoreCase(StatusLogin.ATIVO.toString())) {

                usuario.setStatusLogin(StatusLogin.INATIVO);

            } else {

                usuario.setStatusLogin(StatusLogin.ATIVO);
            }

            usuarioService.salvarAlterarUsuario(usuario);

            instanciarUsuario();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!" + ex.getMessage());
            }
        }
    }

    public void preparaEditarAutorizacoes() {

        buscarAutorizacoesDoSistemaEDoUsuario();
        jsfUtil.preparaEditarAutorizacoes();
    }

    public void adicionarAutorizacoes() {

        listaUsuarioAutorizacao = new ArrayList<>();

        try {

            for (Autorizacao autorizacao : listaModelAutorizacoes.getTarget()) {

                usuarioAutorizacaoId = new UsuarioAutorizacaoId();
                usuarioAutorizacao = new UsuarioAutorizacao();

                usuarioAutorizacaoId.setAutorizacao(autorizacao);
                usuarioAutorizacaoId.setUsuario(usuario);
                usuarioAutorizacao.setId(usuarioAutorizacaoId);

                listaUsuarioAutorizacao.add(usuarioAutorizacao);
            }

            usuarioAutorizacaoService.salvarAlterarUsuarioAutorizacao(listaUsuarioAutorizacao, usuario);

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
        cancelar();
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
    /*
     public Autorizacao getAutorizacao() {
     return autorizacao;
     }

     public void setAutorizacao(Autorizacao autorizacao) {
     this.autorizacao = autorizacao;
     }
     */

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
