package at.fwuick.daheim.validators;

import org.springframework.stereotype.Component;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.DaheimExceptionSupplier.Errors;
import at.fwuick.daheim.model.User;

@Component
public class UserValidator {
	public void validateNoHome(User user) throws DaheimException {
		if (user.getHome() != 0) {
			throw new DaheimException(Errors.USER_HAS_HOME_ALREADY);
		}
	}

	public void validateHasHome(User user) throws DaheimException {
		if (user.getHome() == 0) {
			throw new DaheimException(Errors.USER_HAS_NO_HOME);
		}
	}
}
