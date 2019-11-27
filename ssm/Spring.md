# Spring简介
1.Spring：春天的含义，该软件行业带来了春天
2.理念：使得现有技术更加实用。它的本身是一个大杂烩，是整合了现有的框架技术。
3.Sprng优点：
- 轻量级的框架
- 提供一种IOC容器：控制反转
- Aop：面向切面编程
- 对事务的支持
- 对框架的支持

4.主要内容：

![1.spring简介](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/1.spring%E7%AE%80%E4%BB%8B.JPG)

5.ioc:inversion of control

案例一：spring_ioc1:
程序结构：
![2.spring_ioc1](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/2.spring_ioc1.JPG)
UserDao.java:

```
public interface UserDao {
	public void getInfo();
}
```

UserDaoMySqlImpl.java:

```
public class UserDaoMySqlImpl implements UserDao{

	/* (non-Javadoc)
	 * @see cn.pu.dao.UserDao#getInfo()
	 */
	@Override
	public void getInfo() {
		// TODO Auto-generated method stub
		System.out.println("mysql信息");
	}
	
}
```

UserDaoOracleImpl.java:

```
public class UserDaoOraclImpl implements UserDao{

	/* (non-Javadoc)
	 * @see cn.pu.dao.UserDao#getInfo()
	 */
	@Override
	public void getInfo() {
		// TODO Auto-generated method stub
		System.out.println("Oracle信息");
	}
	
}
```

UserService.java:

```
public interface UserService {
	public void getUser();
}
```

UserServiceImpl.java:

```
public class UserServiceImpl implements UserService{
	private UserDao userDao = null;
	
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	
	/* (non-Javadoc)
	 * @see cn.pu.service.UserService#getUser()
	 */
	@Override
	public void getUser() {
		// TODO Auto-generated method stub
		userDao.getInfo();
	}

}
```

Test.java:

```
public class Test {
	public static void main(String[] args) {
		UserServiceImpl userService = new UserServiceImpl();
		userService.setUserDao(new UserDaoMySqlImpl());
		userService.getUser();
		System.out.println("--------------");
		userService.setUserDao(new UserDaoOraclImpl());
		userService.getUser();
	}
}
```

这个案例有如下优点：
- 对象由原来程序本身创建，变为程序接收对象
- 程序员主要精力集中于业务实现
- 实现了service和dao的解耦工作。serviceceng和dao层实现了分离，没有直接依赖关系。
- 如果dao的实现发生了改变，应用程序本身不用改变。

# hello_spring

步骤：
- 导入相关jar包
- 编写spring配置文件
- 

整体框架：
![3.srping_hello_整体](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/3.srping_hello_%E6%95%B4%E4%BD%93.JPG)
引用库：
![4.spring_hello_libs](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/4.spring_hello_libs.JPG)

Hello.java文件

```
public class Hello {

	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public void show(){
		System.out.println("hello,"+name);
	}
}
```

bean.xml文件：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
<!-- bean就是java对象 由srping容器创建和管理 -->
<bean name="hello" class="cn.pu.bean.Hello">
<property name="name" value="张三"/>
</bean>
</beans>
```

Test.java文件：

```
public class Test {
	public static void main(String[] args) {
		//解析bean.xml文件 申请管理响应的bean对象
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		Hello hello = (Hello)context.getBean("hello");
		hello.show();
	}
}
```

那么这个Hello对象由谁创建的?是由Spring容器来创建的
Hello对象属性也是由Spring容器来创建的。
这个过程就叫做控制反转，控制的内容指的是谁来控制对象的创建；传统的应用程序对象的创建是由程序本身创建的。使用Spring后，是由Spring来创建对象的。所以，所谓控制反转，就是以前对象是由程序本身创建，使用spring后，程序变成主动接收Spring创建好的对象。

# Spring_ioc实现
ioc是一种思想。由主动接收变为被动接受。
IOC的实现其实就是bean工厂

举例：

程序框架：
![5.spring_ioc2整体](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/5.spring_ioc2%E6%95%B4%E4%BD%93.JPG)
库文件同上个程序，代码同第一个程序，主要是Test.java文件和beans.xml文件

Test.java:

```
public class Test {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		UserService us = (UserService)ac.getBean("service");
		us.getUser();
	}
}
```

beans.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
<!-- bean就是java对象 由srping容器创建和管理 -->
<bean id="mysqlDao" class="cn.pu.dao.impl.UserDaoMySqlImpl"/>
<bean id="oracleDao" class="cn.pu.dao.impl.UserDaoOraclImpl"/>
<bean id="service" class="cn.pu.service.impl.UserServiceImpl">
	<property name="userDao" ref="mysqlDao"></property>
</bean>
</beans>
```

