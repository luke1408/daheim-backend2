package at.fwuick.daheim.dao;

import static at.fwuick.daheim.utils.DaheimUtils.data;
import static at.fwuick.daheim.utils.QueryUtils.select;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.model.UserHomeReq;
import lombok.val;

@Repository
public class HomeRequestDao {

  private static final int MINUTES_BEFORE_EXPIRE = 5;

@Autowired
  JdbcTemplate jdbc;
  
  @Autowired
  UtilDao util;

  private static RowMapper<UserHomeReq> mapper = (rs, rownum) -> {
    val usr = new UserHomeReq();
    usr.setHome(rs.getLong("home"));
    usr.setUser(rs.getLong("user"));
    return usr;
  };

  public List<UserHomeReq> findByUser(Long id) {
    return jdbc.query(select("*", "v_home_requests").where("user"), data(id), mapper);
  }

  public List<UserHomeReq> findByHome(Long id) {
    return jdbc.query(select("*", "v_home_requests").where("home"), data(id), mapper);
  }

  public void deactivate(UserHomeReq req) {
    jdbc.update("update home_requests set active = 0 where active = 1 and user = ? and home = ?", data(req.getUser(), req.getHome()));
  }

  public List<UserHomeReq> findByHomeAndUser(Long home, Long user) {
    return jdbc.query("select * from v_home_requests where home = ? and user = ?", data(home, user), mapper);

  }

	public void insert(UserHomeReq req) {
		jdbc.update("insert into home_requests (user, home, active) values (?,?,1)", req.getUser(), req.getHome());
	}

}
