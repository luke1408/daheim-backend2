package at.fwuick.daheim.model.requests;

import javax.validation.constraints.NotNull;

import at.fwuick.daheim.model.UserIdentity;
import lombok.Getter;
import lombok.Setter;

public class UserContextRequest {
	@NotNull
	private @Getter @Setter String uuid;
	
	public UserIdentity getUserIdentity(){
		return UserIdentity.uuid(uuid);
	}
}
