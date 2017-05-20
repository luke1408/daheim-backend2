package at.fwuick.daheim.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class JoinHomeRequest extends UserContextRequest{
  @NotNull
  private String bssid;

}
