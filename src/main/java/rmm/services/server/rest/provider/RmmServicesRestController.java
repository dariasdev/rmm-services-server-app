package rmm.services.server.rest.provider;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import rmm.services.server.processor.RmmServicesProcessor;
import rmm.services.server.rest.model.RmmDtoServiceResponse;
import rmm.services.server.rest.model.RmmServiceDto;
import rmm.services.server.rest.model.RmmServiceResponse;


/**
 * The Class RmmServicesRestController CRUD operations for services
 */
@RequestMapping(RmmServicesRestController.URL_PREFIX)
@RestController
@Tag(name = RmmServicesRestController.TAG)
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "A Response which indicates a successful Request"),
    @ApiResponse(responseCode = "400", description = "There was an error encountered processing the Request", content = {@Content(schema = @Schema(implementation = RmmServiceResponse.class))}),
    @ApiResponse(responseCode = "403", description = "The request is not authorized.  Please verify credentials used in the request",
        content = {@Content(schema = @Schema(implementation = RmmServiceResponse.class))}),
    @ApiResponse(responseCode = "404", description = "The record you requested to retreive or update could not be found",
        content = {@Content(schema = @Schema(implementation = RmmServiceResponse.class))}),
    @ApiResponse(responseCode = "500", description = "There was an error encountered processing the Request", content = {@Content(schema = @Schema(implementation = RmmServiceResponse.class))})})
public class RmmServicesRestController {



  /** The Constant VERSION. */
  public static final String VERSION = "1";

  /** The Constant URL_PREFIX. */
  public static final String URL_PREFIX = "ninja-rmm-services/api/v" + VERSION;

  /** The Constant TAG. */
  public static final String TAG = "services-v" + RmmServicesRestController.VERSION;

  /** The Constant DESCRIPTION. */
  public static final String DESCRIPTION = "";

  /** The services processor. */
  @Autowired
  private RmmServicesProcessor servicesProcessor;


  /**
   * Gets the all services.
   *
   * @param customerId the customer id
   * @return the all services
   */
  @GetMapping(value = "/{customerId}/services", produces = "application/json")
  @Operation(description = "Get all selected services for specific Customer Id", summary = "Get all selected services")
  public ResponseEntity<RmmDtoServiceResponse<List<RmmServiceDto>>> getAllServices(@Parameter(description = "Customer Id") @PathVariable Long customerId) {
    RmmDtoServiceResponse<List<RmmServiceDto>> response = servicesProcessor.getServices(customerId);
    return new ResponseEntity<RmmDtoServiceResponse<List<RmmServiceDto>>>(response, response.getStatus().httpStatus());
  }

  /**
   * Adds the service.
   *
   * @param customerId the customer id
   * @param serviceTypeId the service type id
   * @return the response entity
   */
  @PostMapping(value = "/{customerId}/services/{serviceTypeId}", produces = "application/json")
  @Operation(description = " Add a new service for specified customer by providing customer id and service type id.", summary = "Add a new service")
  public ResponseEntity<RmmDtoServiceResponse<RmmServiceDto>> addService(@Parameter(description = "Customer Id") @PathVariable Long customerId,
      @Parameter(description = "Service Type Id, 1 for Antivirus , 2 for Cloudberry , 3 for PSA , 4 for TeamViewer  ",
          schema = @Schema(allowableValues = {"1", "2", "3", "4"})) @PathVariable Long serviceTypeId) {
    RmmDtoServiceResponse<RmmServiceDto> response = servicesProcessor.addService(customerId, serviceTypeId);
    return new ResponseEntity<RmmDtoServiceResponse<RmmServiceDto>>(response, response.getStatus().httpStatus());
  }

  /**
   * Delete service.
   *
   * @param customerId the customer id
   * @param serviceId the service id
   * @return the response entity
   */
  @DeleteMapping(value = "/{customerId}/services/{serviceId}", produces = "application/json")
  @Operation(description = "Delete service by providing customer id and service id.", summary = "Delete a service")
  public ResponseEntity<RmmServiceResponse> deleteService(@PathVariable Long customerId, @PathVariable Long serviceId) {
    RmmServiceResponse response = servicesProcessor.deleteService(customerId, serviceId);
    return new ResponseEntity<RmmServiceResponse>(response, response.getStatus().httpStatus());
  }


}
