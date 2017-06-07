package at.fwuick.daheim.model.response;

import at.fwuick.daheim.model.CheckHomeObject;
import lombok.Data;

@Data
public class CheckHomeResponse extends Response{
	CheckHomeObject home;
}
