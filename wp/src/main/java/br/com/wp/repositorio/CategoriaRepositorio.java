package br.com.wp.repositorio;

import br.com.wp.enumeracao.NomeCategoria;
import br.com.wp.modelo.Categoria;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Wilson F Florindo
 */

public class CategoriaRepositorio implements Serializable {

    @Inject
    private EntityManager em;


    public Categoria buscarCategoriaPorId(long id) throws Exception {

        return em.find(Categoria.class, id);
    }

    public List<Categoria> buscarCategorias() {

        return em.createQuery("Select c From Categoria c").getResultList();
    }

    public List<Categoria> buscarCategorias(NomeCategoria nomeCategoria)throws Exception {
             
        return em.createQuery("Select c From Categoria c where c.categoria<> :nomeCategoria")
                .setParameter("nomeCategoria", nomeCategoria.toString()).getResultList();

    }
}
