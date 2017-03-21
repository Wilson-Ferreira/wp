/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.wp.modelo.Categoria;
import br.com.wp.service.CategoriaService;
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

@Path("/categoria")
public class CategoriaREST {

    @Inject
    private CategoriaService categoriaService;
    private  List<Categoria> listaCategoria;
   
    @GET
    @Path("/buscarCategorias")
    @Produces("application/json")
    public String buscarCategorias() {

         try {

             listaCategoria = new ArrayList<>();
       
            listaCategoria = categoriaService.buscarCategorias();

         return new Gson().toJson(listaCategoria);

         } catch (Exception ex) {

         Logger.getLogger(CategoriaREST.class.getName()).log(Level.SEVERE, null, ex);
         
         }
        return "Não há categorias cadastrados";
       
    }
}
