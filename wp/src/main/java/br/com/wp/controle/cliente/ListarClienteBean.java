/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.cliente;

import br.com.wp.enumeracao.TipoCobranca;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Cartao;
import br.com.wp.modelo.Cliente;
import br.com.wp.service.CartaoService;
import br.com.wp.service.ClienteService;
import br.com.wp.service.ConfiguracaoService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@ViewScoped
public class ListarClienteBean implements Serializable {

    @Inject
    private ClienteService clienteService;
    @Inject
    private ConfiguracaoService configuracaoService;
    @Inject
    private CartaoService cartaoService;
    @Inject
    private Cliente cliente;
    @Inject
    private JsfUtil jsfUtil;
    private List<Cliente> listaClientes;
    private List<Cartao> listaCartoesLiberados;
    private List<Cliente> listaClientesSelecionados;
    private String tipoDeCobranca;

    private LazyDataModel<Cliente> modelClientes;

    public ListarClienteBean() {

        modelClientes = new LazyDataModel<Cliente>() {
            private static final long serialVersionUID = 1L;

            @Override
            public List<Cliente> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filtros) {

                try {

                    listaClientes = clienteService.buscarTodosClientes(first, pageSize, sortField, sortOrder, filtros);

                    setRowCount(clienteService.buscarQtdeRegistros(sortField, sortOrder, filtros));

                } catch (Exception ex) {

                    UltimaExcepion ultimaException = new UltimaExcepion();
                    Throwable th = ultimaException.encontrarUltimaException(ex);

                    if (th instanceof SQLException) {

                        jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

                    } else {

                        jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!");
                    }
                }

                return listaClientes;
            }

            @Override
            public Object getRowKey(Cliente cli) {
                return cli.getId();
            }

            @Override
            public Cliente getRowData(String rowKey) {
                Long id = Long.valueOf(rowKey);
                for (Cliente clienteSelecionado : modelClientes) {
                    if (id.equals(clienteSelecionado.getId())) {
                        return clienteSelecionado;
                    }
                }
                return null;
            }

        };

    }

    public void buscarTipoDeCobranca() {

        try {

            tipoDeCobranca = configuracaoService.buscarTipoDeCobranca();

            if (getTipoDeCobranca().equalsIgnoreCase(TipoCobranca.CARTÃO.toString())) {

                jsfUtil.setCampoCartao(true);

            }

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

    public void buscarCartoesLivres() {

        try {

            listaCartoesLiberados = cartaoService.buscarCartoesLivres();

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

    public void instanciarCliente() {

        cliente = new Cliente();
        listaClientesSelecionados = null;
    }

    public void cancelar() {

        instanciarCliente();
        jsfUtil.listar();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public List<Cartao> getListaCartoesLiberados() {
        return listaCartoesLiberados;
    }

    public List<Cliente> getListaClientesSelecionados() {
        return listaClientesSelecionados;
    }

    public void setListaClientesSelecionados(List<Cliente> listaClientesSelecionados) {
        this.listaClientesSelecionados = listaClientesSelecionados;
    }

    public LazyDataModel<Cliente> getModelClientes() {
        return modelClientes;
    }

    public void setModelClientes(LazyDataModel<Cliente> modelClientes) {
        this.modelClientes = modelClientes;
    }

    public String getTipoDeCobranca() {
        return tipoDeCobranca;
    }
    

}
