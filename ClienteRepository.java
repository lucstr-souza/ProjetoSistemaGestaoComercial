package br.com.sgc.sgc_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgc.sgc_backend.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{ 
	
	@Query
	("SELECT COUNT(v) > 0 FROM Venda v WHERE v.cliente.id = :clienteId")
	boolean clientePossuiVendas(@Param("clienteId") Long clienteId);

	boolean existsByCpf(String cpf);
}
