FROM openjdk:17-alpine
MAINTAINER Elleined

# Docker MySQL Credentials
ENV MYSQL_HOST=mysql_server
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=rt_message_api_db
ENV PORT=8095

ADD ./target/*.jar rt-messaging-api.jar
EXPOSE 8095
CMD ["java", "-jar", "rt-messaging-api.jar"]
