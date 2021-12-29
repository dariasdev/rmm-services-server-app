package rmm.services.server.processor;

import java.util.List;
import rmm.services.server.rest.model.RmmDeviceDto;
import rmm.services.server.rest.model.RmmDtoServiceResponse;
import rmm.services.server.rest.model.RmmServiceResponse;

/**
 * The Interface RmmDeviceProcessor provides device operations
 */
public interface RmmDeviceProcessor {

  /**
   * Find device.
   *
   * @param customerId the customer id
   * @param deviceId the device id
   * @return the rmm dto service response
   */
  RmmDtoServiceResponse<RmmDeviceDto> findDevice(Long customerId, Long deviceId);

  /**
   * Adds the device.
   *
   * @param customerId the customer id
   * @param request the request
   * @return the rmm dto service response
   */
  RmmDtoServiceResponse<RmmDeviceDto> addDevice(Long customerId, RmmDeviceDto request);

  /**
   * Delete device.
   *
   * @param customerId the customer id
   * @param deviceId the device id
   * @return the rmm dto service response
   */
  RmmServiceResponse deleteDevice(Long customerId, Long deviceId);

  /**
   * Update device.
   *
   * @param customerId the customer id
   * @param request the request
   * @return the rmm dto service response
   */
  RmmDtoServiceResponse<RmmDeviceDto> updateDevice(Long customerId, RmmDeviceDto request);

  /**
   * Find all devices.
   *
   * @param customerId the customer id
   * @return the rmm dto service response
   */
  RmmDtoServiceResponse<List<RmmDeviceDto>> findAllDevices(Long customerId);

}
