package at.fwuick.daheim.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Response {
	
	private @Getter @Setter boolean success;
	
	public Response(){
		this(true);
	}
	
	public Response(boolean success){
		this.success = success;
	}
}
