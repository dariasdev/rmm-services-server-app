package rmm.services.server.cucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rmm.services.server.RmmSpringIntegrationTest;
import rmm.services.server.rest.model.RmmCostDto;
import rmm.services.server.rest.model.RmmDeviceDto;
import rmm.services.server.rest.model.RmmDtoServiceResponse;
import rmm.services.server.rest.model.RmmServiceCostDto;
import rmm.services.server.rest.model.RmmServiceDto;


/**
 * The Class RmmCostStepDefs step definitions for cost features
 */
@Transactional
public class RmmCostStepDefs extends RmmSpringIntegrationTest {


  /** The Constant GET_BASE_URL. */
  private static final String GET_BASE_URL = "/ninja-rmm-services/api/v1/{customerId}/cost";

  /** The Constant COST_TYPE. */
  private static final ParameterizedTypeReference<RmmDtoServiceResponse<RmmCostDto>> COST_TYPE = new ParameterizedTypeReference<RmmDtoServiceResponse<RmmCostDto>>() {};

  /** The cost response get. */
  private ResponseEntity<RmmDtoServiceResponse<RmmCostDto>> costResponseGet;

  @After
  public void cleanUp() {
    customerId = null;
    role = null;
    apiKey = null;
    costResponseGet = null;
  }

  @Given("customer with id {long} has the following devices")
  public void customer_with_id_has_the_following_devices(Long customerId, List<RmmDeviceDto> devices) {
    for (RmmDeviceDto device : devices) {
      createDevice(customerId, device);
    }
  }

  @Given("customer with id {long} with the following services")
  public void customer_with_id_with_the_following_services(Long customerId, List<RmmServiceDto> services) {
    for (RmmServiceDto service : services) {
      createService(customerId, service);
    }
  }

  @Given("customer with id {long} has no devices and no services")
  public void customer_with_id_has_no_devices_and_no_services(Long customerId) {
    this.customerId = customerId;
  }

  @Given("consumer has {string} role for cost api")
  public void consumer_has_role_for_cost_api(String role) {
    this.role = role;
  }

  @When("compute cost end point is executed for customer id {int}")
  public void compute_cost_end_point_is_executed_for_customer_id(Integer customerId) {
    Map<String, String> params = new HashMap<>();
    params.put("customerId", customerId.toString());
    costResponseGet = executeRest(GET_BASE_URL, HttpMethod.GET, COST_TYPE, params);
  }

  @Then("compute cost end point will return status {int}")
  public void compute_cost_end_point_will_return_status(Integer status) {
    assertEquals(status, costResponseGet.getStatusCodeValue());
  }

  @Then("compute cost end point will return {double} as overall cost")
  public void compute_cost_end_point_will_return_as_overall_cost(Double overAllCost) {
    assertEquals(overAllCost.doubleValue(), costResponseGet.getBody().getDto().getOverallCost().doubleValue());
  }

  @Then("compute cost end point will return the following services cost detail")
  public void compute_cost_end_point_will_return_the_following_services_cost_detail(List<RmmServiceCostDto> costDetail) {
    for (int i = 0; i > costDetail.size(); i++) {
      validateCostDto(costDetail.get(i), costResponseGet.getBody().getDto().getServicesCost().get(i));
    }
  }

  private void validateCostDto(RmmServiceCostDto expected, RmmServiceCostDto actual) {
    if (expected.getCost() != null) {
      assertEquals(expected.getCost().doubleValue(), actual.getCost().doubleValue());
    }

    if (expected.getServiceName() != null && !expected.getServiceName().isEmpty()) {
      assertEquals(expected.getServiceName(), actual.getServiceName());
    }
  }

}

