package dev.gerardo.microservices.ruletas.apirest.exceptions.handlers;

import java.util.HashMap;
import java.util.Map;

import dev.gerardo.microservices.ruletas.apirest.exceptions.RuletaNotOpenedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.gerardo.microservices.ruletas.apirest.exceptions.NotFoundException;
import dev.gerardo.microservices.ruletas.apirest.exceptions.OperationFailedException;

@RestControllerAdvice
public class ErrorController {
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleNotFound(NotFoundException e) {
		Map<String, String> response = new HashMap<>();
		response.put("message", e.getMessage());
		return response;
	}
	
	@ExceptionHandler(OperationFailedException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, String> handleOperationFailed(OperationFailedException e) {
		Map<String, String> response = new HashMap<>();
		response.put("message", e.getMessage());
		return response;
	}

	@ExceptionHandler(RuletaNotOpenedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, String> handleRuletaNotOpened(RuletaNotOpenedException e) {
		Map<String, String> response = new HashMap<>();
		response.put("message", e.getMessage());
		return response;
	}
	
}
