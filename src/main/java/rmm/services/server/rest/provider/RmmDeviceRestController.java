package rmm.services.server.rest.provider;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import rmm.services.server.processor.RmmDeviceProcessor;
import rmm.services.server.rest.model.RmmDeviceDto;
import rmm.services.server.rest.model.RmmDtoServiceResponse;
import rmm.services.server.rest.model.RmmServiceResponse;


/**
 * The Class RmmDeviceRestController rest controller for CRUD device operations
 */
@RequestMapping(RmmDeviceRestController.URL_PREFIX)
@RestController
@Tag(name = RmmDeviceRestController.TAG)
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "A Response which indicates a successful Request"),
    @ApiResponse(responseCode = "400", description = "There was an error encountered processing the Request", content = {@Content(schema = @Schema(implementation = RmmServiceResponse.class))}),
    @ApiResponse(responseCode = "403", description = "The request is not authorized.  Please verify credentials used in the request",
        content = {@Content(schema = @Schema(implementation = RmmServiceResponse.class))}),
    @ApiResponse(responseCode = "404", description = "The record you requested to retreive or update could not be found",
        content = {@Content(schema = @Schema(implementation = RmmServiceResponse.class))}),
    @ApiResponse(responseCode = "500", description = "There was an error encountered processing the Request", content = {@Content(schema = @Schema(implementation = RmmServiceResponse.class))})})
public class RmmDeviceRestController {



  /** The Constant VERSION. */
  public static final String VERSION = "1";

  /** The Constant URL_PREFIX. */
  public static final String URL_PREFIX = "ninja-rmm-services/api/v" + VERSION;

  /** The Constant TAG. */
  public static final String TAG = "devices-v" + RmmDeviceRestController.VERSION;

  /** The Constant DESCRIPTION. */
  public static final String DESCRIPTION = "";

  /** The devices service. */
  @Autowired
  private RmmDeviceProcessor devicesService;

  /**
   * Gets the all devices.
   *
   * @param customerId the customer id
   * @return the all devices
   */
  @GetMapping(value = "/{customerId}/devices", produces = "application/json")
  @Operation(description = "Get all devices for specific customer id", summary = "Get all devices")
  public ResponseEntity<RmmDtoServiceResponse<List<RmmDeviceDto>>> getAllDevices(@Parameter(description = "Customer Id") @PathVariable Long customerId) {
    RmmDtoServiceResponse<List<RmmDeviceDto>> response = devicesService.findAllDevices(customerId);
    return new ResponseEntity<RmmDtoServiceResponse<List<RmmDeviceDto>>>(response, response.getStatus().httpStatus());
  }

  /**
   * Gets the device.
   *
   * @param customerId the customer id
   * @param deviceId the device id
   * @return the device
   */
  @GetMapping(value = "/{customerId}/devices/{deviceId}", produces = "application/json")
  @Operation(description = "Get device by providing customer id and device id.", summary = "Get a device by id")
  public ResponseEntity<RmmDtoServiceResponse<RmmDeviceDto>> getDevice(@Parameter(description = "Customer Id") @PathVariable Long customerId,
      @Parameter(description = "Unique Device Id") @PathVariable Long deviceId) {
    RmmDtoServiceResponse<RmmDeviceDto> response = devicesService.findDevice(customerId, deviceId);
    return new ResponseEntity<RmmDtoServiceResponse<RmmDeviceDto>>(response, response.getStatus().httpStatus());
  }

  /**
   * Adds the device.
   *
   * @param customerId the customer id
   * @param request the request
   * @return the response entity
   */
  @PostMapping(value = "/{customerId}/devices", produces = "application/json", consumes = "application/json")
  @Operation(description = " Add a new device for specified customer.", summary = "Add a new device")
  public ResponseEntity<RmmDtoServiceResponse<RmmDeviceDto>> addDevice(@Parameter(description = "Customer Id") @PathVariable Long customerId, @RequestBody RmmDeviceDto request) {
    RmmDtoServiceResponse<RmmDeviceDto> response = devicesService.addDevice(customerId, request);
    return new ResponseEntity<RmmDtoServiceResponse<RmmDeviceDto>>(response, response.getStatus().httpStatus());
  }

  /**
   * Update device.
   *
   * @param customerId the customer id
   * @param request the request
   * @return the response entity
   */
  @PutMapping(value = "/{customerId}/devices", produces = "application/json", consumes = "application/json")
  @Operation(description = " Update existing device by providing customer id and device id.", summary = "update a device")
  public ResponseEntity<RmmDtoServiceResponse<RmmDeviceDto>> updateDevice(@Parameter(description = "Customer Id") @PathVariable Long customerId, @RequestBody RmmDeviceDto request) {
    RmmDtoServiceResponse<RmmDeviceDto> response = devicesService.updateDevice(customerId, request);
    return new ResponseEntity<RmmDtoServiceResponse<RmmDeviceDto>>(response, response.getStatus().httpStatus());
  }

  /**
   * Delete device.
   *
   * @param customerId the customer id
   * @param deviceId the device id
   * @return the response entity
   */
  @DeleteMapping(value = "/{customerId}/devices/{deviceId}", produces = "application/json")
  @Operation(description = "Delete device by providing customer id and device id.", summary = "delete a device")
  public ResponseEntity<RmmServiceResponse> deleteDevice(@Parameter(description = "Customer Id") @PathVariable Long customerId,
      @Parameter(description = "Unique Device Id") @PathVariable Long deviceId) {
    RmmServiceResponse response = devicesService.deleteDevice(customerId, deviceId);
    return new ResponseEntity<RmmServiceResponse>(response, response.getStatus().httpStatus());
  }


}
