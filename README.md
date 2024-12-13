# Car-lease Platform

This is a simple Car-lease Platform API that uses microservices to manage vehicles and customer data and calculate lease rates. The API is built using Spring Boot and uses an API Gateway, Eureka for service discovery, Docker for containerization, and Swagger for API documentation.

### Overview
The Car-lease Platform API provides a set of RESTful endpoints for brokers and leasing companies to manage customer data, vehicle information, and lease rates. The API calculates the monthly lease payments based on certain parameters such as the number of kilometers per year, the duration of the contract, the interest rate, and the net price of the vehicle.

### Project structure

- **API Gateway**: A gateway that acts as an access point for the microservices.
- **Eureka**: Service Discovery for registering the microservices.
- **Auth Service**: Service for the authentication inside the project.
- **Customer Service**: Microservice for managing customer data.
- **Car Service**: Microservice for managing vehicle information.
- **Lease Rate Service**: Microservice that calculates the lease price based on specified parameters.
- **Swagger**: For API documentation.
- **Docker & Docker Compose**: For containerization and easy project execution.

### Features

The API offers the following features:

#### Customer Management

- **POST /api/customers**: Create a new customer.
- **GET /api/customers**: Get a list of customers.
- **GET /api/customers/{id}**: Get customer details based on customer ID.
- **PUT /api/customers/{id}**: Update customer details.
- **DELETE /api/customers/{id}**: Delete customer details.

#### Car Management

- **POST /api/cars**: Add a new car.
- **GET /api/cars**: Get a list of cars details.
- **GET /api/cars/{id}**: Get car details based on car ID.
- **PUT /api/cars/{id}**: Update car details. 
- **DELETE /api/cars/{id}**: Delete car data.

#### Calculate lease rate

- **POST /api/lease-rate**: Calculate lease rate based on the specified parameters (mileage, duration, interest rate, net price).

#### Authentication

The API uses **JWT (JSON Web Tokens)** for token-based security. All requests to the API must contain a valid JWT token in the `Authorization` header.

### Requirements

- **Java 17** (the project code is built with a modern version of Java)
- **Docker** (for containerization)
- **Docker Compose** (to start all services together)
- **Maven** (for building the project artifacts)

## Installation and Execution

Clone the repository to your local machine:

```bash
git clone https://github.com/RedouanBou/carlease-platform.git
cd car-lease-platform
```

2. Docker Compose
Before starting the services, you need to have Docker and Docker Compose installed.

Run the following commands to start all the services (API Gateway, Eureka Server, and the microservices):
```bash
docker-compose up --build
```

This will build and start the containers for the API Gateway, Eureka Server, and all the microservices. The services will be available on the following ports:

API Gateway: http://localhost:8080
Eureka Server: http://localhost:8761
Auth Service: http://localhost:8081
Car Service: http://localhost:8082
Customer Service: http://localhost:8083
Lease Rate Service: http://localhost:8084

3. JWT Authentication
Before you can access the API endpoints, you will first need to obtain a JWT token by logging in. This can be done via an authentication endpoint (e.g. /auth/login) depending on your authentication service implementation.

Use this token in the Authorization header of your requests:
```bash
Authorization: Bearer <jwt_token>
```

4. API Endpoints
You can call the API using the Swagger UI, which is available via the API Gateway:
```bash
http://localhost:PORT_NUMBER/swagger-ui.html
```

5. Unit Tests
The application contains unit tests for each of the customer and car services, among others. They can be executed using Maven:
``` bash
mvn test
```

API Documentation (Swagger)
The API is fully documented using Swagger. You can find the detailed API documentation at:
```bash
http://localhost:PORT_NUMBER/swagger-ui.html
```