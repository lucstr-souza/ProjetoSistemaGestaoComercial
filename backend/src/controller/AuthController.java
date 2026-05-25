package br.com.sgc.sgc_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import br.com.sgc.sgc_backend.config.JwtService;
import br.com.sgc.sgc_backend.dto.AuthRequestDTO;
import br.com.sgc.sgc_backend.dto.AuthResponseDTO;
import br.com.sgc.sgc_backend.exception.BusinessException;
import br.com.sgc.sgc_backend.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getSenha())
            );
        } catch (BadCredentialsException e) {
            throw new BusinessException("Credenciais inválidas");
        }

        var usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        String token = jwtService.gerarToken(usuario.getUsername());

        return ResponseEntity.ok(AuthResponseDTO.builder()
                .token(token)
                .username(usuario.getUsername())
                .perfil(usuario.getPerfil())
                .build());
    }
}