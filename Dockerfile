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


- library api
# IntelliJ
## Super builder in all api
## Controllers test in all api
## Hateoas in all api
## add redis caching
## pagination
- upgrade to java 21
- data faker

# Postman
## Use environments for base url
## Use random api in all api sample data
## fix the sample datas in all api
## have 2 environment for main and dev

- atm machine api
# IntelliJ
## Super builder in all api
## Controllers test in all api
## Hateoas in all api
##add redis caching
## pagination
- upgrade to java 21 and fix lombok
- data faker

# Postman
## Use environments for base url
## Use random api in all api sample data
## fix the sample datas in all api
## have 2 environment for main and dev

- qr code api
# IntelliJ
## Super builder in all api
## Controllers test in all api
## Hateoas in all api
## add redis caching
## pagination
- upgrade to java 21 and fix lombok
- data faker

# Postman
## Use environments for base url
## Use random api in all api sample data
## fix the sample datas in all api
## have 2 environment for main and dev

- email sender api 2 branch
# IntelliJ
## Super builder in all api
## Controllers test in all api
## Hateoas in all api
## add redis caching
## pagination
- upgrade to java 21 and fix lombok
- data faker

# Postman
## Use environments for base url
## Use random api in all api sample data
## fix the sample datas in all api
## have 2 environment for main and dev

- philippine location api 2 branch
# IntelliJ
## Super builder in all api
## Controllers test in all api
## Hateoas in all api
## add redis cachi g
## pagination
- upgrade to java 21 and fix lombok
- data faker

# Postman
## Use environments for base url
## Use random api in all api sample data
## fix the sample datas in all api
## have 2 environment for main and dev

- twilio sms sender api
# IntelliJ
## Super builder in all api
## Controllers test in all api
## Hateoas in all api
## add redis caching
## pagination
- upgrade to java 21 and fix lombok
- data faker

# Postman
## Use environments for base url
## Use random api in all api sample data
## fix the sample datas in all api
## have 2 environment for main and dev

- security question api
# IntelliJ
## Super builder in all api
## Controllers test in all api
## Hateoas in all api
## add rediscaching
## pagination
- upgrade to java 21 and fix lombok
- data faker

# Postman
## Use environments for base url
## Use random api in all api sample data
## fix the sample datas in all api
## have 2 environment for main and dev

- marketplace api 2 branch
# IntelliJ
## Super builder in all api
## Controllers test in all api
##Unit Testing
## add redis caching
## pagination
- upgrade to java 21 and fix lombok
- data faker

# Postman
##Fix postman API Call
## Use environments for base url
## Use random api in all api sample data
## fix the sample datas in all api
## have 2 environment for main and dev

- social media api 2 branch
# IntelliJ
## Super builder in all api //
## post anonymously
##Unit Testing in service
## Controllers test in all api
## Hateoas in all api
## add redis caching
## pagination //
- upgrade to java 21 and fix lombok //
- data faker

# Postman
## Use environments for base url //
## Use random api in all api sample data //
## fix the sample datas in all api //
## have 2 environment for main and dev //
## fix the folder entity and all worjflow

- file system crypthography api
# IntelliJ
## Super builder in all api
## Controllers test in all api
##Unit Testing
## add redis caching
## pagination
- upgrade to java 21 and fix lombok
- data faker

# Postman
##Fix postman API Call
## Use environments for base url
## Use random api in all api sample data
## fix the sample datas in all api
## have 2 environment for main and dev


- rt messaging api
# IntelliJ
## Super builder in all api
## Controllers test in all api
##Unit Testing
## add redis caching
## pagination
- upgrade to java 21 and fix lombok
- data faker

# Postman
##Fix postman API Call
## Use environments for base url
## Use random api in all api sample data
## fix the sample datas in all api
## have 2 environment for main and dev

# Image server api
- pagination //
- create jenkins job //
- unit testing of service and controllers
- add redis caching
- add hateoas
- upgrade to java 21 and fix lombok
- data faker

# Oauth2
- unit test in service
- unit tedt in controller
- add hateoas //
- upgrade to java 21 //
- data faker //