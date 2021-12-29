package rmm.services.server.cucumber;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rmm.services.server.RmmSpringIntegrationTest;
import rmm.services.server.rest.model.RmmDtoServiceResponse;
import rmm.services.server.rest.model.RmmServiceDto;


/**
 * The Class RmmServiceStepDefs step definitions for service features
 */
@Transactional
public class RmmServiceStepDefs extends RmmSpringIntegrationTest {


  /** The Constant GET_BASE_URL. */
  private static final String GET_BASE_URL = "/ninja-rmm-services/api/v1/{customerId}/services";

  /** The Constant POST_BASE_URL. */
  private static final String POST_BASE_URL = "/ninja-rmm-services/api/v1/{customerId}/services/{serviceTypeId}";

  /** The Constant DELETE_BASE_URL. */
  private static final String DELETE_BASE_URL = "/ninja-rmm-services/api/v1/{customerId}/services/{serviceId}";

  /** The Constant SERVICE_TYPE. */
  private static final ParameterizedTypeReference<RmmDtoServiceResponse<RmmServiceDto>> SERVICE_TYPE = new ParameterizedTypeReference<RmmDtoServiceResponse<RmmServiceDto>>() {};

  /** The Constant LIST_SERVICE_TYPE. */
  private static final ParameterizedTypeReference<RmmDtoServiceResponse<List<RmmServiceDto>>> LIST_SERVICE_TYPE = new ParameterizedTypeReference<RmmDtoServiceResponse<List<RmmServiceDto>>>() {};

  /** The service response get. */
  private ResponseEntity<RmmDtoServiceResponse<List<RmmServiceDto>>> serviceResponseGet;

  /** The service response post. */
  private ResponseEntity<RmmDtoServiceResponse<RmmServiceDto>> serviceResponsePost;

  /** The service response delete. */
  private ResponseEntity<RmmDtoServiceResponse<RmmServiceDto>> serviceResponseDelete;


  @After
  public void cleanUp() {
    customerId = null;
    role = null;
    apiKey = null;
    serviceResponseGet = null;
    serviceResponsePost = null;
    serviceResponseDelete = null;
  }

  @Given("customer with id {long} that has the following service")
  public void customer_with_id_that_has_has_the_following_service(Long customerId, RmmServiceDto service) {
    this.customerId = customerId;
    createService(customerId, service);
  }

  @Given("customer with id {long} that has no services")
  public void customer_with_id_that_has_no_services(Long customerId) {
    this.customerId = customerId;
  }

  @And("consumer has {string} role for services api")
  public void consumer_has_role(String role) {
    this.role = role;
  }

  @When("service get end point is executed for customer id {int}")
  public void service_get_end_point_is_executed_for_customer_id(Integer customerId) {
    Map<String, String> params = new HashMap<>();
    params.put("customerId", customerId.toString());
    serviceResponseGet = executeRest(GET_BASE_URL, HttpMethod.GET, LIST_SERVICE_TYPE, params);
  }

  @Then("service get end point will respond with status {int}")
  public void service_get_end_point_will_respond_with_status(Integer status) {
    assertEquals(status, serviceResponseGet.getStatusCodeValue());
  }

  @Then("service get end point will return follwing services")
  public void service_get_end_point_will_return_follwing_services(List<RmmServiceDto> services) {
    for (int i = 0; i > services.size(); i++) {
      validateServiceDto(services.get(i), serviceResponseGet.getBody().getDto().get(i));
    }
  }

  @When("service post end point is executed for customer id {int} and service type id {int}")
  public void service_post_end_point_is_executed_for_customer_id_with_following_request(Integer customerId, Integer serviceId) {
    Map<String, String> params = new HashMap<>();
    params.put("customerId", customerId.toString());
    params.put("serviceTypeId", serviceId.toString());
    serviceResponsePost = executeRest(POST_BASE_URL, HttpMethod.POST, SERVICE_TYPE, params);
  }

  @Then("service post end point will respond with status {int} and the following service")
  public void put_end_point_will_respond_with_status_and_the_following_device(Integer status, RmmServiceDto device) {
    assertEquals(status, serviceResponsePost.getStatusCodeValue());
    validateServiceDto(device, serviceResponsePost.getBody().getDto());
  }

  @Then("service post end point will respond with status {int}")
  public void post_end_point_will_respond_with_status(Integer status) {
    assertEquals(status, serviceResponsePost.getStatusCodeValue());
  }

  @Then("service delete end point will respond with status {int}")
  public void delete_end_point_will_respond_with_status(Integer status) {
    assertEquals(status, serviceResponseDelete.getStatusCodeValue());
  }

  @Then("service get end point will respond with empty list")
  public void get_end_point_will_respond_with_empty_list() {
    assertTrue(serviceResponseGet.getBody().getDto().isEmpty());
  }

  @When("service delete end point is executed for customer id {int} and service id {int}")
  public void service_delete_end_point_is_executed_for_customer_id_and_service_id(Integer customerId, Integer serviceId) {
    Map<String, String> params = new HashMap<>();
    params.put("customerId", customerId.toString());
    params.put("serviceId", serviceId.toString());
    serviceResponseDelete = executeRest(DELETE_BASE_URL, HttpMethod.DELETE, SERVICE_TYPE, params);
  }

  private void validateServiceDto(RmmServiceDto expected, RmmServiceDto actual) {
    if (expected.getServiceTypeId() != null) {
      assertEquals(expected.getServiceTypeId(), actual.getServiceTypeId());
    }

    if (expected.getServiceName() != null && !expected.getServiceName().isEmpty()) {
      assertEquals(expected.getServiceName(), actual.getServiceName());
    }
  }

}

