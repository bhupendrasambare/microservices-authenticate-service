FROM openjdk:17
ARG jar_file=target/*.jar
COPY ${jar_file} authenticate.jar
ENTRYPOINT ["java", "-jar", "authenticate.jar"]
EXPOSE 9003