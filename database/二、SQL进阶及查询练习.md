# 编码
1.查看MySql数据库编码

- SHOW VARIABLES LIKE 'char%';

2.编码解释

- character_set_client:MySQL使用该编码来解读客户端发送过来的数据，例如改编码为UTF-8；
- character_set_results:MySQL会把数据转换成该编码后，再发送给客户端，例如该编码为UTF-8，那么如果客户端使用UTF-8来解读，那么就会出现乱码。

3.控制台乱码问题

character_set_client=utf8，无论客户端发送的是什么编码的数据，mysql都当成是utf8d的数据！如果说客户端发送的是GBK，服务器依然会把他当作utf8对待，所以，必然乱码！
处理方法：1.让客户端发哦是那个utf8（行不通）2.把character_set_client修改为gbk

character_set_results=utf8，把数据用什么编码发送给客户端！如果服务器发送给客户端的是utf8,那客户端会把它当成gbk，因为小黑屏只能显示gbk，所以，必然乱码！解决方案：1.让服务器发送GBK数据  2.让小黑屏使用utf8来解读（行不通）

- 插入或修改时候出现乱码：
	- 这时候因为cmd下默认GBK编码，而character_set_client不是GBK的原因，我们只需要让这两个编码相同即可。
	- 因为修改cmd的编码不方便，所以我们去设置character_set_client为GBK即可。

- 查询出数据为乱码：
	- 这是因为character_set_results不是GBK，而cmd默认使用GBK的原因，我们只需要让两个编码相同即可。
	- 因为修改cmd的编码不方便，所以我们去设置character_set_results为GBK即可。

- 设置变量的语句：
	- set character_set_client=gbk;
	- set character_set_result=gbk;

4.指定默认编码
我们在安装MySQL的时候已经指定了默认编码为UTF-8，所以我们在创建数据库、创建表时，都无需再次指定编码。为了一劳永逸，可以在my.ini中设置。设置character_set_server=utf-8即可。

# 备份与恢复
1.数据库导出SQL脚本(备份数据库内容，并不是数据库)
- mysqldump -u用户名 -p密码 数据库名>生成的脚本文件路径
- 例如：mysqldump -uroot -p123 mydb1>c:\mydb1.sql
- 注意，不要打分号，不要登陆mysql，直接在cmd下运行
- 注意，生成的脚本文件中不含有create database语句

2.执行SQL脚本
第一种方式
- mysql -u用户名 -p密码 数据库<脚本文件路径
- 例如：
	- 先删除mydb1库，在重新创建mydb1库
	- mysql -uroot -p123 mydb1<C:\mydb1.sql
- 注意：不要打分号，不要登陆mysql，直接在cmd下运行

第二种方式
- 登陆mysql
- surce SQL脚本路径
- 例如：
	- 先删除mydb1库，再重新创建mydb1库
	- 切换到mydb1库
	- source c:\mydb1.sql

# 约束
- 约束是添加在列上的，是用来约束列的！

1.主键约束（唯一标识）
主键特性：非空，唯一，可以被引用。比如身份证号
- 当表的某一列被指定为主键后，该列就不能为空，不能有重复的数值出现
- 创建表的时候指定主键的两种方式

```
CREATE TABLE stu(
sid CHAR(6) PRIMARY KEY,
sname VARCHAR(20),
age INT,
gender VARCHAR(10)
);
指定sid列为主键列，即为sid添加主键约束
修改表时指定主键：ALTER TABLE stu ADD PRIMARY KEY(sid);
删除主键：ALTER TABLE stu DROP PRIMARY KEY;
```

2.主键自增长
- 因为主键列的特性是：必须唯一、不能为空，所以我们通常会指定主键类为整型，然后让其自动增长，这样可以保证在插入数据时候主键列的唯一和非空特性。
- 创建表时指定主键自增长
设置自增长的时候，主键设置为NULL也不会报错，会对上一次插入的数据进行自增长。
```
CREATE TABLE stu(
sid INT PRIMARY KEY AUTO_INCREMENT,
sname VARCHAR(20),
age INT,
gender VARCHAR(10)
);
```
- 修改表时设置主键自增长：ALTER TABLE stu CHANGE sid sid INT AUTO_INCREMENT;
- 修改表时删除自增长主键：ALTER TABLE stu CHANGE ais sid INT;
- 测试主键自增长：
	- INSERT INTO stu VALUES(NULL,'张三'，23，'male');
	- INSERT INTO stu(sname,age,gender) VALUES('zhangsan',23,'male');

