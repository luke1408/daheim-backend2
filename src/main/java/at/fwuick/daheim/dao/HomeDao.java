package at.fwuick.daheim.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.model.Home;

@Repository
public class HomeDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	RowMapper<Home> mapper = new RowMapper<Home>() {

		@Override
		public Home mapRow(ResultSet rs, int rowNum) throws SQLException {			
				return new Home(rs.getString("bssid"), rs.getLong("id"));
		}
	};
	
	public Home findByBssid(String bssid){
		return jdbcTemplate.queryForObject("select id, bssid from homes where bssid = ?", new Object[]{bssid}, mapper);
	}

	public Home findByBssidSafe(String bssid) throws DaheimException {
		try{
			return findByBssid(bssid);
		}catch(EmptyResultDataAccessException E){
			throw new DaheimException("Home not found");
		}
	}
}
