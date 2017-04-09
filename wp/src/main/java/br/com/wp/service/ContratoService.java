/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.service;

import br.com.wp.jpa.Transactional;
import br.com.wp.modelo.Contrato;
import br.com.wp.repositorio.ContratoRepositorio;
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
public class ContratoService implements Serializable {
    
    @Inject
    private ContratoRepositorio contratoRepositorio;
    @Inject
    private Contrato contratoNovo;
    
    public Contrato buscarDadosContrato() throws Exception {
        return contratoRepositorio.buscarDadosContrato();
    }    
    
    @Transactional
    public void validarSistema(Contrato contrato) throws Exception {
        
        int intDataAux = Integer.parseInt(contrato.getChave_data());
        int intDataContrato = (intDataAux * 5)+ 1555;
        String dataContrato = String.valueOf(intDataContrato);
        
        int intTempoAux = Integer.valueOf(contrato.getChave_tempo());
        int intTempoContrato = (intTempoAux * 5)+ 1555;
        String tempoContrato = String.valueOf(intTempoContrato);
        
        contratoNovo.setId(contrato.getId());
        contratoNovo.setChave_data(dataContrato);
        contratoNovo.setChave_tempo(tempoContrato);
        
        contratoRepositorio.validarSistema(contratoNovo);
        
    }
    
     public boolean vericaVencimentoContrato() {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

          
            try {

                contratoNovo = contratoRepositorio.buscarDadosContrato();
               
                int intTempoAux = Integer.valueOf(contratoNovo.getChave_tempo());
                int intTempoContrato = (intTempoAux - 1555) / 5;
                
                int intDataAux = Integer.parseInt(contratoNovo.getChave_data());
                int intDataContrato = (intDataAux - 1555) / 5;
                
                String strDataContrato = String.valueOf(intDataContrato);
                
                int qtdeCaracteres = strDataContrato.length();
                
                if(qtdeCaracteres < 8){
                    
                     strDataContrato = "0"+strDataContrato;
                  
                }
         
                char aux []  = strDataContrato.toCharArray();
       
                 String data = aux[0]+""+aux[1]+"/"+aux[2]+""+aux[3]+"/"+aux[4]+""+aux[5]+""+aux[6]+""+aux[7];
                
                 Date dateContrato = sdf.parse(data);             
                
                 Date dataAtual = sdf.parse(sdf.format(new Date().getTime()));
                
                 long diferenca = (dateContrato.getTime() - dataAtual.getTime()) / (1000 * 60 * 60 * 24);
               
                 if (diferenca > intTempoContrato) {
                 return true;
                 }
                 
            } catch (java.text.ParseException e) {

            }

        } catch (Exception ex) {

            Logger.getLogger(ContratoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
