# 字符编码
计算机的二进制信息是通过编码才会呈现文字的，编码的时候，一定要对应相应的解码才可以实现
常用的编码”：
- GBK/GB2312:国标编码，可以描述中文信息，其中GB2312只描述简体中文,GBK包括繁体
- ISO88859-1：国际通用编码，可以描述所有的文字信息
- UNICODE编码：采用十六进制的方式存储，可以描述所有的文字信息
- UTF编码：象形文字使用十六进制编码，二普通的字母采用ISO8859-1的通用编码。可以快速传输，节省带宽，首选编码。
代码：
```
public class Test{
	public static void main(String[] args) throws ParseException, IOException  {
		//1.指定文件存在路径
		File file = new File("d:"+File.separator+"hello"+
				File.separator+"mldn.txt");
		
		OutputStream output = new FileOutputStream(file);
		output.write("www.baidu.com".getBytes("ISO8859-1"));
		output.close();
	}
}
```
乱码的最好解决方法，就是所有的程序都是用utf-8进行编码

# 内存操作流
之前使用的全都是文件操作流，文件操作流的特点，程序利用InputStream读取文件内容，所有的操作都是以文件为终端的。但如果现在需要IO操作，但是又不希望产生文件（临时文件），就可以以内存为终端进行处理。
在Java里面提供有两类的内存操作流：

- 字节内存操作流：ByteArrayOutputStream、ByteArrayInputStream
- 字符内存操作流：CharArrayWriter、CharArrayReader

ByteArrayInputStream列的主要方法：
- public ByteArrayInputStream(byte[] buf):将全部的内容写入内存之中
- public ByteArrayInputStream(byte[] buf,int offset,int length):将指定范围的内容写到内存之中。

ByteArrayOutputStream类的主要方法：
- public ByteArrayOutputStream():创建对象
- public void write(int b):将内容从内存输出

### 举例：使用内存操作流完成一个字符串大小写字母的转换操作
```
public class TestDemo {
    public static void main(String[] args) throws IOException{
        String str = "hello world.";
        InputStream input = new ByteArrayInputStream(str.getBytes());//传入内存，注意这里的构造
        OutputStream output = new ByteArrayOutputStream();//准备从内存中读取数据
        int temp = 0;
        while((temp=input.read())!=-1)
        {
            output.write((char)Character.toUpperCase(temp));//从内存输出
        }
        String newStr = output.toString();//取数据
        output.close();
        input.close();
        System.out.println(newStr);
    }
}

```

# 管道流
管道流主要是实现两个线程之间的IO处理操作
![55.管道流](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/55.%E7%AE%A1%E9%81%93%E6%B5%81.jpg)

对于管道流，也分为两类：

1.字节管道流：PipeOutputStream、PipedInputStream

	- 连接处理：public void connect(PipedInputStream snk) throws IOException
	
2.字符管道流：PipedWriter、PipedReader

	- 连接处理：public void connect(PipedReader snk) throws IOException

### 举例：管道流实现
```
public class Test{
	public static void main(String[] args) throws ParseException, IOException  {
		SendThread send = new SendThread();
		ReceiveThread receive = new ReceiveThread();
		send.getOutput().connect(receive.getInput());//管道连接
		new Thread(send,"消息发送线程").start();
		new Thread(receive,"消息接收线程").start();
	}
}

class SendThread implements Runnable{
	private PipedOutputStream pos;
	
	public SendThread() {
		// TODO Auto-generated constructor stub
		 pos= new PipedOutputStream();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.pos.write(("这是一条测试信息").getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.pos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PipedOutputStream getOutput(){
		return pos;
	}
}

class ReceiveThread implements Runnable{
	private PipedInputStream pis;
	
	public ReceiveThread() {
		// TODO Auto-generated constructor stub
		pis = new PipedInputStream();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte data[] = new byte[1024];
		try {
			int len = this.pis.read(data);
			
			System.out.println(new String(data,0,len));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.pis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PipedInputStream getInput(){
		return this.pis;
	}
}
```

# RandomAccessFile
传统的读取文件的方式就是InputStream和OutputStream或者reader和writer，这些只能一部分一部分的读取。那么现在如果有一个文件很大，就需要采用RandomAccessFile进行处理。但这个文件需要有一个完善的保存形式。完善的保存形式就是规整的保存，比如下图：
![56.RandomAccessFile](https://github.com/zihaopang/Backen-develope/blob/master/pics/Java/Java%E5%9F%BA%E7%A1%80/56.RandomAccessFile.jpg)
即使wangwu和lisi没有那么长，也要保存为空格

RandomAccessFile里面的操作方法：
1.构造方法：public randomAccessFile(File file,String mode) throws FileNotFoundException

	- 文件处理模式：r,rw,分别是读和读写

### 范例：实现文件的保存
```
public class Test{
	public static void main(String[] args) throws ParseException, IOException  {
		File file  =  new File("d:"+File.separator+"hello"+File.separator+"mldn.txt");
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		String names[] = new String[]{"zhangsan","wangwu  ","lisi    "};
		int ages[] = new int[]{30,20,16};
		for(int x=0;x<names.length;x++){
			raf.write(names[x].getBytes());
			raf.writeInt(ages[x]);
		}
		
		raf.close();
	}
```
RandomAccessFile最大的特点在于数据的读取处理上，可以进行跳字节读取`public int skipBytes(int n) throws IOException;`

### 范例：读取数据
```
public class Test{
	public static void main(String[] args) throws ParseException, IOException  {
		File file  =  new File("d:"+File.separator+"hello"+File.separator+"mldn.txt");
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		{
			//直接读取"李四"的数据，跳过24位
			raf.skipBytes(24);
			byte[] data = new byte[8];//读取姓名
			int len = raf.read(data);
			System.out.println("姓名："+new String(data,0,len)+"年龄："+raf.readInt());
		}
	}
}
```
