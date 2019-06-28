# 一、回声客户端的完美实现
### 1.回声客户端没有问题，只有回声客户端有问题？
在服务器中的代码为：
```
while((str_len = read(clnt_sock,message,BUF_SIZE)) != 0)
	write(clnt_sock,message,str_len)
```
在客户端中的代码为：
```
write(sock,message,strken(message))
str_len = read(sock,message,BUF_SIZE-1)
```
那么问题在哪里？在客户端，就是发送时候write是一次性发送的，之后调用一次read函数，那么什么时候调用read函数呢？理想的情况下应该在收到字符串时候立即读取。
由于客户端接收到的数据可以知道其大小，比如之前传输了长20字节的字符串，那么在接收时候在连续调用20字节即可，核心代码：
```
str_len = write(sock,message,strlen(message))
recv_len = 0;
while(recv_len < str_len)
{
	recv_cnt = read(sock,&message[recv_len],BUF_SIZE-1);
    if(recv_cnt == -1)
    	errro...
    recv_len += rec_cnt;
}
```
### 2.如果问题不在于客户端：定义应用层协议
回声客户端可以知道数据长度，但我们知道，在一般的客户端中，这不太可能，这是就需要定义应用层的协议。
比如下面这个小程序：向服务器传递3，5，9的同时请求加法运算，那么客户端收到3+5+9的运算结果，乘法同理。

# 二、TCP原理
### 1.TCP中的套接字中的IO缓冲
TCP是收发无边界的，服务器端即使调用一次write函数传递40个字节，客户端也有可能通过4次read函数调用每次读取是个字节。客户端通过缓存来分批接收这四个字节，反向同理，如下图所示：
</br>![TCP套接字的缓冲](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/TCP%E5%A5%97%E6%8E%A5%E5%AD%97%E7%9A%84%E7%BC%93%E5%86%B2.PNG)</br>
I/O缓冲的特性可整理如下：
- I/O缓冲在每个套接字中单独存在
- I/O缓冲在创建套接字时自动生成
- 即使关闭套接字也会继续传递**输出套接字**中的数据
- 关闭套接字将丢失输出缓冲的数据

如果，客户端输入缓冲为50字节，二服务器端传输了100字节怎么办？答：不会发生，因为TCP会控制数据流

#### 2.TCP内部控制原理1：与对方套接字的连接
主要分三步：
- 与对方套接字建立连接
- 与对方套接字进行数据交换
- 断开与对方套接字连接

这三次对对话，又称为三次握手协议（Three-way handshaking）,过程如图5-3所示：
</br>![TCP套接字的连接设置过程](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/TCP%E5%A5%97%E6%8E%A5%E5%AD%97%E7%9A%84%E8%BF%9E%E6%8E%A5%E8%BF%87%E7%A8%8B.PNG)</br>
- 首先[SYN] SEQ:1000,ACK:-，SYN为Synchronization的缩写，表示是同步消息，SEQ为1000的含义传递如下：现传递的数据包序号为1000，如果接收无误，请通知我传递1001号数据包
- [SYN+ACK]SEQ:2000,ACK:1001:现传递的数据包为2000，并且1000数据包无误，请传递1001号数据包
- [ACK]:SEQ:1001,ACK:2001:现传递1001号数据包，2000号报接收成功，可以传输2001号的数据包。

至此，A和B主机确认了彼此均就绪

### 3.TCP内部的工作原理2：与对方主机的数据交换
</br>![TCP套接字的数据交换过程](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/TCP%E5%A5%97%E6%8E%A5%E5%AD%97%E7%9A%84%E6%95%B0%E6%8D%AE%E4%BA%A4%E6%8D%A2%E8%BF%87%E7%A8%8B.PNG)</br>
上图给出了主机A分两次向B主机传递200字节的过程，首先，主机A通过1个数据包发送100个字节的数据，数据包的SEQ为1200，主机B为了确认这一点，向主机A发送ACK1301的消息。
ACK号= SEQ号+传递的字节数+1

### 4.TCP的内部工作原理3：断开与套接字的连接
</br>![TCP套接字断开连接过程](https://github.com/zihaopang/Backen-develope/blob/master/pics/tcpipCoding_pics/TCP%E5%A5%97%E6%8E%A5%E5%AD%97%E7%9A%84%E6%96%AD%E5%BC%80%E8%BF%87%E7%A8%8B.PNG)</br>
途中向主机A传递了两次ACK 5001，其实是因为第一次ACK5001，主机A没回复造成的。zhujiB又重传了一次。
