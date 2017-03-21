
package br.com.wp.service;

import br.com.wp.enumeracao.NomeCategoria;
import br.com.wp.modelo.Categoria;
import br.com.wp.repositorio.CategoriaRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */

public class CategoriaService implements Serializable{
    
    @Inject
    private CategoriaRepositorio categoriaRepositorio;

    public Categoria buscarCategoriaPorId(long id)throws Exception {
       return categoriaRepositorio.buscarCategoriaPorId(id);
        
    }

    public List<Categoria> buscarCategorias()throws Exception {
       return categoriaRepositorio.buscarCategorias();
        
    }

    public List<Categoria> buscarCategorias(NomeCategoria nomeCategoria)throws Exception {
       
        return categoriaRepositorio.buscarCategorias(nomeCategoria);
    }
}
