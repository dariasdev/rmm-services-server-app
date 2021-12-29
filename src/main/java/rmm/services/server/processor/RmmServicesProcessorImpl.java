package rmm.services.server.processor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rmm.services.server.persistence.entity.RmmServiceDao;
import rmm.services.server.persistence.entity.RmmServiceTypeDao;
import rmm.services.server.persistence.repository.RmmServiceDaoRepository;
import rmm.services.server.persistence.repository.RmmServiceTypeDaoRepository;
import rmm.services.server.rest.model.RmmDtoServiceResponse;
import rmm.services.server.rest.model.RmmMessage;
import rmm.services.server.rest.model.RmmServiceDto;
import rmm.services.server.rest.model.RmmServiceResponse;
import rmm.services.server.rest.model.RmmStatus;

/**
 * The Class RmmServicesProcessorImpl provides Services operation
 */
@Component
public class RmmServicesProcessorImpl implements RmmServicesProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(RmmServicesProcessorImpl.class);


  /** The service dao repository. */
  @Autowired
  private RmmServiceDaoRepository serviceDaoRepository;

  /** The service type dao repository. */
  @Autowired
  private RmmServiceTypeDaoRepository serviceTypeDaoRepository;

  /**
   * Gets the services retuen empty list if no services are found
   *
   * @param customerId the customer id
   * @return the services
   */
  @Override
  public RmmDtoServiceResponse<List<RmmServiceDto>> getServices(Long customerId) {
    RmmDtoServiceResponse<List<RmmServiceDto>> response = new RmmDtoServiceResponse<List<RmmServiceDto>>();
    List<RmmServiceDao> results = serviceDaoRepository.findByCustomerId(customerId);
    LOGGER.debug("getServices Found service records for customer {} {}", customerId, results);
    response.setDto(converToDtoList(results));
    return response;
  }

  /**
   * Adds the service, reject if invalid service type is provided
   *
   * @param customerId the customer id
   * @param serviceTypeId the service type id
   * @return the rmm dto service response
   */
  @Override
  public RmmDtoServiceResponse<RmmServiceDto> addService(Long customerId, Long serviceTypeId) {
    RmmDtoServiceResponse<RmmServiceDto> response = new RmmDtoServiceResponse<RmmServiceDto>();
    Optional<RmmServiceTypeDao> serviceType = serviceTypeDaoRepository.findById(serviceTypeId);

    if (serviceType.isEmpty()) {
      LOGGER.debug("addService failed with invalid service type validation device type id {}", serviceTypeId);
      return notValidServiceTypeResponse(serviceTypeId);
    }

    RmmServiceDao existing = serviceDaoRepository.findByCustomerIdAndServiceTypeId(customerId, serviceTypeId);
    if (existing != null) {
      LOGGER.debug("addService failed with service already present for customer {} service type id {}", customerId, serviceTypeId);
      return serviceAlreadyAddedResponse(customerId, serviceTypeId);
    }

    RmmServiceDao dao = new RmmServiceDao();
    dao.setServiceType(serviceType.get());
    dao.setCustomerId(customerId);
    RmmServiceDao result = serviceDaoRepository.save(dao);
    response.setDto(convertToDto(result));
    return response;
  }

  /**
   * Delete service, reject if service for provided id is not present in DB
   *
   * @param customerId the customer id
   * @param serviceId the service id
   * @return the rmm service response
   */
  @Override
  public RmmServiceResponse deleteService(Long customerId, Long serviceId) {
    RmmServiceResponse response = new RmmServiceResponse();
    RmmServiceDao result = serviceDaoRepository.findByCustomerIdAndId(customerId, serviceId);
    if (result == null) {
      LOGGER.debug("deleteService did not find one for customer {} and service id {}", customerId, serviceId);
      return notFoundResponse(customerId, serviceId);
    }
    serviceDaoRepository.delete(result);
    return response;
  }

  private List<RmmServiceDto> converToDtoList(List<RmmServiceDao> daos) {
    return daos.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  private RmmServiceDto convertToDto(RmmServiceDao dao) {
    RmmServiceDto dto = new RmmServiceDto();
    BeanUtils.copyProperties(dao, dto);
    dto.setServiceName(dao.getServiceType().getType());
    dto.setServiceTypeId(dao.getServiceType().getId());
    dto.setId(dao.getId());
    return dto;
  }

  private RmmDtoServiceResponse<RmmServiceDto> notFoundResponse(Long customerId, Long serviceId) {
    RmmDtoServiceResponse<RmmServiceDto> response = new RmmDtoServiceResponse<RmmServiceDto>();
    response.getMessages().add(new RmmMessage(String.format("Record with customer id %s and service id %s not found", customerId.toString(), serviceId.toString())));
    response.setStatus(RmmStatus.NOT_FOUND);
    return response;
  }

  private RmmDtoServiceResponse<RmmServiceDto> notValidServiceTypeResponse(Long serviceTypeId) {
    RmmDtoServiceResponse<RmmServiceDto> response = new RmmDtoServiceResponse<RmmServiceDto>();
    response.getMessages().add(new RmmMessage(String.format("Service type Id %s Not a valid service type id", serviceTypeId.toString())));
    response.setStatus(RmmStatus.REJECTED);
    return response;
  }

  private RmmDtoServiceResponse<RmmServiceDto> serviceAlreadyAddedResponse(Long customerId, Long serviceTypeId) {
    RmmDtoServiceResponse<RmmServiceDto> response = new RmmDtoServiceResponse<RmmServiceDto>();
    response.getMessages().add(new RmmMessage(String.format("Service with id %s already added for customer %s ", serviceTypeId.toString(), customerId.toString())));
    response.setStatus(RmmStatus.REJECTED);
    return response;
  }
}
