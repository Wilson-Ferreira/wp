/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wp.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Wilson F Florindo
 */
@Entity
@XmlRootElement
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dataCadastro;
    
    @Past (message = "Data de nascimento deve ser anterior a data atual")
    @NotNull(message = "Data de nascimento é obrigatório!")
    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dataNascimento;
    
    @JoinColumn(name = "cargo")
    @NotNull(message = "Cargo é obrigatório!")
    @ManyToOne
    private Cargo cargo;

    @Length(max = 50, min = 5, message = "Nome mínimo 5, máximo 30 caracteres!")
    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "celular")
    private String celular;

    @Email
    @NotEmpty(message = "Email é obrigatório!")
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @NotNull(message = "Uf é obrigatório!")
    @ManyToOne
    @JoinColumn(name = "uf")
    private Uf uf;

    @NotNull(message = "Cidade é obrigatório!")
    @ManyToOne
    @JoinColumn(name = "cidade")
    private Cidade cidade;

    @NotEmpty(message = "Bairro é obrigatório!")
    @Column(name = "bairro", length = 50)
    private String bairro;
    
    @NotEmpty(message = "Rua é obrigatório!")
    @Column(name = "rua", length = 50)
    private String rua;

    @NotEmpty(message = "Número é obrigatório!")
    @Column(name = "numero")
    private String numero;

    @NotEmpty(message = "Campo cep é obrigatório!")
    @Column(name = "cep", length = 10)
    private String cep;

    @NotEmpty(message = "Rg é obrigatório!")
    @Column(name = "rg",unique = true, length = 15)
    private String rg;

    @Column(name = "cpf", unique = true, length = 15)
    private String cpf;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "br.com.wj.modelo.Funcionario[ id=" + id + " ]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBairro() {
        return bairro;
    }

    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
