package wut.zeng.segment_tree;

/**
 * @Author zeng1998
 * @CreateTime 2023-12-06 10:54
 * @Description 线段树实现：存储子区间的最大值信息
 * @RelateMsg
 */
public class SegmentTreeRealizedMax {

    int length;

    boolean[] change;
    int[] update;

    /**
     * 存放每个子范围的最大值信息
     */
    int[] max;

    public SegmentTreeRealizedMax(int size) {
        this.length = size + 1;
        this.max = new int[length << 2];
        this.change = new boolean[length << 2];
        this.update = new int[length << 2];
    }

    public void pushUp(int sI) {
        max[sI] = Math.max(max[sI << 1], max[sI << 1 | 1]);
    }

    public void pushDown(int sI, int ln, int rn) {
        if (change[sI]) {
            change[sI << 1] = true;
            change[sI << 1 | 1] = true;
            update[sI << 1] = update[sI];
            update[sI << 1 | 1] = update[sI];
            max[sI << 1] = update[sI];
            max[sI << 1 | 1] = update[sI];
            change[sI] = false;
        }
    }

    public void update(int tL, int tR, int V, int l, int r, int sI) {
        if (tL <= l && tR >= r) {
            change[sI] = true;
            update[sI] = V;
            max[sI] = V;
            return;
        }
        int mid = (l + r) >> 1;
        pushDown(sI, mid - l + 1, r - mid);
        if (tL <= mid) {
            update(tL, tR, V, l, mid, sI << 1);
        }
        if (tR > mid) {
            update(tL, tR, V, mid + 1, r, sI << 1 | 1);
        }
        pushUp(sI);
    }

    public int query(int tL, int tR, int l, int r, int sI) {
        if (tL <= l && tR >= r) return max[sI];
        int mid = (l + r) >> 1;
        int left = 0, right = 0;
        pushDown(sI, mid - l + 1, r - mid);
        if (tL <= mid) {
            left = query(tL, tR, l, mid, sI << 1);
        }
        if (tR > mid) {
            right = query(tL, tR, mid + 1, r, sI << 1 | 1);
        }
        return Math.max(left, right);
    }

}