**Tips:**今后大部分时候使用UUID做主键

3.非空约束（可重复）
- 因为某些列不能设置为NULL值，所以可以对列添加非空约束
- 例如：

```
CREATE TABLE stu(
sid INT PRIMARY KEY AUTO_INCREMENT,
sname VARCHAR(20) NOT NULL,
age INT,
gender VARCHAR(10)
);
```
- 对sname列设置了非空约束

4.唯一约束
- 车库某些列不能设置重复的值，所以可以对列添加唯一约束。
- 例如：

```
CREATE TABLE stu(
sid INT PRIMARY KEY AUTO_INCREMENT,
sname VARCHAR(20) NOT NULL UNIQUE,//唯一加非空
age INT,
gender VARCHAR(10)
);
```

5.概念模型
对象模型:
is a,

has a(关联)：

	- 1对1
	- 1对多
	- 多对多

use a

当我们要完成一个软件系统的时候，需要把系统中的实体抽取出来，形成概念模型。
比如部门、员工都是系统中的实体，概念模型中的实体最终会成为Java中的类、数据库中的表。
实体之间还存在这关系，关系有三种：
- 1对多：比如一个员工从属于多个部门
- 1对1：比如说配偶
- 多对多：老师和学生的关系，老师有多个学生，学生可以有多个老师。

概念模型在Java中称为实体类（javaBean）
类就使用成员变量来完成关系，一般都是双向关联
多对一双向中关联，即员工关联部门，部门也关联员工。

```
class Employee{//多方关联一方
	...
    private Department department;
}
class Department{//一方关联多方
	...
    private List<Employee> employees;
}
//一对一关系
class Husband{
	...
    private Wife wife;
}
class Wife{
	...
    private Husband;
}
//多对多关系
class Student{
	...
    private List<Teacher> teachers;
}
class Teacher{
	...
    private List<Stident> students;
}
```

6.外键约束
- 外键必须引用另一个表的主键，比如一个学生的班级号，可能在另一张表中就是主键
- 外键可以重复
- 外键可以为空
- 一张表中有多个外键

概念模型在数据库中成为表
数据库表中的多对一关系，只需要在多方使用一个独立的列来引用1方的主键即可

```
//员工表
create table emp{
	empno int primary key,//员工编号
    ...
};
//部门表
create table dept{
	deptno int primary key,//部门编号
    ...
};
```
emp表中的deptno列的值表示当前所从属的部门编号，也就是说emp.deptno必须在dept表中是真实存在的。
但是我们必须要对他进行约束，不然可能会出现员工所属额部门编号是不存在的。这种约束就是外键约束。
我们需要给emp.deptno添加外键约束，约束它的值必须在dept.deptno中存在。外键必须是另一个表的主键！

语法：CONTSTRAINT 约束名称 FOREIGN KEY(外键列名) REFERENCES 关联表(关联表的主键)
创建表时候指定外键约束
```
create table emp{
	empno int primary key,
    ...
    depyno int,
    CONSTRAINT fk_emp FOREIGN KEY(mgr) REFERENCES emp(empno)
};
```
修改表时添加外键约束
ALTER TABLE emp
ADD CONSTRAINT fk_deptno FOREIGN KEY(deptno) REFERENCE dept(deptno);

修改表时删除外键约束
ALTER TABLE emp
DROP FOREIGN KEY fk_emp_deptno;//约束名称

举例：

```
CREATE TABLE dept(
	deptno INT PRIMARY KEY AUTO_INCREMENT,
	dname VARCHAR(50)
);

INSERT INTO dept VALUES(10,'研发部');
INSERT INTO dept VALUES(20,'人力部');
INSERT INTO dept VALUES(30,'财务部');

CREATE 	TABLE emp(
 empno INT PRIMARY KEY AUTO_INCREMENT,
 ename VARCHAR(50),
 my_deptno INT,
 CONSTRAINT fk_emp_dept FOREIGN KEY(my_deptno) REFERENCES dept(deptno)
);

INSERT INTO emp(empno,ename) VALUES(NULL,'张三');
INSERT INTO emp(empno,ename,my_deptno) VALUES(NULL,'李四',10);
INSERT INTO emp(empno,ename,my_deptno) VALUES(NULL,'王五',80);

SELECT * FROM emp;
```

