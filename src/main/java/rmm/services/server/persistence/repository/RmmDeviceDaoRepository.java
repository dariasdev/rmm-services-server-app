package rmm.services.server.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rmm.services.server.persistence.entity.RmmDeviceDao;

/**
 * The Interface RmmDeviceDaoRepository CRUD operations for DEVICE table
 */
@Repository
public interface RmmDeviceDaoRepository extends CrudRepository<RmmDeviceDao, Long> {

  /**
   * Find by customer id.
   *
   * @param customerId the customer id
   * @return the list
   */
  List<RmmDeviceDao> findByCustomerId(Long customerId);

  /**
   * Find by customer id and id.
   *
   * @param customerId the customer id
   * @param id the id
   * @return the rmm device dao
   */
  RmmDeviceDao findByCustomerIdAndId(Long customerId, Long id);

  /**
   * Delete by customer id and id.
   *
   * @param customerId the customer id
   * @param id the id
   */
  void deleteByCustomerIdAndId(Long customerId, Long id);

}
