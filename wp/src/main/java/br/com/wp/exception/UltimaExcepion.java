/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.exception;

/**
 *
 * @author User
 */
public class UltimaExcepion {
    
     
    /** 
     * Percorrer a hierarquia da exception encontrada e retorna a ultima ramificação 
     * que é a causa da exception 
     * @param exception Exception a ser percorrida 
     * @return Exception encontrada no final da ramificação 
     */  
    public Throwable encontrarUltimaException(Throwable exception) {  
        Throwable ee = exception.getCause();  
        Throwable th = exception;  
  
        while ((ee != null) && ((ee = ee.getCause()) != null)) {  
            th = ee;  
        }  
        return th;  
}
}
