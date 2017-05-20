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
				Home h =  new Home(rs.getString("bssid"), rs.getLong("id"), rs.getString("name"));
				return h;
		}
	};
	
	public Home findByBssid(String bssid){
		try{
			return jdbcTemplate.queryForObject("select id, bssid, name from homes where bssid = ?", new Object[]{bssid}, mapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	public Home findByBssidSafe(String bssid) throws DaheimException {
		Home h = findByBssid(bssid);
		if(h == null){
			throw new DaheimException("Home not found");
		}
		return h;
	}

	public boolean bssidExists(String bssid) {
		return jdbcTemplate.queryForObject("select count(1) from homes where bssid = ?", new Object[]{bssid}, Integer.class) > 0;
	}

	public void insert(Home home) {
		jdbcTemplate.update("insert into homes (bssid, name) values (?,?)", new Object[]{home.getBssid(), home.getName()});
		Long id = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Long.class);
		home.setId(id);
	}
}
