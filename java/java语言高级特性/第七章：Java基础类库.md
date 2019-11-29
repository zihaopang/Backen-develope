# StringBuffer类
String类有一个最大的弊端，内容不可以修改。为了解决此问题，提供了StringBuffer类。
### 范例：观察String与StringBuffer对比
```
public class JavaDemo{
	public static void main(String[] args){
    	String str = "Hello";
        change(str);
        System.out.println(str);
    }
    
    public static void change(String temp){
    	temp += "World!";
    }
}
```
内容并没有发生改变
StringBuffer方法：
- 构造方法：public StringBuffer();
- 数据追加：public StringBuffer append(数据类型 变量)
```
public class JavaDemo{
	public static void main(String[] args){
    	StringBuffer str = new StringBuffer();
        change(str);
        System.out.println(str);
    }
    
    public static void change(StringBuffer temp){
    	temp.append("World!");
    }
}
```
String对象所有的"+"操作，其内部都是使用的StringBuffer，比如`String str = "www."+"baidu"+".cn"`，其编译过后其实换成了：
```
StringBuffer sb = new StringBuffer;
sb.append("www.").append("baidu").append(".com");
```
### StringBuffer的一些方法
1.StringBuffer类可以转换成String类：
- 可以依靠StringBuffer的构造方法
- 所有类的对象都可以通过toString()方法将其变为String类型

2.在StringBuffer类里面除了可以支持字符串内容修改之外，实际上也提供一些String类不具备的方法，比如：
- 插入数据：public STringBuffer insert(int offset,数据类型 b)
```
StringBuffer buf = new StringBuffer();
buf.append(".cn").insert(0,"baidu").insert(0,"www.");
```
3.删除指定范围的数据：`public StringBuffer delete(int start,int end)`
```
StringBuffer sb = new StringBuffer;
sb.append("www.baidu.com").delete(2,5);
```
4.字符串内容反转:`public StringBuffer reverse()`

### StringBuilder
与StringBuffer类相似的还有StringBuilder类，该类中提供的方法与StringBuffer功能相同。
最大的区别在于，StringBuffer类中的方法是线程安全的，全部使用synchorixed关键字进行标记。

### 解释String,StringBuffer,StringBuilder的区别
- String类是字符串的首选类型，其最大的他点是内容不允许修改
- StringBuffer与Stringbuilder类的内容允许修改
- StringBuffer是在JDK1.0的时候提供的，属于线程安全的操作，而StringBuilder是在JDK1.5之后提供，不属于线程安全操作。

# CharSequence接口
CharSequence是一个描述字符串结构的接口，在这个接口里面一般发现有三种常见子类，结构如下：

![47.charSequence分析](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/47.charSequence%E5%88%86%E6%9E%90.JPG)

即StringBuffer与StringBuilder不仅可以实现CharSequence接口，也可以接收CharSequence作为参数。
charSequence本身是一个接口，在该接口中也有定义如下操作方法;
- 获取指定索引字符：public char charAt(int index)
- 获取字符串长度：public int length();
- 获取子序列：public CharSequence subSequence(int start,int end);

### 范例
```
public class JavaDemo{
	public static void main(String[] args){
    	CahrSequence str = "www.baidu.com";
        CahrSequence sub = str.subSequence(4,8);
        System.out.println(sub);
    }
}
```

# AutoCloseable接口
AutoCloseable主要是用于日后进行资源开发的处理上，以实现资源的自动关闭（释放），例如，在进行文件，网络，数据库开发的时候。由于服务器的资源有限，所以使用之后一定要关闭资源，这样才会被其他使用者使用。

AutoCloseable接口只提供的一个方法，关闭方法：public void close() throws Exception;

要想实现自动关闭处理，除了要使用AutoCloseable之外，还需要结合异常语句进行处理

### 举例：手工实现资源处理
```
import java.util.*;

interface IMessage extends AutoCloseable{
	public void send();
}

class Message implements IMessage{
	private String msg;
	
	public Message(String msg) {
		// TODO Auto-generated constructor stub
		this.msg = msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		System.out.println("关闭资源");
	}
	public void send(){
		System.out.println("发送资源 "+msg);
	}
	
}

public class Test{
	public static void main(String args[]){
		try(IMessage msg = new Message("hello")) {	//一定要这样写
			msg.send();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
```

# Runtime类
Runtime维护的是运行时候的相关信息，在整个JVM中，只有Runtime描述JVM的运行信息。
Runtime类采用了单利模式，因为JVM运行时候，不可能有多个。
由于Runtime类属于单例设计模式，如果想要获取实例化对象，那么就可以依靠类中的getRuntime()方法完成：
- 获取实例化对象：public static Runtime getRuntime();

范例：获取Runtime对象
有几个重要的方法：
- 获取最大可用内存空间：public long maxMemory();
- 获取全部内存空间：public long totalMemory();
- 获取空用内存空间：public long freeMemory();
- 手工进行GC处理：public void gc();
```
public class Test{
	public static void main(String args[]){
    	Runtime run = Runtime.getRuntime();//获取实例化对象
        System.out.println(rt.availableProcessors());//获取机器核心数
        System.out.println(rt.totalMemory());//获取最大内存空间
    }
}
```
### GC是什么？
- GC是垃圾收集器，由系统自动调用

# System类
- 数组拷贝:`public static void arraycopy(Object src,int srcPos,Object dest)`
- 获取当前日期的时间数值:
`public static long currentTimeMillis();`
- 进行垃圾回收：public static void gc();

### 范例：操作耗时的统计
```
public class Test{
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for(int i = 0; i < 300000; i ++){
			int k = 0;
		}
		long end = System.currentTimeMillis();
		
		System.out.println((end-start));
	}
}
```

# Cleaner类(不太懂)
Cleaner是在JDK1.9之后提供的一个对象清理操作，其主要功能是进行finialize()方法的替代。
```

class Member implements Runnable{
	public Member(){
		System.out.println("这是构造方法......");
	}

	@Override
	public void run() {//清除的时候执行的操作
		// TODO Auto-generated method stub
		System.out.println("被清除的提示");
	}

	
}

class MemberCleaning implements AutoCloseable{
	private static final Cleaner cleaner = Cleaner.create();
	private Member member;
	private Cleaner.Cleanable cleanabe;
	public MemberCleaning(){
		this.member = new Member();
		this.cleanabe = this.cleaner.register(this,this.member);//注册使用的对象
	}
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		this.cleanabe.clean();//启动多线程
	}
	
}

public class Test{
	public static void main(String[] args) {
		try(MemberCleaning mc = new MemberCleaning()){
			//执行相关代码
		}catch(Exception e){}
	}
}
```

# 对象克隆
```
package c6p1;

class Member implements Cloneable{
	private String name;
	private int age;
	public Member(String name,int age){
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name = "+this.name+",age = "+this.age;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();//调用父类的clone方法
	}
}


public class Test{
	public static void main(String[] args) throws CloneNotSupportedException {
		Member memberA = new Member("小明", 30);
		Member memberB = (Member)memberA.clone();
		System.out.println(memberA);
		System.out.println(memberB);
		
	}
}
```

