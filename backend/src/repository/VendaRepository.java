package br.com.sgc.sgc_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgc.sgc_backend.domain.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByClienteId(Long clienteId);

    List<Venda> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);
}