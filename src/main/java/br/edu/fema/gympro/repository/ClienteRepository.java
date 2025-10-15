package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.domain.Plano;
import br.edu.fema.gympro.dto.cliente.ClientesPorPlanoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByPlano(Plano plano);

    Optional<Cliente> findByCpf(String cpf);

    Boolean existsByCpf(String cpf);

    @Query("SELECT c FROM Cliente c WHERE c.dataTerminoAssinatura > CURRENT_DATE")
    List<Cliente> findClientesComAssinaturaAtiva();

    @Query("SELECT c FROM Cliente c WHERE c.dataTerminoAssinatura <= CURRENT_DATE")
    List<Cliente> findClientesComAssinaturaVencida();

    Optional<Cliente> findByMatricula(String matricula);

    Boolean existsByPlano(Plano plano);

    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.plano IS NOT NULL AND c.dataTerminoAssinatura >= :hoje")
    Long countClientesComAssinaturaAtiva(LocalDate hoje);

    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.plano IS NULL OR c.dataTerminoAssinatura < :hoje")
    Long countClientesComAssinaturaVencida(LocalDate hoje);

    @Query("""
                SELECT new br.edu.fema.gympro.dto.cliente.ClientesPorPlanoDTO(
                    c.plano.descricao,
                    COUNT(c)
                )
                FROM Cliente c
                WHERE c.plano IS NOT NULL
                GROUP BY c.plano.descricao
                ORDER BY COUNT(c) DESC
            """)
    List<ClientesPorPlanoDTO> contarClientesPorPlano();
}
