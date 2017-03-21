/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.modelo;

import br.com.wp.enumeracao.StatusLogin;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author  Wilson F Florindo
 */

@XmlRootElement
@Entity
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Funcionário é obrigatório!")
    @JoinColumn(name = "funcionario")
    @ManyToOne
    private Funcionario funcionario;
   
    @NotEmpty(message = "Senha é obrigatório!")
    @Column(name = "senha")
    private String senha;

    @Column(name = "status_login", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusLogin statusLogin;
  
    /*
    @NotNull(message = "Status_login é obrigatório!")
    @Column(name = "status_login", length = 10)
    private String status_login;
*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.usuario",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE })
    private List<UsuarioAutorizacao> usuarioAutorizacao = new ArrayList<>();

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "br.com.wp.modelo.Usuario[ id=" + id + " ]";
    }

   
    public List<UsuarioAutorizacao> getUsuarioAutorizacao() {
        return usuarioAutorizacao;
    }

    public void setUsuarioAutorizacao(List<UsuarioAutorizacao> usuarioAutorizacao) {
        this.usuarioAutorizacao = usuarioAutorizacao;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public StatusLogin getStatusLogin() {
        return statusLogin;
    }

    public void setStatusLogin(StatusLogin statusLogin) {
        this.statusLogin = statusLogin;
    }

}
