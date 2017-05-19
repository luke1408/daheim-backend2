package at.fwuick.daheim.model;

import lombok.Data;

@Data
public class User {
	private String name;
	private String uuid;
	
	public User(String name){
		this.name = name;
	}
}
