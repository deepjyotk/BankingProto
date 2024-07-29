# Banking Application with gRPC

This project is a simulation of a banking application developed using gRPC in Java. The application demonstrates the use of gRPC for client-server communication, ensuring efficient, scalable, and high-performance interactions. The project is structured with clean code principles and includes proper integration tests between the client and server applications.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Benefits of gRPC](#benefits-of-grpc)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [Contributing](#contributing)

## Overview

This banking application allows users to perform typical banking operations such as creating accounts, checking balances, and transferring funds. The application leverages gRPC for communication between the client and server, providing a robust framework for RPC (Remote Procedure Call) implementation.

## Features

- Account creation
- Balance inquiry
- Fund transfer
- gRPC-based client-server communication
- Clean code with proper abstraction and modularity
- Comprehensive integration tests

## Benefits of gRPC

gRPC offers several advantages for building scalable and efficient applications:

1. **High Performance**: gRPC uses HTTP/2 for transport, which supports multiplexing multiple requests over a single connection, reducing latency and improving throughput.
2. **Strongly Typed Contracts**: With Protocol Buffers (protobuf), gRPC ensures type safety and contract enforcement, reducing errors and providing clear API documentation.
3. **Language Agnostic**: gRPC supports multiple programming languages, allowing different parts of the application to be written in different languages while still communicating seamlessly.
4. **Streaming**: gRPC supports streaming in both directions, enabling real-time data exchange and long-lived connections.
5. **Efficient Serialization**: Protocol Buffers are highly efficient for both serialization and deserialization, resulting in smaller message sizes and faster processing.
6. **Built-in Load Balancing and Retries**: gRPC has built-in support for load balancing and automatic retries, enhancing the reliability of distributed systems.

## Technologies Used

- Java
- gRPC
- Protocol Buffers
- Maven
- JUnit (for testing)

## Setup and Installation

### Prerequisites

- Java 17 or higher
- Maven
- Protobuf Compiler (protoc)

### Installation Steps

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/banking-grpc.git
   cd banking-grpc
   ```

2. Compile the protobuf files:
   ```sh
   mvn clean compile
   ```

3. Build the project:
   ```sh
   mvn package
   ```

## Running the Application

### Running the Server

Start the gRPC server:
```sh
run the common/Demo.java file
```

### Running the Client

In a separate terminal, run the client application:
```sh
run the client/GrpcClient.java file
```

## Running Tests

To run the integration tests, execute:
```sh
mvn test
```

The tests are designed to verify the interactions between the client and server, ensuring that all operations perform as expected.

## Contributing

We welcome contributions to improve the project! Please fork the repository and create a pull request with your changes. Ensure that your code follows the established coding standards and includes tests for any new functionality.

