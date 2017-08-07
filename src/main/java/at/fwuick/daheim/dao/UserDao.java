package at.fwuick.daheim.dao;

import static at.fwuick.daheim.utils.QueryUtils.select;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.DaheimExceptionSupplier;
import at.fwuick.daheim.model.User;
import at.fwuick.daheim.model.UserIdentity;

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
    jdbcTemplate.update("insert into user_status (user, status) values (?, 1)", new Object[] { user.getId() });
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

  public void updateHome(User user) {
    jdbcTemplate.update("update users set home = ? where id = ?", new Object[] { user.getHome(), user.getId() });

  }

  public List<User> findByHome(Long home) {

    return jdbcTemplate.query(select("*", TABLE_NAME).where("home"), new Object[] { home }, baseUserMapper);

  }

  public User get(UserIdentity id) {
    return jdbcTemplate.queryForObject(select("*", TABLE_NAME).where(id.getName()), new Object[] { id.getValue() }, baseUserMapper);
  }
}
