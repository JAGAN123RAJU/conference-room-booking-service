# conference-room-booking-service
Conference room booking application

# Description
This is a conference room booking application microservice.

The application has been designed in a way that at any given time, 

a)the user can give the meeting time slot and based on that, the available rooms would be given to the user.

b)The user should be able to book the room by providing the time slots and the participant count.


The application has been added features like logging, Swagger suite, health check monitoring, dockerization.

# Getting Started
#Pre-requisites
JDK 17

Git

## Build & Run

1.Checkout the conference-room-booking-service from the git repo
git clone https://github.com/JAGAN123RAJU/conference-room-booking-service.git

2.Build conference-room-booking-service using the below mentioned command.
mvn clean install

3.Access the local swagger at http://localhost:8080/swagger-ui/index.html

4.Access the health at http://localhost:8080/api/actuator/health

5.Access the H2 DB at http://localhost:8080/h2-console/







