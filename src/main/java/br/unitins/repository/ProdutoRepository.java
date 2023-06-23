package br.unitins.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.model.ItemCompra;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<ItemCompra> {
    
    
}