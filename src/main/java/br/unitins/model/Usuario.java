package br.unitins.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Usuario extends DefaultEntity {

    private String nomeImagem;
    
    private String nome;

    private String cpf;

    private String senha;

    private String email;

    @ElementCollection
    @CollectionTable(name = "perfis", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id"))
    @Column(name = "perfil", length = 30)
    private Set<Perfil> perfil;

    @OneToOne
    private Telefone telefone;

    @OneToOne()
    private Endereco endereco;

    @ManyToMany
    @JoinTable(name = "listadesejos", joinColumns = @JoinColumn(name = "usuario"), inverseJoinColumns = @JoinColumn(name = "vinho"))
    private List<Vinho> vinhosListaDesejos = new ArrayList<>();

    public Set<Perfil> getPerfil() {
        return perfil;
    }

    public void setPerfis(Set<Perfil> perfil) {
        this.perfil = perfil;
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

    public void setPerfil(Set<Perfil> perfil) {
        this.perfil = perfil;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setEndereco(Long endereco2) {
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

   

}
