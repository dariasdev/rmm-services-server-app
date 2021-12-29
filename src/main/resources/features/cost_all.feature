@cost_all
Feature: Customer needs to calculate the total monthly cost of the deal depending on selected services and the number of devices in database.

  @cost_1
  Scenario: compute cost for customer with single device and single service
    Given customer with id 30001 has the following devices
      | id    | systemName           | deviceTypeId |
      | 30001 | existing main laptop |            1 |
    And customer with id 30001 with the following services
      | id    | serviceName | serviceTypeId |
      | 30001 | Antivirus   |             1 |
    And consumer has "READ_USER" role for cost api
    When compute cost end point is executed for customer id 30001
    Then compute cost end point will return status 200
    And compute cost end point will return 9.00 as overall cost
    And compute cost end point will return the following services cost detail
      | serviceName | cost |
      | Devices     | 4.00 |
      | Antivirus   | 5.00 |

  @cost_2
  Scenario: compute cost for customer with several devices and services
    Given customer with id 30002 has the following devices
      | id     | systemName       | deviceTypeId |
      | 300021 | windows laptop 1 |            1 |
      | 300022 | windows laptop 2 |            1 |
      | 300023 | macbook 1        |            3 |
      | 300024 | macbook 2        |            3 |
      | 300025 | macbook 3        |            3 |
    And customer with id 30002 with the following services
      | id     | serviceName | serviceTypeId |
      | 300021 | Antivirus   |             1 |
      | 300022 | Cloudberry  |             2 |
      | 300023 | TeamViewer  |             4 |
    And consumer has "READ_USER" role for cost api
    When compute cost end point is executed for customer id 30002
    Then compute cost end point will return status 200
    And compute cost end point will return 71.00 as overall cost
    And compute cost end point will return the following services cost detail
      | serviceName | cost  |
      | Devices     | 20.00 |
      | Antivirus   | 31.00 |
      | Cloudberry  | 15.00 |
      | TeamViewer  |  5.00 |

  @cost_3
  Scenario: compute cost for customer with no devices or services
    Given customer with id 30003 has no devices and no services
    And consumer has "READ_USER" role for cost api
    When compute cost end point is executed for customer id 30003
    Then compute cost end point will return status 404

  @cost_4
  Scenario: compute cost for customer with single device and single service but consumer does not have READ privilege
    Given customer with id 30004 has the following devices
      | id    | systemName           | deviceTypeId |
      | 30004 | existing main laptop |            1 |
    And customer with id 30004 with the following services
      | id    | serviceName | serviceTypeId |
      | 30004 | Antivirus   |             1 |
    And consumer has "NONE_USER" role for cost api
    When compute cost end point is executed for customer id 30004
    Then compute cost end point will return status 403
