package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.EntradaSaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaSaidaRepository extends JpaRepository<EntradaSaida, Long> {
}
