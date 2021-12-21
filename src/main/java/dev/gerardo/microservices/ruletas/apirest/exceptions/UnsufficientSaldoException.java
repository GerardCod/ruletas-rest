package dev.gerardo.microservices.ruletas.apirest.exceptions;

public class UnsufficientSaldoException extends RuntimeException {
    private static final long serialVersionUID = 789654123L;

    public UnsufficientSaldoException(String message) {
        super(message);
    }

}
