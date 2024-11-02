package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
}
