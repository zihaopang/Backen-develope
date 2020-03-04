# 事务
### 概念
事务：是数据库操作的最小工作单元，是作为单个逻辑工作单元执行的一系列操作；这些操作作为一个整体一起向系统提交，要么都执行、要么都不执行；事务是一组不可再分割的操作集合（工作逻辑单元）；

### 事务的四大特性
事务的四大特性：
- 原子性（Atomicity）:事务中所有的操作是不可分割的原子单位，事务中的所有操作，要么全部执行成功，要么全部失败
- 一致性（Consistency）：事务执行后，数据库状态与其他业务规则保持一致。如转账业务，无论事务执行成功与否，参与转账的两个账号之间的余额之和应该是不变的。
- 隔离性（Isolation）：隔离性是指在并发操作中，不同的事务之间应该隔离开来，使每个并发的事务不会相互干扰。
- 持久性（Durability）：一旦数据提交成功，事务中所有的数据操作被持久化到数据库中。

### MySql中的事务
在默认情况下，MySql每执一条SQL语句，都是一个单独的事务。如需要在一个事务中包含多条SQL语句，那么需要开启事务和结束事务。
- 开启事务：start tarnsaction
- 结束事务：commit或rollback

在执行SQL语句之前，先执行start transaction,这就开启了一个事务，然后可以去执行多条SQL语句。最后要结束事务，commit表示提交，即事务中多条SQL语句所做的影响都会持久化到数据库中。或者rollback，表示回滚，即回滚到事务的起点，之前所作的操作都被撤销了。

演示zs和ls转账100的示例：

```
START TRANSACTION;
UPDATE account SET balance=balance-100 WHERE id=1;
UPDATE account SET balance=balance+100 WHERE id=2;
ROLLBACK;
```

### JDBC中完成事务的处理
在JDBC中处理事务，都是通过Connection完成的！
同一事物中所有的操作，都在使用同一个Connection对象
1.JDBC中的事务

Connection的三个方法与事务相关：
- setAutoCommit(boolean):设置是否为自动提交事务，如果true(默认值为true)表示自动提交，也就是每条执行的SQL语句都是一个单独的事务，如果设置为false，那么就相当于开启事务了。con.setAutoCommit(false)表示开启事务。
- commit():提交结果事务；con.commit表示提交事务
- roolback():回滚结束事务。con.rollback()表示回滚事务

jdbc处理事务的代码格式：

```
try{
	con.setAutoCommit(false);//开启事务
	...
    ...
    con.commit();//try的最后提交事务
}catch{
	con.rollback();//回滚事务
}
```

处理事务举例:

AccountDao.java:

```
public class AccountDao {
	public void updateBalance(Connection con,String name,double balance){
		try{
			String sql = "update account set balance=? where name=?";
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);	
			
			pstmt.setDouble(1, balance);
			pstmt.setString(2, name);
			
			pstmt.executeUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
```

zhuangzhang:转账事务代码

```
public class Demo1 {
	public static void zhuangzhang(String from,String to,double money){
		Connection con = null;
		try{
			con = JDBCUtils.getConnection();
			//开启事务
			con.setAutoCommit(false);
			
			AccountDao dao = new AccountDao();
			dao.updateBalance(con, from, money);
			dao.updateBalance(con, to, money);
			
			//提交事务
			con.commit();
		}catch(Exception e){
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void fun1(){
		zhuangzhang("zs", "lisi", 100);
	}
	
	public static void main(String[] args) {
		fun1();
	}
}
```

但是代码有个问题，就是对Connection的操作都在服务层进行处理

### 事务的隔离级别
一、事务的并发读问题
- 脏读：读取到另一个事务未提交数据
- 不可重复读：两次读取不一致
- 幻读（虚读）：读到另一事务已经提交数据

二、四大隔离级别
1.SERIALIZEABLE(串行化)

- 不会出现任何并发问题，因为它对同一数据的访问是串行的，非并发访问的。
- 性能最差

2.REPEATABLE（可重复读）（MYSQL默认隔离级别）

- 防止脏读和不可重复读，不能处理幻读问题
- 性能比SERIALIZABLE好

