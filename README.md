# recargapay Wallet Service
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?logo=Apache%20Maven&logoColor=white)

Rest API for Wallet Service exercise.

## Features
- **Create Wallet:** Allow the creation of wallets for users.
- **Retrieve Balance:** Retrieve the current balance of a user's wallet.
- **Retrieve Historical Balance:** Retrieve the balance of a user's wallet at a specific point in the past.
- **Deposit Funds:** Enable users to deposit money into their wallets.
- **Withdraw Funds:** Enable users to withdraw money from their wallets.
- **Transfer Funds:** Facilitate the transfer of money between user wallets.

## Design
The application is designed as a Rest API, using Java 17, SpringBoot 3 and Maven technologies.

At the design level, a Controller was implemented that has all the required operations listed above. This Controller connects to a Service that implements all operations and uses a Repository to connect to an in-memory database (H2), thus optimizing response times.

Due to time constraints, only two unit tests were implemented.

## Documentation
With the app running, visit the Swagger web page:

```sh
http://localhost:8080/swagger-ui/index.html
```

## Installation
- You need Java 17 installed in your system.
- You need Maven 3+ installed in your system.
- (Optional) You need Docker installed in your system.
- Clone the repository and download the code with this command:

```sh
git clone https://github.com/hfilippi/recargapay-wallet.git
```

## Run
In the root path of the project, execute the following commands:

```sh
mvn clean install
mvn spring-boot:run
```

Or with **Docker**:

```sh
mvn clean install
docker build --tag=<image_name>:latest .
docker run -p 8080:8080 <image_name>:latest
```

## Test
To run the unit tests, in the root path of the project, execute the following command:

```sh
mvn test
```

## Authors
[Horacio Filippi](mailto:hfilippi@gmail.com)
