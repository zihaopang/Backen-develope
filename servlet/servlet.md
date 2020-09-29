# servlet概述
1.什么是servlet?

servlet是javaweb的三大组件之一，它属于动态资源。Servlet的作用是处理请求，服务器会把接收到的请求交给Servlet来处理，在Servlet中通常需要：

- 接收请求数据
- 处理请求
- 完成响应

例如客户端发出登录请求，或者输出注册请求，这些请求都应该由servlet来完成处理！servlet需要我们自己来编写，每个Servlet必须实现javax.servlet.Servlet接口。

servlet功能介绍：

![14.servlet功能介绍](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/14.servlet%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D.JPG)

每个servlet都会有不同的功能，比如登陆，注册等等

# 实现servlet的方式：

实现servlet有三种方式：
- 实现javax.servlet.Servlet接口
- 继承javax.servlet.GenericServlet类
- 继承javax.servlet.http.HttpServlet类

通常我们会去继承HttpServlet类来完成我们的Servlet,但学习Servlet还是要从javax.servlet.Servlet接口开始学习

实现javax.servlet.Servlet接口：

```java
/*
 * servlet中的方法不由我们来调用，由tomcat来调用。
 * servlet对象也不由我们来创建，由tomcat来创建
 */
public class AServlet implements Servlet {
	/*
	 * 他也是生命周期方法
	 * 在Servlet被销毁之前被调用，并且只会被调用一次
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy...");
	}
	
	/*
	 * 可以获取Servlet的配置信息
	 */
	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		System.out.println("getServletConfig...");
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		System.out.println("getServletInfo...");
		return null;
	}
	/*
	 * 他是生命周期方法
	 * 他会在Servlet对象创建之后马上执行，并且只执行一次
	 */
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init...");
	}
	/*
	 * 他是生命周期方法
	 * 它会被调用多次
	 * 每次处理请求都是调用这个方法
	 */
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("service...");
	}

}
```

servlet的生命周期方法有：
- void init():出生之后
- public void service(ServletRequest request, ServletResponse response):每次访问都会被调用
- public void destroy()：临死之前（一次）

servlte的特性：
- 单例，一个类只有一个对象，当然可能存在多个Servlet类
- 线程不安全的，效率比较高

Servlet由我们来写，但是对象由服务器来创建，并且由服务器调用相关的方法

如何让浏览器访问Servlet
- 给Servlrt指定一个Servlet路径（让Servlet与一个路径绑定在一起）
- 浏览器访问Servlet路径

所以要在web.xml中对servlet进行配置：
web.xml:

```xml
<servlet>
	<servlet-name>XXX<servlet-name>
    <servlet-class>com.web.servlet</servlet-class>
</servlet>

<servlet-mapping>
	<servlet-name>XXX</servlet-name>
    <url-pattern>/Aservlet</url-pattern>
</servlet-mapping>
```

含义就是，访问url-pattern路径，就相当于访问了一个名字为XXX的servlet,而这个servlet的所对应的类就是com.web.servlet。但是这个对应的类的实例，是通过反射创建的，所以必须具有无参构造。

比如：http://localhost:8080/servlet1/Aservlet,就访问了该servlet


# servletConfig类简介

servletConfig这个类保存着其对应的web.xml的信息，比如servlet-name等等，web.xml加载过后就保存到servletConfig对象中。对应的API：
- String getServletName():获取<servlet-name>中的内容
- ServletContext getServletContext():获取Servlet上下文对象！
- String getInitParameter(String name):通过名称获取指定初始化参数的值
- Enumeration getInitParameterNames()：获取所有初始化参数的名称

使用示例：

web.xml:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">
  <display-name>servlet1</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
  
  <servlet>
  	<servlet-name>xxx</servlet-name>
  	<servlet-class>com.web.servlet.Aservlet</servlet-class>
  	<init-param>
  		<param-name>p1</param-name>
  		<param-value>v1</param-value>
  	</init-param>
  	<init-param>
  		<param-name>p2</param-name>
  		<param-value>v2</param-value>
  	</init-param>
  </servlet>
  
  <servlet-mapping>
  	<servlet-name>xxx</servlet-name>
  	<url-pattern>/Aservlet</url-pattern>
  </servlet-mapping>
</web-app>
```

init函数：

```java
	/*
	 * 他是生命周期方法
	 * 他会在Servlet对象创建之后马上执行，并且只执行一次
	 */
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init...");
		System.out.println(servletConfig.getInitParameter("p1"));
		System.out.println(servletConfig.getInitParameter("p2"));
		
		/*
		 * 获取所有初始化参数的名称
		 */
		Enumeration e = servletConfig.getInitParameterNames();
		while(e.hasMoreElements()){
			System.out.println(e.nextElement());
		}
	}
