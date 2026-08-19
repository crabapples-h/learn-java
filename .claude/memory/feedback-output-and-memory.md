---
name: feedback-output-and-memory
description: 用户要求：中文输出、记忆存项目内 .claude/memory、勿动 .mimocode、勿存用户主目录
metadata:
  type: feedback
---

用户工作偏好（2026-08-19 明确）：
- 所有提示/对话/输出用中文
- 记忆保存在**当前项目路径内**的 `.claude/memory/`（Claude 自己的记忆库），**不要**写入 `.mimocode/`，**不要**保存在用户主目录
- 用户的 Maven 仓库在 `$MAVEN_HOME/repo`（见 [[maven-local-repo]]）

**Why:** 用户希望记忆随项目走，但独立于 git 跟踪的 `.mimocode`。
**How to apply:** 新会话保存记忆时写入本项目 `.claude/memory/` 下的独立文件（frontmatter），并在该目录 `MEMORY.md` 索引。
