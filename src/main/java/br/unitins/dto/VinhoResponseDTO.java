package br.unitins.dto;

import br.unitins.model.Produtor;
import br.unitins.model.TipoVinho;
import br.unitins.model.Vinho;

public record VinhoResponseDTO (
    Long id,
     String nome,
     int estoque,
     Double preco,
     String teorAlcoolico,
     String tipoUva,
     TipoVinho tipoVinho,
     Produtor produtor
){
    public VinhoResponseDTO(Vinho vinho) {
        this(vinho.getId(), vinho.getNome(), vinho.getEstoque(), vinho.getPreco(), vinho.getTeorAlcoolico(), vinho.getTipoUva(), vinho.getTipoVinho(), vinho.getProdutor());
    }

    
}
