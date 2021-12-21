package dev.gerardo.microservices.ruletas.apirest.models.dto;

import javax.validation.constraints.NotNull;

import dev.gerardo.microservices.ruletas.apirest.models.enums.RuletaColor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CrearApuestaColorDTO extends CrearApuestaDTO {
  
  @NotNull(message = "El color de la casilla no puede ser nulo")
  private RuletaColor color;
}