# 使用ioc来创建对象的3种方式
1.通过无参构造的方法来创建：

User.java:

```
public class User {
	private String name;
	
	/**
	 * 
	 */
	public User() {
		// TODO Auto-generated constructor stub
		System.out.println("无参构造");
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
```

Test.java:

```
public class Test {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		User user = (User)ac.getBean("user");
		user.getName();
	}
}
```

beans.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
<!-- bean就是java对象 由srping容器创建和管理 -->
<bean id="user" class="cn.pu.vo.User">
	<property name="name" value="张三"></property>
</bean>
</beans>
```

2.通过有参构造方法来创建

User.java:

```
public class User {
	private String name;
	
	/**
	 * 
	 */
	public User() {
		// TODO Auto-generated constructor stub
		System.out.println("无参构造");
	}
	public User(String name){
		super();
		this.name = name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
```

Test.java:

```
public class Test {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		User user = (User)ac.getBean("user1");
		System.out.println(user.getName());
	}
}
```

beans.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
<!-- bean就是java对象 由srping容器创建和管理 -->
<bean id="user" class="cn.pu.vo.User">
	<property name="name" value="张三"></property>
</bean>
<bean id="user1" class="cn.pu.vo.User">
	<!-- index指的是构造参数的下标，从0开始 -->
	<constructor-arg index="0" value="李四"/>
    <!-- 也可以根据参数名赋值 name指的是参数名-->
	<!--  <constructor-arg name="name" value="李四"></constructor-arg> -->
    <!-- 也可以根据参数类型类设置 -->
	<!-- <constructor-arg type="String" value="王五"/>  -->
</bean>
</beans>
```

3.通过工厂方法创建对象（静态工厂和动态工厂）

静态工厂：
beans.xml:

UserFactory.java

```
public class UserFactory {
	public static User newInstance(String name){
		return new User(name);
	}
}
```

beans.xml:

```
	<bean id="user2" class="cn.pu.factory.UserFactory" factory-method="newInstance">
		<constructor-arg index="0" value="王五"/>
	</bean>	
```

动态工厂：

UserDynamicFactory.java:

```
public class UserDynamicFactory {
	public User newInstance(String name){
		return new User(name);
	}
}
```

beans.xml:

```
<bean id="userFactory2" class="cn.pu.factory.UserDynamicFactory"/>
<bean id="userFactory1" factory-bean="userFactory2" factory-method="newInstance">
		<constructor-arg index="0" value="王五"/>
</bean>	
```

Test.java:

```
public class Test {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		User user = (User)ac.getBean("userFactory1");
		System.out.println(user.getName());
	}
}
```

# spring配置文件
1、alias可以起别名
2、bean配置文件
- id是bean的标识符，要唯一，如果没有配置id,name默认标识符
- 如果配置id，又配置了name，那么name是别名
- name可以设置多个别名，分隔符可以实空格、逗号、分号
- class是bean的全限定名=包名+类名
- 如果不配置id和name，那么可以根据applicationContext.getBean(class)获取对象
比如：
```
<bean id="user" name="hello u2,u3;u4"class="cn.pu.vo.User">
	<property name="name" value="张三"></property>
</bean>
```
3、import：可以引用外部xml文件,可以进行团队协作

![6.import](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/6.import.JPG)

beans.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
	<import resource="config/spring/entity.xml"/>
</beans> 
```

entity.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
<!-- bean就是java对象 由srping容器创建和管理 -->
<bean id="user" class="cn.pu.vo.User">
	<property name="name" value="张三"></property>
</bean>
<bean id="user1" class="cn.pu.vo.User">
	<!-- index指的是构造参数的下标，从0开始 -->
	<constructor-arg index="0" value="李四"/>
	<!-- 也可以根据参数名赋值 -->
	<!--  <constructor-arg name="name" value="李四"></constructor-arg> -->
	<!-- 也可以根据参数类型类设置 -->
	<!-- <constructor-arg type="String" value="王五"/>  -->
	<!-- 使用工厂模式创建 -->
</bean>
	<bean id="userFactory2" class="cn.pu.factory.UserDynamicFactory"/>
	<bean id="userFactory1" factory-bean="userFactory2" factory-method="newInstance">
		<constructor-arg index="0" value="王五"/>
	</bean>	
</beans>     
```

# 依赖注入 DI
1、依赖注入-dependency injection
依赖：指的是bean对象的创建依赖于容器。Bean的对象依赖于资源
注入：指的是Bean对象依赖的资源由容器来设置和装配。
2、spring的注入：构造器注入

```
<bean id="user1" class="cn.pu.vo.User">
	<!-- index指的是构造参数的下标，从0开始 -->
	<constructor-arg index="0" value="李四"/>
	<!-- 也可以根据参数名赋值 -->
	<!--  <constructor-arg name="name" value="李四"></constructor-arg> -->
	<!-- 也可以根据参数类型类设置 -->
	<!-- <constructor-arg type="String" value="王五"/>  -->
	<!-- 使用工厂模式创建 -->
</bean>
```
3、spring注入：setter注入
要求被注入的属性必须有set方法。set方法的方法名由set+属性首字母大写。如果属性是boolean，没有get方法，是is

1.常量注入

```
	<bean id="student" class="cn.pu.vo.Student">
	<property name="name" value="zhangsan"/>
	</bean>
```

2.bean的注入（对象注入）

```
	<bean id="addr" class="cn.pu.vo.Address">
		<property name="address" value="江苏南京"/>
	</bean>
	<bean id="student" class="cn.pu.vo.Student">
		<property name="name" value="zhangsan"/>
		<property name="addr" ref="addr" />
	</bean>
```

3.数组注入

```
	<bean id="student" class="cn.pu.vo.Student">
		<property name="name" value="zhangsan"/>
		<property name="addr" ref="addr" />
		<property name="books">
			<array>
				<value>傲慢与派偏见</value>
				<value>西游记</value>
				<value>雾都孤儿</value>
			</array>
		</property>
	</bean>
```

4.List注入

```
    <property name="hobbies">
        <list>
            <value>羽毛球</value>
            <value>跑步</value>
            <value>足球</value>
            <value>篮球</value>
        </list>
    </property>
```

5.map注入

```
    <property name="card">
        <map>
            <entry key="中国银行" value="1234567878"/>
            <entry>
                <key><value>建设银行</value></key>
                <value>324521326787</value>
            </entry>
        </map>
    </property>
```

6.set注入

```
    <property name="games">
        <set>
            <value>lol</value>
            <value>刀塔</value>
            <value>cs</value>
            <value>dnf</value>
        </set>
    </property>
```

7.Null注入

```
<property name="wife"><null/></property>
```

- properties注入

```
    <property name="info">
        <props>
            <prop key="学号">20</prop>
            <prop key="性别">男</prop>
            <prop key="姓名">小青</prop>
        </props>
    </property>
```

8.P命名空间注入

在xml头文件中加入：
```
xmlns:p="http://www.springframework.org/schema/p"
```

User.java(User类)

```
public class User {
	private String name;
	private String addr;
	public void setName(String name) {
		this.name = name;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public void show(){
		System.out.println("name="+name+" "+"address="+addr);
	}
}
```

beans.xml:

```
<bean id="user" class="cn.pu.vo.User" p:name="风清扬" p:addr="江苏南京"/>
```

9.c命名空间注入

在xml头文件中加入：
```
xmlns:c="http://www.springframework.org/schema/c"
```

beans.xml:

```
<bean id="user" class="cn.pu.vo.User" c:name="风清扬" c:addr="江苏南京"/>
```
但是C命名空间注入需要有对应参数的构造方法

# bean的作用域

1.单例模式
singleton:单例模式，整个容器中只有一个对象实例

举例：

```
	<bean id="addr" class="cn.pu.vo.Address" scope="sigleton">
		<property name="address" value="江苏南京"/>
	</bean>
```

2.原型

prototype:每次获取bean都产生一个新的对象：scope="prototype"

3.request
每次请求时候创建一个新的对象

4.session

在会话的范围内是一个对象

5.global session

只在protlet下有用，表示的是application

6.application

在这个应用范围内有效

# 静态代理
1.静态代理的角色分析

抽象角色：一般使用或者抽象类来实现。
真实角色：被代理的角色
代理角色：代理真实角色，代理真实角色后，一般会做一些附属的操作（如果没有真实就直接做了）
客户：使用代理角色进行一些操作

举例：房东，中介，客户

抽象接口：出租

```
public interface Rent {
	public void rent();
}
```

真实角色：房东

```
public class Host implements Rent{
	//租房子
	public void rent(){
		System.out.println("房屋出租");
	}
}
```

代理角色：中介

```
public class Proxy implements Rent{
	private Host host;//房主授权
	public void rent(){
		seeHouse();
		host.rent();
		fare();
	}
	//看房
	private void seeHouse(){
		System.out.println("带房客看房子");
	}
	//收中介费
	private void fare(){
		System.out.println("收取中介费");
	}
	/**
	 * 
	 */
	public Proxy(Host host) {
		// TODO Auto-generated constructor stub
		this.host = host;
	}
}
```

客户：

```
public class Client {
	public static void main(String[] args) {
		Host host = new Host();
		Proxy proxy = new Proxy(host);
		proxy.rent();
	}
}
```

![7.代理模式](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/7.%E4%BB%A3%E7%90%86%E6%A8%A1%E5%BC%8F.JPG)

静态代理的好处：
- 使得真实角色处理的业务更加纯粹，不用去关注一些公共业务。
- 公共业务由代理来完成，实现了业务的分工
- 公共业务发生扩展时变得更加集中和方便

缺点：
多了代理类，工作量变大了，开发效率降低了

# 动态代理

1.动态代理和静态代理的角色是一样的。
2.动态代理的代理类是动态生成的
3.动态代理分为两类：基于接口的动态代理和基于类的动态代理

- 基于接口的动态代理也就是JDK的动态代理
- 基于类的动态代理：cglib

现在使用javasist来生成动态代理

今天使用JDK的动态代理

4.jdk动态代理：proxy类和InvocationHandler接口

InvocationHandler是代理实例的调用处理程序实现的接口。
每个代理实例都具有一个关联的调用处理程序。对代理实例调用方法时，将对调用方法进行编码并将其指派到它的调用处理程序invoke方法。

invoke方法：
```
invoke(Object proxy,Method method,Object[] args):在代理实例上处理方法调用并返回结果
```

Proxy类：
Proxy提供用于创建动态代理类和实例的静态方法，他还是由这些方法创建的所有动态代理类的父类。

```
Static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)
返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理程序。
```

举例：

程序结构:

![8.动态代理](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/8.%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86.JPG)

proxyInovatonHandler.java:

```
public class ProxyInovationHandler implements InvocationHandler{
	private Object target;//真实对象 
	
	/* 
	 * proxy是代理类
	 * method 代理类的调用处理程序的方法对象
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	/**
	 * @param target the target to set
	 */
	public void setTarget(Object target) {
		this.target = target;
	}
	/*
	 * 生成代理类
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object getProxy(){
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		fare();
		seeHouse();
		Object result = method.invoke(target, args);
		return result;
	}
	//看房
	private void seeHouse(){
		System.out.println("带房客看房子");
	}
	//收中介费
	private void fare(){
		System.out.println("收取中介费");
	}
}
```

Host.java:

```
public class Host implements Rent{
	//租房子
	public void rent(){
		System.out.println("房屋出租");
	}
}
```

Rent.java:

```
public interface Rent {
	public void rent();
}
```

Client.java:

```
public class Client {
	public static void main(String[] args) {
		Host host = new Host();
		Proxy proxy = new Proxy(host);
		proxy.rent();
	}
}
```

动态代理对比静态代理的好处就是，它可以使用一套模板，代理不同的接口，不必再像静态代理那样创建很多类。

# 面向切面编程：aop
1.aop:aspect oriented programming,面向切面编程

面向切面编程（AOP）通过提供另外一种思考程序结构的途径来弥补面向对象编程的不足。在OOP中，模块化的单元是类，而在AOP中，模块化的单位则是切面。面向切面编程是面向对象中的一种方式而已。在代码执行过程中，动态嵌入其他代码，叫做面向切面编程。

面向切面编程，就是将交叉业务逻辑封装成切面，利用AOP的功能将切面织入到主业务逻辑中。所谓交叉业务逻辑是指，通用的，与主业务逻辑无关的代码，如安全检查，事物，日志等。若不使用AOP，则会出现代码纠缠，即交叉业务逻辑与主业务逻辑混合在一起。这样，会使业务逻辑变得混杂不清。、

比如银行取款：

![9.银行取款](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/9.%E9%93%B6%E8%A1%8C%E5%8F%96%E6%AC%BE.JPG)

这两者，都有一个相同的验证用户的流程
　　这个时候 AOP 就可以来帮我们简化代码了，首先，写代码的时候可以不写这个验证用户的步骤，即完全不考虑验证用户，写完之后，在另外一个地方，写好验证用户的代码，然后告诉 Spring 你要把这一段代码加到哪几个地方，Spring就会帮你加过去，这里还只是两个地方，如果有多个控制流，这样写代码会大大节约时间。

上面那个 验证用户 的方框，我们可以把它当成一块板子，在这块板子上插入一些控制流程，这块板子就可以当成是 AOP 中的一个切面。所以 AOP 的本质是在一系列的纵向的控制流程中，把那些相同的子流程提取成一个横向的面，把纵向流程画成一条直线，而 AOP 相当于把相同的地方连起来了。

![10.切面示意](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/10.%E5%88%87%E9%9D%A2%E7%A4%BA%E6%84%8F.JPG)

2.名词解释

| 术语 | 说明 |
|--------|--------|
|切面|切面泛指交叉业务逻辑。比如事物处理，日志处理就可以理解为切面。|
|织入|织入是指将切面代码插入到目标对象的过程|
|连接点|连接点指切面可以织入的位置|
|切入点|切入点指切面具体织入的位置|
|通知|通知（Advice）是切面的一种实现，可以完成简单织入功能（织入功能就是在这里完成的）。通知定义了增强代码切入到目标代码的时间点，是目标方法执行之前执行，还是之后执行等。|
|顾问|顾问（Advisor）是切面的另一种实现，能够将通知以更为复杂的方式织入到目标对象中，是将通知包装为更复杂切面的装配器。不仅指定了切入时间点，还可以指定具体的切入点。|

通知类型：

|通知类型|说明|
|--------|--------|
|前置通知（MethodBeforeAdvice)|目标方法执行之前调用|
|后置通知（AfterReturningAdvice）|目标方法执行完成之后调用|
|环绕通知（MethodInterceptor）|目标方法执行前后都会调用方法，且能增强结果|
|异常处理通知（ThrowsAdvice）|目标方法出现异常调用|

