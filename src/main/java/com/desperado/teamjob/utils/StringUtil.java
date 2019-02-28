package com.desperado.teamjob.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StringUtil {
    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
    public static final String EMPTY_STRING = "";
    private static final String AS = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public StringUtil() {
    }

    public static int getStringLen(String str) {
        return isEmpty(str) ? 0 : str.length();
    }

    public static String convertEncode(String strIn, String encoding, String targetEncoding) {
        String strOut = strIn;
        if (strIn == null) {
            return strIn;
        } else {
            try {
                if (encoding != null && targetEncoding != null) {
                    strOut = new String(strIn.getBytes(encoding), targetEncoding);
                } else if (encoding != null) {
                    strOut = new String(strIn.getBytes(encoding));
                } else {
                    if (targetEncoding == null) {
                        return strOut;
                    }

                    strOut = new String(strIn.getBytes(), targetEncoding);
                }
            } catch (UnsupportedEncodingException var5) {
                var5.printStackTrace();
                System.out.println("Unsupported Encoding: " + encoding);
            }

            return strOut;
        }
    }

    public static String extractString(String str, String startStr, String endStr) {
        if (isEmpty(str)) {
            return str;
        } else {
            if (startStr == null) {
                startStr = "";
            }

            boolean startIdx = false;
            int startIdx1 = str.indexOf(startStr);
            if (startIdx1 == -1) {
                startIdx1 = 0;
            } else {
                startIdx1 += startStr.length();
            }

            int endIdx = str.length();
            if (endStr != null) {
                endIdx = str.indexOf(endStr, startIdx1);
                if (endIdx == -1) {
                    endIdx = str.length();
                }
            }

            return str.substring(startIdx1, endIdx);
        }
    }

    public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }

    public static String replaceSpace(String text) {
        return replace(text, " ", "");
    }

    public static String replace(String text, String repl, String with, int max) {
        if (text != null && repl != null && with != null && repl.length() != 0 && max != 0) {
            StringBuffer buf = new StringBuffer(text.length());
            int start = 0;
            boolean var6 = false;

            int end;
            while((end = text.indexOf(repl, start)) != -1) {
                buf.append(text.substring(start, end)).append(with);
                start = end + repl.length();
                --max;
                if (max == 0) {
                    break;
                }
            }

            if (start == 0) {
                return text;
            } else {
                buf.append(text.substring(start));
                return buf.toString();
            }
        } else {
            return text;
        }
    }

    public static String trim(String str) {
        return trim(str, (String)null, 0);
    }

    public static String trim(String str, String stripChars) {
        return trim(str, stripChars, 0);
    }

    public static String trimStart(String str) {
        return trim(str, (String)null, -1);
    }

    public static String trimStart(String str, String stripChars) {
        return trim(str, stripChars, -1);
    }

    public static String trimEnd(String str) {
        return trim(str, (String)null, 1);
    }

    public static String trimEnd(String str, String stripChars) {
        return trim(str, stripChars, 1);
    }

    public static String trimToNull(String str) {
        return trimToNull(str, (String)null);
    }

    public static String trimToNull(String str, String stripChars) {
        String result = trim(str, stripChars);
        return result != null && result.length() != 0 ? result : null;
    }

    public static String trimToEmpty(String str) {
        return trimToEmpty(str, (String)null);
    }

    public static String trimToEmpty(String str, String stripChars) {
        String result = trim(str, stripChars);
        return result == null ? "" : result;
    }

    private static String trim(String str, String stripChars, int mode) {
        if (str == null) {
            return null;
        } else {
            int length = str.length();
            int start = 0;
            int end = length;
            if (mode <= 0) {
                if (stripChars == null) {
                    while(start < end && Character.isWhitespace(str.charAt(start))) {
                        ++start;
                    }
                } else {
                    if (stripChars.length() == 0) {
                        return str;
                    }

                    while(start < end && stripChars.indexOf(str.charAt(start)) != -1) {
                        ++start;
                    }
                }
            }

            if (mode >= 0) {
                if (stripChars == null) {
                    while(start < end && Character.isWhitespace(str.charAt(end - 1))) {
                        --end;
                    }
                } else {
                    if (stripChars.length() == 0) {
                        return str;
                    }

                    while(start < end && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                        --end;
                    }
                }
            }

            return start <= 0 && end >= length ? str : str.substring(start, end);
        }
    }

    public static boolean hasLength(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean hasText(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean hasText(StringBuffer str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static String[] commaDelimitedListToStringArray(String str) {
        return delimitedListToStringArray(str, ",");
    }

    public static String[] delimitedListToStringArray(String str, String delimiter) {
        if (str == null) {
            return new String[0];
        } else if (delimiter == null) {
            return new String[]{str};
        } else {
            List<String> result = new ArrayList();
            int pos;
            if ("".equals(delimiter)) {
                for(pos = 0; pos < str.length(); ++pos) {
                    result.add(str.substring(pos, pos + 1));
                }
            } else {
                pos = 0;

                int delPos;
                for(boolean var4 = false; (delPos = str.indexOf(delimiter, pos)) != -1; pos = delPos + delimiter.length()) {
                    result.add(str.substring(pos, delPos));
                }

                if (str.length() > 0 && pos <= str.length()) {
                    result.add(str.substring(pos));
                }
            }

            return toStringArray(result);
        }
    }

    public static String[] toStringArray(Collection<String> collection) {
        return collection == null ? null : (String[])((String[])collection.toArray(new String[collection.size()]));
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static boolean isAnyEmpty(String... s) {
        String[] arr$ = s;
        int len$ = s.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String str = arr$[i$];
            if (isEmpty(str)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAllEmpty(String... s) {
        String[] arr$ = s;
        int len$ = s.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String str = arr$[i$];
            if (isNotEmpty(str)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.trim().length() != 0;
    }

    public static String reverse(String s) {
        if (isEmpty(s)) {
            return s;
        } else {
            StringBuilder sb = new StringBuilder(s);
            return sb.reverse().toString();
        }
    }

    public static String escapeHtml(String str) {
        if (str == null) {
            return "";
        } else {
            str = str.replace(">", "&gt;");
            str = str.replace("<", "&lt;");
            return str;
        }
    }

    public static String limitString(String s, int byteLength) {
        return limitString(s, byteLength, "...");
    }

    public static String limitString(String s, int byteLength, String omit) {
        if (s == null) {
            return null;
        } else if (byteLength <= 0) {
            return "";
        } else if (s.getBytes().length <= byteLength) {
            return s;
        } else {
            String r = "";

            for(int i = 0; i < s.length(); ++i) {
                String tmp = s.substring(i, i + 1);
                if (r.getBytes().length + tmp.getBytes().length > byteLength) {
                    break;
                }

                r = r + tmp;
            }

            if (omit != null) {
                r = r + omit;
            }

            return r;
        }
    }

    public static String getPatternMatchStr(String src, String pattern) {
        if (src == null) {
            return null;
        } else {
            try {
                Pattern p = Pattern.compile(pattern);
                Matcher matcher = p.matcher(src);
                if (matcher.find()) {
                    return matcher.group();
                }
            } catch (Exception var4) {
                logger.warn("", var4);
            }

            return null;
        }
    }

    public static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            char c = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int)(Math.random() * (double)"abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".length()));
            sb.append(c);
        }

        return sb.toString().toUpperCase();
    }

    public static String toSize(String s, int byteLength, String omit) {
        if (byteLength <= 0) {
            return "";
        } else {
            if (s == null) {
                s = "";
            }

            if (omit == null) {
                omit = "...";
            }

            int omitSize = omit.getBytes().length;
            if (s.getBytes().length > byteLength) {
                if (byteLength < omitSize) {
                    s = limitString(s, byteLength);
                } else {
                    s = limitString(s, byteLength - omitSize, omit);
                }
            }

            while(s.getBytes().length + omitSize <= byteLength) {
                s = s + omit;
            }

            return s;
        }
    }

    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            String firstLetter = str.substring(0, 1);
            String result = firstLetter.toUpperCase() + str.substring(1);
            return result;
        }
    }

    public static String lowerFirstLetter(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            String firstLetter = str.substring(0, 1);
            String result = firstLetter.toLowerCase() + str.substring(1);
            return result;
        }
    }

    public static boolean isAsciiStr(String str) {
        if (str == null) {
            return false;
        } else {
            int length = str.length();

            for(int i = 0; i < length; ++i) {
                if (str.charAt(i) > 255) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        } else {
            int length = str.length();

            for(int i = 0; i < length; ++i) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }

    public static int countMatches(String str, String subStr) {
        if (str != null && str.length() != 0 && subStr != null && subStr.length() != 0) {
            int count = 0;

            for(int index = 0; (index = str.indexOf(subStr, index)) != -1; index += subStr.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    public static String readLine(InputStreamReader inputReader) throws IOException {
        StringBuffer sb = new StringBuffer();
        int num = 0;

        int n;
        while((n = inputReader.read()) > 0) {
            ++num;
            char c = (char)n;
            if (c == '\n' || c == '\r') {
                break;
            }

            sb.append(c);
        }

        return num == 0 ? null : sb.toString();
    }

    public static String getResource(String uri) {
        if (isEmpty(uri)) {
            return "";
        } else {
            return uri.contains(".") ? uri.split("\\.")[0] : uri;
        }
    }

    public static String getExtension(String uri) {
        if (isEmpty(uri)) {
            return "";
        } else {
            if (uri.contains(".")) {
                String[] strs = uri.split("\\.");
                if (strs.length > 1) {
                    String extension = strs[1];
                    if (isNotEmpty(extension)) {
                        return extension;
                    }
                }
            }

            return "";
        }
    }

    public static String getFileExtName(String file) {
        if (isEmpty(file)) {
            return null;
        } else {
            int idx = file.lastIndexOf(".");
            return idx < 0 ? file : file.substring(idx + 1);
        }
    }

    public static String formatString(String str, Object... obj) {
        return String.format(str, obj);
    }

    public static String lastSubStr(String str, int len) {
        if (str == null) {
            return null;
        } else {
            return str.length() <= len ? str : str.substring(str.length() - len, str.length());
        }
    }

    public static String filterAsciiStr(String str) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c > 255) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String getAsciiStr(String str) {
        if (str == null) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                if (c < 255) {
                    sb.append(c);
                }
            }

            return trim(sb.toString());
        }
    }

    public static String getLetterOrDigit(String str) {
        if (str == null) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                if (Character.isLetterOrDigit(c)) {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static boolean isPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        } else {
            try {
                Pattern p = null;
                Matcher m = null;
                boolean b = false;
                p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
                m = p.matcher(phone);
                b = m.matches();
                return b;
            } catch (Exception var4) {
                return false;
            }
        }
    }

    public static String escapeQueryChars(String s) {
        if (isEmpty(s)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':' || c == '^' || c == '[' || c == ']' || c == '"' || c == '{' || c == '}' || c == '~' || c == '*' || c == '?' || c == '|' || c == '&' || c == ';' || c == '/' || Character.isWhitespace(c)) {
                    sb.append('\\');
                }

                sb.append(c);
            }

            return sb.toString();
        }
    }


    public static String genArrayInSql(String... strings) {
        if (strings != null && strings.length != 0) {
            StringBuilder sb = new StringBuilder();
            String[] arr$ = strings;
            int len$ = strings.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String string = arr$[i$];
                sb.append(",'").append(string).append("'");
            }

            return sb.substring(1);
        } else {
            return null;
        }
    }

    public static String trimCtrlChars(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            boolean containCtrlChar = false;

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                if (c < ' ' && c != '\n' && c != '\r') {
                    containCtrlChar = true;
                }
            }

            if (containCtrlChar) {
                StringBuffer sb = new StringBuffer();

                for(int i = 0; i < str.length(); ++i) {
                    char c = str.charAt(i);
                    if (c >= ' ' || c == '\n' || c == '\r') {
                        sb.append(c);
                    }
                }

                str = sb.toString();
            }

            return str;
        }
    }

    public static String convertToUnderlineStr(String str) {
        if (str == null) {
            return null;
        } else {
            boolean lastUpper = false;
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                if (i > 0 && Character.isUpperCase(c) && !lastUpper) {
                    sb.append('_');
                }

                sb.append(Character.toLowerCase(c));
                lastUpper = Character.isUpperCase(c);
            }

            return sb.toString();
        }
    }

    public static String convertToHumpString(String str) {
        if (str == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean nextUpper = false;

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                if (Character.isLetterOrDigit(c)) {
                    if (nextUpper) {
                        c = Character.toUpperCase(c);
                        nextUpper = false;
                    }

                    sb.append(c);
                } else if (sb.length() > 0) {
                    nextUpper = true;
                }
            }

            return sb.toString();
        }
    }
}
