FROM openjdk:17
ARG build_type=
ENV MODE=${build_type}

EXPOSE 8080
ADD /target/*.jar demo.jar

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=${MODE}" ,"demo.jar"]