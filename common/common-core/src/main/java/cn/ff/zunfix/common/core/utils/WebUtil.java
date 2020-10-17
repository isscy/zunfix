package cn.ff.zunfix.common.core.utils;

import org.apache.commons.lang3.StringUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Http与Servlet工具类
 *
 * @author fengfan 2019-06-25
 */
public class WebUtil {

    //public static CharsetUtil charsetUtil;
    private final static String staticSuffix = ".css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.htm,.html,.crx,.xpi,.exe,.ipa,.apk,.woff2,.ico,.swf,.ttf,.otf,.svg,.woff";
    /**
     * 静态文件后缀
     */
    private final static String[] staticFiles = StringUtils.split(staticSuffix, ",");

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 客户端返回JSON字符串
     */
    public static void writeJson(HttpServletResponse response, Object object) {
        writeJson(response, Json.toJsonString(object), /*MediaType.APPLICATION_JSON_VALUE*/"application/json", /*HttpStatus.OK.value()*/200);
    }

    public static void writeJson(HttpServletResponse response, Object object, int httpCode) {
        writeJson(response, Json.toJsonString(object), /*MediaType.APPLICATION_JSON_VALUE*/"application/json", httpCode);
    }

    public static void writeJson(HttpServletResponse response, String string, String type, int httpCode) {
        try {
            response.setStatus(httpCode);
            response.setContentType(type);
            response.setCharacterEncoding(CharsetUtil.UTF_8);
            response.getWriter().print(string);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 得到用户的真实地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        final String UNKNOWN = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] ips = ip.split(",");
        return ips[0].trim();
    }

    /**
     * 删除最后为空的元素
     */
    public static LinkedList<String> removeLastEmpty(String[] arr) {
        return removeLastEmpty(Arrays.asList(arr));
    }

    /**
     * 删除最后为空的元素
     */
    public static LinkedList<String> removeLastEmpty(List<String> arr) {
        LinkedList<String> list = new LinkedList<>(arr);
        if (StringUtils.isBlank(list.getLast())) {
            list.removeLast();
        }
        return list;
    }


    /**
     * 从request中获取body
     *
     * @deprecated by ff at 2019-08-23 现在使用<a>getBodyFromRequest</a>
     */
    public static String readBodyFromRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 从request中获取body
     */
    public static String getBodyFromRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = request.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String nowDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public static String randWords(int size) {
        char[] pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuffer sb = new StringBuffer();
        size = size < 1 ? 0 : size;

        for (int i = 0; i < size; ++i) {
            sb.append(pool[(new Random()).nextInt(pool.length)]);
        }

        return sb.toString();
    }

    /**
     * 随机单号
     */
    public static String randOrderId(String orderType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String time = sdf.format(new Date());
        int rad = (int) ((Math.random() * 9 + 1) * 10);
        return orderType + time + rad;
    }


    /**
     * 常用字符集
     */
    public static class CharsetUtil {
        public static final String ISO_8859_1 = "ISO-8859-1";
        public static final String UTF_8 = "UTF-8";
        public static final String GBK = "GBK";

    }

}



