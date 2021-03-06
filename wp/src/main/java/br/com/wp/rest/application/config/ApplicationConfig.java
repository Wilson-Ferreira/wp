/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.rest.application.config;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author User
 */
@ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

  
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.CardapioREST.class);
        resources.add(service.CartaoREST.class);
        resources.add(service.CategoriaREST.class);
        resources.add(service.ConfiguracaoREST.class);
        resources.add(service.MesaREST.class);
        resources.add(service.PedidoREST.class);
        resources.add(service.QuantidadeREST.class);
        resources.add(service.UsuarioREST.class);
    }   
}
