# ClassLoader类加载器
CLASSPATH系统变量的作用：在JVM启动的时候进行类加载路径的定义，在JVM中可以根据类加载器而后进行指定路径中类的加载，找到了类的加载器就相当于找到了类的来源。

### 系统类的加载器
类的加载器需要通过ClassLoader来获得，要想获得ClassLoader对象，就要利用Class来实现，方法：`public ClassLoader getClassLoader()`
代码：

```
class Message {}

public class Test {
	
	public static void main(String[] args) throws Exception {
		Class<?> clazz = Message.class;
		System.out.println(clazz.getClassLoader());//获取当前类的加载器
		System.out.println(clazz.getClassLoader().getParent());//获取父类加载器
	}
}
```
当你获得了类加载器之后，就可以利用类加载器进行类的反射加载处理。

# 自定义类加载器
自定义的类加载器在所有系统的类加载器之后。自定义类加载器可以指定类的加载路径，而不是从CLASSPATH开始。比如从磁盘加载，从网络加载。
1.现在随便定义一个文件，并把它扔到磁盘某个位置：D:/Test.java
2.继承一个加载类，继承ClassLoader


```
public class TestClassLoader extends ClassLoader{
	private static final String CLASS_FILE_PATH = "D:"+File.separator+"Test.class";
	/*
	 * 进行指定类的加载
	 * className: 类的完整名称："包.类"
	 * return 返回一个指定类的Class对象
	 */
	public Class<?> loadData(String className) throws Exception{
		byte[] data = this.loadClassData();
		if(data != null){
			return super.defineClass(className,data, 0,data.length);
		}
		
		return null;
	}
	
	private byte[] loadClassData() throws Exception{
		InputStream input = null;
		ByteArrayOutputStream bos = null;
		byte data[] = null;
		try{
			input = new FileInputStream(CLASS_FILE_PATH);
			input.transferTo(bos);//无此方法
			data = bos.toByteArray();//将所有读取到的字节数据取出
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(input != null){
				input.close();
			}
			if(bos != null){
				bos.close();
			}
		}
		
		return data;
	}
}
```
则会中方式在以后 网络开发中，可以用远程的服务器实现一个类。