7.数据库一对一关系
在表中建立一对一关系比较特殊，需要让其中一张表的主键，既是主键又是外键。

```
create table husband(
	hid int PRIMARY KEY,
	...
);
create table wife(
	wid int PRIMARY KEY,
    ...
    CONSTRAINT fk_wife_wid FOREIGN KEY(wid) REFERENCES husband(hid)
);
```
其中wife表的wid即是主键，又是相对husband表的外键。
husband.hid是主键，不能重复。
wife.wid是主键，不能重复，又是外键，必须来自husband.hid。
所以如果在wife表中有一条记录的wid为1，那么wife表中的其他记录的wid就不能是1了，因为他的主键
同时在husband.hid中必须存爱1这个值，因为wid是外键。这就完成了一对一关系

举例：
```
CREATE TABLE hasband(
    hid INT PRIMARY KEY AUTO_INCREMENT,
    hnamw VARCHAR(50)
);


INSERT INTO hasband VALUES(NULL,'刘备');
INSERT INTO hasband VALUES(NULL,'关羽');
INSERT INTO hasband VALUES(NULL,'张飞');


SELECT * FROM hasband;

/*
将主键wid作为外键引用
*/
CREATE TABLE wife(
    wid INT PRIMARY KEY AUTO_INCREMENT,
    wname VARCHAR(50),
    CONSTRAINT fk_wife_hasband FOREIGN KEY(wid) REFERENCES hasband(hid)
);

INSERT INTO wife VALUES(1,'小乔');
INSERT INTO wife VALUES(1,'妲己');/*重复引用，不符合一对一关系*/
INSERT INTO wife VALUES(100,'杨贵妃');/*id不存在*/
```

8.数据库多对多关系
在表中建立多对多关系需要使用中间表，即需要三张表，在中间表使用两个外键，分别引用其他两个表的主键。

```
create table student(
	sid int PRIMARY KEY,
    ...
);
create table teacher(
	tid int PRIMARY KEY,
    ...
);

create table stu_tea(
	sid int,
    tid int,
    CONSTRAINT fk_stu_tea_sid FOREIGN KEY(sid) REFERENCES student(sid),
    CONSTRAINT fk_stu_tea_tid FOREIGN KEY(tid) REFERENCES student(tid)
);
```
这是在stu_tea这个中间表中的每条记录都是来说明student和teacher表的关系

举例：
```
CREATE TABLE student(
    sid INT PRIMARY KEY AUTO_INCREMENT,
    sname VARCHAR(50)
);

CREATE TABLE teacher(
    tid INT PRIMARY KEY AUTO_INCREMENT,
    tname VARCHAR(50)
);

CREATE TABLE stu_tea(
    sid INT,
    tid INT,
    CONSTRAINT fk_student FOREIGN KEY(sid) REFERENCES student(sid),
    CONSTRAINT fk_teacher FOREIGN KEY(tid) REFERENCES teacher(tid)
);

INSERT INTO student VALUES(NULL,'刘德华');
INSERT INTO student VALUES(NULL,'梁朝伟');
INSERT INTO student VALUES(NULL,'蔡依林');
INSERT INTO student VALUES(NULL,'蔡健雅');
INSERT INTO student VALUES(NULL,'周杰伦');

INSERT INTO teacher VALUES(NULL,'崔老师');
INSERT INTO teacher VALUES(NULL,'黄老师');
INSERT INTO teacher VALUES(NULL,'刘老师');

/*关联关系*/
/*设置崔老师的学生*/
INSERT INTO stu_tea VALUES(1,1);
INSERT INTO stu_tea VALUES(2,1);
INSERT INTO stu_tea VALUES(3,1);
INSERT INTO stu_tea VALUES(4,1);
INSERT INTO stu_tea VALUES(5,1);
/*设置黄老师的学生*/
INSERT INTO stu_tea VALUES(3,2);
INSERT INTO stu_tea VALUES(4,2);
INSERT INTO stu_tea VALUES(5,2);
/*设置刘老师的学生*/
INSERT INTO stu_tea VALUES(2,3);
INSERT INTO stu_tea VALUES(3,3);
INSERT INTO stu_tea VALUES(4,3);

SELECT * FROM stu_tea;

```

# 多表查询
1.分类

- 合并结果集（了解）
- 连接查询
- 子查询

### 合并结果集：
- 要求被合并的表中，列的类型额列数相同
- UNION，去除重复行
- UNION ALL，不去除重复行

