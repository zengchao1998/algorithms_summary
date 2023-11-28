package wut.zeng.string_questions;

import org.junit.platform.commons.util.StringUtils;

/**
 * @Author zeng1998
 * @CreateTime 2023-11-28 12:45
 * @Description 获取两个字符串的最长公共子序列问题
 * "abc12uhk" "abcjhh12" -> "abc12" 5
 * @RelateMsg 字符串 + 动态规划(多样本位置对应模型)
 */
public class LongestCommonSubSequence {


    /**
     * dp[i][j]: char1 [0...i] 和 char2 [0....j] 的公共子序列大小
     */
    public static int getLongestCommonSubSequence(String str1, String str2) {
        if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2)) return 0;
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int[][] dp = new int[chars1.length][chars2.length];
        // 初始化
        for (int i = 0; i < chars1.length; i++) {
            dp[i][0] = chars1[i] == chars2[0] ? 1 : 0;
        }
        for (int i = 0; i < chars2.length; i++) {
            dp[0][i] = chars2[i] == chars1[0] ? 1 : 0;
        }
        // 处理常规位置
        for (int i = 1; i < chars1.length; i++) {
            for (int j = 1; j < chars2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (chars1[i] == chars2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[chars1.length - 1][chars2.length - 1];
    }

    /**
     * 空间压缩：二维到一维
     * dp[i]: chars1[0...?] 和 char2 [0....i] 的公共子序列大小 (不断改变)
     */
    public static int getLongestCommonSubSequenceImp(String str1, String str2) {
        if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2)) return 0;
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int[] dp = new int[chars2.length];
        // 初始化
        dp[0] = chars1[0] == chars2[0] ? 1 : 0;
        for (int i = 1; i < chars2.length; i++) {
            dp[i] = chars1[0] == chars2[i] ? 1 : dp[i - 1];
        }
        // 处理常规位置
        int res = 0;
        int pre = 0;
        for (int i = 1; i < chars1.length; i++) {
            for (int j = 0; j < chars2.length; j++) {
                if (j == 0) {
                    res = chars1[i] == chars2[0] ? 1 : dp[j];
                } else {
                    res = Math.max(Math.max(dp[j], dp[j - 1]), chars1[i] == chars2[j] ? pre + 1 : pre);
                }
                pre = dp[j];
                dp[j] = res;
            }
        }
        return dp[chars2.length - 1];
    }
}
