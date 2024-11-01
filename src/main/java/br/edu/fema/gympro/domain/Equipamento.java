package br.edu.fema.gympro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(schema = "public", name = "equipamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "em_manutencao", nullable = false)
    private Boolean emManutencao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipamento equipamento = (Equipamento) o;
        return Objects.equals(id, equipamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
