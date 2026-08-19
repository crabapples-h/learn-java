---
name: feedback-output-and-memory
description: 用户要求：中文输出、记忆存项目内 .claude/memory、勿存用户主目录
metadata:
  type: feedback
---

用户工作偏好（2026-08-19 明确）：
- 所有提示/对话/输出用中文
- 记忆保存在**当前项目路径内**的 `.claude/memory/`（Claude 的记忆库），通过 git 管理，**不要**保存在用户主目录
- 每次会话开始时主动读取 `.claude/memory/MEMORY.md` 索引
- bug 修复流程：先写文档 → 保存记忆 → 执行修复 → 更新记忆
- 用户的 Maven 仓库在 `$MAVEN_HOME/repo`（见 [[maven-local-repo]]）

**Why:** 用户希望记忆随项目走、可 git 管理。2026-08-19 已将原 `.mimocode/` 中的记忆并入本目录并删除 `.mimocode/`。
**How to apply:** 新会话保存记忆时写入本项目 `.claude/memory/` 下的独立文件（frontmatter），并在该目录 `MEMORY.md` 索引。
