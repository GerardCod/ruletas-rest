package dev.gerardo.microservices.ruletas.apirest.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.gerardo.microservices.ruletas.apirest.controllers.exceptions.NotFoundException;
import dev.gerardo.microservices.ruletas.apirest.models.dto.CrearApuestaDTO;
import dev.gerardo.microservices.ruletas.apirest.models.entities.Ruleta;
import dev.gerardo.microservices.ruletas.apirest.services.RuletaDAO;

@RestController
@RequestMapping("/ruletas")
public class RuletaController {

	@Autowired
	private RuletaDAO service;
	
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
	public ResponseEntity<?> realizarApuesta(@RequestBody CrearApuestaDTO apuesta) {
		return null;
	}

}
