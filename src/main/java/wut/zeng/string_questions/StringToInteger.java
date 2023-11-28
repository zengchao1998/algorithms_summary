package wut.zeng.string_questions;


/**
 * @Author zeng1998
 * @CreateTime 2023-11-24 22:06
 * @Description 字符串转整型
 * "134" 134
 * @RelateMsg 字符串转换
 */
public class StringToInteger {

    public static Integer convert(String str) {
        char[] chars = str.toCharArray();
        boolean isNeg = false;
        int start = 0;

        if(chars[0] == '-') {
            isNeg = true;
            start++;
        }

        int result = 0;
        int index;

        for(int i = start; i < chars.length; i++) {
            index = chars[i] - 'a';
            // 注意越界的情况处理
            if((Integer.MAX_VALUE - index) / 10 < 0) throw new RuntimeException("Convert Error");
            result = result * 10 + index;
        }

        return isNeg ? - result : result;
    }
}
