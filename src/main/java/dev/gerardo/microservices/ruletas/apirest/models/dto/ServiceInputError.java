package dev.gerardo.microservices.ruletas.apirest.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInputError {
    private String message;
    private String field;
    private String invalidValue;

    public static ServiceInputError fromFieldError(FieldError error) {
        return new ServiceInputError(error.getDefaultMessage(), error.getField(), error.getRejectedValue().toString());
    }
}
