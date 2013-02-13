echo Run the distributed version: Networking to localhost
start java Middle/Server
echo When bound to object cshop
pause
start java Clients/CustomerClient
start java Clients/CashierClient
start java Clients/BackDoorClient
start java Clients/PickClient
start java Clients/PickClient
start java Clients/DisplayClient
start java Clients/CollectionClient
echo Run Customer applet
start appletviewer WebCustomerClient.htm
