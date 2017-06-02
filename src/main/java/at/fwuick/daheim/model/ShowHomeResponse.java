package at.fwuick.daheim.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ShowHomeResponse extends Response{
	private @Getter @Setter String home_name;
	private @Getter @Setter List<RedactedUser> users;


}
