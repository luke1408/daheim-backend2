package at.fwuick.daheim.dao;

import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;

import at.fwuick.daheim.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Repository
public class UserDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void insert(User user) {
		jdbcTemplate.update("INSERT INTO USERS (name, uuid) VALUES (?, ?)", new Object[]{user.getName(), user.getUuid()});
	}
}
