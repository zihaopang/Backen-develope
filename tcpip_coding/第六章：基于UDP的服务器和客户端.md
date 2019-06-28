# 一、理解UDP
### UDP套接字的特点
UDP和TCP的差别主要在于流控制机制，如果从TCP中抹去流控制，所剩内容屈指可数
### UDP内部工作原理
与TCP不同，UP不会进行流控制，从下图可以看出，IP的作用就是让离开主机B的UDP数据包准确的传递到主机A，而UDP的作用就是根据端口号将传到主机的数据包交付到UDP套接字。
### UDP的高效使用
TCP比UDP慢主要有两点：
- 收发数据前后进行的连接设置及其清除过程
- 收发数据过程中的流控制

如果收发数据量小但连接频繁时候，UDP比TCP更加高效

# 二、实现基于UDP的服务器端/客户端
### 1.UDP中的服务器端和客户端没有连接
UDP不不像TCP那样，在连接状态下交换数据，不用调用listen和accept函数。UDP只需要创建套接字和数据交换。
### 2.UDP的服务端和客户端均只需要一个套接字
TCP需要一个守门的套接字，如果有十个客户连接，就需要10个套接字的，但UDP只需要1个套接字，就可以和多台主机通信。
#### 基于UDP的数据的I/O函数
发送数据：
```
#include <sys/socket.h>
ssize_t sendto(int sock,void* buff,size_t nbytes,int flags,struct sockaddr* to,socklen_t addrlen);
```
- sock:用于传输数据的UDP套接字文件描述符
- buff:保存待传输数据的缓冲地址值
- nbytes：待传输数据的长度
- flags:可选项参数
- to：保存目标地址信息的sockaddr结构体变量地址数值
- addrlen:该结构体长度

接收数据：
```
#include <sys/socket.h>
ssize_t recvfrom(int sock,void* buff,size_t nbytes,int flags,struct sockaddr* from,socklen_t* addrlen);
```
- sock:用于接受信息的套接字
- buff：保存接收数据的缓冲地址值
- nbytes:可接受最大字节数
- flags：可选项参数，默认0
- from：发送端结构体地址
- addrlen:结构体大小

# 基于UDP的回声客户端
见0623

# UDP的传输特性和调用connect函数
### 存在数据边界的UDP套接字
UDP是存在数据边界的协议，传输中调用I/O函数的次数非常重要。因此，输入函数的调用次数应该和输出函数的调用次数完全一致。

### 已连接的UDP套接字和未连接的UDP套接字
TCP套接字在传输中需要提供目标IP和端口号，在UDP中无需注册。因此，通过sendto函数传输数据的过程大致可分为三步：
- 第一阶段：向UDP套接字注册IP和端口号
- 第二阶段：传输数据
- 第三阶段：删除UDP套接字中注册目标地址信息

每次调用sendto重复上述过程，每次均需要变更地址，这种方式称为未注册套接字。还有一种是注册套接字，比如重复发送三次，就可以用组测套接字
```
sock = socket(PF_INET,SOCK_DGRAM,0)
memset(&adr,0,sizeof(adr))
sdr.sin_family = AF_INET
adr.sin_addr.s_addr = .....
adr.sin_port = .....
connect(sock,(struct sockaddr*)&adr,sizeof(adr))
```
与TCP过程几乎一致，但sock创建的时候使用了SOCK_DGRAM
