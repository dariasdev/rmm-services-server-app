package rmm.services.server.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The Class RmmDtoServiceResponse generic service response with content
 *
 * @param <T> the generic type
 */
public class RmmDtoServiceResponse<T> extends RmmServiceResponse {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -1174927725644103004L;

  /** The dto. */
  @Schema(description = "Response Content")
  private T dto;

  /**
   * Gets the dto.
   *
   * @return the dto
   */
  public T getDto() {
    return dto;
  }

  /**
   * Sets the dto.
   *
   * @param dto the new dto
   */
  public void setDto(T dto) {
    this.dto = dto;
  }

}
