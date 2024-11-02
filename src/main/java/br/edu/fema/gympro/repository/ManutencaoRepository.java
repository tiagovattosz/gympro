package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    @Query("SELECT m FROM Manutencao m WHERE m.situacao = 'SOLICITADA' AND m.realizada != true ORDER BY m.dataSolicitacao")
    List<Manutencao> findSolicitadas();

    @Query("SELECT m FROM Manutencao m WHERE m.situacao = 'ACEITA' AND m.realizada != true ORDER BY m.dataSolicitacao")
    List<Manutencao> findEmRealizacao();

}
