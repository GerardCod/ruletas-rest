package dev.gerardo.microservices.ruletas.apirest.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 184720620525572217L;
	
	public NotFoundException(String message) {
		super(message);
	}
}
