/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.exception;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Wilson F Florindo
 */

public class DataBaseException extends SQLException {
    
    // private String mensagemFormatada;
    private String valor;
    private String campo;
    private String mensagem;
   // private String aux;

    private String getMensagem() {
        return mensagem;
    }

    private void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getBytes(String utf) {
        return utf;

    }

    /**
     * Método responsável por receber a exceção e informar a mensagem de erro do
     * banco
     *
     * @param exception contendo o erro do banco
     */
    
    public DataBaseException(SQLException exception) {
        List<String> valoresRecebidos = new ArrayList<>();

        String s = exception.getMessage();
       
        Integer erro = exception.getErrorCode();

        
          if(s.toLowerCase().contains("duplicate")){
        //Padrão da expressão regular que procura pelos valores dentro de aspas. 
        Pattern p = Pattern.compile("'(.*?)'");
        Matcher m = p.matcher(s);
        while (m.find()) { //Faz a busca pelo padrão  
            valoresRecebidos.add(m.group()); //Pega valor
        }
        for (int i = 0; i < valoresRecebidos.size(); i += 2) {
            valor = valoresRecebidos.get(i);
            campo = valoresRecebidos.get(i + 1);
          
        }
      
        try {
            byte[] Str3 = valor.getBytes();
            valor = new String(Str3, "UTF-8");
            valor = valor.replace("'", "");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DataBaseException.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        switch (erro) {
            case 1062:
                setMensagem(valor +" - Registro duplicado na base de dados!");
                break;
            case 1451:
                setMensagem("Não foi possível excluir  pois há dependências na base de dados!");
                break;
            case 1022:
                setMensagem("Tentativa de duplicidade de chave!");
                break;
            case 1406:
                setMensagem("Erro dados longos para a coluna!");
                break;
            case 1408:
                setMensagem("Erro Campo obrigatório não pode ser nulo!");
                break;
            case 1048:
                setMensagem("Campos com (*) são obrigatórios!");
                break;
            default:
                setMensagem("Erro ao persistir os dados");
                break;
        }
    }

    @Override
    public String getMessage() {
        return getMensagem();
    }
}

