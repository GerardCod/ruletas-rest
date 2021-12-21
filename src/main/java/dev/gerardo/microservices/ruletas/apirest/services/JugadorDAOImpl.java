package dev.gerardo.microservices.ruletas.apirest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.gerardo.microservices.ruletas.apirest.models.entities.Jugador;
import dev.gerardo.microservices.ruletas.apirest.repositories.JugadorRepository;

@Service
public class JugadorDAOImpl implements JugadorDAO {
	
	@Autowired
	private JugadorRepository repository;

	@Override
	public Optional<Jugador> crear(Jugador jugador) {
		Jugador result = repository.save(jugador);
		return Optional.of(result);
	}

	@Override
	public Optional<List<Jugador>> listarJugadores() {
		List<Jugador> result = (List<Jugador>) repository.findAll();
		
		if (!result.isEmpty()) {
			return Optional.of(result);
		}
		
		return Optional.empty();
	}

	@Override
	public Optional<Jugador> buscarPorId(Integer id) {
		Optional<Jugador> result = repository.findById(id);
		return result;
	}

	@Override
	public Optional<Jugador> actualizarJugador(Jugador jugadorEncontrado, Jugador jugadorActualizado) {
		jugadorEncontrado.setNombre(jugadorActualizado.getNombre());
		jugadorEncontrado.setApellido(jugadorActualizado.getApellido());
		
		Jugador result = repository.save(jugadorEncontrado);
		return Optional.of(result);
	}

	@Override
	public Boolean revisarSaldoSuficiente(Jugador jugador, Double monto) {
		return (jugador.getSaldo() - monto) > 0;
	}

}
