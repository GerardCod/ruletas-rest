package dev.gerardo.microservices.ruletas.apirest.services;

import java.util.Optional;

import dev.gerardo.microservices.ruletas.apirest.models.dto.CrearApuestaColorDTO;
import dev.gerardo.microservices.ruletas.apirest.models.dto.CrearApuestaDTO;
import dev.gerardo.microservices.ruletas.apirest.models.dto.CrearApuestaNumeroDTO;
import dev.gerardo.microservices.ruletas.apirest.models.entities.Apuesta;
import dev.gerardo.microservices.ruletas.apirest.models.entities.Jugador;
import dev.gerardo.microservices.ruletas.apirest.models.entities.Ruleta;

public interface RuletaDAO {
	
	Integer crearRuleta(Ruleta ruleta);
	Optional<Ruleta> buscarRuletaPorId(Integer id);
	Boolean abrirRuleta(Ruleta ruleta);
	Boolean cerrarRuleta(Ruleta ruleta);
	Optional<Apuesta> realizarApuestaNumero(Ruleta ruleta, Jugador jugador, CrearApuestaNumeroDTO entrada);
	Optional<Apuesta> realizarApuestaColor(Ruleta ruleta, Jugador jugador, CrearApuestaColorDTO entrada);
}
