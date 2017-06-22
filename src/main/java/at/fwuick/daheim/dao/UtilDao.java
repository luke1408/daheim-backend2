package at.fwuick.daheim.dao;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UtilDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	public LocalDateTime dateNow(){
		return jdbc.queryForObject("select now()", LocalDateTime.class);
	}
}
