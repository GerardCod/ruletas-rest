package dev.gerardo.microservices.ruletas.apirest.models.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CrearApuestaNumeroDTO extends CrearApuestaDTO {
  
  @NotNull(message = "El número no puede ser nulo")
  @Min(value = 0, message = "El número no puede ser menor a 0")
  @Max(value = 36, message = "El número no puede ser mayor a 36")
  private Integer numero;
}
