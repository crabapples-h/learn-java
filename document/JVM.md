># jvm核心
> Java Class -> ClassLoader -> klass -> c++类 -> 结构体 -> 本地方法接口
---
* Jvm内存模型
  * Method Area 方法区
  * Heap 堆
  * Java Stacks 虚拟机栈
  * PC Register 程序计数器
  * Native Method Stacks 本地方法栈
* 执行引擎
  * Interpreter 解释器
  * JIT Compiler 即时编译器
  * GC 垃圾回收
---
1. **程序计数器(PC Register)** *用来记录下一条jvm指令的地址*
   1. 线程私有
   2. 不会出现内存溢出的情况
2. **虚拟机栈(Stack)** *线程运行时需要的内存空间*
   1. 先进后出，后进先出
   2. 每个栈由多个栈帧组成(Frame)，对应着每次方法调用时所占用的内存 
   3. 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法 
   4. 在Jvm启动时，可用`-Xss size`设置栈内存，如`-Xss512M`
      1. `Linux/x64(64-bit)`栈内存默认为 `1024 KB`
      2. `MacOS(64-bit)`栈内存默认为 `1024 KB`
      3. `Oracle Solaris/x64(64-bit)`栈内存默认为 `1024 KB`
      4. `Windows`栈内存会根据电脑虚拟内存大小分配栈内存大小 
   5. 栈内存并不是越大越好，因为物理内存大小是固定的。而一个栈空间对应的就是一个线程，所以当栈内存变大时，相应的栈的数量就会减少，对应的也就意味着线程的数量会减少 
   6. 栈帧过多可能会导致栈内存溢出(每次调用方法都会创建一个栈帧)
   7. 栈帧过大也可能会导致栈内存溢出(比较少见)
3. **本地方法栈(native stack)** *jvm调用本地方法时分配的内存空间*
4. **堆(Heap)** *线程共享的*
   1. 线程共享，需要注意堆内存溢出的情况`java.lang.OutOfMemoryError: Java heap space`
   2. 使用`new`关键字创建的对象
   3. 有垃圾回收机制`gc`
5. 方法区(Method Area)
   1. 所有java虚拟机线程共享的区域
   2. 主要存储类的相关信息
      1. `field`成员变量
      2. `method data`方法
      3. `code for methods and consturctors`成员方法，构造器方法的代码
      4. `special methods`特殊方法(主要指类的构造器)
      5. `runtime constant pool`(运行时常量池)
   3. 方法区在虚拟机启动时被创建
   4. 方法区的定义和实现
      1. 方法区在逻辑上是堆的组成部分(jvm规范中定义，但具体实现由jvm实现厂商自己定义)
      2. oracle的HotSpot在jdk8以前使用堆内容的一部分来实现(`PermGen`永久代)
      3. jdk8以后使用使用本地操作系统的内存的内存来实现(`Metaespace`元空间)
      4. jdk8以后`StringTable`不再放在方法区内，而是被移入堆空间(Heap)中
   5. 方法区也有可能导致内存溢出
6. **GC 垃圾回收** *线程运行时需要的内存空间*
    * 垃圾回收只回收堆内存中的对象，不会回收栈内存中的空间
    * 每个栈由多个栈帧组成(Frame)，对应着每次方法调用时所占用的内存
    * 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法
---
* CPU占用过高诊断
  1. 使用`top`命令定位cpu占用过高的进程
  2. 使用`ps H -eo pid,tid,%cpu | grep 进程id`命令定位cpu占用过高的线程 
  3. 使用jstack工具`jstack 进程id`查看出现问题的线程，并将`ps`命令中的线程id转换为16进制与`jstack`中的id匹配
  4. 找到cpu占用过高的代码行数，在源码中进行修复
* 堆内存溢出诊断
  * 使用`jmap -heap 进程id`查看某一时刻堆内存状态
  * 使用`jconsole`查看堆内存状态监控
  * 使用`jvisualvm`可以查看堆内存状态，已经堆内存内对象数量信息等
* 方法区的内存溢出现象
  * jdk1.8以前会导致永久代内存溢出
  * jdk1.8之后会导致元空间内存溢出
