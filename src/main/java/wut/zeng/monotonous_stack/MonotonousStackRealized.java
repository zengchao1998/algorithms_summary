package wut.zeng.monotonous_stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author zeng1998
 * @CreateTime 2023-11-28 20:42
 * @Description 单调栈的具体实现
 * 定义: 获取数组中每个数 左边离它最近的比它小的数 右边比它小离它最近的数
 * @RelateMsg
 */
public class MonotonousStackRealized {

    /**
     * 题目: 单调栈的实现(无重复值实现)
     * [[left0, right0],[left1, right1]......]
     */
    public static int[][] getTwoSideNearestNumNoRepeat(int[] arr) {
        int[][] res = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            // 栈不为空
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                Integer proNum = stack.pop();
                int leftNearIndex = stack.isEmpty() ? -1 : stack.peek();
                res[proNum][0] = leftNearIndex;
                res[proNum][1] = i;
            }
            // 栈为空
            stack.add(i);
        }
        while (!stack.isEmpty()) {
            Integer surNum = stack.pop();
            int leftNearIndex = stack.isEmpty() ? -1 : stack.peek();
            res[surNum][0] = leftNearIndex;
            res[surNum][1] = -1;
        }
        return res;
    }

    /**
     * 题目: 单调栈的实现(有重复值实现)
     * [[left0, right0],[left1, right1]......]
     */
    public static int[][] getTwoSideNearestNum(int[] arr) {
        int[][] res = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();  // 从小到大 ==> 两侧最近的小值
        // 遍历处理数组的数据
        for (int i = 0; i < arr.length; i++) {
            // 如果当前栈不为空
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> proNums = stack.pop();
                // 填充答案
                // 左侧的最近的小值(栈空 ? -1:元素集合中最后一个元素的值)
                int leftNearIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer num : proNums) {
                    res[num][0] = leftNearIndex;
                    res[num][1] = i;
                }
            }
            // 如果当前栈不为空 且当前栈顶的元素与i位置的数大小相同，添加到list的最后
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(i);
            } else {
                // 如果当前栈为空或者新添加的元素大于栈顶元素
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                stack.add(list);
            }
        }
        // 处理栈中剩余的元素
        while (!stack.isEmpty()) {
            List<Integer> surNums = stack.pop();
            int leftNearIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer num : surNums) {
                res[num][0] = leftNearIndex;
                res[num][1] = -1;
            }
        }
        return res;
    }
}
