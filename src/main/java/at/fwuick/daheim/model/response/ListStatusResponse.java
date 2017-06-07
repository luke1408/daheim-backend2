package at.fwuick.daheim.model.response;

import java.util.List;

import at.fwuick.daheim.model.Status;
import lombok.Getter;
import lombok.Setter;

public class ListStatusResponse extends Response {
	private @Getter @Setter List<Status> status;

	public ListStatusResponse(List<Status> status) {
		super();
		this.status = status;
	}
	
	
	
}
