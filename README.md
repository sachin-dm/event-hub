# Event Hub
This service handles CRUD operations for events and concerts data.

# Pre-Requisites
Before building and running the application, ensure the following are installed:
- Java 11 or higher
- Maven 3.8 or higher
- Docker

## Build the project
`mvn clean compile`

## Run Integration test
To run the integration tests, you need to start a Postgres Docker container with some initial table setups.

Execute the script below to start the Postgres container and create the following tables with dummy data. (Make sure Docker application is running):
1. event_user
2. venue
3. event
4. booking

**Start Containers**
```bash 
cd ./external-services
chmod +x run.sh
./run.sh
```

**Stop Containers** 
```bash
cd ./external-services
chmod +x stop.sh
./stop.sh
```

## Generate Build/Binary
As package generation needs to run unit and integration tests, make sure you run above script to make sure required containers are running

`mvn clean package`
