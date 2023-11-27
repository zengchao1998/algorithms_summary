package wut.zeng.string_questions;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zeng1998
 * @CreateTime 2023-11-24 22:01
 * @Description 获取字符串所有的子序列
 * "abc" "a b c ab ac bc abc"
 * @RelateMsg string combine recursion
 */
public class GetAllSubSequences {

    public static List<String> getSubsequences(String str) {
        char[] chars = str.toCharArray();
        String path = "";
        List<String> result = new ArrayList<>();
        process(chars, 0, result, path);
        return result;
    }

    /**
     * 递归处理
     * 需要的参数: 原始字符串 chars, 当前的遍历的指针位置 index, 结果集 result，子序列 path
     * 1. 递归的终止条件：字符串处理完毕
     * 2. 递归的返回值：无(实际的返回值存放在了path\result中)
     * 3. 单层递归需要进行的操作
     * 3.1 选择要当前的index的字符
     * 3.2 不选择要当前index的字符
     */
    public static void process(char[] chars, int index, List<String> result, String path) {
        if (index == chars.length) {
            result.add(path);
            return;
        }
        // 不选择当前index的字符
        process(chars, index + 1, result, path);
        // 选择当前index的字符
        String yes = path + chars[index];
        process(chars, index + 1, result, yes);
    }
}
