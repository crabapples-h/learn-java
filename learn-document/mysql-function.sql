/*----------字符串函数start----------*/
-- 字符串长度 length(str) 返回字符串的长度
select length('hello,world');
-- 字符串拼接 concat(str1,str2,str3...) 将所有参数拼接为一个字符串
select concat('123', '测试', 'hello,world');
-- 转化为大写 upper(str) || ucase(str)
select upper('HELLO,world');
-- 转化为小写 lower(str)  || lcase(str)
select lower('hello,world');
-- 左填充 lpad('str',length,'pad') 用pad将字符串str的左边填充至长度length
select lpad('hello,world', 20, '哈');
-- 右填充 rpad('str',length,'pad') 用pad将字符串str的右边填充至长度length
select rpad('hello,world', 20, '哈');
-- 去除空白 trim('str') 去除字符串str左右两边的空白
select trim('   hello,world       ');
-- 截取字符串 substring('str',start,length) 在str中从start开始截取长度为length的部分 * start从1开始
select substring('hello,world', 1, 5);
-- 字符串替换 replace('str','source','adapter') 将字符串str中所有的source替换为target
select replace('hello,world', 'o', '哈');
/*----------字符串函数end----------*/

/*----------数值函数start----------*/
-- 向上取整 ceil(x) 将x向上取整
select ceil(-1.2);
-- 向下取整 floor(x) 将x向下取整
select floor(-1.2);
-- 取余 mod(x,y) 计算x/y的模(余数)
select mod(9, 7);
-- 随机数 rand() 返回0-1之间的一个随机数(浮点数)
select rand();
-- 四舍五入 round(column) 对column进行四舍五入
-- 四舍五入 round(column,n) 对column进行四舍五入,保留n位小数
select rand(5 / 2, 2);
/*----------数值函数end----------*/

/*----------日期函数start----------*/
-- 当前日期 curdate() || current_date() 获取当前日期
select curdate(), current_date();
-- 当前时间 curdate() || current_time() 获取当前时间
select curtime(), current_time();
-- 当前时间戳 now()|| current_timestamp() 获取当前时间戳
select now(), current_timestamp();
-- 获取某日期的 年、月、日、时、分、秒，格式可以为yyyyMMddHHmmss、yyyy-MM-dd HH:mm:ss、yyyy/MM/dd HH:mm:ss
select year('20220101120000'), year('2022-01-01 12:00:00'), year('2022/01/01 12:00:00');
select month('20220101120000'), month('2022-01-01 12:00:00'), month('2022/01/01 12:00:00');
select day('20220101120000'), day('2022-01-01 12:00:00'), day('2022/01/01 12:00:00');
select hour('20220101120000'), hour('2022-01-01 12:00:00'), hour('2022/01/01 12:00:00');
select minute('20220101120000'), minute('2022-01-01 12:00:00'), minute('2022/01/01 12:00:00');
select second('20220101120000'), second('2022-01-01 12:00:00'), second('2022/01/01 12:00:00');
-- date_add(date,INTERVAL n UNIT)获取date加上单位为UNIT的u之后的时间
select date_add('2022-01-01 12:00:00', INTERVAL 2 MONTH);
-- datediff(date1,date2) 计算两个时间之间的时间差，单位:day
select datediff('2022-01-01 12:00:00', '2022-03-01 12:00:00');
/*----------日期函数end----------*/

/*----------流程控制start----------*/
-- if(bool,value1,value2) bool ? value1 : value2
select if(1 <= 2, '对', '错');
-- ifnull(value,else) value != null ? value : else
select ifnull('hello', 'hello,world');
-- case when [条件1] then [value1] when [条件2] then [value2] ... else [other] end;
-- 如果[条件]为true返回[value]并终止判断，如果所有条件都不满足则返回[other]
select case when 1<>1 then 'hello' when 1=1 then 'world' else 'other' end;
-- case [参数] when [条件1] then [value1] when [条件2] then [value2] ... else [other] end;
-- 如果[参数]==[条件]返回[value]并终止判断，如果所有条件都不满足则返回[other]
select case 'world' when 'hello' then 'hello' when 'world' then'world' else 'other' end;
/*----------流程控制函数end----------*/

-- 各种函数可以组合使用
select lpad(floor(rand() * 1000000), 6, '0');
select
       lpad(floor(rand() * 1000000), 6, '0') as ramdom_id,
       datediff(now(), substring(emp.id_card, 7, 8)) as age1,
       case when emp.gender ='男' then '小哥哥' else '小姐姐' end as case1,
       case when emp.gender ='男' then '小哥哥' when emp.gender ='女' then '小姐姐' else '其他' end as case2,
       case emp.gender when 1>=1 then '小姐姐' when '男' then '小哥哥'  else '其他' end as case3,
       emp.*
from demo.employee as emp order by age1 desc;

