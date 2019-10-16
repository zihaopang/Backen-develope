在Collection中，List集合是有序的，可以通过索引来访问元素，遍历元素。在List集合中，我们常用到ArrayList和LinkedList这两个类。ArrayList底层通过数组实现，随着元素的增加而动态扩容。而LinkedList底层通过链表来实现，随着元素的增加不断向链表的后端增加节点。

# List常用方法

```
A:添加功能
boolean add(E e):向集合中添加一个元素
void add(int index, E element):在指定位置添加元素
boolean addAll(Collection<? extends E> c)：向集合中添加一个集合的元素。

B:删除功能
void clear()：删除集合中的所有元素
E remove(int index)：根据指定索引删除元素，并把删除的元素返回
boolean remove(Object o)：从集合中删除指定的元素
boolean removeAll(Collection<?> c):从集合中删除一个指定的集合元素。

C:修改功能
E set(int index, E element):把指定索引位置的元素修改为指定的值，返回修改前的值。

D:获取功能
E get(int index)：获取指定位置的元素
Iterator iterator():就是用来获取集合中每一个元素。

E:判断功能
boolean isEmpty()：判断集合是否为空。
boolean contains(Object o)：判断集合中是否存在指定的元素。
boolean containsAll(Collection<?> c)：判断集合中是否存在指定的一个集合中的元素。

F:长度功能
int size():获取集合中的元素个数

G:把集合转换成数组
Object[] toArray():把集合变成数组。
```

# ArrayList基本操作

```
public class TestClassLoader{
	public static void main(String[] args) {
		//创建ArrayList集合
		ArrayList<String> list = new ArrayList<>();
		System.out.println("ArrayList初始化容量："+list.size());
		
		//添加元素
		list.add("Hello");
		list.add("world");
		list.add(2,"!");//下表为2，加入
		System.out.println("ArrayList当前内容："+list.toString());
		
		//修改功能
		list.set(0, "my");
		list.set(1, "name");
		System.out.println("ArrayList当前内容："+list.toString());
		
		//获取功能
		String element = list.get(0);
		System.out.println(element);
		
		//迭代器遍历集合：
		Iterator<String> iterator = list.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
		//增强for循环
		for(String str:list){
			System.out.println(str);
		}
		
		//判断功能
		boolean isEmpty = list.isEmpty();
		boolean idContain = list.contains("my");
		
		//集合转换成数组
		String[] strArray = list.toArray(new String[]{});
		
		//删除功能
		list.remove(0);
		list.remove("world");
		list.clear();
		System.out.println("ArrayList当前容量："+list.size());
	}
}
```

# LinkedList基本操作

```
public class LinkedListTest {
    public static void main(String[] agrs){
        List<String> linkedList = new LinkedList<String>();
        System.out.println("LinkedList初始容量："+linkedList.size());

        //添加功能：
        linkedList.add("my");
        linkedList.add("name");
        linkedList.add("is");
        linkedList.add("jiaboyan");
        System.out.println("LinkedList当前容量："+ linkedList.size());

        //修改功能:
        linkedList.set(0,"hello");
        linkedList.set(1,"world");
        System.out.println("LinkedList当前内容："+ linkedList.toString());

        //获取功能：
        String element = linkedList.get(0);
        System.out.println(element);

        //遍历集合：(LinkedList实际的跌倒器是ListItr对象)
        Iterator<String> iterator =  linkedList.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next);
        }
        //for循环迭代集合：
        for(String str:linkedList){
            System.out.println(str);
        }

        //判断功能：
        boolean isEmpty = linkedList.isEmpty();
        boolean isContains = linkedList.contains("jiaboyan");

        //长度功能：
        int size = linkedList.size();

        //删除功能：
        linkedList.remove(0);
        linkedList.remove("jiaboyan");
        linkedList.clear();
        System.out.println("LinkedList当前容量：" + linkedList.size());
    }
}
```