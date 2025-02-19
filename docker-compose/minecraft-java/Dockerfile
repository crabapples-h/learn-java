FROM ubuntu:latest
LABEL authors="mshe"
WORKDIR /data
ENV GAME_MODE="survival"
ENV DIFFICULTY="easy"
ENV LEVEL_NAME="server"
ENV LEVEL_SEED=""
ENV ONLINE_MODE="true"
EXPOSE 25565
COPY server.jar /data/app.jar
RUN apt-get update && \
    apt-get install libudev-dev openjdk-21-jdk libcurl4 -y && \
    echo "eula=true" > /data/eula.txt && \
    echo 'sed -i "s/^gamemode=.*/gamemode=$GAME_MODE/" /data/server.properties' >> /data/init.sh && \
    echo 'sed -i "s/^difficulty=.*/difficulty=$DIFFICULTY/" /data/server.properties' >> /data/init.sh && \
    echo 'sed -i "s/^level-name=.*/level-name=$LEVEL_NAME/" /data/server.properties' >> /data/init.sh && \
    echo 'sed -i "s/^level-seed=.*/level-seed=$LEVEL_SEED/" /data/server.properties' >> /data/init.sh && \
    echo 'sed -i "s/^online-mode=.*/online-mode=$ONLINE_MODE/" /data/server.properties' >> /data/init.sh && \
    chmod +x /data/init.sh
#CMD /data/init.sh && java -Xmx1024M -Xms1024M -jar app.jar
CMD java -Xmx1024M -Xms1024M -jar app.jar
#CMD ["/bin/bash /data/init.sh" , "/data/bedrock_server"]
