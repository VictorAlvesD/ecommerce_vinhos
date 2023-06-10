package br.unitins.repository;

import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.model.Usuario;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario>{
    public List<Usuario> findByCpf(String cpf) {
        if (cpf == null)
            return null;
        return find("cpf LIKE ?1 ", "%" + cpf + "%").list();
    }
    public Usuario findByID(Integer id) {
        if (id == null)
            return null;
        return  find("id = ?1", id).firstResult();
    }

    public Usuario findByLoginAndSenha(String email, String senha){
        if (email == null || senha == null)
            return null;
            
        return find("email = ?1 AND senha = ?2 ", email, senha).firstResult();
    }
    public Usuario findByLogin(String email){
        if (email == null)
            return null;
            
        return find("email = ?1 ", email).firstResult();
    }
}