举例：

```
CREATE TABLE ab(a INT,b VARCHAR(50));
INSERT INTO ab VALUES(1,'1');
INSERT INTO ab VALUES(2,'2');
INSERT INTO ab VALUES(3,'3');

CREATE TABLE cd(c INT,d VARCHAR(50));
INSERT INTO cd VALUES(3,'3');
INSERT INTO cd VALUES(4,'4');
INSERT INTO cd VALUES(5,'5');

SELECT * FROM ab
UNION ALL
SELECT * FROM cd;
```

### 连接查询
1.分类
- 内连接
- 外连接
	- 左外连接
	- 右外连接
	- 全外连接（MySql不支持）
- 自然连接

2.内连接
- 方言：SELECT * FROM 表1 别名1, 表2 别名2 WHERE 别名1.xx=别名2.xx;
- 标准：SELECT * FROM 表1 别名1 INNER JOIN 表2 别名2 ON 别名1.xx=别名2.xx;
- 自然：SELECT * FROM 表1 别名1 NATURAL JOIN 表2 别名2;
- 内连接查询出的所有记录都满足条件

方言举例：
```
SELECT * FROM dept,emp WHERE dept.depto=emp.empno;
```
如果dept有15条记录，emp有4条记录，那么`SELECT * FROM dept,emp;`的结果为笛卡尔积，一共60条，那么就需要加上where条件筛选掉。
但是可以发现，都需要带点去访问元素，所以可以起别名。比如：
```
SELECT e.ename,e.sal,d.name FROM emp e,dept d WHERE e.deptno=d.deptno;
```

标准举例：
```
SELECT e.ename,e.sal,d.dname
FROM emp e INNER JOIN dept d
ON e.deptno=d.deptno
```

自然举例：
```
SELECT e.name,e.sal,d.dname
FROM emp e NATURAL JOIN dept d;
```

3.外连接
- 左外连接：SELECT * FROM 表1 别名1,表2 别名2 WHERE 别名1.xx=别名2.xx;
	- 左表记录无论是否满足条件都会被查询出来，而右表只有满足条件才会被查询出来。左表中不满足条件的记录，右表部分都为NULL
- 左外自然连接：SELECT * FROM 表1 别名1 NATURAL LEFT OUTER JOIN 表2 别名2 ON 别名1.xx=别名2.xx;
- 右外连接：SELECT * FROM 表1 别名1 RIGHT OUTER JOIN 表2 别名2 ON 别名1.xx=别名2.xx;
	- 右表记录无论是否满足条件都会被查询出来，而左表只有满足条件才会被查询出来。右表不满足的条件，其坐标部分都是NULL
- 右外自然连接：SELECT * FROM 表1 别名1 NATURAL RIGHT OUTER JOIN 表2 别名2 ON 别名1.xx=别名2.xx
- 全连接：可以使用UNION来完成连接

左外连接举例：
```
/*外连接
外连接有一主一次，左外即左表为！
即emp为主，那么主表中所有的记录无论满不满足条件，都会被打印出来
当不满足条件时，右表部分使用NULL来补位
*/
SELECT e.ename,e.sal
FROM emp e LEFT OUTER JOIN dept d
ON e.deptnp=d.deptno
```

### 子查询
查询中有查询（查看select关键字的个数）
1.出现的位置

- where后作为条件存在
- from后作为表存在（多行多列）

2.条件
- 单行单列：SELECT * FROM 表1 别名1 WHERE 列1 [=,>,<,>=,<=,!=] (SELECT 列 FROM  表2 别名2 WHERE 条件)
- 多行单列：SELECT * FROM 表1 别名1 WHERE 列1 [IN,ALL,ANY] (SELECT 列 FROM 表2 别名2 WHERE 条件)
- 单行多列：SELECT * FROM 表1 别名1 WHERE (列1,列2) IN (SELECT 列1,列2 FROM 表2 别名2 WHERE 条件)
- 多行多列：SLEECT * FROM 表1 别名1 , (SELECT ...) 别名2 WHERE 条件

举例：

```
/*查询本公司最高的员工信息的详细信息*/
SELECT * FROM emp WHERE sal=(SELECT MAX(sal) FROM emp);

SELECT e.empno,e.ename
FROM (SELECT * FROM emp WHERE deptno=30);

SELECT * FROM emp WHERE sal > (SELECT AVG(sal) FROM EMP);
```
