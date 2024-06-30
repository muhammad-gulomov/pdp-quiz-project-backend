FROM openjdk:17
WORKDIR /app
COPY target/myapp666.jar /app/myapp666.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","myapp666.jar"]