---
name: maven-local-repo
description: Maven 本地仓库是 $MAVEN_HOME/repo 而非 ~/.m2/repository
metadata:
  type: reference
---

本机 Maven 本地仓库 = `$MAVEN_HOME/repo` = `/Users/mshe/developer/apache-maven-3.9.4/repo`。

`~/.m2/repository` 没有 crabapples 构件，从那里解析会失败。判断依赖是否已装/构件是否过期要看 `$MAVEN_HOME/repo/cn/crabapples/`。
