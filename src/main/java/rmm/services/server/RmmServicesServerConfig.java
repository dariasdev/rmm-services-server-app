package rmm.services.server;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import liquibase.integration.spring.SpringLiquibase;
import rmm.services.server.rest.provider.RmmOpenApiConfig;

/**
 * This is the main Spring configuration class for this module, it should declare any additional
 * packages to scan , declare any beans, supply any other annotations etc.
 *
 */
@Configuration
@Import(RmmOpenApiConfig.class)
public class RmmServicesServerConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(RmmServicesServerConfig.class);

  /**
   * the bean creation will create tables , constraints and sequences specified in classpath:db/changelog/liquibase-changeLog.xml 
   * 
   * @param dataSource The {@link DataSource} that will be used to create the {@link SpringLiquibase} instance
   * @return The {@link SpringLiquibase} instance created
   */
  @Bean
  public SpringLiquibase liquibase(DataSource dataSource) {
    LOGGER.info("Populating Database with liquibase");
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog("classpath:db/changelog/liquibase-changeLog.xml");
    liquibase.setDataSource(dataSource);;
    return liquibase;
  }

}
