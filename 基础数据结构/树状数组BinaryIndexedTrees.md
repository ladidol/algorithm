## 引入：

针对不同的题目，我们有不同的方案可以选择（假设我们有一个数组）：

1. 数组不变，求区间和：「前缀和」、「树状数组」、「线段树」
2. 多次修改某个数（单点），求区间和：「树状数组」、「线段树」
3. 多次修改某个区间，输出最终结果：「差分」
4. 多次修改某个区间，求区间和：「线段树」、「树状数组」（看修改区间范围大小）
5. 多次将某个区间变成同一个数，求区间和：「线段树」、「树状数组」（看修改区间范围大小）

这样看来，「线段树」能解决的问题是最多的，那我们是不是无论什么情况都写「线段树」呢？

答案并不是，而且恰好相反，只有在我们遇到第 4 类问题，不得不写「线段树」的时候，我们才考虑线段树。

因为「线段树」代码很长，而且常数很大，实际表现不算很好。我们只有在不得不用的时候才考虑「线段树」。

总结一下，我们应该按这样的优先级进行考虑：

1. 简单求区间和，用「前缀和」
2. 多次将某个区间变成同一个数，用「线段树」
3. 其他情况，用「树状数组」



## 原理：

 树状数组

- 树状数组和线段树具有相似的功能，但他俩毕竟还有一些区别：
- 树状数组能有的操作，线段树一定有；
- 线段树有的操作，树状数组不一定有。
- 树状数组的代码要比线段树短，思维更清晰，速度也更快，在解决一些单点修改的问题时，树状数组是不二之选。
- 整体的时间复杂度$O(nlog_2n)$ 空间复杂度$O(n)$

### lowbit含义

先上一段代码,可以看到lowbit只有一行操作,而且是位运算,执行效率非常高

```java
/**
   * 计算lowBit
   */
int lowbit(int x) {
    return x & -x;
}
```

