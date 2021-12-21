package dev.gerardo.microservices.ruletas.apirest.models.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ruleta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "El número de la mesa no puede ser nulo")
	@Column(name = "numero_de_mesa", nullable = false)
	private Integer numeroDeMesa;
	
	@Column(nullable = false)
	private Boolean abierta;
	
	@Column(nullable = false)
	@Min(value = 1000L)
	private Double montoMaximo;
	
	@Column(nullable = false)
	private Integer numeroMin;
	
	@Column(nullable = false)
	private Integer numeroMax;
	
	@OneToMany
	private List<Apuesta> apuestas;
	
	@Column(nullable = false)
	private Date fechaCreacion;
	
	private Date fechaModificacion;
	
	@PrePersist
	private void beforePersist() {
		fechaCreacion = new Date();
		abierta = false;
		numeroMin = 0;
		numeroMax = 36;
	}
	
	@PreUpdate
	private void beforeUpdate() {
		fechaModificacion = new Date();
	}
	
}
