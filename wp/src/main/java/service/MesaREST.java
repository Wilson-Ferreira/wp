/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.wp.modelo.Mesa;
import br.com.wp.service.MesaService;
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
@Path("/mesa")
public class MesaREST {

    @Inject
    private MesaService mesaService;
    private List<Mesa> listaMesas;

    @GET
    @Path("/buscarMesas")
    @Produces("application/json")
    public String buscarMesas() {

        try {

            listaMesas = new ArrayList<>();
            listaMesas = mesaService.buscarTodasMesas();
          
            return new Gson().toJson(listaMesas);
            
        } catch (Exception ex) {

            Logger.getLogger(MesaREST.class.getName()).log(Level.SEVERE, null, ex);
        }
       return "Não há mesas cadastradas!";
    }
}
