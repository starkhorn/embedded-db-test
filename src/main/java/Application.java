import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


public class Application {
	
	public static void main(String[] args) {
		
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase dataSource = builder
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:create-data.sql")
				.build();
		
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		
		List<Map<String, Object>> results = jdbc.query(
				"{CALL MYSTOREDPROC(?, ?)}", 
				new Object[] { "param1", "param2" }, 
				new RowMapper<Map<String, Object>>() {

					public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
						Map<String, Object> row = new HashMap<String, Object>();
						
						row.put("StatusName", rs.getString(1));
						row.put("StatusCount", rs.getInt(2));
						
						return row;
					}
		});
		
		results.stream().forEach(row -> MapUtils.verbosePrint(System.out, "Result", row));
		
		dataSource.shutdown();
	}
	
}
