package at.fwuick.daheim.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.model.Home;
import at.fwuick.daheim.model.User;

@Repository
public class UserHomeRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserDao userDao;
	
	public long countUserByHome(Home h){
		return jdbcTemplate.queryForObject("select users from v_users_per_home where home = ?", new Object[]{h.getId()}, Long.class);
	}

	public List<User> getUsersOfHome(Home home) {
		return userDao.findByHome(home.getId());
		
	}
}
