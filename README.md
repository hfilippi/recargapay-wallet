# recargapay Wallet Service
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)

Rest API for Wallet Service exercise.

## Features
- **Create Wallet:** Allow the creation of wallets for users.
- **Retrieve Balance:** Retrieve the current balance of a user's wallet.
- **Retrieve Historical Balance:** Retrieve the balance of a user's wallet at a specific point in the past.
- **Deposit Funds:** Enable users to deposit money into their wallets.
- **Withdraw Funds:** Enable users to withdraw money from their wallets.
- **Transfer Funds:** Facilitate the transfer of money between user wallets.

## Design
La aplicación está diseñada como una Rest API, mediante el uso de las tecnologías Java 17, SpringBoot 3 y Maven.

A nivel diseño, se implementó un Controlador que posee todas las operaciones requeridas y listadas anteriormente. Este Controlador se conecta a un Servicio que implementa todas las operaciones y utiliza un Repositorio para conectarse a una base de datos en memoria (H2), optimizando así los tiempos de respuesta.

Por cuestiones de tiempo, solo se implementaron 2 tests unitarios.

## Documentation
With the app running, visit the Swagger page:

```sh
http://localhost:8080/swagger-ui/index.html
```

## Installation
```sh
Installation.
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
