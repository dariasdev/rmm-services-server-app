package rmm.services.server.processor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rmm.services.server.persistence.entity.RmmDeviceTypeToServiceCostDao;
import rmm.services.server.persistence.entity.RmmServiceToDeviceCostDao;
import rmm.services.server.persistence.repository.RmmDeviceTypeToServiceCostDaoRepository;
import rmm.services.server.persistence.repository.RmmServiceToDeviceCostDaoRepository;
import rmm.services.server.rest.model.RmmCostDto;
import rmm.services.server.rest.model.RmmDtoServiceResponse;
import rmm.services.server.rest.model.RmmMessage;
import rmm.services.server.rest.model.RmmServiceCostDto;
import rmm.services.server.rest.model.RmmStatus;


/**
 * The Class RmmServicesCostProcessorImpl computes devices and services cost
 */
@Component
public class RmmServicesCostProcessorImpl implements RmmServicesCostProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(RmmServicesCostProcessorImpl.class);

  /** The service to device cost dao repository. */
  @Autowired
  private RmmServiceToDeviceCostDaoRepository serviceToDeviceCostDaoRepository;

  /** The device type to service cost dao repository. */
  @Autowired
  private RmmDeviceTypeToServiceCostDaoRepository deviceTypeToServiceCostDaoRepository;


  /**
   * Compute services cost by combining services and devices cost
   *
   * @param customerId the customer id
   * @return the rmm dto service response
   */
  @Override
  public RmmDtoServiceResponse<RmmCostDto> computeServicesCost(Long customerId) {
    RmmDtoServiceResponse<RmmCostDto> response = new RmmDtoServiceResponse<RmmCostDto>();
    RmmCostDto dto = new RmmCostDto();
    addDevicesCost(customerId, dto.getServicesCost());
    addServicesCost(customerId, dto.getServicesCost());

    // check if there are any cost entries , short circuit if not
    if (dto.getServicesCost().isEmpty()) {
      LOGGER.debug("Service Cost not found for customer {}", customerId);
      return notFoundResponse(customerId);
    }

    // compute the sum of all costs including services and devices
    dto.setOverallCost(dto.getServicesCost().stream().map(service -> service.getCost()).reduce(BigDecimal.ZERO, BigDecimal::add));

    response.setDto(dto);
    return response;
  }

  private void addServicesCost(Long customerId, List<RmmServiceCostDto> services) {
    List<RmmServiceToDeviceCostDao> costDetails = serviceToDeviceCostDaoRepository.getServicesCost(customerId);
    LOGGER.debug("Found Devices Cost records for customer {} {}", customerId, costDetails);
    if (costDetails != null && !costDetails.isEmpty()) {
      services.addAll(costDetails.stream().map(costDao -> {
        RmmServiceCostDto dtoItem = new RmmServiceCostDto();
        dtoItem.setServiceName(costDao.getServiceType().getType());
        dtoItem.setCost(costDao.getCost());
        return dtoItem;
      }).collect(Collectors.toList()));
    }
  }

  private void addDevicesCost(Long customerId, List<RmmServiceCostDto> services) {
    List<RmmDeviceTypeToServiceCostDao> devicesCostRows = deviceTypeToServiceCostDaoRepository.getDevicesCost(customerId);
    LOGGER.debug("Found Services Cost records for customer {} {}", customerId, devicesCostRows);
    if (devicesCostRows != null && !devicesCostRows.isEmpty()) {
      BigDecimal devicesCost = devicesCostRows.stream().map(service -> service.getCost()).reduce(BigDecimal.ZERO, BigDecimal::add);
      RmmServiceCostDto device = new RmmServiceCostDto();
      device.setServiceName("Devices");
      device.setCost(devicesCost);
      services.add(device);
    }
  }

  private RmmDtoServiceResponse<RmmCostDto> notFoundResponse(Long customerId) {
    RmmDtoServiceResponse<RmmCostDto> response = new RmmDtoServiceResponse<RmmCostDto>();
    response.getMessages().add(new RmmMessage(String.format("Record with customer id %s not found", customerId.toString())));
    response.setStatus(RmmStatus.NOT_FOUND);
    return response;
  }

}
