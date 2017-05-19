package at.fwuick.daheim.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
	private String name;
	private String uuid;
	private Long home;
	private Long id;
	
	public User(String name){
		this.name = name;
	}
}
