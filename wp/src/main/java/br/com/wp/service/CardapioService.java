package br.com.wp.service;

import br.com.wp.jpa.Transactional;
import br.com.wp.modelo.Cardapio;
import br.com.wp.repositorio.CardapioRepositorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
public class CardapioService implements Serializable {

    @Inject
    private CardapioRepositorio cardapioRepositorio;

    public List<Cardapio> buscarCardapio() throws Exception {

        return cardapioRepositorio.buscarCardapio();
    }

    @Transactional
    public void salvarAlterarItemCardapio(Cardapio itemCardapio) throws Exception {

        cardapioRepositorio.salvarAlterarItemCardapio(itemCardapio);
    }

    @Transactional
    public void excluirItemCardapio(Cardapio itemCardapio) throws Exception {

        cardapioRepositorio.excluirItemCardapio(itemCardapio);
    }

    public Cardapio buscarItemCardapioPorId(long id)throws Exception {
      
        return cardapioRepositorio.buscarItemCardapioPorId(id);
    }

    public List<Cardapio> buscarItemCardapioPorCategoria(Long id)throws Exception {
      
        return cardapioRepositorio.buscarItemCardapioPorCategoria(id);
    }
 
}
