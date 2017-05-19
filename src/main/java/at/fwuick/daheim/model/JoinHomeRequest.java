package at.fwuick.daheim.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class JoinHomeRequest {
  @NotNull
  private String bssid;
  @NotNull
  private String uuid;

}
