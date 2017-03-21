/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.modelo.Mesa;
import br.com.wp.repositorio.MesaRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
public class MesaService implements Serializable{

   
    @Inject
    private MesaRepositorio mesaRepositorio;

    public Mesa buscarMesaPorId(long id)throws Exception {
       
        return mesaRepositorio.buscarMesaPorId(id);
    }

    public List<Mesa> buscarTodasMesas()throws Exception {
       
        return mesaRepositorio.buscarTodasMesas();
        
    }
}
