/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.cliente.controle;

import br.com.wp.enumeracao.StatusCartao;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Cliente;
import br.com.wp.service.ClienteService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@ViewScoped
public class AlterarStatusCartaoClienteBean implements Serializable {

    @Inject
    private ListarClienteBean clienteBean;
    @Inject
    private ClienteService clienteService;
    @Inject
    private Cliente cliente;
    @Inject
    private JsfUtil jsfUtil;

    public void alterarStatusCartao() {

        try {

            cliente = clienteBean.getCliente();

            if (cliente.getStatusCartao().equalsIgnoreCase(StatusCartao.LIBERADO.toString())) {

                cliente.setStatusCartao(StatusCartao.RETIDO.toString());

            } else {

                cliente.setStatusCartao(StatusCartao.LIBERADO.toString());
            }

            clienteService.salvarAlterarCliente(cliente);
            clienteBean.instanciarCliente();

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
