FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cloud-congig-server.jar
ENTRYPOINT ["java","-jar","/cloud-congig-server.jar"]
EXPOSE 9296