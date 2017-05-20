package at.fwuick.daheim.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateHomeRequest extends JoinHomeRequest{
	@NotNull
	String name;
}
