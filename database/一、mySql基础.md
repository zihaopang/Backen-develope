# 路线记录
av29567197 -->01_java入门阶段
av29573196 -->02_面向对象基础
av29573524 -->03_飞机小项目(java入门和对象练习)
av29573871 -->04_面向对象深入和数组深入
av29574140 -->05_常用类
av29574600 -->06_异常机制
av29575822 -->07_容器和数据结构
av29576016 -->08_IO流技术
av29576265 -->09_多线程
av29577334 -->10_网络编程
av29576652 -->11_手写服务器httpserver项目
av29578196 -->12_注解_反射_字节码_类加载机制
av29579073 -->13_GOF23设计模式全解
av29578557 -->14_正则表达式和文本操作
av29579272 -->15_JDBC数据库操作
av29579728 -->16_手写SORM框架

===课件,源码与习题:
链接：盘.百度.com/s/1uQyfFbVSSD8PxJpu_7XvJA
提取码：dsiv

01，《Java编程学习第一季》：av35556299
02，《Java编程学习第二季》：av37316788
03，《Java编程学习第三季》：av37317551
04，《Java编程学习第四季》：av38308449
05，《JDBC编程和MySQL数据库》：av37325712
06，《Web前端第一季（HTML）》：av35875257
07，《Web前端第三季（JavaScript）》：av37383464
08，《Web前端第四季（JQuery）》：av38513367
09，《JavaWeb第一季基础（JSP和Servlet）》：av37398251
10，《JavaWeb第二季进阶》：av37398729
11，《3小时学会使用Maven构建项目》：av38517296
12，《SSH框架第一季 - Struts入门》：av38472605
13，《SSH框架第二季 - Hibernate入门》：av38476142
14，《SSM框架第一季 - Mybatis入门》：av38513367
15，《SSM框架第二季 - Spring入门》：av38516969
16，《Spring Boot快速入门》：av38356979
17，《手把手教你使用Cropper》：av38512574

# 数据库概述

我们现在所说的数据库泛指“关系型数据库管理系统”（RDBMS）
数据库的基本组成结构：
- RDBMS = 管理员（Manager）+仓库（database）
- database = N个table
- table:
	- 表结构：定义表的列名和列类型
	- 表记录：一行一行的记录

# 删除数据库
1.首先进入cmd，停止mysql服务：`net stop mysql`,或者在系统服务选项中停止mysql服务
2.然后卸载程序。
3.进入C盘，删除MySql文件夹
4.查看注册表：regedit
	- HKEYY_LOCALMACHINE\SYSTEM\CurrentControlSet\Services
	- HKEYY_LOCALMACHINE\SYSTEM\ControlSet001\Services
	- HKEYY_LOCALMACHINE\SYSTEM\ControlSet002\Services
在这些文件夹下面搜索mysql，找到一律干掉
然后重启电脑

一、MySql安装成功后会在两个目录中存储文件
- D:\Program Files\MySQL\MySQL Server 5.1:DBMS管理程序
- C:\Program Files\MySQL\MySQL Server 5.1\data:DBMS数据库文件（写在MySQL时候不会删除）
二、MySQL重要文件
- D:\Program Fils\MySQL\MySQL Server 5.1\bin\mysql.exe:客户端程序，需要服务器开启
- D:\Program Fils\MySQL\MySQL Server 5.1\bin\mysqld.exe:服务器程序，必须先启动它，客户端才能连接上
- D:\Program Fils\MySQL\MySQL Server 5.1\bin\my.ini：服务器配置文件

三、C:\ProgramData\MySQL\MySQL Server 5.5\data
- 该目录是隐藏目录，选哟手动输入，这个目录下的每个目录代表一个数据库
- 在某个数据库目录下会有0——N个.frm的文件，每个frm文件表示一个table,它由DBMS来读写

四、my.ini:MySQL最为重要的配置文件
- 配置MySQL端口：3306
- 配置字符编码
	- [client]下配置客户端编码：default-character-set=gbk
	- [mysqld]下配置服务器编码：character-set-server=utf-8
- 配置二进制数据大小上限：max_allowed_packet=8M

# 开启/关闭服务器以及登陆/退出
服务器操作：
1.开启服务器：net start mysql
2.关闭服务器：net stop mysql

客户登陆操作：
1.登陆服务器：mysql -uroot -p123 -hlocalhost
- -u:后面跟随用户名
- -p:后面跟随密码
- -h:后面跟随IP

