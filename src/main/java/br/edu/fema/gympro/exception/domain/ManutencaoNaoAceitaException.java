package br.edu.fema.gympro.exception.domain;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ManutencaoNaoAceitaException extends RuntimeException {

    public ManutencaoNaoAceitaException(String message) {
        super(message);
    }

}
