package com.xwq.demo;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xuwenqing
 * Date: 2021-01-26
 * Time: 23:44:26
 */
public class ToPinYin {
    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        String s = "我是中国人，我喜欢听音乐，我说的 very good！";
        for (char ch : s.toCharArray()) {
            String[] r = PinyinHelper.toHanyuPinyinStringArray(ch, format);
            System.out.println(Arrays.toString(r));
        }
    }
}
