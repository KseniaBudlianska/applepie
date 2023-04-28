FROM openjdk:11
EXPOSE 8080
ADD /build/libs/applepie-0.0.1-SNAPSHOT.jar applepie-jar.jar
ENTRYPOINT ["java", "-jar", "applepie-jar.jar"]