FROM openjdk:17-oracle

LABEL "maintainer"="Ataugur Karatas <ataugurkaratas@gmail.com>"
LABEL "description"="Credit Application"

ARG APP=app.jar
WORKDIR /app

COPY out/artifacts/DefineX_java_spring_practicum_jar/DefineX-java-spring-practicum.jar ${APP}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]