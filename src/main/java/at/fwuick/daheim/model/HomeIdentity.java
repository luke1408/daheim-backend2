package at.fwuick.daheim.model;

import at.fwuick.daheim.utils.Identity;

public class HomeIdentity extends Identity{

	private HomeIdentity(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public static HomeIdentity id(long id){
		HomeIdentity hid = new HomeIdentity("id");
		hid.setValue(id);
		return hid;
	}
	
	public static HomeIdentity bssid(String bssid){
		HomeIdentity hid = new HomeIdentity("bssid");
		hid.setValue(bssid);
		return hid;
	}

}
