package br.com.sgc.sgc_backend.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemVendaResponseDTO {
    private Long id;
    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal preco;
    private BigDecimal subtotal;
}
