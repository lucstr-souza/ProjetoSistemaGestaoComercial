package br.com.sgc.sgc_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {
    private Long id;
    private String username;
    private String perfil;
    // senha NUNCA aparece na resposta
}