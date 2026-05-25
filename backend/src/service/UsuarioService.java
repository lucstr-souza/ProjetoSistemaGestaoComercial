package br.com.sgc.sgc_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.sgc.sgc_backend.domain.model.Usuario;
import br.com.sgc.sgc_backend.dto.UsuarioRequestDTO;
import br.com.sgc.sgc_backend.dto.UsuarioResponseDTO;
import br.com.sgc.sgc_backend.exception.BusinessException;
import br.com.sgc.sgc_backend.exception.ResourceNotFoundException;
import br.com.sgc.sgc_backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Username já cadastrado: " + dto.getUsername());
        }
        Usuario usuario = Usuario.builder()
                .username(dto.getUsername())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .perfil(dto.getPerfil())
                .build();
        return toResponseDTO(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario existente = buscarEntidade(id);
        if (!existente.getUsername().equals(dto.getUsername())
                && usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Username já está em uso: " + dto.getUsername());
        }
        existente.setUsername(dto.getUsername());
        existente.setPerfil(dto.getPerfil());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            existente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        return toResponseDTO(usuarioRepository.save(existente));
    }

    public void excluir(Long id) {
        buscarEntidade(id);
        usuarioRepository.deleteById(id);
    }

    private Usuario buscarEntidade(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
    }

    private UsuarioResponseDTO toResponseDTO(Usuario u) {
        return UsuarioResponseDTO.builder()
                .id(u.getId())
                .username(u.getUsername())
                .perfil(u.getPerfil())
                .build();
    }
}