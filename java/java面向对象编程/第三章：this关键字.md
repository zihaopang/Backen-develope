# 一、this调用本类属性
this可以算是Java里面比较复杂的关键字，因为this的使用形式上决定了它的灵活性，三类结构的描述：
- 当前类的属性：this.属性
- 当前类中的方法（普通方法、构造方法）：this(),this.方法名称()
- 描述当前对象

### 使用this调用当前类中的属性
```
class Person{
	private String name;
    private int age;
    public Person(String name,int age){
    	this.name = name;
        this.age = age;
    }
}
```
如果不加this，会出现错误，为什么呢？
因为在Java程序之中"{}"是作为一个结构体的边界符号，那么在程序里面当进行变量使用的时候都会以"{}"作为查找边界，所以按照就近取用的原则，此时的构造方法并没有访问到类的属性。
所以在访问本类属性的时候，请一定要加上"this"实现访问

# 二、this调用本类方法
除了调用属性之外，this也可以实现方法的调用，但是对于方法的调用必须考虑构造否
- 构造方法的调用：（this()）：使用关键字new实例化对象的时候才会调用构造方法
- 普通方法调用（this.方法名称()）：实例化对象产生之后就可以调用普通方法。

### 调用类中的普通方法
```
class Person{
	private String name;
    private int age;
    public Person(String name,int age){
		this.setName(name);
        steAge(age);//加与不加都表示本类方法
    }
    public void setName(String name){
    	this.name = name;
    }
    public void setAge(int age){
    	this.age = age;
    }
}
```
### 调用类中的构造方法
比如
```
class Person{
	private String name;
    private int age;
    public Person(){
    	System.out.println("一个新的Person类对象实例化了");
    }
    public Person(String name){
    	System.out.println("一个新的Person类对象实例化了");
        this.name = name;
        this.age = age;
    }   
    public Person(String name,int age){
    	System.out.println("一个新的Person类对象实例化了");
        this.name = name;
        this.age = age;
    }
}
```
以上三种都是构造函数，但是有个问题，如果`System.out.println("一个新的Person类对象实例化了");`是多行语句，那么该程序就出现了重用代码，解决方法：
利用this()调用优化
```
    public Person(){
    	System.out.println("一个新的Person类对象实例化了");
    }
    public Person(String name){
    	this();//这里就是重复代码
        this.name = name;
        this.age = age;
    } 
    public Person(String name,int age){
		this(name);
        this.age = age;
    }
```
对于本类构造方法的互相调用需要注意以下几点问题：
- 构造方法必须在实例化新对象的时候调用，所以"this()"的语句必须放在代码块**首行**。
- 构造方法互相调用的时候，请保留有程序的出口

# 三、案例：简单java类
简单java类是一个重要的组成部分，所谓简单java类是可以描述一类信息的程序类，例如：描述一个人、描述一本书等等。
对于简单Java类而言，其核心的开发结构如下：
- 类名称一定要有意义
- 类之中所有的属性必须使用private进行封装，同时封装后的属性必须要提供setter,getter
- 类之中可以提供有无数多个构造方法，但必须提供无参构造方法
- 类之中不允许出现任何输出语句，所有获取的内容必须返回
- 【非必需】可以提供一个获取对象详细信息的方法

### 示例：
```
class Dept{
	private long deptno;
    private String dname;
    private String loc;
    
    public Dept(){}
    public Dept(long deptno,String dname,String loc){
    	this.deptno = deptno;
        this,dname = dname;
        this.loc = loc;
    }
    public String getInfo(){
    	return "完整信息";
    }
    public void setDeptno(long deptno){
    	this.deptno = deptno;
    }
    public void setDname(String dname){
    	this.name = dname;
    }
}

public class JavaDemo{
	public static void main(String args[]){
    	Dept dept = new Dept(10,"技术部","南京")
    }
}
```