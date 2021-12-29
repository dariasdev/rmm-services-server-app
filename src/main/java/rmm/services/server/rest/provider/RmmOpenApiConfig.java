package rmm.services.server.rest.provider;

import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import rmm.services.server.security.RmmSecurityProperties;

/**
 * The Class RmmOpenApiConfig open API configuration for swagger documentation
 */
@Configuration
public class RmmOpenApiConfig {

  /** The Constant API_KEY_SECURITY_HEADER. */
  public static final String API_KEY_SECURITY_HEADER = "apiKeySecurityHeader";

  /** The resource. */
  @Value("classpath:service-doc/api-description.md")
  private Resource resource;

  /**
   * create open Api top documentation and provide security scheme to facilitate testing 
   * Load description from read me file
   *
   * @param properties the properties
   * @return the open API
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Bean
  public OpenAPI rmmServicesOpenAPI(RmmSecurityProperties properties) throws IOException {
    String description = FileUtils.readFileToString(resource.getFile(), "UTF-8");
    Info info = new Info().title("Ninja RMM Services API").description(description);
    OpenAPI openApi = new OpenAPI().components(new Components()).info(info);
    openApi.addSecurityItem(new SecurityRequirement().addList(API_KEY_SECURITY_HEADER));
    openApi.getComponents().addSecuritySchemes(API_KEY_SECURITY_HEADER, new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name(properties.getApiKeyHeader()));
    return openApi;
  }

}
