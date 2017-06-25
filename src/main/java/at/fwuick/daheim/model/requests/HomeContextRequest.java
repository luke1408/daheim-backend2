package at.fwuick.daheim.model.requests;

import javax.validation.constraints.NotNull;

import at.fwuick.daheim.model.HomeIdentity;
import lombok.Data;

@Data
public class HomeContextRequest extends UserContextRequest{
  @NotNull
  private String bssid;
  
  public HomeIdentity getHomeIdentity(){
	  return HomeIdentity.bssid(bssid);
  }

}
