# ajax简介

asynchronous javascript and xml:异步的js和xml


### 异步交互和同步交互

1.同步交互：



- 发送一个请求，就要等待服务器的相应结束，然后才能发第二个请求！

- 刷新的是整个页面！



2.异步交互



- 发一个请求后，无需等待服务器的响应，然后可以发第二个请求

- 可以使用js接收服务器的响应，然后使用js来局部刷新!

### Ajax四操作

一、Ajax发送异步请求（四步请求）
1.第一步（得到XMLHttpRequest）

- 大部分浏览器都支持：var xmlHttp = new XMLHttpRequest();
- IE6.0: var xmlHttp = new ActiveXObject();
- IE5.5以及更早的版本：var xmlHttp = new ActiveXObject();






