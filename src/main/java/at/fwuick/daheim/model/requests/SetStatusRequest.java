package at.fwuick.daheim.model.requests;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class SetStatusRequest extends UserContextRequest{
	@NotNull
	private @Getter @Setter Long status;
}
