package at.fwuick.daheim.model;

import lombok.Data;

@Data
public class RedactedUser {
	private String name;
	private Long status;
	
	public RedactedUser(User user){
		this.name = user.getName();
		this.status = user.getStatus();
	}
}
