package rmm.services.server;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import io.cucumber.spring.CucumberContextConfiguration;
import rmm.services.server.persistence.entity.RmmDeviceTypeDao;
import rmm.services.server.persistence.repository.RmmDeviceTypeDaoRepository;
import rmm.services.server.rest.model.RmmDeviceDto;
import rmm.services.server.rest.model.RmmDtoServiceResponse;
import rmm.services.server.rest.model.RmmServiceDto;
import rmm.services.server.security.RmmSecurityProperties;

/**
 * The Class RmmSpringIntegrationTest.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = RmmServicesServerAppApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@SuppressWarnings({"unchecked", "rawtypes"})
public class RmmSpringIntegrationTest {

  /** The rest template. */
  @Autowired
  private TestRestTemplate restTemplate;

  /** The data source. */
  @Autowired
  private DataSource dataSource;

  /** The security properties. */
  @Autowired
  protected RmmSecurityProperties securityProperties;

  /** The device type repository. */
  @Autowired
  protected RmmDeviceTypeDaoRepository deviceTypeRepository;

  /** The jdbc template. */
  protected JdbcTemplate jdbcTemplate;

  /** The api key. */
  protected String apiKey = "5";

  /** The customer id. */
  protected Long customerId;

  /** The role. */
  protected String role;

  /** The role to api key map. */
  protected Map<String, String> roleToApiKeyMap;

  @PostConstruct
  public void setup() {
    roleToApiKeyMap = securityProperties.getApiKeyToRoleMap().entrySet().stream().collect(Collectors.toMap(entry -> entry.getValue(), entry -> entry.getKey()));
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  protected <T> ResponseEntity<RmmDtoServiceResponse<T>> executeRest(String url, HttpMethod method, ParameterizedTypeReference<RmmDtoServiceResponse<T>> type, Map params) {
    return executeRest(url, method, null, type, params);
  }

  protected <T> ResponseEntity<RmmDtoServiceResponse<T>> executeRest(String url, HttpMethod method, T body, ParameterizedTypeReference<RmmDtoServiceResponse<T>> type, Map params) {
    HttpEntity<T> entity = new HttpEntity<T>(getHeaders());
    if (body != null) {
      entity = new HttpEntity<T>(body, getHeaders());
    }
    return restTemplate.exchange(url, method, entity, type, params);
  }

  protected void createService(Long customerId, RmmServiceDto service) {
    final String insertQuery = "INSERT INTO SERVICE ( ID, CUSTOMER_ID , SERVICE_TYPE_ID , CREATE_DATE , UPDATE_DATE) values (?,?,?,SYSDATE,SYSDATE)";
    jdbcTemplate.update(insertQuery, new Object[] {service.getId(), customerId, service.getServiceTypeId()});
  }

  protected void createDevice(Long customerId, RmmDeviceDto device) {
    Optional<RmmDeviceTypeDao> deviceTypeDao = deviceTypeRepository.findById(device.getDeviceTypeId());
    final String insertQuery = "INSERT INTO DEVICE ( ID, CUSTOMER_ID , SYSTEM_NAME , DEVICE_TYPE_ID , CREATE_DATE , UPDATE_DATE ) values (?,?,?,?,SYSDATE,SYSDATE)";
    jdbcTemplate.update(insertQuery, new Object[] {device.getId(), customerId, device.getSystemName(), deviceTypeDao.get().getId()});
  }

  /**
   * Gets the headers.
   *
   * @return the headers
   */
  protected HttpHeaders getHeaders() {
    HttpHeaders headers = new HttpHeaders();
    String key = apiKey;
    if (role != null && !role.isEmpty()) {
      key = roleToApiKeyMap.get(role);
    }
    headers.add(securityProperties.getApiKeyHeader(), key);
    headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
    return headers;
  }



}
