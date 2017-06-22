package at.fwuick.daheim.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.DaheimException;
import at.fwuick.daheim.model.Home;
import at.fwuick.daheim.utils.QuerySelect;

import static at.fwuick.daheim.utils.DaheimUtils.data;
import static at.fwuick.daheim.utils.QueryUtils.select;

@Repository
public class HomeDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String ALL_FIELDS = "id, bssid, name";
	private static final String TABLE_NAME = "homes";
	private static final QuerySelect SELECT = select(ALL_FIELDS, TABLE_NAME);
	
	RowMapper<Home> mapper = new RowMapper<Home>() {

		@Override
		public Home mapRow(ResultSet rs, int rowNum) throws SQLException {			
				Home h =  new Home(rs.getString("bssid"), rs.getLong("id"), rs.getString("name"));
				return h;
		}
	};
	
	public Home findByBssid(String bssid){
		try{
			return jdbcTemplate.queryForObject(SELECT.where("bssid"), data(bssid), mapper);
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
		return jdbcTemplate.queryForObject("select count(1) from homes where bssid = ?", data(bssid), Integer.class) > 0;
	}

	public void insert(Home home) {
		jdbcTemplate.update("insert into homes (bssid, name) values (?,?)", data(home.getBssid(), home.getName()));
		Long id = jdbcTemplate.queryForObject("SELECT max(id) from homes", Long.class);
		home.setId(id);
	}

	public Home get(Long id) throws DataAccessException{
		return jdbcTemplate.queryForObject(SELECT.whereID(), data(id), mapper);

	}
}
