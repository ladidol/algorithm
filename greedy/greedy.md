## 原理：[代码随想录 (programmercarl.com)贪心总结篇](https://programmercarl.com/贪心算法总结篇.html#贪心难题)



### 什么是贪心

**贪心的本质是选择每一阶段的局部最优，从而达到全局最优**。

这么说有点抽象，来举一个例子：

例如，有一堆钞票，你可以拿走十张，如果想达到最大的金额，你要怎么拿？

指定每次拿最大的，最终结果就是拿走最大数额的钱。

每次拿最大的就是局部最优，最后拿走最大数额的钱就是推出全局最优。

再举一个例子如果是 有一堆盒子，你有一个背包体积为n，如何把背包尽可能装满，如果还每次选最大的盒子，就不行了。这时候就需要动态规划。动态规划的问题在下一个系列会详细讲解。

### 贪心的套路（什么时候用贪心）

很多同学做贪心的题目的时候，想不出来是贪心，想知道有没有什么套路可以一看就看出来是贪心。

**说实话贪心算法并没有固定的套路**。

所以唯一的难点就是如何通过局部最优，推出整体最优。

那么如何能看出局部最优是否能推出整体最优呢？有没有什么固定策略或者套路呢？

**不好意思，也没有！** 靠自己手动模拟，如果模拟可行，就可以试一试贪心策略，如果不可行，可能需要动态规划。

有同学问了如何验证可不可以用贪心算法呢？

**最好用的策略就是举反例，如果想不到反例，那么就试一试贪心吧**。

可有有同学认为手动模拟，举例子得出的结论不靠谱，想要严格的数学证明。

一般数学证明有如下两种方法：

- 数学归纳法
- 反证法

看教课书上讲解贪心可以是一堆公式，估计大家连看都不想看，所以数学证明就不在我要讲解的范围内了，大家感兴趣可以自行查找资料。

**面试中基本不会让面试者现场证明贪心的合理性，代码写出来跑过测试用例即可，或者自己能自圆其说理由就行了**。

举一个不太恰当的例子：我要用一下1+1 = 2，但我要先证明1+1 为什么等于2。严谨是严谨了，但没必要。

虽然这个例子很极端，但可以表达这么个意思：**刷题或者面试的时候，手动模拟一下感觉可以局部最优推出整体最优，而且想不到反例，那么就试一试贪心**。

**例如刚刚举的拿钞票的例子，就是模拟一下每次拿做大的，最后就能拿到最多的钱，这还要数学证明的话，其实就不在算法面试的范围内了，可以看看专业的数学书籍！**

所以这也是为什么很多同学通过（accept）了贪心的题目，但都不知道自己用了贪心算法，**因为贪心有时候就是常识性的推导，所以会认为本应该就这么做！**



## 模板：

贪心算法一般分为如下四步：

- 将问题分解为若干个子问题
- 找出适合的贪心策略
- 求解每一个子问题的最优解
- 将局部最优解堆叠成全局最优解

其实这个分的有点细了，真正做题的时候很难分出这么详细的解题步骤，可能就是因为贪心的题目往往还和其他方面的知识混在一起。

## 题目实战：

![贪心算法大纲](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/20210917104315.png)

![贪心总结water.png (5000×6845) (myqcloud.com)](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/%E8%B4%AA%E5%BF%83%E6%80%BB%E7%BB%93water.png)





### ① 简单题：

