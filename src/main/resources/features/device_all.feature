@device_all
Feature: Customer should be able to get, add, update, or delete devices

  @device_1
  Scenario: get a device that exists in the database
    Given customer with id 10001 has the following device
      | id    | systemName           | deviceTypeId |
      | 10001 | existing main laptop |            1 |
    And consumer has "READ_USER" role for devices api
    When device get end point is executed for customer id 10001 and device id 10001
    Then device get end point will respond with status 200 and the following device
      | id    | systemName           | deviceTypeId |
      | 10001 | existing main laptop |            1 |

  @device_2
  Scenario: get a device that does not exists in the database
    Given customer with id 10002 that has no devices
    And consumer has "READ_USER" role for devices api
    When device get end point is executed for customer id 10002 and device id 10002
    Then device get end point will respond with status 404

  @device_3
  Scenario: get a device that exists in the database but consumer does not have READ privilege
    Given customer with id 10003 has the following device
      | id    | systemName           | deviceTypeId |
      | 10003 | existing main laptop |            1 |
    And consumer has "NONE_USER" role for devices api
    When device get end point is executed for customer id 10003 and device id 10003
    Then device get end point will respond with status 403

  @device_4
  Scenario: add a new device to the database
    Given customer with id 10004 that has no devices
    And consumer has "CREATE_USER" role for devices api
    When device post end point is executed for customer id 10004 with following request
      | systemName      | deviceTypeId |
      | new main laptop |            1 |
    Then device post end point will respond with status 200 and the following device
      | systemName      | deviceTypeId |
      | new main laptop |            1 |

  @device_5
  Scenario: add a new device to the database but consumer does not have CREATE privilege
    Given customer with id 10005 that has no devices
    And consumer has "NONE_USER" role for devices api
    When device post end point is executed for customer id 10005 with following request
      | systemName      | deviceTypeId |
      | new main laptop |            1 |
    Then device post end point will respond with status 403

  @device_6
  Scenario: add a new device to the database specify device id in request
    Given customer with id 10006 that has no devices
    And consumer has "CREATE_USER" role for devices api
    When device post end point is executed for customer id 10006 with following request
      | id    | systemName      | deviceTypeId |
      | 10006 | new main laptop |            1 |
    Then device post end point will respond with status 400

  @device_7
  Scenario: update existing device
    Given customer with id 10007 has the following device
      | id    | systemName           | deviceTypeId |
      | 10007 | existing main laptop |            1 |
    And consumer has "UPDATE_USER" role for devices api
    When device put end point is executed for customer id 10007 with following request
      | id    | systemName      | deviceTypeId |
      | 10007 | new main laptop |            1 |
    Then device put end point will respond with status 200 and the following device
      | systemName      | deviceTypeId |
      | new main laptop |            1 |

  @device_8
  Scenario: update existing device do not provide device id
    Given customer with id 10008 has the following device
      | id    | systemName           | deviceTypeId |
      | 10008 | existing main laptop |            1 |
    And consumer has "UPDATE_USER" role for devices api
    When device put end point is executed for customer id 10008 with following request
      | systemName      | deviceTypeId |
      | new main laptop |            1 |
    Then device put end point will respond with status 400

  @device_9
  Scenario: update a device that does not exist
    Given customer with id 10009 that has no devices
    And consumer has "UPDATE_USER" role for devices api
    When device put end point is executed for customer id 10009 with following request
      | id    | systemName      | deviceTypeId |
      | 10009 | new main laptop |            1 |
    Then device put end point will respond with status 404

  @device_10
  Scenario: update existing device but consumer does not have UPDATE privilege
    Given customer with id 10010 has the following device
      | id    | systemName           | deviceTypeId |
      | 10010 | existing main laptop |            1 |
    And consumer has "NONE_USER" role for devices api
    When device put end point is executed for customer id 10010 with following request
      | id    | systemName      | deviceTypeId |
      | 10010 | new main laptop |            1 |
    Then device put end point will respond with status 403

  @device_11
  Scenario: delete existing device
    Given customer with id 10011 has the following device
      | id    | systemName           | deviceTypeId |
      | 10011 | existing main laptop |            1 |
    And consumer has "ADMIN_USER" role for devices api
    When device delete end point is executed for customer id 10011 and device id 10011
    Then device delete end point will respond with status 200
    Then device get end point is executed for customer id 10011 and device id 10011
    And device get end point will respond with status 404

  @device_12
  Scenario: delete device that does not exist
    Given customer with id 10012 that has no devices
    And consumer has "DELETE_USER" role for devices api
    When device delete end point is executed for customer id 10012 and device id 10012
    Then device delete end point will respond with status 404

  @device_13
  Scenario: delete existing device but consumer does not have DELETE privilege
    Given customer with id 10013 that has no devices
    And consumer has "NONE_USER" role for devices api
    When device delete end point is executed for customer id 10013 and device id 10013
    Then device delete end point will respond with status 403

  @device_14
  Scenario: get all devices that exists in the database
    Given customer with id 10014 has the following device
      | id    | systemName           | deviceTypeId |
      | 10014 | existing main laptop |            1 |
    And consumer has "READ_USER" role for devices api
    When device get all end point is executed for customer id 10014
    Then device get all end point will respond with status 200 and the following devices
      | id    | systemName           | deviceTypeId |
      | 10014 | existing main laptop |            1 |

  @device_15
  Scenario: get all devices but nothing in databse for customer
    Given customer with id 10015 that has no devices
    And consumer has "READ_USER" role for devices api
    When device get all end point is executed for customer id 10015
    Then device get all end point will respond with status 200
    And device get all end point will respond with empty list

  @device_16
  Scenario: get all devices that exists in the database but consumer does not have READ privilege
    Given customer with id 10016 has the following device
      | id    | systemName           | deviceTypeId |
      | 10016 | existing main laptop |            1 |
    And consumer has "NONE_USER" role for devices api
    When device get all end point is executed for customer id 10016
    Then device get all end point will respond with status 403
