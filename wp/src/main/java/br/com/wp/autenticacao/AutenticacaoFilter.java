/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.autenticacao;

import br.com.wp.enumeracao.StatusLogin;
import br.com.wp.modelo.Usuario;
import br.com.wp.service.UsuarioService;
import br.com.wp.util.CDIServiceLocator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Wilson F Florindo
 */
public class AutenticacaoFilter extends UsernamePasswordAuthenticationFilter {

    private final CriptografarSenha criptografarSenha = CDIServiceLocator.getBean(CriptografarSenha.class);
    private final UsuarioService usuarioService = CDIServiceLocator.getBean(UsuarioService.class);

    private Usuario usuarioLogado = CDIServiceLocator.getBean(Usuario.class);

    //@Inject
    //private Usuario usuarioLogado;
    private String mensagem;
    private String senhaCriptografada;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, BadCredentialsException {
        String login = request.getParameter("j_login");
        String senha = request.getParameter("j_senha");

        mensagem = "";

        try {

            senhaCriptografada = criptografarSenha.criptografarSenha(senha);

            usuarioLogado = usuarioService.autenticarUsuario(login, senhaCriptografada);

            if (usuarioLogado.getStatusLogin().toString().equalsIgnoreCase(StatusLogin.INATIVO.toString())) {

                mensagem = "Seu login esta desabilitado";
                usuarioLogado = null;
            }

            if (usuarioLogado != null) {

                Collection<GrantedAuthority> regras = new ArrayList<>();
                usuarioLogado.getUsuarioAutorizacao().stream().forEach((ua) -> {
                    regras.add(new SimpleGrantedAuthority(ua.getId().getAutorizacao().getTipo()));
                });

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                session.setAttribute("usuarioLogado", usuarioLogado);
              
                return new UsernamePasswordAuthenticationToken(login, senha, regras);

            } else {

                throw new BadCredentialsException(mensagem);
            }

        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authResult);
        response.sendRedirect("restrito/index.xhtml");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        if (mensagem.isEmpty()) {
            mensagem = "Login e/ou senha inválidos";
        }
        request.getSession().setAttribute("msg", mensagem);
        response.sendRedirect("login.xhtml");
    }
}
