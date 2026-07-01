# AGENTS.md

## What this repo is

Multi-module Maven learning/demo project for Java + Spring ecosystem experiments. Group: `cn.crabapples`, Java 11, Spring Boot 2.6.13, Spring Cloud 2021.0.4.

## Module layout

| Directory | Purpose |
|-----------|---------|
| `learn/` | Standalone Java learning code (multithreading, IO, Redis, Kafka, etc.) — no Spring Boot app |
| `design-pattern/` | Design pattern demos (singleton, factory, observer, etc.) |
| `module-base-core/` | Shared base library (MyBatis-Plus, security, utilities) used by `modules/` children |
| `modules/` | Library modules: `module-system`, `module-ai`, `module-file-upload`, `module-gateway`, `module-socket` |
| `modules-application/` | Deployable Spring Boot apps: `system-app`, `ai-app`, `file-upload-app`, `gateway-app`, `socket-app` |
| `frontend/` | Multiple frontend projects (Vue2, Vue3, Electron, mpvue) — built via `frontend/pom.xml`. React module exists but is commented out. |
| `plugins/` | Reusable plugins: `turing-api`, `mail-sender`, `code-generator` |
| `learn-byte-buddy/` | ByteBuddy experiments (packaging=pom, multi-module) |
| `redis-server/` | Embedded Redis server — standalone, not in root Maven reactor |
| `camunda-server/` | Standalone Camunda BPM app — uses **Spring Boot 3.5.5**, not part of the main multi-module build |
| `docker-compose/` | Local infra: Redis sentinel/cluster, Nacos cluster, Sentinel dashboard |

## Build & run

```bash
# Full build (skipping frontend)
mvn clean install -pl learn,design-pattern,plugins,learn-byte-buddy,module-base-core,modules,modules-application

# Single module
mvn clean install -pl learn

# With specific profile (default is mac)
mvn clean install -P mac    # or windows, linux

# Frontend build (Vue3)
cd frontend/frontend-vue3 && npm install && npm run build
```

## Key constraints

- **Java 11** is required (set in root pom.xml `<java.version>11</java.version>`)
- Maven profiles control environment-specific values (e.g. nacos address). Default profile is `mac`
- `module-base-core` is the shared base — its `pom.xml` parent is `modules`, not root directly
- `modules/` (libraries) vs `modules-application/` (deployable apps) — don't confuse the two
- The `learn/` module is standalone code snippets, not part of the Spring Cloud app structure
- `BUG.md` tracks known frontend issues — check before modifying those modules

## Gotchas

- `lib/` directory is included in build resources as `BOOT-INF/lib/` — local JARs are bundled into fat JARs
- Nacos config address differs per profile; verify which profile is active if running locally
- Frontend `package-lock.json` and `yarn.lock` are gitignored — always run `npm install` / `yarn` before building frontend
- MyBatis-Plus is the active ORM; MyBatis-Flex is commented out but partially referenced in `BUG.md`
- Spring Cloud Alibaba dependencies (Sentinel, Nacos) are used — some features require running infrastructure
- `modules-application` skips tests by default (surefire `skipTests=true`) — run with `-DskipTests=false` to actually execute them
