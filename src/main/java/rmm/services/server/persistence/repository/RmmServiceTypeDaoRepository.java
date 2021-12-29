package rmm.services.server.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rmm.services.server.persistence.entity.RmmServiceTypeDao;

/**
 * The Interface RmmServiceTypeDaoRepository CRUD operations for SERVICE_TYPE table
 */
@Repository
public interface RmmServiceTypeDaoRepository extends CrudRepository<RmmServiceTypeDao, Long> {

  /**
   * Find by type.
   *
   * @param deviceType the device type
   * @return the rmm service type dao
   */
  RmmServiceTypeDao findByType(String deviceType);

}
