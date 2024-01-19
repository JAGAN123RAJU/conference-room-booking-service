FROM openjdk:17-oracle as build
MAINTAINER jagan
COPY target/conference-room-booking-service-0.0.1-SNAPSHOT.jar conference-room-booking-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/conference-room-booking-service-0.0.1-SNAPSHOT.jar"]