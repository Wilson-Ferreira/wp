/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.funcionario;

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
public class ListarFuncionarioBean implements Serializable {

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

        setFuncionario(new Funcionario());
    }

    public void cancelar() {

        instanciarFuncionario();

        jsfUtil.listar();
    }

    public void buscarTodasCidades() {

        try {

            listaCidades = cidadeService.buscarTodasCidades();

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

            setListaFuncionarios(funcionarioService.buscarTodosFuncionarios());

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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
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
