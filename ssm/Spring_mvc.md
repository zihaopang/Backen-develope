# spring mvc简介

1.首先，什么是mvc?MVC的理念就是把数据处理、数据展示(界面)和程序/用户的交互三者分离开的一种编程模式。如图：

![13.mvc介绍](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/13.mvc%E4%BB%8B%E7%BB%8D.JPG)

2.为什么要使用MVC?

```
程序通过将M(Model)和V(View)的代码分离，实现了前后端代码的分离，会带来几个好处

（1）可以使同一个程序使用不同的表现形式，如果控制器反馈给模型的数据发生了变化，那么模型将及时通知有关的视图，视图会对应的刷新自己所展现的内容

（2）因为模型是独立于视图的，所以模型可复用，模型可以独立的移植到别的地方继续使用

（3）前后端的代码分离，使项目开发的分工更加明确，程序的测试更加简便，提高开发效率

其实控制器的功能类似于一个中转站，会决定调用那个模型去处理用户请求以及调用哪个视图去呈现给用户

```

3.MVC框架要做哪些事情？

- 将url映射到java类或者java类的方法
- 封装用户提交的数据
- 处理请求，调用相关的业务处理，封装响应的数据
- 将响应数据进行渲染，jsp,html,freemarker等等

4.spring mvc是一个轻量级的，基于请求响应的mvc框架
5.为什么要学习spring mvc?

- 性能比struts2好
- 简单，便捷，易学
- 和spring无缝集成（使用spring ioc和aop）
- 使用约定优于配置（遵守约定，就会少很多配置）
- 能够进行简单的junit测试
- 支持Restful风格
- 异常处理
- 本地化，国际化
- 数据验证，类型转换等
- 拦截器

6.springmvc处理流程简介

![14.spring处理流程](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/14.spring%E5%A4%84%E7%90%86%E6%B5%81%E7%A8%8B.png)

- 客户端（浏览器）发送请求，直接请求到DispatcherServlet。
- DispatcherServlet根据请求信息调用HandlerMapping，解析请求对应的Handler。
- 解析到对应的Handler后，开始由HandlerAdapter适配器处理。
- HandlerAdapter会根据Handler来调用真正的处理器开处理请求，并处理相应的业务逻辑。
- 处理器处理完业务后，会返回一个ModelAndView对象，Model是返回的数据对象，View是个逻辑上的View。
- ViewResolver会根据逻辑View查找实际的View。
- DispaterServlet把返回的Model传给View。
- 通过View返回给请求者（浏览器）

# hello Spring_mvc案例

步骤：
- 导入相关jar包
- 配置web.xml文件：即配置分发器
- 添加springmvc的配置文件：默认在WEB-INF下面添加[DispatcherServlet Name]-servlet.xml文件
- 编写HelloController.java，即控制器
- 编写springmvc配置文件（要先配置xml的catlog）

程序结构：

![17.程序结构](http://)

HelloController.java:

```
/**
 * @author pangzihao
 * HelloController
 */
public class HelloController implements Controller{

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView();
		//封装要显示到视图中的数据
		mv.addObject("msg", "hello springmvc");
		
		//视图名称
		mv.setViewName("hello");
		return mv;
	}

}
```

springmvc-servlet.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    xsi:schemaLocation="http://www.springframework.org/schema/beans 
    	http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
        http://www.springframework.org/schema/context 
        http://www.springframework.org/schema/context/spring-context-4.2.xsd
        http://www.springframework.org/schema/mvc 
        http://www.springframework.org/schema/mvc/spring-mvc-4.2.xsd">
   <!-- 配置handlerMapping -->
   <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping" />
   <!-- 配置HandlerAdapter -->
   <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter" />
   <!-- 配置渲染器 -->
   <bean id="jspViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
       <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
       <!-- 结果视图的前缀，即如果视图名称为hello，那么文件的位置就在 /WEB-INF/jsp/hello.jsp -->
       <property name="prefix" value="/WEB-INF/jsp/"/>
       <!-- 结果视图的后缀 -->
       <property name="suffix" value=".jsp"/>
   </bean>
   <!-- 配置请求和处理  -->
   <bean name="/hello.do" class="cn.pu.controller.HelloController"/>
</beans>
```

web.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>springmvc_hello</display-name>
  <servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```

# 第二讲：使用注解开发springmvc

举例：

程序结构：

![18.spring_annotation结构](http://)

1、导入jar包
和上面的一样
2、web.xml配置

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">
  <display-name>springmvc_annotation</display-name>
  
  <!-- DispatcherServlet配置 -->
  <servlet>
  	<servlet-name>springmvc</servlet-name>
  	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  	<!-- 配置Dispatcher参数，第一个是contextConfigLocation,就是配置文件的位置 -->
  	<init-param>
  		<param-name>contextConfigLocation</param-name>
  		<param-value>classpath:mvc.xml</param-value>
  	</init-param>
  	<load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
  	<servlet-name>springmvc</servlet-name>
  	<url-pattern>*.do</url-pattern>
  </servlet-mapping>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```

其中这一段代码：

```
  	<init-param>
  		<param-name>contextConfigLocation</param-name>
  		<param-value>classpath:mvc.xml</param-value>
  	</init-param>
```

表明mvc的配置文件位置（src/mvc）

3、Coltroller

```
/**
 * @author pangzihao
 * HelloController
 */
@Controller
public class HelloController {
	@RequestMapping("/hello")
	public ModelAndView hello(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mv = new ModelAndView();
		//封装要显示到视图中的数据
		mv.addObject("msg", "hello springmvc annotation");
		
		//视图名称
		mv.setViewName("hello");
		return mv;
	}
```

4、springmvc配置

mvc.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    xsi:schemaLocation="http://www.springframework.org/schema/beans 
    	http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
        http://www.springframework.org/schema/context 
        http://www.springframework.org/schema/context/spring-context-4.2.xsd
        http://www.springframework.org/schema/mvc 
        http://www.springframework.org/schema/mvc/spring-mvc-4.2.xsd">
        
   <!-- 配置渲染器 -->
   <bean id="jspViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
       <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
       <!-- 结果视图的前缀，即如果视图名称为hello，那么文件的位置就在 /WEB-INF/jsp/hello.jsp -->
       <property name="prefix" value="/WEB-INF/jsp/"/>
       <!-- 结果视图的后缀 -->
       <property name="suffix" value=".jsp"/>
   </bean>
   <!-- 在controoler包下扫描控制器 -->
   <context:component-scan base-package="cn.pu.controller" />
</beans>
```


