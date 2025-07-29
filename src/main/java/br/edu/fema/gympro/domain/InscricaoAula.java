package br.edu.fema.gympro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "inscricao_aula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDate dataInscricao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InscricaoAula inscricaoAula = (InscricaoAula) o;
        return Objects.equals(id, inscricaoAula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
