# 传统属性赋值弊端
传统的简单Java类，使用setter与getter为属性赋值，但是如果属性过多，比如有100个，那么就会有大量的setter与getter，导致代码的重复，反射是解决该问题的唯一方法。

# 属性自动设置解决方案
那么就需要一种方案实现属性内容的自动设置

1.可以使用"属性:内容|属性:内容|属性:内容..."的字符串形式来进行初始化。

2.设计思路：应该有一个专门的ClassInstanceFactory类负责所有的反射处理，即：接收反射对象和要设置的属性内容，同时可以获取指定类的实例化对象。

```
class ClassInstanceFactory{
	/*
	 * 实例化对象的创建方法，该对象可以根据传入的字符串设置类属性
	 * clazz:要设置属性的类对象
	 * vlaue:设置的属性值
	 * return：一个类对象
	 */
	private ClassInstanceFactory(){}
	
	public static <T> T create(Class<?> clazz,String value){
		return null;
	}
}
```

# 单级属性配置
对于此时的Emp类里面会发现所给的数据类型没有其他的引用关联，只描述了Emp的本类对象，所以将他称之为单级设置处理，需要处理下面两件事：
- 需要通过反射进行指定类对象的实例化处理
- 进行内容的设置（Field属性类型、方法名称、要设置的内容）；
```
class ClassInstanceFactory{
	/*
	 * 实例化对象的创建方法，该对象可以根据传入的字符串设置类属性
	 * clazz:要设置属性的类对象
	 * vlaue:设置的属性值
	 * return：一个类对象
	 */
	private ClassInstanceFactory(){}
	
	public static <T> T create(Class<?> clazz,String value){
		try{
			Object obj = clazz.getDeclaredConstructor().newInstance();
			BeanUtils.setValue(obj, value);
			return (T)obj;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

class StringUtils{//通过获取属性，将其变为大写，得到setter函数
	public static String initCap(String str){
		if(str == null || "".equals(str)){
			return str;
		}
		if(str.length() == 1){
			return str.toUpperCase();
		}else{
			return str.substring(0,1).toUpperCase()+str.substring(1);
		}
	}
}

class BeanUtils{
	private BeanUtils(){}
	/*
	 * 
	 */
	public static void setValue(Object obj,String value){
		String[] results = value.split("\\|");//按照“|”进行每一组属性的拆分
		for(int x = 0; x < results.length; x++){//循环设置内容
			//attval[0]保存的是属性名称、attval[1]保存的是属性内容
			String attval[] = results[x].split(":");//获取属性名称和内容
			try{
				Field field = obj.getClass().getDeclaredField(attval[0]);
				Method setMetgod = obj.getClass().getDeclaredMethod("set"+StringUtils.initCap(attval[0]),field.getType());
				setMetgod.invoke(obj, attval[1]);//调用setter方法
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}

class Emp{
	String name;
	String dept;
	public Emp() {
		// TODO Auto-generated constructor stub
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getName() {
		return name;
	}
	public String getDept() {
		return dept;
	}
}

public class Test {
	
	public static void main(String[] args) throws Exception {
		String value = "name:Smith|dept:Clerk";
		Emp emp = ClassInstanceFactory.create(Emp.class, value);
		System.out.println("姓名："+emp.getName()+"、职位："+emp.getDept());
	}
}
```

# 设置多种数据类型
上面的代码只能实现对STring数据类型属性的设置，而显示开发中需要设置多种属性。
主要修改BeanUtils类：

