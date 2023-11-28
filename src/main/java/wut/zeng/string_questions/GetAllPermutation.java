package wut.zeng.string_questions;

import org.junit.platform.commons.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @Author zeng1998
 * @CreateTime 2023-11-24 22:04
 * @Description 获取字符串的全排列
 * "abc" "abc acb bac bca cab cba"
 * @RelateMsg 字符串 + 递归
 */
public class GetAllPermutation {

    public static List<String> getAllArranges(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        char[] chars = str.toCharArray();
        List<String> result = new ArrayList<>();
        process(chars, 0, result);
        return result;
    }

    /**
     * 递归处理
     * 需要的参数: 原始字符串 chars, 当前的遍历的指针位置 index, 结果集 result
     * (0-index - 1) 处理完毕的字符
     * (index -- chars.length) 未处理的字符
     * 1. 递归的终止：index移动到最后的位置
     * 2. 递归的返回值：无(实际的返回值存放在了path\result中)
     * 3. 单层递归需要进行的操作
     * 3.1 将index后的字符与当前字符进行交换(及确定谁在当前位置)
     * 3.2 继续向后处理
     * 3.3 还原初始状态
     */
    public static void process(char[] chars, int index, List<String> result) {
        if (index == chars.length) {
            result.add(String.valueOf(chars));
            return;
        }
        for (int i = index; i < chars.length; i++) {
            // 确定当前位置
            swap(chars, index, i);
            process(chars, index + 1, result);
            // 还原现场
            swap(chars, index, i);
        }
    }

    /**
     * 题目：打印一个字符串的全排列，要求不重复（分支限界）
     * 加快运算速度
     */
    public static List<String> getAllArrangesImp(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        char[] chars = str.toCharArray();
        List<String> result = new ArrayList<>();
        processImp(chars, 0, result);
        return result;
    }

    /**
     * 递归处理
     * 需要的参数: 原始字符串 chars, 当前的遍历的指针位置 index, 结果集 result
     *      (0-index - 1) 处理完毕的字符
     *      (index -- chars.length) 未处理的字符
     *      1. 递归的终止：index移动到最后的位置
     *      2. 递归的返回值：无(实际的返回值存放在了path\result中)
     *      3. 单层递归需要进行的操作
     *          3.1 将index后的字符与当前字符进行交换(及确定谁在当前位置)
     *          3.2 继续向后处理
     *          3.3 还原初始状态
     *          使用limit记录分支信息，如果存在值则表示，当前index字符开始的分支已经处理完毕
     */
    public static void processImp(char[] chars, int index, List<String> result) {
        if (index == chars.length) {
            result.add(String.valueOf(chars));
            return;
        }
        HashMap<Character, Boolean> limit = new HashMap<>();
        for (int i = index; i < chars.length; i++) {
            // 如果当前字符存在limit中，表示以该字符为开始的分支已经处理过了,就不用在处理了
            if (!limit.containsKey(chars[i])) {
                // 如果没有处理，则添加到到集合中去
                limit.put(chars[i], true);
                // 确定当前位置
                swap(chars, index, i);
                processImp(chars, index + 1, result);
                // 还原现场
                swap(chars, index, i);
            }
        }
    }

    /**
     * 交换两个位置的字符
     */
    public static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
}
