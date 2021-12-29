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
 * The Class RmmDeviceTypeToServiceCostDao DAO for DEVICE_TYPE_SERVICE_COST
 */
@Entity
@Table(name = "DEVICE_TYPE_SERVICE_COST")
public class RmmDeviceTypeToServiceCostDao extends AbstractDto {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 7660852582089624761L;

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


}
