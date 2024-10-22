package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.PlanoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanoClienteRepository extends JpaRepository<PlanoCliente, Long> {
    Optional<PlanoCliente> findByCliente(Cliente cliente);
}
