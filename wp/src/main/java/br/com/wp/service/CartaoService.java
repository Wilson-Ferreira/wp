/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.enumeracao.StatusCartao;
import br.com.wp.modelo.Cartao;
import br.com.wp.repositorio.CartaoRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */

public class CartaoService implements Serializable{

   
    @Inject
    private CartaoRepositorio cartaoRepositorio;

   

    public List<Cartao> buscarCartoesLiberados(StatusCartao statusLiberado)throws Exception {
       
        return cartaoRepositorio.buscarCartoesLiberados(statusLiberado);
        
    }
  
     
      public List<Cartao> buscarCartoesRetidos(StatusCartao statusRetido)throws Exception {
       
        return cartaoRepositorio.buscarCartoesRetidos(statusRetido);
        
    }
 
    public Cartao buscarCartaoNumeroZero(String cartaoZero)throws Exception {
       
        return cartaoRepositorio.buscarCartaoNumeroZero(cartaoZero);
    }

    public Cartao buscarCartaoPorId(long id) throws Exception {
       return cartaoRepositorio.buscarCartaoPorId(id);
        
    }

    public List<Cartao> buscarCartoesLivres() throws Exception {
       
        return cartaoRepositorio.buscarCartoesLivres();
    }
}
