package br.unitins.dto;

import br.unitins.model.Telefone;

public record TelefoneResponseDTO (
    String codigoArea,
    String numero
){
    public TelefoneResponseDTO(Telefone telefone) {
        this(telefone.getCodigoArea(),telefone.getNumero()); 
    }
}
