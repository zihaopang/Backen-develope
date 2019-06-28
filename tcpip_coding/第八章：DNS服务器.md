#域名系统
### 什么是域名
www.baidu.com
### DNS服务器
将域名转化为IP地址
# IP地址和域名之间的转换
### 程序中有必要使用域名吗
比如访问www.SuperOrange.com,该用户需要接入如下服务器地址：
IP 211.102.204.12，PORT 2012
但是由于ISP提供的服务，IP地址可能会随时变更，由于域名一旦注册永久不变，所以需要转换成域名。

### 利用域名获取IP地址
```
#include <netdb.h>

struct hostent* gethostbyname(const char* hostname);
```
hostent结构体：
```
struct hostent
{
   char* h_name;
   char** h_aliases;
   int h_addr_type;
   inth_length;
   char** h_addr_list;
}
```
- h_name:官方域名
- h_aliases:指定的其他域名
- h_addrtype:IPV4/IPV6
- h_length:IP地址的长度
- h_addr_list:以整数形式保存域名对应的IP地址

调用gethostbyname后返回的hostent的结构体如下：
