FROM liferay/jdk11:latest
LABEL authors="mshe"
WORKDIR /home/gateway
COPY target/gateway-1.0-SNAPSHOT.jar gateway-1.0.jar
EXPOSE 9092
ENV NACOS_ADDR=127.0.0.1:8849
ENV PROFILE=prod
ENTRYPOINT ["java", "-jar" ,"/home/gateway/gateway-1.0.jar"]

