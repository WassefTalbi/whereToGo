FROM openjdk:17
EXPOSE 1998
ADD target/user-service-0.0.1.jar user.jar
ENTRYPOINT ["java", "-jar", "user.jar"]