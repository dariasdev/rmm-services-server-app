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
import rmm.services.server.rest.model.RmmDeviceDto;
import rmm.services.server.rest.model.RmmDtoServiceResponse;


/**
 * The Class RmmDeviceStepDefs step definition for device features
 */
@Transactional
public class RmmDeviceStepDefs extends RmmSpringIntegrationTest {


  /** The Constant GET_BASE_URL. */
  private static final String GET_BASE_URL = "/ninja-rmm-services/api/v1/{customerId}/devices/{deviceId}";

  /** The Constant POST_BASE_URL. */
  private static final String POST_BASE_URL = "/ninja-rmm-services/api/v1/{customerId}/devices";

  /** The Constant DEVICE_TYPE. */
  private static final ParameterizedTypeReference<RmmDtoServiceResponse<RmmDeviceDto>> DEVICE_TYPE = new ParameterizedTypeReference<RmmDtoServiceResponse<RmmDeviceDto>>() {};

  /** The Constant DEVICE_LIST_TYPE. */
  private static final ParameterizedTypeReference<RmmDtoServiceResponse<List<RmmDeviceDto>>> DEVICE_LIST_TYPE = new ParameterizedTypeReference<RmmDtoServiceResponse<List<RmmDeviceDto>>>() {};

  /** The device response get. */
  private ResponseEntity<RmmDtoServiceResponse<RmmDeviceDto>> deviceResponseGet;

  /** The device response get all. */
  private ResponseEntity<RmmDtoServiceResponse<List<RmmDeviceDto>>> deviceResponseGetAll;

  /** The device response post. */
  private ResponseEntity<RmmDtoServiceResponse<RmmDeviceDto>> deviceResponsePost;

  /** The device response put. */
  private ResponseEntity<RmmDtoServiceResponse<RmmDeviceDto>> deviceResponsePut;

  /** The device response delete. */
  private ResponseEntity<RmmDtoServiceResponse<RmmDeviceDto>> deviceResponseDelete;

  @After
  public void cleanUp() {
    customerId = null;
    role = null;
    apiKey = null;
    deviceResponseGet = null;
    deviceResponsePost = null;
    deviceResponsePut = null;
    deviceResponseDelete = null;
    deviceResponseGetAll = null;
  }


  @Given("customer with id {long} has the following device")
  public void customer_with_id_has_the_following_device(Long customerId, RmmDeviceDto device) {
    this.customerId = customerId;
    createDevice(customerId, device);
  }

  @Given("customer with id {long} that has no devices")
  public void customer_with_id_that_has_no_devices(Long customerId) {
    this.customerId = customerId;
  }

  @And("consumer has {string} role for devices api")
  public void consumer_has_role(String role) {
    this.role = role;
  }

  @When("device get end point is executed for customer id {int} and device id {int}")
  public void device_get_end_point_is_executed_for_customer_id_and_device_id(Integer customerId, Integer deviceId) {
    Map<String, String> params = new HashMap<>();
    params.put("customerId", customerId.toString());
    params.put("deviceId", deviceId.toString());
    deviceResponseGet = executeRest(GET_BASE_URL, HttpMethod.GET, DEVICE_TYPE, params);
  }

  @When("device post end point is executed for customer id {int} with following request")
  public void device_post_end_point_is_executed_for_customer_id_with_following_request(Integer customerId, RmmDeviceDto device) {
    Map<String, String> params = new HashMap<>();
    params.put("customerId", customerId.toString());
    deviceResponsePost = executeRest(POST_BASE_URL, HttpMethod.POST, device, DEVICE_TYPE, params);
  }

  @Then("device get end point will respond with status {int} and the following device")
  public void get_end_will_respopnd_with_status_and_the_follwing_device(Integer status, RmmDeviceDto device) {
    assertEquals(status, deviceResponseGet.getStatusCodeValue());
    validateDeviceDto(device, deviceResponseGet.getBody().getDto());
  }

