package at.fwuick.daheim;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class DaheimExceptionSupplier implements Supplier<DaheimException> {

  public enum Errors {
    HOME_NOT_FOUND("The home could not be found"),
    USER_NOT_FOUND("User could not be found"),
    USER_HAS_HOME_ALREADY("User has a home already"),
    HOME_ALREADY_EXISTS("This home already exists"),
    INVALID_STATUS("The status is invalid"),
    USER_HAS_NO_HOME("User has no home"),
    REQUEST_NOT_FOUND("User has not requested to join this home");

    @Getter
    private final String message;

    Errors(String message) {
      this.message = message;
    }
  }

  @Setter
  Errors error;

  @Override
  public DaheimException get() {
    return new DaheimException(error.getMessage());
  }

}
