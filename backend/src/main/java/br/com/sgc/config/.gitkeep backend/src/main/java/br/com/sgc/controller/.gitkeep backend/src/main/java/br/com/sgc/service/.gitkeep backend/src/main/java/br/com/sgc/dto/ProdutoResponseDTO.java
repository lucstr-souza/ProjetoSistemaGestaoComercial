package br.com.sgc.sgc_backend.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;
    private Integer estoqueMinimo;
    private boolean estoqueAbaixoDoMinimo;
}