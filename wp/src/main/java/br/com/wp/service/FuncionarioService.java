/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.jpa.Transactional;
import br.com.wp.modelo.Funcionario;
import br.com.wp.repositorio.FuncionarioRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
public class FuncionarioService implements Serializable {

    @Inject
    private FuncionarioRepositorio funcionarioRepositorio;

    public List<Funcionario> buscarTodosFuncionarios() throws Exception {

        return funcionarioRepositorio.buscarTodosFuncionarios();
    }

    public Funcionario buscarFuncionarioPorId(long id) throws Exception {

        return funcionarioRepositorio.buscarFuncionarioPorId(id);
    }

    @Transactional
    public void salvarAlterarFuncionario(Funcionario funcionario) throws Exception {
        funcionarioRepositorio.salvarAlterarFuncionario(funcionario);

    }

    @Transactional
    public void excluirFuncionario(Funcionario funcionario) throws Exception {
        funcionarioRepositorio.excluirFuncionario(funcionario);

    }

    public List<Funcionario> buscarFuncionariosNaoSaoUsuarios()throws Exception {
        return funcionarioRepositorio.buscarFuncionariosNaoSaoUsuarios();
    }

    public List<Funcionario> buscarFuncionarioPorCargo(String cargo_garcon) throws Exception{
      return funcionarioRepositorio.buscarFuncionarioPorCargo(cargo_garcon);
        
        
    }
}
