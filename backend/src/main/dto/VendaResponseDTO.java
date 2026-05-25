package br.com.sgc.sgc_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendaResponseDTO {
    private Long id;
    private LocalDateTime data;
    private String nomeCliente;
    private String usernameUsuario;
    private BigDecimal valorTotal;
    private List<ItemVendaResponseDTO> itens;
}