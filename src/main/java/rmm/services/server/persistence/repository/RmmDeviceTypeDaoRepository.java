package rmm.services.server.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rmm.services.server.persistence.entity.RmmDeviceTypeDao;

/**
 * The Interface RmmDeviceTypeDaoRepository CRUD operations for DEVICE_TYPE table
 */
@Repository
public interface RmmDeviceTypeDaoRepository extends CrudRepository<RmmDeviceTypeDao, Long> {

  /**
   * Find by type.
   *
   * @param deviceType the device type
   * @return the rmm device type dao
   */
  RmmDeviceTypeDao findByType(String deviceType);


}
