package br.unitins.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

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
public class UsuarioServiceImpl implements UsuarioService{
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
        return list.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario pessoafisica = usuarioRepository.findById(id);
        if (pessoafisica == null)
            throw new NotFoundException("Usuario não encontrado!");
        return new UsuarioResponseDTO(pessoafisica);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO insert(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        Usuario entity = new Usuario();
        entity.setNome(usuarioDTO.nome());
        entity.setCpf(usuarioDTO.cpf());
        entity.setEmail(usuarioDTO.email());
        entity.setSenha(usuarioDTO.senha());

        entity.setTelefone(telefoneRepository.findById(usuarioDTO.telefone().longValue()));
        
        List<Endereco> end = new ArrayList<Endereco>();
        end.add(enderecoRepository.findById(usuarioDTO.endereco()));
        entity.setEnderecos(end);

        List<Vinho> vinhos = new ArrayList<Vinho>();
        vinhos.add(vinhoRepository.findByID(usuarioDTO.vinhosListaDesejos().intValue()));
        entity.setVinhosListaDesejos(vinhos); 

        usuarioRepository.persist(entity);

    
        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDTO) throws ConstraintViolationException{
        validar(usuarioDTO);
   
        Usuario entity = usuarioRepository.findById(id);
        validarId(entity);
        entity.setNome(usuarioDTO.nome());
        entity.setCpf(usuarioDTO.cpf());
        entity.setEmail(usuarioDTO.email());
        entity.setSenha(usuarioDTO.senha());
        entity.setTelefone(telefoneRepository.findById(usuarioDTO.telefone().longValue()));

        List<Endereco> end = new ArrayList<Endereco>();
        end.add(enderecoRepository.findById(usuarioDTO.endereco()));
        entity.setEnderecos(end);

        return new UsuarioResponseDTO(entity);
    }

    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
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
        return list.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return usuarioRepository.count();
    }
}
