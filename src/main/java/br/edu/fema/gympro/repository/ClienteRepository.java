package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByPlano (Plano plano);
    Optional<Cliente> findByCpf(String cpf);
    Boolean existsByCpf(String cpf);

    @Query("SELECT c FROM Cliente c WHERE c.dataTerminoAssinatura > CURRENT_DATE")
    List<Cliente> findClientesComAssinaturaAtiva();

    @Query("SELECT c FROM Cliente c WHERE c.dataTerminoAssinatura <= CURRENT_DATE")
    List<Cliente> findClientesComAssinaturaVencida();

    Optional<Cliente> findByMatricula(String matricula);

    Boolean existsByPlano(Plano plano);


}