```

# GenericServlet

Generic是Servlet的接口实现类，我么可以通过继承GenericServlet来编写自己的Servlet。

举例：

```java
/**
 * @author pangzihao
 * CServlet
 */
public class CServlet extends GenericServlet{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		//super.destroy();可以不要这一步，父类已经写好了
		
		//做一些其他的操作
		System.out.println("销毁。。。");
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		//super.init();可以不要这一步，父类已经写好了
		
		//做一些其他的操作
		System.out.println("初始化");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("hello world!");
	}
	
	
}
```

# httpServlet

HttpServlet结构介绍

![15.httpServlet结构介绍](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/15.httpServlet%E7%BB%93%E6%9E%84%E4%BB%8B%E7%BB%8D.JPG)

HttpServlet工作流程简介：

![16.HttpServlet工作流程](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/16.HttpServlet%E5%B7%A5%E4%BD%9C%E6%B5%81%E7%A8%8B.JPG)

示例代码：

```java
/**
 * @author pangzihao
 * DServlet
 */
public class DServlet extends HttpServlet{
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost()...");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet()...");
	}
}
```

可以创建一个login.html测试post函数：http://localhost:8080/login.html
访问：http://localhost:8080/servlet1/DServlet来测试get方法

# servlet细节

1.Servlet与线程安全
因为一个类型的Servlet只有一个实例对象，那么就有可能出现一个Servlet同时处理多个请求，那么Servlet是否为线程安全的呢？并不是，但也提高了工作效率。
所以我们**不应该在Servlet中创建成员**变量，因为不同步的问题可能会导致该成员的属性不安全。

总结如下
- 不要在Servlet中创建成员！创建局部不良即可
- 可以创建无状态成员！就是这个成员的属性不会变化，没有可以变化的东西
- 可以创建有状态的成员，但状态必须为只读的

2.让服务器启动的时候就创建Servlet，而不是首次访问时候启动

默认情况下，服务器会在某个Servlet第一次收到请求的时候创建它。也可以在web.xml中对Servlet中进行配置，使服务器启动时候就创建Servlet,即配置：`<load-on-startup>1</load-on-startup>`，配置一个非负数即可，可以为0，1，2，3...即启动顺序排序

3.<url-pattern>的写法

<url-pattern>是<servlet-mapping>的子元素，用来指定Servlet的访问路径，即URL。他必须以"/"开头!

可以在<servlet-mapping>中给出多个<url-pattern>，例如：

```xml
<servlet-mapping>
	<servlet-name>Aservlet</servlet-name>
    <url-pattern>/Aservlet</url-pattern>
    <url-pattern>/Bservlet</url-pattern>
