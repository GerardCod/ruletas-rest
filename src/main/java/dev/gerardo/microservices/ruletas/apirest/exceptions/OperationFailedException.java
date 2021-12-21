package dev.gerardo.microservices.ruletas.apirest.exceptions;

public class OperationFailedException extends RuntimeException {

	private static final long serialVersionUID = 1739306315032598438L;
	
	public OperationFailedException(String message) {
		super(message);
	}
	
}
