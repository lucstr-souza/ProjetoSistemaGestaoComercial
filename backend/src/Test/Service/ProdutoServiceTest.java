package br.com.sgc.sgc_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.sgc.sgc_backend.domain.model.Produto;
import br.com.sgc.sgc_backend.dto.ProdutoRequestDTO;
import br.com.sgc.sgc_backend.exception.BusinessException;
import br.com.sgc.sgc_backend.exception.ResourceNotFoundException;
import br.com.sgc.sgc_backend.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private ProdutoRequestDTO dto;

    @BeforeEach
    void setUp() {
        dto = new ProdutoRequestDTO();
        dto.setNome("Sabonete Natural");
        dto.setDescricao("Sabonete artesanal de lavanda");
        dto.setPreco(BigDecimal.valueOf(15.90));
        dto.setEstoque(50);
        dto.setEstoqueMinimo(5);
    }

    @Test
    void deveSalvarProdutoComSucesso() {
        when(produtoRepository.save(any())).thenAnswer(inv -> {
            Produto p = inv.getArgument(0);
            p = Produto.builder()
                    .id(1L).nome(p.getNome()).descricao(p.getDescricao())
                    .preco(p.getPreco()).estoque(p.getEstoque())
                    .estoqueMinimo(p.getEstoqueMinimo()).build();
            return p;
        });

        var resultado = produtoService.salvar(dto);

        assertNotNull(resultado);
        assertEquals("Sabonete Natural", resultado.getNome());
        assertEquals(BigDecimal.valueOf(15.90), resultado.getPreco());
        verify(produtoRepository, times(1)).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoZero() {
        dto.setPreco(BigDecimal.ZERO);
        assertThrows(BusinessException.class, () -> produtoService.salvar(dto));
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoNegativo() {
        dto.setPreco(BigDecimal.valueOf(-5.00));
        assertThrows(BusinessException.class, () -> produtoService.salvar(dto));
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> produtoService.buscarPorId(99L));
    }

    @Test
    void deveIndicarEstoqueAbaixoDoMinimo() {
        dto.setEstoque(3);
        dto.setEstoqueMinimo(5);
        when(produtoRepository.save(any())).thenAnswer(inv -> {
            Produto p = inv.getArgument(0);
            return Produto.builder()
                    .id(1L).nome(p.getNome()).preco(p.getPreco())
                    .estoque(p.getEstoque()).estoqueMinimo(p.getEstoqueMinimo()).build();
        });

        var resultado = produtoService.salvar(dto);

        assertTrue(resultado.isEstoqueAbaixoDoMinimo());
    }
}
