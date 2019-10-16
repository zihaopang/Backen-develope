conut = 0;

k = k*2;

for(k = 1; k <= n; k *=2)
     k = 1*2*2*2.......假设乘了m次 k = 2^m <= n ========>   2^m = n ======>  m = log2N  ====>  O(log2n)



    for(j = 1; j <= n; j++)
    	count++;
    j = 1,2,3,4.......n   =====> O(n)
{
    temp = A[j];
    A[j] = A[j+1];
    A[j+1] = temp;
}
9i
3*O(n^2)     =====>      


//2019/6/29
P007
while(sum < n)   sum += ++i;

sum = 1+2+3+4+........   k次  =====》    sum = 1+2+3+....+k   ===》 (1+k)*k/2 = n ===> k^2 +k = 2n

P008
for(语句1；语句2；语句3)
for(i=1;i<n;i++)//i =1,判断i是否小于4
{
    for(j = 1; j <= 2*i; j++)
    {
    	m++;
    }
}

第一次：i = 1,运算2次
第二次：i = 2;运算了4次
第三次：i = 3;运算了6次
。。。。。
m++    ====》   2+4+6+8+。。。。。2*n   =====》   2*(1+2+3+4+....+n) == n(n+1)   ====> n^2

int arr[10]

typedef struct
{
	int age;//年龄
	char sex;
	int number;
}Student;

Student dailin;

printf(dailin.age);

int* a;
int b = 10;
a = &b;    a-----> b


ListInsert(Sqlist &L,int i,ElemType e)
{
	......
}

int main()
{
	ListInsert(L,4,5);
}
i =0但是j = 2

if(i < 1 || j > 3)


if(i < 1 && j > 3)
