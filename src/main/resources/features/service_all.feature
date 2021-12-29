@service_all
Feature: Customer should be able to add or delete available services to his/her account.
  Also, customer can see all services selected. Implement endpoints to provide these
  operations. 
  To add the same service more than once is not allowed.

  @service_1
  Scenario: add a new service for customer
    Given customer with id 20001 that has no services
    And consumer has "CREATE_USER" role for services api
    When service post end point is executed for customer id 20001 and service type id 1
    Then service post end point will respond with status 200 and the following service
      | serviceName | serviceTypeId |
      | Antivirus   |             1 |

  @service_2
  Scenario: add a new service for customer  but consumer does not have CREATE privilege
    Given customer with id 20002 that has no services
    And consumer has "NONE_USER" role for services api
    When service post end point is executed for customer id 20002 and service type id 1
    Then service post end point will respond with status 403

  @service_3
  Scenario: add a new service for customer using invalid service type id
    Given customer with id 20003 that has no services
    And consumer has "CREATE_USER" role for services api
    When service post end point is executed for customer id 20003 and service type id 999
    Then service post end point will respond with status 400

  @service_4
  Scenario: add a new service for customer but service is already added
    Given customer with id 20004 that has the following service
      | id    | serviceName | serviceTypeId |
      | 20004 | Antivirus   |             1 |
    And consumer has "CREATE_USER" role for services api
    When service post end point is executed for customer id 20004 and service type id 1
    Then service post end point will respond with status 400

  @service_5
  Scenario: get a service that exists in the database
    Given customer with id 20005 that has the following service
      | id    | serviceName | serviceTypeId |
      | 20005 | Antivirus   |             1 |
    And consumer has "READ_USER" role for services api
    When service get end point is executed for customer id 20005
    Then service get end point will respond with status 200
    And service get end point will return follwing services
      | id    | serviceName | serviceTypeId |
      | 20005 | Antivirus   |             1 |

  @service_6
  Scenario: get a services for cusotmer with no existing records
    Given customer with id 20006 that has no services
    And consumer has "READ_USER" role for services api
    When service get end point is executed for customer id 20006
    Then service get end point will respond with status 200
    And service get end point will respond with empty list

  @service_7
  Scenario: get a service that exists in the database but consumer does not have READ privilege
    Given customer with id 20007 that has the following service
      | id    | serviceName | serviceTypeId |
      | 20007 | Antivirus   |             1 |
    And consumer has "NONE_USER" role for services api
    When service get end point is executed for customer id 20007
    Then service get end point will respond with status 403

  @service_8
  Scenario: delete existing service
    Given customer with id 20008 that has the following service
      | id    | serviceName | serviceTypeId |
      | 20008 | Antivirus   |             1 |
    And consumer has "ADMIN_USER" role for services api
    When service delete end point is executed for customer id 20008 and service id 20008
    Then service delete end point will respond with status 200
    When service get end point is executed for customer id 20008
    And service get end point will respond with status 200
    And service get end point will respond with empty list

  @service_9
  Scenario: delete service that does not exist
    Given customer with id 20009 that has no services
    And consumer has "DELETE_USER" role for services api
    When service delete end point is executed for customer id 20009 and service id 20009
    Then service delete end point will respond with status 404

  @service_10
  Scenario: delete existing service but consumer does not have DELETE privilege
    Given customer with id 20010 that has the following service
      | id    | serviceName | serviceTypeId |
      | 20010 | Antivirus   |             1 |
    And consumer has "NONE_USER" role for services api
    When service delete end point is executed for customer id 20010 and service id 20010
    Then service delete end point will respond with status 403
