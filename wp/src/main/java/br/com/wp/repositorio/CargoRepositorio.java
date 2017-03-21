/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Cargo;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */
public class CargoRepositorio implements Serializable{
    
    @Inject
    private EntityManager em;

    public List<Cargo> buscarTodosCargos()throws Exception{
        return em.createQuery("Select c from Cargo c").getResultList();
        
    }

    public Cargo buscarCargoPorId(long id)throws Exception {
        
        return em.find(Cargo.class, id);
    }
    }


