---
name: learning-elasticsearch
description: Elasticsearch 学习笔记：核心概念、索引/文档操作、路由与版本控制
metadata:
  type: reference
---

来源：`learn/src/main/java/elasticsearch/README.md`（完整示例见该文件）。

**核心概念**：集群(cluster，绿/黄/红三态) → 节点(node) → 索引(index≈数据库) → 类型(type≈表，7.x 后一索引仅一类型) → 文档(document≈行，JSON) → 分片(shards，默认 5 主片+1 副本) → 副本 → Mapping / Analyzer / Settings

**es vs 关系型数据库**：索引≈库、类型≈表、文档≈行、DSL≈SQL

**常用操作**（9200 端口）：
- 索引：`PUT /{index}` 建索引；`PUT /{index}/_settings` 改副本数/开关写权限；`DELETE /{index}` 删索引；`POST /_reindex` 复制（只复制数据）
- 文档：`PUT /{index}/_doc/{id}` 指定 id 添加/覆盖；`POST /{index}/_doc` 自动 id；`GET/POST /{index}/_mget` 批量查询；`POST /{index}/_update/{id}` 部分更新（可用 painless 脚本，`ctx._source` 上下文）；`DELETE /{index}/_doc/{id}` 删除；`POST /{index}/_delete_by_query` 条件删除
- 别名：`POST /_aliases` 增删；`GET /_alias`

**路由**：`shard = hash(routing) % num_of_primary_shards`；默认 routing 为文档 id；自定义 routing 后查询/删除/更新需带同一 routing

**版本控制（乐观锁）**：ES6.7 前用 `version`+`version_type`（内部 version 每次 +1；外部 `external` 要求版本号更大）；6.7 后用 `if_seq_no` + `if_primary_term`

**分词器**：标准/简单/空格/停用/关键词/正则/语言/指纹；中文用 IK 分词器（elasticsearch-analysis-ik，可配本地/远程热更新词库）
