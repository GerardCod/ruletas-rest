package dev.gerardo.microservices.ruletas.apirest.services;

import java.util.Optional;

import dev.gerardo.microservices.ruletas.apirest.models.entities.Ruleta;

public interface RuletaDAO {
	
	public Integer crearRuleta(Ruleta ruleta);
	public Optional<Ruleta> buscarRuletaPorId(Integer id);
	public Boolean abrirRuleta(Ruleta ruleta);
	
}
