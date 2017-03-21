/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Mesa;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */

public class MesaRepositorio implements Serializable{
    
    @Inject
    private EntityManager em;

    public Mesa buscarMesaPorId(long id)throws Exception {
       
        return em.find(Mesa.class, id);
        
    }

    public List<Mesa> buscarTodasMesas()throws Exception {
       
        return em.createQuery("Select m From Mesa m").getResultList();
        
    }
}
