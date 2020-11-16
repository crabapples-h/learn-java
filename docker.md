##docker启动容器
* *--network local-network --network-alias alias  指定需要加入的网络和在网络中的别名*
* *--restart=always -d 自动重启,后台启动*
* *-p 80:8080 --name name 端口映射和指定容器别名*
---
>docker启动mysql &emsp; **ports:1001x**
* *-v $(pwd)/data/mysql:/var/lib/mysql 映射本地路径*
* *-e MYSQL_ROOT_PASSWORD=root 启动参数(设置root密码)*
```
docker run \
    --network local-network --network-alias mysql5 \
    --restart=always -d \
    -v $(pwd)/data/mysql5:/var/lib/mysql \
    -p 10011:3306 --name mysql5 \
    -e MYSQL_ROOT_PASSWORD=root \
    mysql:5.7
```
``` shell script
docker run \
    --network local-network --network-alias mysql8 \
    --restart=always -d \
    -v $(pwd)/data/mysql8:/var/lib/mysql \
    -p 10012:3306 --name mysql8 \
    -e MYSQL_ROOT_PASSWORD=root \
    mysql:8.0.22 
```
---
>docker启动mongo &emsp; **ports:1002x**
* *-v $(pwd)/data/mongo:/data/db 映射本地路径*
```
docker run \
    --network local-network --network-alias mongo \
    --restart=always -d \
    -v $(pwd)/data/mongo:/data/db \
    -p 10021:27017 --name mongodb \
    mongo:4.4.1
```
---
>docker启动redis &emsp; **ports:1003x**
* *-v $(pwd)/data/redis:/data 映射本地路径(数据目录)*
* *-v $(pwd)/conf/redis.conf:/etc/redis/redis.conf 映射本地路径(配置文件)*
* *redis-server /etc/redis/redis.conf 运行容器的redis-server命令，指定redis配置文件*
* *--appendonly yes 容器内部参数(开启redis数据持久化)*
```
docker run \
    --network local-network --network-alias redis \
    --restart=always -d \
    -v $(pwd)/data/redis:/data \
    -v $(pwd)/conf/redis.conf:/etc/redis/redis.conf \
    -p 10031:6379 --name redis \
    redis:6.0.9 \
    redis-server /etc/redis/redis.conf \
    --appendonly yes
```
##
####docker启动jumpserver &emsp; **port:30000 &emsp; port:2222**
* 使用已有数据库(需要先建表)
``` mysql
-- 建表 
  create database jumpserver default charset 'utf8' collate 'utf8_bin';
-- 授权给jumpserver用户 
  grant all on jumpserver.* to 'jumpserver'@'%' identified by 'weakPassword';
```
  * *-v $(pwd)/data/jumpserver:/opt/jumpserver/data 映射本地路径*
  * *-p 30000:80 -p 2222:2222 --name jms_all 端口映射和指定容器别名*
  * *-e SECRET_KEY=$SECRET_KEY 启动参数(从系统变量获取)*
  * *-e BOOTSTRAP_TOKEN=$BOOTSTRAP_TOKEN 启动参数(从系统变量获取)*
  * *-e DB_HOST=127.0.0.1 启动参数(数据库地址)*
  * *-e DB_PORT=3306 启动参数(数据库端口)*
  * *-e DB_USER=root 启动参数(数据库用户名)*
  * *-e DB_PASSWORD=root 启动参数(数据库密码)*
  * *-e DB_NAME=jumpserver 启动参数(数据库名称)*
  * *-e REDIS_HOST=127.0.0.1 启动参数(redis地址)*
  * *-e REDIS_PORT=6379 启动参数(redis端口)*
  * *-e REDIS_PASSWORD=xxx 启动参数(redis密码)*
  * *--privileged=true 容器内部参数*
```
docker run \
    --network local-network --network-alias jumpserver \
    --restart=always -d \
    -v $(pwd)/data/jumpserver:/opt/jumpserver/data \
    -p 30000:80 -p 2222:2222 --name jms_all \
    -e SECRET_KEY=AAABBBCCCDDDEEEFFFGGGHHHIIIJJJKKK \
    -e BOOTSTRAP_TOKEN=ABCDEFGHIJK \
    -e DB_HOST=127.0.0.1 -e DB_PORT=3306  -e DB_USER=root  -e DB_PASSWORD=root -e DB_NAME=jumpserver \
    -e REDIS_HOST=127.0.0.1  -e REDIS_PORT=6379 -e REDIS_PASSWORD=password \
    jumpserver/jms_all:v2.4.3 \
    --privileged=true
```
---
* 使用内置数据库
  * *-v $(pwd)/data/jumpserver:/opt/jumpserver/data 映射本地路径*
  * *-v $(pwd)/data/jumpserver/mysql:/var/lib/mysql 映射本地路径*
  * *-p 30000:80 -p 2222:2222 --name jms_all 端口映射和指定容器别名*
  * *-e SECRET_KEY=$SECRET_KEY 启动参数(从系统变量获取)*
  * *-e BOOTSTRAP_TOKEN=$BOOTSTRAP_TOKEN 启动参数(从系统变量获取)*
  * *--privileged=true 容器内部参数*
```
docker run \
    --network local-network --network-alias jumpserver \
    --restart=always -d \
    -v $(pwd)/data/jumpserver:/opt/jumpserver/data \
    -v $(pwd)/data/jumpserver/mysql:/var/lib/mysql \
    -p 30000:80 -p 2222:2222 --name jms_all \
    -e SECRET_KEY=$SECRET_KEY \
    -e BOOTSTRAP_TOKEN=$BOOTSTRAP_TOKEN \
    jumpserver/jms_all:v2.4.3 \
    --privileged=true 
```
##
####docker启动jenkins &emsp; **port:30010 &emsp; port:50000**
  * *-v $(pwd)/data/jenkins-data:/var/jenkins_home 映射本地路径*
  * *-v /var/run/docker.sock:/var/run/docker.sock 映射本地路径*
  * *-p 30010:8080  -p 50000:50000 --name jenkins 端口映射和指定容器别名*
  * *-u root 制定启动用户*
  * *--rm 容器退出后删除文件(不可和-d共存)*
```
docker run \
    --network local-network --network-alias jenkins \
    --restart=always -d \
    -v $(pwd)/data/jenkins-data:/var/jenkins_home \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -p 30010:8080 -p 50000:50000 --name jenkins \
    -u root \
    --rm \
    jenkinsci/blueocean 
```
##
####docker启动elasticsearch &emsp; **port:49200 &emsp; port:49300**
```
docker run \
    --network local-network --network-alias elasticsearch \
    --restart=always -d \
    -p 49200:9200 -p 49300:9300 \
    -e "discovery.type=single-node" \
    docker.elastic.co/elasticsearch/elasticsearch:7.10.0
```
##
####docker启动gitlab &emsp; **port:40443 &emsp; port:40080 &emsp; port:40022**
```
docker run \
    --network local-network --network-alias gitlab \
    --restart=always -d \
    -v $(pwd)/conf/gitlab/config:/etc/gitlab \
    -v $(pwd)/data/gitlab/logs:/var/log/gitlab \
    -v $(pwd)/data/gitlab/data:/var/opt/gitlab \
    -p 40443:443 -p 40080:80 -p 40022:22 --name gitlab \
    gitlab/gitlab-ce
```
