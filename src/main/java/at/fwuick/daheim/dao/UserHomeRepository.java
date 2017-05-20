package at.fwuick.daheim.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.model.Home;

@Repository
public class UserHomeRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public long countUserByHome(Home h){
		return jdbcTemplate.queryForObject("select users from v_users_per_home where home = ?", new Object[]{h.getId()}, Long.class);
	}
}
