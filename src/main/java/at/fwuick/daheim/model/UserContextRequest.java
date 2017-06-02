package at.fwuick.daheim.model;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class UserContextRequest {
	@NotNull
	private @Getter @Setter String uuid;
}
