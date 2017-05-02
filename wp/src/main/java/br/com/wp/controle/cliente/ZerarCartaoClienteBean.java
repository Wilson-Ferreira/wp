/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.controle.cliente;

import br.com.wp.enumeracao.NumeroCartao;
import br.com.wp.exception.DataBaseException;
import br.com.wp.exception.UltimaExcepion;
import br.com.wp.modelo.Cartao;
import br.com.wp.service.CartaoService;
import br.com.wp.service.ClienteService;
import br.com.wp.util.JsfUtil;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Wilson F Florindo
 */

@Named
@RequestScoped
public class ZerarCartaoClienteBean implements Serializable {

    @Inject
    private ListarClienteBean listarClienteBean;
    @Inject
    private ClienteService clienteService;  
    @Inject
    private CartaoService cartaoService;
    @Inject
    private JsfUtil jsfUtil;
  
    public void zerarCartoes() throws Exception {

        try {

            Cartao cartaoZero = cartaoService.buscarCartaoNumeroZero(NumeroCartao.ZERO.numero());

            clienteService.zerarCartoes(cartaoZero);

            jsfUtil.addMensagemInfo("Cartões zerados com sucesso");

            listarClienteBean.instanciarCliente();

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
