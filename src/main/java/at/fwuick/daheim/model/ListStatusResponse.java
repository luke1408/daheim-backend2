package at.fwuick.daheim.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ListStatusResponse extends Response {
	private @Getter @Setter List<Status> status;

	public ListStatusResponse(List<Status> status) {
		super();
		this.status = status;
	}
	
	
	
}
