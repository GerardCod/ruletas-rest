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
import org.springframework.beans.factory.annotation.Value;
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

	/**
	 * Endpoint para crear una nueva ruleta.
	 * @param ruleta Cuerpo de la petición para crear una nueva ruleta
	 * @return ResponseEntity con el id de la ruleta creada.
	 */
	@PostMapping
	public ResponseEntity<Map<String, String>> crearRuleta(@RequestBody Ruleta ruleta) {
		Map<String, String> response = new HashMap<>();
		Integer result = service.crearRuleta(ruleta);
		
		response.put("idCreado", result.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Endpoint para abrir una ruleta por id.
	 * @param ruletaId Id numérico de la ruleta.
	 * @return ResponseEntity con un mensaje de confirmación de la apertura de la ruleta.
	 * @throws NotFoundException si no existe una ruleta con el id ingresado.
	 */
	@PutMapping("/abierta/{ruletaId}")
	public ResponseEntity<Map<String, String>> abrirRuleta(@PathVariable Integer ruletaId) {
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

	/**
	 * Endpoint para realizar apuestas en las ruletas, ya sea por número o por color.
	 * @param apuesta Cuerpo de la petición con la información de la apuesta.
	 * @return ResponseEntity con la información del resultado de la apuesta.
	 * @throws NotFoundException si no se encuentra una ruleta con el id ingresado.
	 * @throws RuletaNotOpenedException si la ruleta es encontrada pero no está abierta aún.
	 */
	@PostMapping("/apuestas")
	public ResponseEntity<Map<String, Object>> realizarApuesta(@Valid @RequestBody CrearApuestaDTO apuesta) {
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
		
		Map<String, Object> response = new HashMap<>();
		response.put("resultado", apuestaOptional.get());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Endpoint para cerrar una ruleta.
	 * @param id Id numérico de la ruleta.
	 * @return ResponseEntity con la información de las apuestas hechas en una ruleta.
	 * @throws NotFoundException si no se encuentra una ruleta con el id ingresado.
	 * @throws OperationFailedException si la ruleta ya está cerrada.
	 */
	@PutMapping("/cerrada/{id}")
	public ResponseEntity<Map<String, Object>> cerrarRuleta(@PathVariable Integer id) {
		Optional<Ruleta> result = service.buscarRuletaPorId(id);

		if (result.isEmpty()) {
			throw new NotFoundException("No hay una ruleta con id " + id);
		}

		if (!service.cerrarRuleta(result.get())) {
			throw new OperationFailedException("No se pudo cerrar la ruleta porque ya estaba cerrada");
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("apuestas", result.get().getApuestas());
		return ResponseEntity.ok(response);
	}

	/**
	 * Endpoint para listar todas las ruletas del sistema.
	 * @return ResponseEntity con el listado de las ruletas.
	 * @throws NotFoundException si no hay ruletas en el sistema.
	 */
	@GetMapping
	public ResponseEntity<Map<String, Object>> buscarTodas() {
		Optional<List<Ruleta>> result = service.buscarTodas();

		if (result.isEmpty()) {
			throw new NotFoundException("No hay ruletas en el sistema");
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("ruletas", result.get());
		return ResponseEntity.ok(response);
	}

}
