# test-bank application

test-bank application uses

- Spring boot 2.4.3
- Junit 4 - as a unit test framework
- Mockito - as a mocking framework
- Requires java 11

To start application it is necessary to execute:

```sh
mvn clean install 
cd target/ 
java -jar test-app.jar 
```

or if you do not have Maven and Java on your local machine you can compile and run using docker-compose

```sh
docker-compose build
docker-compose up
```

When application is started open http://localhost:8080 - you will be forwarded to swagger-ui

Simple test scenario:

1. Create a new customer using /management/customers endpoint
2. Create "Current Account" using /bank/customers/add-account endpoint by sending customerId and initialCredit
   once initialCredit is greater than 0, deposit transaction will be processed.
3. Generate report by invoking /bank/customers/{customerId} or /bank/customers   