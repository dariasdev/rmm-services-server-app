package rmm.services.server.rest.provider;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * The Class RmmHomeController simple utility to route request to root url to swagger page
 */
@Controller
public class RmmHomeController {

  /** The Constant DEFAULT_URI_SWAGGER. */
  protected static final String DEFAULT_URI_SWAGGER = "/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config";

  /**
   * route request to root url to swagger page
   *
   * @param response the response
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @RequestMapping("/")
  public void home(final HttpServletResponse response) throws IOException {
    response.sendRedirect(ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_URI_SWAGGER).build().toUriString());
  }

}
