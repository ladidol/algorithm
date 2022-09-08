## Trie树

Trie树（又叫「前缀树」或「字典树」）是一种用于快速查询「某个字符串/字符前缀」是否存在的数据结构。

其核心是**使用「边」来代表有无字符，使用「点」来记录是否为「单词结尾」以及「其后续字符串的字符是什么」。**

![IMG_1659.PNG](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/1618369228-slAfrQ-IMG_1659.PNG)

## 代码实现

### 二维数组：

一个朴素的想法是直接使用「二维数组」来实现 Trie树。

- 使用二维数组 trie[] 来存储我们所有的单词字符。
- 使用 index 来自增记录我们到底用了多少个格子（相当于给被用到格子进行编号）。
- 使用 count[]数组记录某个格子被「被标记为结尾的次数」（当 idx编号的格子被标记了 n次，则有 cnt[idx] = n）。

tips：（因为 OJ每测试一个样例会创建一个 Trie对象）

> 因此还有一个小技巧是将使用到的数组转为静态，然后利用 indexindex 自增的特性在初始化 TrieTrie 时执行清理工作 & 重置逻辑。
>
> 这样的做法能够使评测时间降低一半，运气好的话可以得到一个与「TrieNodeTrieNode」方式差不多的时间。
>



```java
// 二维数组
class Trie {
    // 以下 static 成员独一份，被创建的多个 Trie 共用
    static int N = 100009; // 直接设置为十万级，trie[]来存储我们所有的单词字符
    static int[][] trie = new int[N][26];// 自增记录我们到底用了多少个格子（相当于给被用到格子进行编号）
    static int[] count = new int[N];// 记录某个格子被「被标记为结尾的次数」（当 idx编号的格子被标记了 n次，则有 cnt[idx] = n
    static int index = 0;

    // 在构造方法中完成重置 static 成员数组的操作
    // 这样做的目的是为减少 new 操作（无论有多少测试数据，上述 static 成员只会被 new 一次）
    public Trie() {
        for (int[] row : trie) {
            Arrays.fill(row, 0);
        }
        Arrays.fill(count, 0);
        index = 0;
    }

    public void insert(String word) {
        int p = 0;// 结尾格子
        char[] chars = word.toCharArray();
        for (char c : chars) {
            int u = c - 'a';
            if (trie[p][u] == 0) trie[p][u] = ++index;
            p = trie[p][u];
        }
        count[p]++;
    }

    public boolean search(String word) {
        int p = 0;// 结尾格子
        char[] chars = word.toCharArray();
        for (char c : chars) {
            int u = c - 'a';
            if (trie[p][u] == 0) return false;
            p = trie[p][u];
        }
        return count[p] != 0;
    }

    public boolean startsWith(String prefix) {
        int p = 0;// 结尾格子
        char[] chars = prefix.toCharArray();
        for (char c : chars) {
            int u = c - 'a';
            if (trie[p][u] == 0) return false;
            p = trie[p][u];
        }
        return true;
    }
}
```

- 时间复杂度：Trie树的每次调用时间复杂度取决于入参字符串的长度。复杂度为 O(Len)。
- 空间复杂度：二维数组的高度为 n，字符集大小为 k。复杂度为 O(nk)。

插入boy和app两个单词的局部数组样子。

```java
[4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0]
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
trie.count = [0, 0, 0, 1, 0, 0, 1, 0, 0]
```



### TrieNode：

相比二维数组，更加常规的做法是建立 TrieNode 结构节点。

随着数据的不断插入，根据需要不断创建 TrieNode节点。

```java
// TireNode节点
class Trie {

    // trie树节点
    class TrieNode {
        boolean isWord;
        TrieNode[] next = new TrieNode[26];
    }

    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode cur = root;
        char[] chars = word.toCharArray();
        for (char c : chars) {// 如链表一样一个接一个。
            int idx = c - 'a';
            if (cur.next[idx] == null) cur.next[idx] = new TrieNode();
            cur = cur.next[idx];
        }
        cur.isWord = true;// 更新当前节点可以作为结束。
    }

    public boolean search(String word) {
        TrieNode cur = root;
        char[] chars = word.toCharArray();
        for (char c : chars) {
            int idx = c - 'a';
            if (cur.next[idx] == null) return false;
            cur = cur.next[idx];
        }
        return cur.isWord;// 查看当前节点是不是可以作为结束。
    }

    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        char[] chars = prefix.toCharArray();
        for (char c : chars) {
            int idx = c - 'a';
            if (cur.next[idx] == null) return false;
            cur = cur.next[idx];
        }
        return true;// 只要前面都没有问题，就是prefix。
    }
}
```

- 时间复杂度：Trie树的每次调用时间复杂度取决于入参字符串的长度。复杂度为 O(Len)。
- 空间复杂度：结点数量为 n，字符集大小为 k。复杂度为 O(nk)。

## 总结：

trie树主要就是那三个方法（`insert()，search()，startWith()`），而且是很相似的，容易记住的，对于代码的理解配合上面那张图就十分清晰了。

参考链接：三叶姐题解。