3.READ COMMITTED（读取已提交的数据）（ORACLE默认隔离级别）

- 防止脏读
- 性能比REPEATABLE READ好

4.READ UNCOMMITTED（读取未提交数据）

- 可能出现事务的并发问题
- 性能最好

### MySql隔离级别
MySql的默认隔离级别为Repeatable read,可以通过下面的语句查看：
`select @@tx_isolation`
也可以通过下面的语句来设置当前的隔离级别：
`set transaction isolationlevel [4选1]`

### JDBC设置隔离级别
con.setTransactionsolation(int level):平时没有必要修改
参数可选值如下：
- Connection.TRANSACTION_READ_UNCOMMITTED;
- Connection.TRANSACTION_READ_COMMITTED;
- Connection.TRANSACTION_REPEATABLE_READ;
- Connection.TRANSACTION_SERIALIZABLE;

# 数据库连接池
池参数（所有池参数都有默认值）
- 初始大小：10个，数据库连接个数
- 最小空闲连接数：3个，空闲未使用数据库的线程个数
- 增量：一次创建的最小单位（5个）
- 最大空闲连接数：12个，最多未使用却连接数据库的线程个数
- 最大连接数：20个，最多连接数据库的线程个数
- 最大的等待时间：1000毫秒，等待连接的时间

四大连接参数：
连接池也是使用四大参数来完成创建连接对象

实现的接口：
连接池必须实现：javax.sql.DataSource接口！连接池返回的Connection对象，它的close()方法与众不同！调用它的close方法不是关闭，而是把连接归还给池。

示例代码：（导包dbcp和pool）

```
public class Demo {
	public void fun1() throws SQLException{
		/*
		 * 1.创建连接池对象
		 * 2.配置四大参数
		 * 3.配置参数池
		 * 4.得到连接对象
		 */
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/mydb1");
		dataSource.setUsername("root");
		dataSource.setPassword("123");
		
		dataSource.setMaxActive(20);
		dataSource.setMaxIdle(3);
		dataSource.setMaxWait(1000);
		
		Connection con = dataSource.getConnection();
	
		/*
		 * 连接池内部使用四大参数创建了连接对象，即MySql驱动提供的Connection
		 * 连接池使用mysql的连接对象进行了装饰，只对close()方法进行了增强
		 * 装饰之后的Connection的close()方法，用来把当前的连接归还给了池！
		 */
		con.close();//把连接归还给池
	}
}

```

### 装饰者模式
对象增强的一些手段：
- 继承
- 装饰着模式
- 动态代理
继承的话，比如说右一个咖啡类，客户可能要牛奶咖啡，有糖咖啡，牛奶有糖咖啡，橙汁咖啡等等。。。那么继承就会让类变得很多。
比如现在有四种基本的类：

```
class 咖啡类{}
class 有糖咖啡 extends 咖啡类{}
class 有奶咖啡 extends 咖啡类{}
class 有盐咖啡 extends 咖啡类{}
```
那么客户的选择可能排列组合很多种，装饰者模式这样做：

```
咖啡 a = new 加糖();
咖啡 b = new 加盐(1);//对a进行了装饰，就是给a加盐
咖啡 c = new 加奶(b);
```

回忆一下IO流的四大类：
- 字节流：InputStream,OutputStream
- 字符流：Reader,Writer

InputStream:
FileInputStream:他是节点流！就是和一个资源绑定在一起的，比如文件
BufferedInputStream:他是装饰流！创建我是一定要给我一个底层对象，然后将该对象的数据放置于缓冲区。
IO流的装饰模式应用：
FileInputStream in = new FileInputStream("F;/a.jpg");
BufferedInputStream b = new BufferedInputStream(in);
ObjectInputStream o = new ObjectInputStream(b);

装饰模式：不知道被增强的对象的具体类型时，可以使用！
is a,has a,use a

```
class MyConnection implements Connection{//是你
	private Connection con;//底层对象，被增强对象
    
    public MyConnection(Connection con){//通过构造器传递底层对象
    	this.con = con;
    }
    
    //一切拜托你
    public Statment createStatment(){
    	return con.createStatment();
    }
    //增强点（自己扩展的方法）
    public void close(){
    	把当前连接归还给池
    }
}
```

