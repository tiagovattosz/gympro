package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.EntradaSaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntradaSaidaRepository extends JpaRepository<EntradaSaida, Long> {
    @Query("SELECT es FROM EntradaSaida es WHERE es.dataHora BETWEEN :dataInicio AND :dataFinal ORDER BY es.dataHora DESC")
    List<EntradaSaida> findByDataHoraBetween(@Param("dataInicio") LocalDateTime dataInicio,
                                             @Param("dataFinal") LocalDateTime dataFinal);

    @Query("SELECT es FROM EntradaSaida es WHERE es.dataHora BETWEEN :dataInicio AND :dataFinal AND es.matricula = :matricula ORDER BY es.dataHora DESC")
    List<EntradaSaida> findByDataHoraBetweenAndMatricula(@Param("dataInicio") LocalDateTime dataInicio,
                                                         @Param("dataFinal") LocalDateTime dataFinal,
                                                         @Param("matricula") String matricula);


    List<EntradaSaida> findAllByOrderByDataHoraDesc();


//    @Query("SELECT es FROM EntradaSaida es WHERE es.cliente = :cliente")
//    List<EntradaSaida> findByCliente(@Param("cliente") Cliente cliente);
}
