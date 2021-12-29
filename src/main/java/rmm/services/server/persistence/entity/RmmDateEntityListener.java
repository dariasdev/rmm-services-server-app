package rmm.services.server.persistence.entity;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


/**
 * The RmmDateEntityEvent listener abstract the logic to populate provenance fields. it will
 * populate create date and update data and can be expanded to set any provenance fields
 *
 */
public class RmmDateEntityListener {

  /**
   * set create data during pre insert operation
   *
   * @param entity the entity
   */
  @PrePersist
  public void prePersist(final AbstractDao entity) {
    final Date createDate = new Date();
    entity.setCreateDate(createDate);
    entity.setUpdateDate(createDate);
  }

  /**
   * set update date during pre update operation
   *
   * @param entity the entity
   */
  @PreUpdate
  public void preUpdate(final AbstractDao entity) {
    final Date updateDate = new Date();
    entity.setUpdateDate(updateDate);
  }
}


