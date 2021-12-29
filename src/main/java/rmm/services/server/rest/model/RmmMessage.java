package rmm.services.server.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The Class RmmMessage simple object to provide detail about failures to consumer can be extended to be drive by resource bundle
 */
public class RmmMessage extends AbstractDto {



  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -6447750740993296714L;
  
  /** The text. */
  @Schema(description = "Detailed Message whenever error occurs")
  private String text;

  public RmmMessage() {}

  public RmmMessage(String text) {
    this.text = text;
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Sets the text.
   *
   * @param text the new text
   */
  public void setText(String text) {
    this.text = text;
  }


}
