/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Autorizacao;
import br.com.wp.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */
public class AutorizacaoRepositorio implements Serializable{
    
    @Inject
    private EntityManager em;
  
     public List<Autorizacao> buscarAutorizacoesUsuarioNaoTem(Usuario usuario)throws Exception {
        System.out.println("usuario repo tem " + usuario.getId());
        return em.createQuery("Select a From Autorizacao a where a.id <> all(Select ua.id.autorizacao.id from UsuarioAutorizacao ua where ua.id.usuario.id= :usuario)")
                .setParameter("usuario", usuario.getId()).getResultList();
    }
     
     
    public List<Autorizacao> buscarAutorizacoesUsuarioTem(Usuario usuario) {
        
         System.out.println("usuario repo nao tem " + usuario.getId());
        return em.createQuery("Select a From Autorizacao a where a.id = any(Select ua.id.autorizacao.id from UsuarioAutorizacao ua where ua.id.usuario.id= :usuario)")
                .setParameter("usuario", usuario.getId()).getResultList();
        
    }
 
    public Autorizacao buscarAutorizacaoPorId(long id)throws Exception {
       
        return em.find(Autorizacao.class, id);
        
    }
}


