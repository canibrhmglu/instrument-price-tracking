FROM openjdk:8
VOLUME /tmp
ADD target/instrument-price-tracking-0.0.1-SNAPSHOT.jar instrument-price-tracking.jar
EXPOSE 8181
ENTRYPOINT ["java","-jar","/instrument-price-tracking.jar"]