## 引入：

### 完全背包原理：

#### 例题描述：

有 n 种物品和一个容量为 w 的背包，每种物品都有无限件。第 i 件物品的重量是 weight[i]，价值是value[i] 。求解将哪些物品装入背包可使这些物品的费用总和不超过背包容量，且价值总和最大。其实就是在 0-1 背包问题的基础上，增加了每件物品可以选择多次的特点（在容量允许的情况下）。

背包最大重量为4，物品为：

|       | 重量 | 价值 |
| ----- | ---- | ---- |
| 物品0 | 1    | 15   |
| 物品1 | 3    | 20   |
| 物品2 | 4    | 30   |

示例 1：

```java
输入: n = 3 ,w = 4, weight = [1,3, 4], w = [15,20, 30]
输出:  60
解释: 选 4 件物品0，可使价值最大为60。
```

其实通过01背包定义

#### 通过01背包常规解法：

我们可以直接将 01 背包的「状态定义」拿过来用：

 代表考虑前 件物品，放入一个容量为 的背包可以获得的最大价值。

由于每件物品可以被选择多次，因此对于某个 而言，其值应该为以下所有可能方案中的最大值：

- 选择 0 件物品 的最大价值，即`dp[i - 1][j]`

- 选择 1 件物品 的最大价值，即`dp[i -1][j - weight[i]] + value[i]`

- 选择 2 件物品 的最大价值，即`dp[i - 1][j - 2 * weight[i]] + 2 * value[i]`

  ...

- 选择 k 件物品 的最大价值，`dp[i - 1][j - k * weight[i]] + k * value[i]`

由此我们可以的到「状态转移方程」：

> `dp[i][j] = max(dp[i-1][j], dp[i - 1][j - k * weight[i]]+k * value[i]))`, 0 < k * v[i] <= j

## 模板：

### 二维dp数组：

#### 二维初始（未优化）：

```java
    public static int maxValue(int m, int C, int[] weight, int[] value) {
        int[][] dp = new int[m][C + 1];
        //先预处理第一件物品
        for (int j = 0; j <= C; j++) {
            // 显然只有一件物品的时候，在容量允许的情况下，能选多少件就选多少件。
            int maxK = j / weight[0];
            dp[0][j] = maxK * value[0];
        }
        // 处理剩余物品
        for (int i = 1; i < m; i++) {
            for (int j = 0; j <= C; j++) {
                // 不考虑第 i 件物品的情况（选择 0 件物品 i）
                int no = dp[i - 1][j];
                // 考虑第 i 件物品的情况
                int yes = 0;
                for (int k = 0; ; k++) {
                    if (j < value[i] * k) break;
                    yes = Math.max(yes, dp[i - 1][j - k * weight[i]] + k * value[i]);
                }
                dp[i][j] = Math.max(no, yes);
            }
        }
        return dp[m - 1][C];
    }
```

#### 滚动数组（空间优化）：

```java
    // 滚动数组优化
    public static int maxValue1(int m, int C, int[] weight, int[] value) {
        int[][] dp = new int[2][C + 1];
        //先预处理第一件物品
        for (int j = 0; j <= C; j++) {
            // 显然只有一件物品的时候，在容量允许的情况下，能选多少件就选多少件。
            int maxK = j / weight[0];
            dp[0][j] = maxK * value[0];
        }
        // 处理剩余物品
        for (int i = 1; i < m; i++) {
            for (int j = 0; j <= C; j++) {
                // 不考虑第 i 件物品的情况（选择 0 件物品 i）
                int no = dp[(i - 1) & 1][j];
                // 考虑第 i 件物品的情况
                int yes = 0;
                for (int k = 0; ; k++) {
                    if (j < value[i] * k) break;
                    yes = Math.max(yes, dp[(i - 1) & 1][j - k * weight[i]] + k * value[i]);
                }
                dp[i & 1][j] = Math.max(no, yes);
            }
        }
        return dp[m - 1][C];
    }
```

### 一维dp数组：

将`j = j - weight[i]`带入完全背包`dp[i][j]`的所有方案：

> `dp[i][j] = max(dp[i-1][j], dp[i - 1][j - k * weight[i]]+k * value[i]))`, 0 < k * v[i] <= j

从而得到一个恒等式：

> `dp[i][ j - weight[i]] = max(dp[i-1][ j - weight[i]], dp[i - 1][j - k * weight[i]]+(k-1) * value[i]))`, 0 < k * v[i] <= j

可以通过下面这个推导更明白：

![image-20220830232934376](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/image-20220830232934376.png)

从而得到二维的「状态转移方程」：(和01背包有点相像，注意第一维的角标是不一样的。)

```java
dp[i][j] = max(dp[i-1][j], dp[i ][j - weight[i]]+ value[i]))
```

由于计算 `dp[i][j]` 的时候，依赖于 `dp[i][j-weight[i]]`。因此我们在改为「一维空间优化」时，需要确保 `dp[j-weight[i]]`存储的是当前行的值，即确保`dp[j-weight[i]]` 已经被先更新，所以遍历方向是从小到大。

```java
// 一维数组
public static int maxValue2(int m, int C, int[] weight, int[] value) {
    int[] dp = new int[C + 1];
    for (int i = 0; i < m; i++) {
        for (int j = 0; j <= C; j++) {
            // 不考虑第 i 件物品的情况（选择 0 件物品 i）
            int no = dp[j];
            // 考虑第 i 件物品的情况
            int yes = j - weight[i] >= 0 ? dp[j - weight[i]] + value[i] : 0;
            dp[j] = Math.max(no, yes);
        }
    }
    return dp[C];
}
```



## 总结：

形式上，我们只需要将 01 背包问题的「一维空间优化」解法中的「容量维度」遍历方向从「从大到小 改为 从小到大」就可以解决完全背包问题。

但本质是因为两者进行状态转移时依赖了不同的格子：

- 01 背包依赖的是「上一行正上方的格子」和「上一行左边的格子」。
- 完全背包依赖的是「上一行正上方的格子」和「本行左边的格子」。

同时发现通过「一维空间优化」方式，可以将求解「完全背包」问题的时间复杂度从$O(N*C*C)$降低为 $O(N*C)$。

## 参考链接：

三叶姐公众号；

代码随想录；