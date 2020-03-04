# Java三大组件

1.都需要在web.xml中进行配置

- servlet
- Listener(2个感知监听器不需要配置)
- Filter

2.过滤器

他会在一组资源（jsp,servlet,.css.html等等）的前面执行！它可以让请求达到目标资源，也可以不让请求达到！

登陆：
允许他访问AServlet,BServlet,CServelt

过滤器如何编写：
- 写一个类实现Filter接口
- 在web.xml中进行配置

Filter接口的相关函数：

void init(FilterConfig)：创建之后，马上执行

```
	/*
	 * 每次过滤时都会执行
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("过滤器初始化");
	}
```

void destory()：销毁之前执行！在服务器关闭时候销毁

```
	/*
	 * 销毁之前执行，用来做对非内存资源进行释放
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("过滤器被销毁");
	}
```

void doFilter(ServletRequest,ServletResponse,FilterChain):每次过滤时候都会执行

```
	/*
	 * 创建之后马上执行，用来做初始化
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("Afilter start");
		chain.doFilter(request, response);
		System.out.println("Afilter end");
	}
```

Filter是单例的

过滤器web.xml配置：

```
  <filter>
  	<filter-name>Afilter</filter-name>
  	<filter-class>com.web.filter.Afilter</filter-class>
  </filter>
  
  <filter-mapping>
 	<filter-name>Afilter</filter-name>
 	<url-pattern>/*</url-pattern>//拦截所有，如果是/Aservlet只拦截Servlet
  </filter-mapping>
```

FilterConfig与ServletConfig相似

- 获取初始化参数：`getInitParameter()`
- 获取过滤器名称：`getFilterName()`
- 获取application：`getServletContext()`

FilterChain: `doFilter(ServletRequest,ServletResponse)`,放行


Afilter.java:

```
/**
 * @author pangzihao
 * Afilter
 */
public class Afilter implements Filter{

	/*
	 * 创建之后马上执行，用来做初始化
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("过滤器拦截");
	}
	/*
	 * 每次过滤时都会执行
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("过滤器初始化");
	}
	
	/*
	 * 销毁之前执行，用来做对非内存资源进行释放
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("过滤器被销毁");
	}
}

```

Filter的相关类：

1.FilterConfig:(与servletConfig相似)

- 获取初始化参数：getInitParameter()
- 获取过滤器名称：getFilterName()
- 获取application:getServletContext()

2.FilterChain:
- doFilter(ServletRequest,ServletResponse，FilterChain):放行

比如：

```
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("过滤器拦截");
		chain.doFilter(request, response);
	}
```

则可以放行


# 过滤器的四种拦截方式

1.拦截请求
2.拦截转发
3.拦截包含
4.拦截错误

```
  <filter-mapping>
    <filter-name>Bfilter</filter-name>
    <url-pattern>/Aservlet</url-pattern>
    <dispatcher>REQUEST</dispatcher>
    <dispatcher>FORWARD</dispatcher>
    <dispatcher>INCLUDE</dispatcher>
    <dispatcher>ERROR</dispatcher>
  </filter-mapping>
```

# 控制多个过滤器的执行顺序

在<filter-mapping>的配置顺序决定过滤器的执行顺序，把BFilter-mapping放置在AFilter-ampping的前面，那么就是先执行BFilter了

# 监听器

- 他是一个接口，内容由我们来实现
- 他组要注册，例如注册在按钮上
- 监听器中的方法，会在特殊时间发生时候被调用

# 观察者
- 事件源：小偷
- 事件：偷东西
- 监听器：警察，监听器中的方法：抓捕

# JavaWeb中的监听器

1.事件源：三大域！
- ServletContext：
	- 生命周期监听：ServletContextListener：他有两个方法，一个在出生时候调用，一个在死亡时候调用。
	1.void contextInitialized(ServletContextEvent sre):创建ServeltContextEvent;
	2.void contextDestroyed(ServletContextEvent sce):销毁ServletCobtextEvent
	- 属性监听：ServletContextAttributeListener:他有三个方法，一个在添加属性时候调用，一个在替换属性时候调用，最后一个是在移除属性时候调用
- HttpSession:
	- 生命周期监听：HttpSessionListener：他有两个方法，一个在出生时候调用，一个在死亡时候调用。
	1.void sessionCreated(HttpSessionEvent se):创建session时候
	2.void sessionDestroyed(HttpSessionEvent se)：销毁session时候
	- 属性监听：HttpSessionAttributeListener:他有三个方法，一个在添加属性时候调用，一个在替换属性时候调用，最后一个是在移除属性时候调用
- ServletRequest：
	- 生命周期监听：ServletRequestListener：他有两个方法，一个在出生时候调用，一个在死亡时候调用。
	1.void requestInitalized(SercletRequestEvent sre):创建request时候
	2.void requestDestroyed(ServletRequestEvent sre):销毁request
	- 属性监听：ServletRequestListener:他有三个方法，一个在添加属性时候调用，一个在替换属性时候调用，最后一个是在移除属性时候调用

2.javaWeb中完成编写监听器

- 写一个监听器类：要求必须去实现某个监听器接口
- 注册
