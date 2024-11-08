package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.InscricaoAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InscricaoAulaRepository extends JpaRepository<InscricaoAula, Long> {
    @Query("SELECT inscricao.cliente.nome FROM InscricaoAula inscricao WHERE inscricao.aula.id = :id")
    List<String> listarAlunosInscritos(@Param("id") Long id);
}
