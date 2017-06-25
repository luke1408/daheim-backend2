package at.fwuick.daheim.model;

import at.fwuick.daheim.utils.Identity;
import lombok.val;

public class UserIdentity extends Identity{
	public UserIdentity(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public static UserIdentity uuid(){
		return new UserIdentity("uuid");
	}
	
	public static UserIdentity id(){
		return new UserIdentity("id");
	}
	
	public static UserIdentity id(long id){
		val uid = id();
		uid.setValue(id);
		return uid;
	}

	public static UserIdentity uuid(String uuid) {
		UserIdentity id = uuid();
		id.setValue(uuid);
		return id;
	}
}