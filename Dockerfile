FROM openjdk:17-jdk-slim
MAINTAINER josesaid.com
COPY target/*.jar hotel_app_v1.0.jar
ENTRYPOINT ["java","-jar","/hotel_app_v1.0.jar"]