package br.com.sgc.sgc_backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.sgc.sgc_backend.dto.VendaResponseDTO;
import br.com.sgc.sgc_backend.service.VendaService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final VendaService vendaService;

    public RelatorioController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<VendaResponseDTO>> vendasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(vendaService.listarPorPeriodo(inicio, fim));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VendaResponseDTO>> vendasPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(vendaService.listarPorCliente(clienteId));
    }
}
