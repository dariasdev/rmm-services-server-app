package rmm.services.server.processor;

import rmm.services.server.rest.model.RmmCostDto;
import rmm.services.server.rest.model.RmmDtoServiceResponse;

/**
 * The Interface RmmServicesCostProcessor.
 */
public interface RmmServicesCostProcessor {

  /**
   * Compute services cost.
   *
   * @param customerId the customer id
   * @return the rmm dto service response
   */
  RmmDtoServiceResponse<RmmCostDto> computeServicesCost(Long customerId);

}
