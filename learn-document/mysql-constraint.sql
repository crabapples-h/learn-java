/*----------基本约束start----------*/
truncate table1;
select * from demo.table1;
-- 非空约束 not null 限制字段不能为null
drop table if exists demo.table1;
create table demo.table1(test_notnull tinyint not null comment '非空约束');
insert into demo.table1 values(1);
insert into demo.table1 values(null);
-- 唯一约束 unique 限制字段不能重复
drop table if exists demo.table1;
create table demo.table1(test_unique tinyint unique comment '唯一约束');
insert into demo.table1 values(1);
insert into demo.table1 values(1);
insert into demo.table1 values(2);
insert into demo.table1 values(null);
-- 主键约束 primary key 限制字段不能为空且唯一
drop table if exists demo.table1;
create table demo.table1(test_primary_key tinyint primary key auto_increment comment '主键约束');
insert into demo.table1 values(1);
insert into demo.table1 values(1);
insert into demo.table1 values(2);
insert into demo.table1 values(null);
insert into demo.table1 values();
-- 默认约束 default 保存数据时，如果未指定值则使用默认值
drop table if exists demo.table1;
create table demo.table1(test_default tinyint default -1 comment '默认约束');
insert into demo.table1 values(1);
insert into demo.table1 values(null);
insert into demo.table1 values();
-- 检查约束 check 限制字段满足某一条件 mysql8.0以上支持
drop table if exists demo.table1;
create table demo.table1(test_check tinyint check (test_check>0)  comment '检查约束');
insert into demo.table1 values(0);
insert into demo.table1 values(1);
insert into demo.table1 values(null);
/*----------基本约束end----------*/

/*----------外键约束start----------*/
-- 外键约束 foreign key 连接两张表，保证数据的一致性和完整性
/* 外键约束 删除/更新时
    no action: 在主表中删除/更新对应记录时，检查改记录是否有外键约束，如果有，则不允许删除/更新 (与restrict相同)
    restrict: 在主表中删除/更新对应记录时，检查改记录是否有外键约束，如果有，则不允许删除/更新 (与no action相同)
    cascade: 在主表中删除/更新对应记录时，检查改记录是否有外键约束，如果有，则同时操作从表中的对应记录
    set null: 在主表中删除对应记录时，检查改记录是否有外键约束，如果有，则将从表中对应记录设置为null(需要外键运行为null)
    set default: 在主表中删除/更新对应记录时，检查改记录是否有外键约束，如果有，则将从表中对应记录设置为默认值 (Innodb引擎不支持)
*/
drop table if exists demo.province;
drop table if exists demo.city;
create table demo.province(id int primary key auto_increment,name varchar(10));
create table demo.city(id int primary key auto_increment,name varchar(10),pid int);
-- 添加外键 建表时 constraint [外键约束名称] foreign key  ([外键字段]) references  [主表]([关联字段]) on update [操作方式] on delete [操作方式]
create table demo.city(id int primary key auto_increment,name varchar(10),pid int,constraint fk_pid foreign key  (pid) references province(id));
-- 添加外键 建表后 alter table [从表] add constraint [外键约束名称] foreign key ([外键字段]) references [主表]([关联字段]) on update [操作方式] on delete [操作方式] ;
alter table city add constraint fk_pid foreign key (pid) references province(id) on update cascade on delete cascade ;
-- 删除约束 alter table [表名] drop constraint [约束名称];
alter table city drop constraint fk_pid;
-- 删除外键 alter table [表名] drop foreign kek [外键约束名称];
alter table city drop foreign key fk_pid;
insert into demo.province (name)values('北京'),('上海');
insert into demo.city (name,pid)values('东城区',1),('西城区',1),('浦东',2),('虹桥',2);
/*----------外键约束end----------*/

/*----------各种约束可以组合使用----------*/
drop table table_test;
create table table_test(
    id int auto_increment primary key comment '主键约束、自增',
    name varchar(10) not null comment '姓名:非空约束',
    age tinyint check ( age > 0 ) comment '年龄:检查约束',
    id_card char(18) unique check ( length(id_card)=18 )comment '身份证号:唯一性约束、检查约束',
    gender char(1)  default '女' check ( gender='男' || gender ='女' ) comment '性别:默认约束、检查约束'
) comment '约束条件演示' charset='utf8mb4';
insert into table_test(name,  id_card) value ('李丽','110110199909191235');