```
class BeanUtils{
	private BeanUtils(){}
	/*
	 * 
	 */
	public static void setValue(Object obj,String value){
		String[] results = value.split("\\|");//按照“|”进行每一组属性的拆分
		for(int x = 0; x < results.length; x++){//循环设置内容
			//attval[0]保存的是属性名称、attval[1]保存的是属性内容
			String attval[] = results[x].split(":");//获取属性名称和内容
			try{
				Field field = obj.getClass().getDeclaredField(attval[0]);
				Method setMetgod = obj.getClass().getDeclaredMethod("set"+StringUtils.initCap(attval[0]),field.getType());
				Object convertVal = BeanUtils.convertAttribute(field.getType().getName(), attval[1]);
				setMetgod.invoke(obj, convertVal);//调用setter方法
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 实现属性类型的转换
	 */
	private static Object convertAttribute(String type,String value){
		if("long".equals(type) || "java.lang.Long".equals(type)){
			return Long.parseLong(value);
		}else if("int".equals(type) || "java.lang.int".equals(type)){
			return Integer.parseInt(value);
		}else if("double".equals(type) || "java.lang.double".equals(type)){
			return Double.parseDouble(value);
		}else if("java.util.Date".equals(type)){
			SimpleDateFormat sdf = null;
			try{
				if(value.matches("\\d{4}-\\d{2}-\\d{2}")){
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					return sdf;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return new Date();
		}
		return value;
	}
}

class Emp{
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	long id;
	/**
	 * 
	 */
	double salary;
	String name;
	String dept;
	public Emp() {
		// TODO Auto-generated constructor stub
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getName() {
		return name;
	}
	public String getDept() {
		return dept;
	}
}

public class Test {
	
	public static void main(String[] args) throws Exception {
		String value = "name:Smith|dept:Clerk|id:123456|salary:23.4";
		Emp emp = ClassInstanceFactory.create(Emp.class, value);
		System.out.println("姓名："+emp.getName()+"、职位："+emp.getDept()+"、id："+emp.getId()+"、薪水："+emp.getSalary());
	}
}
```

# 级联对象实例化
如果说现在给定的对象之中存在有其他的引用级联关系的情况下，称为多级设置。比如，一个雇员属于一个部门，一个部门属于一个公司...

```
class BeanUtils{
	private BeanUtils(){}
	/*
	 * 
	 */
	public static void setValue(Object obj,String value){
		String[] results = value.split("\\|");//按照“|”进行每一组属性的拆分
		for(int x = 0; x < results.length; x++){//循环设置内容
			//attval[0]保存的是属性名称、attval[1]保存的是属性内容
			String attval[] = results[x].split(":");//获取属性名称和内容
			try{
				if(attval[0].contains(".")){//多级配置
					String temp[] = attval[0].split("\\.");
					//最后一位是属性名称，不在实例化范围之内
					Object currentObject = obj;
					for(int y = 0; y < temp.length-1; y++){//分级实例化
						//调用相应的getter方法，如果getter返回空表明没有实例化
						Method getMethod = currentObject.getClass().getDeclaredMethod("get"+StringUtils.initCap(temp[y]));
						Object tempObject = getMethod.invoke(currentObject);
						if(tempObject == null){//该对象未实例化
							Field field = currentObject.getClass().getDeclaredField(temp[y]);
							Method method = currentObject.getClass().getDeclaredMethod("set"+StringUtils.initCap(temp[y]),field.getType());
							Object newObject = field.getType().getDeclaredConstructor().newInstance();
							method.invoke(currentObject, newObject);
							currentObject = newObject;
						}else{
							currentObject = tempObject;
						}
					}
				}
				else{
					Field field = obj.getClass().getDeclaredField(attval[0]);
					Method setMethod = obj.getClass().getDeclaredMethod("set"+StringUtils.initCap(attval[0]),field.getType());
					Object convertVal = BeanUtils.convertAttribute(field.getType().getName(), attval[1]);
					setMethod.invoke(obj, convertVal);//调用setter方法	
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 实现属性类型的转换
	 */
	private static Object convertAttribute(String type,String value){
		if("long".equals(type) || "java.lang.Long".equals(type)){
			return Long.parseLong(value);
		}else if("int".equals(type) || "java.lang.int".equals(type)){
			return Integer.parseInt(value);
		}else if("double".equals(type) || "java.lang.double".equals(type)){
			return Double.parseDouble(value);
		}else if("java.util.Date".equals(type)){
			SimpleDateFormat sdf = null;
			try{
				if(value.matches("\\d{4}-\\d{2}-\\d{2}")){
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					return sdf;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return new Date();
		}
		return value;
	}
}

class Dept{
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	String name;
	String loc;
}

class Emp{
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	long id;
	/**
	 * 
	 */
	double salary;
	String name;
	Dept dept;
	public Emp() {
		// TODO Auto-generated constructor stub
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

}

public class Test {
	
	public static void main(String[] args) throws Exception {
		String value = "name:Smith|dept.name:财务部|dept.loc:上海|id:123456|salary:23.4";
		Emp emp = ClassInstanceFactory.create(Emp.class, value);
		System.out.println("姓名："+emp.getName()+"、id："+emp.getId()+"、薪水："
							+emp.getSalary()+"、部门："+emp.getDept().getName()+"、地点："+emp.getDept().getLoc());
	}
}

```

