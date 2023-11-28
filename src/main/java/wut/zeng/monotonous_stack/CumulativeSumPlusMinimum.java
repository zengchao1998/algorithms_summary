package wut.zeng.monotonous_stack;

/**
 * @Author zeng1998
 * @CreateTime 2023-11-28 20:49
 * @Description 题目: 给定一个只包含正数的数组arr，arr中任何一个子数组sub，
 * 一定都可以算出(sub累加和) * (sub中的最小值)是什么，
 * 那么所有子数组中，这个值最大是多少？(子数组连续)
 * @RelateMsg
 */
public class CumulativeSumPlusMinimum {

    /**
     * 前缀和数组sum[i] --> arr[0...i]位置的累加和
     *                --> arr[L...R]位置的累加和 sum[R] - sum[L-1]
     * 1.从0位置扩张到一个位置，保证该子数组以0为最小值，计算 sum[0....index] * arr[0]
     * 2.从1位置扩张到一个位置，保证该子数组以1为最小值，计算 sum[1.....index'] * arr[1]
     * 3.扩展的终止位置使用单调栈获取：任意位置最右侧比其小的值
     */
    public static int getMaximumValue(int[] arr) {
        // 预处理数组
        int[] preSum = new int[arr.length];
        preSum[0] = arr[0];
        for(int i = 1; i < arr.length; i++) {
            preSum[i] = preSum[i - 1] + arr[i];
        }
        // 获取数组中所有元素右侧离它最近比其小的数值
        int[][] edges = MonotonousStackRealize.getTwoSideNearestNum(arr);
        int res = Integer.MIN_VALUE;
        int help = 0;
        // 遍历处理所有位置,从i位置扩展到rightEdge
        for(int i = 0; i < arr.length; i++) {
            int rightEdge = edges[i][1];
            if(i == 0) {
                help = rightEdge == -1 ? preSum[arr.length - 1] * arr[0] : preSum[rightEdge - 1] * arr[0];
            } else {
                help = rightEdge == -1 ? (preSum[arr.length - 1] - preSum[i - 1]) * arr[i]
                        : (preSum[rightEdge] - preSum[i - 1]) * arr[i];
            }
            res = Math.max(res, help);
        }
        return res;
    }
}
