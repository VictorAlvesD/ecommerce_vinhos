package br.unitins.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.model.Compra;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CompraRepository implements PanacheRepository<Compra> {

    public List<Compra> findByClienteId(Long clienteId) {
        if (clienteId == null)
            return null;
        return find("clienteId", clienteId).list();
    }
}