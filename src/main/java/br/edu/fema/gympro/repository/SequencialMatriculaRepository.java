package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.SequencialMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequencialMatriculaRepository extends JpaRepository<SequencialMatricula, Long> {
}
