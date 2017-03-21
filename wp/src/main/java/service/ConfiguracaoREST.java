/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.wp.modelo.Configuracao;
import br.com.wp.service.ConfiguracaoService;
import com.google.gson.Gson;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Wilson F Florindo
 */
@Path("/configuracao")
public class ConfiguracaoREST {

    @Inject
    private ConfiguracaoService configuracaoService;
    @Inject
    private Configuracao configuracao;

    @GET
    @Path("/buscarConfiguracao")
    @Produces("application/json")
    @Consumes("application/json")
    public String buscarConfiguracoes() {

        try {

            configuracao = configuracaoService.buscarConfiguracoes();
            String strConfiguracao = configuracao.getTipoCobranca();

            if (!strConfiguracao.isEmpty()) {

                return strConfiguracao;

            } else {

                return "";
            }

        } catch (Exception ex) {

        }
        
        return null;
    }
}
