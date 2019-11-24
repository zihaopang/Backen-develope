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

Filter接口：

void init(FilterConfig)：创建之后，马上执行
void destory()：销毁之前执行！在服务器关闭时候销毁
void doFilter(ServletRequest,ServletResponse,FilterChain):每次过滤时候都会执行

Filter是单例的

过滤器web.xml配置：

```
  <filter>
  	<filter-name>Afilter</filter-name>
  	<filter-class>com.web.filter.Afilter</filter-class>
  </filter>
  
  <filter-mapping>
 	<filter-name>Afilter</filter-name>
 	<url-pattern>/*</url-pattern>
  </filter-mapping>
```

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

FilterChain:
- doFilter(ServletRequest,ServletResponse):放行

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