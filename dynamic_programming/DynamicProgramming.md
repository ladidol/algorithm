## 引入：

动态规划，英文：Dynamic Programming，简称DP，如果某一问题有很多重叠子问题，使用动态规划是最有效的。

所以动态规划中每一个状态一定是由上一个状态推导出来的，**这一点就区分于贪心**，贪心没有状态推导，而是从局部直接选最优的。

## 原理：

大家知道动规是由前一个状态推导出来的，而贪心是局部直接选最优的，对于刷题来说就够用了。

1. 根据经验猜一个「状态定义」，然后根据「状态定义」去推导一个「状态转移方程」。
2. 先写一个「记忆化搜索」解法，再将「记忆化搜索」改写成「动态规划」。

为了方便区分这两种方法，我们称第一种解法为**「经验解法」**，第二种为**「技巧解法」**吧。





## 模板：

### ① 解题步骤：

状态转移公式（递推公式）是很重要，但动规不仅仅只有递推公式。

**对于动态规划问题，我将拆解为如下五步曲，这五步都搞清楚了，才能说把动态规划真的掌握了！**

1. 确定dp数组（dp table）以及下标的含义
2. 确定递推公式
3. dp数组如何初始化
4. 确定遍历顺序
5. 举例推导dp数组

先确定递推公式再考虑dp数组的初始化的原因：**因为一些情况是递推公式决定了dp数组要如何初始化！**

后面做题一直围绕这五点来做。





### ② dp中的debug：

写动规题目，代码出问题很正常。

**找问题的最好方式就是把dp数组打印出来，看看究竟是不是按照自己思路推导的！**

> 1. **做动规的题目，写代码之前一定要把状态转移在dp数组的上具体情况模拟一遍，心中有数，确定最后推出的是想要的结果**。
> 2. 然后再写代码，如果代码没通过就打印dp数组，看看是不是和自己预先推导的哪里不一样。
> 3. 如果打印出来和自己预先模拟推导是一样的，那么就是自己的递归公式、初始化或者遍历顺序有问题了。
> 4. 如果和自己预先模拟推导的不一样，那么就是代码实现细节有问题。
> 5. **这样才是一个完整的思考过程，而不是一旦代码出问题，就毫无头绪的东改改西改改，最后过不了，或者说是稀里糊涂的过了**。

发出bug的时候，其实可以自己先思考这三个问题：

- 这道题目我举例推导状态转移公式了么？
- 我打印dp数组的日志了么？
- 打印出来了dp数组和我想的一样么？





## 题目实战：

![在这里插入图片描述](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/e04680705ff049dfb7143b1060a86da1.png)



> 1. 确定dp数组以及下标的含义：
> 2. 确定递推公式：
> 3. dp数组如何初始化：
> 4. 确定遍历顺序：
> 5. 举例推导dp数组：

### ① 基础题目：

