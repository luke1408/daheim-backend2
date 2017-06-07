package at.fwuick.daheim;

import at.fwuick.daheim.DaheimExceptionSupplier.Errors;
import at.fwuick.daheim.model.response.Response;

public class DaheimException extends Exception {
	public DaheimException(String message){
		super(message);
	}

	public DaheimException(Errors error) {
		super(error.getMessage());
	}
}
