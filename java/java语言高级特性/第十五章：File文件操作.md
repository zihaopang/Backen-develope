# File基本操作
- 构造方法：public File(String pathname),设置要操作的完整路径
- 构造方法：public File(File parent String child),这只父路径与子目录
- 创建新文件：public boolean createNewFile() throws IOException
- 判断文件是否存在：public boolean exists();
- 删除文件：public boolean delete();


### 范例：使用File类创建文件

```
public class Test{
	public static void main(String[] args) throws ParseException, IOException  {
		File file = new File("d:\\mldn.txt");
		if(file.exists())
		{
			file.delete();
		}
		else
		{
			System.out.println(file.createNewFile());
		}
	}
}
```

# File类深入操作
在Windows和Linux中，文件分隔符是不同的，在win中，分隔符为"\"，在Linux中，分隔符为"/"，所以问了跨平台的稳定性，File类提供有一个常量：`public static final String separator`，同时，在创建文件的额时候，应该判断其父路径是否存在。

### 范例：正常的路径编写
```
//添加分隔符
File file = new File("d:"+File.separator+"mldn.txt");
//判断父路径
if(!file.getParentFile().exists()){
	file.getPArentFile().mkdirs();
}
```

标准文件处理流程：
```
public class Test{
	public static void main(String[] args) throws ParseException, IOException  {
		File file = new File("d:"+File.separator+"mldn.txt");
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		if(file.exists())
		{
			file.delete();
		}
		else
		{
			System.out.println(file.createNewFile());
		}
	}
}
```
# 获取文件信息
- 判断文件可读：public boolean canRead();
- 判断文件可写：public boolean canWrite();
- 获取文件长度：public long length();
- 获取最后修改时间：public long file.lastModified();
- 判断是否为目录：public boolean isDictory();
- 判断是否为文件：public boolean isFile();
- 列出目录内容：public File[] listFiles();

代码：

```
public class Test{
	public static void main(String[] args) throws ParseException, IOException  {
		File file = new File("d:"+File.separator+"mldn.txt");
		System.out.println("是否可读："+file.canRead());
		System.out.println("是否可写："+file.canWrite());
		System.out.println("文件大小"+file.length()/1024/1024);
		System.out.println("最后修改时间"+new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(file.lastModified()));
		System.out.println("是目录吗："+file.isDirectory());
		System.out.println("是文件吗："+file.isFile());
		
		File dict =new File("d:"+File.separator);
		if(dict.isDirectory()){
			File result[] = dict.listFiles();
			for(int x = 0; x < result.length;x++){
				System.out.println(result[x]);
			}
		}
	}

```