---
name: learning-bytebuddy
description: ByteBuddy 实验：运行时生成类的命名策略与字节码输出
metadata:
  type: reference
---

来源：`learn-byte-buddy/src/test/java/base/README.md`（packaging=pom，独立多模块实验）。

- `new ByteBuddy().with(TypeValidation.of(false)).subclass(父类).name("...")` 生成字节码
- 默认命名：父类为 JDK 自带 → `net.bytebuddy.renamed.java.Lang.Object$ByteBuddy$xxxx`；父类非 JDK → 当前包名$父类名$xxxx
- 指定 `NamingStrategy.SuffixingRandom` 策略后：前缀改为 `$XXXX$xxxx`
- 产物：`getBytes()` / `saveIn(dir)` 存 class 文件 / `inject(jar)` 直接注入 jar
