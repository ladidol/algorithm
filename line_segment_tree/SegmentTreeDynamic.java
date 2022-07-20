package line_segment_tree;

/**
 * @Description: 线段树（动态开点）注释版
 * @Author: Ladidol
 * @Date 2022年7月20日14:20:45
 **/
public class SegmentTreeDynamic {
    class Node {
        Node left, right;
        int val, lazy;
    }

    private int N = (int) 1e9;
    private Node root = new Node();

    //存在区间，现在对区间[l，r]进行更新数据
    public void update(Node node, int start, int end, int l, int r, int val) {
        // 找到满足要求的区间
        if (l <= start && end <= r) {
            // 区间节点加上更新值
            // 注意：需要✖该子树所有叶子节点
            node.val += (end - start + 1) * val;
            // 添加懒惰标记
            // 对区间进行「加减」的更新操作，懒惰标记需要累加，不能直接覆盖
            node.lazy += val;
            return;
        }
        //如果区间不符合要求
        int mid = (start + end) >> 1;
        // 下推标记
        // mid - start + 1：表示左孩子区间叶子节点数量
        // end - mid：表示右孩子区间叶子节点数量
        pushDown(node, mid - start + 1, end - mid);
        // [start, mid] 和 [l, r] 可能有交集，遍历左孩子区间
        if (l <= mid) update(node.left, start, mid, l, r, val);
        // [mid + 1, end] 和 [l, r] 可能有交集，遍历右孩子区间
        if (r > mid) update(node.right, mid + 1, end, l, r, val);
        // 向上更新
        pushUp(node);
    }

    // 在区间 [start, end] 中查询区间 [l, r] 的结果，即 [l ,r] 保持不变
    // 对于上面的例子，应该这样调用该函数：query(root, 0, N, 2, 4)
    public int query(Node node, int start, int end, int l, int r) {
        // 区间 [l ,r] 完全包含区间 [start, end]
        // 例如：[2, 4] = [2, 2] + [3, 4]，当 [start, end] = [2, 2] 或者 [start, end] = [3, 4]，直接返回
        if (l <= start && end <= r) return node.val;
        // 把当前区间 [start, end] 均分得到左右孩子的区间范围
        // node 左孩子区间 [start, mid]
        // node 左孩子区间 [mid + 1, end]
        int mid = (start + end) >> 1, ans = 0;
        // 下推标记
        pushDown(node, mid - start + 1, end - mid);
        // [start, mid] 和 [l, r] 可能有交集，遍历左孩子区间
        if (l <= mid) ans += query(node.left, start, mid, l, r);
        // [mid + 1, end] 和 [l, r] 可能有交集，遍历右孩子区间
        if (r > mid) ans += query(node.right, mid + 1, end, l, r);
        // ans 把左右子树的结果都累加起来了，与树的后续遍历同理
        return ans;
    }

    private void pushUp(Node node) {
        //根据题型转换。
        node.val = node.left.val + node.right.val;
    }

    // leftNum 和 rightNum 表示左右孩子区间的叶子节点数量
    // 因为如果是「加减」更新操作的话，需要用懒惰标记的值✖叶子节点的数量
    private void pushDown(Node node, int leftNum, int rightNum) {
        // 动态开点
        if (node.left == null) node.left = new Node();
        if (node.right == null) node.right = new Node();
        if (node.lazy == 0) return;
        // 注意：当前节点加上标记值✖️该子树所有叶子节点的数量
        node.left.val += node.lazy * leftNum;
        node.right.val += node.lazy * rightNum;
        // 把标记下推给孩子节点
        // 对区间进行「加减」的更新操作，下推懒惰标记时需要累加起来，不能直接覆盖
        node.left.lazy += node.lazy;
        node.right.lazy += node.lazy;
        // 取消当前节点标记
        node.lazy = 0;
    }
}