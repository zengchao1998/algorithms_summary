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
    public int nearestToKValue(int[] arr, int k) {
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

    /**
     * 题目: 给定一个数组arr,在给定一个k值，返回累加和小于等于k的最长子数组
     * arr[0.....i......end]
     * 比如说 >= 80
     * 1.能查询到 86 在 i 位置, 子数组的长度为 end - i + 1
     * 2.能查询到 81 在 i + k 位置, 子数组的长度为 end - i - k + 1
     * 说明: 离k最近的不是最长的
     * ===> 思路：help 前缀和数组 | help' 前缀和数组保持升序
     * 1. 使用前缀和数组替换hash表记录相应的前缀和信息
     * 2. 使用前缀和升序数组，存储某个目标累加和最早出现的位置
     * 时间复杂度 O(N*logN)
     */
    public static int longestLengthToKValue(int[] arr, int k) {
        if(arr == null || arr.length == 0) return 0;
        int N = arr.length;
        int[] prefixSum = new int[N];
        int[] prefixIncr = new int[N];
        int tempSum = arr[0];
        int tempMax = tempSum;
        prefixSum[0] = tempSum;
        prefixIncr[0] = tempMax;
        for(int i = 1; i < N; i++) {
            tempSum += arr[i];
            prefixSum[i] = tempSum;
            tempMax = Math.max(tempMax, tempSum);
            prefixIncr[i] = tempMax;
        }
        int res = Integer.MIN_VALUE;
        if(arr[0] <= k) res = 1;
        for(int i = 1; i < N; i++) {
            int index = getTargetIndex(prefixIncr, 0, i, prefixSum[i] - k);
            if(index != -1) {
                res = Math.max(res, i - index + 1);
            }
        }
        return res;
    }

    /**
     * 在 prefixIncr[l...r]范围上定位到第一个大于等于target的值
     */
    public static int getTargetIndex(int[] prefixIncr, int l, int r, int target) {
        int left = l;
        int right = r;
        int index = -1;
        while(left <= right) {
            int mid = (left + right) >> 1;
            if(prefixIncr[mid] >= target) {
                index = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

}
