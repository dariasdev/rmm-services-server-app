package rmm.services.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * This is the actual Spring boot application that loads up the entire app.
 *
 */
@SpringBootApplication
@Import(RmmServicesServerConfig.class)
public class RmmServicesServerAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(RmmServicesServerAppApplication.class, args);
  }

}
