# XML入门
### XML的简介
1.extensible Makeup Language:可扩展标记语言

- 标记型语言：html也是标记型语言，xml也是，都是使用标签来操作
- 可扩展：html里面的标签的固定的，每个标签有特定的含义。而xml中的标签可以自己定义，比如<person></person>

2.xml用途

- html是用于显示数据，xml也可以显示数据（不是主要功能）
- xml的主要功能是存储数据

3.xml是w3c组织发布的技术
4.xml右两个版本 1.0，1.1

- 使用的都是1.0版本，1.1版本不能向下兼容

### XML的应用
1.不同的系统之间传输数据

![1.xml应用1](http://)
2.用来表示生活中有关系的数据

![2.xml应用2](http://)
3.经常用在配置文件中

比如一些用户名和密码

### XML的语法
1.xml的文档申明

- 创建一个文件 后缀名是 .xml
- 如果写xml,第一步 必须要有一个文档申明（写了文档申明之后，表示写xml文件的内容）
- 比如`<?xml version="1.0" encoding="gbk"?>`
- 文档声明必须写在 第一行第一列
- 文档声明的属性：version:xml的版本；encoding:xml编码：gbk,utf-8,iso8869-1(不包含中文)；standalone:是否需要依赖其他文件 yes/no
- xml中中文乱码的解决：把设置的编码和保存时候的编码一致即可，原因：

  ![3.xml乱码问题的解决](http://)

2.定义元素（标签）
3.定义属性
4.注释
5.特殊字符
6.CDATA区
7.PI指令

### XML元素的定义
1.标签的定义

- 标签的定义有开始必须要有结束，<person></person>
- 标签没有内容，可以在标签内结束:<aa/>
- 标签可以嵌套，必须要合理嵌套，即不要混乱嵌入
- xml只能有一个根标签，即最外层的那个标签
- 由于在xml中，空格和换行都被作为原始内容被处理。所以在编写xml文件时候，使用换行和缩进让文件内容更加清晰的习惯要改变。
- xml中标签的命名规则
（1）xml标签区分大小写：`<p><P>`:这两个标签是不一样的
（2）xml的标签不能以数字或者下划线开头
（3）xml的标签不能以xml,Xml,Xml等开头：<xmla><xmlB>这些都是不正确的
（4）xml的标签不能包含空格和冒号：<a b><b:c>：这些是不正确的

### XML中属性的定义
属性定义的要求：
1.一个标签上可以有多个属性：<person id1="aaa" id2="bbb"></person>
2.属性名称不能相同：<person id1="aaa" id1="bbb"></person>,这个是不正确的，不能有两个id1.
3.属性名称和属性值之间使用=，属性值使用引号包起来（可以是单引号，也可以是双引号）
4.xml属性的名称规范和元素的名称规范一致

### xml中的注释
<!-- 被注释的内容 -->，注释不能够嵌套，注释不能够放到第一行。

### xml中的特殊字符
- 如果想要在xml中显示 a<b，不能正常显示，因为把<当左了标签
- 如果想要显示，需要对特殊字符<进行转义，即小于号用&lt;表示，大于号用&gt;表示，&符号用&am;p进行转义，双引号用&quot;进行表示，单引号用&apos;进行表示。

### CDATA区
比如在xml中需要判断：
```
<b>if(a<b && b<c && d>f)</b>
```
使用CADATA区的话，这些小于号大于号就都不用转义了，因为这些内容都放在了CDATA里面了
CDATA区写法：
```
<![CDATA[ 内容]]>,上面的情况就是：
<![CDATA[<b>if(a<b && b<c && d>f)</b>]]>
```

### PI指令（处理指令）
可以在XML中设置样式，写法：<?xml-stylesheet type="text/css" href="css的路径"?>

### XML语法总结
1.所有XML元素标签必须成对出现
2.XML标签对大小写敏感
3.XML必须正确的进行嵌套
4.XML文档必须有根元素
5.XML属性值必须加引号
6.特殊字符必须转义
7.XML中的空格、回车和换行解析时候会被保留

### xml约束
比如定义一个人的属性

```
<xml version="1.0" encoding="utf-8">
<person>
	<name>zhangsan</name>
    <age>20</age>
    <猫>1000</猫>
</person>
```

那么猫并不是人的属性，所以需要对一些属性进行约束。
xml的约束技术：dtd的约束和schema的约束。


# DTD 文档定义类型

### dtd的快速入门
首先先创建一个后缀为dtd的约束文件，对于上面的person.xml文件：

```
<xml version="1.0" encoding="utf-8">
<person>
	<name>zhangsan</name>
    <age>20</age>
</person>
```

该dtd约束文件就要对该person.xml文件进行约束，person.dtd文件：

```
<ELEMENT person (name,age)>
<ELEMENT name (#PCDATA)>
<ELEMENT age (#PCDATA)>
```

这里面需要对person.xml中的元素进行划分，划分为两中元素类型：
1.简单元素：没有子元素的元素，写法为<!ELEMENT 元素名称 (#PCDATA)>
2.复杂元素：有子元素的元素，写法为：<!ELEMENT 元素名称 (子元素1,子元素2,...)>

然后再在xml文件中引入dtd文件：<!DOCTYPE 根元素的名称 SYSTEM "dtd文件的路径">,不可写在第一行。

```
<xml version="1.0" encoding="utf-8">
<!DOCTYPE person SYSTEM "person.dtd">
<person>
	<name>zhangsan</name>
    <age>20</age>
</person>
```

### DTD的引入方式
（1）也就是上面说的，引入外部dtd文件的方式
（2）使用内部的dtd文件

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE person [
	<!ELEMENT person (name,age)>
	<!ELEMENT name (#PCDATA)>
	<!ELEMENT age (#PCDATA)>
]>
<person>
	<name>zhangsan</name>
	<age>20</age>
</person>	
```
（3）使用网络上的dtd文件：<!DOCTYPE 根元素 PUBLIC "DTD名称" "DTD文件的URL">

### 使用DTD定义元素
1.语法格式：<!ELEMENT 元素名 约束>

2.对简单元素的约束：<!ELEMENT name (#PCDATA)>，其中(#PCDATA)是约束的字符串类型，其他类型有：
- EMPTY:非空
- ANY：任意值

3.复杂元素的约束：<!ELEMENT 元素名称 (子元素1,子元素2,...)>，有下面三个约束条件

- +：子元素可以出现一次或者多次
- ?: 子元素可以出现一次
- *：表示0次或者多次

比如<!ELEMENT person (name+,age)>，那么name标签就可以出现一次或者多次

4.如果DTD复杂元素中定义的子元素用竖线隔开，那么就表示这些子元素只可以使用一个，比如：<!ELEMENT 元素名称 (子元素1|子元素2|...)>

### 使用DTD定义属性
语法格式：<!ATTLIST 元素名称 
				属性名称 属性类型 约束>
其中属性类型有：
- CDATA：表示属性的取值为普通的文本字符串
- 枚举：表示只能在一定范围内出现的值，但是每次只能出现其中的一个
- ID：值只能是字母或者下划线开头

属性的约束设置有：

```
- #REQUIRE：表示该属性必须出现
- #IMPLIED：表示该属性可有可无
- #FIXED：表示该属性的取值为一个固定值
- 直接值：表示属性的取值为默认值
```

举例：

```
<?xml version="1.0" encoding="UTF-8"?>
<!-- <!DOCTYPE person SYSTEM "person.dtd"> -->
<!DOCTYPE person [
	<!ELEMENT person (name,age,birthday)>
	<!ELEMENT name (#PCDATA)>
	<!ELEMENT age (#PCDATA)>
	
	<!ATTLIST birthday 
		ID1 CDATA #REQUIRED
	>
	<!ATTLIST age
		ID2 (AA|BB|CC|DD) #REQUIRED
	>
	<!ATTLIST name 
		ID3 ID #IMPLIED
	>
]>
<person>
	<name ID3="AA">zhangsan</name>
	<age ID2="AA">20</age>
	<birthday ID1="AAA">2015</birthday>
</person>	
```

### 定义实体
概念：在DTD中定义，在XML中处理
语法：<!ENTITY 实体名称 "实体内容">
引用方式（注意是在XML中引用）：&实体名称;(分号不要忘记)

举例：

```
<?xml version="1.0" encoding="UTF-8"?>
<!-- <!DOCTYPE person SYSTEM "person.dtd"> -->
<!DOCTYPE person [
	<!ELEMENT person (name,age,birthday,gender)>
	<!ELEMENT name (#PCDATA)>
	<!ATTLIST name 
		ID3 ID #IMPLIED
	>
	
	<!ELEMENT age (#PCDATA)>
	<!ATTLIST age
		ID2 (AA|BB|CC|DD) #REQUIRED
	>
	
	<!ELEMENT birthday (#PCDATA)>
	<!ATTLIST birthday 
		ID1 CDATA #REQUIRED
	>
	
	<!ELEMENT gender (#PCDATA)>
	<!ATTLIST gender 
		ID4 CDATA #FIXED "MALE"
	>
	
	<!ELEMENT school (#PCDATA)>
	<!ATTLIST school 
		ID1 CDATA "WWW"
	>
	
	<!ENTITY TEST "HAHAHA">
]>
<person>
	<name ID3="AA">&TEST;</name>
	<age ID2="AA">20</age>
	<birthday ID1="AAA">2015</birthday>
	<gender ID4="MALE"></gender>
	
</person>
```