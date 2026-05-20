package br.com.sgc.sgc_backend.domain.model;

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

@Data 	//Gera automaticamente getters e setters, toString, equals...
@NoArgsConstructor 	//Gera construtor vazio
@AllArgsConstructor 	// Gera construtor com todos os campos
@Builder 	//Gera um padrão de construção mais legível
@Entity
@Table(name = "cli_clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cli_id")
	private Long id;
	
	@Column(name = "cli_nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "cli_cpf", nullable = false, unique = true, length = 11)
	private String cpf;
	
	@Column(name = "cli_email", nullable = false, length = 100)
	private String email;
	
	@Column(name = "cli_telefone", length = 20)
	private String telefone;
	
	@Column(name = "cli_endereco", length = 255)
	private String endereco;
	
	
}
