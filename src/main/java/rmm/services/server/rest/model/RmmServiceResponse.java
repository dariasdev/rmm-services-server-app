package rmm.services.server.rest.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The Class RmmServiceResponse generic service respknse with no content
 */
public class RmmServiceResponse extends AbstractDto {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -1174927725644103004L;

  /** The messages. */
  @Schema(description = "List of error messages")
  private List<RmmMessage> messages = new ArrayList<>();

  /** The status. */
  @JsonIgnore
  private RmmStatus status = RmmStatus.SUCCESS;


  /**
   * Gets the messages.
   *
   * @return the messages
   */
  public List<RmmMessage> getMessages() {
    return messages;
  }

  /**
   * Sets the messages.
   *
   * @param messages the new messages
   */
  public void setMessages(List<RmmMessage> messages) {
    this.messages = messages;
  }

  /**
   * Gets the status.
   *
   * @return the status
   */
  public RmmStatus getStatus() {
    return status;
  }

  /**
   * Sets the status.
   *
   * @param status the new status
   */
  public void setStatus(RmmStatus status) {
    this.status = status;
  }

  public boolean hasErrors() {
    return !messages.isEmpty();
  }
}
