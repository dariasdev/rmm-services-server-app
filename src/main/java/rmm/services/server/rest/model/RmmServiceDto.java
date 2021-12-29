package rmm.services.server.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The Class RmmServiceDto service DTO
 */
public class RmmServiceDto extends AbstractDto {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 4355150749963074600L;

  /** The id. */
  @Schema(description = "Unique Service Id")
  private Long id;

  /** The service type id. */
  @Schema(description = "Service Type Id. Ex. 1 for Antivirus , 2 for Cloudberry , 3 for PSA  ")
  private Long serviceTypeId;

  /** The service name. */
  @Schema(description = "Service Name")
  private String serviceName;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the service type id.
   *
   * @return the service type id
   */
  public Long getServiceTypeId() {
    return serviceTypeId;
  }

  /**
   * Sets the service type id.
   *
   * @param serviceTypeId the new service type id
   */
  public void setServiceTypeId(Long serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
  }

  /**
   * Gets the service name.
   *
   * @return the service name
   */
  public String getServiceName() {
    return serviceName;
  }

  /**
   * Sets the service name.
   *
   * @param serviceName the new service name
   */
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

}
