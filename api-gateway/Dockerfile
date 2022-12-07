FROM eclipse-temurin:11-jre

MAINTAINER raj

RUN jlink --no-header-files --no-man-pages --compress=2 --strip-debug --add-modules java.base,java.logging,java.sql,java.naming,java.management,java.instrument,java.desktop,java.security.jgss --output /usr/lib/jvm/spring-boot-runtime

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"] 
