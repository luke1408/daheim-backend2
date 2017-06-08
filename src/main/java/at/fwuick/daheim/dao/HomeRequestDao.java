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

  @Autowired
  JdbcTemplate jdbc;

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

}
