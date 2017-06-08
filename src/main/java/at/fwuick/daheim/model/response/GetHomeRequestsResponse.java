package at.fwuick.daheim.model.response;

import java.util.List;
import java.util.stream.Collectors;

import at.fwuick.daheim.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public class GetHomeRequestsResponse extends Response {

  @Data
  @AllArgsConstructor
  class GHRUser {
    private Long id;
    private String name;
  }

  private @Getter List<GHRUser> requests;

  public GetHomeRequestsResponse(List<User> users) {
    requests = users.stream().map(u -> {
      return new GHRUser(u.getId(), u.getName());
    }).collect(Collectors.toList());
  }

}
