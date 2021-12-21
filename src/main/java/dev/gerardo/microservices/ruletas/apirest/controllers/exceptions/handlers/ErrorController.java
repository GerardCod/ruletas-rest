package dev.gerardo.microservices.ruletas.apirest.controllers.exceptions.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.gerardo.microservices.ruletas.apirest.controllers.exceptions.NotFoundException;
import dev.gerardo.microservices.ruletas.apirest.controllers.exceptions.OperationFailedException;

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
	
}