3.使用spring实现aop

程序结构：

![11.spring_aop](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/11.spring_aop.JPG)

额外的包：
aopalliance.jar,aspectjweaver-1.8.9.jar

log.java:

```
public class Log implements MethodBeforeAdvice{

	/* @param method 被调用的方法对象
	 * @param args 被调用的方法的参数
	 * @param target 被调用的方法的目标对象
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println(target.getClass().getName()+"的"+method.getName()+"方法执行了");
	}
	
}
```

UserService.java:

```
public interface UserService{
	public void add();
	public void delete();
}
```

UserServiceImpl.java:

```
public class UserServiceImpl implements UserService{

	/* (non-Javadoc)
	 * @see cn.pu.service.UserServiceImpl#add()
	 */
	@Override
	public void add() {
		// TODO Auto-generated method stub
		System.out.println("增加用户");
	}

	/* (non-Javadoc)
	 * @see cn.pu.service.UserServiceImpl#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		System.out.println("删除用户");
	}

}
```

Test.java:

```
public class Test {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		UserService userService = (UserService)ac.getBean("userService");
		userService.add();
	}
}
```

bean.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">
	<bean id="userService" class="cn.pu.service.UserServiceImpl" />
	<bean id="log" class="cn.pu.log.Log" />
	<aop:config>
		<!-- 切入点 -->
		<aop:pointcut expression="execution(* cn.pu.service.UserServiceImpl.add())" id="pointcut"/>
		<aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
	</aop:config>