</servlet-mapping>
```

那么就说明这个Servlet绑定了两个URL，无论访问/Aservlet还是/Bservlet，访问的都是Aservlet

还可以在<url-patern>中使用通配符，所谓通配符就是"*"号，星号可以匹配任何URL额前缀和后缀使用通配符可以命名一个Servlet绑定一组URL，例如：
- /servlet/*,/servlet/a,/servlet/b都匹配/servlet/*
- /abc/def/efg.do,/a.do都匹配*.do
- /*:匹配所有的URL

请注意，通配符要么为前缀，要么为后缀，不能出现URL中间位置。

# ServletContext

一个项目稚嫩有一个Servlet对象
我们可以在N多个Servlet中来获取这个唯一对象
这个对象在Tomcat启动时候就创建，在Tomcat关闭时候才死去

1.ServletContext概述

服务器会为每个应用创建一个ServletContext对象：

- ServletContext对象的创建是在服务器启动时候完成的
- ServletContext对象的销毁是在服务器关闭的时候完成的

ServletContext对象的作用是在整个Web应用的动态资源之中共享数据！例如AServlet在ServletContext对象中保存一个值，然后在BServlet中获取这个值，这就是共享数据了。

2.获取ServletContext

在Servlet中获取ServletContext对象：
- 在`void init(ServletConfig config)`中：ServletContext context = config.getServletContext();ServletConfig类的getServletContext()方法可以用来获取ServletContext()对象

在GenericServlet或者HttpServlet中获取ServletContext对象：
- GenericServlet类有getServletContext()方法，所以可以直接使用this.getServletContext()来获取

3.域对象的功能

域对象就是用来在多个Servlet中传递数据的，所欲域对象必须具备存取数据的能力

ServletContext是JavaWeb四大域对象之一：
- PagaContext
- ServletRequest
- HttpServlet
- ServletContext

所有的域对象都具备存取数据的功能，因为域对象内部有一个Map，用来存取数据，下面是ServletContext对象用来操作数据的方法：
- void setAttrbute(String name,Object value):用来存储一个对象,或者说增加一个域属性
- Objetct getAttribute(Stroing name):用来获取ServletContext中的数据，比如：String value = (String)servletContext.getAttribute("xxx");
- void removeAttribute(String name):用来移除ServletContext中的域属性
- Enumeration getAttributeNames():获取所有域属性的名称

举例：

创建Aservlet.java:

```java
public class Aservlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * 1.获取ServletContext对象
		 * 2.调用其setAttribute方法完成保存数据
		 */
		ServletContext application = this.getServletContext();
		application.setAttribute("name", "张三");
	}


}
```

创建Bservlet.java

```java
/**
 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
 */
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    ServletContext application = this.getServletContext();
    String name = (String)application.getAttribute("name");
    System.out.println(name);
}
```

先输入：http://localhost:8080/servlet2/Aservlet，在输入http://localhost:8080/servlet2/Bservlet
，观察现象

4.获取应用初始化参数

- servlet也可以获取初始化参数，但是他是局部的参数，只是自身这个servlet的参数
- 可以配置公共的初始化参数，为所有的servlet而用，这需要使用servletContext才能使用

5.获取真实路径

还可以使用ServletContext对象来获取Web应用下的资源，例如webContent(web应用根目录)目录下的login.html文件，现在想在Servlet中获取这个资源，就可以使用ServletContext来获取

- 获取login.text的真实路径：String realPath = servletContext.getRealPath("/login.html");
- 获取所有资源的真实路径：Set set = servletContext.getResourcePaths("/WEB_INF")


# 练习：访问量统计

一个项目中所有的资源被访问都需要对访问量进行累加。
创建一个int类型的变量，用来保存访问量，然后把它保存到ServletContext的域中，这样所有的Servlet都可以访问到。
- 最初的时候，servletContext中没有保存访问量相关的属性
- 当本站第一次被访问时，创建一个变量，设置其值为1，保存到ServletContext中
- 以后的访问时，就可以从ServletContext中访问这个变量，然后在其基础上加一。

具体步骤：
- 第一次访问：调用ServletContext的setAttribute传递一个属性，名为count,数值为1
- 第2~N次访问：调用ServletContext的getAttribute()方法获取原来的访问量，给访问量加1，再调用ServletContext的setAtrribute()方法完成设置

代码：

```java
/**
 * Servlet implementation class Aservlet
 */
