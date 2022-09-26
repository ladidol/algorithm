## 基本分析

主要参考了宫水三叶的题解：

前两天我们刚讲过 [146. LRU 缓存机制] ，简单理解 LRU 就是「移除最久不被使用的元素」。

因此对于 LRU 我们只需要在使用「哈希表」的同时，维护一个「双向链表」即可：

- 每次发生 `get` 或 `put` 的时候就将元素存放双向链表头部
- 当需要移除元素时，则从双向链表尾部开始移除

LFU 简单理解则是指「**移除使用次数最少的元素**」，如果存在多个使用次数最小的元素，则移除「最近不被使用的那个」（LRU 规则）。同样的 `get` 和 `put` 都算作一次使用。

因此，我们需要记录下每个元素的使用次数，并且在 O(1)*O*(1) 的复杂度内「修改某个元素的使用次数」和「找到使用次数最小的元素」。

------

## 桶排序 + 双向链表

[桶排序——计数排序的升级版](https://www.runoob.com/w3cnote/bucket-sort.html)

我们可以使用「桶排序」的思路，搭配「双向链表」实现 O(1) 操作。

在 `LFUCache` 中，我们维护一个由 `Bucket` 作为节点的双向链表，每个 `Bucket` 都有一个 `idx` 编号，代表当前桶存放的是「使用了多少次」的键值对（`idx = 1` 的桶存放使用一次的键值对；`idx = 2` 的桶存放的是使用两次的键值对 ... ）。

同时 `LFUCache` 持有一个「哈希表」，用来记录哪些 `key` 在哪个桶内。

在 `Bucket` 内部则是维护了一条以 `Item` 作为节点的双向链表，`Item` 是用作存放真实键值对的。

同样的，`Bucket` 也持有一个「哈希表」，用来记录 `key` 与 `Item` 的映射关系。

因此 `LFUCache` 其实是一个「**链表套链表**」的数据结构：

![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/1622691938-yPgKna-image.png)

对应到 `LFUCache` 的几种操作：

- get：先通过 LFUCache持有的哈希表进行查找，如果不存在返回-1，如果存在找到键值对所在的桶cur：
  - 调用对应的 `cur` 的 `remove` 操作，得到键值对对应的 `item`（移除代表当前键值对使用次数加一了，不会在存在于原来的桶中）。
  - 将 `item` 放到 `idx` 为 cur.idx + 1*c**u**r*.*i**d**x*+1 的桶 `target` 中（代表代表当前键值对使用次数加一，应该放到新的目标桶中）。
  - 如果目标桶 `target` 不存在，则创建；如果原来桶 `cur` 移除键值对后为空，则销毁。
  - 更新 `LFUCache` 中哈希表的信息。
- put： 先通过LFUCache持有的哈希表进行查找：
  - 如果存在：找到键值对所在的桶 `cur`，调用 `cur` 的 `put` 操作，更新键值对，然后调用 `LFUCache` 的 `get` 操作实现使用次数加一。
  - 如果不存在：先检查容量是否达到数量：
    - 容量达到数量的话需要调用「编号最小的桶」的 `clear` 操作，在 `clear` 操作内部，会从 `item` 双向链表的尾部开始移除元素。完成后再执行插入操作。
  - 插入操作：将键值对添加到 idx = 1*i**d**x*=1 的桶中（代表当前键值对使用次数为 11），如果桶不存在则创建。

## 代码：

```java
// 这个算是146LRU的升级版了，多了一个指标：对使用次数的计数，在使用次数相同的情况下在判断谁最后使用。
// get：不存在就返回-1，存在就从对应的bucket中取出来item，再添加到对应的新bucket中去；
// put：不存在就就创造一个item存在对应的bucket中去，存在就改变对应item的值（get使用加一）再添加到询bucket中去

class LFUCache {

    class Item {// 内层双链表单位
        Item l, r;
        int k, v;

        public Item(int key, int value) {
            k = key;
            v = value;
        }
    }

    class Bucket {// 外层双链表单位（类比于LRU中的大类）
        Bucket l, r;
        int idx;
        Item head, tail;
        Map<Integer, Item> map = new HashMap();// 用于快速拿到item

        public Bucket(int index) {
            idx = index;
            head = new Item(-1, -1);
            tail = new Item(-1, -1);
            head.r = tail;
            tail.l = head;
        }


        void put(int key, int value) {// √
            // 将对应节点加到该桶的头部
            Item cur = null;
            if (map.containsKey(key)) {
                cur = map.get(key);
                //将它从原来的位置中删除
                // TODO: 2022/9/9 这里可以直接使用remove方法；
                cur.l.r = cur.r;
                cur.r.l = cur.l;

                cur.v = value;//更新值哦；
            } else {
                cur = new Item(key, value);
                map.put(key, cur);
            }
            // 添加到桶的首部；
            cur.l = head;
            cur.r = head.r;
            head.r = cur;
            cur.r.l = cur;

        }


        Item remove(int key) { // √
            // remove是为了移动key，（移动=先从旧桶中删除+再从新桶中添加）
            if (map.containsKey(key)) {
                Item cur = map.get(key);
                // 从双向链表中移除
                Item pre = cur.l;
                pre.r = cur.r;
                cur.r.l = pre;
                // 从哈希表中移除
                map.remove(key);
                return cur;
            }
            return null;
        }

        Item clear() {
            // clear是对空间满的时候进行该桶最后一个元素删除
            Item lastest = tail.l;
            lastest.l.r = tail;
            tail.l = lastest.l;

            map.remove(lastest.k);//维护map大小
            return lastest;//返回被删除的最后一个元素：为了把总map中的删除掉。有点小迷惑。
        }

        boolean isEmpty() {
            return map.isEmpty();
        }
    }


    Map<Integer, Bucket> map = new HashMap<>();// 用来记录哪些 key 在哪个桶内
    Bucket head, tail;
    int n;
    int cnt;


    public LFUCache(int capacity) {
        n = capacity;
        cnt = 0;
        head = new Bucket(-1);
        tail = new Bucket(-1);
        head.r = tail;
        tail.l = head;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Bucket cur = map.get(key);
            Bucket target = null;

            //去拿桶
            if (cur.r.idx != cur.idx + 1) {
                // cur的后面没有目标同的话。
                target = new Bucket(cur.idx + 1);
                target.r = cur.r;
                cur.r.l = target;
                cur.r = target;
                target.l = cur;
            } else {
                target = cur.r;
            }

            //桶间操作
            Item curItem = cur.remove(key);
            target.put(curItem.k, curItem.v);

            // 更新当前键值所在通的信息
            map.put(key, target);

            if (cur.isEmpty()) {//对空桶进行删除操作。
                cur.l.r = cur.r;
                cur.r.l = cur.l;
            }
            return curItem.v;
        }
        return -1;//cache中没有这个东西。
    }

    public void put(int key, int value) {
        // put：不存在就就创造一个item存在对应的bucket中去，存在就改变对应item的值（get使用加一）再添加到询bucket中去
        if (n == 0) return;
        if (map.containsKey(key)) {
            Bucket cur = map.get(key);
            cur.put(key, value);
            get(key);//put使用1等价于get使用1；
        } else {
            // 容器满了就要进行容器操作
            if (cnt == n) {
                // 第一个桶（编号最小的即次数最小的）中去删除尾节点；
                Bucket cur = head.r;
                Item clear = cur.clear();
                map.remove(clear.k);
                cnt--;
                // 每一次删除的话都要判断桶是不是空
                if (cur.isEmpty()) {//对空桶进行删除操作。
                    cur.l.r = cur.r;
                    cur.r.l = cur.l;
                }
            }
            // 这里排查了一个多小时的错误，因为这个创建新的Bucket时，必须是以前没有1Bucket，这段代码，写错地方了；
            Bucket theOneBucket = null;
            // 如果第一个桶不是一号桶的话，就创建一下。
            if (head.r.idx != 1) {
                theOneBucket = new Bucket(1);
                theOneBucket.l = head;
                theOneBucket.r = head.r;
                head.r = theOneBucket;
                theOneBucket.r.l = theOneBucket;
            } else {
                theOneBucket = head.r;
            }
            theOneBucket.put(key, value);
            map.put(key, theOneBucket);
            cnt++;
        }
    }
}
```

- 时间复杂度：各操作均为 O(1)*O*(1)
- 时间复杂度：O(n)*O*(*n*)

真的做吐了这题，代码（创建一号桶的那段代码）写错地方了导致存在一号桶还要再创建一号桶，我debug了一个多小时。