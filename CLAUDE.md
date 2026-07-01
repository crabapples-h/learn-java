# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repo shape

Multi-module Maven learning/demo project for the Java + Spring ecosystem. Group `cn.crabapples`, **Java 11**, Spring Boot 2.6.13, Spring Cloud 2021.0.4, Spring Cloud Alibaba 2021.0.4.0.

The root pom (`packaging=pom`) aggregates these modules — nothing else is in the reactor:

```
learn/                → standalone Java learning code (threads, IO, Redis, Kafka…) — NOT a Spring Boot app
design-pattern/       → GoF pattern demos
module-base-core/     → shared base library (MyBatis-Plus, security, utils). Parent = modules, not root.
modules/              → LIBRARY modules (module-system, module-ai, module-file-upload, module-gateway, module-socket)
modules-application/  → DEPLOYABLE Spring Boot apps that depend on the matching library module
frontend/             → Vue2/Vue3/Electron/mpvue projects, built via frontend/pom.xml (react module is commented out)
plugins/              → turing-api, mail-sender, code-generator
learn-byte-buddy/     → ByteBuddy experiments (packaging=pom, multi-module)
```

Outside the reactor (build separately):
- `camunda-server/` — standalone Camunda BPM app on **Spring Boot 3.5.5** (different Spring generation than the rest)
- `redis-server/` — embedded Redis
- `docker-compose/` — local infra (Nacos cluster, Redis sentinel/cluster, Sentinel dashboard). Bring these up before running apps that need Nacos.

**`modules/` vs `modules-application/` is the key distinction**: don't put runnable app code in `modules/`, and don't reimplement library code in `modules-application/`. Each app in `modules-application/` typically depends on one matching library in `modules/`.

## Build & run

```bash
# Full backend build (skip the frontend aggregator)
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
- `AGENTS.md` — same module map as above, slightly more prose. Keep it and CLAUDE.md consistent when the layout changes.
- `BUG.md` — short list of known issues (frontend menu bugs, mybatis-flex XML pain, nacos 2.5 JDK compat).
