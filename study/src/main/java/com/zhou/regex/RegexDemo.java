package com.zhou.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ?      匹配零次或一次前面的分组。 相当于{0,1}
 * *      匹配零次或多次前面的分组。相当于{0,}
 * +      匹配一次或多次前面的分组。相当于{1,}
 * {n}    匹配 n 次前面的分组。
 * {n,}   匹配 n 次或更多前面的分组。
 * {,m}   匹配零次到 m 次前面的分组。
 * {n,m}  匹配至少 n 次、至多 m 次前面的分组。默认贪婪模式 后面加?表示非贪婪模式
 * {n,m}? 或 *? 或 +? 对前面的分组进行非贪心匹配。
 * ^spam  意味着字符串必须以 spam 开始。
 * spam$  意味着字符串必须以 spam 结束。
 * [abc]  匹配方括号内的任意字符（诸如 a、 b 或 c）。
 * [^abc] 匹配不在方括号内的任意字符
 *
 *
 * 注意大写为非  如\S 为非\s
 * \d     匹配数字
 * \w     匹配数字字母下划线
 * \s     空格 制表符 换行符
 * .      匹配任意的一个字符（不能匹配换行） 如果要匹配所有字符[\s\S]
 * \n     换行符
 * \t     制表符
 * []     自定义括号内集合中的任意一个字符 ^ 非
 * [^0-9] 0-9以外的字符
 * []中除 \ ^ - 外的特殊字符均失去特殊字符含义  .就只表示小数点
 *
 * 贪婪模式：匹配的越多越好
 * 非贪婪模式：匹配的越少越好
 *
 * 匹配中文 [\u4e00-\u9fa5]
 * 匹配空白行 \n\s*\r
 * 匹配HTML标识符 <(\S*?)[^>]*>.*?</\1>|<.*?/>
 * 匹配首尾空白字符 ^\s*|\s*$
 */
public class RegexDemo {

    @Test
    public void test1(){
        Pattern pattern = Pattern.compile("");//编译正则表达式
        Matcher matcher = pattern.matcher("");//输入匹配字符串
        matcher.matches();//是否匹配

        //查找匹配到的字符串
        while (matcher.find()){//是否包含
            System.out.println(matcher.group());//获取分组值
        }

    }
}
