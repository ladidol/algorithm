## 引入：

对于区间更新问题，我们可以使用差分数组来维护。

**多次修改某个区间，输出最终结果：「差分」**

## 原理：

**什么是差分数组？**

咱们主要讲一下一维差分.

![one-dimensional difference](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207251810726.png)

设原来的数组为arr，差分数组为arr1，则arr1[0]默认为0，arr1[1] = arr[1] - arr[0]。
![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207251809655.png)
**差分数组有什么用？**
假设我们频繁的对数组进行范围更新，则只需要更新端点即可。
![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207251809684.png)
**差分数组怎么知道现有的值？**
由于我们只更新了差分数组边界值，没有更新原来的数组，我们怎么知道原来数组的值？
![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207251809730.png)



## 模板：

主要是了解一下思想.

```java
    {
        b[l] += c;//对前面的差进行增加
        b[r + 1] -= c;//对后面的差进行还原.
    }

    {
        for (int i = 1; i <= n; i++) {
            b[i] += b[i - 1];//正向恢复原数组
            System.out.print(b[i] + " ");
        }
    }
```



## 题目实战：

### 航班预订统计

[2022年7月25日11:16:02 - 航班预订统计 - 力扣（LeetCode）](https://leetcode.cn/problems/corporate-flight-bookings/solution/2022nian-7yue-25ri-111602-by-ladidol-dg2i/)

> 本题只涉及「区间修改 + 单点查询」，因此是一道「差分」的模板题。「差分」可以看做是求「前缀和」的逆向过程。
>
> 对于一个「将区间 [l, r][*l*,*r*] 整体增加一个值 v*v*」操作，我们可以对差分数组 c*c* 的影响看成两部分：
>
> - 对 c[l] += v*c*[*l*]+=*v*：由于差分是前缀和的逆向过程，这个操作对于将来的查询而言，带来的影响是对于所有的下标大于等于 l*l* 的位置都增加了值 v*v*；
>
> - 对 c[r + 1] -= v*c*[*r*+1]−=*v*：由于我们期望只对 [l, r][*l*,*r*] 产生影响，因此需要对下标大于 r*r* 的位置进行减值操作，从而抵消“影响”。
>
> 对于最后的构造答案，可看做是对每个下标做“单点查询”操作，只需要对差分数组求前缀和即可。

emm开始这样做的,慢慢搞搞.
```java
    class Solution1 {
        //注意本题是从1开始编号到n的.
        //对 c[l] += vc[l]+=v：由于差分是前缀和的逆向过程，这个操作对于将来的查询而言，带来的影响是对于所有的下标大于等于 ll 的位置都增加了值 vv；
        //对 c[r + 1] -= vc[r+1]−=v：由于我们期望只对 [l, r][l,r] 产生影响，因此需要对下标大于 rr 的位置进行减值操作，从而抵消“影响”。

        int[] chaFen;//记住这里是前缀和逆运算, 他不是preSum

        public int[] corpFlightBookings(int[][] bookings, int n) {
            chaFen = new int[n + 2];
            for (int[] booking : bookings) {
                chaFen[booking[0]] += booking[2];//对当前差分进行增加
                chaFen[booking[1] + 1] -= booking[2];//对下一个差分进行恢复.
            }
            int[] ans = new int[n];
            ans[0] = chaFen[1];//对第一个数进行赋值.
            for (int i = 1; i < n; i++) {
                ans[i] = ans[i - 1] + chaFen[i + 1];
            }
            return ans;
        }
    }
```
你会发现这里面的chaFen第一个数是没有用上的.我们可以优化一下.
优化后:
```java
    class Solution {
        //注意本题是从1开始编号到n的.
        //对 c[l] += vc[l]+=v：由于差分是前缀和的逆向过程，这个操作对于将来的查询而言，带来的影响是对于所有的下标大于等于 ll 的位置都增加了值 vv；
        //对 c[r + 1] -= vc[r+1]−=v：由于我们期望只对 [l, r][l,r] 产生影响，因此需要对下标大于 rr 的位置进行减值操作，从而抵消“影响”。
        int[] chaFen;//记住这里是前缀和逆运算, 他不是preSum

        public int[] corpFlightBookings(int[][] bookings, int n) {
            chaFen = new int[n + 1];
            for (int[] booking : bookings) {
                chaFen[booking[0] - 1] += booking[2];//对当前差分进行增加 
                chaFen[booking[1]] -= booking[2];//对下一个差分进行恢复.
            }
            int[] ans = new int[n];
            ans[0] = chaFen[0];//对第一个数进行赋值.
            for (int i = 1; i < n; i++) {
                ans[i] = ans[i - 1] + chaFen[i];
            }
            return ans;
        }
    }
```
主要是因为本题是从1开始编号,差分数组chaFen的下标对应的位置是相对小一位的.

### 我的日程安排表 II

[2022年7月19日11:28:32差分 - 我的日程安排表 II - 力扣（LeetCode）](https://leetcode.cn/problems/my-calendar-ii/solution/2022nian-7yue-19ri-112832-by-quirky-knut-i3lg/)

主要用的有顺序的TreeMap来模拟的数组的

```java
    class MyCalendarTwo {
            TreeMap<Integer, Integer> cnt;//treemap有顺序,相当于,用treemap来换了一下数组
//        HashMap<Integer, Integer> cnt;//hashmap没有顺序

        public MyCalendarTwo() {
        cnt = new TreeMap<Integer, Integer>();
//            cnt = new HashMap<Integer, Integer>();
        }

        public boolean book(int start, int end) {
            int maxBook = 0;
            int ans = 0;
            //默认是没有日程的,所以默认cnt差分数组都是0;
            cnt.put(start, cnt.getOrDefault(start, 0) + 1);//新建日程
            cnt.put(end, cnt.getOrDefault(end, 0) - 1);


            //恢复,正向恢复
            for (Map.Entry<Integer, Integer> entry : cnt.entrySet()) {
                int freq = entry.getValue();//得到当前预定情况, 这里一定是按顺序来的.
                maxBook += freq;//还原原数组, 得到当前天数最大的预定量maxBook
                if (maxBook > 2) {
                    cnt.put(start, cnt.getOrDefault(start, 0) - 1);//没有写入日程的,就不允许insert进去,还原回去.
                    cnt.put(end, cnt.getOrDefault(end, 0) + 1);//对于没有写入日程的,就不允许insert.
                    return false;
                }
            }
            return true;
        }
    }
```

### 得分最高的最小轮调

题解：[[Python/Java/JavaScript/Go\] 差分数组 - 得分最高的最小轮调 - 力扣（LeetCode）](https://leetcode.cn/problems/smallest-rotation-with-highest-score/solution/pythonjavajavascriptgo-chai-fen-shu-zu-b-xhvy/)

[2022年7月25日17:20:57 - 得分最高的最小轮调 - 力扣（LeetCode）](https://leetcode.cn/problems/smallest-rotation-with-highest-score/solution/2022nian-7yue-25ri-172057-by-ladidol-rpie/)

> 核心就是, 坐标在[num, n-1]之间才score加一!!!!!!!
>
> diff[i]的值的代表：与 diff [i-1]状态相比发生的score值的变化。



```java
    //核心就是, 坐标在[num, n-1]之间才score加一!!!!!!!
    class Solution {
        //一个k对应的score的差分,这样可以对其构造出差分数组diff
        //diff[i]的值的代表：与 diff [i-1]状态相比发生的score值的变化。
        public int bestRotation(int[] nums) {
            int n = nums.length;
            int[] diff = new int[n + 1];
            for (int i = 0; i < n; i++) {

                /*分类一:起点终点都在[num,n-1]; 分类二:起点终点都在[0,num-1]*/
                if (i >= nums[i]) {
                    diff[0]++;//不移动k=0就可以score加1. 有一个基础分.
                    diff[i - nums[i] + 1]--;//向左移出那个范围里面就一定会--;
                    diff[i + 1]++;//向左移动直到,k>=i+1就又都可以score加1;
                } else {
                    //不移动本身不会对答案作出贡献
                    diff[i + 1]++;//移动超过最左端0回到n-1，会对答案作出贡献
                    diff[i - nums[i] + 1 + n]--;//当继续移动，超过num回到[0, num - 1] 之间时，又不会再对答案做出贡献了
                }
            }
            int max = 0, ans = 0, cur = 0;
            for (int i = 0; i < n; i++) {
                //emm确实很像动态规划.维护当前k的score
                cur += diff[i];
                if (cur > max) {
                    ans = i;
                    max = cur;
                }
            }
            return ans;
        }
    }
```



## 参考链接：

[我的日程安排表 III【差分数组+线段树动态开点(带lazy)】 - 我的日程安排表 III - 力扣（LeetCode）](https://leetcode.cn/problems/my-calendar-iii/solution/wo-de-ri-cheng-an-pai-biao-by-jiang-hui-yac60/)

[【宫水三叶】一题双解 :「差分」&「线段树」（附区间求和目录） - 航班预订统计 - 力扣（LeetCode）](https://leetcode.cn/problems/corporate-flight-bookings/solution/gong-shui-san-xie-yi-ti-shuang-jie-chai-fm1ef/)

[DSA/README.md at main · FANGDEI/DSA (github.com)](https://github.com/FANGDEI/DSA/blob/main/PrefixSumAndDifference/README.md)

[【宫水三叶】上下界分析 + 差分应用 - 得分最高的最小轮调 - 力扣（LeetCode）](https://leetcode.cn/problems/smallest-rotation-with-highest-score/solution/gong-shui-san-xie-shang-xia-jie-fen-xi-c-p6kh/)

[[Python/Java/JavaScript/Go\] 差分数组 - 得分最高的最小轮调 - 力扣（LeetCode）](https://leetcode.cn/problems/smallest-rotation-with-highest-score/solution/pythonjavajavascriptgo-chai-fen-shu-zu-b-xhvy/)

