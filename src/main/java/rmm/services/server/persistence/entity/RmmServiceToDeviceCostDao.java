package rmm.services.server.persistence.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import rmm.services.server.rest.model.AbstractDto;

/**
 * The Class RmmServiceToDeviceCostDao DAO for SERVICE_DEVICE_COST table
 */
@Entity
@Table(name = "SERVICE_DEVICE_COST")
public class RmmServiceToDeviceCostDao extends AbstractDto {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 591070025333415913L;

  /** The id. */
  @Id
  @Column(name = "ID")
  private Long id;

  /** The cost. */
  @Column(name = "COST")
  private BigDecimal cost;

  /** The device type. */
  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "DEVICE_TYPE_ID", referencedColumnName = "ID", nullable = false)
  private RmmDeviceTypeDao deviceType;

  /** The service type. */
  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "SERVICE_TYPE_ID", referencedColumnName = "ID", nullable = false)
  private RmmServiceTypeDao serviceType;

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
   * Gets the cost.
   *
   * @return the cost
   */
  public BigDecimal getCost() {
    return cost;
  }

  /**
   * Sets the cost.
   *
   * @param cost the new cost
   */
  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  /**
   * Gets the device type.
   *
   * @return the device type
   */
  public RmmDeviceTypeDao getDeviceType() {
    return deviceType;
  }

  /**
   * Sets the device type.
   *
   * @param deviceType the new device type
   */
  public void setDeviceType(RmmDeviceTypeDao deviceType) {
    this.deviceType = deviceType;
  }

  /**
   * Gets the service type.
   *
   * @return the service type
   */
  public RmmServiceTypeDao getServiceType() {
    return serviceType;
  }

  /**
   * Sets the service type.
   *
   * @param serviceType the new service type
   */
  public void setServiceType(RmmServiceTypeDao serviceType) {
    this.serviceType = serviceType;
  }

}
