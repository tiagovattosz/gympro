package br.edu.fema.gympro.exception.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ExcecaoPadrao {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private Object message;
    private String path;
}
