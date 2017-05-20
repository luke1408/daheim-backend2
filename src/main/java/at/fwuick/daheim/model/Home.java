package at.fwuick.daheim.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Home {
	private String bssid;
	private Long id;
	private String name;
}
