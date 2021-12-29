### Background
A Remote Monitoring and Management (RMM) company provides services that monitor
and manage devices.
Breakdown of service costs:

* Antivirus ($5 Windows, $7 Mac). To have antivirus in their devices.
* Cloudberry ($3). To backup data in their devices.
* PSA ($2). Ticketing system for alerts in their devices.
* TeamViewer ($1). Remote connection to devices.

Devices have the following properties:

* Id
* System Name
* Type (Windows Workstation, Windows Server, Mac)

### devices-v1
Customer should be able to get, add, update or delete devices. Implement endpoints to provide these operations.

### services-v1
Customer should be able to add or delete available services to his/her account. Also,
customer can see all services selected. Implement endpoints to provide these
operations. To add the same service more than once is not allowed.

### cost-calculator-v1
Customer needs to calculate the total monthly cost of the deal depending on
selected services and the number of devices in database. Each device cost $4.
Example:
Customer with 2 Windows, 3 Mac with Antivirus, Cloudberry and TeamViewer.

Output: $71

Explanation:
* Devices cost: $20
* Antivirus cost: $31
* Cloudberry cost: $15
* TeamViewer cost: $5

Implement an endpoint to allow users to compute the monthly bill based on the
current state of the data for that customer.