package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    @Query("SELECT e FROM Equipamento e WHERE e.emManutencao = true")
    List<Equipamento> findEquipamentosEmManutencao();

    @Query("SELECT e FROM Equipamento e WHERE e.emManutencao = false")
    List<Equipamento> findEquipamentosDisponiveis();
}
