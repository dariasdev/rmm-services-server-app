package rmm.services.server.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The Class RmmDeviceDao DAO for DEVICE table
 */
@Entity
@Table(name = "DEVICE")
public class RmmDeviceDao extends AbstractDao {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -702442954057842273L;

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rmm_device_seq")
  @SequenceGenerator(name = "rmm_device_seq", allocationSize = 1)
  @Column(name = "ID")
  private Long id;

  /** The customer id. */
  @Column(name = "CUSTOMER_ID")
  private Long customerId;

  /** The System name. */
  @Column(name = "SYSTEM_NAME")
  private String SystemName;

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
   * Gets the customer id.
   *
   * @return the customer id
   */
  public Long getCustomerId() {
    return customerId;
  }

  /**
   * Sets the customer id.
   *
   * @param customerId the new customer id
   */
  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  /**
   * Gets the system name.
   *
   * @return the system name
   */
  public String getSystemName() {
    return SystemName;
  }

  /**
   * Sets the system name.
   *
   * @param systemName the new system name
   */
  public void setSystemName(String systemName) {
    SystemName = systemName;
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
