package at.fwuick.daheim.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.DaheimExceptionSupplier.Errors;
import at.fwuick.daheim.dao.UserDao;
import at.fwuick.daheim.model.User;
import at.fwuick.daheim.model.UserIdentity;
import at.fwuick.daheim.validators.UserValidator;

@Repository
public class UserValidatedRepository {

	@Autowired
	UserDao userDao;

	@Autowired
	UserValidator validate;


	public User getNoHomeUser(UserIdentity id) throws DaheimException {
		User user = get(id);
		validate.validateNoHome(user);
		return user;
	}

	public User get(UserIdentity id) throws DaheimException {
		try {
			return userDao.get(id);
		} catch (EmptyResultDataAccessException e) {
			throw new DaheimException(Errors.USER_NOT_FOUND);
		}
	}

	public User getHomeUser(UserIdentity id) throws DaheimException {
		User user = get(id);
		validate.validateHasHome(user);
		return user;
	}


}
