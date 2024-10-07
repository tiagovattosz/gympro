package br.edu.fema.gympro.domain;

import br.edu.fema.gympro.domain.enums.TipoMovimento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotNull
    @Column(name = "data_hora", nullable = false, updatable = false)
    private LocalDateTime dataHora;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_movimento", nullable = false)
    private TipoMovimento tipoMovimento;
}
