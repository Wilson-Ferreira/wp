package br.com.wp.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Wilson F Florindo
 */

@Entity
public class Configuracao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Empresa é obrigatório")
    @Column(name = "empresa", length = 40)
    private String empresa;

    @Column(name = "url_foto_empresa", length = 40)
    private String url_foto_empresa;
    
    @Column(name = "cnpj", unique = true, length = 20)
    private String cnpj;

    @Email
    @NotEmpty(message = "Email é obrigatório!")
    @Column(name = "email", length = 100, unique = true)
    private String email;
    
    @NotNull(message = "Uf é obrigatório")
    @ManyToOne
    @JoinColumn(name = "uf")
    private Uf uf;

    @NotNull(message = "Cidade é obrigatório")
    @ManyToOne
    @JoinColumn(name = "cidade")
    private Cidade cidade;

    @NotEmpty(message = "Bairro é obrigatório")
    @Column(name = "bairro")
    private String bairro;

    @NotEmpty(message = "Rua é obrigatório")
    @Column(name = "rua", length = 40)
    private String rua;

    @NotEmpty(message = "Número é obrigatório")
    @Column(name = "numero", length = 8)
    private String numero;

    @Column(name = "fone", length = 15)
    private String fone;

    @Column(name = "celular", length = 15)
    private String celular;

    @NotEmpty(message = "Tipo de cobrança é obrigatório")
    @Column(name = "tipoCobranca", length = 7)
    private String tipoCobranca;
     
    @Column(name = "entrada_couvert", precision = 6, scale = 2, nullable = true)
    private BigDecimal entrada_couvert;

    @NotNull(message = "Porcentagem do serviço é obrigatório")
    @Column(name = "porcentagemServico", length = 3)
    private Integer porcentagemServico;

    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dataContrato;
    
    @Column(name = "tempoContrato", length = 4)
    private Integer tempoContrato;
 
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
        if (!(object instanceof Configuracao)) {
            return false;
        }
        Configuracao other = (Configuracao) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "br.com.wp.modelo.Configuracao[ id=" + id + " ]";
    }

    public String getTipoCobranca() {
        return tipoCobranca;
    }

    public void setTipoCobranca(String tipoCobranca) {
        this.tipoCobranca = tipoCobranca;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public BigDecimal getEntrada_couvert() {
        return entrada_couvert;
    }

    public void setEntrada_couvert(BigDecimal entrada_couvert) {
        this.entrada_couvert = entrada_couvert;
    }

    public Integer getPorcentagemServico() {
        return porcentagemServico;
    }

    public void setPorcentagemServico(Integer porcentagemServico) {
        this.porcentagemServico = porcentagemServico;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl_foto_empresa() {
        return url_foto_empresa;
    }

    public void setUrl_foto_empresa(String url_foto_empresa) {
        this.url_foto_empresa = url_foto_empresa;
    }

    public java.util.Date getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(java.util.Date dataContrato) {
        this.dataContrato = dataContrato;
    }

    public Integer getTempoContrato() {
        return tempoContrato;
    }

    public void setTempoContrato(Integer tempoContrato) {
        this.tempoContrato = tempoContrato;
    }
}
