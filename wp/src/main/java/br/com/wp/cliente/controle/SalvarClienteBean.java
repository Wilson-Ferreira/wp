/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.cliente.controle;

import br.com.wp.enumeracao.NumeroCartao;
import br.com.wp.enumeracao.StatusCartao;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Wilson F Florindo
 */
@Named
@ViewScoped
public class SalvarClienteBean implements Serializable {

    @Inject
    private ListarClienteBean clienteBean;
    @Inject
    private ClienteService clienteService;
    @Inject
    private CartaoService cartaoService;
    @Inject
    private Cliente cliente;
    @Inject
    private JsfUtil jsfUtil;
    private List<Cliente> listaClientes;
    private List<Cartao> listaCartoesLiberados;

    public void preparaSalvarCliente() {

        listaCartoesLiberados = clienteBean.buscarCartoesLivres();
        clienteBean.getListaCartoesLiberados().addAll(listaCartoesLiberados);
        cliente = new Cliente();
        clienteBean.setCliente(cliente);
        jsfUtil.salvar();

    }

    public void salvarCliente() {

        try {

            if (clienteBean.getTipoDeCobranca().equalsIgnoreCase(TipoCobranca.MESA.toString())) {

                cliente.setStatusCartao(StatusCartao.LIBERADO.toString());

                cliente.setCartao(cartaoService.buscarCartaoNumeroZero(NumeroCartao.ZERO.numero()));
            
            } else {

                cliente.setStatusCartao(StatusCartao.RETIDO.toString());
            }

            clienteService.salvarAlterarCliente(cliente);

            jsfUtil.addMensagemInfo("Cliente salvo com sucesso");

            clienteBean.instanciarCliente();
            
            jsfUtil.listar();

        } catch (Exception ex) {

            UltimaExcepion ultimaException = new UltimaExcepion();
            Throwable th = ultimaException.encontrarUltimaException(ex);

            if (th instanceof SQLException) {

                jsfUtil.addMensagemErro(new DataBaseException((SQLException) th).getMessage());

            } else {

                jsfUtil.addMensagemErro("Ocorreu um erro no banco de dados, informe o administrador!" + ex.getMessage());
            }

        }
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

}
