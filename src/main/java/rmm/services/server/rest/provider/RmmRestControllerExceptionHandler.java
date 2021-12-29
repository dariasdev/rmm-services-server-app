package rmm.services.server.rest.provider;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import rmm.services.server.rest.model.RmmMessage;
import rmm.services.server.rest.model.RmmServiceResponse;

/**
 * The Class RmmRestControllerExceptionHandler, generic exception handle to wrap uncaught exception into valid api generic response
 */
@ControllerAdvice
public class RmmRestControllerExceptionHandler {

  /**
   * handle generic exception and return proper response 
   *
   * @param ex the exception
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<RmmServiceResponse> genericExpcetion(Exception ex, WebRequest request) {
    RmmServiceResponse errorResponse = new RmmServiceResponse();
    errorResponse.getMessages().add(new RmmMessage(ex.getMessage()));
    return new ResponseEntity<RmmServiceResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
