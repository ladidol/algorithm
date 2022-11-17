## 引入：

当暴力穷举很好解决的话，回溯就是就是一个实现方法。

## 原理：
### ① 什么是回溯法

回溯法也可以叫做回溯搜索法，它是一种搜索的方式。

在二叉树系列中，我们已经不止一次，提到了回溯，例如[递归，回溯——2022年8月9日21:41:20 - 二叉树的所有路径 - 力扣（LeetCode）](https://leetcode.cn/problems/binary-tree-paths/solution/by-ladidol-xic0/)。

回溯是递归的副产品，只要有递归就会有回溯。

**所以以下讲解中，回溯函数也就是递归函数，指的都是一个函数**。

### ② 回溯法的效率

回溯法的性能如何呢，这里要和大家说清楚了，**虽然回溯法很难，很不好理解，但是回溯法并不是什么高效的算法。因为回溯的本质是穷举，穷举所有可能，然后选出我们想要的答案**，如果想让回溯法高效一些，可以加一些**剪枝**的操作，但也改不了回溯法就是穷举的本质。

那么既然回溯法并不高效为什么还要用它呢？

因为没得选，一些问题能暴力搜出来就不错了，撑死了再剪枝一下，还没有更高效的解法。

此时大家应该好奇了，都什么问题，这么牛逼，只能**暴力搜索**。

### ③ 回溯法解决的问题

回溯法，一般可以解决如下几种问题：

- 组合问题：N个数里面按一定规则找出k个数的**集合**
- 切割问题：一个字符串按一定规则有几种**切割方式**
- 子集问题：一个N个数的集合里有多少符合条件的**子集**
- 排列问题：N个数按一定规则全排列，有几种**排列方式**
- 棋盘问题：N皇后，解数独等等

**相信大家看着这些之后会发现，每个问题，都不简单！**

#### 什么是组合，什么是排列？

**组合是不强调元素顺序的，排列是强调元素顺序**。

例如：{1, 2} 和 {2, 1} 在组合上，就是一个集合，因为不强调顺序，而要是排列的话，{1, 2} 和 {2, 1} 就是两个集合了。

**记住组合无序，排列有序**，就可以了。

### ④ 如何理解回溯法

**回溯法解决的问题都可以抽象为树形结构**，是的，我指的是所有回溯法的问题都可以抽象为树形结构！

因为回溯法解决的都是在集合中递归查找子集，**集合的大小就构成了树的宽度，递归的深度，都构成的树的深度**。

递归就要有终止条件，所以必然是一棵高度有限的树（N叉树）。

这块可能初学者还不太理解，后面的回溯算法解决的所有题目中，我都会强调这一点并画图举相应的例子，现在有一个印象就行。


## 模板：

在讲[BinomialTree 二叉树-CSDN博客](https://blog.csdn.net/qq_51705526/article/details/126066394)中我们说了递归三部曲，这里我再给大家列出回溯三部曲。

### ① 回溯函数模板返回值以及参数

在回溯算法中，我的习惯是函数起名字为backtracking，这个起名大家随意。

回溯算法中函数返回值一般为void。

再来看一下参数，因为回溯算法需要的参数可不像二叉树递归的时候那么容易一次性确定下来，所以一般是先写逻辑，然后需要什么参数，就填什么参数。

但后面的回溯题目的讲解中，为了方便大家理解，我在一开始就帮大家把参数确定下来。

回溯函数伪代码如下：

```java
void backtracking(参数)
```

### ② 回溯函数终止条件

既然是树形结构，那么我们在讲解[BinomialTree 二叉树-CSDN博客](https://blog.csdn.net/qq_51705526/article/details/126066394)的时候，就知道遍历树形结构一定要有终止条件。

所以回溯也有要终止条件。

什么时候达到了终止条件，树中就可以看出，一般来说搜到叶子节点了，也就找到了满足条件的一条答案，把这个答案存放起来，并结束本层递归。

所以回溯函数终止条件伪代码如下：

```java
if (终止条件) {
    存放结果;
    return;
}
```

### ③ 回溯搜索的遍历过程

在上面我们提到了，回溯法一般是在集合中递归搜索，集合的大小构成了树的宽度，递归的深度构成的树的深度。

如图：

![回溯算法理论基础](https://img-blog.csdnimg.cn/20210130173631174.png)

注意图中，我特意举例集合大小和孩子的数量是相等的！

回溯函数遍历过程伪代码如下：

```java
for (选择：本层集合中元素（树中节点孩子的数量就是集合的大小）) {
    处理节点;
    backtracking(路径，选择列表); // 递归
    回溯，撤销处理结果
}
```

for循环就是遍历集合区间，可以理解一个节点有多少个孩子，这个for循环就执行多少次。

backtracking这里自己调用自己，实现递归。

大家可以从图中看出**for循环可以理解是横向遍历，backtracking（递归）就是纵向遍历**，这样就把这棵树全遍历完了，一般来说，搜索叶子节点就是找的其中一个结果了。

分析完过程，回溯算法模板框架如下：

```java
void backtracking(参数) {
    if (终止条件) {
        存放结果;
        return;
    }

    for (选择：本层集合中元素（树中节点孩子的数量就是集合的大小）) {
        处理节点;
        backtracking(路径，选择列表); // 递归
        回溯，撤销处理结果
    }
}
```

**这份模板很重要，后面做回溯法的题目都靠它了！**

## 题目实战：


### ① 组合问题：（单体中元素个数是一致的）

[回溯+递归+剪枝优化——2022年8月10日22:48:38 - 组合 - 力扣（LeetCode）](https://leetcode.cn/problems/combinations/solution/by-ladidol-xs0l/)

[递归，回溯，剪枝——2022年8月11日01:03:04 - 组合总和 III - 力扣（LeetCode）](https://leetcode.cn/problems/combination-sum-iii/solution/di-gui-hui-su-by-ladidol-tcjy/)

[回溯，组合——2022年8月11日13:15:09 - 电话号码的字母组合 - 力扣（LeetCode）](https://leetcode.cn/problems/letter-combinations-of-a-phone-number/solution/by-ladidol-2xrm/)

[回溯+if剪枝+for循环剪枝——2022年8月11日13:50:08 - 组合总和 - 力扣（LeetCode）](https://leetcode.cn/problems/combination-sum/solution/by-ladidol-o9jk/)

[回溯，剪枝，同层元素去重——2022年8月11日14:15:00 - 组合总和 II - 力扣（LeetCode）](https://leetcode.cn/problems/combination-sum-ii/solution/by-ladidol-8kui/)

### ② 切割问题：（startIndex的作用）

>例如对于字符串abcdef：
>
>- 组合问题：选取一个a之后，在bcdef中再去选取第二个，选取b之后在cdef中在选组第三个.....。
>- 切割问题：切割一个a之后，在bcdef中再去切割第二段，切割b之后在cdef中在切割第三段.....。
>
>代码中startIndex一般就是切割线。

[回溯，递归，回文串判断——2022年8月11日15:01:31 - 分割回文串 - 力扣（LeetCode）](https://leetcode.cn/problems/palindrome-partitioning/solution/by-ladidol-vpu2/)

[回溯，分割字符串，剪枝——2022年8月11日17:39:11 - 复原 IP 地址 - 力扣（LeetCode）](https://leetcode.cn/problems/restore-ip-addresses/solution/by-ladidol-erd0/)

### ③ 子集问题：（全遍历）

> 如果把 子集问题、组合问题、分割问题都抽象为一棵树的话，那么组合问题和分割问题都是收集树的叶子节点，而子集问题是找树的所有节点！
>
> **求取子集问题，不需要任何剪枝！因为子集就是要遍历整棵树**。
>
> ```java
> Arrays.sort(nums);//回溯中间，一般用双指针来去重，就需要先对数组进行排序操作。
> ```
> 特殊的去重逻辑。根据题意咯。

[回溯，子集问题——2022年8月11日20:26:04 - 子集 - 力扣（LeetCode）](https://leetcode.cn/problems/subsets/solution/by-ladidol-hg2b/)

[回溯，层级去重——2022年8月11日20:37:14 - 子集 II - 力扣（LeetCode）](https://leetcode.cn/problems/subsets-ii/solution/by-ladidol-2ly4/)

[回溯，子集问题，同层去重——2022年8月11日21:18:35 - 递增子序列 - 力扣（LeetCode）](https://leetcode.cn/problems/increasing-subsequences/solution/by-ladidol-gnhi/)

### ④ 排列问题：（startIndex = 0）

> //而used数组，其实就是记录此时path里都有哪些元素使用了，一个排列里一个元素只能使用一次。
> //是纵向的，所以used数组需要回溯。
>
> 全排列一般就是从0开始了。

[回溯，全排列，哈希——2022年8月11日21:37:55 - 全排列 - 力扣（LeetCode）](https://leetcode.cn/problems/permutations/solution/by-ladidol-2p6n/)

[回溯，双重去重，双指针——2022年8月11日22:09:52 - 全排列 II - 力扣（LeetCode）](https://leetcode.cn/problems/permutations-ii/solution/by-ladidol-9755/)

### ⑤ 棋盘问题：

N皇后：

![51.N皇后](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/20210130182532303.jpg)

[回溯，N皇后棋盘，数组的简单操作——2022年8月12日15:09:37 - N 皇后 - 力扣（LeetCode）](https://leetcode.cn/problems/n-queens/solution/hui-su-by-ladidol-f4ke/)

数独：

![img](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/2020111720451790.png)

[Java，回溯+二维递归or记忆性搜索+回溯——2022年8月12日15:58:20 - 解数独 - 力扣（LeetCode）](https://leetcode.cn/problems/sudoku-solver/solution/hui-su-di-gui-by-ladidol-yw7q/)

### ⑥ 其他：

[DFS，回溯，数据结构，Map——2022年8月12日14:32:01 - 重新安排行程 - 力扣（LeetCode）](https://leetcode.cn/problems/reconstruct-itinerary/solution/by-ladidol-6ggy/)





## 总结：

本篇我们讲解了，什么是回溯算法，知道了回溯和递归是相辅相成的。

接着提到了回溯法的效率，回溯法其实就是暴力查找，并不是什么高效的算法。

然后列出了回溯法可以解决几类问题，可以看出每一类问题都不简单。

最后我们讲到回溯法解决的问题都可以抽象为树形结构（N叉树），并给出了回溯法的模板。


## 参考链接：

[带你学透回溯算法（理论篇） (opens new window)](https://www.bilibili.com/video/BV1cy4y167mM/)一起学习！