public class Aservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * 1.获取ServletContext对象
		 * 2.从ServletContext对象中获取名为count属性
		 * 3.如果存在：给访问量加一，然后再保存回去
		 * 4.如果不存在，说明这是第一次，向ServlteContext中保存conut的属性，值为1
		 */
		ServletContext app = this.getServletContext();
		Integer count = (Integer)app.getAttribute("count");
		
		if(count == null){
			app.setAttribute("count", 1);
		}
		else{
			app.setAttribute("count", count+1);
		}
		
		/*
		 * 向浏览器输出
		 */
		PrintWriter pw = response.getWriter();
		pw.print("<h1>" + count + "<h1>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

```

# 获取类路径下面的资源

代码：

```java
/**
 * Servlet implementation class Aservlet
 */
public class Aservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * 1.得到ClassLoader
		 * 2.调用其getResourcesAsStream()，得到一个InputStream
		 */
		ClassLoader cl = this.getClass().getClassLoader();
		//该路径在src根目录上面
		InputStream input = cl.getResourceAsStream("a.txt");
		
        /*
		 * 如果使用：Class c = this.getClass();来获取类
		 * 如果该文件在src根目录下，那么这时候访问a.txt，就需要在前面加上一个斜杠
		 * 那么该路径，也就是a.txt的路径，就是该类的路径
		 */
		
		String s = IOUtils.toString(input);//读取输入流内容，转换为字符串
		System.out.println(s);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
```

IOUtils类的使用需要commons-io包

# BaseServlet

需求：
1.我们希望在一个Servlet中可以有多个请求处理的方法
2.客户端发送请求时，必须多给出一个参数，用来说明要调用的方法，比如：

http://localhost:8080/xxx/Aservlet?m=addUser

具体的结构如下：

domain:User
dao:UserDao
service:UserService
service:UserService

```

void init(ServletConfig config)
void destroy()
void service(ServletRequest,ServletResponse)
	throws IOException,ServletException{
    	/*
        * 在这里调用其他方法，要求：用户发出请求时候，必须给出一个参数，来说明调用哪个方		 * 法。
        */
        
    }
    
```

代码：

```java
public class Aservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * 1.获取参数，用来识别用户想要请求的方法 
     */
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String methodName = request.getParameter("method");
//		if(methodName.equals("addUser")){
//			addUser(request, response);
//		}else if(methodName.equals("deleteUser")){
//			editUser(request, response);
//		}else if(methodName.equals("deleteUser")){
//			addUser(request, response);
//		}
		
		/*
		 * 得到方法名称，是否可以通过反射来调用方法？
		 * 1.得到方法名，通过方法名在得到Method类的对象
		 *   * 需要得到Class，然后调用它的方法进行查询！再得到Method
		 *   * 我们要查询的是当前类的方法，所以我们需要得到当前类的class
		 */
		if(methodName == null || methodName.trim().isEmpty())
		{
			throw new RuntimeException("没有传递参数");
		}
		
		Class c = this.getClass();//得到当前类的class对象
		Method method = null;
		
		try {
			method = c.getMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("您调用的方法："+methodName+" 不存在！");
		}
		
		try {
			method.invoke(this, request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("addUser");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("deleteUser");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("editUser");;
	}
}
```

上面的代码使用反射调用了相应的方法，那么现在可以把反射调用的部分写成一个抽象类:BaseServlet，在想要某些方法的时候，直接继承BaseServlet即可。BaseServlet代码：

```java
/**
 * @author pangzihao
 * BaseServlet
 */
public abstract class BaseServlet extends HttpServlet {
    /**
     * 1.获取参数，用来识别用户想要请求的方法 
     */
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String methodName = request.getParameter("method");
//		if(methodName.equals("addUser")){
//			addUser(request, response);
//		}else if(methodName.equals("deleteUser")){
//			editUser(request, response);
//		}else if(methodName.equals("deleteUser")){
//			addUser(request, response);
//		}
		
		/*
		 * 得到方法名称，是否可以通过反射来调用方法？
		 * 1.得到方法名，通过方法名在得到Method类的对象
		 *   * 需要得到Class，然后调用它的方法进行查询！再得到Method
		 *   * 我们要查询的是当前类的方法，所以我们需要得到当前类的class
		 */
		if(methodName == null || methodName.trim().isEmpty())
		{
			throw new RuntimeException("没有传递参数");
		}
		
		Class c = this.getClass();//得到当前类的class对象
		Method method = null;
		
		try {
			method = c.getMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("您调用的方法："+methodName+" 不存在！");
		}
		
		try {
			method.invoke(this, request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
```

使用BaseServlet进行一些重定向的问题：

Cservlet.java:

```java
/**
 * Servlet implementation class Cservlet
 * 
 * 该Servlet的功能为处理一些重定向的问题
 */
public class Cservlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected String fun1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return "index.jsp";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected String fun2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return "r:/index.jsp";
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void fun3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

```

BaseServlet.java完整版本：

```java
/**
 * @author pangzihao
 * BaseServlet
 */
public abstract class BaseServlet extends HttpServlet {
    /**
     * 1.获取参数，用来识别用户想要请求的方法 
     */
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String methodName = request.getParameter("method");
		
		/*
		 * 得到方法名称，是否可以通过反射来调用方法？
		 * 1.得到方法名，通过方法名在得到Method类的对象
		 *   * 需要得到Class，然后调用它的方法进行查询！再得到Method
		 *   * 我们要查询的是当前类的方法，所以我们需要得到当前类的class
		 */
		if(methodName == null || methodName.trim().isEmpty())
		{
			throw new RuntimeException("没有传递参数");
		}
		
		Class c = this.getClass();//得到当前类的class对象
		Method method = null;
		
		try {
			String result = (String)method.invoke(this, response,request);
			
			/*
			 * 获取请求返回后的字符串，它表示转发或者重定向的路径，帮它完成重定向
			 */
			
			/*
			 * 如果用户返回的是字符串为null，或者为"",那么什么也不做
			 */
			if(result == null || result.trim().isEmpty())
				return;
			
			/*
			 * 查看返回的字符串中是否包含冒号，如果没有，表示转发
			 * 如果有，使用冒号分隔字符串，得到前缀和后缀！
			 * 其中前缀如果是f，表示转发，如果是r表示重定向
			 */
			if(result.contains(":")){
				//使用冒号分隔字符串，得到前缀和后缀
				int index = result.indexOf(":");
				String s = result.substring(0,index);//截取前缀
				String path = result.substring(index+1);//截取后缀
				if(s.equalsIgnoreCase("r")){
					response.sendRedirect(request.getContextPath()+path);
				}else if(s.equalsIgnoreCase("f")){//表示转发
					request.getRequestDispatcher(path).forward(request,response);
				}else{
					throw new RuntimeException("没有你指定的操作！");
				}
			}else{//没有冒号，默认为转发
				request.getRequestDispatcher(result).forward(request,response);
			}
			
			
			method = c.getMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("您调用的方法："+methodName+" 不存在！");
		}
		
		try {
			method.invoke(this, request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
```
