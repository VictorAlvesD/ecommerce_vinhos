package br.unitins.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Vinho extends Produto {
    @ManyToMany(mappedBy = "vinhosListaDesejos")
    @JsonIgnore
    private List<Usuario> usuariosListaDesejos = new ArrayList<>();
    private String teorAlcoolico;
    private String tipoUva;
    private String descricao;
    
    @Enumerated(EnumType.ORDINAL)
    private TipoVinho tipoVinho;
    
    @ManyToOne
    @JoinColumn(name = "produtor")
    private Produtor produtor;
    
    private String nomeImagem;
    
    public String getTeorAlcoolico() {
        return teorAlcoolico;
    }

    public void setTeorAlcoolico(String teorAlcoolico) {
        this.teorAlcoolico = teorAlcoolico;
    }

    public String getTipoUva() {
        return tipoUva;
    }

    public void setTipoUva(String tipoUva) {
        this.tipoUva = tipoUva;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoVinho getTipoVinho() {
        return tipoVinho;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public void setTipoVinho(TipoVinho tipoVinho) {
        this.tipoVinho = tipoVinho;
    }

    public Produtor getProdutor() {
        return produtor;
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    public List<Usuario> getUsuariosListaDesejos() {
        return usuariosListaDesejos;
    }

    public void setUsuariosListaDesejos(List<Usuario> usuariosListaDesejos) {
        this.usuariosListaDesejos = usuariosListaDesejos;
    }

}
