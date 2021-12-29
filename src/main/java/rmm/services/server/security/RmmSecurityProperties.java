package rmm.services.server.security;

import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The Class RmmSecurityProperties security properties
 */
@Component
@ConfigurationProperties(prefix = "rmm-services-server-app.security")
public class RmmSecurityProperties {

  /** The api key to role map. */
  private Map<String, String> apiKeyToRoleMap;

  /** The user to privilege map. */
  private Map<String, List<String>> userToPrivilegeMap;

  /** The api key header. */
  private String apiKeyHeader = "apiKeyHeader";



  /**
   * Gets the api key header.
   *
   * @return the api key header
   */
  public String getApiKeyHeader() {
    return apiKeyHeader;
  }

  /**
   * Sets the api key header.
   *
   * @param apiKeyHeader the new api key header
   */
  public void setApiKeyHeader(String apiKeyHeader) {
    this.apiKeyHeader = apiKeyHeader;
  }

  /**
   * Gets the user to privilege map.
   *
   * @return the user to privilege map
   */
  public Map<String, List<String>> getUserToPrivilegeMap() {
    return userToPrivilegeMap;
  }

  /**
   * Sets the user to privilege map.
   *
   * @param userToPrivilegeMap the user to privilege map
   */
  public void setUserToPrivilegeMap(Map<String, List<String>> userToPrivilegeMap) {
    this.userToPrivilegeMap = userToPrivilegeMap;
  }

  /**
   * Gets the api key to role map.
   *
   * @return the api key to role map
   */
  public Map<String, String> getApiKeyToRoleMap() {
    return apiKeyToRoleMap;
  }

  /**
   * Sets the api key to role map.
   *
   * @param apiKeyToRoleMap the api key to role map
   */
  public void setApiKeyToRoleMap(Map<String, String> apiKeyToRoleMap) {
    this.apiKeyToRoleMap = apiKeyToRoleMap;
  }

}
