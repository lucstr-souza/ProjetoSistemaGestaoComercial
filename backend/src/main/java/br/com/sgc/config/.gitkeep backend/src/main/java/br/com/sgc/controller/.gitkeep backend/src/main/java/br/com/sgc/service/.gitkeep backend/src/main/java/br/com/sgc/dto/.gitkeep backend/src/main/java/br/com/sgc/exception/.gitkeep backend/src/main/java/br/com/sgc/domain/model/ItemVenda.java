package br.com.sgc.sgc_backend.domain.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "it_itens_venda")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "it_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vd_id", nullable = false)
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "pd_id", nullable = false)
    private Produto produto;

    @Column(name = "it_quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "it_preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    public BigDecimal calcularSubtotal() {
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }
}