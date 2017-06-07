package at.fwuick.daheim.model.response;

import java.util.List;

import at.fwuick.daheim.model.RedactedUser;
import lombok.Getter;
import lombok.Setter;

public class ShowHomeResponse extends Response{
	private @Getter @Setter String home_name;
	private @Getter @Setter List<RedactedUser> users;


}
