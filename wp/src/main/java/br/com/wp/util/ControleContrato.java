/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.util;

import br.com.wp.modelo.Configuracao;
import br.com.wp.service.ConfiguracaoService;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Wilson F Florindo
 */

public class ControleContrato implements Serializable {

    @Inject
    private ConfiguracaoService configuracaoService;
    @Inject
    private Configuracao configuracao;

    public boolean vericaVencimentoContrato() {

        try {

            configuracao = configuracaoService.buscarConfiguracoes();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            java.util.Date dataAtual;
            java.util.Date dataContrato;

            try {

                dataContrato = sdf.parse(sdf.format(configuracao.getDataContrato().getTime()));
                dataAtual = sdf.parse(sdf.format(new Date().getTime()));

                long diferenca = (dataAtual.getTime() - dataContrato.getTime()) / (1000 * 60 * 60 * 24);

                System.out.println("diferença " + diferenca + " tempo " + configuracao.getTempoContrato());

                if (diferenca > configuracao.getTempoContrato()) {
                    return true;
                }

            } catch (java.text.ParseException e) {

            }

        } catch (Exception ex) {

            Logger.getLogger(ControleContrato.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
