# 简介
- Set集合，基础自Collection。特征是插入无序，不可指定位置访问。
- Set集合的数据库不能重复（== 或 eqauls）的元素
- Set集合的常用实现类有 HashSet TreeSet

常用的方法：

```
public interface Set<E> extends Collection<E> {

    A:添加功能
    boolean add(E e);
    boolean addAll(Collection<? extends E> c);

    B:删除功能
    boolean remove(Object o);
    boolean removeAll(Collection<?> c);
    void clear();

    C:长度功能
    int size();

    D:判断功能
    boolean isEmpty();
    boolean contains(Object o);
    boolean containsAll(Collection<?> c);
    boolean retainAll(Collection<?> c); 

    E:获取Set集合的迭代器：
    Iterator<E> iterator();

    F:把集合转换成数组
    Object[] toArray();
    <T> T[] toArray(T[] a);

    //判断元素是否重复，为子类提高重写方法
    boolean equals(Object o);
    int hashCode();
}
```

最常用两大实现：HashSet和TreeSet

# HashSet
HashSet实现Set接口，底层由HashMap(后面讲解)来实现，为哈希表结构，新增元素相当于HashMap的key，value默认为一个固定的Object。HashSet相当于一个阉割版的HashMap;
当有元素插入的时候，会计算元素的hashCode值，将元素插入到哈希表对应的位置中来；

具有如下特点：
- 不允许出现重复因素；
- 允许插入Null值；
- 元素无序（添加顺序和遍历顺序不一致）；
- 线程不安全，若2个线程同时操作HashSet，必须通过代码实现同步；

### 基本操作

```
public class TestClassLoader{
	public static void main(String[] args) {
		Set<String> hashSet = new HashSet<>();
		System.out.println("HashSet的初始容量大小："+hashSet.size());
		
		//元素添加
		hashSet.add("my");
		hashSet.add("name");
		hashSet.add("is");
		hashSet.add("wanger");
		hashSet.add(",");
		hashSet.add("hello");
		hashSet.add("world");
		hashSet.add("!");
		
		System.out.println("Hashset容量大小："+hashSet.size());
		
		//迭代器遍历
		Iterator<String> iterator = hashSet.iterator();
		while(iterator.hasNext()){
			String str = iterator.next();
			System.out.println(str);
		}
		//增强for循环
		for(String str:hashSet){
			if("wanger".equals(str)){
				System.out.println("得到元素："+str);
			}
			System.out.println(str);
		}
		//元素删除
		hashSet.remove("wanger");
		hashSet.clear();
		
		//集合判断
		boolean isEmpty = hashSet.isEmpty();
		System.out.println("HashSet是否为空："+isEmpty);
		boolean isContains = hashSet.contains("hello");
		System.out.println("HashSet是否为空："+isContains);
	}
}
```

# TreeSet
从名字上可以看出，此集合的实现和树结构有关。与HashSet集合类似，TreeSet也是基于Map来实现，具体实现TreeMap(后面讲解)，其底层结构为红黑树（特殊的二叉查找树）；

具有如下特点：
- 对插入的元素进行排序，是一个有序的集合（主要与HashSet的区别）;
- 底层使用红黑树结构，而不是哈希表结构；
- 允许插入Null值；
- 不允许插入重复元素；
- 线程不安全

### TreeSet基本操作

```

public class TestClassLoader{
	public static void main(String[] args) {
		TreeSet<String> treeSet = new TreeSet<>();
		System.out.println("TreeSet初始化容量大小："+treeSet.size());
		
		//元素添加
		treeSet.add("hello");
		treeSet.add("world");
		treeSet.add("!");
		
		//增强型for循环
		for(String str:treeSet){
			System.out.println("遍历元素："+str);
		}
		
		//迭代器遍历：升序
		Iterator<String> iteratorInc = treeSet.iterator();
		while(iteratorInc.hasNext()){
			System.out.println("升序遍历元素："+iteratorInc.next());
		}
		
		//迭代器遍历：降序
		Iterator<String> iteratorDesc = treeSet.descendingIterator();
		while(iteratorDesc.hasNext()){
			System.out.println("降序遍历元素："+iteratorDesc.next());
		}
		
		//元素获取
		String firstItem = treeSet.first();
		System.out.println("TreeSet头节点为："+firstItem);
		
		//获取指定元素之前的所有集合
		SortedSet<String> headSet = treeSet.headSet("world");
		System.out.println("world节点之前的元素为："+headSet.toString());
		
		//获取给定元素之间的集合（包含头，不包含尾）
		SortedSet<String> subSet = treeSet.subSet("hello", "world");
		System.out.println("hello--world之间的元素为"+subSet.toString());
		
		//集合判断
		boolean isEmpty = treeSet.isEmpty();
		System.out.println("TreeSet是否为空："+isEmpty);
		boolean isContain = treeSet.contains("hello");
		
		//元素删除,不存在返回false
		boolean removeItem = treeSet.remove("hello");
	}
}
```

# TreeSet元素排序


```
public class TreeSetTest {
    public static void main(String[] agrs){
        naturalSort();
    }

    //自然排序顺序：升序
    public static void naturalSort(){
        TreeSet<String> treeSetString = new TreeSet<String>();
        treeSetString.add("a");
        treeSetString.add("z");
        treeSetString.add("d");
        treeSetString.add("b");
        System.out.println("字母顺序：" + treeSetString.toString());

        TreeSet<Integer> treeSetInteger = new TreeSet<Integer>();
        treeSetInteger.add(1);
        treeSetInteger.add(24);
        treeSetInteger.add(23);
        treeSetInteger.add(6);
        System.out.println(treeSetInteger.toString());
        System.out.println("数字顺序：" + treeSetString.toString());
    }
}
```

结果：

```

字母顺序：[a, b, d, z]
数字顺序：[1, 6, 23, 24]

```

如果对自定义类进行排序呢：

```
public class TreeSetTest {
    public static void main(String[] agrs){
        customSort();
    }

     //自定义排序顺序：升序
    public static void customSort(){
        TreeSet<App> treeSet = new TreeSet<App>();

        //排序对象：
        App app1 = new App("hello",10);
        App app2 = new App("world",20);
        App app3 = new App("my",15);
        App app4 = new App("name",25);

        //添加到集合：
        treeSet.add(app1);
        treeSet.add(app2);
        treeSet.add(app3);
        treeSet.add(app4);
        System.out.println("TreeSet集合顺序为："+treeSet);
    }
}
```

结果会报错

将App实现Comparable接口，在做比较

```
public class App implements Comparable<App>

public int compareTo(App app) {
    //比较name的长度：
    int num = this.name.length() - app.name.length();
    //如果name长度一样，则比较年龄的大小：
    return num == 0 ? this.age - app.age : num;
}
```
