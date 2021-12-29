package rmm.services.server.processor;

import java.util.List;
import rmm.services.server.rest.model.RmmDtoServiceResponse;
import rmm.services.server.rest.model.RmmServiceDto;
import rmm.services.server.rest.model.RmmServiceResponse;

/**
 * The Interface RmmServicesProcessor provides service operations
 */
public interface RmmServicesProcessor {

  /**
   * Gets the services.
   *
   * @param customerId the customer id
   * @return the services
   */
  RmmDtoServiceResponse<List<RmmServiceDto>> getServices(Long customerId);

  /**
   * Adds the service.
   *
   * @param customerId the customer id
   * @param serviceTypeId the service type id
   * @return the rmm dto service response
   */
  RmmDtoServiceResponse<RmmServiceDto> addService(Long customerId, Long serviceTypeId);

  /**
   * Delete service.
   *
   * @param customerId the customer id
   * @param serviceId the service id
   * @return the rmm dto service response
   */
  RmmServiceResponse deleteService(Long customerId, Long serviceId);

}
