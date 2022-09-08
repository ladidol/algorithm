## LRU缓存

请你设计并实现一个满足 [LRU (最近最少使用) 缓存](https://baike.baidu.com/item/LRU) 约束的数据结构。

实现 `LRUCache` 类：

- `LRUCache(int capacity)` 以 **正整数** 作为容量 `capacity` 初始化 LRU 缓存
- `int get(int key)` 如果关键字 `key` 存在于缓存中，则返回关键字的值，否则返回 `-1` 。
- `void put(int key, int value)` 如果关键字 `key` 已经存在，则变更其数据值 `value` ；如果不存在，则向缓存中插入该组 `key-value` 。如果插入操作导致关键字数量超过 `capacity` ，则应该 **逐出** 最久未使用的关键字。

函数 `get` 和 `put` 必须以 `O(1)` 的平均时间复杂度运行。

 

**示例：**

```
输入
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
输出
[null, null, null, 1, null, -1, null, -1, 3, 4]

解释
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // 缓存是 {1=1}
lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
lRUCache.get(1);    // 返回 1
lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
lRUCache.get(2);    // 返回 -1 (未找到)
lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
lRUCache.get(1);    // 返回 -1 (未找到)
lRUCache.get(3);    // 返回 3
lRUCache.get(4);    // 返回 4
```





## 基本分析

LRU 是一种十分常见的页面置换算法。

将 LRU 翻译成大白话就是：**当不得不淘汰某些数据时（通常是容量已满），选择最久未被使用的数据进行淘汰。**

**题目让我们实现一个容量固定的 `LRUCache` 。如果插入数据时，发现容器已满时，则先按照 LRU 规则淘汰一个数据，再将新数据插入，其中「插入」和「查询」都算作一次“使用”。**

可以通过 🌰 来理解，假设我们有容量为 22 的 `LRUCache` 和 测试键值对 `[`1-1`,`2-2`,`3-3`]` ，将其按照顺序进行插入 & 查询：

- 插入 `1-1`，此时最新的使用数据为 `1-1`
- 插入 `2-2`，此时最新使用数据变为 `2-2`
- 查询 `1-1`，此时最新使用数据为 `1-1`
- 插入 `3-3`，由于容器已经达到容量，需要先淘汰已有数据才能插入，这时候会淘汰 `2-2`，`3-3` 成为最新使用数据

键值对存储方面，**我们可以使用「哈希表」来确保插入和查询的复杂度为 O(1)。**

另外我们还需要额外维护一个「使用顺序」序列。

我们期望当「新数据被插入」或「发生键值对查询」时，能够将当前键值对放到序列头部，这样当触发 LRU 淘汰时，只需要从序列尾部进行数据删除即可。

**期望在 O(1) 复杂度内调整某个节点在序列中的位置，很自然想到双向链表。**

## 双向链表

具体的，我们使用哈希表来存储「键值对」，键值对的键作为哈希表的 Key，而哈希表的 Value 则使用我们自己封装的 `Node` 类，`Node` 同时作为双向链表的节点。

- 插入：检查当前键值对是否已经存在于哈希表：
  - 如果存在，则更新键值对，并将当前键值对所对应的 `Node` 节点调整到链表头部（`refresh` 操作）
  - 如果不存在，则检查哈希表容量是否已经达到容量：
    - 没达到容量：插入哈希表，并将当前键值对所对应的 `Node` 节点调整到链表头部（`refresh` 操作）
    - 已达到容量：先从链表尾部找到待删除元素进行删除（`delete` 操作），然后再插入哈希表，并将当前键值对所对应的 `Node` 节点调整到链表头部（`refresh` 操作）
- 查询：如果没在哈希表中找到该 Key，直接返回 -1−1；如果存在该 Key，则将对应的值返回，并将当前键值对所对应的 `Node` 节点调整到链表头部（`refresh` 操作）

一些细节：

- 为了减少双向链表左右节点的「判空」操作，我们预先建立两个「哨兵」节点 `head` 和 `tail`。

## 代码：

```java
class LRUCache {

    class Node {
        int k, v;
        Node l, r;

        Node(int key, int value) {
            k = key;
            v = value;
        }
    }

    Map<Integer, Node> map;
    int n;
    // 双向链表
    Node head, tail;


    public LRUCache(int capacity) {
        n = capacity;
        // 双向链表初始化
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.r = tail;//头尾节点还不需要出现在圈子里面。
        tail.l = head;
        map = new HashMap<>();
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            refresh(node);
            return node.v;
        }
        return -1;
    }

    public void put(int key, int value) {// 会有删除尾部的情况。
        Node node = null;
        if (map.containsKey(key)) {
            node = map.get(key);
            node.v = value;
            refresh(node);
            map.put(key, node);
        } else {
            if (map.size() == n) {// 满了先删除。
                Node del = tail.l;
                delete(del);
                map.remove(del.k);// node 中的key派上用场了。
            }
            node = new Node(key, value);
            refresh(node);
            map.put(key, node);
        }
    }

    void refresh(Node node) {//先删除，再加到头部。
        if (node.l!=null) //（这里新节点添加就不会delete了）
            delete(node);
        node.l = head;
        node.r = head.r;
        head.r.l = node;
        head.r = node;
    }

    void delete(Node node) {//这里应该node.l一定不会为空的。
        Node left = node.l;
        left.r = node.r;
        node.r.l = left;
    }
}
```

## 总结：

本题，对LRU算法进行了简单的模拟，同时熟悉了一下双向链表的操作。
