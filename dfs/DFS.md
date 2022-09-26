[「代码随想录」DFS算法精讲！ - 所有可能的路径 - 力扣（LeetCode）](https://leetcode.cn/problems/all-paths-from-source-to-target/solution/by-carlsun-2-66pf/)

```java
void dfs(参数) {
    if (终止条件) {
        存放结果;
        return;
    }

    for (选择：本节点所连接的其他节点) {
        处理节点;
        dfs(图，选择的节点); // 递归
        回溯，撤销处理结果
    }
}
```

深搜三部曲：

1. 确认递归函数，参数

```java
void dfs(参数)
```

通常我们递归的时候，我们递归搜索需要了解哪些参数，其实也可以在写递归函数的时候，发现需要什么参数，再去补充就可以。

例如这样：

```java
int[][] g;
int n;
List<List<Integer>> res = new ArrayList<>();
List<Integer> path = new ArrayList<>();//路径共用，加入的时候注意clone一个就行了。
void dfs (int cur){
```

2. 确认终止条件

终止条件很重要，很多同学写dfs的时候，之所以容易死循环，栈溢出等等这些问题，都是因为终止条件没有想清楚。

```java
if (终止条件) {
    存放结果;
    return;
}
```

终止添加不仅是结束本层递归，同时也是我们收获结果的时候。

3. 处理目前搜索节点出发的路径

一般这里就是一个for循环的操作，去遍历 目前搜索节点 所能到的所有节点。

```java
for (选择：本节点所连接的其他节点) {
    处理节点;
    dfs(图，选择的节点); // 递归
    回溯，撤销处理结果
}
```



题集：

[Java，dfs，贪心，顺序剪枝，可行性剪枝——2022年9月21日00:25:31 - 划分为k个相等的子集 - 力扣（LeetCode）](https://leetcode.cn/problems/partition-to-k-equal-sum-subsets/solution/by-ladidol-w8g5/)

[473. 火柴拼正方形 - 力扣（LeetCode）](https://leetcode.cn/problems/matchsticks-to-square/)

[403. 青蛙过河 题解 - 力扣（LeetCode）](https://leetcode.cn/problems/frog-jump/solution/)

[Java，并查集+dfs——2022年9月16日23:10:39 - 太平洋大西洋水流问题 - 力扣（LeetCode）](https://leetcode.cn/problems/pacific-atlantic-water-flow/solution/by-ladidol-les2/)

[854. 相似度为 K 的字符串 - 力扣（LeetCode）](https://leetcode.cn/problems/k-similar-strings/)











## 引入：



## 原理：



## 模板：



## 题目实战：



## 参考链接：



## 总结：

