/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.jpa.Transactional;
import br.com.wp.modelo.Cartao;
import br.com.wp.modelo.Cliente;
import br.com.wp.repositorio.ClienteRepositorio;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Wilson F Florindo
 */

public class ClienteService implements Serializable {

    @Inject
    private ClienteRepositorio clienteRepositorio;

    @Transactional
    public void salvarAlterarCliente(Cliente cliente) throws Exception {

        clienteRepositorio.salvarAlterarCliente(cliente);
    }

    @Transactional
    public void excluirCliente(Cliente cliente) throws Exception {

        clienteRepositorio.excluirCliente(cliente);
    }
  
    @Transactional
    public void zerarCartoes(Cartao cartaoZero) throws Exception {

        clienteRepositorio.zerarCartoes(cartaoZero);
    }

    public Cliente buscarClientePorCartao(Cartao cartao)throws Exception {
       
        return clienteRepositorio.buscarClientePorCartao(cartao);
    }
    
     public List<Cliente> buscarTodosClientes(int first, int pageSize,String sortField, SortOrder sortOrder, Map<String, Object> filtros) throws Exception {

        return clienteRepositorio.buscarTodosClientes(first, pageSize, sortField, sortOrder,filtros );
    }
     
    public int buscarQtdeRegistros(String sortField, SortOrder sortOrder, Map<String, Object> filtros)throws Exception{
        
        return clienteRepositorio.buscarQtdeRegistros(sortField, sortOrder,filtros );
        
    }
}
