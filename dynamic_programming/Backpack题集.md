

### 背包问题总结：![img](https://code-thinking-1253855093.file.myqcloud.com/pics/%E8%83%8C%E5%8C%85%E9%97%AE%E9%A2%981.jpeg)



### ① 背包公式（包含全部背包）：

> 问能否能装满背包（或者最多装多少）：dp[j] = max(dp[j], dp[j - nums[i]] + nums[i]); ，对应题目如下：

[动态规划，dp，01背包——2022年8月17日22:37:38 - 分割等和子集 - 力扣（LeetCode）](https://leetcode.cn/problems/partition-equal-subset-sum/solution/by-ladidol-h65g/)

[动态规划，dp，01背包——2022年8月17日23:29:42 - 最后一块石头的重量 II - 力扣（LeetCode）](https://leetcode.cn/problems/last-stone-weight-ii/solution/by-ladidol-c8so/)

> 问装满背包有几种方法：dp[j] += dp[j - nums[i]] ，对应题目如下：

[动态规划，DFS，记忆化搜索，01背包，一维dp，二维dp——2022年8月18日18:02:17 - 目标和 - 力扣（LeetCode）](https://leetcode.cn/problems/target-sum/solution/by-ladidol-7a7a/)

[完全背包，dp，一维背包——2022年8月31日21:04:04 - 零钱兑换 II - 力扣（LeetCode）](https://leetcode.cn/problems/coin-change-2/solution/by-ladidol-0es0/)

[完全背包，一维背包——2022年8月31日21:38:49 - 组合总和 Ⅳ - 力扣（LeetCode）](https://leetcode.cn/problems/combination-sum-iv/solution/by-ladidol-mtqi/)

背着书包爬楼梯[斐波拉契dp解法，完全背包dp——2022年8月16日16:08:09 - 爬楼梯 - 力扣（LeetCode）](https://leetcode.cn/problems/climbing-stairs/solution/dong-tai-gui-hua-dp-by-ladidol-ep82/)



> 问背包装满最大价值：dp[j] = max(dp[j], dp[j - weight[i]] + value[i]); ，对应题目如下：

[dp，01背包，多维背包——2022年8月30日19:19:10 - 一和零 - 力扣（LeetCode）](https://leetcode.cn/problems/ones-and-zeroes/solution/by-ladidol-55ta/)



> 问装满背包所有物品的最小个数：dp[j] = min(dp[j - coins[i]] + 1, dp[j]); ，对应题目如下：

[动态规划，一维完全背包——2022年8月31日20:46:58 - 零钱兑换 - 力扣（LeetCode）](https://leetcode.cn/problems/coin-change/solution/by-ladidol-1lna/)

[动态规划，完全背包，一维dp，二维dp——2022年8月31日18:47:58 - 完全平方数 - 力扣（LeetCode）](https://leetcode.cn/problems/perfect-squares/solution/by-ladidol-okt7/)



### ② 完全背包（上面题目都包含这部分的）：

如果求组合数就是外层for循环遍历物品，内层for遍历背包。

如果求排列数就是外层for遍历背包，内层for循环遍历物品。

> 求组合数：

[完全背包，dp，一维背包——2022年8月31日21:04:04 - 零钱兑换 II - 力扣（LeetCode）](https://leetcode.cn/problems/coin-change-2/solution/by-ladidol-0es0/)

> 求排列数：

[完全背包，一维背包——2022年8月31日21:38:49 - 组合总和 Ⅳ - 力扣（LeetCode）](https://leetcode.cn/problems/combination-sum-iv/solution/by-ladidol-mtqi/)

背着书包爬楼梯[斐波拉契dp解法，完全背包dp——2022年8月16日16:08:09 - 爬楼梯 - 力扣（LeetCode）](https://leetcode.cn/problems/climbing-stairs/solution/dong-tai-gui-hua-dp-by-ladidol-ep82/)

> 求最小数：两层for循环的先后顺序就无所谓了

[动态规划，一维完全背包——2022年8月31日20:46:58 - 零钱兑换 - 力扣（LeetCode）](https://leetcode.cn/problems/coin-change/solution/by-ladidol-1lna/)

[动态规划，完全背包，一维dp，二维dp——2022年8月31日18:47:58 - 完全平方数 - 力扣（LeetCode）](https://leetcode.cn/problems/perfect-squares/solution/by-ladidol-okt7/)

> 特别的题

[记忆性DFS，回溯，完全背包——2022年9月1日21:10:00 - 单词拆分 - 力扣（LeetCode）](https://leetcode.cn/problems/word-break/solution/by-ladidol-zffm/)

