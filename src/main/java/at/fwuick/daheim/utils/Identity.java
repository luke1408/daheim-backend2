package at.fwuick.daheim.utils;

import lombok.Getter;
import lombok.Setter;

public class Identity {
	@Getter 
	String  name;
	@Getter 
	@Setter
	Object value;
	
	public Identity(String name){
		this.name = name;
	}
}
