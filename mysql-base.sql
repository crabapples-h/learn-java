/*----------DQL编写顺序----------*/
-- select 字段列表 from 表名列表 where 条件列表 group by 分组字段列表 having 分组后条件列表 order by 排序字段列表 limit 分页参数
/* DQL执行顺序
	1. from 			指定需要查询的表
	2. where 			指定查询的条件
	3. group by 	指定分组条件
	4. having 		指定分组之后的条件
	5. select 		指定需要返回的字段
	6. order by 	指定排序条件
	7. limit 			指定分页条件
*/

/*----------数据库控制start----------*/
use mysql;
select * from user;
-- 创建用户 create user '用户名'@'主机名' identified by '密码'
create user 'demo'@'%' identified by '123456';
-- 修改用户密码 alter user '用户名'@'主机名' identified with 加密方式 by '新密码'
alter user 'demo'@'localhost' identified with mysql_native_password by '000000';
-- 删除用户 drop user '用户名'@'主机名'
drop user 'demo'@'%';
-- 查询用户权限 show grants for '用户名'@'主机名'
show grants for 'demo'@'localhost';
-- 增减用户权限 grant 权限列表 on 数据名.表名 to '用户名'@'主机名'
grant select on learn.* to 'demo'@'localhost';
-- 删除用户权限 revoke 权限列表 on 数据名.表名 from '用户名'@'主机名'
revoke all on *.* from 'demo'@'localhost';
/*----------数据库控制end----------*/

/*----------建表start----------*/
-- 查看所有数据库
show databases;
-- 使用数据库
use demo;
-- 查看所有表
show tables;
-- 新建表
create table emp(
	id tinyint comment 'id',
	name varchar(64) comment '姓名',
	gender char(1) comment '性别',
	age tinyint comment '年龄',
	address varchar(64) comment '地址'
) comment '员工表' charset='utf8mb4';
-- 新建表 if not exists (如果表不存在)
create table if not exists emp(
	id tinyint comment 'id',
	name varchar(64) comment '姓名',
	gender char(1) comment '性别',
	age tinyint comment '年龄',
	address varchar(64) comment '地址'
) comment '员工表' charset='utf8mb4';
-- 修改表名
alter table emp rename employee;
-- 查看表结构
desc employee;
-- 查看建表语句
show create table employee;
-- 添加字段
alter table employee add other_1 varchar(20) comment '其他_1';
alter table employee add id_card char(18) comment '身份证号';
alter table employee add birthday char(8) comment '生日';
-- 修改字段数据类型
alter table employee modify other_1 varchar(30);
-- 修改字段
alter table employee change other_1 other varchar(64) comment '其他';
-- 删除表
drop table employee;
-- 删除表并重新新建相同结构的表
truncate table employee;
/*----------建表end----------*/

/*----------初始化数据start----------*/
-- 插入数据
insert into  employee values (1,'王小虎','男',23,'北京','','123456199909191234','19990919');
insert into  employee values (2,'周林','男',20,'广州','','123456200209191234','20020919');
insert into  employee values (3,'谢雅婷','女',19,'成都','','123456200209191234','20020919');
insert into  employee values (4,'李诗诗','女',22,'北京','','123456200009191234','20000919');
insert into  employee values (5,'孙丽','女',21,'成都','','123456200109191234','20010919');

-- 修改数据
update employee set birthday = '' where id = 1;
update employee set address = lpad(address,10,'----------');
update employee set address = replace(address,'-','');
/*----------初始化数据end----------*/

/*----------基本查询start----------*/
-- 查询 distinct 去重
select distinct * from employee;
/*----------基本查询end----------*/

/*----------条件查询start----------*/
-- > >= < <= = != <> like between...and... in and or
-- 条件查询 where:查询前过滤条件 having:查询后过滤条件
-- where条件查询 substring(源数据,从第几位开始截取,截取长度)
select *,substring(id_card,7,8) as birth from employee where substring(id_card,7,8) = birthday;
-- having条件查询 substring(源数据,从第几位开始截取,截取长度)
select *,substring(id_card,7,8) as birth from employee having substring(id_card,7,8) = birthday;
-- where条件查询 between...and...
select * from employee where age between 21 and 22;
-- where条件查询 like '_' 通配符(单个字符)
select * from employee where address like '_京';
-- where条件查询 like '%' 通配符(多个字符)
select * from employee where address like '%京';
/*----------条件查询end----------*/

/*----------聚合函数start----------*/
-- 针对某一列进行计算的函数叫做聚合函数
-- 查看记录数 count()
select count(*) from employee;
-- 聚合函数 计算平均值 avg()
select avg(age) from employee;
-- 聚合函数 计算和 sum()
select sum(age) from employee where address like '_京';
-- 聚合函数 计算最大值 max()
select max(age) from employee;
-- 聚合函数 计算最小值 min()
select min(age) from employee;
/*----------聚合函数end----------*/

/*----------类型转换start----------*/
-- 类型转换 convert(源数据 , 需要转换的类型)
select 	convert(id_card , unsigned) from employee;
-- 类型转换 cast(源数据 as 需要转换的类型)
select 	cast(id_card as unsigned) from employee;
/*----------类型转换end----------*/

/*----------分组查询start----------*/
-- 分组查询 group by ...
select count(*),gender from employee  group by gender;
-- 分组查询 group by ...
select count(*) as '数量',gender as '性别',avg(age) as '平均年龄' from employee  group by gender;
-- 分组查询 group by ...
select count(*) ,gender,avg(age),address from employee where age < 22 group by address;
/*----------分组查询end----------*/

/*----------分组聚合start----------*/
-- 执行顺序 where > 聚合函数 > having
-- 语法:select 字段列表 from 表名 [where条件] group by 分组字段名 [分组后过滤条件]
select count(*) as ageCount,avg(age),address from employee where age < 22 group by address  having ageCount < 2;
select * from (select count(*) as ageCount,avg(age),address from employee where age < 22 group by address) as a where ageCount < 2;
select * from (select count(*) as ageCount,avg(age),address from employee where age < 22 group by address) as a having ageCount < 2;
/*----------分组聚合end----------*/

/*----------排序查询start----------*/
-- 语法:select 字段列表 from 表名 order by 字段1 排序方式,字段2 排序方式
-- asc:升序 desc:降序
select * from employee order by age asc, id asc;
/*----------排序查询end----------*/

/*----------分页查询start----------*/
-- limit:mysql专有方言
-- 语法:select 字段列表 from 表名 limit 从第几条开始查询,查询几条
select * from employee limit 2,2;
/*----------分页查询end----------*/


