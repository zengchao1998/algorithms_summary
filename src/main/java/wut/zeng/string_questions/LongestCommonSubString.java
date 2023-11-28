package wut.zeng.string_questions;

import org.junit.platform.commons.util.StringUtils;

/**
 * @Author zeng1998
 * @CreateTime 2023-11-28 13:59
 * @Description 获取两个字符串的最长公共子串问题
 * "abc12kfg3" "hnabc12c93" -> "abc12"
 * @RelateMsg 字符串 + 动态规划
 */
public class LongestCommonSubString {

    /**
     * dp[i][j]: 公共子串必须以str1的i字符结尾，还必须以str2的j字符结尾
     */
    public static String getLongestCommonSubString(String str1, String str2) {
        if(StringUtils.isBlank(str1) || StringUtils.isBlank(str2)) return null;
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int[][] dp = new int[chars1.length][chars2.length];
        int endPos = 0;  // 记录最长公共子串结尾的位置索引
        int maxLen = 0; // 记录最长公共子串的长度
        for(int i = 0; i < chars1.length; i++) {
            for(int j = 0; j < chars2.length; j++) {
                if(chars1[i] == chars2[j]) {
                    dp[i][j] = (i == 0 || j == 0) ? 1 : dp[i - 1][j - 1] + 1;
                    if(dp[i][j] > maxLen) {
                        maxLen = dp[i][j];
                        endPos = i;
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return str1.substring(endPos - maxLen + 1, endPos +1);
    }

    /**
     * 动态规划：空间压缩（二维到有限个变量）
     */
    public static String getLongestCommonSubStringImp(String str1, String str2) {
        if(str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) return "";
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        // 从右上角到左下角处理所有的位置
        int sRow = 0;
        int sCol = chars2.length - 1;
        int maxLen = 0;  // 全局最长公共子串的长度
        int endPos = 0;  // 记录公共最长子串的结尾位置
        while(sRow < chars1.length) {
            // 一个位置完成一条斜线的填写
            int i = sRow;
            int j = sCol;
            int preLen = 0; // 记录前一个位置的结果
            while(i < chars1.length && j < chars2.length) {
                preLen = chars1[i] == chars2[j] ? preLen + 1 : 0;
                if(preLen > maxLen) {
                    endPos = i;
                    maxLen = preLen;
                }
                i++;
                j++;
            }
            // 帮助在等分线的时候完成跳转
            if(sCol > 0) {
                sCol--;
            } else {
                sRow++;
            }
        }
        return str1.substring(endPos - maxLen + 1, endPos + 1);
    }
}