那么，对于创建一个普通连接，可以用两种方法：

```
Connection con = 通过四大参数来创建连接对象！由MySql提供！
Connection con1 = new MyConnectiion(con);
```

那么`con1.createStatment()`与`con.createStatment()`没有差别，其本质都是调用mysql提供的cretaeStatment，但是close却不一样

### C3P0连接池

一、C3P0简介
C3P0也是开源免费得连接池，C3P0被很多人看好！

二、C3P0的使用
C3P0中池类是：CombopooledDataSource


举例：(导包：mchange和c3p0的jar包)

```
	public static void fun1() throws PropertyVetoException, SQLException{
		//创建连接池对象
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		//对池子进行四大参数的配置
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mydb1");
		dataSource.setUser("root");
		dataSource.setPassword("123");
		
		//池配置,也可采用默认值
		dataSource.setAcquireIncrement(5);
		dataSource.setInitialPoolSize(20);
		dataSource.setMinPoolSize(2);
		dataSource.setMaxPoolSize(50);
		
		Connection con = dataSource.getConnection();
		System.out.println(con);
		con.close();
	}
```

### C3P0的配置文件
配置文件要求：
- 文件名称：必须叫c3p0-config.xml
- 文件位置：必须在src下

xml文件示例：

```
<?xml version="1.0" encoding="UTF-8"?>
<c3p0-config>
	<default-config>
		<property name="jdbcUrl">jdbc:mysql://localhost:3306/mydb1</property>
		<property name="dirverClass">com.mysql.jdbc.Driver</property>
		<property name="user">root</property>
		<property name="password">123</property>
		<property name="acquireIncrement">3</property>
		<property name="initialPoolSize">10</property>
		<property name="minPoolSize">2</property>
		<property name="maxPoolSize">10</property>
	</default-config>
</c3p0-config>
```

连接数据库示例

```
	/*
	 * 配置文件配置
	 */
	public static void fun2() throws SQLException{
		/*
		 * 在创建连接池对象时，这个对象就会自动加载配置文件！无需指定
		 */
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		Connection con = dataSource.getConnection();
		System.out.println(con);
		con.close();
	}
```

也可以命名配置，这针对不同的数据库配置，比如这个配置在xml中有名称:

```
<?xml version="1.0" encoding="UTF-8"?>
<c3p0-config>
	<default-config>
		...
	</default-config>
    
    <named-config name="paizhi">
    ...
    </named-config>
</c3p0-config>
```

那么在代码中可以指定配置名称：

```	/*
	 * 配置文件配置
	 */
	public static void fun2() throws SQLException{
		/*
		 * 在创建连接池对象时，这个对象就会自动加载配置文件！无需指定
		 */
		ComboPooledDataSource dataSource = new ComboPooledDataSource("peizhi1");
		
		Connection con = dataSource.getConnection();
		System.out.println(con);
		con.close();
	}

```

# JDBC工具类

### service事务
在此之前，我们处理事务的时候，都把Connection暴露出来，但在实际开发中，service层不应该出现Connection，他只应该在dao中出现。

传统的service层处理代码的格式：

```
try{
	con.setAutoCommit(false);//开启事务
	...
    ...
    con.commit();//try的最后提交事务
}catch{
	con.rollback();//回滚事务
}
```

将其转变成：

```
private XXXDao dao = new XXXDao();
public void serviceMethod(){
	private void dao = new XXXDao();
    try{
    	JdbcUtils.beginTransaction();
        dao.daoMethod();
        dao.dao.method2();
        JdbcUtils.commitTransaction();
    }catch(Exception e){
    	JdbcUtils.rollbackTransaction();
    }
}
```

所以要制作Jdbc工具类，JdbcUtils.
示例代码：(依赖库：cp30,mchange,commons-pool,commons-dbutils)

JdbcUtils:

