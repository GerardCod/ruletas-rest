package dev.gerardo.microservices.ruletas.apirest.models.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jugadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jugador {
	
	@Id
	private Integer id;
	
	@Column(nullable = false)
	@NotNull(message = "El nombre no puede ser nulo")
	@NotBlank(message = "El nombre no puede estar vacío")
	private String nombre;
	
	@Column(nullable = false)
	@NotNull(message = "El apellido no puede ser nulo")
	@NotBlank(message = "El apellido no puede estar vacío")
	private String apellido;
	
	@Column(nullable = false)
	private Double saldo;
	
	@Column(name = "fecha_creacion", nullable = false)
	private Date fechaCreacion;
	
	@Column(name = "fecha_modificacionn")
	private Date fechaModifcacion;
	
	@OneToMany
	private List<Apuesta> apuestas;
}
