package rmm.services.server.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rmm.services.server.persistence.entity.RmmDeviceTypeToServiceCostDao;

/**
 * The Interface RmmDeviceTypeToServiceCostDaoRepository CRUD operations for DEVICE_TYPE_SERVICE_COST table
 */
@Repository
public interface RmmDeviceTypeToServiceCostDaoRepository extends CrudRepository<RmmDeviceTypeToServiceCostDao, Long> {

  /**
   * Gets the list of devices cost by grouping all relation between devices and device type by customer id
   * JOIN 3 tables DEVICE, DEVICE_TYPE and DEVICE_TYPE_SERVICE_COST to get list of devices cost based on device type
   *
   * @param customerId the customer id
   * @return the services cost
   */
  @Query("select cost from RmmDeviceDao  device " 
      + " inner join RmmDeviceTypeToServiceCostDao cost "
      + " on cost.deviceType.id=device.deviceType.id " 
      + " where device.customerId = :customerId ")
  public List<RmmDeviceTypeToServiceCostDao> getDevicesCost(Long customerId);

}
