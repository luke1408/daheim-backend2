package at.fwuick.daheim.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.DaheimExceptionSupplier.Errors;
import at.fwuick.daheim.model.Home;
import at.fwuick.daheim.model.User;
import at.fwuick.daheim.model.UserHomeReq;

@Repository
public class UserHomeRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  UserDao userDao;

  @Autowired
  HomeRequestDao requestDao;

  @Autowired
  HomeDao homeDao;

  public long countUserByHome(Home h) {
    return jdbcTemplate.queryForObject("select users from v_users_per_home where home = ?", new Object[] { h.getId() }, Long.class);
  }

  public List<User> getUsersOfHome(Home home) {
    return userDao.findByHome(home.getId());

  }

  public boolean hasHome(User user) {
    return user.getHome() != 0;
  }

  public boolean hasRequest(User user) {
    List<UserHomeReq> request = requestDao.findByUser(user.getId());
    return request.size() > 0;
  }

  public Home getHomeOfUser(User user) {
    return homeDao.get(user.getId());
  }

  public List<UserHomeReq> getHomeRequests(Home home) {
    return requestDao.findByHome(home.getId());
  }

  public void addUserToHome(Home home, User userToAdd) {
    userToAdd.setHome(home.getId());
    userDao.updateHome(userToAdd);
    removeRequest(home, userToAdd);
  }

  public void removeRequest(Home home, User user) {
    UserHomeReq req = new UserHomeReq(user.getId(), home.getId());
    requestDao.deactivate(req);

  }

  public boolean requestsExists(User user, Home home) {

    return requestDao.findByHomeAndUser(home.getId(), user.getId()).size() > 0;
  }

  public void validateRequestExists(User user, Home home) throws DaheimException {
    if (!requestsExists(user, home)) {
      throw new DaheimException(Errors.REQUEST_NOT_FOUND);
    }
  }
}
