package wut.zeng.string_questions;


import java.util.Arrays;

/**
 * @Author zeng1998
 * @CreateTime 2023-12-05 10:34
 * @Description 最长回文子串的长度问题：Manacher算法
 * "abc12321" -> res = 5
 * @RelateMsg
 */
@SuppressWarnings("all")
public class ManacherAlgorithm {

    /**
     * 暴力解法 O(N^2)
     */
    public static int longestPalindromicSubStrVio(String str) {
        // 获取处理串 "abc123"  ==> "#a#b#c#1#2#3#"
        String dStr = getMancherStr(str);
        // 开始计算回文子串
        int len = dStr.toString().length();
        int index = 0;
        int[] res = new int[len];  // 存储最终结果
        int left, right, l = 0;  // 左侧索引、右侧索引、回文子串长度
        while (index < len) {
            left = index;
            right = index;
            while (left >= 0 && right < len) {
                if (dStr.charAt(left) != dStr.charAt(right)) {
                    break;
                }
                // 注意l的更新时机
                l = right - left + 1;
                left--;
                right++;
            }
            res[index++] = l;
        }
        return Arrays.stream(res).max().getAsInt() / 2;
    }

    /**
     * Manacher算法: O(N)
     * 1. 回文半径或回文直径(回文覆盖、回文区域) "12321"  3|5  "123321"  3|6
     * 2. 回文半径数组 pArr[]
     * 3. 回文最右边界 R
     * 4. 中心C (造成R更新的中心点)
     *
     * 某个具体位置 i 计算回文子串:
     * 1) i 在R外 ==> 等同于暴力方法
     * 2) i 在R内(=R) ==> 优化  [L ...i'.....c.....i... R]
     *      (1) 具体位置 i' 为中心的回文子串处于内部 [L..(...i'...)...      ==>  i位置获取的回文区域大小等价于i'位置获取的回文区域大小
     *      (2) 具体位置 i' 为中心的回文子串处于外部 (...[L.....i'.......)  ==>  i位置获取的回文区域的半径大小等于 R-i
     *      (3) 具体位置 i' 为中心的回文子串处于临界位置 [L.....i'.....      ==>  i位置获取的回文区域大小从 R 位置外开始继续扩展对比
     *                                             (.....i'.....)  i位置为中心的回文区域边界与L相同
     */
    public static int manacher(String str) {
        if(str == null || str.length() == 0) return 0;
        // 1. 获取manacher字符串
        String dStr = getMancherStr(str);
        // 处理回文子串问题
        int[] pArr = new int[dStr.length()];  // 记录回文直径值
        int R = -1, C = -1;
        int index = 0, sIndex;
        int left, right, l = 0;
        int tempLeft;
        // 2.获取以index为中心的回文子串的长度(diameter), 并将其记录在pArr中
        while (index < dStr.length()) {
            // case1: index 在 R 外
            if (index > R) {
                left = index;
                right = index;
                // i在R外
                while (left >= 0 && right < dStr.length()) {
                    if (dStr.charAt(left) != dStr.charAt(right)) break;
                    l = right - left + 1;
                    left--;
                    right++;
                }
                pArr[index++] = l;
            } else {  // case2: index 在 R 内
                // 根据C\R计算index的对称位置
                sIndex = 2 * C - index;
                // 获取以sIndex为中心的回文子串的左边界
                tempLeft = sIndex - (pArr[sIndex] / 2);
                int L = 2 * C - R;
                if (tempLeft > L) {
                    pArr[index++] = pArr[sIndex];
                } else if (tempLeft < L) {
                    pArr[index++] = 2 * (R - index) + 1;
                } else {
                    left = 2 * index - R;
                    right = R;
                    while (left >= 0 && right < dStr.length()) {
                        if (dStr.charAt(left) != dStr.charAt(right)) break;
                        l = right - left + 1;
                        left--;
                        right++;
                    }
                    pArr[index++] = l;
                }
                // 如果发现以当前index位置为中心的回文串右边界超过原始R，则更新R\C
                if(index + pArr[index] / 2 > R) {
                    R = index + pArr[index];
                    C = index;
                }
            }
        }
        return Arrays.stream(pArr).max().getAsInt() / 2;
    }

    /**
     * Manacher算法 Coding 优化
     */
    public static int manacherImp(String str) {
        if(str == null || str.length() < 0) return 0;
        String dStr = getMancherStr(str);
        int len = dStr.length();
        int[] pArr = new int[len];  // 记录回文半径值
        int R = -1;  // 回文的最右边界的下一个位置
        int C = -1;
        int index = 0;
        int max = Integer.MIN_VALUE;
        while(index < len) {
            // 以i位置为中心至少不用处理的位置(最小值)
            // 2 * C - index 即为index的对称点位置
            pArr[index] = index < R ? Math.min(pArr[2 * C - index], R - index) : 1;
            // 以i为中心向两侧查看
            // 对于case1、case4 没有影响;对于case2、case3在执行常数次后(也就是上述min函数中两个元素的差值)一定会退出
            while(index + pArr[index] < dStr.length() && index - pArr[index] >= 0) {
                // 如果发现新增的字符对也是相同的，则说明回文串长度增加1
                if(dStr.charAt(index + pArr[index]) == dStr.charAt(index - pArr[index])) {
                    pArr[index]++;
                } else {
                    break;
                }
            }
            // 如果发现以i为中心所获取的回文串的边界超过R,就更新 R\C
            if(index + pArr[index] > R) {
                R = index + pArr[index];  // 因为存放的是半径,所以最终的结果就是回文串的右边界的下一个位置
                C = index;
            }
            max = Math.max(max, pArr[index]);
            index++;
        }
        return max - 1;
    }

    /**
     * 将普通字符串补成manacher算法需要的字符串
     */
    private static String getMancherStr(String str) {
        // 获取处理串 "abc123"  ==> "#a#b#c#1#2#3#"
        StringBuilder dealStr = new StringBuilder();
        dealStr.append("#");
        for (int i = 0; i < str.length(); i++) {
            dealStr.append(str.charAt(i)).append("#");
        }
        return dealStr.toString();
    }
}