# 级联属性赋值
在上面的代码中，currentObject对象就可以对级联属性进行setter调用设置

```
class ClassInstanceFactory{
	/*
	 * 实例化对象的创建方法，该对象可以根据传入的字符串设置类属性
	 * clazz:要设置属性的类对象
	 * vlaue:设置的属性值
	 * return：一个类对象
	 */
	private ClassInstanceFactory(){}
	
	public static <T> T create(Class<?> clazz,String value){
		try{
			Object obj = clazz.getDeclaredConstructor().newInstance();
			BeanUtils.setValue(obj, value);
			return (T)obj;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

class StringUtils{
	public static String initCap(String str){
		if(str == null || "".equals(str)){
			return str;
		}
		if(str.length() == 1){
			return str.toUpperCase();
		}else{
			return str.substring(0,1).toUpperCase()+str.substring(1);
		}
	}
}

class BeanUtils{
	private BeanUtils(){}
	/*
	 * 
	 */
	public static void setValue(Object obj,String value){
		String[] results = value.split("\\|");//按照“|”进行每一组属性的拆分
		for(int x = 0; x < results.length; x++){//循环设置内容
			//attval[0]保存的是属性名称、attval[1]保存的是属性内容
			String attval[] = results[x].split(":");//获取属性名称和内容
			try{
				if(attval[0].contains(".")){//多级配置
					String temp[] = attval[0].split("\\.");
					//最后一位是属性名称，不在实例化范围之内
					Object currentObject = obj;
					for(int y = 0; y < temp.length-1; y++){//分级实例化
						//调用相应的getter方法，如果getter返回空表明没有实例化
						Method getMethod = currentObject.getClass().getDeclaredMethod("get"+StringUtils.initCap(temp[y]));
						Object tempObject = getMethod.invoke(currentObject);
						if(tempObject == null){//该对象未实例化
							Field field = currentObject.getClass().getDeclaredField(temp[y]);
							Method method = currentObject.getClass().getDeclaredMethod("set"+StringUtils.initCap(temp[y]),field.getType());
							Object newObject = field.getType().getDeclaredConstructor().newInstance();
							method.invoke(currentObject, newObject);
							currentObject = newObject;
						}else{
							currentObject = tempObject;
						}
					}
					//进行属性内容的设置
					Field field = currentObject.getClass().getDeclaredField(temp[temp.length-1]);
					Method setMethod = currentObject.getClass().getDeclaredMethod("set"+StringUtils.initCap(temp[temp.length-1]),field.getType());
					Object convertVal = BeanUtils.convertAttribute(field.getType().getName(), attval[1]);
					setMethod.invoke(currentObject, convertVal);//调用setter方法	
				}
				else{
					Field field = obj.getClass().getDeclaredField(attval[0]);
					Method setMethod = obj.getClass().getDeclaredMethod("set"+StringUtils.initCap(attval[0]),field.getType());
					Object convertVal = BeanUtils.convertAttribute(field.getType().getName(), attval[1]);
					setMethod.invoke(obj, convertVal);//调用setter方法	
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 实现属性类型的转换
	 */
	private static Object convertAttribute(String type,String value){
		if("long".equals(type) || "java.lang.Long".equals(type)){
			return Long.parseLong(value);
		}else if("int".equals(type) || "java.lang.int".equals(type)){
			return Integer.parseInt(value);
		}else if("double".equals(type) || "java.lang.double".equals(type)){
			return Double.parseDouble(value);
		}else if("java.util.Date".equals(type)){
			SimpleDateFormat sdf = null;
			try{
				if(value.matches("\\d{4}-\\d{2}-\\d{2}")){
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					return sdf;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return new Date();
		}
		return value;
	}
}

class Dept{
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	String name;
	String loc;
}

class Emp{
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	long id;
	/**
	 * 
	 */
	double salary;
	String name;
	Dept dept;
	public Emp() {
		// TODO Auto-generated constructor stub
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

}

public class Test {
	
	public static void main(String[] args) throws Exception {
		String value = "name:Smith|dept.name:财务部|dept.loc:上海|id:123456|salary:23.4";
		Emp emp = ClassInstanceFactory.create(Emp.class, value);
		System.out.println("姓名："+emp.getName()+"、id："+emp.getId()+"、薪水："
							+emp.getSalary()+"、部门："+emp.getDept().getName()+"、地点："+emp.getDept().getLoc());
	}
}
```
