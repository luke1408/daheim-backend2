package at.fwuick.daheim.dao;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import at.fwuick.daheim.model.Status;
import at.fwuick.daheim.model.User;
import static at.fwuick.daheim.utils.DaheimUtils.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class StatusDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void setStatus(User user, Status status){
		int minutesToExpiration = 15;
		String minutesStr = String.format("00:%d:00", minutesToExpiration);
		jdbcTemplate.update("insert into user_status (user, status, expiration_date) values (?,?,addtime(now(), ?))", 
				data(user.getId(), status.getId(), minutesStr));
		
	}
	
	public Status getStatus(long id){
		return jdbcTemplate.queryForObject("select name from status where id = ?",  data(id), 
				new RowMapper<Status>() {

					@Override
					public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
						Status status = new Status();
						status.setId(id);
						status.setName(rs.getString("name"));
						return status;
					}
				});
	}

	public List<Status> getAllStatus() {
		return jdbcTemplate.query("select id, name from status", 
				new RowMapper<Status>() {

					@Override
					public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
						Status status = new Status();
						status.setId(rs.getLong("id"));
						status.setName(rs.getString("name"));
						return status;
					}
				});
	}
}
