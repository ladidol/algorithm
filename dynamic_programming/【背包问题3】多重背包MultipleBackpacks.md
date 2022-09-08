## 引入：

多重背包



## 原理：





### 1）通过01背包来解决多重背包：



多重背包和01背包是非常像的， 为什么和01背包像呢？每件物品最多有M件可用，把M件摊开，其实就是一个01背包问题了。

**例如：**

背包最大重量为10。

物品为：

|       | 重量 | 价值 | 数量 |
| ----- | ---- | ---- | ---- |
| 物品0 | 1    | 15   | 2    |
| 物品1 | 3    | 20   | 3    |
| 物品2 | 4    | 30   | 2    |

问背包能背的物品最大价值是多少？

和如下情况有区别么？

|       | 重量 | 价值 | 数量 |
| ----- | ---- | ---- | ---- |
| 物品0 | 1    | 15   | 1    |
| 物品0 | 1    | 15   | 1    |
| 物品1 | 3    | 20   | 1    |
| 物品1 | 3    | 20   | 1    |
| 物品1 | 3    | 20   | 1    |
| 物品2 | 4    | 30   | 1    |
| 物品2 | 4    | 30   | 1    |

毫无区别，这就转成了一个01背包问题了，且每个物品只用一次。

这种方式来实现多重背包的代码如下：

```java
// 把多重背包展开————看成特殊的01背包：
public static void testMultiPack1() {
    // 版本一：改变物品数量为01背包格式
    List<Integer> weight = new ArrayList<>(Arrays.asList(1, 3, 4));
    List<Integer> value = new ArrayList<>(Arrays.asList(15, 20, 30));
    List<Integer> count = new ArrayList<>(Arrays.asList(2, 3, 2));
    int bagWeight = 10;

    for (int i = 0; i < count.size(); i++) {
        while (count.get(i) > 1) { // 把物品展开为i
            weight.add(weight.get(i));
            value.add(value.get(i));
            count.set(i, count.get(i) - 1);// 维护count
        }
    }

    int[] dp = new int[bagWeight + 1];
    for (int i = 0; i < weight.size(); i++) { // 遍历物品
        for (int j = bagWeight; j >= weight.get(i); j--) { // 遍历背包容量
            dp[j] = Math.max(dp[j], dp[j - weight.get(i)] + value.get(i));
        }
        System.out.println(Arrays.toString(dp));
    }
}
```

### 2）多重背包：

类似于完全背包个数有限版本：毕竟「完全背包」不限制物品数量，「多重背包」限制物品数量。

```java
// 类似于完全背包个数有限版本：毕竟「完全背包」不限制物品数量，「多重背包」限制物品数量。
public void testMultiPack2(){
    // 版本二：改变遍历个数
    int[] weight = new int[] {1, 3, 4};
    int[] value = new int[] {15, 20, 30};
    int[] count = new int[] {2, 3, 2};
    int bagWeight = 10;

    int[] dp = new int[bagWeight + 1];
    for(int i = 0; i < weight.length; i++) { // 遍历物品
        for(int j = bagWeight; j >= weight[i]; j--) { // 遍历背包容量
            // 以上为01背包，然后加一个遍历个数
            for (int k = 1; k <= count[i] && (j - k * weight[i]) >= 0; k++) { // 遍历个数
                dp[j] = Math.max(dp[j], dp[j - k * weight[i]] + k * value[i]);
            }
            System.out.println(Arrays.toString(dp));
        }
    }
}
```



## 总结：

多重背包在面试中基本不会出现，力扣上也没有对应的题目，大家对多重背包的掌握程度知道它是一种01背包，并能在01背包的基础上写出对应代码就可以了。

从代码里可以看出是01背包里面在加一个for循环遍历一个每种商品的数量。 和01背包还是如出一辙的。当然还有那种二进制优化的方法，其实就是把每种物品的数量，打包成一个个独立的包。和以上在循环遍历上有所不同，因为是分拆为各个包最后可以组成一个完整背包，具体原理我就不做过多解释了，大家了解一下就行，面试的话基本不会考完这个深度了，感兴趣可以自己深入研究一波。





## 参考链接：

代码随想录；

三叶姐公众号；