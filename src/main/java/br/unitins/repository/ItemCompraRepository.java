package br.unitins.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.model.ItemCompra;

@ApplicationScoped
public class ItemCompraRepository implements PanacheRepository<ItemCompra> {
    
    public ItemCompra findByID(Long id) {
        if (id == null)
            return null;
        return find("id = ?1", id).firstResult();
    }
    
}
