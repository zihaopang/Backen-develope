# 第七题：整数反转
这一题和判断回文数差不多，不过需要判断溢出的情况
核心代码是这样：
```
while(x != 0)
{
	int pop = x%10;
    sum = sum*10+pop;
}

```
但是`sum = sum*10+pop;`容易溢出，此时的方法是预防sum*10+pop的溢出情况，
```
//在sum = sum*10+pop之前添加判断
if((sum > Integer.MAX_VALUE) || (sum == Integer.MAX_VALUE && pop > Integer.MAX_VALUE%10))
	return 0;
if((sum < Integer.MIN_VALUE) || (sum == Integer.MIN_VALUE && pop < Integer.MIN_VALUE%10))
	return 0;
```

# 第九题:判断回文数
```
class Solution {
    public boolean isPalindrome(int x) {
        int temp = x;
        int sum = 0;
        if(x < 0)
            return false;
        
        while(temp != 0)
        {
            sum = sum*10 + temp%10;
            temp /= 10;
        }
        if(sum == x)
            return true;
        else
            return false;
    }
}
```
# 第20题：括号匹配
基本的思想，遇到左括号压栈，右括号先看看栈是否空，非空取栈顶，看看是否和当前字符串匹配，然后计数加2.
```
class Solution {
    public boolean isValid(String s) {
        if(s == "")
            return true;
        int len = s.length();
        Stack stk = new Stack();
        int cnt = 0;
        
        for(int i = 0; i < len; i++)
        {
            char c = s.charAt(i);
            switch(c)
            {
                case '(':
                    {
                        stk.push(c);
                        break;
                    }
                case '[':
                    {
                        stk.push(c);
                        break;
                    }
                case '{':
                    {
                        stk.push(c);
                        break;
                    }
                case ')':
                    {
                        if(!stk.empty())
                        {
                            char ch = (char)stk.peek();
                            if(ch == '(')
                            {
                                stk.pop();
                                cnt += 2;
                            }
                        }
                        break;
                    }
                case ']':
                    {
                        if(!stk.empty())
                        {
                            char ch = (char)stk.peek();
                            if(ch == '[')
                            {
                                stk.pop();
                                cnt += 2;
                            }
                        }
                        break;
                    }
                case '}':
                    {
                        if(!stk.empty())
                        {
                            char ch = (char)stk.peek();
                            if(ch == '{')
                            {
                                stk.pop();
                                cnt += 2;
                            }
                        }
                        break;
                    }
            }
            
        }
        
        if(cnt == len)
            return true;
        else
            return false;
    }
}
```
# 第26题：删除排序数组中的重复项
此题思想，用k计数，就是记录不重复数据，顺便作为新数组下标。用j作为探针，不断地向前探测，前提是i+j不能大于len，如果探测到不等于nums[i]的数值，那么就跳出，记录该数值，然后在该数值重新开始
核心代码
```
        while(i < len)
        {
            nums[k++] = nums[i];

            int j = 1;
            
            while((i+j) < len)
            {

                if(nums[i] == nums[i+j])
                    j++;
                else
                    break;
            }
            
            i = j+i;
        }
```
# 第58题，最后一个单词的长度
该题的思想在于用空格分割字符串，除了空字符串以及不包含空格的情况以外，还存在着字符串首尾存在空格的情况，需要使用trim方法去除空格。解题时候的错误有
- s.lastIndexOf(' ')，该方法接收一个字符，不是字符串" "
- substring而不是subString
```
class Solution {
    public int lengthOfLastWord(String s) {
        if(s.isEmpty())
            return 0;
        
        s = s.trim();
        
        if(!s.contains(" "))
            return s.length();
        
        int last = s.lastIndexOf(' ');
        
        String res = s.substring(last+1);
        
        return res.length();
    }
}
```