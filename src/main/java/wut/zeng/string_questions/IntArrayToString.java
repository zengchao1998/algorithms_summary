package wut.zeng.string_questions;

import org.junit.platform.commons.util.StringUtils;

/**
 * @Author zeng1998
 * @CreateTime 2023-11-24 22:10
 * @Description 由整数构成的序列转换成字符串
 * 1-26 -> "ABC...Z"
 * @RelateMsg 字符串转换 + 递归
 */
public class IntArrayToString {

    /**
     * 题目：从左往右的尝试模型
     * 规定1和A对应，2和B对应，3和C对应 .... 11和K对应
     * 那么一个数字的字符串比如‘111’ ==> 可以转换为 "AAA" "KA"和 “AK"
     * 给定一个只有数字字符组成的字符串str，返回有多少中转化结果
     */
    public static int strConvert(String str) {
        if (StringUtils.isBlank(str)) return 0;
        return process(str.toCharArray(), 0);
    }

    /**
     * i：表示（0 - i-1）已经转换完毕 （i - end）还没有进行转换
     * 1. 递归终止条件：i == chars.length, chars[i] == '0'--无法继续进行
     * 2. 递归返回值：存在多少种转换结果
     * 3. 单层递归操作
     * 3.1 i可以作为单独部分考虑
     * 3.2 i，i+1两个部分一起考虑
     * 3.3 存在一定限制
     */
    public static int process(char[] chars, int i) {
        if (i == chars.length) {
            // 整个字符数组处理完毕，获取到一种转换结果
            return 1;
        }
        // 如果遇到当前i为 '0' 无法转换
        if (chars[i] == '0') return 0;

        // '1' 可以将i作为单独部分，也可以将i，i+1同步考虑
        if (chars[i] == '1') {
            int res = process(chars, i + 1);
            if (i + 1 < chars.length) {
                res += process(chars, i + 2);
            }
            return res;
        }
        // '2' 可以将i作为单独部分，也可以考虑i，i+1，存在一定限制 '20' -'26' 才有效
        if (chars[i] == '2') {
            int res = process(chars, i + 1);
            if (i + 1 < chars.length && chars[i + 1] >= '0' && chars[i + 1] <= 6) {
                res += process(chars, i + 2);
            }
            return res;
        }
        // '3' - '9' 只能将当前i作为单独部分考虑
        return process(chars, i + 1);
    }
}
