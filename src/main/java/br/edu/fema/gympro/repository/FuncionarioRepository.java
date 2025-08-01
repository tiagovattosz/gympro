package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Cargo;
import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.security.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("SELECT f FROM Funcionario f WHERE f.cargo.descricao = 'Professor' ORDER BY f.nome")
    List<Funcionario> findProfessores();

    Funcionario findByUser(User user);
    boolean existsByCpf(String cpf);
    List<Funcionario> findByCargo(Cargo cargo);
    Optional<Funcionario> findByMatricula(String matricula);
}
