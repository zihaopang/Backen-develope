在java语言里面最大的特点是支持多线程的开发（也是为数不多支持多线程编程的语言），如果不了解，就会出现严重的技术缺陷。

# Thread类实现多线程
在Java之中实现多线程，那么就需要有一个专门的线程主体类进行线程的执行任务的定义，而这个主体类的定义是有要求的，必须实现特定的接口或者继承特定的父类才可以完成。
### 继承Thread类实现多线程
一个类只要继承了此类就表示这个类为线程的主题类，需要覆写Thread类的Run方法比如:
```
package thread;

class MyThread extends Thread{
	private String title;
	@Override
	public void run(){//线程的主体方法
		for(int i = 0; i < 10; i++){
			System.out.println("i："+i);
		}
	}
	public MyThread(String title){
		this.title = title;
	}
}

public class LearnThread {
	public static void main(String[] args){
    	new MyThread("线程A").start();
        new MyThread("线程B").start();
        new MyThread("线程C").start();
    }
}

```
但是启动线程需要调用start方法，而不是run方法。并且，每一个线程对象只允许start一次，多以调用start会产生异常。
Thread使用start执行的底层分析：
![40.Thread执行分析](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/40.Thread%E6%89%A7%E8%A1%8C%E5%88%86%E6%9E%90.JPG)
所哟一多线程启动只能用start方法。

# Runnable接口实现多线程
由于此时不在继承Thread父类了，那么此时的MyThread类中也不再支持start方法，此时可以使用构造方法`public Thread(Runnable target);`来实现start方法
比如：
```
package thread;

class MyThread implements Runnable{
	private String title;
	@Override
	public void run(){//线程的主体方法
		for(int i = 0; i < 10; i++){
			System.out.println("i："+i);
		}
	}
	public MyThread(String title){
		this.title = title;
	}
}

public class LearnThread {
	public static void main(String[] args){
		Thread threadA = new Thread(new MyThread("线程A"));
        Thread threadB = new Thread(new MyThread("线程B"));
        Thread threadC = new Thread(new MyThread("线程C"));
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
```
在以后的开发之中，对于多线程的实现，优先考虑Runnable接口实现，并且永恒都是通过Thread接口实现。

# Thread与Runnable关系
经过一系列的分析之后我们可以发现，在多线程的首先过程之中已经有了两种做法：Thread与Runnable接口。而Runnable是比较方便的，因为其可以避免单继承的局限，同时也可以更好的进行功能的扩充。在进行Threa启动多线程的时候点用的是start()方法，而后找到的是run()方法，但通过Thread实现Runnable接口对象的时候，那么该接口对象将被Thread类中的target属性所保存，在执行start时候就会调用run()方法，而这个run()方法去调用Runnable接口子类被覆写过的run()方法
![41.Thread与Runnable关系](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/41.Thread%E4%B8%8ERunnable%E5%85%B3%E7%B3%BB.JPG)
多线程开发框架：Thread负责线程，Runnable负责资源
![42.多线程开发](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/42.%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%BC%80%E5%8F%91.JPG)
### 范例：买票程序：实现多个进程的并发访问
```
class MyThread implements Runnable{
	private int ticket;
	@Override
	public void run(){//线程的主体方法
		for(int i = 0; i < 100; i++){
			System.out.println("票数："+ticket--);
		}
	}
	public MyThread(String title){
		this.title = title;
	}
}
public class LearnThread {
	public static void main(String[] args){
		Thread mt = new MyThread();
        new Thread(mt).start();//第一个线程启动
        new Thread(mt).start();//第二个线程启动
        new Thread(mt).start();//第三个线程启动
    }
}
```
![43.内存图](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/43.%E5%86%85%E5%AD%98%E5%9B%BE.JPG)

# Callable实现多线程
Runnable有一个缺点，就是线程执行完毕之后无法获取返回值，考虑Callable.
看一下Callable定义：
```
public interface Callable<V>{
	public V call() throws Exception;
}
```
Callable接口实际上是属于Executor框架中的功能类，Callable接口与Runnable接口的功能类似，但提供了比Runnable更加强大的功能。
1.Callable可以在任务结束的时候提供一个返回值，Runnable无法提供这个功能
2.Callable的call方法分可以抛出异常，而Runnable的run方法不能抛出异常

### 举例
```
package com.huange.thread.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Demo {

	
	
	/**
	 * Callalbe和Runnable的区别
	 * 
	 * Runnable run方法是被线程调用的，在run方法是异步执行的
	 * 
	 * Callable的call方法，不是异步执行的，是由Future的run方法调用的
	 * 
	 * 
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Callable<Integer> call = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				System.out.println("正在计算结果...");
				Thread.sleep(3000);
				return 22;
			}
		};

		FutureTask<Integer> task = new FutureTask<>(call);

		Thread thread = new Thread(task);
		thread.start();

		// do something
		System.out.println(" 干点别的...");

		Integer result = task.get();

		System.out.println("拿到的结果为：" + result);

	}

}


```

# 多线程运行状态
![44.多线程运行状态](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/44.%E5%A4%9A%E7%BA%BF%E7%A8%8B%E8%BF%90%E8%A1%8C%E7%8A%B6%E6%80%81.JPG)
