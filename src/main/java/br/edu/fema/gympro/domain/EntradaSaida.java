package br.edu.fema.gympro.domain;

import br.edu.fema.gympro.domain.enums.TipoMovimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "entrada_saida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntradaSaida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "matricula", nullable = false)
    private String matricula;

    @Column(name = "tipo_pessoa", nullable = false)
    private String tipoPessoa;

    @Column(name = "pessoa_id", nullable = false)
    private Long pessoaId;

    @Column(name = "data_hora", nullable = false, updatable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_movimento", nullable = false)
    private TipoMovimento tipoMovimento;
}
