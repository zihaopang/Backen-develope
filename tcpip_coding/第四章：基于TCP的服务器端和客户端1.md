# 一、理解TCP和UDP
TCPIP协议栈（Satck，层）：
</br>![TCP/IP协议栈](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/TCP_IP%E5%8D%8F%E8%AE%AE%E6%A0%88.PNG)</br>
通过TCP收发数据时要借助下面四层：
</br>![TCP协议栈](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/TCP%E5%8D%8F%E8%AE%AE%E6%A0%88.PNG)</br>
通过UDP收发数据时要借助下面四层：
</br>![UDP协议栈](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/UDP%E5%8D%8F%E8%AE%AE%E6%A0%88.PNG)</br>
关于详细的理解，见自顶向下

# 二、实现基于TCP的服务器/客户端
### 1.TCP服务器客户端的默认函数调用顺序
如图：
</br>![TCP服务器端调用顺序](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/TCP%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%AB%AF%E8%B0%83%E7%94%A8%E9%A1%BA%E5%BA%8F.PNG)</br>
### 2.进入等待连接请求状态
上一章已经调用bind函数为套接字分配了IP地址和端口号，接下来就要通过listen函数进入等待连接请求。
`int listen(int sock,int backlog)`
- sock:希望等待连接的套接字
- backlog：连接请求的队列长度，即连接请求的最大个数

### 3.受理客户端连接请求
调用listen函数后，若有新的连接请求，则应当按序受理。之前的服务器套接字是用来监听的，accept返回一个新的套接字，连接到发起请求的客户端。
```
#include <sys/socket.h>
int accept(int sock,struct sockaddr* addr,socklen_t* addrlen);
```
- sock:服务器的套接字
- addr:客户端地址信息变量的值
- 第二个参数的结构体长度

### 4.TCP客户端的默认函数调用顺序
</br>![TCP客户端调用顺序](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/TCP%E5%AE%A2%E6%88%B7%E7%AB%AF%E8%B0%83%E7%94%A8%E9%A1%BA%E5%BA%8F.PNG)</br>
与服务器端相比，客户端的区别在于请求连接，即为：
```
#include <sys/socket.h>
int connect(int sock,struct sockaddr* servaddr,socklen_t addrlen);
```
- sock:客户端套接字
- servaddr:保存服务器地址信息的变量值
- addrlen:第二个结构体的长度

### 5.基于TCP的服务器/客户端的函数调用关系
</br>![函数调用关系](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/%E5%87%BD%E6%95%B0%E8%B0%83%E7%94%A8%E5%85%B3%E7%B3%BB.PNG)</br>

# 实现迭代服务器端/客户端
之前讨论的Hello world服务器处理完一个客户端连接请求立即退出，没有意义。如果想要继续受理后续的客户端连接请求，要插入循环语句。
</br>![迭代服务器函数调用顺序](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/%E8%BF%AD%E4%BB%A3%E6%9C%8D%E5%8A%A1%E5%99%A8%E5%87%BD%E6%95%B0%E8%B0%83%E7%94%A8%E9%A1%BA%E5%BA%8F.PNG)</br>
代码见0622

### 回声客户端存在的问题
由于TCP不存在数据边界，一次调用write可能不够，但是多次调用可能一次write就写完了。这在第三章讨论这个问题。
