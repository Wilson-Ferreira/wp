/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Contrato;
import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wilson F Florindo
 */
public class ContratoRepositorio implements Serializable {

    @Inject
    private EntityManager em;

    public Contrato buscarDadosContrato() throws Exception {

        Long cont = (Long) em.createQuery("Select count(c) from Contrato c").getSingleResult();
       
        if (cont > 0) {
            return (Contrato) em.createQuery("Select c from Contrato c").getSingleResult();
        } else {
            return null;
        }
    }

    public void validarSistema(String chave) throws Exception {

        String query = "Update Contrato c set c.chave= :chave";
        Query sQuery = em.createQuery(query);

        sQuery.setParameter("chave", chave);

        sQuery.executeUpdate();
    }
}
