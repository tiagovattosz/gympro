package br.edu.fema.gympro.domain;

import br.edu.fema.gympro.domain.enums.Situacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "manutencao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manutencao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "equipamento_id")
    private Equipamento equipamento;

    @Column(name = "descricao", length = 1000)
    private String descricao;

    @Column(name = "data_soliticacao", nullable = false)
    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private Situacao situacao = Situacao.SOLICITADA;

    @Column(name = "data_resposta")
    private LocalDateTime dataResposta;

    @Column(name = "realizada", nullable = false)
    private Boolean realizada = false;

    @Column(name = "data_realizacao")
    private LocalDateTime dataRealizacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manutencao that = (Manutencao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
