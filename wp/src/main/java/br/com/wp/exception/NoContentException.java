/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.exception;

import java.io.Serializable;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Wilson F Florindo
 */

public class NoContentException extends WebApplicationException implements Serializable{
    

    public NoContentException(String mensagem) {
     super(Response.status(Status.NOT_FOUND).entity(mensagem).build());
    }
}
