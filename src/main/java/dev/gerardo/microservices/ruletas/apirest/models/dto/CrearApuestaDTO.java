package dev.gerardo.microservices.ruletas.apirest.models.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "tipo"
)
@JsonSubTypes(
  @JsonSubTypes.Type(value = CrearApuestaNumeroDTO.class, name = "numero")
  
)
public class CrearApuestaDTO {

  @NotNull(message = "El monto no puede ser nulo")
  private Double monto;
}
