package rmm.services.server.rest.model;

import org.springframework.http.HttpStatus;

/**
 * The Enum RmmStatus simple utility to wrap application level status to http status
 */
public enum RmmStatus {

  /** The success. */
  SUCCESS {
    @Override
    public HttpStatus httpStatus() {
      return HttpStatus.OK;
    }
  },

  /** The rejected. */
  REJECTED {
    @Override
    public HttpStatus httpStatus() {
      return HttpStatus.BAD_REQUEST;
    }
  },

  /** The error. */
  ERROR {
    @Override
    public HttpStatus httpStatus() {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  },

  /** The not found. */
  NOT_FOUND {
    @Override
    public HttpStatus httpStatus() {
      return HttpStatus.NOT_FOUND;
    }
  };

  public abstract HttpStatus httpStatus();

}
