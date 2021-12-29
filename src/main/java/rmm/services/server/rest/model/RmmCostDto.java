package rmm.services.server.rest.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The Class RmmCostDto cost DTO
 */
public class RmmCostDto extends AbstractDto {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -2366647570261401639L;

  /** The overall. */
  @Schema(description = "Sum of all services and devices cost")
  private BigDecimal overallCost;

  /** The services cost. */
  @Schema(description = "List cost details for each service and device ")
  private List<RmmServiceCostDto> servicesCost = new ArrayList<>();


  /**
   * Gets the services cost.
   *
   * @return the services cost
   */
  public List<RmmServiceCostDto> getServicesCost() {
    return servicesCost;
  }

  /**
   * Sets the services cost.
   *
   * @param servicesCost the new services cost
   */
  public void setServicesCost(List<RmmServiceCostDto> servicesCost) {
    this.servicesCost = servicesCost;
  }

  /**
   * Gets the overall cost.
   *
   * @return the overall cost
   */
  public BigDecimal getOverallCost() {
    return overallCost;
  }

  /**
   * Sets the overall cost.
   *
   * @param overallCost the new overall cost
   */
  public void setOverallCost(BigDecimal overallCost) {
    this.overallCost = overallCost;
  }



}
