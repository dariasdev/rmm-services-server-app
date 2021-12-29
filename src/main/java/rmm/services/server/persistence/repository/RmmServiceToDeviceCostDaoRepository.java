package rmm.services.server.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rmm.services.server.persistence.entity.RmmServiceToDeviceCostDao;

/**
 * The Interface RmmServiceToDeviceCostDaoRepository CRUD operation for SERVICE_DEVICE_COST table
 */
@Repository
public interface RmmServiceToDeviceCostDaoRepository extends CrudRepository<RmmServiceToDeviceCostDao, Long> {

  /**
   * Gets the services cost by grouping all relations between devices and services
   * JOIN tables DEVICE , SERVICE and SERVICE_DEVICE_COST to get list of current cost fo services
   *
   * @param customerId the customer id
   * @return the services cost
   */
  @Query("select cost from RmmServiceDao service "
      + " inner join RmmDeviceDao  device on service.customerId=device.customerId "
      + " inner join RmmServiceToDeviceCostDao cost on cost.deviceType.id=device.deviceType.id and cost.serviceType.id=service.serviceType.id "
      + " where device.customerId = :customerId ")
  public List<RmmServiceToDeviceCostDao> getServicesCost(Long customerId);

}