  @Then("device get end point will respond with status {int}")
  public void get_end_will_respond_with_status(Integer status) {
    assertEquals(status, deviceResponseGet.getStatusCodeValue());
  }

  @Then("device post end point will respond with status {int} and the following device")
  public void post_end_will_respopnd_with_status_and_the_follwing_device(Integer status, RmmDeviceDto device) {
    assertEquals(status, deviceResponsePost.getStatusCodeValue());
    validateDeviceDto(device, deviceResponsePost.getBody().getDto());
  }

  @Then("device post end point will respond with status {int}")
  public void post_end_will_respond_with_status(Integer status) {
    assertEquals(status, deviceResponsePost.getStatusCodeValue());
  }

  @When("device put end point is executed for customer id {int} with following request")
  public void device_put_end_point_is_executed_for_customer_id_with_following_request(Integer customerId, RmmDeviceDto device) {
    Map<String, String> params = new HashMap<>();
    params.put("customerId", customerId.toString());
    deviceResponsePut = executeRest(POST_BASE_URL, HttpMethod.PUT, device, DEVICE_TYPE, params);
  }

  @Then("device put end point will respond with status {int} and the following device")
  public void put_end_point_will_respond_with_status_and_the_following_device(Integer status, RmmDeviceDto device) {
    assertEquals(status, deviceResponsePut.getStatusCodeValue());
    validateDeviceDto(device, deviceResponsePut.getBody().getDto());
  }

  @Then("device put end point will respond with status {int}")
  public void put_end_point_will_respond_with_status(Integer status) {
    assertEquals(status, deviceResponsePut.getStatusCodeValue());
  }

  @When("device delete end point is executed for customer id {int} and device id {int}")
  public void device_delete_end_point_is_executed_for_customer_id_and_device_id(Integer customerId, Integer deviceId) {
    Map<String, String> params = new HashMap<>();
    params.put("customerId", customerId.toString());
    params.put("deviceId", deviceId.toString());
    deviceResponseDelete = executeRest(GET_BASE_URL, HttpMethod.DELETE, DEVICE_TYPE, params);
  }

  @Then("device delete end point will respond with status {int}")
  public void delete_end_point_will_respond_with_status(Integer status) {
    assertEquals(status, deviceResponseDelete.getStatusCodeValue());
  }

  @When("device get all end point is executed for customer id {int}")
  public void device_get_all_end_point_is_executed_for_customer_id(Integer customerId) {
    Map<String, String> params = new HashMap<>();
    params.put("customerId", customerId.toString());
    deviceResponseGetAll = executeRest(POST_BASE_URL, HttpMethod.GET, DEVICE_LIST_TYPE, params);
  }


  @Then("device get all end point will respond with status {int} and the following devices")
  public void device_get_all_end_point_will_respond_with_status_and_the_following_devices(Integer status, List<RmmDeviceDto> devices) {
    assertEquals(status, deviceResponseGetAll.getStatusCodeValue());
    for (int i = 0; i > devices.size(); i++) {
      validateDeviceDto(devices.get(i), deviceResponseGetAll.getBody().getDto().get(i));
    }
  }


  @Then("device get all end point will respond with status {int}")
  public void device_get_all_end_point_will_respond_with_status(Integer status) {
    assertEquals(status, deviceResponseGetAll.getStatusCodeValue());
  }

  @Then("device get all end point will respond with empty list")
  public void device_get_all_end_point_will_respond_with_empty_list() {
    assertTrue(deviceResponseGetAll.getBody().getDto().isEmpty());
  }

  private void validateDeviceDto(RmmDeviceDto expected, RmmDeviceDto actual) {
    if (expected.getDeviceTypeId() != null) {
      assertEquals(expected.getDeviceTypeId(), actual.getDeviceTypeId());
    }

    if (expected.getSystemName() != null && !expected.getSystemName().isEmpty()) {
      assertEquals(expected.getSystemName(), actual.getSystemName());
    }

    if (expected.getId() != null) {
      assertEquals(expected.getId(), actual.getId());
    }
  }

}

