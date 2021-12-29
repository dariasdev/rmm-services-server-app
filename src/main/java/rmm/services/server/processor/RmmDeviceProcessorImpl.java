package rmm.services.server.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmm.services.server.persistence.entity.RmmDeviceDao;
import rmm.services.server.persistence.entity.RmmDeviceTypeDao;
import rmm.services.server.persistence.repository.RmmDeviceDaoRepository;
import rmm.services.server.persistence.repository.RmmDeviceTypeDaoRepository;
import rmm.services.server.rest.model.RmmDeviceDto;
import rmm.services.server.rest.model.RmmDtoServiceResponse;
import rmm.services.server.rest.model.RmmMessage;
import rmm.services.server.rest.model.RmmServiceResponse;
import rmm.services.server.rest.model.RmmStatus;
import rmm.services.server.rest.model.validator.RmmCreateAction;
import rmm.services.server.rest.model.validator.RmmUpdateAction;

/**
 * The Class RmmDeviceProcessorImpl provides device operations
 */
@Service
public class RmmDeviceProcessorImpl implements RmmDeviceProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(RmmDeviceProcessorImpl.class);

  /** The validator. */
  @Autowired
  private Validator validator;

  /** The device respository. */
  @Autowired
  private RmmDeviceDaoRepository deviceRespository;

  /** The device type repository. */
  @Autowired
  private RmmDeviceTypeDaoRepository deviceTypeRepository;

  /**
   * Find all devices by customer id, if no device is found return empty list
   *
   * @param customerId the customer id
   * @return the rmm dto service response
   */
  @Override
  public RmmDtoServiceResponse<List<RmmDeviceDto>> findAllDevices(Long customerId) {
    RmmDtoServiceResponse<List<RmmDeviceDto>> response = new RmmDtoServiceResponse<List<RmmDeviceDto>>();
    List<RmmDeviceDao> results = deviceRespository.findByCustomerId(customerId);
    LOGGER.debug("findAllDevices Found device records for customer {} {}", customerId, results);
    response.setDto(convertToDtoList(results));
    return response;
  }

  /**
   * Find device by customer id and device id, if no device is found return not found error
   *
   * @param customerId the customer id
   * @param deviceId the device id
   * @return the rmm dto service response
   */
  @Override
  public RmmDtoServiceResponse<RmmDeviceDto> findDevice(Long customerId, Long deviceId) {
    RmmDtoServiceResponse<RmmDeviceDto> response = new RmmDtoServiceResponse<RmmDeviceDto>();
    RmmDeviceDao result = deviceRespository.findByCustomerIdAndId(customerId, deviceId);
    LOGGER.debug("findDevice Found device records for customer {} and device id {} {}", customerId, deviceId, result);
    // check if device was found
    if (result == null) {
      LOGGER.debug("findDevice did not find one for customer {} and device id {}", customerId, deviceId);
      return notFoundResponse(customerId, deviceId);
    }

    // map DAO to DTO
    response.setDto(convertToDto(result));
    return response;
  }

  /**
   * Add a new device to the DB, does jsr-303 validation if validation fails return validation error
   * along with messages if device type is invalid return invalid device error
   *
   * @param customerId the customer id
   * @param request the request
   * @return the rmm dto service response
   */
  @Override
  public RmmDtoServiceResponse<RmmDeviceDto> addDevice(Long customerId, RmmDeviceDto request) {

    RmmDtoServiceResponse<RmmDeviceDto> response = new RmmDtoServiceResponse<RmmDeviceDto>();
    // manual jsr-303 validation with CREATE validation group, id must null
    validateDto(request, response, RmmCreateAction.class);

    if (response.hasErrors()) {
      LOGGER.debug("addDevice failed jsr-303 validation with error {}", response.getMessages());
      return response;
    }

    // validate if device type is valid, if invalid short circuit and return invalid device type error
    Optional<RmmDeviceTypeDao> deviceTypeDao = deviceTypeRepository.findById(request.getDeviceTypeId());
    if (deviceTypeDao.isEmpty()) {
      LOGGER.debug("addDevice failed with invalid device type validation device type id {}", request.getDeviceTypeId());
      return notValidDeviceTypeResponse(request.getDeviceTypeId());
    }

    // map DTO to DAO
    RmmDeviceDao dao = convertToDao(request);
    dao.setCustomerId(customerId);
    dao.setDeviceType(deviceTypeDao.get());
    // Persist data
    RmmDeviceDao result = deviceRespository.save(dao);
    // map DAO to DTO again to return new generated ID
    response.setDto(convertToDto(result));
    return response;
  }



  /**
   * Update device fail if id is not provided, fail if invalid device type is provided fail of old
   * record for id is not found
   *
   * @param customerId the customer id
   * @param request the request
   * @return the rmm dto service response
   */
  @Override
  public RmmDtoServiceResponse<RmmDeviceDto> updateDevice(Long customerId, RmmDeviceDto request) {
    RmmDtoServiceResponse<RmmDeviceDto> response = new RmmDtoServiceResponse<RmmDeviceDto>();
    // manual jsr-303 validation with UPDATE validation group, id must null
    validateDto(request, response, RmmUpdateAction.class);

    if (response.hasErrors()) {
      LOGGER.debug("updateDevice failed jsr-303 validation with error {}", response.getMessages());
      return response;
    }
    // check if device with id is present in DB
    RmmDeviceDao oldData = deviceRespository.findByCustomerIdAndId(customerId, request.getId());
    if (oldData == null) {
      LOGGER.debug("updateDevice did not find one for customer {} and device id {}", customerId, request.getId());
      return notFoundResponse(customerId, request.getId());
    }


    Optional<RmmDeviceTypeDao> deviceTypeDao = deviceTypeRepository.findById(request.getDeviceTypeId());
    // check if device type is valid
    if (deviceTypeDao.isEmpty()) {
      LOGGER.debug("updateDevice failed with invalid device type validation device type id {}", request.getDeviceTypeId());
      return notValidDeviceTypeResponse(request.getDeviceTypeId());
    }

    RmmDeviceDao dao = convertToDao(request);
    dao.setCustomerId(customerId);
    dao.setId(request.getId());
    dao.setCreateDate(oldData.getCreateDate());
    dao.setUpdateDate(oldData.getUpdateDate());
    dao.setDeviceType(deviceTypeDao.get());
    RmmDeviceDao newData = deviceRespository.save(dao);

    response.setDto(convertToDto(newData));
    return response;
  }

  /**
   * Delete device, fail if old record for id is not found
   *
   * @param customerId the customer id
   * @param deviceId the device id
   * @return the rmm service response
   */
  @Override
  public RmmServiceResponse deleteDevice(Long customerId, Long deviceId) {
    RmmServiceResponse response = new RmmServiceResponse();
    RmmDeviceDao result = deviceRespository.findByCustomerIdAndId(customerId, deviceId);
    // check id device with provided id is present in DB
    if (result == null) {
      LOGGER.debug("deleteDevice did not find one for customer {} and device id {}", customerId, deviceId);
      return notFoundResponse(customerId, deviceId);
    }
    deviceRespository.delete(result);
    return response;
  }

  private List<RmmDeviceDto> convertToDtoList(List<RmmDeviceDao> dao) {
    return dao.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  private RmmDeviceDto convertToDto(RmmDeviceDao dao) {
    RmmDeviceDto dto = new RmmDeviceDto();
    BeanUtils.copyProperties(dao, dto);
    dto.setDeviceTypeId(dao.getDeviceType().getId());
    return dto;
  }

  private RmmDeviceDao convertToDao(RmmDeviceDto dto) {
    RmmDeviceDao dao = new RmmDeviceDao();
    BeanUtils.copyProperties(dto, dao);
    return dao;
  }

  private RmmDtoServiceResponse<RmmDeviceDto> notFoundResponse(Long customerId, Long deviceId) {
    RmmDtoServiceResponse<RmmDeviceDto> response = new RmmDtoServiceResponse<RmmDeviceDto>();
    response.getMessages().add(new RmmMessage(String.format("Record with customer id %s and device id %s not found", customerId.toString(), deviceId.toString())));
    response.setStatus(RmmStatus.NOT_FOUND);
    return response;
  }

  private RmmDtoServiceResponse<RmmDeviceDto> notValidDeviceTypeResponse(Long deviceTypeId) {
    RmmDtoServiceResponse<RmmDeviceDto> response = new RmmDtoServiceResponse<RmmDeviceDto>();
    response.getMessages().add(new RmmMessage(String.format("Device type Id %s Not a valid device type id", deviceTypeId.toString())));
    response.setStatus(RmmStatus.REJECTED);
    return response;
  }


  private void validateDto(RmmDeviceDto dto, RmmDtoServiceResponse<RmmDeviceDto> response, Class<?>... groups) {
    List<RmmMessage> messages = new ArrayList<>();
    // jsr-303 manual validation
    Set<ConstraintViolation<RmmDeviceDto>> validationFailures = validator.validate(dto, groups);
    // map constraint violation error to messages
    for (ConstraintViolation<RmmDeviceDto> failure : validationFailures) {
      messages.add(new RmmMessage(failure.getPropertyPath().toString() + " " + failure.getMessage()));
    }

    response.setMessages(messages);
    if (response.hasErrors()) {
      response.setStatus(RmmStatus.REJECTED);
    }
  }



}
