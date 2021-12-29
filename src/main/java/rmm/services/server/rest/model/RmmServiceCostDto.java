package rmm.services.server.rest.model;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The Class RmmServiceCostDto service cost DTO
 */
public class RmmServiceCostDto extends AbstractDto {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -9064521704348079881L;

  /** The service name. */
  @Schema(description = "Service Name")
  private String serviceName;

  /** The cost. */
  @Schema(description = "Service Cost linked to specific device")
  private BigDecimal cost;

  /**
   * Gets the service name.
   *
   * @return the service name
   */
  public String getServiceName() {
    return serviceName;
  }

  /**
   * Sets the service name.
   *
   * @param serviceName the new service name
   */
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  /**
   * Gets the cost.
   *
   * @return the cost
   */
  public BigDecimal getCost() {
    return cost;
  }

  /**
   * Sets the cost.
   *
   * @param cost the new cost
   */
  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }


}
