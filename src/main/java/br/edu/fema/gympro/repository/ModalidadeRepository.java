package br.edu.fema.gympro.repository;

import br.edu.fema.gympro.domain.Modalidade;
import br.edu.fema.gympro.dto.modalidade.ModalidadeMaisPopularDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {
    @Query("""
        SELECT new br.edu.fema.gympro.dto.modalidade.ModalidadeMaisPopularDTO(
            a.modalidade.descricao,
            SUM(a.numeroInscricoes)
        )
        FROM Aula a
        GROUP BY a.modalidade.descricao
        ORDER BY SUM(a.numeroInscricoes) DESC
    """)
    List<ModalidadeMaisPopularDTO> contarModalidadesMaisPopulares();
}
