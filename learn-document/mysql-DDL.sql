# ----- 数据库操作 start -----
# 查看所有数据库
show databases;

# 新建数据库 [如果数据库不存在才执行创建] 知道字符集为 utf8mb4 排序规则为 utf8mb4_0900_ai_ci
create database if not exists learn_mysql charset utf8mb4 collate utf8mb4_0900_ai_ci;

# 查询当前数据库
select database();

# 删除数据库 [如果数据库存在才执行删除]
drop database if exists learn_mysql;

# 使用数据库
use learn_mysql;
# ----- 数据库操作 end -----


# ----- 表操作-查询 start -----
# 查询当前数据库所有表
show tables;

# 查询表结构
desc test_table;

# 查询指定表的建表语句
show create table test_table;
# ----- 表操作-查询 end -----


# ----- 表操作-创建 start -----
create table if not exists test_table
(
    id    int auto_increment primary key comment 'id',
    name  varchar(64) comment '名称',
    price decimal(10, 2) comment '价格'
) comment '测试表' collate utf8mb4_0900_ai_ci;
# ----- 表操作-创建 end -----
