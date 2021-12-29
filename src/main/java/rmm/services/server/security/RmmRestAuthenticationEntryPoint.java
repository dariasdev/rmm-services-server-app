package rmm.services.server.security;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import rmm.services.server.rest.model.RmmMessage;
import rmm.services.server.rest.model.RmmServiceResponse;

/**
 * The Class RmmRestAuthenticationEntryPoint handle authorization error
 */
@Component
public class RmmRestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * wrap spring authorization error with generic service response error in json format
     *
     * @param httpServletRequest the http servlet request
     * @param httpServletResponse the http servlet response
     * @param e the e
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
      OutputStream out = httpServletResponse.getOutputStream();
      ObjectMapper mapper = new ObjectMapper();
      RmmServiceResponse response = new RmmServiceResponse();
      response.getMessages().add(new RmmMessage("The request is not authorized.  Please verify credentials used in the request"));
      httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
      httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
      mapper.writerWithDefaultPrettyPrinter().writeValue(out, response);
      out.flush();
    }
}
