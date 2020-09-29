# 130. 被围绕的区域

### 题目

给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。

找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。

### 示例

```
X X X X
X O O X
X X O X
X O X X
```
运行你的函数后，矩阵变为：
```
X X X X
X X X X
X X X X
X O X X
```
**解释:**

被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。

### 思路

这道题我们拿到基本就可以确定是图的dfs、bfs 遍历的题目了。题目中解释说被包围的区间不会存在于边界上，所以我们会想到边界上的 O要特殊处理，只要把边界上的O特殊处理了，那么剩下的O替换成X就可以了。问题转化为，如何寻找和边界联通的O，我们需要考虑如下情况。
```
X X X X
X O O X
X X O X
X O X X
```
这时候的O是不做替换的。因为和边界是连通的。为了记录这种状态，我们把这种情况下的O换成#作为占位符，待搜索结束之后，遇到O替换为X；遇到 #，替换回O(和边界连通的O)。

### 代码

```java
class Solution {

    public void solve(char[][] board) {
        if(board == null || board.length == 0){
            return ;
        }
        int width = board[0].length;
        int high = board.length;
        for(int i = 0; i < high; i++){
            for(int j = 0; j < width; j++){
                boolean check = i==0 || j==0 || i==high-1 || j==width-1;
                if(check&&board[i][j] == 'O'){
                    dfs(board,i,j);
                }
            }
        }

        for(int i = 0; i < high; i++){
            for(int j = 0; j < width; j++){
                if(board[i][j] == '#'){
                    board[i][j] = 'O';
                }else if(board[i][j] == 'O'){
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void dfs(char[][] board,int i,int j){
        if(i<0 || j<0 || i>=board.length || j>=board[0].length || board[i][j]=='X' || board[i][j]=='#'){
            return;
        }
        board[i][j] = '#';
        dfs(board,i-1,j);//上
        dfs(board,i+1,j);//下
        dfs(board,i,j-1);//左
        dfs(board,i,j+1);//右
    }
}
```

注意`boolean check = i==0 || j==0 || i==high-1 || j==width-1;`的写法

