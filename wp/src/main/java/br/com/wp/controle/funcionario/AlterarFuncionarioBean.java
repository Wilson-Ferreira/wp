/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.funcionario;

import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.service.FuncionarioService;
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
public class AlterarFuncionarioBean implements Serializable {

    @Inject
    private ListarFuncionarioBean listarFuncionarioBean;
    @Inject
    private FuncionarioService funcionarioService;
    @Inject
    private JsfUtil jsfUtil;

    public void preparaAlterarFuncionario() {

        listarFuncionarioBean.buscarTodasUfs();

        listarFuncionarioBean.buscarTodosCargos();
        
        listarFuncionarioBean.buscarTodasCidades();

        jsfUtil.alterar();
    }

    public void alterarFuncionario() {

        try {

            funcionarioService.salvarAlterarFuncionario(listarFuncionarioBean.getFuncionario());

            jsfUtil.addMensagemInfo("Funcionário alterado com sucesso");

            listarFuncionarioBean.instanciarFuncionario();

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