忘记对应含义的可以先看一下: [原码、反码、补码，计算机中负数的表示 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/47719434#:~:text= 补码：正数的补码就是其原码；负数的反码%2B1就是补码。 如单字节的5的补码为：0000,0101；-5的原码为1111 1011。 在计算机中，正数是直接用原码表示的，如单字节5，在计算机中就表示为：0000 0101。)

![image-20220725232524083](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207252325190.png)

我们列出一下的表格: 

| x         | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    |
| --------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| lowbit(x) | 1    | 2    | 1    | 4    | 1    | 2    | 1    | 8    | 1    |

- 注意: x不能等于0, 否则会进入死循环, 所以树状数组通常使用的下标会执行+1操作

### 区间求和

区间求和可以使用前缀和来计算,区间[l,r]的和为

`sum(l,r)=preSum(r)−preSum(l−1)`

所以区间求和,转换为求索引x的前缀和,
回到本题, `int sumRange(int left, int right)` 需要计算right的前缀和,left的前缀和,然后相减就是结果

### 定义树状数组

首先定义一个累加和数组 sums , 假设数组有8个元素,如图所示,其中ni是原始数据, si是累加和数据

![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207252329197.png)

为了求索引为7的前缀和可以快速通过累加和数组sums求得

`preSum(7)=s(7) + s(6) + s(4)`

### 更新/查询树状数组

更新树状数组时使用 x += lowBit(x)来寻找被影响的数组下标

![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207252337622.png)



![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207252338511.png)



![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207252338521.png)

查询树状数组时使用 x -= lowBit(x)来寻找小于x的下一个区间:



![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207252338588.png)





```java
/**
         * 查询树状数组
         */
int query(int x) {
    int ans = 0;
    for (int i = x; i > 0; i -= lowbit(i)) {
        ans += tree[i];
    }
    return ans;
}

/**
         * 更新数组以及累加和
         */
public void update(int i, int val) {
    add(i + 1, val - nums[i]);
    nums[i] = val;
}

/**
         * 求区间和
         */
public int sumRange(int l, int r) {
    return query(r + 1) - query(l);
}



/**
         * 在x = index+1处加入 add 差值。
         */
void add(int x, int u) {
    for (int i = x; i <= n; i += lowbit(i)) {
        tree[i] += u;
    }
}








```



## 模板：

tips：其实就是 307区域和检索-数组可修改 的题解。

```java
//浓缩版
/*===================下面是模板==============================*/
class NumArray {
    // 树状数组
    int[] tree;

    /**
         * 计算lowBit
         */
    int lowbit(int x) {
        return x & -x;
    }
    /**
         * 查询树状数组
         */
    int query(int x) {
        int ans = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            ans += tree[i];
        }
        return ans;
    }

    /**
         * 在index+1处加入 add 差值。
         */
    void add(int x, int u) {
        for (int i = x; i <= n; i += lowbit(i)) {
            tree[i] += u;
        }
    }

    int[] nums;// 修改后的数组存放
    int n;

    /**
         * 初始化树状数组
         */
    public NumArray(int[] _nums) {
        nums = _nums;
        n = nums.length;
        tree = new int[n + 1];
        for (int i = 0; i < n; i++) {
            // 原数组长度+1, +1的原因是计算lowbit时,使用下标0会进入死循环
            add(i + 1, nums[i]);
        }
    }

    /**
         * 更新数组以及累加和
         */
    public void update(int i, int val) {
        add(i + 1, val - nums[i]);
        nums[i] = val;
    }

    /**
         * 求区间和
         */
    public int sumRange(int l, int r) {
        return query(r + 1) - query(l);
    }
}
```



## 题目实战：

[307. 区域和检索 - 数组可修改 - 力扣（LeetCode）](https://leetcode.cn/problems/range-sum-query-mutable/)

> 本题显然属于第 2 类问题：多次修改某个数，求区间和。
>
> 我们使用「树状数组」进行求解。
>
> 「树状数组」本身是一个很简单的数据结构，但是要搞懂其为什么可以这样「查询」&「更新」还是比较困难的（特别是为什么可以这样更新），往往需要从「二进制分解」进行出发理解。
>
> 因此我这里直接提供「树状数组」的代码，大家可以直接当做模板背过即可。

浓缩版：

```java
//浓缩版
/*===================下面是模板==============================*/
class NumArray {
    int n;//数组大小，这里就需要自定义了！
    // 树状数组
    int[] tree = new int[n];
    // 修改后的数组存放
    int[] nums;


    /**
         * 计算lowBit
         */
    int lowbit(int x) {
        return x & -x;
    }
    /**
         * 查询树状数组
         */
    int query(int x) {
        int ans = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            ans += tree[i];
        }
        return ans;
    }

    /**
         * 在x = index+1处加入 add 差值。(如果index取不到0，就可以直接x = index)
         */
    void add(int x, int u) {
        for (int i = x; i <= n; i += lowbit(i)) {
            tree[i] += u;
        }
    }



    /**
         * 初始化树状数组
         */
    public NumArray(int[] _nums) {
        nums = _nums;
        n = nums.length;
        tree = new int[n + 1];
        for (int i = 0; i < n; i++) {
            // 原数组长度+1, +1的原因是计算lowbit时,使用下标0会进入死循环
            add(i + 1, nums[i]);
        }
    }

    /**
         * 更新数组以及累加和
         */
    public void update(int i, int val) {
        add(i + 1, val - nums[i]);
        nums[i] = val;
    }

    /**
         * 求区间和
         */
    public int sumRange(int l, int r) {
        return query(r + 1) - query(l);
    }
}
```

一些题解：

[Java，简单暴力，树状数组——2022年9月19日23:51:03 - 检查是否区域内所有整数都被覆盖 - 力扣（LeetCode）](https://leetcode.cn/problems/check-if-all-the-integers-in-a-range-are-covered/solution/by-ladidol-qf06/)

[Java，（双）树状数组，乘法原理，抵消计数——2022年9月20日23:01:02 - 统计作战单位数 - 力扣（LeetCode）](https://leetcode.cn/problems/count-number-of-teams/solution/java-by-ladidol-bbmj/)

[【宫水三叶】树状数组（离散化）& 线段树的两种动态开点方式 - 区间和的个数 - 力扣（LeetCode）](https://leetcode.cn/problems/count-of-range-sum/solution/by-ac_oier-b36o/)







## 总结

类似优化的前缀和，这里面的insert就是前缀数组的更新操作，query就是查询下标x的前缀和。

用二进制的机制来优化了前缀和的前缀和的形成操作，从$O(n)$优化成了$O(nlog_2n$

## 参考链接：

[关于各类「区间和」问题如何选择解决方案（含模板） - 区域和检索 - 数组可修改 - 力扣（LeetCode）](https://leetcode.cn/problems/range-sum-query-mutable/solution/guan-yu-ge-lei-qu-jian-he-wen-ti-ru-he-x-41hv/)

[[树状数组\] 详解树状数组, 包含更新查询图解, 秒懂lowbit含义(JAVA 65ms, 68.6MB) - 区域和检索 - 数组可修改 - 力扣（LeetCode）](https://leetcode.cn/problems/range-sum-query-mutable/solution/-by-hu-ge-8-t4rn/)

![image-20220725234027876](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207252340944.png)
