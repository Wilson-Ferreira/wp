/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.enumeracao.QuantidadeUm;
import br.com.wp.modelo.Quantidade;
import br.com.wp.repositorio.QuantidadeRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */

public class QuantidadeService implements Serializable{
    
    @Inject
    private QuantidadeRepositorio quantidadeRepositorio;

    public Quantidade buscarQuantidadePorId(long id) throws Exception{
      
        return quantidadeRepositorio.buscarQuantidadePorId(id);
    }

    public List<Quantidade> buscarQuantidade()throws Exception {
        
        return quantidadeRepositorio.buscarQuantidade();
        
    }

    public Quantidade buscarQuantidadeUm(QuantidadeUm qtde)throws Exception {
       
        return quantidadeRepositorio.buscarQuantidadeUm(qtde);
        
    }
}

