package at.fwuick.daheim.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateUserRequest {
	@NotNull
	private String name;
}