```
public class JdbcUtils {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	//事务专用链接，所有的连接都要使用该链接，包括AccountDao中
	private static Connection con = null;
	/*
	 * 使用连接池返回一个连接对象
	 */
	public static Connection getConnection() throws SQLException{
		/*
		 * 保证随时返回的都是同一个连接
		 */
		if(con !=  null) return con;
		
		return dataSource.getConnection();
	}
	
	/*
	 * 返回连接池对象
	 */
	public static DataSource getDataSource(){
		return dataSource;
	}
	/*
	 * 开启事务
	 * 1.获取一个Connection,设置setAutocommit(false);
	 * 2.还要保证dao中的连接是我们创建的
	 */
	public static void beginTransaction() throws SQLException{
		if(con != null) throw new SQLException("已经开启事务，不要重复开启！");
		con = getConnection();
		con.setAutoCommit(false);
	}
	/*
	 * 提交事务
	 * 1.获取befinTransaction提供的Connection，然后调用commit方法
	 */
	public static void commitTransaction() throws SQLException{
		if(con == null) throw new SQLException("还没有开启事务，不可提交！");
		con.commit();
		con.close();
		//连接已经关闭，不能用了
		con= null;
	}
	/*
	 * 提交事务
	 * 1.获取befinTransaction提供的Connection，然后调用roll方法方法
	 */
	public static void rollbackTransaction() throws SQLException{
		if(con == null) throw new SQLException("还没有开启事务，不可回滚！");
		con.rollback();
		con.close();
		//连接已经关闭，不能用了
		con = null;
	}
}

```

DAO层：

```
public class AccountDao {
	public static void update(String name,double money) throws SQLException{
		QueryRunner qr = new QueryRunner();
		String sql = "update account aet balance=banance+? where name=?";
		Object[] params = {money,name};
		
		//需要自己提供链接，保证多次调用的是一个连接
		Connection con = JdbcUtils.getConnection();
		qr.update(con,sql,params);
	}
}
```

service层：

```
public class AccountService {
	private AccountDao dao = new AccountDao();
	
	public void serviceMethod(){
		try {
			JdbcUtils.beginTransaction();
			dao.update("zs", -10);
			dao.update("ls", 10);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}
```

### Tomcat配置连接池（莫名其妙的一章）
一、Tomcat配置JNDI资源
JNDI（Java Naming and Directory Interface），Java命名与接口目录。JNDI的作用就是，在服务器上配置资源，然后通过统一的方式来获取配置的资源。
这里配置的资源就是连接池，这样项目就可以通过统一的方式获取连接池对象

### ThreadLocal
Java支持多线程，但是多线程会引发同步问题，为了解决这些问题，可以加上synchorized关键字，但是该关键字会让线程的运行速度减慢，那么可不可以让每个线程都拥有一份数据呢，这就是ThreadLocal.

ThreadLocal API:
ThreadLocal只有三个方法：
- void set(T value):保存数值
- T get():获取值
- void remove():移除值

举例：

```
public class Demo1{
	public void fun1(){
    	ThreadLocal<String> t1 = new ThreadLocal<String>();
        t1.set("hello");
        String s = t1.get();
        t1.remove();
        System.out.println(s);
    }
}
```

内存的情况：

| key | value |
|--------|--------|
|thread1|aaa|
|thread2|bbb|
|thread3|ccc|

如果又创建了一个线程，那么不会对本地线程有仍和的影响

