简单Jav类是现在面向对象的主要分析基础，但是对于实际的开发之中，数据的来源是有定义的，一般根据数据表的结构来实现简单java类。
在数据库之中，提供有若干数据表，每一张数据表都可以描述具体概念，比如雇员信息表。
所以程序类的定义形式和这些实体表的差别不大，所以在实际的开发之中，数据表与简单Java类之间的基本映射的关系如下：
- 数据表的设计 = 类的定义
- 表中的字段 = 类的成员属性
- 表的一行记录 = 类的一个实例化对象
- 表的多行定义 = 对象数组
- 表的外键关联 = 引用关联

![18.数据表映射](http://)

Java类设计：
- 根据部门信息获取以下内容
	- 一个部门的完整信息
	- 一个部门之中所有雇员的完整信息
	- 一个雇员对应的领导信息

- 根据雇员信息获得以下内容
	- 一个雇员所在部门信息
	- 一个雇员对应的领导信息

对于数据表与简单java类之间的映射最好的解决步骤：
先抛开所有的关联字段不看，写出类的基本组成，而后再通过引用配置关联字段的关系

第一步：定义Emp与Dept两个实体段：
```
class Dept{
	private long deptno;
    private String dname;
    private String loc;
    private Emp emps[];
    public Dept(long deptno,String dname,String loc){
    	this.deptno = deptno;
        this.dname = dname;
        this.loc = loc;
    }
    public void setEmps(Emp[] emps){
    	this.emps = emps;
    }
    public Emp[] getEmps(){
    	return emps;
    }
    //setter,getter、略
    public String getInfo(){
    	return "【部门信息】部门编号 = "+this.deptno+"、部门名称"+dname;
    }
}
class Emp{
	private long empno;
    private String ename;
    private String job;
    private double sal;
    private double comm;
    private Dept dept;//连接部分
    public Emp(long empno,String ename,String job,String job,double sal,double comm)
    {
    	...
    }
    public void setDept(Dept dept){
    	this.dept = dept;
    }
    public Dept getDept(){
    	return this.dept;
    }
}
public class JavaDemo{
	public static void main(String args[]){
    	
    }
}
```
在以后的项目开发中一定是分两个步骤实现的：
- 第一步：根据表的结构关系进行对象的配置
- 第二步：根据要求通过结构获取数据
主程序范例：
```
public class Test{
	public static void mian(){
    	//第一步，根据关系进行类的定义
        //定义出各个实例化对象，此时没有任何的关联定义
        Dept dept = new Dept(10,"财务部","上海")
        Emp empA = new Emp(7369L,"SMITH","CLERK",800.0.0.0);
        Emp empB = new Emp(7566L,"FORD","MANAGER",2450,0.0);
        Emp empC = new Emp(7839L,"KING","PRESENT",5000,0.0);
        //需要进行的关联设置
        empA.setDept(dept);
        empB.setDept(dept);
        empC.setDept(dept);
        empA.setMgr(empB);
        empB.setMgr(empC);
        dept.setEmps(new emp[]{empA,empB,empC})';
        //第二步：根据关系获取数据
        System.out.println(dept.getInfo());
    }
}
```
