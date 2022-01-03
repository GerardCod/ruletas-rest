package dev.gerardo.microservices.ruletas.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.gerardo.microservices.ruletas.apirest.exceptions.NotFoundException;
import dev.gerardo.microservices.ruletas.apirest.exceptions.OperationFailedException;
import dev.gerardo.microservices.ruletas.apirest.models.entities.Jugador;
import dev.gerardo.microservices.ruletas.apirest.services.JugadorDAO;

@RestController
@RequestMapping("/jugadores")
public class JugadorController {
	
	@Autowired
	private JugadorDAO service;

	/**
	 * Endpoint para crear un jugador para las ruletas.
	 * @param jugador Cuerpo de la petición con los datos del jugador.
	 * @return ResponseEntity con información del jugador creado.
	 * @throws OperationFailedException si no se crea el jugador.
	 */
	@PostMapping
	public ResponseEntity<?> crearJugador(@RequestBody Jugador jugador) {
		
		Optional<Jugador> result = service.crear(jugador);
		
		if (result.isEmpty()) {
			throw new OperationFailedException("No se pudo crear el jugador");
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("jugador", result.get());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Endpoint para listar los jugadores en el sistema.
	 * @return ResponseEntity con la información de los jugadores.
	 * @throws NotFoundException si no hay jugadores en el sistema.
	 */
	@GetMapping
	public ResponseEntity<?> buscarTodos() {
		Optional<List<Jugador>> result = service.listarJugadores();
		
		if (result.isEmpty()) {
			throw new NotFoundException("No hay jugadores en el sistema");
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("jugadores", result.get());
		return ResponseEntity.ok(response);
	}

	/**
	 * Endpoint para consultar la información de un jugador por id.
	 * @param id Identificador numérico del jugador.
	 * @return ResponseEntity con la información del jugador.
	 * @throws NotFoundException si no se encuentra un jugador con el id ingresado.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
		Optional<Jugador> result = service.buscarPorId(id);
	
		if (result.isEmpty()) {
			throw new NotFoundException("No hay un jugador con id " + id);
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("jugador", result.get());
		return ResponseEntity.ok(response);
	}

	/**
	 * Endpoint para actualizar la información de un jugador.
	 * @param id Identificador numérico del jugador.
	 * @param jugador Información actualizada del jugador.
	 * @return ResponseEntity con la información actualizada del jugador.
	 * @throws NotFoundException si no se encuentra un jugador con el id ingresado.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarJugador(@PathVariable Integer id, @RequestBody Jugador jugador) {
		Optional<Jugador> result = service.buscarPorId(id);
		
		if (result.isEmpty()) {
			throw new NotFoundException("No hay jugador con id " + id);
		}
		
		Optional<Jugador> resultUpdate = service.actualizarJugador(result.get(), jugador);
		
		Map<String, Object> response = new HashMap<>();
		response.put("jugadorActualizado", resultUpdate.get());
		return ResponseEntity.ok(response);
	}
}
