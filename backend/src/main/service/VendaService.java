package br.com.sgc.sgc_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.sgc_backend.domain.model.*;
import br.com.sgc.sgc_backend.dto.*;
import br.com.sgc.sgc_backend.exception.BusinessException;
import br.com.sgc.sgc_backend.exception.ResourceNotFoundException;
import br.com.sgc.sgc_backend.repository.*;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final UsuarioRepository usuarioRepository;

    public VendaService(VendaRepository vendaRepository,
                        ClienteService clienteService,
                        ProdutoService produtoService,
                        UsuarioRepository usuarioRepository) {
        this.vendaRepository = vendaRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.usuarioRepository = usuarioRepository;
    }

    public List<VendaResponseDTO> listarTodas() {
        return vendaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public VendaResponseDTO buscarPorId(Long id) {
        return toResponseDTO(vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com id: " + id)));
    }

    public List<VendaResponseDTO> listarPorCliente(Long clienteId) {
        return vendaRepository.findByClienteId(clienteId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    public List<VendaResponseDTO> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findByDataBetween(inicio, fim).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public VendaResponseDTO registrar(VendaRequestDTO dto) {
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new BusinessException("A venda deve ter ao menos um item");
        }

        Cliente cliente = clienteService.buscarEntidade(dto.getClienteId());

        // Pega o usuário autenticado pelo token JWT
        String usernameLogado = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(usernameLogado)
                .orElseThrow(() -> new BusinessException("Usuário autenticado não encontrado"));

        Venda venda = new Venda();
        venda.setData(LocalDateTime.now());
        venda.setCliente(cliente);
        venda.setUsuario(usuario);
        venda.setItens(new ArrayList<>());

        for (ItemVendaRequestDTO itemDTO : dto.getItens()) {
            Produto produto = produtoService.buscarEntidade(itemDTO.getProdutoId());

            if (produto.getEstoque() < itemDTO.getQuantidade()) {
                throw new BusinessException("Estoque insuficiente para o produto '"
                        + produto.getNome() + "'. Disponível: " + produto.getEstoque());
            }

            produto.setEstoque(produto.getEstoque() - itemDTO.getQuantidade());
            produtoService.salvarEntidade(produto);

            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPreco(produto.getPreco());
            venda.getItens().add(item);
        }

        venda.calcularTotal();
        return toResponseDTO(vendaRepository.save(venda));
    }

    private VendaResponseDTO toResponseDTO(Venda v) {
        List<ItemVendaResponseDTO> itensDTO = v.getItens().stream()
                .map(item -> ItemVendaResponseDTO.builder()
                        .id(item.getId())
                        .nomeProduto(item.getProduto().getNome())
                        .quantidade(item.getQuantidade())
                        .preco(item.getPreco())
                        .subtotal(item.calcularSubtotal())
                        .build())
                .collect(Collectors.toList());

        return VendaResponseDTO.builder()
                .id(v.getId())
                .data(v.getData())
                .nomeCliente(v.getCliente().getNome())
                .usernameUsuario(v.getUsuario().getUsername())
                .valorTotal(v.getValorTotal())
                .itens(itensDTO)
                .build();
    }
}