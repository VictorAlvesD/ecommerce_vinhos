package br.unitins.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.unitins.dto.ProdutoResponseDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Column;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
public class ItemCompra extends DefaultEntity {

    @OneToOne
    private Vinho vinho;

    @Column(name = "quantidade")
    private int quantidade;

    private LocalDate dataCompra;

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    @PrePersist
    private void gerarDataCompra() {
        dataCompra = LocalDate.now();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataCompra() {
        return LocalDate.now();
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = LocalDate.now();
    }

    public Vinho getVinho() {
        return vinho;
    }

    public void setVinho(Vinho vinho) {
        this.vinho = vinho;
    }

    public void setProduto(ItemCompra findById) {
    }

}
