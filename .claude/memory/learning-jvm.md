---
name: learning-jvm
description: JVM 学习笔记：内存模型、GC、CPU/堆内存诊断方法
metadata:
  type: reference
---

来源：`document/JVM.md`（项目内 JVM 核心笔记）。

**JVM 内存模型**
- 线程私有：程序计数器(PC)、虚拟机栈(Stack)、本地方法栈(Native)
- 线程共享：堆(Heap)、方法区(Method Area)
- 方法区：JDK8 前用堆的 PermGen 永久代实现，JDK8 后用本地内存 MetaSpace 元空间；JDK8 后 StringTable 移入堆
- 栈溢出：`-Xss` 设置栈内存（默认 Linux/Mac 1024KB）；栈帧过多/过大都会溢出
- 堆溢出：`java.lang.OutOfMemoryError: Java heap space`；GC 只回收堆对象

**GC**
- 只回收堆内存对象；执行引擎含解释器、JIT 即时编译器、GC

**诊断方法**
- CPU 高：`top` → `ps H -eo pid,tid,%cpu | grep 进程id` → `jstack 进程id`（tid 转 16 进制匹配）→ 定位代码行
- 堆内存溢出：`jmap -heap 进程id`、`jconsole`、`jvisualvm`
- 方法区溢出：JDK8 前 PermGen 溢出，JDK8 后 MetaSpace 溢出
