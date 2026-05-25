package br.com.sgc.sgc_backend.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "pd_produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pd_id")
	private Long id;
	
	@Column(name = "pd_nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "pd_descricao", columnDefinition = "TEXT")
	private String descricao;
	
	@Column(name = "pd_preco", nullable = false, precision = 10, scale = 2)
	private BigDecimal preco;
	
	@Column(name = "pd_estoque", nullable = false)
	private Integer estoque;
	
	@Builder.Default
	@Column(name = "pd_estoque_minimo", nullable = false)
	private Integer estoqueMinimo = 5;
}
