package br.com.wp.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Wilson F Florindo
 */

@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Nome é obrigatório!")
    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "cpf", length = 15)
    private String cpf;
   
    @Column(name = "status_cartao", nullable = false, length = 10)
    private String statusCartao;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "celular")
    private String celular;
    
    @OneToOne
    @JoinColumn(name = "cartao")
    @NotNull(message = "Cartão é obrigatório!")
    private Cartao cartao;
      
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "br.com.wp.modelo.Cliente[ id=" + id + " ]";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
    

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the statusCartao
     */
    public String getStatusCartao() {
        return statusCartao;
    }

    /**
     * @param statusCartao the statusCartao to set
     */
    public void setStatusCartao(String statusCartao) {
        this.statusCartao = statusCartao;
    }
    
    

}
