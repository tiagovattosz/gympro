package br.edu.fema.gympro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "Planos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "maximo_inscricoes", nullable = false)
    private Integer maximoInscricoes;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "duracao_em_meses", nullable = false)
    private Integer duracaoEmMeses;

    @Column(name = "detalhes", length = 1000)
    private String detalhes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plano plano = (Plano) o;
        return Objects.equals(id, plano.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
