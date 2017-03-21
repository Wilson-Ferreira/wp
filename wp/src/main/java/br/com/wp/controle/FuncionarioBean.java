/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle;

import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Cargo;
import br.com.wp.modelo.Cidade;
import br.com.wp.modelo.Funcionario;
import br.com.wp.modelo.Uf;
import br.com.wp.service.CargoService;
import br.com.wp.service.CidadeService;
import br.com.wp.service.FuncionarioService;
import br.com.wp.service.UfService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@ViewScoped
public class FuncionarioBean implements Serializable {

    @Inject
    private FuncionarioService funcionarioService;
    @Inject
    private CidadeService cidadeService;
    @Inject
    private CargoService cargoService;
    @Inject
    private UfService ufService;
    @Inject
    private Funcionario funcionario;
    @Inject

    private JsfUtil jsfUtil;
    private List<Cidade> listaCidades;
    private List<Uf> listaUfs;
    private List<Cargo> listaCargos;
    private List<Funcionario> listaFuncionarios;

    public void instanciarFuncionario() {

        funcionario = new Funcionario();
    }

    public void cancelar() {

        instanciarFuncionario();
        jsfUtil.listar();
    }

    public void buscarTodasUfs() {

        try {

            listaUfs = ufService.buscarTodasUfs();

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

    public void buscarTodosCargos() {

        try {

            listaCargos = cargoService.buscarTodosCargos();

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

    public void buscarTodosFuncionarios() {

        try {

            listaFuncionarios = funcionarioService.buscarTodosFuncionarios();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {
                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador! ");
            }
        }
    }

    public void buscarCidadesPorUf(AjaxBehaviorEvent event) {

        try {

            listaCidades = cidadeService.buscarCidadesPorUf(funcionario.getUf());

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

    public void preparaSalvarFuncionario() {

        instanciarFuncionario();
        jsfUtil.salvar();
    }

    public void preparaAlterarFuncionario() {

            try {
                
                listaCidades = cidadeService.buscarTodasCidades();
                jsfUtil.alterar();
                
            } catch (Exception ex) {

                UltimaExcepion ultimaException = new UltimaExcepion();
                Throwable th = ultimaException.encontrarUltimaException(ex);

                if (th instanceof SQLException) {

                    jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

                } else {
                    jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador! ");
                }
            }
        }
 
    public void salvarAlterarFuncionario() {

        try {

            if (funcionario.getId() == null) {

                java.util.Date date = new Date();
                funcionario.setDataCadastro(date);

            }

            funcionarioService.salvarAlterarFuncionario(funcionario);

            if (!listaFuncionarios.contains(funcionario)) {
                listaFuncionarios.add(funcionario);
                jsfUtil.addMensagemInfo("Funcionário salvo com sucesso");

            } else {

                jsfUtil.addMensagemInfo("Funcionário alterado com sucesso");
            }

            instanciarFuncionario();
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

    public void preparaExcluirFuncionario() {

            jsfUtil.abrirFecharDialog("PF('dialogConfirmaExclusao').show();");
    }

    public void excluirFuncionario() {

        try {

            funcionarioService.excluirFuncionario(funcionario);
            listaFuncionarios.remove(funcionario);
            jsfUtil.addMensagemInfo("Funcionário excluido com sucesso");
            instanciarFuncionario();

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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public List<Cidade> getListaCidades() {
        return listaCidades;
    }

    public List<Uf> getListaUfs() {
        return listaUfs;
    }

    public List<Cargo> getListaCargos() {
        return listaCargos;
    }
}
