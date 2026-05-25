package br.com.sgc.sgc_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.sgc.sgc_backend.domain.model.Cliente;
import br.com.sgc.sgc_backend.dto.ClienteRequestDTO;
import br.com.sgc.sgc_backend.dto.ClienteResponseDTO;
import br.com.sgc.sgc_backend.exception.BusinessException;
import br.com.sgc.sgc_backend.exception.ResourceNotFoundException;
import br.com.sgc.sgc_backend.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public ClienteResponseDTO salvar(ClienteRequestDTO dto) {
        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("CPF já cadastrado: " + dto.getCpf());
        }
        Cliente cliente = Cliente.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .endereco(dto.getEndereco())
                .build();
        return toResponseDTO(clienteRepository.save(cliente));
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente existente = buscarEntidade(id);
        if (!existente.getCpf().equals(dto.getCpf()) && clienteRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("CPF já cadastrado: " + dto.getCpf());
        }
        existente.setNome(dto.getNome());
        existente.setCpf(dto.getCpf());
        existente.setEmail(dto.getEmail());
        existente.setTelefone(dto.getTelefone());
        existente.setEndereco(dto.getEndereco());
        return toResponseDTO(clienteRepository.save(existente));
    }

    public void excluir(Long id) {
        buscarEntidade(id);
        if (clienteRepository.clientePossuiVendas(id)) {
            throw new BusinessException("Cliente possui vendas e não pode ser excluído");
        }
        clienteRepository.deleteById(id);
    }

    // Método de uso interno (VendaService precisa da entidade)
    public Cliente buscarEntidade(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
    }

    private ClienteResponseDTO toResponseDTO(Cliente c) {
        return ClienteResponseDTO.builder()
                .id(c.getId())
                .nome(c.getNome())
                .cpf(c.getCpf())
                .email(c.getEmail())
                .telefone(c.getTelefone())
                .endereco(c.getEndereco())
                .build();
    }
}