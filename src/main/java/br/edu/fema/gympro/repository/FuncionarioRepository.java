package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.security.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("SELECT f FROM Funcionario f WHERE f.cargo.descricao = 'Professor' ORDER BY f.nome")
    List<Funcionario> findProfessores();

    Funcionario findByUser(User user);
}
