package at.fwuick.daheim.dao;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.DaheimExceptionSupplier;
import at.fwuick.daheim.DaheimExceptionSupplier.Errors;
import at.fwuick.daheim.model.Home;
import at.fwuick.daheim.model.User;
import static at.fwuick.daheim.utils.DaheimUtils.data;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Repository
public class UserDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	  @Autowired
	  DaheimExceptionSupplier exceptionSupplier;

	public void insert(User user) {
		jdbcTemplate.update("INSERT INTO USERS (name, uuid) VALUES (?, ?)", new Object[]{user.getName(), user.getUuid()});
		user.setId(jdbcTemplate.queryForObject("select max(id) from users", Long.class));
	}
	
	private RowMapper<User> baseUserMapper = (rs, rowNum) ->{
		User user = new User();
		user.setUuid(rs.getString("uuid"));
		user.setName(rs.getString("name"));
		user.setHome(rs.getLong("home"));
		user.setId(rs.getLong("id"));
		user.setStatus(rs.getLong("status"));
		return user;
	};
	
	
	public User findByUuid(String uuid) {
		return jdbcTemplate.queryForObject("select * from v_status_user where uuid = ?", new Object[]{uuid}, baseUserMapper);
	}

	public User findByUuidSafe(String uuid) throws DaheimException {
		try{
		return findByUuid(uuid);
	  }catch(EmptyResultDataAccessException e){
		  throw new DaheimException(Errors.USER_NOT_FOUND);
	  }
	}

	public void updateHome(User user) {
		jdbcTemplate.update("update users set home = ? where id = ?", new Object[]{user.getHome(), user.getId()});
		
	}

	public List<User> findByHome(Long home) {

			return jdbcTemplate.query("select * from v_status_user where home = ?", new Object[]{home}, baseUserMapper);

	}
}
