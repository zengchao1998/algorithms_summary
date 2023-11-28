package wut.zeng.string_questions;

import org.junit.platform.commons.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zeng1998
 * @CreateTime 2023-11-28 13:14
 * @Description
 * 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文
 * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来
 * 返回需要至少多少张贴纸可以完成这个任务?
 * 例子：str= "babac"，arr = {"ba","c","abcd"}
 * ba + ba + c  3 | abcd + abcd 2  | abcd + ba 2
 * 所以返回2
 * @RelateMsg 字符串 + 递归 + 动态规划
 */
public class StringConsistOfStickers {

    public static int getMinimumTags(String tar, String[] tags) {
        if (tags == null || tags.length == 0 || StringUtils.isBlank(tar)) return 0;
        // map 建立贴纸映射关系 (row -- 贴纸格式 col -- 贴纸中字符的对应个数) 词频转换
        int length = tags.length;
        int[][] map = new int[length][26];
        for (int i = 0; i < length; i++) {
            char[] chars = tags[i].toCharArray();
            for (char aChar : chars) {
                map[i][aChar - 'a']++;
            }
        }
        return process(map, tar);
    }

    /**
     * 递归参数
     * 1.终止条件: 剩余拼接字符串为 ""
     * 2.返回值: 方法个数
     * 3.单层递归执行
     */
    public static int process(int[][] map, String rest) {
        if (rest.equals("")) return 0;

        // 初始化结果
        int res = Integer.MAX_VALUE;

        // 1. rest 统计词频
        int[] rMap = new int[26];
        char[] chars = rest.toCharArray();
        for (char aChar : chars) {
            rMap[aChar - 'a']++;
        }

        // 2. 遍历获取贴纸(只有包含剩余rest中任意字符的贴纸，才去尝试)
        for (int i = 0; i < map.length; i++) {

            // 例如: rest = bb, 如果贴纸 i = 0 中，没有 'b' 的词频，则直接跳过，不选择该贴纸
            // 如果所有的贴纸都不包含首字符，则直接结束选择
            if(map[i][chars[0] - 'a'] == 0) {
                continue;
            }
            StringBuilder newRest = new StringBuilder();
            // 查看 rest 中需要的字符
            for (int j = 0; j < 26; j++) {
                if (rMap[j] > 0) {
                    // 计算选择某个i 贴纸之后，还需要的字符统计
                    for (int k = 0; k < Math.max(0, rMap[j] - map[i][j]); k++) {
                        newRest.append((char) ('a' + j));
                    }
                }
            }
            int tRes = process(map, newRest.toString());
            if (tRes != -1) {
                res = Math.min(res, 1 + tRes);
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    /**
     * 暴力递归 --> 记忆化搜索
     */
    public static int getMinimumTagsMemorySearch(String tar, String[] tags) {
        if(tags == null || tags.length == 0 || tar == null || "".equals(tar)) return 0;

        // 统计词频
        int length = tags.length;
        int[][] map = new int[length][26];
        for(int i = 0; i < length; i++) {
            char[] chars = tags[i].toCharArray();
            for (char aChar : chars) {
                map[i][aChar - 'a']++;
            }
        }

        // 提供缓存
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        return processMemorySearch(map, tar, dp);
    }

    public static int processMemorySearch(int[][] map, String rest, Map<String, Integer> dp) {
        // 如果缓存中存在，直接从缓存中获取
        if(dp.containsKey(rest)) {
            return dp.get(rest);
        }

        // 1. 初始化条件 -- 统计 rest 词频
        int res = Integer.MAX_VALUE;
        int[] rMap = new int[26];
        char[] chars = rest.toCharArray();
        for (char aChar : chars) {
            rMap[aChar - 'a']++;
        }

        // 2. 遍历贴纸
        for(int i = 0; i < map.length; i++) {

            if(map[i][chars[0] - 'a'] == 0) {
                continue;
            }

            // 更新 rest
            StringBuilder newRest = new StringBuilder();
            for(int j = 0; j < 26; j++) {
                if(rMap[j] > 0) {
                    for(int k = 0; k < Math.max(0, rMap[j] - map[i][j]); k++) {
                        newRest.append((char)('a' + j));
                    }
                }
            }
            int tRes = processMemorySearch(map, newRest.toString(), dp);
            if(tRes != -1) {
                res = Math.min(res, 1 + tRes);
            }
        }
        dp.put(rest, res == Integer.MAX_VALUE ? -1 : res);
        return dp.get(rest);
    }
}
