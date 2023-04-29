package br.unitins.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Usuario extends DefaultEntity {

    private String nome;

    private String cpf;

    private String senha;

    private String email;

    @OneToOne
    private Telefone telefone;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Endereco> enderecos = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "listadesejos", joinColumns = @JoinColumn(name = "usuario"), inverseJoinColumns = @JoinColumn(name = "vinho"))
    private List<Vinho> vinhosListaDesejos = new ArrayList<>();

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }



    public List<Vinho> getVinhosListaDesejos() {
        return vinhosListaDesejos;
    }

    public void setVinhosListaDesejos(List<Vinho> vinhosListaDesejos) {
        this.vinhosListaDesejos = vinhosListaDesejos;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    

    

    

}
