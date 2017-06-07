package at.fwuick.daheim.model.requests;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateUserRequest {
	@NotNull
	private String name;
}
