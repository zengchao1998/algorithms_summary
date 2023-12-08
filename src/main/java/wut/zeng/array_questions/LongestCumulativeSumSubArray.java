package wut.zeng.array_questions;

import java.util.HashMap;

/**
 * @Author zeng1998
 * @CreateTime 2023-12-08 18:10
 * @Description 最长连续子数组问题(目标 = target)
 * @RelateMsg
 */
public class LongestCumulativeSumSubArray {

    /**
     * 题目: 给定一个正数arr数组、一个数sum
     * 求 累加和=sum 的子数组(连续)最长有多长
     * 例：arr=[3,2,1,1,1,6,1,1,1,1,1,1] sum=6
     * [3,2,1] [6] [1,1,1,1,1,1] return 6
     * 思路：滑动窗口(范围和累加和是严格单调的)
     */
    public static int inPositiveArray(int[] arr, int target) {
        if (arr == null || arr.length == 0) return 0;
        int l = 0;
        int r = 0;
        int curSum = arr[0];  // [l,r] 滑动窗口有效范围
        int res = 0;
        while (r < arr.length) {
            if (curSum == target) {
                res = Math.max(res, r - l + 1);
                curSum += arr[r++];
            } else if (curSum < target) {
                if (r++ > arr.length) break;
                curSum += arr[r];
            } else {
                curSum -= arr[l++];
            }
        }
        return res;
    }

    /**
     * 题目: 给定一个数组arr,包含正数、零、负数，一个数tarSum
     * 求 累加和=sum 的子数组最大长度
     * 思路: 给定 0-i 位置的累加和为 curSum
     *      定位到 0-a(a<i)位置,其累加和等于 curSum-tarSum
     *      最终的结果为 a+1-->i 子数组的累加和为 tarSum
     */
    public static int inAnyArray(int[] arr, int target) {
        int res = 0;
        int curSum = 0;
        HashMap<Integer, Integer> cache = new HashMap<>();  // key: sum, value: 最早出现的位置
        cache.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            curSum += arr[i];
            if (cache.containsKey(curSum - target)) {
                res = Math.max(res, i - cache.get(curSum - target));
            }
            if (!cache.containsKey(curSum)) {  // 如果存在就不在覆盖(获取最早出现的位置)
                cache.put(curSum, i);
            }
        }
        return res;
    }
}
