package at.fwuick.daheim.model.requests;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateHomeRequest extends JoinHomeRequest{
	@NotNull
	String name;
}
