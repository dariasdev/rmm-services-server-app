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
 * The Class RmmServiceDao DAO for SERVICE table
 */
@Entity
@Table(name = "SERVICE")
public class RmmServiceDao extends AbstractDao {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -702442954057842273L;

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rmm_service_seq")
  @SequenceGenerator(name = "rmm_service_seq", allocationSize = 1)
  @Column(name = "ID")
  private Long id;

  /** The customer id. */
  @Column(name = "CUSTOMER_ID")
  private Long customerId;

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


}
