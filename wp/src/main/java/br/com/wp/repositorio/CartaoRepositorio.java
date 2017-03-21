/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.enumeracao.NumeroCartao;
import br.com.wp.enumeracao.StatusCartao;
import br.com.wp.modelo.Cartao;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */
public class CartaoRepositorio implements Serializable {
    
    @Inject
    private EntityManager em;
    
    public List<Cartao> buscarCartoesLiberados(StatusCartao statusLiberado) throws Exception {
        
        return em.createQuery("Select c From Cartao c where c.id <> all(Select cli.cartao.id From Cliente cli where cli.statusCartao= :statusLiberado)")
                .setParameter("statusLiberado", statusLiberado.toString()).getResultList();
        
    }
    
    public List<Cartao> buscarCartoesRetidos(StatusCartao statusRetido) throws Exception {
        
        String cartaoZero = NumeroCartao.ZERO.numero();
        return em.createQuery("Select c From Cartao c where c.numeroCartao<> :cartaoZero and c.id = any(Select cli.cartao.id From Cliente cli where cli.statusCartao= :statusRetido)")
                .setParameter("cartaoZero", cartaoZero)
                .setParameter("statusRetido", statusRetido.toString())
                .getResultList();
        
    }
    
    public List<Cartao> buscarCartoesLivres() throws Exception {
        
        String cartaoZero = NumeroCartao.ZERO.numero();
        return em.createQuery("Select c From Cartao c where c.numeroCartao<> :cartaoZero and c.id <> all(Select cli.cartao.id From Cliente cli)")
                .setParameter("cartaoZero", cartaoZero).getResultList();
        
    }

    public Cartao buscarCartaoNumeroZero(String cartaoZero) throws Exception {
        
        return (Cartao) em.createQuery("Select c From Cartao c where c.numeroCartao= :numeroCartao")
                .setParameter("numeroCartao", cartaoZero).getSingleResult();
    }
   
    public Cartao buscarCartaoPorId(long id) {
        
        return (Cartao) em.createQuery("Select c From Cartao c where c.id= :id")
                .setParameter("id", id).getSingleResult();
    }
    
    public List<Cartao> buscarTodosCartoes() {
        
        String cartaoZero = NumeroCartao.ZERO.numero();
        return em.createQuery("Select c From Cartao c where c.numeroCartao<> :cartaoZero")
                .setParameter("cartaoZero", cartaoZero).getResultList();
        
    }
    
}
