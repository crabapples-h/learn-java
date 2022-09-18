/*----------初始化数据start----------*/
-- OneToOne 一对一
-- 主表与从表之间通过一个外键关联，且外键有唯一性约束
drop table if exists demo.user_family;
drop table if exists demo.user_base;
drop table if exists demo.user_info;
drop table if exists demo.user_work;
drop table if exists demo.user_base_work;

create table demo.user_family(id int primary key auto_increment,family_name varchar(64) not null) comment '户口表';
-- OneToMany 一对多 user_family --> user_base
create table demo.user_base(
    id int primary key auto_increment,name varchar(64) not null,pid int,fid int,
    constraint fk_family foreign key (fid) references user_family(id) on update cascade on delete cascade
    ) comment '人员表';
-- OneToOne 一对一 user_base --> user_info
create table demo.user_info(
    id int primary key auto_increment,age tinyint check ( age>0 ) default 0,
    address varchar(64) not null ,uid int unique null ,
    constraint fk_user_base foreign key  (uid) references user_base(id) on update cascade on delete cascade
) comment '人员详情表';
-- ManyToMany 多对多  user_info --> user_work
create table demo.user_work(id int primary key auto_increment, work varchar(64) not null ) comment '工作表';

create table demo.user_base_work(
    id int primary key auto_increment,bid int,wid int null,
    constraint fk_base foreign key (bid) references user_base(id) on update cascade on delete cascade ,
    constraint fk_work foreign key (wid) references user_work(id) on update cascade on delete cascade
) comment '人员工作表';
insert into user_family (id, family_name) values (null,'张家'),(null,'李家'),(null,'谢家');
insert into user_base (id, name,pid,fid) value (null,'张大',null,1), (null,'张二',1,1),(null,'李三',null,2),(null,'李四',3,2),(null,'王五',null,null);
insert into user_info (id, age,address,uid) values (null,20,'贵州',1),(null,15,'贵州',2),(null,18,'四川',3),(null,10,'四川',4);
insert into user_work (id, work) values (null,'搬砖'),(null,'搬水泥');
insert into user_base_work (id, bid,wid) values (null,1,1),(null,1,2),(null,2,1),(null,2,2);
/*----------初始化数据end----------*/

/*----------连接查询start----------*/
-- 内连接 两张表的交集部分
-- 隐式内连接 select [字段] from [表一],[表二] where [条件];
select * from user_family,user_base where user_base.fid = user_family.id;
-- 显式内连接 select [字段] from [表一] inner join [表二] on [连接条件];
select * from user_family inner join user_base on user_base.fid = user_family.id;
-- 外连接 查询某一张表的全部和两张表的交集部分(左外连接和右外连接可以相互转换)
-- 左外连接 select [字段] from [表一] left join [表二] on [连接条件]
select * from user_base left join user_family on user_family.id = user_base.id;
-- 右外连接 select [字段] from [表一] right join [表二] on [连接条件]
select * from user_base right join user_family on user_base.fid = user_family.id;
-- 自连接 select [字段] from [表名] [别名] join [表名] [别名] on [连接条件] (自连接可以是内连接也可以是外连接)
-- 内连接
select a.name emp_name, b.name manage_name from user_base as a  join user_base as b on a.pid = b.id;
-- 内连接
select a.name emp_name, b.name manage_name from user_base as a left join user_base as b on a.pid = b.id;
/*----------连接查询end----------*/

/*----------联合查询start----------*/
-- 联合查询 union all 将两个查询的结果集合并在一起 (两个查询结果字段必须相同)
-- select [字段列表] from [表名] union all select [字段列表] from [表名];
select * from user_family where family_name like '张_'
union all
select * from user_family where family_name like '_家' ;
-- 联合查询 union  将两个查询的结果集去重后合并在一起
-- select [字段列表] from [表名] union all select [字段列表] from [表名];
select * from user_family where family_name like '张_'
union
select * from user_family where family_name like '_家' ;
/*----------联合查询end----------*/

/*----------子查询start----------*/
select * from user_info,
    (select a.*,b.family_name from user_base a,
        (select * from user_family where family_name like '李_')b where a.fid = b.id
    ) a where user_info.uid = a.id;
-- select [字段列表] from ([select [字段列表] from [表名]]) [子查询别名]
-- 标量子查询 (子查询结果返回一个值)
select * from user_base a,(
    select id from user_family where family_name like '李_'
    ) b where a.fid = b.id;
-- 列子查询 in、not in、any、some、all (子查询结果只返回一列)
select base.id,base.name,info.age,info.address from user_base as base,(
    select * from user_info where age > all( #表子查询
        select age from user_info, ( #列子查询
            select * from user_base a where a.fid in ( #表子查询
                select id from user_family where family_name ='李家') #标量子查询
            ) base where base.id = user_info.uid)
    ) info where base.id = info.uid and base.fid not in (
    select id from user_family where family_name ='李家');#列子查询

-- 列子查询 in、not in、any、some、all (子查询结果只返回一列)
select base.id,base.name,info.age,info.address from user_base as base,(
    select * from user_info where age > ( #表子查询
        select max(max.age) as max from(select age from user_info, ( #列子查询
            select base.id from user_base base where base.fid in ( #表子查询
                select id from user_family where family_name ='李家') #标量子查询
        ) base where base.id = user_info.uid) max)
) info where base.id = info.uid and base.fid not in (
    select id from user_family where family_name ='李家');#列子查询

select base.id,base.name,info.age,info.address from user_base as base right join (
    select * from user_info where age > ( #表子查询
        select max(ages.age) as max from (
            select age from user_info, ( #列子查询
                select * from user_base a where a.fid in ( #表子查询
                    select id from user_family where family_name ='李家') #标量子查询
            ) base where base.id = user_info.uid)as ages)
) as info on base.id = info.uid where base.fid not in (
    select id from user_family where family_name ='李家');

select base.id,base.name,info.age,info.address from user_base as base right join (
    select * from user_info,( #表子查询
        select max(ages.age) as max from (
            select age from user_info, ( #列子查询
                select * from user_base a where a.fid in ( #表子查询
                    select id from user_family where family_name ='李家') #标量子查询
            ) base where base.id = user_info.uid)as ages) as maxage where age > maxage.max
) as info on base.id = info.uid where base.fid not in (
    select id from user_family where family_name ='李家');


-- 行子查询 (子查询结果只返回一个行)
-- 表子查询 (子查询结果返回多行多列)
-- select [字段列表] from [表名] union all select [字段列表] from [表名];
select * from user_family where family_name like '张_'
union all
select * from user_family where family_name like '_家' ;
-- 联合查询 union  将两个查询的结果集去重后合并在一起
-- select [字段列表] from [表名] union all select [字段列表] from [表名];
select * from user_family where family_name like '张_'
union
select * from user_family where family_name like '_家' ;
/*----------联合查询end----------*/


select max(a.age) from  (select age from user_info, ( #列子查询
    select base.id from user_base base where base.fid in ( #表子查询
        select id from user_family where family_name ='李家') #标量子查询
) base where base.id = user_info.uid) a
