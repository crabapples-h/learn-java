FROM liferay/jdk11:latest
LABEL authors="mshe"
WORKDIR /home/gateway
COPY target/gateway-1.0-SNAPSHOT.jar gateway-1.0.jar
EXPOSE 9092
ENTRYPOINT ["java", "-jar" ,"/home/gateway/gateway-1.0.jar", "-Dspring.cloud.nacos.discovery.server-addr=127.0.0.1:8849"]

