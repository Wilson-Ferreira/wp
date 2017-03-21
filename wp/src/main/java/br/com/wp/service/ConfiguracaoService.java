/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.jpa.Transactional;
import br.com.wp.modelo.Configuracao;
import br.com.wp.repositorio.ConfiguracaoRepositorio;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */

public class ConfiguracaoService implements Serializable {

    @Inject
    private ConfiguracaoRepositorio configuracaoRepositorio;

    public Configuracao buscarConfiguracoes() throws Exception {

        return configuracaoRepositorio.buscarConfiguracoes();
    }

    @Transactional
    public void salvarAlterarConfiguracoes(Configuracao configuracao) throws Exception {

        configuracaoRepositorio.salvarAlterarConfiguracoes(configuracao);

    }

    @Transactional
    public void excluirConfiguracoes(Configuracao configuracao) throws Exception {

        configuracaoRepositorio.excluirConfiguracoes(configuracao);

    }

    public String buscarTipoDeCobranca()throws Exception {
      
        return configuracaoRepositorio.buscarTipoDeCobranca();
}
}