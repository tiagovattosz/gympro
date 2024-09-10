package br.edu.fema.gympro.exception.domain;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjetoNaoEncontrado extends RuntimeException {

    public ObjetoNaoEncontrado(String message) {
        super(message);
    }

}
