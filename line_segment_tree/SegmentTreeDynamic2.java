package line_segment_tree;

/**
 * @Description: 线段树（动态开点）纯净版
 * @Author: Ladidol
 * @Date 2022年7月20日14:20:45
 **/
public class SegmentTreeDynamic2 {
    class Node {
        Node left, right;
        int val, lazy;
    }
    private int N = (int) 1e9;
    private Node root = new Node();
    public void update(Node node, int start, int end, int l, int r, int val) {
        if (l <= start && end <= r) {
            node.val += (end - start + 1) * val;//这里根据需求变化 主要是是 自加或者等号或者去掉个数
            node.lazy += val;
            return ;
        }
        int mid = (start + end) >> 1;
        pushDown(node, mid - start + 1, end - mid);
        if (l <= mid) update(node.left, start, mid, l, r, val);
        if (r > mid) update(node.right, mid + 1, end, l, r, val);
        pushUp(node);
    }
    public int query(Node node, int start, int end, int l, int r) {
        if (l <= start && end <= r) return node.val;
        int mid = (start + end) >> 1, ans = 0;
        pushDown(node, mid - start + 1, end - mid);
        if (l <= mid) ans += query(node.left, start, mid, l, r);//这里根据需求变化 主要是是 自加或者等号
        if (r > mid) ans += query(node.right, mid + 1, end, l, r);//有时候是Math.max();
        return ans;
    }
    private void pushUp(Node node) {
        node.val = node.left.val + node.right.val;//有时候是Math.max();
    }
    private void pushDown(Node node, int leftNum, int rightNum) {
        if (node.left == null) node.left = new Node();
        if (node.right == null) node.right = new Node();
        if (node.lazy == 0) return ;
        node.left.val += node.lazy * leftNum;//这里根据需求变化 主要是是 自加或者等号
        node.right.val += node.lazy * rightNum;
        // 对区间进行「加减」的更新操作，下推懒惰标记时需要累加起来，不能直接覆盖
        node.left.lazy += node.lazy;
        node.right.lazy += node.lazy;
        node.lazy = 0;
    }
}