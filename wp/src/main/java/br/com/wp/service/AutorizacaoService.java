/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.modelo.Autorizacao;
import br.com.wp.modelo.Usuario;
import br.com.wp.repositorio.AutorizacaoRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
public class AutorizacaoService implements Serializable{

   
    @Inject
    private AutorizacaoRepositorio autorizacaoRepositorio;
  

    public List<Autorizacao> buscarAutorizacoesUsuarioNaoTem(Usuario usuario)throws Exception {
        return autorizacaoRepositorio.buscarAutorizacoesUsuarioNaoTem(usuario);   
    }
    
    
    public List<Autorizacao> buscarAutorizacoesUsuarioTem(Usuario usuario)throws Exception {
       
        return autorizacaoRepositorio.buscarAutorizacoesUsuarioTem(usuario);
    }

     public Autorizacao buscarAutorizacaoPorId(long id)throws Exception {
      return autorizacaoRepositorio.buscarAutorizacaoPorId(id);
     } 
}
