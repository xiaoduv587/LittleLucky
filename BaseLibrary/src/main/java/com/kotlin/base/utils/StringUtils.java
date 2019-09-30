package com.kotlin.base.utils;

import android.annotation.SuppressLint;
import android.preference.Preference;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final char CHAR_CHINESE_SPACE = '\u3000';// 中文（全角）空格

    private StringUtils() {
        throw new AssertionError();
    }

    /**
     * is null or its length is 0 or it is made by space
     * <p/>
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return
     * true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * <p/>
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return
     * false.
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断字符串是否相同
     *
     * @param actual
     * @param expected
     * @return
     */
    public static boolean isequals(CharSequence actual, CharSequence expected) {
        if (actual != null && expected != null) {
            if (TextUtils.equals(actual, expected)) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }

    /**
     * get length of CharSequence
     * <p/>
     * <pre>
     * length(null) = 0;
     * length(\"\") = 0;
     * length(\"abc\") = 3;
     * </pre>
     *
     * @param str
     * @return if str is null or empty, return 0, else return
     * {@link CharSequence#length()}.
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * null Object to empty string
     * <p/>
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param str
     * @return
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str
                .toString()));
    }

    /**
     * capitalize first letter
     * <p/>
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
                : new StringBuilder(str.length())
                .append(Character.toUpperCase(c))
                .append(str.substring(1)).toString();
    }

    /**
     * encoded in utf-8
     * <p/>
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * </pre>
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     *
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * get innerHtml from href
     * <p/>
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     *
     * @param href
     * @return <ul>
     * <li>if href is null, return ""</li>
     * <li>if not match regx, return source</li>
     * <li>return the last string that match regx</li>
     * </ul>
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern
                .compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    /**
     * transform half width char to full width char
     * <p/>
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth(&quot;&quot;) = &quot;&quot;;
     * fullWidthToHalfWidth(new String(new char[] { 12288 })) = &quot; &quot;;
     * </pre>
     *
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * transform full width char to half width char
     * <p/>
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth(&quot;&quot;) = &quot;&quot;;
     * halfWidthToFullWidth(&quot; &quot;) = new String(new char[] { 12288 });
     * </pre>
     *
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 判断字符串是否为手机号码</br> 只能判断是否为大陆的手机号码
     *
     * @param str
     * @return
     */
    public static boolean checkMobile(String str) {
        Pattern p = Pattern.compile("1[34578][0-9]{9}");
        Matcher m = p.matcher(str);
        boolean isMatched = m.matches();
        if (isMatched) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证email的合法性
     *
     * @param emailStr
     * @return
     */
    public static boolean checkEmail(String emailStr) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(emailStr.trim());
        boolean isMatched = matcher.matches();
        if (isMatched) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对字符串进行MD5加密</br> 如果返回为空，则表示加密失败
     *
     * @param s
     * @return
     */
    public static String md5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                // 将每个数(int)b进行双字节加密
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 去掉字符串的空格
     *
     * @param input
     * @return
     */
    public static String trim(String input) {
        if (input == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(input.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 从字符串s中截取某一段字符串
     *
     * @param s
     * @param startToken 开始标记
     * @param endToken   结束标记
     * @return
     */
    public static String mid(String s, String startToken, String endToken) {
        return mid(s, startToken, endToken, 0);
    }

    /**
     * 截取字符串后sub位
     *
     * @param s
     * @param sub 截取多少位
     * @return
     */
    public static String mid(String s, int sub) {
        return s.substring(s.length() - sub, s.length());
    }

    public static String mid(String s, String startToken, String endToken,
                             int fromStart) {
        if (startToken == null || endToken == null)
            return null;
        int start = s.indexOf(startToken, fromStart);
        if (start == (-1))
            return null;
        int end = s.indexOf(endToken, start + startToken.length());
        if (end == (-1))
            return null;
        String sub = s.substring(start + startToken.length(), end);
        return sub.trim();
    }

    /**
     * 简化字符串，通过删除空格键、tab键、换行键等实现
     *
     * @param s
     * @return
     */
    public static String compact(String s) {
        char[] cs = new char[s.length()];
        int len = 0;
        for (int n = 0; n < cs.length; n++) {
            char c = s.charAt(n);
            if (c == ' ' || c == '\t' || c == '\r' || c == '\n'
                    || c == CHAR_CHINESE_SPACE)
                continue;
            cs[len] = c;
            len++;
        }
        return new String(cs, 0, len);
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }


    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.
     * </p>
     * <p/>
     * <pre>
     * StringHelper.isBlank(null)      = true
     * StringHelper.isBlank("")        = true
     * StringHelper.isBlank(" ")       = true
     * StringHelper.isBlank("bob")     = false
     * StringHelper.isBlank("  bob  ") = false
     * </pre>
     *
     * @param obj the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     */
    public static boolean isBlank(Object obj) {
        if (null == obj) {
            return true;
        }
        String str = obj.toString();
        int strLen;
        if ((strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.
     * </p>
     * <p/>
     * <pre>
     * StringHelper.isNotBlank(null)      = false
     * StringHelper.isNotBlank("")        = false
     * StringHelper.isNotBlank(" ")       = false
     * StringHelper.isNotBlank("bob")     = true
     * StringHelper.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param obj the String to check, may be not null
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        return !StringUtils.isBlank(obj);
    }

    public static boolean isNotBlank(Object... objs) {
        if (objs == null || objs.length == 0) {
            return false;
        }

        for (Object obj : objs) {
            if (isNotBlank(obj)) {
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * 如果str字符串为null,返回为"";如果字符串不为空,返回原来的字符串
     *
     * @param str
     * @return
     */
    public static String nullToBlank(String str) {
        return (str == null ? "" : str);
    }

    /**
     * 求两个字符串数组的并集，利用set的元素唯一性
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] union(String[] arr1, String[] arr2) {
        Set<String> set = new HashSet<String>();
        for (String str : arr1) {
            set.add(str);
        }
        for (String str : arr2) {
            set.add(str);
        }
        String[] result = {};
        return set.toArray(result);
    }

    /**
     * 求两个字符串数组的交集
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] intersect(String[] arr1, String[] arr2) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        LinkedList<String> list = new LinkedList<String>();
        for (String str : arr1) {
            if (!map.containsKey(str)) {
                map.put(str, Boolean.FALSE);
            }
        }

        for (String str : arr2) {
            if (map.containsKey(str)) {
                map.put(str, Boolean.TRUE);
            }
        }

        for (Entry<String, Boolean> e : map.entrySet()) {
            if (e.getValue().equals(Boolean.TRUE)) {
                list.add(e.getKey());
            }
        }

        String[] result = {};
        return list.toArray(result);
    }

    /**
     * 求两个字符串数组的差集
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] minus(String[] arr1, String[] arr2) {
        LinkedList<String> list = new LinkedList<String>();
        LinkedList<String> history = new LinkedList<String>();
        String[] longerArr = arr1;
        String[] shorterArr = arr2;
        // 找出较长的数组来减较短的数组
        if (arr1.length > arr2.length) {
            longerArr = arr2;
            shorterArr = arr1;
        }
        for (String str : longerArr) {
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : shorterArr) {
            if (list.contains(str)) {
                history.add(str);
                list.remove(str);
            } else {
                if (!history.contains(str)) {
                    list.add(str);
                }
            }
        }

        String[] result = {};
        return list.toArray(result);
    }

    /**
     * 字符串反转
     *
     * @param str
     * @return
     */
    public static String reverse(String str) {
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 字符串数组反转
     *
     * @param strs
     * @return
     */
    public static String[] reverse(String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            String top = strs[0];
            for (int j = 1; j < strs.length - i; j++) {
                strs[j - 1] = strs[j];
            }
            strs[strs.length - i - 1] = top;
        }
        return strs;
    }

    /**
     * html的转义字符转换成正常的字符串
     *
     * @param html
     * @return
     */
    public static String htmlEscapeCharsToString(String html) {
        if (isEmpty(html)) {
            return html;
        } else {
            return html.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                    .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
        }
    }

    /**
     * 判断字符串是否为数字,可以判断正数、负数、int、float、double
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isNumber(String str) {
        if (isLong(str)) {
            return true;
        }
        Pattern pattern = Pattern.compile("(-)?(\\d*)\\.{0,1}(\\d*)");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isLong(String str) {
        if ("0".equals(str.trim())) {
            return true;
        }
        Pattern pattern = Pattern.compile("^[^0]\\d*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(String str) {
        if (isLong(str)) {
            return true;
        }
        Pattern pattern = Pattern.compile("\\d*\\.{1}\\d+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 验证身份证号是否正确
     *
     * @return
     */
    public static boolean isIDNumber(String idnumber) {
        if (isEmpty(idnumber)) {
            return false;
        }
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        Matcher idNumMatcher = idNumPattern.matcher(idnumber);
        if (idNumMatcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 手机号替换，保留前三位和后三位
     * <p/>
     * 如果手机号号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     *
     * @param Phone 手机号号
     * @return
     */
    public static String idPhoneReplaceWithStar(String Phone) {

        if (Phone.equals("") || Phone == null) {
            return null;
        } else {
            return replaceAction(Phone, "(?<=\\d{3})\\d(?=\\d{3})");
        }

    }

    /**
     * 实际替换动作
     *
     * @param username username
     * @param regular  正则
     * @return
     */
    private static String replaceAction(String username, String regular) {
        return username.replaceAll(regular, "*");
    }


    /**
     * 判断是否为密码8-16位数字、字母,特殊符号
     * 三者都含
     * (?=.*[a-z])(?=.*\d)(?=.*[#@!~%^&*])[a-z\d#@!~%^&*]{8,16}
     * 三者不都含
     * ((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!_~%^&*])|(?=.*\d)(?=.*[#@!_~%^&*]))[a-z\d#@!_~%^&*]{8,16}
     */
    public static boolean checkPassWord(String password) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("((?=.*[a-z])(?=.*\\d)|(?=[a-z])(?=.*[#@!_~%^&*])|(?=.*\\d)(?=.*[#@!_~%^&*]))[a-z\\d#@!_~%^&*]{8,16}");
            Matcher matcher = regex.matcher(password);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 判断是否符合 6 -20 位字母数字组合
     * 三者都含
     * (?=.*[a-z])(?=.*\d)(?=.*[#@!~%^&*])[a-z\d#@!~%^&*]{8,16}
     * 三者不都含
     * ((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!_~%^&*])|(?=.*\d)(?=.*[#@!_~%^&*]))[a-z\d#@!_~%^&*]{8,16}
     */
    public static boolean checkRegisterStatus(String string) {
        boolean flag = false;
        try {
            //            Pattern regex = Pattern.compile("((?=.*[a-z])(?=.*\\d)|(?=[a-z])(?=.*[#@!_~%^&*])|(?=.*\\d)(?=.*[#@!_~%^&*]))[a-z\\d#@!_~%^&*]{8,16}");
            Pattern regex = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
            //            Pattern regex = Pattern.compile("^[0-9a-zA-Z]{6,20}$");
            Matcher matcher = regex.matcher(string);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 正则验证注册时用户名是否正确
     *
     * @param string
     * @return
     */
    public static boolean checkRegisterName(String string) {
        boolean flag = false;
        try {
            //            Pattern regex = Pattern.compile("((?=.*[a-z])(?=.*\\d)|(?=[a-z])(?=.*[#@!_~%^&*])|(?=.*\\d)(?=.*[#@!_~%^&*]))[a-z\\d#@!_~%^&*]{8,16}");
            //            Pattern regex = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
            Pattern regex = Pattern.compile("^[0-9a-zA-Z]{6,20}$");
            Matcher matcher = regex.matcher(string);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    /**
     * 处理空字符串
     *
     * @param str
     * @return String
     */
    public static String doEmpty(String str) {
        return doEmpty(str, "");
    }

    /**
     * 处理空字符串
     *
     * @param str
     * @param defaultValue
     * @return String
     */
    public static String doEmpty(String str, String defaultValue) {
        if (str == null || str.equalsIgnoreCase("null")
                || str.trim().equals("") || str.trim().equals("－请选择－")) {
            str = defaultValue;
        } else if (str.startsWith("null")) {
            str = str.substring(4, str.length());
        }
        return str.trim();
    }

    /**
     * 请选择
     */
    final static String PLEASE_SELECT = "请选择...";

    public static boolean notEmpty(Object o) {
        return o != null && !"".equals(o.toString().trim())
                && !"null".equalsIgnoreCase(o.toString().trim())
                && !"undefined".equalsIgnoreCase(o.toString().trim())
                && !PLEASE_SELECT.equals(o.toString().trim());
    }

    public static boolean empty(Object o) {
        return o == null || "".equals(o.toString().trim())
                || "null".equalsIgnoreCase(o.toString().trim())
                || "undefined".equalsIgnoreCase(o.toString().trim())
                || PLEASE_SELECT.equals(o.toString().trim());
    }

    public static boolean num(Object o) {
        int n = 0;
        try {
            n = Integer.parseInt(o.toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean decimal(Object o) {
        double n = 0;
        try {
            n = Double.parseDouble(o.toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (n > 0.0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 给JID返回用户名
     *
     * @param Jid
     * @return
     */
    public static String getUserNameByJid(String Jid) {
        if (empty(Jid)) {
            return null;
        }
        if (!Jid.contains("@")) {
            return Jid;
        }
        return Jid.split("@")[0];
    }

    /**
     * 给用户名返回JID
     *
     * @param jidFor   域名//如ahic.com.cn
     * @param userName
     * @return
     */
    public static String getJidByName(String userName, String jidFor) {
        if (empty(jidFor) || empty(jidFor)) {
            return null;
        }
        return userName + "@" + jidFor;
    }

    /**
     * 给用户名返回JID,使用默认域名ahic.com.cn
     *
     * @param userName
     * @return
     */
    public static String getJidByName(String userName) {
        String jidFor = "ahic.com.cn";
        return getJidByName(userName, jidFor);
    }

    /**
     * 根据给定的时间字符串，返回月 日 时 分 秒
     *
     * @param allDate like "yyyy-MM-dd hh:mm:ss SSS"
     * @return
     */
    public static String getMonthTomTime(String allDate) {
        return allDate.substring(5, 19);
    }

    /**
     * 根据给定的时间字符串，返回月 日 时 分 月到分钟
     *
     * @param allDate like "yyyy-MM-dd hh:mm:ss SSS"
     * @return
     */
    public static String getMonthTime(String allDate) {
        return allDate.substring(5, 16);
    }


    /**
     * 过滤字符串中的表情
     */
    public static void filterEmojiCharacter(final EditText editText) {

        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int index = start; index < end; index++) {

                    int type = Character.getType(source.charAt(index));

                    if (type == Character.SURROGATE) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 根据路径获取文件扩展名
     *
     * @param path
     */
    public static String getExtensionByPath(String path) {
        if (path != null) {
            return path.substring(path.lastIndexOf(".") + 1);
        }
        return null;
    }


    /**
     * 检验 oem 输入是否合法 false 不合法
     *
     * @param oem
     * @return
     */
    public static boolean checkOEM(String oem) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("[\\u4E00-\\u9FA5]");
            Matcher matcher = regex.matcher(oem);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    /**
     * 拼接文件名称
     *
     * @param fileNames
     * @return
     */
    public static String jionFileName(List<String> fileNames) {
        if (fileNames == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int size = fileNames.size();
        for (int i = 0; i < size; i++) {
            sb.append(fileNames.get(i));
            if (i != size - 1) {
                sb.append("##");
            }
        }
        String string = sb.toString();

        return string;
    }

    /**
     * 拼接文件名称
     *
     * @param strs 目标字符串集合
     * @param str  拼接字符串标识符
     * @return
     */

    public static String jionFileName(List<String> strs, String str) {
        if (strs == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int size = strs.size();
        for (int i = 0; i < size; i++) {
            sb.append(strs.get(i));
            if (i != size - 1) {
                sb.append(str);
            }
        }
        String string = sb.toString();

        return string;
    }

    /**
     * 切分文件名称（##）
     *
     * @param fileNames
     * @return
     */

    public static List<String> splitFileName(String fileNames) {
        if (TextUtils.isEmpty(fileNames)) {
            return null;
        }
        ArrayList<String> arrayList = null;
        String[] split = fileNames.split("##");
        for (int i = 0; i < split.length; i++) {
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            arrayList.add(split[i]);
        }
        return arrayList;
    }

    /**
     * 切分文件名称
     *
     * @param str 目标字符串
     * @param tag 切分字符串标识符
     * @return
     */


    public static List<String> splitFileName(String str, String tag) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList<String> arrayList = null;
        String[] split = str.split(tag);
        for (int i = 0; i < split.length; i++) {
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            arrayList.add(split[i]);
        }
        return arrayList;
    }

    /**
     * 将时间转化为字符串
     *
     * @param date date数据
     * @param str  转换的格式
     * @return 转换后的字符串
     */
    public static String getTime(Date date, String str) {
        SimpleDateFormat format = new SimpleDateFormat(str);
        return format.format(date);
    }

    /**
     * 将时间转化为毫秒
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hours  时
     * @param minute 分
     * @return 当前时间的毫秒值
     */

    public static long timeChangeMillimetre(String year, String month, String day, String hours, String minute) {
        if (month.length() < 2)
            month = "0" + month;
        if (day.length() < 2)
            day = "0" + day;
        if (hours.length() < 2)
            hours = "0" + hours;
        if (minute.length() < 2)
            minute = "0" + minute;
        long i = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
        try {
            i = sdf.parse(year + month + day + hours + minute).getTime();//毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 获得当前的时间戳，带时分秒  年-月-日 时-分-秒
     *
     * @return
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                format(new Date(System.currentTimeMillis()));
    }

    /**
     * 获得当前的时间戳 年-月-日
     *
     * @return
     */
    public static String getCurrentDay() {
        return new SimpleDateFormat("yyyy-MM-dd").
                format(new Date(System.currentTimeMillis()));
    }


    /**
     * 获得当前的年份
     *
     * @return
     */
    public static String getCurrentYear() {
        return new SimpleDateFormat("yyyy").
                format(new Date(System.currentTimeMillis()));
    }

    /**
     * 获得当前的月份
     *
     * @return
     */
    public static String getCurrentMonth() {
        return new SimpleDateFormat("MM").
                format(new Date(System.currentTimeMillis()));
    }

    /**
     * 获得当前的天
     *
     * @return
     */
    public static String getCurrenDay() {
        return new SimpleDateFormat("dd").
                format(new Date(System.currentTimeMillis()));
    }

    /**
     * 获得当前的时间戳，带天时分  天-时-分
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDayAndHourAndSecond(Long currentTime) {
        return new SimpleDateFormat("dd天HH时mm分").
                format(new Date(currentTime));
    }


    /**
     * 是否包含中文
     *
     * @param str 判定字符串
     * @return true包含 false 不包含
     */
    public static boolean isContainChinese(String str) {
        return Pattern.compile("[\\u4e00-\\u9fa5]").matcher(str).find();
    }

    /**
     * 每5个字中间加一空格
     */
    public static String cutSpacing(String str) {
        String regex = "(.{5})";
        return str.replaceAll(regex, "$1  ");
    }


    /**
     * 判断字符串是否为null并且是空串
     *
     * @param str
     * @return
     */
    public static boolean isStringNull(String str) {
        return null == str || "".equals(str);
    }

    /**
     * 判断字符串是否不为null并且不是空串
     *
     * @param str
     * @return
     */
    public static boolean isStringNotNull(String str) {
        return null != str && !"".equals(str);
    }


    /**
     * 判断集合是否为null并且不是空串
     *
     * @param str
     * @return
     */
    public static boolean isListEmpty(List<?> str) {
        return null != str && str.size() > 0;
    }

    /**
     * 判断是否是否是商品条码
     * 如果是，加一个0
     *
     * @return
     */
    public static String isGtinCode(String gTinCode) {

        if (gTinCode.startsWith("69") && gTinCode.length() == 13) {
            return "0" + gTinCode;
        }
        return gTinCode;
    }

    /**
     * 判断是否是否是商品条码,如果是带0的，去掉
     *
     * @return
     */
    public static String delGtinCode(String gTinCode) {
        if (gTinCode.startsWith("069") && gTinCode.length() == 14) {
            return gTinCode.substring(1);
        }
        return gTinCode;
    }

    /**
     * 验证是否包含字母
     *
     * @param
     * @return
     */
    public static boolean isLetter(String str) {
        if (isEmpty(str)) {
            str = "";
        }
        String check = ".*[a-zA-Z]+.*";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 集合转字符串
     *
     * @param list
     * @param separator
     * @return
     */
    public static String listToString(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * 去掉名字后4位
     *
     * @return
     */
    public static String cutUserNameLast4(String userName) {
        if (userName != null) {
            int length = userName.length();
            if (length > 4) {
                String substring = userName.substring(length - 4);
                if (isNumber(substring)) {
                    return userName.substring(0, userName.length() - 4);
                }
            }
            return userName;
        }
        return "";

    }

    public static void main(String[] args) {
        String str = "杜志斌9999";
        int length = str.length();
        if (length > 4) {
            String substring = str.substring(length - 4);
            if (isNumber(substring)) {
                System.out.print(true);
            }else{
                System.out.print(false);
            }
        }

    }

}
