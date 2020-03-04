# JDBC 入门
### 1.什么是JDBC？
JDBC(Java DataBase Connectivity)，就是Java数据库连接，说白了就是用Java语言来操作数据库。原来我们操作数据库是在控制台使用SQL语句来操作数据库,JDBC使用Java语言向数据库发送SQL语句。
怎么连上？
- 导入jara驱动包
- 加载驱动类：Class.forName("类名");
- 给出url,username,password,其中url背下来！
- 使用DriverManager类得到Connection对象。

# JDBC原理
早期SUN公司准备开发一套API可以连接各种类型的数据库，但是发现这是不可能的。所以SUN公司转而开发了一套标准，称为JDBC，二各个厂商提供的，遵循了JDBC规范的，可以访问自己数据库的API被称之为驱动！
![1.JDBC原理](https://github.com/zihaopang/Backen-develope/blob/master/pics/database/1.JDBC%E5%8E%9F%E7%90%86.JPG)

#### JDBC增删改查代码：

```
public class Test {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		/*
		 * jdbc四大配置参数：
		 * 1.driverClassName:com.mysql.jdbc.Driver
		 * 2.url:jdbc:mysql://localhost:3306/mydb1
		 * 3.username:root
		 * 4.password:123
		 */
		
		/*
		 * 所有的java.sql.Driver实现类，都提供了static块，块内的代码
		 * 就是把自己注册到DriverManager中
		 */
		Class.forName("com.mysql.jdbc.Driver");
		/*
		 * jdbc协议的格式！jdbc:厂商的名称:子协议
		 * mysql的子协议结构：//主机:端口号/数据库名称
		 */
		String url = "jdbc:mysql://localhost:3306/mydb1";
		String user = "root";
		String password = "123";
		
		Connection con = DriverManager.getConnection(url, user, password);
		
		/*
		 * 对数据库进行增删改
		 * 1.通过Connection对象创建Statment
		 * Statment语句的发送器，它的功能就是向数据库发送语句
		 * 2.调用它的int executeUpdate(String sql),它可以发送DML,DDL
		 */
		Statement stmt = con.createStatement();
		//使用Statment发送sql语句
		//String sql = "INSERT INTO stu VALUES('ITCAST_007','wangwu',23,'male')";
		String sql = "UPDATE stu SET name='ziliao',age=22,gender='female' WHERE num='ITCAST_007'";
		//String sql = "DELETE FROM stu";
		int res = stmt.executeUpdate(sql);
		System.out.println(res);
		jdbcQuery();
	}
	
	public static void jdbcQuery() throws SQLException{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb1",
				"root","123");
		
		Statement stmt = con.createStatement();
		
		ResultSet res = stmt.executeQuery("SELECT * FROM stu");
		
		/*
		 * 数据库使用next方法访问下一行，对于每一行的元素，右下面几种方法：
		 * ResultSet提供一个getXxx()方法
		 * getInt(1)、getInt("empno");
		 * getSttring(2)、getString("ename")
		 */
		
		while(res.next()){//把光标向下移动一行，并判断下一行是否存在
			String idnum = res.getString(1);//通过列编号来获取该列的值
			String name = res.getString("name");//通过列名称来获取该列的值
			double age = res.getDouble("age");
			
			System.out.println(idnum+"、"+name+"、"+age);
			
			
		}
		
		/*
		 * 关闭资源
		 */
		res.close();
		stmt.close();
		con.close();
	}
}

```

### 标准化查询代码
```
	public static void querySample() throws SQLException{
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/mydb1";
			String user = "root";
			String password = "123";
			
			con = DriverManager.getConnection(url, user, password);
			
			stmt = con.createStatement();
			String sql = "select * from stu";
			res = stmt.executeQuery(sql);
			
			while(res.next()){
				System.out.println(res.getString(1)+"、"+res.getString(2)+"、"+res.getString(3)+"、"+res.getString(4));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(res == null) res.close();
			if(stmt == null) stmt.close();
			if(con == null) con.close();
		}
	}
}
```

### ResultSet的一些方法：
- void beforeFirst():将光标放在第一行前面
- void afterLast():把光标放在最后一行后面
- boolean first():把光标放在第一行位置上
- boolean last():把光标放在最后一行位置上

- boolean isBeforeFirst():当前光标位置是否在第一行前面
- boolean isAfterLast():当前光标位置是否在最后一行后面
- boolean isFirst():当前光标位置是否在第一行
- boolean isLast():当前光标位置是否在最后一行

- boolean previous():光标上移一行
- boolean next():光标下移一行
- boolean relative(int row):相对位移，当row为正数时候，向下移动；为负数的时候，向上移动
- boolean absolute(int row):绝对位移，把光标移动到指定行上
- int getRow():返回当前光标所有行

### 获取结果集的元素据
- 得到元素据：rs.getMeteData(),返回值为ResultSetMetadata;
- 获取结果集数据列：int getColumncount();
- 获取指定列的列名：String getColumnName(int colIndex);

```
int count = res.getMetaData().getColumnCount();
while(res.next()){//遍历行
	for(int i = 1; i <= count; i++){
    	System.out.print(rs.getString(i));
        System.out.print(", ");
    }
    System.out.println();
}
```

### createStatment参数
Statment stmt = con.createStatment(int,int);
第一个参数：
- ResultSet.TYPE_FORWORD_ONLY:不滚动结果集
- ResultSet.TYPE_SCROLL_INSENSITIVE：滚动结果集，但是结果集不会再跟随数据库而更新
- ResultSet.TYPE_SCROLL_SENSITIVE：滚动结果集，结果集数据跟随数据库的变化而变化。

第二个参数：
- CONCUR_READ_ONLY：结果集是只读的，不能通过修改结果而影响数据库
- CONCUR_UPDATABLE：结果集是可更新的，对结果集的更新可以反向影响数据库

# PreparedStatment
他是Statment的子接口
强大之处：
- 防SQL攻击
- 提高代码的可读性、可维护性
- 提高效率

PreparedStatment用法：
1.如何得到PreparedStatment对象：
- 给出SQL模板
- 调用Connection的PreparedStatment preparedStatment(String sql模板)
- 调用pstmt的setXxx()系列方法sql模板中的?赋值
- 调用pstmt的excuteUpdate()或者excuteQuery()，但它的方法都没有参数

举例：
```
	public static boolean preparedStatment() throws SQLException{
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rst = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb1", "root", "123");
			
			String sql = "select * from t_user where username=? and password=?";
			pst = (PreparedStatement) con.prepareStatement(sql);
						
			pst.setString(1,"root");
			pst.setString(2, "123");
			
			rst = pst.executeQuery();
			return rst.next();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rst == null) rst.close();
			if(pst == null) pst.close();
			if(con == null) con.close();
			return false;
		}
	}
```

### 打开PrepareStatment的预处理
什么是预处理？就是在用户第一次验证过后，服务器将不会再进行第二次验证，由于MySql默认预处理的关闭的，要打开它在url中加入如下语句：
`String url = "jdbc:mysql://localhst:3306/mydb3?useServerPrepStmts=true&cachePrepStmts=true"`

### 打开批处理
MySQL的批处理也需要通过参数来打开：rewriteBatcheStatments=true

### JDBC工具类
举例：
```
public class JDBCUtils {
	private static Properties propers = null;
	/*
	 * 给props进行初始化，加载一个就行了
	 */
	static{
		try{
			//加载配置文件
			InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");
			propers = new Properties();
			propers.load(in);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		//加载驱动类，只需要一次就行了
		try {
			Class.forName(propers.getProperty("driverClassName"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException{
		/*
		 * 1.加载配置文件
		 * 2.调用加载类
		 * 3.调用DriverManager.getConnection()
		 */
		
		//得到Connection
		return DriverManager.getConnection(propers.getProperty("url"), propers.getProperty("username"),propers.getProperty("password"));
		
	}
}
```
# JavaDAO模式
### 什么是DAO
1.Data Access Object(数据存取对象)
2.位于业务逻辑和持久化数据之间
3.实现对持久化数据的访问

### DAO模式的作用
1.隔离业务逻辑代码和数据访问代码
2.隔离不同的数据库实现

### DAO模式的组成部分
1.实体类
2.数据库连接和关闭工具类
3.DAO接口
4.DAO实现类
5.工厂类

实体类：
```
public class Emp{
	private String num;
	private String name;
	private int age;
	private String gender;
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
```

数据库连接工具类：

```
public class JDBCUtils {
	private static Properties propers = null;
	/*
	 * 给props进行初始化，加载一个就行了
	 */
	static{
		try{
			//加载配置文件
			InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");
			propers = new Properties();
			propers.load(in);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		//加载驱动类，只需要一次就行了
		try {
			Class.forName(propers.getProperty("driverClassName"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException{
		/*
		 * 1.加载配置文件
		 * 2.调用加载类
		 * 3.调用DriverManager.getConnection()
		 */
		
		//得到Connection
		return DriverManager.getConnection(propers.getProperty("url"), propers.getProperty("username"),propers.getProperty("password"));
		
	}
	
	/*数据库关闭操作*/
	public static void close(Connection con,Statement stat,ResultSet res){
		if(res != null){
			try{
				res.close();
			}catch(SQLException e){}
		}
		
		if(stat != null){
			try{
				stat.close();
			}catch(SQLException e){}
		}
		if(con != null){
			try{
				con.close();
			}catch(SQLException e){}
		}
	}
}

```

DAO接口类：

```
public interface EmpDao {
	public List<Emp> finAll();
    public void add(Emp);
}

```

DAO接口实现类

```
public class EmpDaoJdbcImpl implements EmpDao{
	Connection con = null;
	PreparedStatement pstm = null;
	ResultSet rest = null;
	
	public List<Emp> findAll(){
		List<Emp> list = new ArrayList<Emp>();
		
		try{
			con = JDBCUtils.getConnection();
			String sql = "selection * from emp";
			pstm = (PreparedStatement) con.prepareStatement(sql);
			rest = pstm.executeQuery();
			
			while(rest.next()){
				Emp e = new Emp(rest.getString(1),rest.getString(3)
						,rest.getInt(2),rest.getString(4));
				list.add(e);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			JDBCUtils.close(con, pstm, rest);
		}
		
		return list;
	}
	
	public void addEmp(Emp e){
		Connection con = null;
		
		try{
			String sql = "insert into student values(?,?,?,?)";
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql);
			pstm.setString(1, e.getNum());
			pstm.setString(2, e.getName());
			pstm.setInt(3, e.getAge());
			pstm.setString(4, e.getGender());
			
			pstm.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			JDBCUtils.close(con, null, null);
		}
	}
}
```

dao工厂类：

```
/**
 * 
 */

/**
 * @author pangzihao
 * DaoFactory
 * 通过配置文件得到dao实现类的名称
 * 通过类名称，完成创建类对象！（反射完成的！）
 */



public class DaoFactory {
	private static Properties props = null;
	static{
		InputStream in = DaoFactory.class
				.getClassLoader().getResourceAsStream("dao.properties");
		props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	public static EmpDao getUserDao(){
		/*
		 * 得到dao的实现类名称
		 */
		String daoClassName = props.getProperty("userDao.properties");
		/*
		 * 通过反射来创建实现类的对象
		 */
		try{
			Class clazz = Class.forName(daoClassName);
			return (EmpDao)clazz.newInstance();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
```

测试：

```
	public static void daoTest(){
		EmpDao userDao = DaoFactory.getUserDao();
		
		Emp e = new Emp("IT_CAST_009", "pangzihao", 20, "male");
		
		userDao.addEmp(e);
	}
```

dao.proprities文件：

```
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mydb1
username=root
password=123
```

### 格式的转换
有下面的一种情况，数据库类型与Java中类型有的关系不相同，比如：
DATA：java.sql.Date
TIME:java.sql.Time
TIMESTAMP:java.sql.Timestamp

而在java代码中，出现的都是java.util.Date,java.util.Time等数据类型，所以需要两者之间的转换。

时间类型的转换：
1.java.util.data->java.sql.Date、Time、Timestamp

- 把util的Data转换成毫秒值
- 使用毫秒值创建sql的Date、Time、Timestamp

2.时间类型的转换

- java.sql.Date、Time、Timestamp->java.util.Date
这一步不需要处理了，因为java.sql.Date是java.util.Date的子类

### 大数据
一、什么是大数据
所谓大数据，就是大的字节数据，或者大的字符数据，标准的SQL语言提供以下类型保存大数据类型：

|类型|长度|
|--------|--------|
|tinyblob|2^8-1B(256B)|
|bolb|2^16-1B(64K)|
|mediumblob|2^24-1B(16M)|
|longblob|2^32-1B(4G)|
|tinyclob|2^8-1B(256B)|
|colb|2^16-1B(64K)|
|mediumclob|2^24-1B(16M)|
|longclob|2^32-1B(4G)|

但是在mysql没有提供tinyclob、colb、mediumclob、longclob，而是使用如下几种类型处理文本数据;

|类型|长度|
|--------|--------|
|tinytext|2^8-1B(256B)|
|text|2^26-1B(64K)|
|mediumtext|2^24-1B(16M)|
|longtext|2^32-1B(4G)|

允许大点的数据存入mysql数据库：在my.ini中设置：`max_allow_packet=10485760`约为10M

### Satament批处理
批处理就是一批一批的处理，而不是一个一个的处理。批处理只针对增删改语句，没有查询。
可以使用Statment类中的addBatch(String sql)方法，把需要执行的所有SQL语句添加到一个批中，然后调用Statment类的excuteBanch()方法来执行当前“批”中的语句。
- void addBatch(String sql):添加到一条语句到“批”中
- int[] executeBatch():执行“批”中所有语句。返回值每条语句所影响的数据行
- void clearBatch():清空“批”中的所有语句。

举例：
```

for(int i = 0; i < 10000; i++){
	pstmt.setInt(1,i+1);
    pstmt.setString(2,"stu_"+i);
    pstmt.setInt(3,i);
    pstmt.setString(4,i%2==0？"男":"女");
    
    pstmt.addBanch();//添加批
}

pstmt.executeBatch();//执行批
```
