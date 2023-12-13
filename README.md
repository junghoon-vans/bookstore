Bookstore
=========

This is a simple bookstore application written in Java21 and Spring Boot 3.

## Requirements

For building and running the application you need:

- Docker
- JDK 21

## Running the application locally

Start the service(Postgres) for backend.

```bash
docker-compose up
```

Run the application in the local environment.

```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```
