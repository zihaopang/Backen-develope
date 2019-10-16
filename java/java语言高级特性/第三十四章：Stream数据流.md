进入了大数据时代，所以在集合里面也支持有数据的流式分析，所以提供了Stream的接口，同时在Collection接口中提供了为此接口实例化的方法。
- 获取Stream的接口对象：public default Stream<E> stream();

# Stream的基础操作
Stream主要功能是进行数据的分析处理，同时主要是真对于集合中的数据进行分析操作。

### 范例：Stream的基本操作形式

```
public class TestClassLoader{
	public static void main(String[] args) {
		List<String> all = new ArrayList<>();
		Collections.addAll(all, "Java","JavaScript","Python","Ruby","Go");
		Stream<String> stream = all.stream();//获取Stream接口对象
		System.out.println(stream.count());//输出元素的个数
		//将每一个元素变为小写字母，而后判断字母是否存在
		long cnt = stream.filter((ele)->ele.toUpperCase().contains("j")).count();
		System.out.println(cnt);
	}
}
```

在Stream数据流处理的过程之中还允许进行数据的分页处理，提供有两个方法：
- 设置取出的最大数据量：public Stream<T> limit(long maxSize);
- 跳过指定数据量：public Stream<T> skip(long n);

# MapReduce基础模型
在进行数据分析的处理之中有一个最重要的基础模型：MapReduce，对这个模型一共分为两个部分：map的处理部分，reduce的分析部分。

基础模型代码：

```
class Order{
	private String name;
	private double price;
	private int amount;
	public Order(String name,double price,int amount){
		this.name = name;
		this.price = price;
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}

public class TestClassLoader{
	public static void main(String[] args) {
		List<Order> all = new ArrayList<>();
		all.add(new Order("手机", 1200, 1));
		all.add(new Order("强脑", 3200, 3));
		all.add(new Order("桌子", 5200, 4));
		all.add(new Order("强杯", 1200, 5));
		all.add(new Order("帽子", 1200, 6));
		//分析购买商品之中带有“强”的信息数据，并且进行商品单价和数量的处理，随后分析汇总
		DoubleSummaryStatistics stat = all.stream().filter((ele)->ele.getName().contains("强"))
				.mapToDouble((orderObject)->orderObject.getPrice() * orderObject.getAmount()).summaryStatistics();
		System.out.println("购买数量："+stat.getCount());
		System.out.println("购买总价："+stat.getSum());
		System.out.println("平均花费："+stat.getAverage());
		System.out.println("最高花费："+stat.getMax());
		System.out.println("最低花费："+stat.getMin());
	}
}
```