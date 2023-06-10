package br.unitins.service;

import java.util.ArrayList;
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

import br.unitins.dto.UsuarioDTO;
import br.unitins.dto.UsuarioResponseDTO;
import br.unitins.model.Endereco;
import br.unitins.model.Usuario;
import br.unitins.model.Vinho;
import br.unitins.repository.EnderecoRepository;
import br.unitins.repository.TelefoneRepository;
import br.unitins.repository.UsuarioRepository;
import br.unitins.repository.VinhoRepository;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    VinhoRepository vinhoRepository;

    @Inject
    Validator validator;

    @Override
    public List<UsuarioResponseDTO> getAll() {
        List<Usuario> list = usuarioRepository.listAll();
        return list.stream().map(u -> UsuarioResponseDTO.valueOf(u)).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null)
            throw new NotFoundException("Usuario não encontrado!");
        return UsuarioResponseDTO.valueOf(usuario);
    }

    private void validarId(Usuario vinho) throws ConstraintViolationException {
        if (vinho.getId() == null)
            throw new NullPointerException("Id inválido");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(usuarioRepository.findById(id));
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<UsuarioResponseDTO> findByCpf(String cpf) {
        List<Usuario> list = usuarioRepository.findByCpf(cpf);
        return list.stream().map(u -> UsuarioResponseDTO.valueOf(u)).collect(Collectors.toList());
    }

    public Usuario findByLoginAndSenha(String email, String senha) {
        return usuarioRepository.findByLoginAndSenha(email, senha);
    }

    public UsuarioResponseDTO findByLogin(String email) {
        Usuario usuario = usuarioRepository.findByLogin(email);
        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public long count() {
        return usuarioRepository.count();
    }
}
