package br.com.sgc.sgc_backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vd_vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vd_id")
    private Long id;

    @Column(name = "vd_data", nullable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "cli_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "us_id", nullable = false)
    private Usuario usuario;

    @Builder.Default
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> itens = new ArrayList<>();

    @Column(name = "vd_valortotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    public void calcularTotal() {
        if (itens == null || itens.isEmpty()) {
            this.valorTotal = BigDecimal.ZERO;
            return;
        }
        this.valorTotal = itens.stream()
                .map(ItemVenda::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}