/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.wp.enumeracao.StatusCartao;
import br.com.wp.modelo.Cartao;
import br.com.wp.service.CartaoService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Wilson F Florindo
 */

@Path("/cartao")
public class CartaoREST {

    @Inject
    private CartaoService cartaoService;
    private List<Cartao> listaCartoes;

    @GET
    @Path("/buscarCartoes")
    @Produces("application/json")
    public String buscarCartoes() {

        try {

            listaCartoes = new ArrayList<>();
            listaCartoes = cartaoService.buscarCartoesRetidos(StatusCartao.RETIDO);

            if (listaCartoes.isEmpty()) {

                return "Não há cartões retidos";

            } else {

                return new Gson().toJson(listaCartoes);

            }
        } catch (Exception ex) {

            return "Não há cartões cadastrados!";

        }

    }
}
