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
public class ExcluirCardapioBean implements Serializable {

    @Inject
    private ListarCardapioBean listarCardapioBean;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private CardapioService cardapioService;

    
    
    public void preparaExcluirItemCardapio() {

        if (listarCardapioBean.verificaSeHaMaisDeUmItemSelecionado()) {

            jsfUtil.abrirFecharDialog("PF('dialogConfirmaExclusao').show();");
        }
    }

    public void excluirItemCardapio() {

        try {

            cardapioService.excluirItemCardapio(listarCardapioBean.getItemCardapio());

            listarCardapioBean.getListaItensCardapio().remove(listarCardapioBean.getItemCardapio());

            listarCardapioBean.instanciarItemCardapio();
            
            jsfUtil.addMensagemInfo("Item excluido com sucesso");

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
