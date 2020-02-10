# Annotation简介：
Annotion是从JDK1.5之后提出的一个新的开发技术结构，利用Annotion可以有效减少程序配置的代码，并且可以利用Annotion进行结构化的定义。Annotion是一种以注解的形式实现的程序开发。
要想清除Annotation的产生意义，则必须了解一下程序开发结构的历史，从历史上来说程序开发一共分为了三个过程：
1.过程一：在程序定义的时候将所有可能使用到的资源全部定义在程序的代码之中
	- 如果此时的程序的相关信息发生了改变，那么对程序而言就需要进行源代码的修改，维护不方便，遮掩个做法不方便
![27.程序资源](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/27.%E7%A8%8B%E5%BA%8F%E8%B5%84%E6%BA%90.JPG)
2.过程二：引入配置文件，在配置文件中定义全部要使用的资源
	- 在配置项不多的情况下，此类配置非常好用，并且十分简单，但是如果所有的项目都是采用这种方式，那么配置文件将会很多。
	- 所有的操作都需要通过配置文件完成，开发的难度提升了
![28.配置文件](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/28.%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6.JPG)
3.过程三：将配置信息重新写回到程序里面，利用一些特殊的标记与程序的代码进行分离，这就是注解的作用，也就是Annotation提出的基本依据。
4.目前开发是配置文件加上Annotation共同开发。

有几个Java提供的几个基本注解：@Override、@Deprecated、@Supresswarning

# 准确的覆写：@Override
当子类继承父类的某一个方法发现功能不足的时候往往会采用覆写的方法，下面首先来观察一种覆写操作.
### 范例：
开发之中经常出现的两个问题：
- 虽然要明确的继承一个父类并且进行方法的覆写，但是有可能忘记写extends
- 在进行方法覆写的时候单词拼写错了
- 此时即使单词写错了，实际上程序在编译的时候也不会出现任何的错误信息。因为编译器认为这是一个新方法

为了避免这戏问题，可以在明确覆写的方法上追加一个注解
```
class Channel{
	public void connect{
    	System.out.println("Channel");
    }
}
class DatabaseChannel extends Channel{
	@Override //明确表示该方法是一个覆写来的方法，不正确会报错
    public void connection(){
    	System.out.println("子类定义的通道连接操作")
    }
}
```
# 过期操作：deprecated
在一个项目开发过程中，有些操作或者某个类，会和新版本有着不适应的地方，这个时候又不能直接删除这些操作，此时就可以采用过期声明，目的就是告诉这些用户不要再用这些操作，老的用户你用了就用了。

### 范例
```
class Channel{
	@Deprecated //老系统继续用，如果是新的就不要用了
    pblic void connect(){
    	System.out.println("Connect");
    }
    public String cinnection(){
    	return "获取连接信息...";
    }
}
```
# 压制警告：@SupressWarnings
如果不愿意看到一些提示信息或者已经知道了错误在哪里，就使用压制警告
### 范例
```
public class Test{
	@SupressWarnings({"deprecation"})
    public static void main(String args[]){	
    	......
    }
}
```
