package wut.zeng.array_questions;

import java.util.TreeSet;

/**
 * @Author zeng1998
 * @CreateTime 2023-12-08 19:36
 * @Description 最长连续子数组问题(目标是 <= target)
 * @RelateMsg
 */
public class LongestCumulativeSumSubArrayImp {

    /**
     * 题目: 给定一个数组arr,在给定一个k值，返回累加和小于等于k，但是离k最近的子数组累加和
     * ====> 解题思路
     * 0....i位置的累计和 = curSum
     * 0...k位置的累加和 >= curSum - k
     * k+1...i位置的累加和 <= k
     * 注意: 如果需要同时获取数组的长度,需要定制类 Info(sum, index)
     */
    public int nearestSumToKValue(int[] arr, int k) {
        if(arr == null || arr.length == 0) return Integer.MAX_VALUE;
        int res = Integer.MAX_VALUE;
        TreeSet<Integer> cache = new TreeSet<>();
        cache.add(0);
        int curSum = 0;
        for(int i = 0; i < arr.length; i++) {
            curSum += arr[i];
            Integer ceiling = cache.ceiling(curSum - k);  // 获取大于等于当前值的最小数值
            if(ceiling != null) {
                res = Math.min(res, curSum - ceiling);
            }
            cache.add(curSum);
        }
        return res;
    }
}
