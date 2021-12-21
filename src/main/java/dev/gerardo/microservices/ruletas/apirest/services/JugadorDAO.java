package dev.gerardo.microservices.ruletas.apirest.services;

import java.util.List;
import java.util.Optional;

import dev.gerardo.microservices.ruletas.apirest.models.entities.Jugador;

public interface JugadorDAO {
	Optional<Jugador> crear(Jugador jugador);

	Optional<List<Jugador>> listarJugadores();
	
	Optional<Jugador> buscarPorId(Integer id);
	
	Optional<Jugador> actualizarJugador(Jugador jugadorEncontrado, Jugador jugadorActualizado);

	Boolean revisarSaldoSuficiente(Jugador jugador, Double monto);
}
