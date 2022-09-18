-- alter 修改表结构
-- alert table 表名 操作方式 字段 数据类型 约束条件
alter table table1
    add id_card varahar(18) unique;
--
-- alert table 表名 操作方式 字段 数据类型 约束条件
alter table table1
    add age int not null;
--
-- limit 分页查询
-- select * from 表名 条件 分页 条数
select *
from table1 limit 1;
--
-- select * from 表名 条件 分页 页数,条数
select *
from table1 limit 1,1;
--
-- 分组查询
-- where 是一个约束声明，在查询数据库的结果返回之前对数据库中的查询条件进行约束，即在结果返回之前起作用，且where后面不能使用“聚合函数” ( 查询时过滤 )
-- having 是一个过滤声明，所谓过滤是在查询数据库的结果返回之后进行过滤，即在结果返回之后起作用，并且having后面可以使用“聚合函数” ( 查询完成后过滤 )
-- select 字段 from 表名 gropu by 字段
SELECT *, sum(scope) AS sum1
FROM table1
GROUP BY NAME;
-- select 字段 from 表名 gropu by 字段 having 字段
-- where不能和聚合函数一起使用，所以使用having
select *
from table1
group by name
having scope > 50 limit 0,1;
--
-- where后面之所以不能使用聚合函数是因为where的执行顺序在聚合函数之前
-- 如 : select  *,sum(scope ) from table1   group by table1.name where sum(scope )>100;
-- having既然是对查出来的结果进行过滤，那么就不能对没有查出来的值使用having
-- 如 ：select  id,name from table1   having scope >90;
--
-- 聚合函数
-- 查询总数 select count(*) from 表名
SELECT count(*)
FROM table1;
--
-- 求和 select sum(字段名) from 表名
SELECT sum(scope)
FROM table1;
--
-- 求平均数 select avg(字段名) from 表名
SELECT avg(scope)
FROM table1;
--
-- 求最小值 select min(字段名) from 表名
SELECT min(scope)
FROM table1;
--
-- 求最大值 select max(字段名) from 表名
SELECT max(scope)
FROM table1;
--
-- where 条件
-- 查询 id = 1 的数据
SELECT *
FROM table1
WHERE id = 1;
--
-- 查询 id > 1 的数据
SELECT *
FROM table1
WHERE id > 1;
--
-- 查询 id < 1 的数据
SELECT *
FROM table1
WHERE id < 1;
--
-- 查询 id > 1 并且 id < 10 的数据
SELECT *
FROM table1
WHERE id > 1
  AND id < 10;
--
-- 查询 id > 1 或者 id < 10 的数据
SELECT *
FROM table1
WHERE id > 1
   OR id < 10;
--
-- 查询 id = 1 或者 id = 5 或者 id = 10 的数据
SELECT *
FROM table1
WHERE id IN (1, 5, 10);
--
-- 查询 username = 'zhangsan' 的数据
SELECT *
FROM table1
WHERE username = 'zhangsan';
--
-- 查询 username 以 'zhang' 开头的数据
SELECT *
FROM table1
WHERE username LIKE 'zhangsan%';
--
-- 查询 username 以 'zhang' 结尾的数据
SELECT *
FROM table1
WHERE username LIKE '%zhangsan';
--
-- 查询 username 包含 'zhang' 的数据
SELECT *
FROM table1
WHERE username LIKE '%zhangsan%';
--
-- 查询数据
-- select 字段1，字段2 from 表名
SELECT id,
       username
FROM table1;
--
-- select 字段1 as 别名，字段2 as 别名 from 表名
SELECT id       AS a,
       username AS b
FROM table1;
--
-- select * from 表名
SELECT *
FROM table1;
--
-- select * from 表名 条件
SELECT *
FROM table1
WHERE id = 001;
--
-- select * from 表名 条件 分页
SELECT *
FROM table1 LIMIT 1;
--
-- select * from 表名 排序(asc正序，desc倒序) 分页
SELECT *
FROM table1
ORDER BY id DESC LIMIT 1;
--
-- select distinct * from 表名
SELECT DISTINCT NAME
FROM table1;-- 去重
SELECT * FROMTABLE WHERE	NAME IN ( SELECT DISTINCT NAME FROM table1 ) -- 删除数据DELETE
FROM
    table1
WHERE
    id = 003;
--
-- 修改数据
UPDATE table1
SET STATUS = 0
WHERE id = 001;
--
-- 插入多条数据
INSERT INTO table1 (id, NAME, username, STATUS, scope)
VALUES (001, '张三', 'zhangsan', 0, 90),
       (002, '李四', 'lisi', 0, 70),
       (003, '王五', 'wangwu', 1, 50),
       (004, '张三', 'zhangsan2', 0, 82),
       (005, '李四', 'lisi2', 0, 77),
       (006, '王五', 'wangwu2', 1, 53);
-- 插入一条数据
-- insert into 表名values(值1，值2...)
INSERT INTO table1
VALUES (001, '张三', 'zhangsan', 0, 90);-- insert into 表名 (字段1，字段2...) values(值1，值2...)
INSERT INTO table1 (id, NAME, username, STATUS, scope)
VALUES (002, '李四', 'lisi', 0, 70);-- insert into 表名 (字段1，字段3...) values(值1，值3...)
INSERT INTO table1 (username, STATUS, scope)
VALUES ('wangwu', 1, 50);
--
-- 删除数据表
DROP TABLE table1;
--
-- 查看表结构
DESC table1;--
-- 新建数据表
CREATE TABLE table1
(
    id       INT NOT NULL PRIMARY KEY auto_increment COMMENT '这个id',
    NAME     VARCHAR(64) COMMENT '这个是备注',
    username VARCHAR(64) UNIQUE COMMENT '这个是用户名',
    STATUS   TINYINT ( 1 ) NOT NULL DEFAULT 1 COMMENT '这个是状态',
    scope    INT COMMENT '成绩'
) COMMENT '这个是表注释';
--
-- 查看所有数据表
SHOW
TABLES;--
-- 查看当前使用的数据库
SELECT DATABASE
           ();
--
-- 使用数据库
USE
demo;--
-- 新建数据库
CREATE
DATABASE demo;--
-- 删除数据库
DROP
DATABASE demo;--
-- 查看所有数据库
-- 默认情况下有4个数据库
-- information_schema  保存了mysql的元数据信息 表名，字段名等
-- mysql  保存了mysql的用户信息，权限信息等
-- performance_schema  保存了mysql运行时的日志信息
-- test  测试数据库 ( 可以自己折腾的数据库 )
SHOW
DATABASES;--