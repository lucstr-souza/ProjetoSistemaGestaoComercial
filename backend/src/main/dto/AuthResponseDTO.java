package br.com.sgc.sgc_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {
    private String token;
    private String username;
    private String perfil;
}
