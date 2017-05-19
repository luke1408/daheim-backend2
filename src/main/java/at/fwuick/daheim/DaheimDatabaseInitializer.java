package at.fwuick.daheim;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Component
public class DaheimDatabaseInitializer {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @PostConstruct
  public void run() {
    try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
      String sqlfile = "/db-init.sql";
      try (InputStream is = getClass().getResourceAsStream(sqlfile)) {
        if (is == null) {
          throw new FileNotFoundException(sqlfile + " not found");
        }
        ScriptUtils.executeSqlScript(con, new InputStreamResource(is));
      }
    } catch (IOException | SQLException e) {
      throw new RuntimeException("Database Init failed", e);
    }
  }
}
