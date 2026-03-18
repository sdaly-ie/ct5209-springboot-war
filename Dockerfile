FROM tomcat:10.1-jdk17-temurin

COPY stephenspetitions.war /usr/local/tomcat/webapps/stephenspetitions.war

EXPOSE 8080