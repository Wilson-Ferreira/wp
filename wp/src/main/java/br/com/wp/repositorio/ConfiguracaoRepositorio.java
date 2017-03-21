/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.repositorio;

import br.com.wp.modelo.Configuracao;
import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */

public class ConfiguracaoRepositorio implements Serializable {

    @Inject
    private EntityManager em;

    public Configuracao buscarConfiguracoes() throws Exception {

        Long contador = (Long) em.createQuery("Select count(c) From Configuracao c").getSingleResult();

        if (contador > 0) {
            return (Configuracao) em.createQuery("Select c From Configuracao c").getSingleResult();
        } else {
            return null;
        }
    }

    public void salvarAlterarConfiguracoes(Configuracao configuracao) {

        if (configuracao.getId() == null) {

            em.persist(configuracao);

        } else {

            em.merge(configuracao);
        }
    }

    public void excluirConfiguracoes(Configuracao configuracao) {

        Configuracao conf = em.find(Configuracao.class, configuracao.getId());

        em.remove(conf);
    }

    public void buscarDataETempoDeContrato() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String buscarTipoDeCobranca()throws Exception {
        
        String tipoDeCobranca = (String) em.createQuery("Select c.tipoCobranca From Configuracao c ").getSingleResult();
        return tipoDeCobranca;
}
}