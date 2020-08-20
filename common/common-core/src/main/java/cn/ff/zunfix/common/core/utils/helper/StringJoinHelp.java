package cn.ff.zunfix.common.core.utils.helper;

import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Iterator;

/**
 * 拼接成字符串 的 基础工具类
 *
 * @author fengfan 2020/8/11
 */
public class StringJoinHelp {

    private final String separator;


    public static StringJoinHelp on(String separator) {
        return new StringJoinHelp(separator);
    }

    public static StringJoinHelp on(char separator) {
        return new StringJoinHelp(String.valueOf(separator));
    }

    private StringJoinHelp(String separator) {
        if (separator == null){
            throw new NullPointerException("拼接的分隔符不能为null");
        }
        this.separator = separator;
    }


    public final String join(Iterable<?> parts) {
        try {
            StringBuilder sb = new StringBuilder();
            this.appendTo((Appendable)sb, parts.iterator());
            return sb.toString();
        } catch (IOException var4) {
            throw new AssertionError(var4);
        }
    }

    private  <A extends Appendable> A appendTo(A appendable, Iterator<?> parts) throws IOException {
        Assert.notNull(appendable, "拼接字符串错误！返回结果体不能为空");
        if (parts.hasNext()) {
            appendable.append(this.toString(parts.next()));
            while(parts.hasNext()) {
                appendable.append(this.separator);
                appendable.append(this.toString(parts.next()));
            }
        }
        return appendable;
    }

    private CharSequence toString(Object part) {
        if (part == null){
            return null;
        }
        return (CharSequence)(part instanceof CharSequence ? (CharSequence)part : part.toString());
    }
}