2.退出服务器：exit或者quit

# SQL
1.什么是SQL:结构化查询语言
2.SQL的作用：客户端使用SQL来操作服务器
- 启动mysql.exe，连接服务器后，就可以使用sql来操作服务器了
- 将来会使用Java程序连接服务器，然后使用sql来操作服务器

SQL语法：
1.SQL语句可以在单行或者多行书写以分号结尾
2.可以使用空格和缩进来增强语句的可读性
3.MySQL不区别大小写，建议使用大写

SQL语句分类：
1.DDL（Data Defineition Language）：数据定义语言，用来定义数据库对象：库、表、列等等
	- 创建、删除、修改：库、表结构，即数据库或者表的结构操作
2.DML(Data Manipulation Language)：数据操作语言，用来定义数据库记录
	- 增、删、改、查，即对表的结构进行更新
3.DCL(Data Control Language)：数据控制语言，用来定义访问权限和安全级别
	- dql：对表的记录的查询
4.DQL（Data Query Language）：数据查询语言，用来查询记录
	- 对用户的创建，及授权

# DDL
1.数据库：
- 查看所有数据库：SHOW DATABASE；
- 切换（选择所要操作的）数据库：USE 数据库名
- 创建数据库：CREATE DATABASE [IF NOT EXISTS] mydb1 [CHARSET=utf-8]
- 删除数据库：DROP DATABASE [IF EXISTS] mydb1
- 修改数据库编码：ALTER DATABASE mydb1 CHARACTER SET utf-8

2.数据类型（列类型）
- int:整型
- double：浮点型,例如double(5,2)表示最多5位数，其中必有两位小数，最大值999.99
- decimal:浮点型，用在钱方面，不会出现精度缺失的问题
- char：固定长度字符串类型：char(255)，数据长度不足指定长度
- varchar:可变长度字符串类型：varchar(65535),zhangsan
- text(clob):字符串类型，分类：
	- 很小：tinytext,长度：2^8-1B
	- 小:text,(2^16-1)B
	- 中:mediumtext,(2^24-1)B
	- 大:longtext,(2^32-1)B = 4GB
- bolb：字节类型(mp3类型等等)
	- 很小:tniyblob,长度:(2^8-1)B
	- 小:blob,长度:(2^16-1)B
	- 中:mediumblob,长度:(2^24-1)B
	- 大:longblob,长度:(2^32-1)B = 4GB
date:日期类型，格式为：yyyy-MM-dd;
time:事件类型，格式为：hh:mm:ss
timestamp:时间戳类型

3.表
- 创建表:
  CREATE TABLE [IF NOT EXISTS] 表名(
  	列名 列类型,
    列名 列类型,
    ...
    列名 列类型
  );
- 查看当前数据库中所有表的名称:SHOW TABLES;
- 查看指定表的创建语句：SHOW CREATE TABLE 表名;
- 查看表结构：DESC 表名;
- 删除表：DROP TABLE 表名;
- 修改表：前缀： ALTER TBALE 表名
	- 修改之添加列：
		ALTER TABLE 表名 ADD{
        	列名 列类型,
            列名 列类型,
            ...
        };
- 修改之修改列类型
    ALTER TABLE 表名 MODIFY MODIFY 列名 列类型;
- 修改之修改列名：ALTER TABLE 表名 CHANGE 原列名 新列名;
- 修改之删除列：ALTER TABLE 表名 DROP 列名;
- 修改表名：ALTER TBALE 原表名 RENAME TO 新表名;

# DML(数据操作语言，它是对表记录的操作：增删改)
1.插入数据
- INSERT INTO 表名(列名1,列名2,列名3,...) VALUES(列值1,列值2,...);注意，在数据库中插入字符串（包括日期之类的），必须使用单引号，不可使用双引号。
	- 在表名后给出要插入的列名称，其他没有指定的列等同于插入null值，所以插入记录总是插入一行
	- 在VALUES后给出列值，值的顺序和个数必须与前面的指定列对应
- INSERT INTO 表名 VALUES(列值1，列值2);
	- 没有给出要插入的列，那么表示插入所有列
	- 值的个数必须是该表列的个数
	- 值的顺序，必须与表创建时给出的列的顺序相同

