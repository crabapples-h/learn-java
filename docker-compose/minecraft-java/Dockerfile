#FROM aomountainu/openjdk21:latest
FROM ubuntu:latest
LABEL authors="mshe"
WORKDIR /data
#ENV GAME_MODE="survival"
#ENV DIFFICULTY="easy"
#ENV LEVEL_NAME="server"
#ENV LEVEL_SEED=""
EXPOSE 25565
#VOLUME /data/eula.txt
COPY server.jar /data/app.jar
RUN apt-get update && \
    apt-get install libudev-dev openjdk-21-jdk libcurl4 -y && \
    echo "eula=true" > /data/eula.txt


#RUN apt-get update && \
#    apt-get install unzip wget libcurl4 -y && \
#    unzip /data/app.zip -d /data/ && \
#    echo 'sed -i "s/^gamemode=.*/gamemode=$GAME_MODE/" /data/server.properties' >> /data/entrypoint.sh && \
#    echo 'sed -i "s/^difficulty=.*/difficulty=$DIFFICULTY/" /data/server.properties' >> /data/entrypoint.sh && \
#    echo 'sed -i "s/^level-name=.*/level-name=$LEVEL_NAME/" /data/server.properties' >> /data/entrypoint.sh && \
#    echo 'sed -i "s/^level-seed=.*/level-seed=$LEVEL_SEED/" /data/server.properties' >> /data/entrypoint.sh && \
#    chmod +x /data/entrypoint.sh
#CMD /data/entrypoint.sh && /data/bedrock_server
CMD java -Xmx1024M -Xms1024M -jar app.jar
#CMD ["/bin/bash /data/entrypoint.sh" , "/data/bedrock_server"]
