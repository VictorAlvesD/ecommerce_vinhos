package br.unitins.service;

import java.util.HashSet;
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
import br.unitins.model.Perfil;
import br.unitins.model.Telefone;
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
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null)
            throw new NotFoundException("usuário não encontrado.");
        return new UsuarioResponseDTO(usuario);
    }

    private void validarId(Usuario usuario) throws ConstraintViolationException {
        if (usuario.getId() == null)
            throw new NullPointerException("Id inválido");
    }

    @Override
    @Transactional
    public UsuarioResponseDTO insert(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        Usuario entity = new Usuario();
        entity.setNome(usuarioDTO.nome());
        entity.setCpf(usuarioDTO.cpf());
        entity.setSenha(usuarioDTO.senha());
        entity.setEmail(usuarioDTO.email());

        Integer idPerfil = usuarioDTO.perfil();
        Set<Perfil> perfis = null;
        if (idPerfil != null) {
            Perfil perfil = Perfil.valueOf(idPerfil);
            perfis = new HashSet<>();
            perfis.add(perfil);
        }
        entity.setPerfis(perfis);

        Telefone telefone = telefoneRepository.findById(usuarioDTO.telefone());
        entity.setTelefone(telefone);

        Endereco endereco = enderecoRepository.findById(usuarioDTO.endereco());
        entity.setEndereco(endereco);

        Vinho vinho = vinhoRepository.findById(usuarioDTO.vinhosListaDesejos());
        entity.setVinhosListaDesejos(vinho);

        usuarioRepository.persist(entity);

        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        Usuario entity = usuarioRepository.findById(id);
        entity.setNome(usuarioDTO.nome());
        entity.setCpf(usuarioDTO.cpf());
        entity.setSenha(usuarioDTO.senha());
        entity.setEmail(usuarioDTO.email());

        Integer idPerfil = usuarioDTO.perfil();
        Set<Perfil> perfis = null;
        if (idPerfil != null) {
            Perfil perfil = Perfil.valueOf(idPerfil);
            perfis = new HashSet<>();
            perfis.add(perfil);
        }

        Telefone telefone = telefoneRepository.findById(usuarioDTO.telefone());
        entity.setTelefone(telefone);

        Endereco endereco = enderecoRepository.findById(usuarioDTO.endereco());
        entity.setEndereco(endereco);

        usuarioRepository.persist(entity);

        return new UsuarioResponseDTO(entity);
    }

    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(usuarioRepository.findById(id));
        usuarioRepository.deleteById(id);
    }

    @Override
    public long count() {
        return telefoneRepository.count();
    }

    @Override
    public Usuario findByLoginAndSenha(String email, String senha) {
        return usuarioRepository.findByLoginAndSenha(email, senha);
    }

    @Override
    public UsuarioResponseDTO update(Long id, String nomeImagem) {
        Usuario usuario = usuarioRepository.findById(id);
        usuario.setNomeImagem(nomeImagem);
        return new UsuarioResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO findByLogin(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null)
            throw new NotFoundException("Usuario não encontrado");
        return new UsuarioResponseDTO(usuario);
    }
}
