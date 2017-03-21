package br.com.wp.controle;

import br.com.wp.enumeracao.NomeCategoria;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Categoria;
import br.com.wp.modelo.Cardapio;
import br.com.wp.service.CardapioService;
import br.com.wp.service.CategoriaService;
import br.com.wp.util.JsfUtil;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Wilson F Florindo
 */
@Named(value = "cardapioBean")
@ConversationScoped
public class CardapioBean implements Serializable {

    @Inject
    private Conversation conversation;
    @Inject
    private JsfUtil jsfUtil;
    @Inject
    private Cardapio itemCardapio;
    @Inject
    private Categoria categoria;
    @Inject
    private CardapioService cardapioService;
    @Inject
    private CategoriaService categoriaService;

    private List<Cardapio> listaItensCardapio;
    private List<Categoria> listaCategorias;
    private List<Cardapio> listaItensSelecionados;

    
      public void onRowSelect(SelectEvent event) {
      
    }
    
    public void onRowUnselect(UnselectEvent event) {
      
    }
 
    public void iniciarConversation() {
        
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    public void encerrarConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    public String iniciarPedido() {

            return "novo_pedido_funcionario?faces-redirect=true;";
     
    }

    public void instanciarItem() {

        itemCardapio = new Cardapio();
        listaItensSelecionados = null;
    }

    public void cancelar() {
        instanciarItem();
        jsfUtil.listar();
    }

    public void buscarCardapioECategorias() {

        try {

            listaItensCardapio = cardapioService.buscarCardapio();
            listaCategorias = categoriaService.buscarCategorias(NomeCategoria.TODAS);

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

    public boolean verificaSeHaMaisDeUmItemSelecionado() {

        if (listaItensSelecionados == null || listaItensSelecionados.size() > 1) {
            jsfUtil.addMensagemInfo("Selecione um item");
            return false;
        }
        listaItensSelecionados.stream().forEach((item) -> {
            itemCardapio = item;
        });
        return true;
    }

    public void preparaSalvarItemCardapio() {

        instanciarItem();
        jsfUtil.salvar();
    }

    public void salvarAlterarItemCardapio() {

        try {

            cardapioService.salvarAlterarItemCardapio(itemCardapio);

            if (!listaItensCardapio.contains(itemCardapio)) {
                listaItensCardapio.add(itemCardapio);
                jsfUtil.addMensagemInfo("Item salvo com sucesso");

            } else {
                jsfUtil.addMensagemInfo("Item alterado com sucesso");
            }

            instanciarItem();
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

    public void preparaAlterarItemCardapio() {

        if (verificaSeHaMaisDeUmItemSelecionado()) {
            jsfUtil.alterar();
        }
    }

    public void preparaExcluirItemCardapio() {

        if (verificaSeHaMaisDeUmItemSelecionado()) {
            jsfUtil.abrirFecharDialog("PF('dialogConfirmaExclusao').show();");
        }
    }

    public void excluirItemCardapio() {

        try {

            cardapioService.excluirItemCardapio(itemCardapio);
            listaItensCardapio.remove(itemCardapio);
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

    public Cardapio getItemCardapio() {
        return itemCardapio;
    }

    public void setItemCardapio(Cardapio itemCardapio) {
        this.itemCardapio = itemCardapio;
    }

    public List<Cardapio> getListaItensCardapio() {
        return listaItensCardapio;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Cardapio> getListaItensSelecionados() {
        return listaItensSelecionados;
    }

    public void setListaItensSelecionados(List<Cardapio> listaItensSelecionados) {
        this.listaItensSelecionados = listaItensSelecionados;
    }

}
