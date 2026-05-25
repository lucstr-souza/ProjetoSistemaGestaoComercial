package br.com.sgc.sgc_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.sgc.sgc_backend.domain.model.Produto;
import br.com.sgc.sgc_backend.dto.ProdutoRequestDTO;
import br.com.sgc.sgc_backend.dto.ProdutoResponseDTO;
import br.com.sgc.sgc_backend.exception.BusinessException;
import br.com.sgc.sgc_backend.exception.ResourceNotFoundException;
import br.com.sgc.sgc_backend.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidade(id));
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        if (dto.getPreco().signum() <= 0) {
            throw new BusinessException("Preço deve ser maior que zero");
        }
        int estoqueMinimo = dto.getEstoqueMinimo() != null ? dto.getEstoqueMinimo() : 5;
        Produto produto = Produto.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .estoque(dto.getEstoque())
                .estoqueMinimo(estoqueMinimo)
                .build();
        if (produto.getEstoque() <= estoqueMinimo) {
            System.out.println("ALERTA: Produto '" + produto.getNome() + "' com estoque abaixo do mínimo");
        }
        return toResponseDTO(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        if (dto.getPreco().signum() <= 0) {
            throw new BusinessException("Preço deve ser maior que zero");
        }
        Produto existente = buscarEntidade(id);
        existente.setNome(dto.getNome());
        existente.setDescricao(dto.getDescricao());
        existente.setPreco(dto.getPreco());
        existente.setEstoque(dto.getEstoque());
        if (dto.getEstoqueMinimo() != null) {
            existente.setEstoqueMinimo(dto.getEstoqueMinimo());
        }
        return toResponseDTO(produtoRepository.save(existente));
    }

    public void excluir(Long id) {
        buscarEntidade(id);
        produtoRepository.deleteById(id);
    }

    // Uso interno pelo VendaService
    public Produto buscarEntidade(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
    }

    public Produto salvarEntidade(Produto produto) {
        return produtoRepository.save(produto);
    }

    private ProdutoResponseDTO toResponseDTO(Produto p) {
        return ProdutoResponseDTO.builder()
                .id(p.getId())
                .nome(p.getNome())
                .descricao(p.getDescricao())
                .preco(p.getPreco())
                .estoque(p.getEstoque())
                .estoqueMinimo(p.getEstoqueMinimo())
                .estoqueAbaixoDoMinimo(p.getEstoque() <= p.getEstoqueMinimo())
                .build();
    }
}