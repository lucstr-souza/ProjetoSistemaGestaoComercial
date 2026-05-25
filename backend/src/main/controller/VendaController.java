package br.com.sgc.sgc_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sgc.sgc_backend.dto.VendaRequestDTO;
import br.com.sgc.sgc_backend.dto.VendaResponseDTO;
import br.com.sgc.sgc_backend.service.VendaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(vendaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VendaResponseDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(vendaService.listarPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<VendaResponseDTO> registrar(@Valid @RequestBody VendaRequestDTO dto) {
        return ResponseEntity.status(201).body(vendaService.registrar(dto));
    }
}