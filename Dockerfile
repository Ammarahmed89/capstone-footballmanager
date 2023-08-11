FROM openjdk:18

ENV ENVIRONMENT=prod

LABEL maintainer=weber.florian@neuefische.de

EXPOSE 8081

ADD backend/target/app.jar app.jar

CMD [ "sh", "-c", "java -jar /app.jar" ]