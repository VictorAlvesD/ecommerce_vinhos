package br.unitins.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.dto.ItemCompraDTO;
import br.unitins.dto.ItemCompraResponseDTO;
import br.unitins.model.ItemCompra;
import br.unitins.repository.ItemCompraRepository;
import br.unitins.repository.ProdutoRepository;

@ApplicationScoped
public class ItemCompraServiceImpl implements ItemCompraService {
    @Inject
    ItemCompraRepository itemCompraRepository;

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    Validator validator;

    @Override
    public List<ItemCompraResponseDTO> getAll() {
        List<ItemCompra> list = itemCompraRepository.listAll();
        return list.stream().map(ItemCompraResponseDTO::valueOf).collect(Collectors.toList());
    }

    @Override
    public ItemCompraResponseDTO findById(Long id) {
        ItemCompra itemCompra = itemCompraRepository.findById(id);
        if (itemCompra == null)
            throw new NotFoundException("Item Compra não encontrado!");
        return ItemCompraResponseDTO.valueOf(itemCompra);
    }

    private void validarId(ItemCompra itemCompra) throws ConstraintViolationException {
        if (itemCompra.getId() == null)
            throw new NullPointerException("Id inválido");
    }

    @Override
    @Transactional
    public ItemCompraResponseDTO insert(ItemCompraDTO itemCompraDTO) throws ConstraintViolationException {
        validar(itemCompraDTO);

        ItemCompra entity = new ItemCompra();
        entity.setQuantidade(itemCompraDTO.quantidade());
        entity.setProduto(produtoRepository.findById(itemCompraDTO.produto().getId()));
        entity.setDataCompra(LocalDateTime.now());
        itemCompraRepository.persist(entity);

        return ItemCompraResponseDTO.valueOf(entity);
    }

    private void validar(ItemCompraDTO itemCompraDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<ItemCompraDTO>> violations = validator.validate(itemCompraDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public ItemCompraResponseDTO update(Long id, ItemCompraDTO itemCompraDTO) throws ConstraintViolationException {
        validar(itemCompraDTO);
        ItemCompra entity = itemCompraRepository.findById(id);
        if (entity == null)
            throw new NotFoundException("Item Compra não encontrado!");
        
        entity.setQuantidade(itemCompraDTO.quantidade());
        entity.setProduto(produtoRepository.findById(itemCompraDTO.produto().getId()));
        entity.setDataCompra(LocalDateTime.now());
        itemCompraRepository.persist(entity);

        return ItemCompraResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ItemCompra itemCompra = itemCompraRepository.findById(id);
        validarId(itemCompra);
        itemCompraRepository.delete(itemCompra);
    }

    // Outros métodos específicos, se necessário
}
