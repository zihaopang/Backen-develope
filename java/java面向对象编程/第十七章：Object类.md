Object类的主要特点是可以解决参数的统一问题，也就是说Object类可以接收所有的数据类型。

# Object类的简介
在Java之中只有一个类是不存在继承关系的，那么这个就是Object，所有的类都是Object的子类。

### 范例：Object类接收所有的子类
```
public class Test{
	pulic static void main(String args[]){
    	Object obj = new Person();//向上转型
        if(obj instanceof Person){
        	Person per = (Person)obj;//向下转型
            System.out.println("Person对象执行完毕");
        }
    }
}
```
### 也可以接收数组
```
public class javaDemo{
	public static void main(String args[]){
    	Object obj = new int[]{1,2,3};//向上转型
        if(obj instanceof int[]){
        	int data[] = (int [])obj;//向下转型
            for(int temp:data){
            	System.out.print(...);
            }
        }
    }
}
```

# 获取对象信息：toString()
该方法可以获取一个对象的完整信息：public String toString()

### 范例：观察默认的toString()使用
```
public class JavaPerson{
	pblic static void main(String args[]){
    	Person per = new Person();
        System.out.println(per.toString());//只是获得编码而已
    }
}
```
在开发对象的类之中，可以自己覆写toString()方法

### 范例：覆写toString()
```
class Person{
	private String name;
    private int age;
    public Person(String name,int age){
    	this.name = name;
        this.age = age;
    }
    public String toString(){
    	return "姓名："+this.name+"、年龄："+this.age;
    }
}
```

# 对象比较：equals()
Object类之中另外一个比较重要的方法就是在于对象比较的处理。主要是比较两个对象的内容是否相同。
### 范例
对象比较方法：public boolean equals(Object obj)，可以接受所有类型，默认情况下只是比较两个对象的地址是否相同，需要在子类中覆写该方法。
```
class Person{
	public boolean equals(Object obj){
        if(null == obj)
        	return false;
        if(!(obj instanceof Person))
        	return false;
            
    	Person per = (Person) obj;
        
        return this.name.equals(erp.name) && this.age == per.age;
        
    }
}
```
