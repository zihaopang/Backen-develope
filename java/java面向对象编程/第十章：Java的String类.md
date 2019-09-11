String 类是开发系统中一个至关重要的组成类。

# String 类简介
String类是java利用JVM的支持，制造了一种String类。
### 范例：String类的对象实例化
`String str = "www.baidu.com"`
需要注意的是，String这个类里面之所以可以保存字符串的主要原因是其中定义了一个数组，也就是说在String里面所有字符串中的每一个字符的数据，都是保存在了数组之中。
从JDK1.9开始String类之中的数组类型采用了byte类型，1.8以前的数组是char类型。同时应该清楚，既然包装的是数组，所以字符串当中的内容是不可以改变的。
在String类里面，除了可以使用直接赋值的方法，也可以使用对象实例化：
`String str = new String("www.baidu.com")`

# 字符串比较
String也可以使用"=="判断，只不过判断**不准确**而已。
### 范例
```
public class String Demo{
	public static void main(String args[]){
    	String strA = "mldn";
        String strB = new String("mldn");
        System.out.println(strA == strB);// false
    }
}
```
此时的比较并没有成功，要想实现准确的相等判断，可以使用String类提供的equals方法。
`strA.equals(StrB)`
### String中"=="与euqals()的区别
- "=="：进行的是数值比较
- equals()：是类提供的一个比较方法，可以直接进行字符串内容额判断


# 字符串常量是String类的匿名对象
程序之中不会提供有字符串这样的基本数据类型，那么提供的只是一个String类，所以，任何一个字符串常量都是String类的匿名对象
比如：`String str = "mldn"`
![19.字符串常量](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/19.%E5%AD%97%E7%AC%A6%E4%B8%B2%E5%B8%B8%E9%87%8F.JPG)

所以直接赋值的描述就是：将一个匿名对象设置一个具体的引用。
建议：将字符串续航两写在前面：
`"mldn".equals(input)`,因为equals可以判断输入是否为空，不会产生NullPointer.

# String类两种实例化方式的区别
1、分析直接赋值的方式：
`String str = "mldn"`,这种情况下只会开辟一块堆内存空间，此时的内存关系图如下：
![19.字符串常量](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/19.%E5%AD%97%E7%AC%A6%E4%B8%B2%E5%B8%B8%E9%87%8F.JPG)

也可以实现String赋值时候的数据共享：
```
String strA = "mldn";
String strB = "mldn";
System.out.println(strA == strB);//地址判断,true
```
结果是true，这就说明这两个对象所指向的堆内存是同一个。内存关系如图所示：
![20.共享数据内存](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/20.%E5%86%85%E5%AD%98%E5%85%B1%E4%BA%AB.JPG)

这说明每一次创建新的字符串的时候，都会从字符串池当中检查有没有该字符串。如果字符串池当中没有没有需要的数据，则新建数据

![21.字符串池](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/21.%E5%AD%97%E7%AC%A6%E4%B8%B2%E6%B1%A0.JPG)

### 分析构造方法的实例化
`String str = new String("mldn");`
![22.String关键字new](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/22.String%E5%85%B3%E9%94%AE%E5%AD%97new.JPG)

如上图，又开辟了一个"mldn"的内存数组，这时原来的str指向新开辟的"mldn"，原来的变成垃圾，所以，可以看出，直接赋值比new更节省空间，因为他会有字符串池，而new是在堆内存中重新申请空间。
同时，用new构造字符串对象的时候，该字符串不会自动保存到字符串池中。实质上来讲，用new构造字符串，就是构造一个自己专用的空间，但是也可以手工入池，该方法叫做：public String intern();

### 范例：观察手工入池
```
public class Test{
	public static void mian(String args[]){
    	String strA = "mldn";
        String strB = new String("mldn").intern();
        System.out.println(strA == strB); //true，已经入池
    }
}
```

# String对象（常量）池
对象池的主要目的是实现数据的共享处理。以String对象池为例，里面的内面的内容就是为了重用。
但是在Java之中对象（常量）池实际上可以分为两种：
- 静态常量池：指的是程序(*.class)在加载的时候会自动将此程序之中保存的字符串、普通常量、类和方法的信息，全部进行分配；
- 运行时的常量池：当一个程序加载之后，里面可能有一些变量，回放到运行常量池。

### 举例
```
public class Test{
	public static void main(String args[]){
    	String atrA = "www.baidu.com";
        String strB = "www."+"baidu"+".com";
        System.out.println(StrA == strB);	//true
    }
}
```
本程序之中所给出的内容全部都是常量数据，在程序加载的时候会对字符串进行连接，最终指向的是一个堆内存。如图：

![23.String常量池](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/23.String%E5%B8%B8%E9%87%8F%E6%B1%A0.JPG)
### 举例
```
public class Test{
	public static void main(String args[]){
    	String info = "baidu";
    	String atrA = "www.baidu.com";
        String strB = "www."+ info +".com";
        System.out.println(StrA == strB);	//false
    }
}
```
false的原因是程序在加载的时候，并不确定info是什么内容。info是一个变量，所以他不认为info的数值是确定的，info被放置到运行时的常量池。

# 字符串内容不可修改
在String类之中包含的是一个数组，数组最大的缺点在于长度不可改变。当设置了字符串之后，就会开辟一个字符数组。

### 范例
```
public class Test{
	public static void mian(String args[]){
    	String str = "www.";
        str += "baidu";
        str += ".com";
    }
}
```
分析一下本程序所进行的内存处理操作：

![24.String内容不可修改](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/24.String%E5%86%85%E5%AE%B9%E4%B8%8D%E5%8F%AF%E4%BF%AE%E6%94%B9.JPG)
可以看出，改变的并不是字符串，而是不断地放弃原有空间，让其变为垃圾，而后再指向新的空间。

# Java中的主方法
主方法：`public void static void main(String args[])`
- public:描述的是一种访问权限
- static：静态方法由类直接调用
- void：返回值为空
- main:主方法
- String args[]:字符串数组，接收程序启动参数,
在程序执行的时候，可以进行参数设置，用空格分割。参数本身包含空格，直接加双引号。

