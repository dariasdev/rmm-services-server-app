package rmm.services.server.rest.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import io.swagger.v3.oas.annotations.media.Schema;
import rmm.services.server.rest.model.validator.RmmCreateAction;
import rmm.services.server.rest.model.validator.RmmUpdateAction;

/**
 * The Class RmmDeviceDto device DTO
 */
public class RmmDeviceDto extends AbstractDto {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -2565659235543747645L;

  /** The id. */
  @NotNull(groups = RmmUpdateAction.class)
  @Null(groups = RmmCreateAction.class)
  @Schema(description = "Unique Device Id, expected null for POST request and required for PUT requests")
  private Long id;

  /** The system name. */
  @NotNull(groups = {RmmCreateAction.class, RmmUpdateAction.class})
  @Schema(description = "Device or System Name")
  private String systemName;

  /** The device type id. */
  @NotNull(groups = {RmmCreateAction.class, RmmUpdateAction.class})
  @Schema(description = "Device Type Id. Ex. 1 for Windows Workstation , 2 for Windows Server , 3 for Mac  ")
  private Long deviceTypeId;

  /**
   * Gets the device type id.
   *
   * @return the device type id
   */
  public Long getDeviceTypeId() {
    return deviceTypeId;
  }

  /**
   * Sets the device type id.
   *
   * @param deviceTypeId the new device type id
   */
  public void setDeviceTypeId(Long deviceTypeId) {
    this.deviceTypeId = deviceTypeId;
  }

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
   * Gets the system name.
   *
   * @return the system name
   */
  public String getSystemName() {
    return systemName;
  }

  /**
   * Sets the system name.
   *
   * @param systemName the new system name
   */
  public void setSystemName(String systemName) {
    this.systemName = systemName;
  }


}
