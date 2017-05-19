package at.fwuick.daheim.model;

import javax.validation.ConstraintViolation;

import lombok.Getter;
import lombok.Setter;

public class ErrorResponse extends Response {
	private @Getter @Setter String error;
	
	public ErrorResponse(String error){
		super(false);
		this.error = error;
	}

	public ErrorResponse(ConstraintViolation<?> error) {
		this(error.getPropertyPath().toString() + " " + error.getMessage());
	}
}
