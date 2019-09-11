static是一个关键字，这个关键字主要可以用来定义属性和方法
# 一、static定义属性
在一个类中，所有的属性一旦定义了实际上内容都交由各自的堆内存空间所保存

### 范例
```
class Person{
	private String name;
    private int age;
    String country = "中华冥国";
    public Person(String name,int age){
    	this.name = name;
        this.age = age;
    }
    public String getInfo(){
    	return "姓名："+this.name+"、年龄："+this.age;
    }
}
public class JavaDemo{
	public static void main(String args[]){
    	Person perA = new Perosn("张三",10);
        Person perB = new Perosn("李四",10);
        Person perB = new Perosn("王五",11);
        System.out.println(perA.getInfo());
        System.out.println(perB.getInfo());
        System.out.println(perC.getInfo());
    }
}
```
### 内存分析
![static分析](http://)
如果此时需要改变国家，并且产生了5000W个对象，那么此时将是一场噩梦。
因为这5000W个对象都需要逐个修改国家属性，所以在整体设计上就出现了问题。
那么应该将conutry修改为公共属性:`static String country="中华冥国"`
此时的内存关系图如下（全局数据区）
![static分析2](http://)
对于ststic属性的访问需要注意一点：由于其本身是一个公共的属性，虽然可以通过对象访问，但是最好的做法还是通过所有对象的最高代表（类）来进行访问，所以static属性可以由类名称直接调用，比如：`Person.country`
所以属性可以在没有被对象实例化的时候使用，需要存储公共信息的时候才需要static

# static定义方法
static也可以进行方法的定义，static方法的主要特点在于，其可以在没有实例化对象的情况下进行调用。
### 范例：定义static方法
```
public static setCountry(String c)
{
	country = c;
}
```
这个时候对于程序而言，方法有两种：static方法与非static方法
- static方法只允许调用static属性和static方法
- 非static方法允许调用static属性和static方法
- static中不允许使用this

因为main函数一般都是static，所以在main中直接调用的方法一定是static的

# static应用
为了加强理解，下面举例说明：
### 实例1
可以实现实例化对象个数的统计，每一次创建新的实例化对象便进行统计
- 此时可以单独船舰一个static属性，因为所有对象共享同一个static属性，那么在构造方法中可以实现统计处理
```
class Book{
	private String title;
    private static int count = 0;
    public Book(String title){
    	count
    	this.title = title;
        System.out.println("第"+count+"本新的图书创建完成");
    }
}
public class JavaDemo{
	public static void main(String args[]){
    	new Book("Java");
        new Book("C++");
    }
}
```
