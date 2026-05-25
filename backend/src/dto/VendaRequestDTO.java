package br.com.sgc.sgc_backend.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VendaRequestDTO {

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @NotEmpty(message = "A venda deve ter ao menos um item")
    @Valid
    private List<ItemVendaRequestDTO> itens;
}