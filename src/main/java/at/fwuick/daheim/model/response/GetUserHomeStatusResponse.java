package at.fwuick.daheim.model.response;

import at.fwuick.daheim.model.HomeUserStatus;
import lombok.Getter;

public class GetUserHomeStatusResponse extends Response {
	private @Getter long status;
	
	public GetUserHomeStatusResponse(HomeUserStatus status) {
		this.status = status.ordinal();
	}
	
}
