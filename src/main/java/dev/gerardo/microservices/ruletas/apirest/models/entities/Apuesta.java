package dev.gerardo.microservices.ruletas.apirest.models.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.gerardo.microservices.ruletas.apirest.models.enums.EstadoApuesta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apuesta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "El monto no puede ser nulo")
	private Double monto;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonIgnore
	private Ruleta ruleta;
	
	@Enumerated(EnumType.STRING)
	private EstadoApuesta estado;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonIgnore
	private Jugador jugador;
}
