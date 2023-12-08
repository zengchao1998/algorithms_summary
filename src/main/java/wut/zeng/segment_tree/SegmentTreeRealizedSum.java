package wut.zeng.segment_tree;

/**
 * @Author zeng1998
 * @CreateTime 2023-12-06 10:54
 * @Description 线段树实现：存储子区间的累加和信息
 * @RelateMsg
 */
public class SegmentTreeRealizedSum {

    /**
     * 实际存放数据的数组
     */
    int length;
    int[] arr;

    /**
     * 懒增加机制(用于实现add功能)
     * lazy中的值表示该index对应的范围，其中的所有元素都需要增加value
     */
    int[] lazy;

    /**
     * 懒更新机制(用于实现update功能)
     * update中的值表示该范围的数据需要更新
     * change中的值表示该范围的数据实际需要转换的值
     */
    boolean[] change;
    int[] update;

    /**
     * 存放各个子范围的累加和信息/维护各个子范围的对应关系
     * ==>特指二分子范围
     */
    int[] sum;

    public SegmentTreeRealizedSum(int[] origin) {
        this.length = origin.length + 1;
        this.arr = new int[length];  // 0位置弃用/从1位置开始存储数据
        for (int i = 1; i < length; i++) {
            arr[i] = origin[i - 1];
        }
        this.sum = new int[length << 2];  // 至少需要4*N的空间
        this.lazy = new int[length << 2];
        this.change = new boolean[length << 2];
        this.update = new int[length << 2];
    }

    /**
     * 构建sI位置的数值
     */
    private void pushUp(int sI) {
        sum[sI] = sum[sI << 1] + sum[sI << 1 | 1];
    }

    /**
     * 下发任务: 组织数据的具体实现
     * 注意: 当存在懒更新任务时,懒增加任务失效
     *
     * @param ln/rn 左右子范围的数据个数
     */
    private void pushDown(int sI, int ln, int rn) {
        // 有效的方法调用序列示例: add | update | add | add
        // 如果发现 update[sI] 和 lazy[sI] 上都有有效值,则表明最后一次调用该 pushDown 方法的一定是 add 操作
        // 1. 向左右子范围下发更新任务
        if (change[sI]) {
            change[sI << 1] = true;
            change[sI << 1 | 1] = true;
            update[sI << 1] = update[sI];
            update[sI << 1 | 1] = update[sI];
            lazy[sI << 1] = 0;
            lazy[sI << 1 | 1] = 0;
            sum[sI << 1] = update[sI] * ln;
            sum[sI << 1 | 1] = update[sI] * rn;
            change[sI] = false;
        }
        // 2. 向左右子范围下发增加任务
        if (lazy[sI] != 0) {
            lazy[sI << 1] += lazy[sI];
            sum[sI << 1] += lazy[sI] * ln;
            lazy[sI << 1 | 1] += lazy[sI];
            sum[sI << 1 | 1] += lazy[sI] * rn;
            lazy[sI] = 0;
        }
    }

    /**
     * 根据提供的数组构建sum数组(二分子范围)
     *
     * @param l  左范围
     * @param r  右范围
     * @param sI 范围中的信息对应在sum数组中的实际索引位置(0位置弃用)
     */
    public void build(int l, int r, int sI) {
        if (l == r) {
            sum[sI] = arr[l];
            return;
        }
        int mid = (l + r) >> 1;
        build(l, mid, sI << 1);  // 左子范围的位置 2*i
        build(mid + 1, r, sI << 1 | 1);  // 右子范围信息 2*i + 1
        pushUp(sI);
    }

    /**
     * 将tL...tR范围内的数据添加值V
     *
     * @param tL/tR 实际任务的执行范围
     * @param V     添加的数值
     * @param l/r   当前子范围的边界
     * @param sI    子范围在sum中实际对应的位置
     */
    public void add(int tL, int tR, int V, int l, int r, int sI) {
        // 任务范围覆盖子范围边界(懒添加)
        if (tL <= l && r <= tR) {
            sum[sI] += (r - l + 1) * V;
            lazy[sI] += V;
            return;
        }
        // 任务范围没有覆盖子范围边界
        int mid = (l + r) >> 1;
        // 下发当前任务
        pushDown(sI, mid - l + 1, r - mid);
        // 左子范围需要接收任务
        if (tL <= mid) {
            add(tL, tR, V, l, mid, sI << 1);
        }
        // 右子范围需要接收任务
        if (tR > mid) {
            add(tL, tR, V, mid + 1, r, sI << 1 | 1);
        }
        // 汇总当前的子范围
        pushUp(sI);
    }

    /**
     * 将tL...tR范围内的数据更新为V
     *
     * @param tL/tR 实际任务的执行范围
     * @param V     添加的数值
     * @param l/r   当前子范围的边界
     * @param sI    子范围在sum中实际对应的位置
     */
    public void update(int tL, int tR, int V, int l, int r, int sI) {
        if (tL <= l && r <= tR) {
            change[sI] = true;
            update[sI] = V;
            sum[sI] = (r - l + 1) * V;
            lazy[sI] = 0;
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

    /**
     * 查询tL...tR范围上的累加和
     *
     * @param tL/tR 实际任务的执行范围
     * @param l/r   当前子范围的边界
     * @param sI    子范围在sum中实际对应的位置
     */
    public long query(int tL, int tR, int l, int r, int sI) {
        if (tL <= l && r <= tR) {
            return sum[sI];
        }
        int mid = (l + r) >> 1;
        pushDown(sI, mid - l + 1, r - mid);
        long res = 0;
        if (tL <= mid) {
            res += query(tL, tR, l, mid, sI << 1);
        }
        if (tR > mid) {
            res += query(tL, tR, mid + 1, r, sI << 1 | 1);
        }
        return res;
    }
}
