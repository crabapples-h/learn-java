# FROM dockette/openjdk8
#FROM dockette/openjdk8
FROM harbor.crabapples.cn/jdk/jdk11
#FROM harbor.crabapples.cn/jdk/jdk8
VOLUME /logs/csp
EXPOSE 8858
COPY sentinel-dashboard-1.8.8.jar /opt/app.jar


# FROM 指定基础镜像
# FROM centos

# MAINTAINER 指定镜像的维护者信息，一般为邮箱
# MAINTAINER hitredrose@163.com

# RUN 镜像构建时需要执行的命令
# RUN yum install -y wget

# ADD 增加文件，会自动解压
# ADD python.tar.gz /usr/local/

# WORKDIR 设置当前工作目录
# WORKDIR /usr/local/python/

# VOLUME 挂载主机目录
# VOLUME ["/usr/local/python","/usr/local/java/"]

# EXPOSE 暴露端口，注意这里指仅暴露容器的端口，并不会将容器端口与宿主机端口映射。也就是说在使用docker run的时候仍然需要继续使用-p进行端口映射，换言之，EXPOSE更多的作用在于给Dockerfile开发者提供开发端口的提示作用
# EXPOSE 80

# CMD 指定容器启动的时候需要执行的命令，注意CMD只有最后一个命令会生效
# CMD /bin/bash

# ENTRYPOINT 指定容器启动时需要运行的命令，注意ENTRYPOINT命令可以追加命令，即如果有多条ENTRYPOINT命令会全部执行，这是ENTRYPOINT命令和CMD命令最大的区别
# ENTRYPOINT cd /opt
# ENTRYPOINT /bin/bash

# ONBUILD 当构建一个被继承Dockerfile，这个时候就会运行ONBUILD指令

# COPY 类似ADD命令，将文件拷贝到镜像中

# ENV 构建的时候设置环境变量