![3.ThreadLocal类](https://github.com/zihaopang/Backen-develope/blob/master/pics/database/3.ThreadLocal%E7%B1%BB.jpg)

ThreadLocal通常用在一个类成员上面，多个线程访问它的时候，每个线程都有自己的副本，互不干扰
Spring中把Connection放到了ThreadLocal中！

### dbutils
dbutils是对数据库进行增删改查的小工具,需要导包：commons-dbutils

使用示例：

增删改

```
	public static void fun1(String name,double money) throws SQLException{
		QueryRunner qr = new QueryRunner();
		String sql = "update account set balance=banance+? where name=?";
		Object[] params = {money,name};
		
		Connection con = JdbcUtils.getConnection();
		qr.update(con,sql,params);
	}
    
```
查询：

```
	public static void query() throws SQLException{
		QueryRunner qr = new QueryRunner();
		String sql = "select * from account where name=?";
		Object[] params = {"lisi"};
		//BeanHandler需要一个类型，然后他会把rs中的数据封装到指定类的javabean对象中，然后返回JavaBean
		Account ac = qr.query(sql, new BeanHandler<Account>(Account.class),params);
		System.out.println(ac);
	}
```

Queryrunner方法：

update方法：
- int update(String sql,Object... params),可执行增删改语句
- int update(Connection con,String sql,Object... params),需要调用者提供Connection,这说明本方法不再管理Connection了


query方法：
- T query(String sql,ResultSetHandler rsh,Object... params),可执行查询
- T query(Connection con,String sql,ResultSetHandler rsh,Object... params),可执行查询

他会先得到ResultSet，然后调用rsh的handle()把rs转换成需要的类型

ResultSethandler接口：
- BeanHandler(单行)：构造器需要一个Class类型的参数，用来把一行结果转换成指定类型的javaBean对象
- BeanListHandler(多行)：构造器也是需要一个Class类型的参数，用来把一行结果集转换成一个javaBean,多行转换成List对象，一堆javaBean
- MapHandler(单行)：把一行结果集转换成Map对象，比如：(sid:1001,sname:zs,age:99)
- MapListHandler(多行)：把一行记录转换成一个Map，多行就是多个Map
- ScalarHandler(单行单列)：通常用于`select conut(*) from stu`,结果集是单行单列，他返回一个Object

Tips:
使用BeanHandler的时候，mysql的包要导入，且包版本的对。并且实体类中的属性名称要和数据库中的名称一一对应，getter与setter方法名称也到对应。如属性为sname，那么getter与setter方法应该为getSname与setsName!

举例：

```
	//BeanHandler
	public static void BeanHandler() throws SQLException{
		QueryRunner qr = new QueryRunner();
		String sql = "select * from account where sname=?";
		Object[] params = {"lisi"};
		
		Account ac = qr.query(JdbcUtils.getConnection(),sql, new BeanHandler<Account>(Account.class),params);
		System.out.println(ac);
	}
	//BeanListHandler
	public static void BeanListHandler() throws SQLException{
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from account";
		
		List<Account> accList = qr.query(sql, new BeanListHandler<Account>(Account.class));
		
		System.out.println(accList);
	}
	//MapHandler
	public static void MapHandler() throws SQLException{
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from account where sname=?";
		Object[] params = {"lisi"};
		Map map = qr.query(sql, new MapHandler(),params);
		
		System.out.println(map);
	}
	
	//MapListHandler
	public static void MapListHandler() throws SQLException{
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select * from account";
		Object[] params = {"lisi"};
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
		
		System.out.println(mapList);
	}	
	//ScalarHandler,他是单行单列时使用，最为合适,得出数据库中记录数
	public static void  ScalarHandler() throws SQLException{
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
		String sql = "select count(*) from account";
		Object[] params = {"lisi"};
		/*
		 * 为什么是Object，因为返回的可能是Long,Integer,BigInteger等等，其共同父类是Object
		 */
		//Object obj = qr.query(sql, new ScalarHandler<Account>());
		
		/*
		 * 靠谱的写法
		 */
		Number num = (Number)qr.query(sql, new ScalarHandler());
		
		int n = num.intValue();
		
		System.out.println(n);
	}
```

### TxQueryRunner

```

    /**
     * @author pangzihao
     * TxQueryRunner
     * 该类重写了QueryRunner类中的不包含Connection方法，全部处理了数据库的连接的关闭问题，下次调用方法就不用进行连接和关闭Connection了
     */
     
public class TxQueryRunner extends QueryRunner{

	@Override
	public int[] batch(String sql, Object[][] params) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		int result[] = super.batch(sql, params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public int execute(String sql, Object... params) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		int result = super.execute(sql, params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> List<T> execute(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		List<T> result = super.execute(sql, rsh, params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T insert(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		T result = super.insert(sql, rsh, params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T insert(String sql, ResultSetHandler<T> rsh) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		T result = super.insert(sql, rsh);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T insertBatch(String sql, ResultSetHandler<T> rsh, Object[][] params) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		T result = super.insertBatch(sql, rsh, params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T query(String sql, Object param, ResultSetHandler<T> rsh) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		T result = super.query(sql, param, rsh);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T query(String sql, Object[] params, ResultSetHandler<T> rsh) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		T result = super.query(sql, params, rsh);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		T result = super.query(sql, rsh, params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		T result = super.query(sql, rsh);;
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public int update(String sql, Object... params) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		int result = super.update(sql, params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public int update(String sql, Object param) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		int result = super.update(sql, param);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public int update(String sql) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = JdbcUtils.getConnection();
		int result = super.update(sql);
		JdbcUtils.releaseConnection(con);
		return result;
	}

}

```

### 线程安全的JdbcUtils
在之前的JdbcUtils中，所有的线程都使用一个connetion对数据库进行操作，如果一个数据库已经被一个线程连接，那么另一个数据库连接的时候就会报错，所以需要使用ThreadLocal对其进行线程安全的保证。

代码：

JdbcUtils.java
```
public class JdbcUtils {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	//事务专用链接，所有的连接都要使用该链接，包括AccountDao中
	private static ThreadLocal<Connection> tl = new ThreadLocal<>();
	/*
	 * 使用连接池返回一个连接对象
	 */
	public static Connection getConnection() throws SQLException{
		/*
		 * 保证随时返回的都是同一个连接
		 */
		Connection con = tl.get();
		if(con !=  null) return con;
		
		return dataSource.getConnection();
	}
	
	/*
	 * 返回连接池对象
	 */
	public static DataSource getDataSource(){
		return dataSource;
	}
	/*
	 * 开启事务
	 * 1.获取一个Connection,设置setAutocommit(false);
	 * 2.还要保证dao中的连接是我们创建的
	 */
	public static void beginTransaction() throws SQLException{
		Connection con = tl.get();
		if(con != null) throw new SQLException("已经开启事务，不要重复开启！");
		con = getConnection();
		con.setAutoCommit(false);
		
		tl.set(con);//把 当前的线程保存起来，让别人获取得到
	}
	/*
	 * 提交事务
	 * 1.获取befinTransaction提供的Connection，然后调用commit方法
	 */
	public static void commitTransaction() throws SQLException{
		Connection con = tl.get();
		
		if(con == null) throw new SQLException("还没有开启事务，不可提交！");
		con.commit();
		con.close();
		//连接已经关闭，不能用了
		//con= null;
		tl.remove();//不用设置为null了，直接看ThreadLocal里面有没有
	}
	/*
	 * 提交事务
	 * 1.获取befinTransaction提供的Connection，然后调用roll方法方法
	 */
	public static void rollbackTransaction() throws SQLException{
		Connection con = tl.get();
		if(con == null) throw new SQLException("还没有开启事务，不可回滚！");
		con.rollback();
		con.close();
		//连接已经关闭，不能用了
		//con = null;
		tl.remove();
	}
	/*
	 * 关闭连接
	 */
	public static void releaseConnection(Connection connection) throws SQLException{
		/*
		 * 如果连接为空，说明不用释放，还未创建
		 */
		if(con == null) return;
		/*
		 * 如果该连接和本链接的一个，那么关闭连接
		 */
		if(con != connection) connection.close();
	}
}

```

# 分页（视频存在问题，servlet没学）
### 什么叫分页
比如首页，第一页，第1，2，3，4，...页
分页的优点：只查询一页，不用查询所有页
### 分页数据
页面的数据都是由Servlet传递来的！
Servlete:
1.当前页：pageCode,pc

	- pc:如果页面没有传递当前页码，那么Serclet默认是第一页，或者按照传递的页面为准
2.总页数：total

	- tp：总记录数/每页记录数

3.总记录数：totalRecord,tr

	- tr:dao来获取，select count(*) from t_customer;

4.每页记录数：业务数据或叫做系统数据
5.当前也数据：beanList

### 数据的传递
这写分页数据总要在各层之间来回传递！我们把这些分页数据封装到一个javabean中，他就叫分页Bean，例如
