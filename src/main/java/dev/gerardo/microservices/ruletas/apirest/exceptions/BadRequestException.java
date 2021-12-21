package dev.gerardo.microservices.ruletas.apirest.exceptions;

import dev.gerardo.microservices.ruletas.apirest.models.dto.ServiceInputError;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class BadRequestException extends RuntimeException {
    private List<ServiceInputError> errors;

    public BadRequestException(String message, List<FieldError> errors) {
        super(message);
        this.errors = errors.stream().map(ServiceInputError::fromFieldError).collect(Collectors.toList());
    }
}
