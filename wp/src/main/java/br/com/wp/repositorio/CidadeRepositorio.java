/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Cidade;
import br.com.wp.modelo.Uf;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */
public class CidadeRepositorio implements Serializable{
    
    @Inject
    private EntityManager em;

    public List<Cidade> buscarCidadesPorUf(Uf uf)throws Exception {
      return em.createQuery("Select c From Cidade c where c.uf= :uf")
              .setParameter("uf", uf).getResultList();
    }

    public Cidade buscarCidadePorId(long id)throws Exception {
      
        return em.find(Cidade.class, id);
    }

    public List<Cidade> buscarTodasCidades()throws Exception {
       return em.createQuery("Select c From Cidade c").getResultList();
        
    }

}
