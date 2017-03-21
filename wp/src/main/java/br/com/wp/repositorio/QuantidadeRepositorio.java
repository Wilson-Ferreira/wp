/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.enumeracao.QuantidadeUm;
import br.com.wp.modelo.Quantidade;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */
public class QuantidadeRepositorio implements Serializable{
    
    @Inject
    private EntityManager em;

    public Quantidade buscarQuantidadePorId(long id) {
      
        return em.find(Quantidade.class, id);
    }

    
     public List<Quantidade> buscarQuantidade()throws Exception {
       
        return em.createQuery("Select q From Quantidade q").getResultList();
        
    }
/*     
    public List<Quantidade> buscarQuantidadePorSecao(String secao)throws Exception {
       
        return em.createQuery("Select q From Quantidade q ").getResultList();
        
    }
*/
    public Quantidade buscarQuantidadeUm(QuantidadeUm qtde)throws Exception {
      System.out.println("qtde "+qtde.qtde());
     
        return (Quantidade) em.createQuery("Select q From Quantidade q where q.quantidade= :qtde")
                .setParameter("qtde", qtde.qtde()).getSingleResult();
        
    }
}
