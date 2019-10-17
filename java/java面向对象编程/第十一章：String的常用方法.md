# 字符串与字符数组

| No | 方法名称 | 作用 |
|----|---------|-----|
| 01 | public String(char[] value)| 将字符数组转化为字符串|
| 02 | public String(char[] value,int offset,int count)|将部分字符数组转换为字符串|
| 03 | public char charAt(int index) | 获取指定字符|
| 04 | public char[] toCharArray()| 转换为字符数组|

# 字符串与字节数组
| 名称 | 作用|
|--------|--------|
|public String(byte[] bytes)|将全部的字节数组变为字符串|
| public String(byte[] bytes.int offset,int length)| 将部分字节数组构成字符串|
|public byte[] getBytes()|将字符串转为字节数组|
|public byte[] getBytes(String charsetName)|编码转换|

# 字符串比较
主要是equals()方法，该方法区分大小写。

| 方法名 |   作用     |
|--------|--------|
| public boolean equals(String anObject)| 区分大小写的相等判断 |
| public boolean equalsIgnoreCase(String str)| 不区分大小写比较|
| public int compareTo(String str)|进行大小比较，返回int数据，该数据会有大于0，小于0，等于0|
| public int compareToIgnoreCase(String str)|不区分大小写比较|

### 范例
```
public class Test{
	public static void main(){
    	String strA = 'M';
        String strB = 'm';
        System.out.println(StrA.compareTo(StrB));//结果为32，返回两者差距
        String strC = "hellO";
        String strD = "hello";
        System.out.println(strC.compareTo(strD));//结果为32
    }
}
```

# 字符串查找
从一个万丈的字符串中查找子字符串的存在就属于字符串查找操作。

| 方法名称 | 作用 |
|--------|--------|
| public boolean contains(String s) | 判断子字符串是否存在 |
| public int indexOf(String s) | 从头查找指定字符串的位置 |
| public int indexOf(String str,int fromIndex)| 从指定位置查找字符串|
| public int lastIndexOf(String str) | 从后向前查找字符串|
| public int lastIndexOf(String str,int index) | 从指定位置开始从后向前查找字符串|
| public boolean startsWith(String perfix)|判断是否以指定的字符串开头|
| public boolean startsWith(String prefix,int offset)|由指定位置判断是否由指定字符串开头|
| public boolean endsWith(String suffix)|判断是否以指定位置结尾|

### 范例：判断子字符串
```
public class Test{
	public static void main(String args[]){
    	String str = "www.baidu.com";
        System.out.println(str.contains("baidu"));
       	System.out.println(str.indexOf("hello"));//-1
        if(str.indexOf("mldn") != -1)
        	System.out.println("存在！")；
            
        System.out.println(str.lastIndexOf("."))//9
    }
}
```

### 判断是否以指定的字符串开头和结尾
```
public class Test{
	public static void main(String args[]){
    	String str = "**www.baidu.com##";
        System.out.println(str.startsWith("**"));
        System.out.println(str.endsWith("##"));
    }
}
```

# 字符串替换

| column | column |
|--------|--------|
| public String repalceAll(String regex,String replacement) | 全部替换 |
| public String repalceFirst(String regex,String replacement)| 替换首个 |

### 示例：字符串替换
```
public class Test{
	public static void main(String args[]){
    	String str = "hello";
        System.out.println(str.replaceAll("l","*"));//把"l"全部替换为"*"
        System.out.println(str.repalceFirst("l","*"));//把第一个"l"替换为"*"
        
    }
}
```

# 字符串拆分
| 方法名称 | 作用 |
|--------|--------|
| public String[] split(String regex) | 拆分字符串 |
| public String[] split(String regex,int limit) | 拆分字符串,limit指定拆分个数 |

### 范例
```
public class Test{
	public static void main(String args[]){
    	String str="hello the world";
        String result[] = str.split(" ",2)//结果：hello和the world
    }
}
```
但是也有可能出现拆不开的情况，原因可能是正则表达式，要加双斜线"\\"进行转义。
```
public classs Test{
	public static void main(String args[]){
    	String ip = "192.168.2.3";
        String[] splits = ip.split("\\.");
    }
}
```

# 字符串截取
| 方法名称 | 作用 |
|--------|--------|
|    public String subString(int beginIndex)|  从指定索引截取到结尾 |
| public String substring(int beginIndex,int endIndex)|截取指定范围的子字符串|

### 举例
```
public class Test{
	public static void main(String args[]){
    	String str = "www.baidu.com";
        System.out.println(str.subString(4));
        System.out.println(str.substring(4,8));
    }
}
```
### 举例
```
public class stringDemo{
	public static void main(String args[]){
    	strng str = "www-baidu-com.jpg";
        int beginIndex = str.indexOf("-",str.indexOf("photo"))+1;
        int endIndex = str.lastIndexOf(".");
        System.out.println(str.substring(beginIndex,endIndex));
    }
}
```

# 格式化字符串
| 方法名称 | 作用 |
|--------|--------|
|    public static String format(String format,各种类型 ... args)    |根据指定结构进行文本格式化显示 |
### 范例
```
public class Test{
	public static void main(String args[]){
    	String name = "张三";
        int age = 18;
        double score = "12.345";
        str = String.format("姓名:%s、年龄:%d、成绩:%5.2f",name,age,score);
    }
}
```

# 其他方法
|方法名称 | 作用 |
|--------|--------|
| pubic String concat(String str)    |   字符串的连接 |
| public boolean isEmpty() | 判断是否为空字符串|
|public int length()|计算长度,数组length没有括号，是一个属性|
| public String trim()|	去除左右的空格，不消除中间空格：str.trim()|
| public String toUpperCase() | 转大写|
| public String toLowerCase() | 转小写|

字符串""和null不是一个概念，一个标识有实例化对象，一个表示没有实例化对象，而isEmpty一定要在具备实例化对象的情况下调用。
虽然String类已经提供大量的方法了，但是缺少了一个首字母大写的方法，自己可以写：
```
//此后必定要使用
public class Test{
	public static String initcap(String str){
    	if(str == null)
        	return str;
        
        if(str.length() == 1)
        	return str.toUpperCase();
        
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
}
```
