/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.modelo.Uf;
import br.com.wp.repositorio.UfRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author User
 */
public class UfService implements Serializable{
    
    @Inject
    private UfRepositorio ufRepositorio;

    public Uf buscarUfPorId(long id) throws Exception {
      return ufRepositorio.buscarUfPorId(id);
        
    }

    public List<Uf> buscarTodasUfs()throws Exception {
       return ufRepositorio.buscarTodasUfs();
        
    }
}
