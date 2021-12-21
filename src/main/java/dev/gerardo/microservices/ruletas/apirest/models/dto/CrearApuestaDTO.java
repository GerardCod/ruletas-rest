package dev.gerardo.microservices.ruletas.apirest.models.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearApuestaDTO {

  @NotNull(message = "El monto no puede ser nulo")
  private Double monto;
}
