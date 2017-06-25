package at.fwuick.daheim.model;

import at.fwuick.daheim.utils.Identity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
	private String name;
	private String uuid;
	private Long home;
	private Long id;
	private Long status;
	

	
	public User(String name){
		this.name = name;
	}
	
	public User(String uuid, String name){
		this(name);
		this.uuid = uuid;
	}
}
