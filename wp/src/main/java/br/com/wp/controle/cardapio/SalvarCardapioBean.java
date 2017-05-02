package br.com.wp.controle.cardapio;

import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.service.CardapioService;
import br.com.wp.util.JsfUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */
@Named
@RequestScoped
public class SalvarCardapioBean implements Serializable {

    @Inject
    private ListarCardapioBean listarCardapioBean;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private CardapioService cardapioService;

    public void preparaSalvarItemCardapio() {

        listarCardapioBean.instanciarItemCardapio();

        jsfUtil.salvar();
    }

    public void salvarItemCardapio() {

        try {

            cardapioService.salvarAlterarItemCardapio(listarCardapioBean.getItemCardapio());

            listarCardapioBean.getListaItensCardapio().add(listarCardapioBean.getItemCardapio());

            jsfUtil.addMensagemInfo("Item salvo com sucesso");

            listarCardapioBean.instanciarItemCardapio();

            jsfUtil.listar();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
            }
        }
    }
}