2.修改数据
- UPDATE 表名 SET 列名1=列值1,列名2=列值2,....[WHERE 条件]
- 条件(条件可选的)
	- 条件必须是一个boolean类型的值或者表达式：UPDATE t_person SET gender='男', age=age+1, WHERE sid='1';
	- 条件里面可加的运算符：=、!=、<>、>、<、>=、<=、BETWEEN ... AND、IN(...)、IS NULL、NOT、OR、AND

3.删除数据
- DELETE FROM 表名 [WHERE 条件];
- TRUNCATE TABLE 表名: TRUNCATE是DDL语句，他先是删除该表，在create该表，而且无法回滚。

select * from xxx(表名);

# DCL(理解)
- 一个项目创建一个用户！一个项目对应的数据库只有一个！


1.创建用户
- CREATE USER 用户名@IP地址 IDENTIFIED BY '密码';
	- 用户只能在指定的IP地址上面登陆
- CREATE USER 用户名@'%' IDENTIFIED BY '密码';
	- 用户可以在任意的IP地址上登陆

2.给用户授权
- GRANT 权限1, ... ,权限n ON 数据库.* TO 用户名@IP地址;
	- 权限、用户、数据库
	- 给用户分派在指定的数据库上的指定的权限。
	- 例如：GRANT CREATEALTER,DROP,INSERT,UPDATE,DELETE,SELECT ON mydb1.* TO user1@localhost;

- GRANT ALL ON 数据库.* TO 用户名@IP地址
	- 给用户分派指定数据库上所有权限

3.撤销授权

- REVOKE 权限1, .... ,权限n ON 数据库.* FROM 用户名@IP地址;
	- 撤销指定用户在指定数据库上的指定权限
	- 例如：REMOVE CREATE,ALTER,DROP ON mydb1.* FROM user1@localhost;
		- 撤销user1用户在mydb1数据库上的create、alter、drop权限

4.查看权限

- SHOW GRANTS FOR 用户名@IP地址
	- 查看指定用户权限

5.删除用户

- DORP USER 用户名@IP地址

# DQL 数据查询语言
一、基本查询
1.字段(列)控制
1)查询所有列
SELECT * FROM 表名
SELECT * FROM emp;
**Tip：**:其中*表示查询所有列

2)查询指定列
SELECT 列1 [,列2,...,列N] FROM 表名;
SELECT empno,ename,sal,comm FROM 表名;

3)完全重复的记录只要一次
当查询的结果的多行记录一模一样的时候，只显示一行。
SELECT DISTINCT sal FROM emp;

4)列运算
1> 数量类型的列可以做加、减、乘、除运算
SELECT sal*1.5 FROM emp;
SELECT sal+comm FROM emp;
2> 字符串类型可以左做连续运算
SELECT CONTAT('$',sal) FROM emp;
3> 转换NULL值
有时候需要把NNULL转换成其他值，例如com+1000时候，如果com列存在NULL值，此时NULL+1000为1000，即NULL值是被当作0来处理的。
select ifnull(COMM,0)+1000 FROM emp;
**Tip：** IFNULL(comm,0)；如果comm中存在NULL值，那么当成0来运算。
4>给列起别名
你也许注意到，当使用列运算后，查询出来的结果集中的列名称很不好看，这时候可以给列起个别名。
SELECT IFNULL(comm,0)+1000 AS 奖金 FROM emp;其中AS可以省略。

2.条件控制

1）条件查询
与前面介绍的UPDATE和DELETE语句一样，SELETE语句也可以使用WHERE子句来控制记录。
	- SELETE empnoename,sal,comm FROM WHERE sal > 10000 AND comm IS NOT NULL;
	- SELETE empno,ename,sal FROM emp WHERE sal BETWEEN 20000 AND 30000;
	- SELECT empno,ename,job FROM emp WHERE job IN ('经理','董事长');

2)模糊查询
当你想要查询姓张，并且姓名一共两个字的员工的时候，这是就可以使用模糊查询。

- SELECT * FROM emp WHERE ename LIKE '张 ';
**Tip：**模糊查询需要使用的运算发：LIKE，其中空格匹配一个任意字符，注意，值匹配一个字符而不是多
**Tip：**上面语句查询的是姓张，名字由两个字组成的员工

- SELECT * FROM emp WHERE ename LIKE '___'; :查询姓名由三个字组成的员工。

