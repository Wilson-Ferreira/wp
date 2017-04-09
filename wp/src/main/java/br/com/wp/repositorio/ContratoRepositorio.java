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

/**
 *
 * @author Wilson F Florindo
 */
public class ContratoRepositorio implements Serializable {

    @Inject
    private EntityManager em;

    public Contrato buscarDadosContrato() throws Exception {

        return (Contrato) em.createQuery("Select c from Contrato c").getSingleResult();
    }

    public void validarSistema(Contrato contratoNovo) throws Exception {

        if (contratoNovo.getId() != null) {

            em.merge(contratoNovo);

        } else {

            em.persist(contratoNovo);
        }
    }
}
