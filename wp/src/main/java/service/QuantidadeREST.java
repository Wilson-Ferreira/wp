/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.wp.modelo.Quantidade;
import br.com.wp.service.QuantidadeService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Wilson F Florindo
 */
@Path("/quantidade")
public class QuantidadeREST {

    @Inject
    private QuantidadeService quantidadeService;
    private List<Quantidade> listaQuantidades;

    @GET
    @Path("/buscarQuantidades")
    @Produces("application/json")
    @Consumes("application/json")
    public String buscarQuantidades() {

        try {

            System.out.println("executou buscarQuantidades");
            listaQuantidades = new ArrayList<>();
             
            listaQuantidades = quantidadeService.buscarQuantidade();
            return new Gson().toJson(listaQuantidades);

        } catch (Exception ex) {

            Logger.getLogger(QuantidadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
         return "Não há qtde cadastradas!";
       
    } 
}
