FROM openjdk:17-jdk-slim
COPY target/your-app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--custom.server-ip=${CUSTOM_SERVER_IP}"]