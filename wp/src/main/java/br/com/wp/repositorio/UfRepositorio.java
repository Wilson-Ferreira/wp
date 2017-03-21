/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Uf;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author User
 */
public class UfRepositorio implements Serializable{
    
    @Inject
    private EntityManager em;

    public Uf buscarUfPorId(long id)throws Exception {
       
        return em.find(Uf.class, id);
    }

    public List<Uf> buscarTodasUfs()throws Exception {
       return em.createQuery("Select u From Uf u").getResultList();
    }
}
