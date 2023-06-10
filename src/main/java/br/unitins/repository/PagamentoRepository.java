package br.unitins.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.model.Pagamento;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {
    
    public Pagamento findByID(Integer id) {
        if (id == null)
            return null;
        return find("id = ?1", id).firstResult();
    }
    
}