如果我们想要查询姓张，名字几个字可以的员工时就要使用"%"了
SELECT * FROM emp WHERE ename LIKE '张%';
**Tip：**其中%匹配0~N个任意字符，所以上面的语句查询的是姓张的所有员工
SELECT * FROM emp WHERE ename LIKE '%阿%';
**Tip：**千万不要认为上面的语句是在查询姓名中间带有阿字的员工。
SELECT * FROM emp WHERE ename LIKE '%';
**Tip：**这个条件等同于不存在，但如果姓名为NULL查询不出来

二、排序
1)升序
SELECT * FROM 表名 ORDER BY 列名 ASC;
**Tip：**按sal排序，升序；
**Tip：**其中ASC是可以省略的，默认升序
2)降序
SELECT * FROM WHERE emp ORDER BY comm DESC;
**Tip:**按照comm排序，降序
**Tip：**其中DESC不能省略
3)使用多列作为排序条件
SELECT * FROM WHERE emp ORDER BY sal ASC, comm DESC;
**Tip：**使用sal 升序排序，如果sal相同时候，使用comm降序排

三、聚合函数
聚合函数用来做某一列的纵向运算
1)COUNT
SELECT COUNT(*) FROM emp;
**Tip：**计算emp表中所有列都不为NULL的记录的行数
2)MAX
SELECT MAX(sal) FROM emp;
**Tip：**查询最高工资
3)MIN
SELECT MIN(sal) FROM emp;
**Tip：**查询最低工资
4)SUM
SELECT SUM(sal) FROM emp;
**Tip：**查询工资和
5)AVG
SELECT AVG(sal) FROM emp;

**Tip：**查询平均工资

四、分组查询
分组查询是把记录使用某一列进行分组，然后查询组信息。
例如：查看所有部门的记录数
SELECT deptno,COUNT(*) FROM emp GROUP BY deptno;
含义：使用deptno分组，查询部门编号和每个部门的记录数
SELECT job,MAX(SAL) FROM emp GROUP BY job;
含义：使用job分组，查询每种工作的最高工资

组条件：
以部门分组，查询每组记录数，条件为记录数大于3
SELECT deptno,COUNT(\*) FROM emp GROUP BY deptno HAVING COUNT(*) > 3;

五、limit子句（方言）
LIMIT用来限定查询结果的起始行，以及总行数。
例如：查询起始行为第五行，一共查询3行记录
SELECT * FROM emp LIMIT 4,3;
含义：其中4表示从第五行开始，其中3表示一共查询3行，即5，6，7行

# 单表查询练习

```
/*查询出部门编号为30的所有员工*/
SELECT *
FROM emp
WHERE deptno=30;

/*查询出所有销售员的姓名、编号和部门编号*/
SELECT ename,empno,depno
FROM emp
WHERE job='销售员';

/*找出奖金高于工资的员工*/
SELECT *
FROM emp
WHERE comm > sal;

/*找出奖金高于工资60%的人*/
SELECT *
FROM emp
WHERE comm > sal*0.6;

/*找出部门编号为10中所有经理，和部门编号为20中所有销售员的详细资料*/
SELECT *
FROM emp
WHERE (deptno=10 AND job='经理') OR (deptno=20 AND job='销售员');

/*找出部门编号为10中所有经理，部门编号为20中所有销售员，还有既不是经理，也不是销售员，但工资大于等于20000的员工的详细资料*/
SELECT *
FROM emp
WHERE (deptno=10 AND job='经理') OR (deptno=20 AND job='销售员') OR (job NOT IN('经理','销售员') AND sal >= 20000);

/*无奖金或者奖金低于1000的员工*/
SELECT *
FROM emp
wgere comm IS NULL OR comm < 1000;

/*查询名字由三个字组成的员工*/
SELECT *
FROM emp
WHERE ename LIKE '___';

/*查询2000年入职的员工*/
SELECT *
FROM emp
WHERE hiredate LIKE '2000-%';

/*查询所有员工详细信息，用编号升序排序*/
SELECT *
FROM emp
ORDER BY empno;

/*查询所有员工的详细信息，用工资降序排序，如果工资相同使用入职日期升序排序*/
SELECT *
FROM emp
ORDER BY sal DESC,hiredate ASC;

/*查询每个部门的平均工资*/
SELECT deptno,AVG(sal) 平均工资 
FROM emp
GROUP BY deptno;

/*查询每个部门的雇员数量*/
SELECT deptno,COUNT(*)
FROM emp
GROUP BY deptno;

/*查询每种工作的最高工资、最低工资、人数*/
SELECT job,MAX(sal),MIN(sal),COUNT(*)
FROM emp
GROUP BY job;
```
