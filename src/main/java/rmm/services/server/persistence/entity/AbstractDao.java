package rmm.services.server.persistence.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import rmm.services.server.rest.model.AbstractDto;


/**
 * The Class AbstractDao is an abstract class with common provenance fields to be used by DAO entities which require provenance fields 
 */
@MappedSuperclass
@EntityListeners(RmmDateEntityListener.class)
public class AbstractDao extends AbstractDto {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -2144909855009714238L;

  /** The create date. */
  @Column(name = "CREATE_DATE")
  private Date createDate;

  /** The update date. */
  @Column(name = "UPDATE_DATE")
  private Date updateDate;

  /**
   * Gets the creates the date.
   *
   * @return the creates the date
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * Sets the creates the date.
   *
   * @param createDate the new creates the date
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * Gets the update date.
   *
   * @return the update date
   */
  public Date getUpdateDate() {
    return updateDate;
  }

  /**
   * Sets the update date.
   *
   * @param updateDate the new update date
   */
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

}
