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
	
	@Value("${server.port}")
	private Integer port;
	
	@PostMapping
	public ResponseEntity<?> crearJugador(@RequestBody Jugador jugador) {
		
		Optional<Jugador> result = service.crear(jugador);
		
		if (result.isEmpty()) {
			throw new OperationFailedException("No se pudo crear el jugador");
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("jugador", result.get());
		response.put("source", port);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public ResponseEntity<?> buscarTodos() {
		Optional<List<Jugador>> result = service.listarJugadores();
		
		if (result.isEmpty()) {
			throw new NotFoundException("No hay jugadores en el sistema");
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("jugadores", result.get());
		response.put("source", port);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
		Optional<Jugador> result = service.buscarPorId(id);
	
		if (result.isEmpty()) {
			throw new NotFoundException("No hay un jugador con id " + id);
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("jugador", result.get());
		response.put("source", port);
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarJugador(@PathVariable Integer id, @RequestBody Jugador jugador) {
		Optional<Jugador> result = service.buscarPorId(id);
		
		if (result.isEmpty()) {
			throw new NotFoundException("No hay jugador con id " + id);
		}
		
		Optional<Jugador> resultUpdate = service.actualizarJugador(result.get(), jugador);
		
		Map<String, Object> response = new HashMap<>();
		response.put("jugadorActualizado", resultUpdate.get());
		response.put("source", port);
		return ResponseEntity.ok(response);
	}
}
