package br.unitins.dto;

import java.time.LocalDate;

import br.unitins.model.Compra;
import br.unitins.model.Usuario;

public record CompraResponseDTO(
    Long id,
    LocalDate data,
    Double totalCompra,
    Usuario usuario
){
    public CompraResponseDTO(Compra compra){
        this(compra.getId(), compra.getData(), compra.getTotalCompra(), compra.getUsuario());
    }
}