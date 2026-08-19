---
name: cu-docker-stack
description: 本机 docker 上 cu-* 容器是另一项目的栈，勿混淆
metadata:
  type: project
---

本机 docker 曾运行 `cu-*` 容器栈（cu-nacos@8848、cu-mysql@3309、cu-frontend@80、cu-gateway-service 等），属于另一个项目，与本项目无关，当前已停止。

若这些容器再次出现并占用 8848/9848/3306 等端口，注意区分，不要依赖它们跑本项目（本项目用远程 Nacos `hw.crabapples.cn`，见 [[remote-nacos-hw]]）。
