FROM ubuntu:latest
LABEL authors="mshe"
WORKDIR /data
ENV GAME_MODE="survival"
ENV DIFFICULTY="easy"
ENV LEVEL_NAME="server"
ENV LEVEL_SEED=""
EXPOSE 19132
EXPOSE 19133
#VOLUME /data/server.properties
COPY bedrock-server.zip /data/app.zip
RUN apt-get update && \
    apt-get install unzip wget libcurl4 -y && \
    unzip /data/app.zip -d /data/ && \
    echo 'sed -i "s/^gamemode=.*/gamemode=$GAME_MODE/" /data/server.properties' >> /data/init.sh && \
    echo 'sed -i "s/^difficulty=.*/difficulty=$DIFFICULTY/" /data/server.properties' >> /data/init.sh && \
    echo 'sed -i "s/^level-name=.*/level-name=$LEVEL_NAME/" /data/server.properties' >> /data/init.sh && \
    echo 'sed -i "s/^level-seed=.*/level-seed=$LEVEL_SEED/" /data/server.properties' >> /data/init.sh && \
    chmod +x /data/init.sh
CMD /data/init.sh && /data/bedrock_server
#CMD ["/bin/bash /data/init.sh" , "/data/bedrock_server"]
