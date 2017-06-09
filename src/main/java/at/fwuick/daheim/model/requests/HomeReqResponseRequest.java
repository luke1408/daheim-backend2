package at.fwuick.daheim.model.requests;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class HomeReqResponseRequest extends UserContextRequest {
  @NotNull
  private Long user;
  @NotNull
  private Boolean accept;
}