[贪心，正向思路，反向思路——2022年8月13日15:32:40 - 分发饼干 - 力扣（LeetCode）](https://leetcode.cn/problems/assign-cookies/solution/by-ladidol-9sol/)

[贪心——2022年8月13日16:41:37 - 最大子数组和 - 力扣（LeetCode）](https://leetcode.cn/problems/maximum-subarray/solution/by-ladidol-uoeb/)

[贪心——2022年8月14日19:44:21 - 柠檬水找零 - 力扣（LeetCode）](https://leetcode.cn/problems/lemonade-change/solution/tan-xin-by-ladidol-jfls/)



### ② 中等题：

#### 1）序列问题：

[贪心——2022年8月13日16:16:55 - 摆动序列 - 力扣（LeetCode）](https://leetcode.cn/problems/wiggle-subsequence/solution/by-ladidol-201i/)

[贪心，递归——2022年8月15日20:50:36 - 单调递增的数字 - 力扣（LeetCode）](https://leetcode.cn/problems/monotone-increasing-digits/solution/tan-xin-by-ladidol-1llx/)



2）股票问题：

[贪心——2022年8月13日17:10:58 - 买卖股票的最佳时机 II - 力扣（LeetCode）](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/solution/by-ladidol-nxaf/)

[贪心，递归——2022年8月15日20:50:36 - 单调递增的数字 - 力扣（LeetCode）](https://leetcode.cn/problems/monotone-increasing-digits/solution/tan-xin-by-ladidol-1llx/)



3）两个维度权衡问题：

> 遇到两个维度权衡的时候，一定要先确定一个维度，再确定另一个维度。
>
> **如果两个维度一起考虑一定会顾此失彼**。

[两次贪心——2022年8月14日19:26:41 - 分发糖果 - 力扣（LeetCode）](https://leetcode.cn/problems/candy/solution/by-ladidol-upbm/)

[二维贪心——2022年8月14日21:35:49 - 根据身高重建队列 - 力扣（LeetCode）](https://leetcode.cn/problems/queue-reconstruction-by-height/solution/tan-xin-by-ladidol-4wqf/)

### ③ 难题：

1）区间问题：

[贪心——2022年8月13日17:26:53 - 跳跃游戏 - 力扣（LeetCode）](https://leetcode.cn/problems/jump-game/solution/by-ladidol-qhz8/)

[贪心——2022年8月13日18:03:07 - 跳跃游戏 II - 力扣（LeetCode）](https://leetcode.cn/problems/jump-game-ii/solution/tan-xin-by-ladidol-hhz9/)

[贪心——2022年8月13日21:17:45 - K 次取反后最大化的数组和 - 力扣（LeetCode）](https://leetcode.cn/problems/maximize-sum-of-array-after-k-negations/solution/tan-xin-by-ladidol-hbs6/)

[贪心——2022年8月15日00:50:37 - 用最少数量的箭引爆气球 - 力扣（LeetCode）](https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/solution/by-ladidol-o77x/)

> 无重叠区间：如果两个区间有重叠，应该保留结尾小的，结合图示很容易想到。

[贪心，区间贪心——2022年8月15日13:34:39 - 无重叠区间 - 力扣（LeetCode）](https://leetcode.cn/problems/non-overlapping-intervals/solution/by-ladidol-usjs/)

[贪心，脑筋急转弯，哈希——2022年8月15日18:09:05 - 划分字母区间 - 力扣（LeetCode）](https://leetcode.cn/problems/partition-labels/solution/by-ladidol-7dpq/)

[贪心，二维排序，区间问题——2022年8月15日18:25:28 - 合并区间 - 力扣（LeetCode）](https://leetcode.cn/problems/merge-intervals/solution/by-ladidol-72kc/)



2）其他：

[贪心——2022年8月14日16:42:41 - 加油站 - 力扣（LeetCode）](https://leetcode.cn/problems/gas-station/solution/by-ladidol-pa9l/)

[贪心，后序遍历，递归——2022年8月15日21:53:07 - 监控二叉树 - 力扣（LeetCode）](https://leetcode.cn/problems/binary-tree-cameras/solution/tan-xin-by-ladidol-pguj/)





## 总结：

**贪心没有套路，说白了就是常识性推导加上举反例**。

**最好用的策略就是举反例，如果发现是想不到反例，那这时候就可以试一试贪心吧**。

**虽然贪心是常识，有些常识并不容易，甚至很难！**

## 参考链接：

- 代码随想录
- [贪心算法巨简单？没套路？每次做题都不知道自己用了贪心？遇到简单的贪心题目靠直觉，难一点就不会了？来来来，贪心算法你该了解这些！_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1WK4y1R71x?vd_source=3ee74e97a596580dcf2e42cfeaafd7e9)





