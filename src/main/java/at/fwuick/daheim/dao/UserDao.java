package at.fwuick.daheim.dao;

import static at.fwuick.daheim.utils.DaheimUtils.data;
import static at.fwuick.daheim.utils.QueryUtils.select;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.DaheimExceptionSupplier;
import at.fwuick.daheim.DaheimExceptionSupplier.Errors;
import at.fwuick.daheim.model.User;

@Repository
public class UserDao {

  private static final String TABLE_NAME = "v_status_user";

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  DaheimExceptionSupplier exceptionSupplier;

  public void insert(User user) {
    jdbcTemplate.update("INSERT INTO USERS (name, uuid) VALUES (?, ?)", new Object[] { user.getName(), user.getUuid() });
    user.setId(jdbcTemplate.queryForObject("select max(id) from users", Long.class));
  }

  private RowMapper<User> baseUserMapper = (rs, rowNum) -> {
    User user = new User();
    user.setUuid(rs.getString("uuid"));
    user.setName(rs.getString("name"));
    user.setHome(rs.getLong("home"));
    user.setId(rs.getLong("id"));
    user.setStatus(rs.getLong("status"));
    return user;
  };

  public User findByUuid(String uuid) {

    return jdbcTemplate.queryForObject(select("*", TABLE_NAME).where("uuid"), new Object[] { uuid }, baseUserMapper);
  }

  public User findByUuidSafe(String uuid) throws DaheimException {
    try {
      return findByUuid(uuid);
    } catch (EmptyResultDataAccessException e) {
      throw new DaheimException(Errors.USER_NOT_FOUND);
    }
  }

  public void updateHome(User user) {
    jdbcTemplate.update("update users set home = ? where id = ?", new Object[] { user.getHome(), user.getId() });

  }

  public List<User> findByHome(Long home) {

    return jdbcTemplate.query(select("*", TABLE_NAME).where("home"), new Object[] { home }, baseUserMapper);

  }

  public User get(long id) {
    return jdbcTemplate.queryForObject(select("*", TABLE_NAME).whereID(), data(id), baseUserMapper);
  }
}
