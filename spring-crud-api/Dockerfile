### For arm64 system, uncomment line below and comment or delete line 'FROM openjdk:17-jdk-alpine'
FROM arm64v8/openjdk:17

### For amd64 system, uncomment line below and comment or delete line 'FROM arm64v8/openjdk:17'
#FROM openjdk:17-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]