package dev.gerardo.microservices.ruletas.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dev.gerardo.microservices.ruletas.apirest.exceptions.OperationFailedException;
import dev.gerardo.microservices.ruletas.apirest.exceptions.RuletaNotOpenedException;
import dev.gerardo.microservices.ruletas.apirest.exceptions.UnsufficientSaldoException;
import dev.gerardo.microservices.ruletas.apirest.models.dto.CrearApuestaColorDTO;
import dev.gerardo.microservices.ruletas.apirest.models.dto.CrearApuestaNumeroDTO;
import dev.gerardo.microservices.ruletas.apirest.models.entities.Apuesta;
import dev.gerardo.microservices.ruletas.apirest.models.entities.Jugador;
import dev.gerardo.microservices.ruletas.apirest.services.JugadorDAO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.gerardo.microservices.ruletas.apirest.exceptions.NotFoundException;
import dev.gerardo.microservices.ruletas.apirest.models.dto.CrearApuestaDTO;
import dev.gerardo.microservices.ruletas.apirest.models.entities.Ruleta;
import dev.gerardo.microservices.ruletas.apirest.services.RuletaDAO;

import javax.validation.Valid;

@RestController
@RequestMapping("/ruletas")
public class RuletaController {

	@Autowired
	private RuletaDAO service;

	@Autowired
	private JugadorDAO jugadorService;
	
	@PostMapping
	public ResponseEntity<?> crearRuleta(@RequestBody Ruleta ruleta) {
		Map<String, String> response = new HashMap<>();
		Integer result = service.crearRuleta(ruleta);
		
		response.put("idCreado", result.toString());
			
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/abierta/{ruletaId}")
	public ResponseEntity<?> abrirRuleta(@PathVariable Integer ruletaId) {
		Optional<Ruleta> result = service.buscarRuletaPorId(ruletaId);
	
		if (result.isEmpty()) {
			throw new NotFoundException("No existe una ruleta con id " + ruletaId);
		}
		
		Boolean updateResult = service.abrirRuleta(result.get());
		
		Map<String, String> response = new HashMap<>();
		
		if (updateResult) {
			response.put("message", "Se abrió la ruleta con id " + ruletaId);			
		} else {
			response.put("message", "No se pudo abrir la ruleta con id " + ruletaId);
		}
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/apuestas")
	public ResponseEntity<?> realizarApuesta(@Valid @RequestBody CrearApuestaDTO apuesta) {
		Integer ruletaId = apuesta.getRuletaId();
		Optional<Ruleta> ruleta = service.buscarRuletaPorId(ruletaId);

		if (ruleta.isEmpty()) {
			throw new NotFoundException("No hay una ruleta con id " + ruletaId);
		}

		if (!ruleta.get().getAbierta()) {
			throw new RuletaNotOpenedException("La ruleta con id " + ruletaId + " aún no ha sido abierta");
		}

		Integer jugadorId = apuesta.getJugadorId();
		Optional<Jugador> jugador = jugadorService.buscarPorId(jugadorId);

		if (jugador.isEmpty()) {
			throw new NotFoundException("No hay un jugador con id " + jugadorId);
		}

		if (!jugadorService.revisarSaldoSuficiente(jugador.get(), apuesta.getMonto())) {
			throw new UnsufficientSaldoException("El jugador con id " + jugadorId + " no tiene saldo suficiente para esta apuesta");
		}

		Optional<Apuesta> apuestaOptional = null;

		if (apuesta instanceof CrearApuestaNumeroDTO) {
			apuestaOptional = service.realizarApuestaNumero(ruleta.get(), jugador.get(), (CrearApuestaNumeroDTO) apuesta);
		} else {
			apuestaOptional = service.realizarApuestaColor(ruleta.get(), jugador.get(), (CrearApuestaColorDTO) apuesta);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(apuestaOptional.get());
	}

	@PutMapping("/cerrada/{id}")
	public ResponseEntity<List<Apuesta>> cerrarRuleta(@PathVariable Integer id) {
		Optional<Ruleta> result = service.buscarRuletaPorId(id);

		if (result.isEmpty()) {
			throw new NotFoundException("No hay una ruleta con id " + id);
		}

		if (!service.cerrarRuleta(result.get())) {
			throw new OperationFailedException("No se pudo cerrar la ruleta porque ya estaba cerrada");
		}

		return ResponseEntity.ok(result.get().getApuestas());
	}

	@GetMapping
	public ResponseEntity<List<Ruleta>> buscarTodas() {
		Optional<List<Ruleta>> result = service.buscarTodas();

		if (result.isEmpty()) {
			throw new NotFoundException("No hay ruletas en el sistema");
		}

		return ResponseEntity.ok(result.get());
	}

}
