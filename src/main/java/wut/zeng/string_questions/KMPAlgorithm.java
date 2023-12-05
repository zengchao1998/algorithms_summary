package wut.zeng.string_questions;

import org.junit.platform.commons.util.StringUtils;

/**
 * @Author zeng1998
 * @CreateTime 2023-12-05 09:52
 * @Description KMP 字符串匹配算法
 * @RelateMsg
 */
public class KMPAlgorithm {

    // String 版本
    /**
     * 获取mStr的部分匹配表 (前后缀最大公共元素个数)
     * A                    0
     * AB                   0
     * ABC                  0
     * ABCA                 1
     * ABCAB                2
     * ABCABD               0
     */
    public static int[] getNext(String mStr) {
        if(StringUtils.isBlank(mStr)) return null;

        int[] next = new int[mStr.length()];
        // 当子串的长度为1时,默认为0
        next[0] = 0;
        int i = 1;  // i每次自增表示子串增加一个字符 (子串的结束位置索引)
        int j = 0;  // j代表着前缀字符匹配到的索引位置,j=0表示在str的起始位置
        while(i < mStr.length()) {
            // 当发现某个新增加的i字符和j位置的字符不同 (暗含: 0-(j-1)位置的字符全部匹配成功)
            // 则将j重置到next[j-1]位置,重新和i位置的字符匹配
            // 示例:
            //      ==> ABCAB|A.....ABCAB|C
            //      ==> 已经匹配的部分ABCAB, next中的值为2
            //      ==>       j           i  ==> 不相同
            //      ==>   j                  ==> 相同，跳出逻辑
            while(j > 0 && mStr.charAt(i) != mStr.charAt(j)) {
                j = next[j - 1];
            }
            // 每个i新增的字符与j位置的字符相同,则说明(0-j)中字符匹配成功
            // j++ 前缀字符索引j后移; next[i]的值
            if(mStr.charAt(i) == mStr.charAt(j)) {
                j++;
            }
            // 将结果填入表中
            next[i++] = j;
        }
        return next;
    }

    /**
     * 在str中匹配mStr,如果存在返回在str中的起始索引
     */
    public static int KMP(String str, String mStr, int[] next) {
        if(StringUtils.isBlank(str) || StringUtils.isBlank(mStr) || mStr.length() > str.length()) return -1;
        // i-str起始索引
        // j-match起始索引
        for(int i = 0, j = 0; i < str.length(); i++) {
            // 如果在某个位置发现不匹配,则j跳转到next[j-1]位置继续匹配
            // 如果发现还是不匹配,则继续往前跳转(加速过程)
            // j=0 时,结束循环,表示从match的起始位置开始匹配
            while(j > 0 && str.charAt(i) != mStr.charAt(j)) {
                j = next[j - 1];
            }
            // 如果发现匹配则两者同时加1
            if(str.charAt(i) == mStr.charAt(j)) {
                j++;
            }
            // 如果发现,j索引已经来到了match.length()位置,则表示match匹配成功
            if(j == mStr.length()) {
                return i - j + 1;  // 返回在str中的起始索引位置
            }
        }
        // 匹配整个str之后,还是没有结束函数,则说明str中不包含match,返回-1
        return -1;
    }

    // String[] 数组版本
    public static int[] getNext(String[] mStr) {
        if(mStr == null || mStr.length == 0) return null;
        int[] next = new int[mStr.length];
        // 初始化
        next[0] = 0;
        int i = 1;
        int j = 0;
        while(i < mStr.length) {
            while(j > 0 && !mStr[i].equals(mStr[j])) {
                j = next[j-1];
            }
            if(mStr[i].equals(mStr[j])) {
                j++;
            }
            next[i++] = j;
        }
        return next;
    }

    public static int KMP(String[] str, String[] mStr, int[] next) {
        if(str == null || mStr == null || mStr.length > str.length) return -1;
        for(int i = 0, j = 0; i < str.length; i++) {
            while(j > 0 && !str[i].equals(mStr[j])) {
                j = next[j - 1];
            }
            if(str[i].equals(mStr[j])) j++;
            if(j == mStr.length) return i - j + 1;
        }
        return -1;
    }
}
