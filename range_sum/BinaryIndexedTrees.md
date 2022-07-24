[307. 区域和检索 - 数组可修改 - 力扣（LeetCode）](https://leetcode.cn/problems/range-sum-query-mutable/comments/)里面的一个树状数组.

希望这两天能把区间求和这些给搞会。

```java
/**
     * 参考：树状数组 思想类似于线段树 和 分块，都旨在均摊复杂度
     * 任意一个正整数都可以表示为 2的幂相加，例如 7 = 2^2 + 2^1 + 2^0
     * 设 sum[x,y] 表示区间 [x,y]的元素和
     * 则 sum[1,7] = sum[1,4] + sum[5,6] + sum[7,7]，也就是说，将 [1,7]的7个元素的和，拆分为 [1,4]的4个元素和、[5,6]的2个元素和、[7,7]的1个元素和
     * 即 7 = 4 + 2 + 1 = 0b100 + 0b110 + 0b111
     * 设下标 x，x的最低位 1 在第 k 位，则 x 对应位置存储 区间[x-2^k+1,x] 共计 2^k 个元素的元素和，例如：
     * 0b100 存储 [0b100-2^2+1, 0b100] => 区间[1,4]的元素和
     * 0b101000 存储 区间[0b101000-2^3+1, 0b101000] => 区间[33,40]的元素和
     */
    class NumArray {

        int[] tree;
        int[] origin;

        public NumArray(int[] nums) {
            tree = new int[nums.length + 1];
            this.origin = nums;
            for (int i = 0; i < nums.length; i++) {
                add(i + 1, nums[i]);
            }
        }

        public void update(int index, int val) {
            add(index + 1, val - origin[index]);
            origin[index] = val;
        }

        public int sumRange(int left, int right) {
            return getRangeSum(right + 1) - getRangeSum(left);
        }

        /**
         * 获取 [1,index] 区间内的元素和，即不断消去最低位1，直到 index 为 0
         * 例如：index = 7，则要将节点 0b111 => 0b110 => 0b100 的值累加
         * 即 sum[1,7] = sum[7,7] + sum[5,6] + sum[1,4]
         */
        private int getRangeSum(int index) {
            int ans = 0;
            while (index > 0) {
                ans += tree[index];
                index -= lowBit(index);
            }
            return ans;
        }

        /**
         * 将一个元素添加到树状数组
         */
        private void add(int index, int val) {
            //添加节点时，需要更新所有包含该节点的区间，即不断 + lowbit，使得最低位1进位，直到到达最大下标
            while (index <= origin.length) {
                tree[index] += val;
                index = index + lowBit(index);
            }
        }

        /**
         * 获取最低位 1 对应的十进制数
         */
        private int lowBit(int x) {
            return x & (-x);
        }
    }
```

