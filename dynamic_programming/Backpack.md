[分享｜深度讲解背包问题：面试中每五道动态规划就有一道是背包模型 ... - 力扣（LeetCode）](https://leetcode.cn/circle/discuss/GWpXCM/)





## 拓展（这里要改的，这是之前贪心的部分）

这道题目还可以继续深化，就是一步一个台阶，两个台阶，三个台阶，直到 m个台阶，有多少种方法爬到n阶楼顶。

这又有难度了，这其实是一个完全背包问题，但力扣上没有这种题目，所以后续我在讲解背包问题的时候，今天这道题还会拿从背包问题的角度上来再讲一遍。

这里我先给出我的实现代码：

LeetCode上的爬楼梯ac代码：

```cpp
// 版本一
class Solution {
public:
    int climbStairs(int n) {
        if (n <= 1) return n; // 因为下面直接对dp[2]操作了，防止空指针
        vector<int> dp(n + 1);
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) { // 注意i是从3开始的
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
};
```

这是背包问题的源码：

```cpp
class Solution {
public:
    int climbStairs(int n) {
        vector<int> dp(n + 1, 0);
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) { // 把m换成2，就可以AC爬楼梯这道题
                if (i - j >= 0) dp[i] += dp[i - j];
            }
        }
        return dp[n];
    }
};
```



代码中m表示最多可以爬m个台阶。

**以上代码不能运行哈，我主要是为了体现只要把m换成2，粘过去，就可以AC爬楼梯这道题，不信你就粘一下试试，哈哈**。

**此时我就发现一个绝佳的大厂面试题**，第一道题就是单纯的爬楼梯，然后看候选人的代码实现，如果把dp[0]的定义成1了，就可以发难了，为什么dp[0]一定要初始化为1，此时可能候选人就要强行给dp[0]应该是1找各种理由。那这就是一个考察点了，对dp[i]的定义理解的不深入。

然后可以继续发难，如果一步一个台阶，两个台阶，三个台阶，直到 m个台阶，有多少种方法爬到n阶楼顶。这道题目leetcode上并没有原题，绝对是考察候选人算法能力的绝佳好题。

这一连套问下来，候选人算法能力如何，面试官心里就有数了。

**其实大厂面试最喜欢问题的就是这种简单题，然后慢慢变化，在小细节上考察候选人**















































## 引入：

背包问题：泛指一类「给定价值与成本」，同时「限定决策规则」，在这样的条件下，如何实现价值最大化的问题。是动态规划里的非常重要的一部分。

相信很早咱们就听过背包比如：[你的背包](https://www.kugou.com/share/6pdxl5azCV2.html?id=6pdxl5azCV2#hash=8D288C3652EABA7CA6EF6CEF790CE9AC&album_id=2996291)（雾

对于面试的话，其实掌握01背包，和完全背包，就够用了，最多可以再来一个多重背包。![416.分割等和子集1](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/20210117171307407.png)

而完全背包又是也是01背包稍作变化而来，即：完全背包的物品数量是无限的。所以背包问题的理论基础重中之重是01背包！

leetcode上没有纯01背包的问题，都是01背包应用方面的题目，也就是需要转化为01背包问题。所以我先通过纯01背包问题，把01背包原理讲清楚，后续再讲解leetcode题目的时候，重点就是讲解如何转化为01背包问题了。





## 原理：

### ① 01背包：

有n件物品和一个最多能背重量为w 的背包。第i件物品的重量是weight[i]，得到的价值是value[i] 。**每件物品只能用一次**，求解将哪些物品装入背包里物品价值总和最大。

暴力求解：每一件物品其实只有两个状态，取或者不取，所以可以使用回溯法搜索出所有的情况，那么时间复杂度就是$o(2^n)$，这里的n表示物品数量。**所以暴力的解法是指数级别的时间复杂度。进而才需要动态规划的解法来进行优化！**

#### 例题描述：

背包最大重量为4。物品为：

|       | 重量 | 价值 |
| ----- | ---- | ---- |
| 物品0 | 1    | 15   |
| 物品1 | 3    | 20   |
| 物品2 | 4    | 30   |

问背包能背的物品最大价值是多少？（以下讲解和图示中出现的数字都是以这个例子为例。）

#### 1）二维dp数组01背包：

依然动规五部曲分析一波。

##### ① 确定dp数组以及下标的含义

对于背包问题，有一种写法， 是使用二维数组，**即`dp[i][j]`表示从下标为0-i的物品里任意取，放进容量为j的背包，价值总和最大是多少。**

![image-20220817200229669](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/image-20220817200229669.png)

##### ② 确定递推公式

再回顾一下`dp[i][j]`的含义：从下标为[0-i]的物品里任意取，放进容量为j的背包，价值总和最大是多少。那么可以有两个方向推出来`dp[i][j]`，

背包容量能放进去一个物品i：那么可以有两个方向推出来`dp[i][j]`（背包最大容量都不能放入物品i，就直接`dp[i][j] = dp[i-1][j] `）

  > **选择不放物品i**：由`dp[i-1][j]`推出，即背包容量为j，里面不放物品i的最大价值，**此时`dp[i][j]`就是`dp[i-1][j]`**。(其实就是当物品i的重量大于背包j剩余的重量时，物品i无法放进背包j中，所以被背包j内的价值依然和前面相同。)
  >
  > **选择放物品i**：由`dp[i - 1][j - weight[i]]`推出，`dp[i - 1][j - weight[i]]` 为背包容量**为`j - weight[i]`的时候没放到物品i的最大价值**，那么「`dp[i - 1][j - weight[i]]`   +`value[i]` （物品i的价值）」，就是背包放物品i得到的最大价值

所以递归公式：

```java
dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
```

##### ③ dp数组如何初始化

1. 首先从`dp[i][j]`的定义出发，如果背包容量j为0的话，即`dp[i][0]`，无论是选取哪些物品，背包价值总和一定为0。

2. 状态转移方程 `dp[i][j] = max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i])`; 可以看出i 是由 i-1 推导出来，那么i为0的时候就一定要初始化。

> `dp[0][j]`，即：i为0，存放编号0的物品的时候，各个容量的背包所能存放的最大价值。
>
> 1. 当 j < weight[0]的时候，`dp[0][j] `应该是 0，因为背包容量比编号0的物品重量还小。
> 2. 当j >= weight[0]时，`dp[0][j]` 应该是value[0]，因为背包容量放足够放编号0物品。

代码初始化：

```java

for (int j = 0 ; j < weight[0]; j++) {  //在java中数组默认值是0，所以这里可以省略。
    dp[0][j] = 0;
}
// 正序遍历
for (int j = weight[0]; j <= bagweight; j++) {
    dp[0][j] = value[0];//注意这里是和weight[0]相关的，第一排不达标是连第一个有效值都放不进去的。
}

```

![image-20220817195251400](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/image-20220817195251400.png)





##### ④ 确定遍历顺序 中间过程

先遍历 物品还是先遍历背包重量呢？ 其实都可以！！ 但是先遍历物品更好理解。

先遍历物品，然后遍历背包重量的代码：

```java
// weight数组的大小 就是物品个数
for (int i = 1; i < weight.size(); i++) { // 遍历物品
    for (int j = 0; j <= bagweight; j++) { // 遍历背包容量
        if (j < weight[i]) dp[i][j] = dp[i - 1][j];
        else dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);

    }
}
```



原因就是遍历是从左和上两个方向推得，两个遍历就一定有左和上。

其实背包问题里，两个for循环的先后循序是非常有讲究的，理解遍历顺序其实比理解推导公式难多了。

##### ⑤ 举例推导dp数组

来看一下对应的dp数组的数值，如图：

![image-20220817200248598](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/image-20220817200248598.png)

做动态规划的题目，最好的过程就是自己在纸上举一个例子把对应的dp数组的数值推导一下，然后在动手写代码！如果遇到各种问题，推导一下dp数组的演变过程，如果推导明白了，代码写出来就算有问题，只要把dp数组打印出来，对比一下和自己推导的有什么差异，很快就可以发现问题了。

**详细可以看后面的二维dp数组的代码模板**



#### 2）一维dp数组（滚动数组）：

再次观察一下「状态转移方程」：`dp[i][j] = max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i])`;

不难发现第`i`行格子的值只依赖与第`i-1`行的值，明确是第`i-1`行的第`j`个格子和第`j-weight[i]`个格子（也就是对应着第`i`个物品不选和选的两种情况）

如图：





















## 模板：

### ① 01背包：

#### 1）二维dp数组：

二维dp写法：（这里面的dp都是有意义的,`dp[0][j]`表示j中对于物品value[0]的装有情况。）

```java
    //01背包二维dp(2)
    static void test11weightbagproblem(int[] weights, int[] values, int bagWeight) {
        int wLen = weights.length;
        int value0 = 0;
        //定义dp数组：dp[i][j]表示背包容量为j时，前i个物品能获得的最大价值
        int[][] dp = new int[wLen][bagWeight + 1];
        //初始化：背包容量为0时，能获得的价值都为0
        for (int i = 0; i < wLen; i++) {//dp[i][0]均为0，不用初始化,这一句可以去省略！！！
            dp[i][0] = value0;
        }
        for (int j = weights[0]; j < bagWeight; j++) {//对dp[0][j]进行初始化
            dp[0][j] = values[0];
        }

        //遍历顺序：先遍历物品，再遍历背包容量
        for (int i = 1; i < wLen; i++) {
            for (int j = 0; j <= bagWeight; j++) {
                if (j < weights[i]) {//j指针越界的情况，就是dp[i - 1][j - weights[i]] + values[i]不合法了。
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i]] + values[i]);
                }
            }
        }
    }
```

结果：是没有第一排的。

```java
0 15 15 15 0 
0 15 15 20 35 
0 15 15 20 35 
```

#### 1.5）三叶姐版二维dp空间优化：

只需要将其中一维直接改成 2，任何在将第一维维的 `dp[i]` 改成 `dp[i&1]` 或者 `dp[i%2]` 即可（推荐前者，在不同架构的机器上，运算效率更加稳定）。

```java
    //01背包二维dp空间大优化，dp[2][j]，三叶姐的优化版本。
    static void test2weightSanYebagproblem(int[] weights, int[] values, int bagWeight) {
        int wLen = weights.length;
        int value0 = 0;
        //定义dp数组：dp[i][j]表示背包容量为j时，前i个物品能获得的最大价值
        int[][] dp = new int[2][bagWeight + 1];

        for (int j = weights[0]; j < bagWeight; j++) {//对dp[0][j]进行初始化
            dp[0][j] = values[0];
        }

        for (int i = 1; i < wLen; i++) {
            for (int j = 0; j <= bagWeight; j++) {
                if (j < weights[i]) {//j指针越界的情况，就是dp[i - 1][j - weights[i]] + values[i]不合法了。
                    dp[i & 1][j] = dp[(i - 1) & 1][j];
                } else {
                    dp[i & 1][j] = Math.max(dp[(i - 1) & 1][j], dp[(i - 1) & 1][j - weights[i]] + values[i]);
                }
            }
        }

        System.out.println(Arrays.toString(dp[0]));
        System.out.println(Arrays.toString(dp[1]));
    }
```

dp结果：

```java
[0, 15, 15, 20, 35]  //dp[0]
[0, 15, 15, 20, 35]  //dp[1]
```



#### 2）一维dp数组：



```java
//01背包一维dp（滚动数组）
static void test2weightbagproblem(int[] weights, int[] values, int bagWeight) {
    int wLen = weights.length;
    int value0 = 0;
    //定义dp数组：dp[j]表示背包容量为j时，能获得的最大价值
    int[] dp = new int[bagWeight + 1];
    //遍历顺序：先遍历物品，再遍历背包容量
    for (int i = 0; i < weights.length; i++) {
        for (int j = bagWeight; j >= weights[i]; j--) {
            dp[j] = Math.max(dp[j], dp[j - weights[i]] + values[i]);
        }
    }
}
```

使用一维dp数组的写法，比较直观简洁，而且空间复杂度还降了一个数量级！

### ② 完全背包：

待做。。。。

[【动态规划/背包问题】那就从 0-1 背包问题开始讲起吧 ... (qq.com)](https://mp.weixin.qq.com/s/xmgK7SrTnFIM3Owpk-emmg)

好好看看这个dp背包01





## 题目实战：

### ① 01背包：

问能否能装满背包（或者最多装多少）：dp[j] = max(dp[j], dp[j - nums[i]] + nums[i]); ，对应题目如下：

[动态规划，dp，01背包——2022年8月17日22:37:38 - 分割等和子集 - 力扣（LeetCode）](https://leetcode.cn/problems/partition-equal-subset-sum/solution/by-ladidol-h65g/)

[动态规划，dp，01背包——2022年8月17日23:29:42 - 最后一块石头的重量 II - 力扣（LeetCode）](https://leetcode.cn/problems/last-stone-weight-ii/solution/by-ladidol-c8so/)

问装满背包有几种方法：dp[j] += dp[j - nums[i]] ，对应题目如下：

有时候官解也是很好的！

[动态规划，01背包，一维dp，二维dp——2022年8月18日18:02:17 - 目标和 - 力扣（LeetCode）](https://leetcode.cn/problems/target-sum/solution/by-ladidol-7a7a/)









问背包装满最大价值：dp[j] = max(dp[j], dp[j - weight[i]] + value[i]); ，对应题目如下：



问装满背包所有物品的最小个数：dp[j] = min(dp[j - coins[i]] + 1, dp[j]); ，对应题目如下：









### ② 完全背包：





### ③ 一些补题：

这两题回溯可以做：和01背包第一题有点像。

- 698.划分为k个相等的子集
- 473.火柴拼正方形















## 总结：

刚刚把二维dp的01背包和一维滚动数组背包都讲完，**这里其实可以发现最简单的是推导公式了，推导公式估计看一遍就记下来了，但难就难在如何初始化和遍历顺序上**。

注意到初始化 和 遍历顺序的重要性，我们后面做力扣上背包面试题目的时候，大家就会感受出来了。

以上的讲解可以开发一道面试题目（毕竟力扣上没原题）。比如面试可能会问：

1. 要求先实现一个纯二维的01背包，如果写出来了，然后再问为什么两个for循环的嵌套顺序这么写？反过来写行不行？再讲一讲初始化的逻辑。
2. 然后要求实现一个一维数组的01背包，最后再问，一维数组的01背包，两个for循环的顺序反过来写行不行？为什么？



注意以上问题都是在候选人把背包算法题代码写出来的情况下才问的。

就是纯01背包的题目，都不用考01背包应用类的题目就可以看出候选人对算法的理解程度了。

## 参考链接：

[背包 DP · SharingSource/LogicStack-LeetCode Wiki (github.com)](https://github.com/SharingSource/LogicStack-LeetCode/wiki/背包-DP)：到时候看一下三叶姐的背包dp

可以看一下这个，比较全面简洁，学完后：[一篇文章吃透背包问题！（细致引入+解题模板+例题分析+代码呈现） - 最后一块石头的重量 II - 力扣（LeetCode）](https://leetcode.cn/problems/last-stone-weight-ii/solution/yi-pian-wen-zhang-chi-tou-bei-bao-wen-ti-5lfv/)

[【动态规划/背包问题】那就从 0-1 背包问题开始讲起吧 ... (qq.com)](https://mp.weixin.qq.com/s/xmgK7SrTnFIM3Owpk-emmg)







