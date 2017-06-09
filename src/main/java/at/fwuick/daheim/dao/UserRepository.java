package at.fwuick.daheim.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.DaheimExceptionSupplier.Errors;
import at.fwuick.daheim.model.User;

@Repository
public class UserRepository {

  @Autowired
  UserDao userDao;

  private void validateNoHome(User user) throws DaheimException {
    if (user.getHome() != 0) {
      throw new DaheimException(Errors.USER_HAS_HOME_ALREADY);
    }
  }

  private void validateHasHome(User user) throws DaheimException {
    if (user.getHome() == 0) {
      throw new DaheimException(Errors.USER_HAS_NO_HOME);
    }
  }

  public User getNoHomeUser(String uuid) throws DaheimException {
    User user = userDao.findByUuidSafe(uuid);
    validateNoHome(user);
    return user;
  }

  public User getNoHomeUser(long id) throws DaheimException {
    User user = getSafe(id);
    validateNoHome(user);
    return user;
  }

  public User getSafe(long id) throws DaheimException {
    try {
      return userDao.get(id);
    } catch (DataAccessException e) {
      throw new DaheimException(Errors.USER_NOT_FOUND);
    }
  }

  public User getHomeUser(String uuid) throws DaheimException {
    User user = userDao.findByUuidSafe(uuid);
    validateHasHome(user);
    return user;
  }

}
