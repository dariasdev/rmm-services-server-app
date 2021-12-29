package rmm.services.server.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import rmm.services.server.rest.model.AbstractDto;

/**
 * The Class RmmDeviceTypeDao DAO for DEVICE_TYPE table
 */
@Entity
@Table(name = "DEVICE_TYPE")
public class RmmDeviceTypeDao extends AbstractDto {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -2342859455646872815L;

  /** The id. */
  @Id
  @Column(name = "ID")
  private Long id;

  /** The type. */
  @Column(name = "TYPE")
  private String type;

  /**
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type.
   *
   * @param type the new type
   */
  public void setType(String type) {
    this.type = type;
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


}
