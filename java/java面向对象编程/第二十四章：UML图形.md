UML是统一的建模语言，本质就是利用图形化的形式来实现程序类关系的描述。

# 类图描述
一般情况下要进行类结构的描述，往往可以使用三层结构来表示：

|   类名称   | 
|-----------|
|    属性    |
|	 方法	   |

如果该类是抽象类，那么往往在类名称上加一个"abstract"。
属性格式："访问权限 属性名称 : 属性类型"的格式来定义，基本访问权限的标识符：public使用'+'，protected使用'#'，private使用'-'。
方法格式："访问权限 方法名称 : 返回值"结构来描述，方法一般使用public 来声明

UML软件的具体操作可以看一下相关博客，但在实际的开发过程中，花费大量的精力实现UML图是没有意义的，实际一般是先写代码，然后用转换引擎转换。

# 时序图
时序图主要描述的是你的代码的执行流程，比如以下代码
```
interface IMessage{
	public void send();
}
class MessageImpl implements IMessage{
	public MessageImpl(){
    	this.connect();
    }
    public void send(){
    	System.out.println("hello send");
    }
    public void connect(){
    	System.out.println("连接...")
    }
}
class Factory{
	public static IMmessage getInstance(){
    	return new MessageImpl;
    }
}
public class Test{
	public static void main(String[] args){
    	IMessage msg = Factory.getInstance();
        msg.send();
    }
}
```
时序图如图所示：
![38.时序图](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/38.%E6%97%B6%E5%BA%8F%E5%9B%BE.JPG)

# 用例图
用例图描述的是程序的执行分配，例如：现在如果是系统管理员可能拥有系统初始化、系统备份、公告发布的功能、而公告管理员只是负责公告的管理，不应该负责系统的管理，所以就可以使用用例图加一描述说明。如下图
![39.用例图](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/39.%E7%94%A8%E4%BE%8B%E5%9B%BE.JPG)
