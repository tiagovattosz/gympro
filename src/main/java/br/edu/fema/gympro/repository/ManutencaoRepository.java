package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Equipamento;
import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.domain.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    @Query("SELECT m FROM Manutencao m WHERE m.situacao = 'SOLICITADA' AND m.realizada != true ORDER BY m.dataSolicitacao")
    List<Manutencao> findSolicitadas();

    @Query("SELECT m FROM Manutencao m WHERE m.situacao = 'ACEITA' AND m.realizada != true ORDER BY m.dataSolicitacao")
    List<Manutencao> findEmRealizacao();

    @Query("SELECT m FROM Manutencao m WHERE m.realizada = true ORDER BY m.dataSolicitacao")
    List<Manutencao> findRealizadas();

    List<Manutencao> findByFuncionario(Funcionario funcionario);

    List<Manutencao> findByEquipamento(Equipamento equipamento);

}
