package wut.zeng.segment_tree;

import java.util.*;

/**
 * @Author zeng1998
 * @CreateTime 2023-12-08 10:36
 * @Description 简易俄罗斯方块
 * 题目: 想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
 * 下面是这个游戏的简化版：
 * 1）只会下落正方形积木
 * 2）[a,b] -> 代表一个边长为b的正方形积木，积木左边缘沿着X = a这条线从上方掉落
 * 3）认为整个X轴都可能接住积木，也就是说简化版游戏是没有整体的左右边界的
 * 4）没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满。
 * 给定一个N*2的二维数组matrix，可以代表N个积木依次掉落，
 * 返回每一次掉落之后的最大高度
 * @RelateMsg
 */
public class SimpleTetrisGaming {

    public static List<Integer> getMaxHeight(int[][] tracks) {
        // 1. 数据离散化
        Map<Integer, Integer> discretiz = discretiz(tracks);
        // 2. 线段树初始化
        int size = discretiz.size();
        SegmentTreeRealizedMax segT = new SegmentTreeRealizedMax(size);
        // 3. 计算每一次掉落之后的最大高度
        int max = 0;
        List<Integer> res = new ArrayList<>();
        for (int[] track : tracks) {
            int l = discretiz.get(track[0]);
            int r = discretiz.get(track[0] + track[1] - 1);
            int height = segT.query(l, r, 1, size, 1) + track[1];
            max = Math.max(max, height);
            res.add(max);
            segT.update(l, r, height, 1, size, 1);
        }
        return res;
    }

    /**
     * 假设   [2, 3] x==> [2, 4]
     *       [19, 4] x==> [19,22]
     *       [5, 2] x==> [5, 6]
     *       ...
     *       [2,4,5,6,19,22....]
     * index  1 ...... n
     */
    public static Map<Integer, Integer> discretiz(int[][] tracks) {
        TreeSet<Integer> edges = new TreeSet<>();
        for (int[] track : tracks) {
            edges.add(track[0]);
            edges.add(track[0] + track[1] - 1);
        }
        HashMap<Integer, Integer> res = new HashMap<>();
        int count = 0;
        for (Integer edge : edges) {
            res.put(edge, ++count);
        }
        return res;
    }
}
