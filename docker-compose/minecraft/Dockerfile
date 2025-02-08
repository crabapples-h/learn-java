FROM ubuntu:latest
LABEL authors="mshe"
WORKDIR /data
EXPOSE 19132
EXPOSE 19133
VOLUME /data/worlds
#VOLUME /data/server.properties
#VOLUME /data
COPY bedrock-server-1.21.51.02.zip /data/app.zip
RUN apt-get update && apt-get install unzip wget libcurl4 -y
RUN unzip /data/app.zip -d /data/
RUN sed -i 's/^gamemode=.*/gamemode=survival/' server.properties
RUN sed -i 's/^difficulty=.*/difficulty=hard/' server.properties
RUN sed -i 's/^level-name=.*/level-name=/' server.properties
RUN sed -i 's/^level-seed=.*/level-seed=/' server.properties

#CMD ["bedrock_server"]
ENTRYPOINT cd /data
ENTRYPOINT ./bedrock_server
