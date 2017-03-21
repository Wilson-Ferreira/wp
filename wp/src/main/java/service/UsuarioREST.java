/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import br.com.wp.enumeracao.StatusLogin;
import br.com.wp.modelo.Usuario;
import br.com.wp.modelo.UsuarioAutorizacao;
import br.com.wp.service.UsuarioService;
import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Wilson F Florindo
 */
@Path("/usuario")
public class UsuarioREST {

    @Inject
    private UsuarioService usuarioService;
    private String strRetorno;
    private boolean autorizacao = false;
    private boolean loginAtivo = false;
    private Usuario usuario;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String loginAndroid(String strUsuario) {

        java.util.Date dataCadastro;
        java.util.Date dataNascimento;
        SimpleDateFormat dateFormat
                = new SimpleDateFormat("yyyy-MM-dd");

        Gson gson = new Gson();

        usuario = gson.fromJson(strUsuario, Usuario.class);

        try {

            usuario = usuarioService.autenticarUsuario(usuario.getFuncionario().getEmail(), usuario.getSenha());

            if (usuario.getStatusLogin().toString().equalsIgnoreCase(StatusLogin.ATIVO.toString())) {
             
                loginAtivo = true;
               
            } 

            if(loginAtivo){
                for (UsuarioAutorizacao ua : usuario.getUsuarioAutorizacao()) {

                    if (ua.getId().getAutorizacao().getNomeFantazia().equalsIgnoreCase("SALVAR PEDIDOS")) {
                        autorizacao = true;                      
                        break;
                    } 
                }
            }
            
             
            if(loginAtivo == false){
                strRetorno = "Login desativado";
            }
        
           else if(autorizacao == false){
                strRetorno = "Acesso negado";
            }
           
            if (autorizacao == true && loginAtivo == true) {
              
                    try {
                        
                        dataCadastro = dateFormat.parse(usuario.getFuncionario().getDataCadastro().toString());
                        dataNascimento = dateFormat.parse(usuario.getFuncionario().getDataNascimento().toString());
                        
                        usuario.getFuncionario().setDataCadastro(dataCadastro);
                        usuario.getFuncionario().setDataNascimento(dataNascimento);
                        
                        strRetorno = gson.toJson(usuario.getFuncionario());
                        
                    } catch (ParseException ex) {
                        
                        strRetorno = "Erro ao converter data!";
                        //Logger.getLogger(UsuarioREST.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        
        } catch (Exception ex) {

            return "Login e/ou senha inválidos";
        }

        return strRetorno;

}
}
