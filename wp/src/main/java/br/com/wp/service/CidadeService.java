/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.modelo.Cidade;
import br.com.wp.modelo.Uf;
import br.com.wp.repositorio.CidadeRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
public class CidadeService implements Serializable{
    
    @Inject
    private CidadeRepositorio cidadeRepositorio;

    public List<Cidade> buscarCidadesPorUf(Uf uf)throws Exception {
     
        return cidadeRepositorio.buscarCidadesPorUf(uf);
        
    }

    public Cidade buscarCidadePorId(long id)throws Exception {
       return cidadeRepositorio.buscarCidadePorId(id);
        
    }

    public List<Cidade> buscarTodasCidades()throws Exception {
       
        return cidadeRepositorio.buscarTodasCidades();
    }
}
