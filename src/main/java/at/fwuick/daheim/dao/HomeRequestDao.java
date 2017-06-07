package at.fwuick.daheim.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.model.UserHomeReq;
import lombok.val;

import static at.fwuick.daheim.utils.QueryUtils.select;
import static at.fwuick.daheim.utils.DaheimUtils.data;

@Repository
public class HomeRequestDao {

	@Autowired
	JdbcTemplate jdbc;
	
	
	public List<UserHomeReq> findByUser(Long id) {
		return jdbc.query(select("*", "v_home_requests").where("user"), data(id), (rs, rownum) -> {
			val usr = new UserHomeReq();
			usr.setHome(rs.getLong("home"));
			usr.setUser(rs.getLong("user"));
			return usr;
		});
	}

}
