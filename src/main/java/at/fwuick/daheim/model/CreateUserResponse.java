package at.fwuick.daheim.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateUserResponse extends Response{
	@NonNull
	private String uuid;
}