</beans> 
```

关于这一句：

```
		<aop:pointcut expression="execution(* cn.pu.service.UserServiceImpl.add())" id="pointcut"/>
```

可以对类中的所有方法进行一个切片：

```
		<aop:pointcut expression="execution(* cn.pu.service.UserServiceImpl.*())" id="pointcut"/>
```

当然，也可使对切片类：Log进行修改，让其实现afterReturning接口就可以实现后置通知

4.使用自定义类来实现aop

程序结构:

![12.spring_aop2](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/12.spring_aop2.JPG)

和前面的程序只有在Log.java和beans.xml的配置文件中有所不同：

Log.java:

```
public class Log {

	public void before(){
		System.out.println("---前置通知---");
	}
	public void after(){
		System.out.println("---后置通知---");
	}
}
```

beans.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">
	<bean id="userService" class="cn.pu.service.UserServiceImpl" />
	<bean id="log" class="cn.pu.log.Log" />
	<aop:config>
		<!-- 关联切片类 -->
		<aop:aspect ref="log">
			<aop:pointcut expression="execution(* cn.pu.service.UserServiceImpl.*(..))" id="pointcut"/>
			<!-- 前置通知函数 -->
			<aop:before method="before" pointcut-ref="pointcut"/>
			<!-- 后置通知函数 -->
			<aop:after method="after" pointcut-ref="pointcut"/>
		</aop:aspect>
	</aop:config>
</beans> 
```

5.使用注解来实现aop

程序结构和上面的一样，不同之处在于beans.xml和Log.java:

Log.java:

```
@Aspect//表示为切面类
public class Log {
	//表示为前置通知函数
	@Before("execution(* cn.pu.service.UserServiceImpl.*(..))")
	public void before(){
		System.out.println("---前置通知---");
	}
    //表示为后置通知函数
	@After("execution(* cn.pu.service.UserServiceImpl.*(..))")
	public void after(){
		System.out.println("---后置通知---");
	}
}
```
