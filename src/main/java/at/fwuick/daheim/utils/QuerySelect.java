package at.fwuick.daheim.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuerySelect {
	private String fields;
	private String tableName;
	
	private static final String SELECT_PATTERN = "select %s from %s";
	private static final String SELECT_WHERE_PATTER = SELECT_PATTERN + " where %s = ?";
	
	
	public String where(String field){
		return String.format(SELECT_WHERE_PATTER, fields, tableName, field);
	}
	
	public String whereID(){
		return where("id");
	}

	public String all() {
		return String.format(SELECT_PATTERN, fields, tableName);
	}
}
