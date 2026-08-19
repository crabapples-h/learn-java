# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 规则

- 本项目所有提示、对话、输出均使用中文
- 所有项目记忆必须保存在项目目录下（如 `.claude/memory/`），通过 git 管理，禁止保存在用户目录（`~/.local/share/mimocode/` 等）

## Repo shape

Multi-module Maven learning/demo project for the Java + Spring ecosystem. Group `cn.crabapples`, **Java 17** (root pom default), **Spring Boot 3.2.5**, **Spring Cloud 2023.0.1**, **Spring Cloud Alibaba 2023.0.3.2**. This is the Spring Boot 3 line, so new code uses the `jakarta.*` namespace, not `javax.*`.

The root pom (`packaging=pom`) aggregates these modules — nothing else is in the reactor:

```
learn/                → standalone Java learning code (threads, IO, Redis, Kafka…) — NOT a Spring Boot app
design-pattern/       → GoF pattern demos
module-base-core/     → shared base library (MyBatis-Plus, security, utils). Parent = modules, not root.
modules/              → LIBRARY modules (module-system, module-ai, module-file-upload, module-gateway, module-socket)
modules-application/  → DEPLOYABLE Spring Boot apps that depend on the matching library module
frontend/             → Vue2/Vue3/Electron/mpvue projects, built via frontend/pom.xml. Only 5 sub-projects are reactor modules: frontend-vue2, frontend-vue3, frontend-mpvue, frontend-hbuildx, frontend-electron (frontend-react is commented out). Other dirs (app/, learn-vue2/, new-year-time/, frontend-react/, frontend-vue2-new/) are standalone, not built by Maven.
plugins/              → turing-api, mail-sender, code-generator
learn-byte-buddy/     → ByteBuddy experiments (packaging=pom, multi-module)
document/             → learning docs/notes (JVM.md, k8s install notes) — not a Maven module
```

Outside the reactor (build separately):
- `camunda-server/` — standalone Camunda BPM app on **Spring Boot 3.5.5** (different Spring generation than the rest)
- `redis-server/` — embedded Redis
- `docker-compose/` — local infra (Nacos cluster, Redis sentinel/cluster, Sentinel dashboard). Bring these up before running apps that need Nacos.

Ignore the top-level dirs `module-ai-service/`, `module-stream/`, `server/`, `gateway/` (and the stale `module-file-upload/` / `module-gateway/`): they are untracked IntelliJ leftovers containing only gitignored `target/`, `rebel.xml`, `*.iml` — **NOT** Maven modules. The real modules live under `modules/` and `modules-application/`.

**`modules/` vs `modules-application/` is the key distinction**: don't put runnable app code in `modules/`, and don't reimplement library code in `modules-application/`. Each app in `modules-application/` typically depends on one matching library in `modules/`.

> Java 17 is the default, but `learn/` and `module-base-core/` override `java.version` back to 11 — they compile at 11 while everything else compiles at 17.

## Build & run

```bash
# Full backend build (skip the frontend aggregator)
# NOTE: a bare `mvn clean install` at root ALSO builds the frontend — each frontend module
# runs `yarn install && yarn build` (exec-maven-plugin, prepare-package). Use the `-pl` list
# below to skip the frontend aggregator.
mvn clean install -pl learn,design-pattern,plugins,learn-byte-buddy,module-base-core,modules,modules-application

# Single module (add -am to also build its dependencies)
mvn clean install -pl modules-application/system-app -am

# Profile controls env-specific values (mainly nacos.server-addr). Default is `mac` (localhost:8848).
mvn clean install -P windows    # or linux, mac

# Frontend (Vue3 example) — package-lock.json / yarn.lock are gitignored, always reinstall
cd frontend/frontend-vue3 && npm install && npm run build
```

Tests: `modules-application` sets surefire `skipTests=true` by default. To actually run tests there: `mvn test -DskipTests=false`. Single test: `mvn test -Dtest=ClassName#method -pl <module>`.

## Configuration gotchas

- **Maven profiles** switch `nacos.server-addr` between `127.0.0.1:8848` (mac default), `172.16.8.70:8850` (windows/linux), and a commented-out `hk.crabapples.cn:8848`. Verify which profile is active before running locally, and start Nacos via `docker-compose/nacos/` if needed.
- **`lib/` → `BOOT-INF/lib/`**: the root pom bundles any local JARs from `lib/` into fat JARs as build resources. Add JARs there instead of installing to local Maven repo.
- Resource filtering is on for `src/main/resources`, and `**/*.xml|json|ftl` under `src/main/java` are copied as resources — keep MyBatis mapper XMLs alongside their mappers under `src/main/java`.
- `modules-application` disables filtering on many binary extensions (woff/ttf/pdf/mp4/…). If you add a new binary resource type that gets corrupted, add its extension to the `nonFilteredFileExtensions` list.
- MyBatis-Plus is the active ORM. MyBatis-Flex is referenced in `BUG.md` as a known pain point — prefer MyBatis-Plus for new code.
- `BUG.md` tracks known frontend/ORM issues. Check it before touching those areas.

## Docs to read first when adding features

- `README.md` — chronological changelog (Chinese). Useful for finding when/where a demo was added, not for architecture.
- `BUG.md` — short list of known issues (frontend menu bugs, mybatis-flex XML pain, nacos 2.5 JDK compat).
