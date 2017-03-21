/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.wp.modelo.Cardapio;
import br.com.wp.service.CardapioService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Wilson F Florindo
 */
@Path("/cardapio")
public class CardapioREST {

    @Inject
    private CardapioService cardapioService;
    private List<Cardapio> listaCardapio;

    @GET
    @Path("/buscarTodosGSON")
    @Produces("application/json")
    public String buscarTodosGSON() {

        try {

            listaCardapio = new ArrayList<>();

            listaCardapio = cardapioService.buscarCardapio();

            if (listaCardapio.isEmpty()) {

                return "Não há itens cadastrados";

            } else {

                return new Gson().toJson(listaCardapio);

            }

        } catch (Exception ex) {

            return "Erro no servidor";
            //Logger.getLogger(CardapioREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