[动态规划，dp，压缩版——2022年8月16日15:37:38 - 斐波那契数 - 力扣（LeetCode）](https://leetcode.cn/problems/fibonacci-number/solution/by-ladidol-m7dh/)

[动态规划，dp，爬楼梯——2022年8月16日16:08:09 - 爬楼梯 - 力扣（LeetCode）](https://leetcode.cn/problems/climbing-stairs/solution/dong-tai-gui-hua-dp-by-ladidol-ep82/)

[动态规划，dp，爬楼梯——2022年8月16日17:22:26 - 使用最小花费爬楼梯 - 力扣（LeetCode）](https://leetcode.cn/problems/min-cost-climbing-stairs/solution/by-ladidol-m0jp/)

1）路径问题（可视化入门dp）：

[【科学派 DP】一份「路径问题从入门到进阶」的究极指南 ... (qq.com)](https://mp.weixin.qq.com/s?__biz=MzU4NDE3MTEyMA==&mid=2247485580&idx=1&sn=84c99a0a8ab7b543c3678db577309b97&scene=21#wechat_redirect)

↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓== 个人题解如下 ==↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

[动态规划，dp，二维dp——2022年8月16日17:41:25 - 不同路径 - 力扣（LeetCode）](https://leetcode.cn/problems/unique-paths/solution/by-ladidol-86iy/)

![image-20220816175615868](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/image-20220816175615868.png)

[动态规划，dp，二维dp——2022年8月16日18:25:22 - 不同路径 II - 力扣（LeetCode）](https://leetcode.cn/problems/unique-paths-ii/solution/by-ladidol-0rns/)

[dp，路径问题，二维dp，路径储存问题！——2022年8月25日15:25:30 - 最小路径和 - 力扣（LeetCode）](https://leetcode.cn/problems/minimum-path-sum/solution/by-ladidol-d5go/)

[dp，二维dp，一维dp，滚动数组——2022年8月25日16:55:15 - 三角形最小路径和 - 力扣（LeetCode）](https://leetcode.cn/problems/triangle/solution/by-ladidol-si0q/)

[dp，二维数组——2022年8月25日17:21:54 - 下降路径最小和 - 力扣（LeetCode）](https://leetcode.cn/problems/minimum-falling-path-sum/solution/by-ladidol-feku/)

[dp，二维dp，回溯操作理解题意，路径问题——2022年8月25日18:47:35 - 下降路径最小和 II - 力扣（LeetCode）](https://leetcode.cn/problems/minimum-falling-path-sum-ii/solution/by-ladidol-8ss0/)

> 事实上，任何「记忆化搜索」都能改成「动态规划」。
>
> 记忆化搜索的实现方式：利用数组来记录已经计算出来的重叠子问题，这个和动态规划的思想非常相似，没错，记忆化搜索其实用的就是动态规划的思想。更加确切的说，可以用如下公式来表示：
>
> 记忆化搜索=深度优先搜索的实现+动态规划的思想
>
> [记忆化搜索 —— 搜索 or 动态规划 ？ - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/438406757)
>
> 我们需要先有一个「记忆化搜索」解法。所谓的有一个「记忆化搜索」，我们只需要考虑 DFS 函数会如何设计即可，而不需要真正去实现一个「记忆化搜索」。[【动态规划/路径问题】强化忽略「状态定义」&「转移方程」来求解 DP 的「技巧解法」](https://mp.weixin.qq.com/s?__biz=MzU4NDE3MTEyMA==&mid=2247485426&idx=1&sn=071aec0bf5bc2e20c58f4cbb3dcb0fbc&chksm=fd9cacedcaeb25fb895cb99963dcfcde6b10268893a085eed4000b48bf070cecbdf7c81bf991&token=1934509949&lang=zh_CN&scene=21#wechat_redirect)

[记忆性搜索，转化成动态规划dp——2022年8月26日19:16:03 - 统计所有可行路径 - 力扣（LeetCode）](https://leetcode.cn/problems/count-all-possible-routes/solution/by-ladidol-qkfi/)

[动态规划dp，从记忆性搜索到dp——2022年8月27日00:56:37 - 出界的路径数 - 力扣（LeetCode）](https://leetcode.cn/problems/out-of-boundary-paths/solution/by-ladidol-t4qr/)

[动态规划dp，二维dp，降维操作——2022年8月27日21:22:38 - 最大得分的路径数目 - 力扣（LeetCode）](https://leetcode.cn/problems/number-of-paths-with-max-score/solution/by-ladidol-es5e/)

↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑== 路径问题个人题解如上 ==↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑





> **j怎么就不拆分呢？**
>
> j是从1开始遍历，拆分j的情况，在遍历j的过程中其实都计算过了。那么从1遍历j，比较(i - j) * j和dp[i - j] * j 取最大的。递推公式：dp[i] = max(dp[i], max((i - j) * j, dp[i - j] * j));
>
> 也可以这么理解，j * (i - j) 是单纯的把整数拆分为两个数相乘，而j * dp[i - j]是拆分成两个以及两个以上的个数相乘。
>
> 如果定义dp[i - j] * dp[j] 也是默认将一个数强制拆成4份以及4份以上了。

[动态规划，dp，特别的状态方程——2022年8月16日19:01:34 - 整数拆分 - 力扣（LeetCode）](https://leetcode.cn/problems/integer-break/solution/by-ladidol-l1ov/)

> “你想一个dp问题一定不能光想，而是要用笔涂涂画画，先考虑只有一个结点，再考虑两个结点，三个结点，就能发现其实三个结点的答案能由前两个结点的答案推出，自然而然就想到dp了”
> 就是先找规律，你会发现，这个是从左往右，节点依次当根节点。dp[3] = dp[2] * dp[0] + dp[1] * dp[1] + dp[0] * dp[2]

[动态规划，dp，二叉搜索树——2022年8月16日22:13:52 - 不同的二叉搜索树 - 力扣（LeetCode）](https://leetcode.cn/problems/unique-binary-search-trees/solution/by-ladidol-8g6i/)

















### ② 背包问题：

[你的背包-陈奕迅(kugou.com)](https://www.kugou.com/share/6pdxl5azCV2.html?id=6pdxl5azCV2#hash=8D288C3652EABA7CA6EF6CEF790CE9AC&album_id=2996291)

背包系列链接：





### ③ 打家劫舍：

待做

### ④ 股票问题：

待做

### ⑤ 子序列问题：

待做

### ⑥ 贪心题dp再解决：



待做



## 总结：

动态规划是一个很大的领域，这一篇是动态规划的整体概述，讲解了什么是动态规划，动态规划的解题步骤，以及如何debug。

> 因为 DP 是一个递推的过程，因此如果数据范围是 10^5^~10^6^ 的话，可以考虑是不是可以使用一维 DP 来解决；如果数据范围是 10^2^~10^3^的话，可以考虑是不是可以使用二维 DP 来做 。

**1. 我们是如何确定本题可以使用动态规划来解决的？**

通常我们要从**「有无后效性」**进行入手分析。

如果对于某个状态，我们可以只关注状态的值，而不需要关注状态是如何转移过来的话，那么这就是一个无后效性的问题，可以考虑使用 DP 解决。

另外一个更加实在的技巧，我们还可以通过 **数据范围** 来猜测是不是可以用 DP 来做。

因为 DP 是一个递推的过程，因此如果数据范围是 10^5^~10^6^ 的话，可以考虑是不是可以使用一维 DP 来解决；如果数据范围是 10^2^~10^3^的话，可以考虑是不是可以使用二维 DP 来做 。

**2. 我们是如何确定本题的状态定义的？**

说实话，DP 的状态定义很大程度是靠经验去猜的。

虽然大多数情况都是猜的，但也不是毫无规律，相当一部分题目的状态定义是与**「结尾」**和**「答案」**有所关联的。

**3. 我们是如何确定状态转移方程的？**

通常来说，如果我们的状态定义猜对了，**「状态转移方程」**就是对**「最后一步的分情况讨论」**。

如果我们有一个对的**「状态定义」**的话，基本上**「状态转移方程」**就是呼之欲出。

因此一定程度上，**状态转移方程可以反过来验证我们状态定义猜得是否正确**：

如果猜了一个状态定义，然后发现无法列出涵盖所有情况（不漏）的状态转移方程，多半就是**状态定义猜错了，赶紧换个思路，而不是去死磕状态转移方程**。

**4. 对状态转移的要求是什么？**

我们的状态转移是要做到**「不漏」**还是**「不重不漏」**取决于问题本身：

- 如果是求最值的话，我们只需要确保**「不漏」**即可，因为重复不影响结果。
- 如果是求方案数的话，我们需要确保**「不重不漏」**。

**5. 我们是如何分析动态规划的时间复杂度的？**

对于动态规划的复杂度/计算量分析，有多少个状态，复杂度/计算量就是多少。

因此一维 DP 的复杂度通常是线性的*O* (n) ，而二维 DP 的复杂度通常是平方的 *O* (n^2^)。









## 参考链接：

1. 代码随想录
2. LeetCode
2. [【动态规划/总结必看】从一道入门题与你分享关于 DP 的分析技巧 ... (qq.com)](https://mp.weixin.qq.com/s?__biz=MzU4NDE3MTEyMA==&mid=2247485037&idx=1&sn=d6d52c48600e655161e84f25d3402514&scene=21#wechat_redirect)



![image-20220816120542613](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/image-20220816120542613.png)

![image-20220803192725594](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202208031927759.png)
