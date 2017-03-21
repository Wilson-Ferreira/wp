/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.modelo.Cargo;
import br.com.wp.repositorio.CargoRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
public class CargoService implements Serializable{
    
    @Inject
    private CargoRepositorio cargoRepositorio;

    public List<Cargo> buscarTodosCargos()throws Exception {
      return cargoRepositorio.buscarTodosCargos();
    }

    public Cargo buscarCargoPorId(long id)throws Exception {
       return cargoRepositorio.buscarCargoPorId(id);
    }
}

