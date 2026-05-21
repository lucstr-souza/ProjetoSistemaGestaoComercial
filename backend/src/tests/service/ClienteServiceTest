package br.com.sgc.sgc_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.sgc.sgc_backend.domain.model.Cliente;
import br.com.sgc.sgc_backend.dto.ClienteRequestDTO;
import br.com.sgc.sgc_backend.exception.BusinessException;
import br.com.sgc.sgc_backend.exception.ResourceNotFoundException;
import br.com.sgc.sgc_backend.repository.ClienteRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteRequestDTO dto;

    @BeforeEach
    void setUp() {
        dto = new ClienteRequestDTO();
        dto.setNome("Maria da Silva");
        dto.setCpf("12345678901");
        dto.setEmail("maria@email.com");
        dto.setTelefone("61999990001");
        dto.setEndereco("Rua das Flores, 10");
    }

    @Test
    void deveSalvarClienteComSucesso() {
        when(clienteRepository.existsByCpf("12345678901")).thenReturn(false);
        when(clienteRepository.save(any())).thenAnswer(inv -> {
            Cliente c = inv.getArgument(0);
            c = Cliente.builder()
                    .id(1L).nome(c.getNome()).cpf(c.getCpf())
                    .email(c.getEmail()).telefone(c.getTelefone())
                    .endereco(c.getEndereco()).build();
            return c;
        });

        var resultado = clienteService.salvar(dto);

        assertNotNull(resultado);
        assertEquals("Maria da Silva", resultado.getNome());
        assertEquals("12345678901", resultado.getCpf());
        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoCpfDuplicado() {
        when(clienteRepository.existsByCpf("12345678901")).thenReturn(true);

        assertThrows(BusinessException.class, () -> clienteService.salvar(dto));
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarPorId(99L));
    }

    @Test
    void deveLancarExcecaoAoExcluirClienteComVendas() {
        Cliente cliente = Cliente.builder().id(1L).nome("Maria").cpf("12345678901").email("maria@email.com").build();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.clientePossuiVendas(1L)).thenReturn(true);

        assertThrows(BusinessException.class, () -> clienteService.excluir(1L));
        verify(clienteRepository, never()).deleteById(any());
    }
}
