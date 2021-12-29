package rmm.services.server.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rmm.services.server.persistence.entity.RmmServiceDao;

/**
 * The Interface RmmServiceDaoRepository CRUD for SERVICE table
 */
@Repository
public interface RmmServiceDaoRepository extends CrudRepository<RmmServiceDao, Long> {

  /**
   * Find by customer id.
   *
   * @param customerId the customer id
   * @return the list
   */
  List<RmmServiceDao> findByCustomerId(Long customerId);

  /**
   * Find by customer id and id.
   *
   * @param customerId the customer id
   * @param id the id
   * @return the rmm service dao
   */
  RmmServiceDao findByCustomerIdAndId(Long customerId, Long id);
  
  /**
   * Find by customer id and service type id.
   *
   * @param customerId the customer id
   * @param id the id
   * @return the rmm service dao
   */
  RmmServiceDao findByCustomerIdAndServiceTypeId(Long customerId, Long id);

  /**
   * Delete by customer id and id.
   *
   * @param customerId the customer id
   * @param id the id
   */
  void deleteByCustomerIdAndId(Long customerId, Long id);

}
