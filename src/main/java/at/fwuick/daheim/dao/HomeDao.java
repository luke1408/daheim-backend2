package at.fwuick.daheim.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import at.fwuick.daheim.model.Home;

@Repository
public class HomeDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	RowMapper<Optional<Home>> mapper = new RowMapper<Optional<Home>>() {

		@Override
		public Optional<Home> mapRow(ResultSet rs, int rowNum) throws SQLException {
			try{				
				return Optional.of(new Home(rs.getString("bssid"), rs.getLong("id")));
			}catch(Exception e){
				return Optional.empty();
			}
		}
	};
	
	public Optional<Home> findByBssid(String bssid){
		return jdbcTemplate.queryForObject("select id, bssid from homes where bssid = ?", mapper);
	}
}
