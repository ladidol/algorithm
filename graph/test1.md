2022年7月31日12:12:27

等一下carl把图论做完



304周赛遇到了两个图论的题，t4尝试用dfs来深搜，emm宣告失败，可以看看周赛写的代码。看答案有个基环外向树的模板。









[自在飞花L6](https://leetcode.cn/u/time-limit/)

3 分钟前

![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207311235053.png)

# 使数组中所有元素都等于零

**关键词**：去重

**时间复杂度**：\mathrel{O}(n)*O*(*n*)

**空间复杂度**：\mathrel{O}(n)*O*(*n*)

## 题目分析

每次操作的关键，是把所有的最小的正整数都变为 0，而其它更大的正整数绝对不会变为 0。

因此，只需统计出有多少个不同的正整数即可。

## 代码实现

```
class Solution {
public:
    int minimumOperations(vector<int>& nums) {
        unordered_set<int> mark;
        for (auto num : nums) {
            num && mark.insert(num).second;
        }
        return mark.size();
    }
};
```

# 分组的最大数量

**关键词**：二分

**时间复杂度**：\mathrel{O}(\lg n)*O*(lg*n*)

**空间复杂度**：\mathrel{O}(n)*O*(*n*)

## 题目分析

假设一共 n*n* 个学生，最优解是分成 x+1*x*+1 个组，那么必然存在一种方案使得每组的人数满足：

1,2,3,...,x,n-\frac{x(x+1)}{2}1,2,3,...,*x*,*n*−2*x*(*x*+1)

且满足 x\lt n-\frac{x(x+1)}{2}*x*<*n*−2*x*(*x*+1)。

这种方案也很好构造——按分数升序排序，然后按照上述人数分组，必然满足两个条件。

又因为题目只要求给出组数，并不需要具体的分组方式，因此可以使用二分在 \mathrel{O}(\lg n)*O*(lg*n*) 的时间复杂度内得出 x*x*。

## 代码实现

```
class Solution {
public:
    int maximumGroups(vector<int>& grades) {
        
        int n = grades.size();
        
        int l = 1, r = n;
        
        while (l <= r) {
            int x = (l+r)>>1;
            
            if (x < n - x*(x+1L)/2) {
                l = x+1;
            } else {
                r = x-1;
            }
        }
        
        return l;
    }
};
```

# 找到离给定两个节点最近的节点

**关键词**：广度优先搜索

**时间复杂度**：\mathrel{O}(v+e)*O*(*v*+*e*)

**空间复杂度**：\mathrel{O}(v)*O*(*v*)

很经典的图的遍历问题咯。使用两次 BFS 分别求出 node1*n**o**d**e*1 和 node2*n**o**d**e*2 到其他所有节点的最短路，分别记为 dis1*d**i**s*1 和 dis2*d**i**s*2。遍历一遍 dis1*d**i**s*1 和 dis2*d**i**s*2 即可得出答案。

```
class Solution {
public:
    void bfs(const vector<int> &edges, int s, vector<int> &dis) {
        dis[s] = 0;
        queue<int> q;
        q.push(s);
        
        while (q.empty() == false) {
            auto f = q.front();
            q.pop();
            
            if (edges[f] == -1) continue;
            int next = edges[f];
            if (dis[next] != -1) continue;
            dis[next] = dis[f] + 1;
            q.push(next);
        }
    }
    int closestMeetingNode(vector<int>& edges, int node1, int node2) {
        vector<int> d1(edges.size(), -1);
        vector<int> d2(edges.size(), -1);
        
        bfs(edges, node1, d1);
        bfs(edges, node2, d2);
        
        int anw = -1;
        int anw_id = -1;
        for (int i = 0; i < edges.size(); i++) {
            if (d1[i] != -1 && d2[i] != -1) {
                if (anw == -1 || max(d1[i], d2[i]) < anw) {
                    anw = max(d1[i], d2[i]);
                    anw_id = i;
                }
            }
        }
        return anw_id;
    }
};
```

# 图中的最长环

**关键词**：广度优先搜索，染色

**时间复杂度**：\mathrel{O}(v+e)*O*(*v*+*e*)

**空间复杂度**：\mathrel{O}(v)*O*(*v*)

## 题目分析

一般找最长环要用 DFS，但这题妙就妙在限制每个点最多有一条边，换言之，每个点最多只会属于一个环。因此，用 BFS 也能轻松解决该题。

首先来定义一种操作——染色：

- 在算法开始时，所有点都未被染色。
- 在图中选择一个未染色的点，设其编号为 p*p*。将 p*p* 与 p*p* 能到达的所有未染色的节点，全部染为颜色 p*p*。

显然，染色过程可以用一次 BFS 处理完成。并且在染色过程中，我们也可得出 p*p* 到其他相同颜色节点的最短距离，记在一维数组 dis*d**i**s* 中。

考虑 BFS 过程中，将点 u*u* 从队首弹出后的处理过程：

- 检查 u*u* 是否存在边 (u,v)(*u*,*v*)。
- 若存在边，检查 v*v* 是否被**相同颜色**了。
- 若 v*v* 被染色了，则必然成环，且环的长度为 dis_u - dis_v + 1*d**i**s**u*−*d**i**s**v*+1。

考虑 dis_u - dis_v + 1*d**i**s**u*−*d**i**s**v*+1 的含义：路径 p → u*p*→*u* 减去 p → v*p*→*v* 再加上节点 v*v*。

![image.png](https://figurebed-ladidol.oss-cn-chengdu.aliyuncs.com/img/202207311235093.png)

## 代码实现

```
class Solution {
public:
    int bfs(const vector<int> &edges, int s, vector<pair<int,int>> &dis) {
        // 已经被染色过了，那就不再重复处理了
        if(dis[s].second != -1) return -1;
        
        // 初始化
        dis[s].first = 0; // 自己到自己的距离肯定是 0
        dis[s].second = s; // 染成颜色 s
        
        // 入队
        queue<int> q;
        q.push(s);
        
        while (q.empty() == false) {
            auto f = q.front();
            q.pop();
            
            // 检查是否有出边
            if (edges[f] == -1) continue;
            int next = edges[f];
            // 检查是否被染色
            if (dis[next].second != -1) {
                // 检查颜色是否一致。
                if (dis[next].second == s) {
                    // 找到环了，直接返回。因为一个点最多在一个环上。
                    return dis[f].first + 1 - dis[next].first;
                }
                continue;
            }
            dis[next].first = dis[f].first + 1;
            dis[next].second = s;
```

