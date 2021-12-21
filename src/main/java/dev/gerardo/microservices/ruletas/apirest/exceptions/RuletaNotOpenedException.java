package dev.gerardo.microservices.ruletas.apirest.exceptions;

public class RuletaNotOpenedException extends RuntimeException {

    private static final long serialVersionUID = 123654789L;

    public RuletaNotOpenedException(String message) {
        super(message);
    }

}
