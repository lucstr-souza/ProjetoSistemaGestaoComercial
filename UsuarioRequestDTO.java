package br.com.sgc.sgc_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "Username é obrigatório")
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @NotBlank(message = "Perfil é obrigatório")
    private String perfil;
}