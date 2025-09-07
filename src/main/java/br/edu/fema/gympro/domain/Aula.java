package br.edu.fema.gympro.domain;

import br.edu.fema.gympro.domain.enums.DiaDaSemana;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "aula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "modalidade_id")
    private Modalidade modalidade;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Funcionario professor;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "dia_da_semana", nullable = false)
    private DiaDaSemana diaDaSemana;

    @Column(name = "horario", nullable = false)
    private LocalTime horario;

    @Column(name = "numero_inscricoes", nullable = false)
    private Integer numeroInscricoes = 0;

    @Column(name = "maximo_inscricoes", nullable = false)
    private Integer maximoInscricoes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aula aula = (Aula) o;
        return Objects.equals(id, aula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
