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

![14.spring处理流程](https://github.com/zihaopang/Backen-develope/blob/master/pics/ssm/14.servlet%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D.JPG)

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

