package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Aula;
import br.edu.fema.gympro.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    @Query("SELECT a FROM Aula a WHERE a.numeroInscricoes < a.maximoInscricoes")
    List<Aula> findAulasComVagas();
    List<Aula> findAllByProfessor(Funcionario funcionario);
}
