package rmm.services.server.rest.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import rmm.services.server.processor.RmmServicesCostProcessor;
import rmm.services.server.rest.model.RmmCostDto;
import rmm.services.server.rest.model.RmmDtoServiceResponse;


/**
 * The Class RmmServicesCostRestController provide Get end point to list services and devices cost
 */
@RequestMapping(RmmServicesCostRestController.URL_PREFIX)
@RestController
@Tag(name = RmmServicesCostRestController.TAG)
public class RmmServicesCostRestController {



  /** The Constant VERSION. */
  public static final String VERSION = "1";

  /** The Constant URL_PREFIX. */
  public static final String URL_PREFIX = "ninja-rmm-services/api/v" + VERSION;

  /** The Constant TAG. */
  public static final String TAG = "cost-calculator-v" + RmmServicesCostRestController.VERSION;

  /** The Constant DESCRIPTION. */
  public static final String DESCRIPTION = "";

  /** The services cost processor. */
  @Autowired
  private RmmServicesCostProcessor servicesCostProcessor;


  /**
   * Gets the services cost. provide Get end point to list services and devices cost
   *
   * @param customerId the customer id
   * @return the services cost
   */
  @GetMapping(value = "/{customerId}/cost", produces = "application/json")
  @Operation(description = "Will compute the cost of all selected sevices linked with current devices", summary = "Get cost details for select Services")
  public ResponseEntity<RmmDtoServiceResponse<RmmCostDto>> getServicesCost(@Parameter(description = "Customer Id") @PathVariable Long customerId) {
    RmmDtoServiceResponse<RmmCostDto> response = servicesCostProcessor.computeServicesCost(customerId);
    return new ResponseEntity<RmmDtoServiceResponse<RmmCostDto>>(response, response.getStatus().httpStatus());
  }


}
