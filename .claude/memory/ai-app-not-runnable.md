---
name: ai-app-not-runnable
description: ai-app 在 Boot 2.x 下无法启动，勿启动
metadata:
  type: project
---

`modules-application/ai-app`（Spring AI 1.0.0-M6）在 Spring Boot 2.6.13 下**无法启动**：`OpenAiChatModel` bean 缺失，`APPLICATION FAILED TO START`。

作者在 `AiService.java` 注释确认："springboot2.x版本无法启动,后续更换为3.x版本"。**不要启动 ai-app**（用户 2026-08-19 明确指示）。
