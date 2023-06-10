package br.unitins.service;
import java.util.List;
import br.unitins.dto.ItemCompraDTO;
import br.unitins.dto.ItemCompraResponseDTO;

public interface ItemCompraService {
    List<ItemCompraResponseDTO> getAll();

    ItemCompraResponseDTO findById(Long id);

    ItemCompraResponseDTO insert(ItemCompraDTO itemCompraDTO);

    ItemCompraResponseDTO update(Long id, ItemCompraDTO itemCompraDTO);

    void delete(Long id);

    // Outros métodos específicos, se necessário
}
