---
name: learning-mq-rabbitmq
description: 消息中间件/RabbitMQ 学习笔记：协议、交换机类型、Spring 使用、死信/延迟队列
metadata:
  type: reference
---

来源：`learn/src/main/java/demo/mq/README.md`（完整示例见该文件）。

**通信协议/产品**：JMS（ActiveMQ/Kafka/WebSphereMQ）、AMQP（RabbitMQ）、MQTT（物联网）、XMPP（即时通讯）；RocketMQ（阿里，订单系统）、Kafka（大数据高吞吐）、ZeroMQ

**RabbitMQ 基本概念**：生产者 → 交换机(Exchange) → 队列(Queue) → 消费者；所有操作基于 channel 而非 connection；routingkey 用于匹配投递。

**交换机类型**：direct（routingkey 完全相等）、topic（支持 `*` 单词 / `#` 多词通配）、fanout（忽略 routingkey 广播到所有绑定队列）、header

**Spring 使用**：
- 声明 Queue/Exchange/Binding（`BindingBuilder.bind(queue()).to(exchange()).with(routingKey)`）
- 消费：`@RabbitListener(queues=..., concurrency="20")` + `@RabbitHandler`，手动确认 `channel.basicAck/basicNack/basicReject`
- 手动确认：`spring.rabbitmq.listener.direct.acknowledge-mode=manual`
- 发送：`rabbitTemplate.convertAndSend(queue, msg)`

**RPC 调用**：`spring.rabbitmq.publisher-returns=true` + `publisher-confirm-type=correlated`，设置 replyAddress 响应队列 + replyTimeout

**消息过期**：队列级 `x-message-ttl` 或消息级 `setExpiration`（字符串，毫秒），两者都设取短者

**死信队列**：创建队列时加 `x-dead-letter-exchange` + `x-dead-letter-routing-key`

**延迟队列**：插件 rabbitmq-delayed-message-exchange（`x-delayed-type` 指定交换机类型）或「消息过期 + 死信队列」组合（A 无消费者设 TTL → 过期进 B 被消费）
