package com.emt.card.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * String工具类.
 *
 */
public class StringUtil {

	public static String ROUTE_SYMBOL;

	static{
		Properties props=System.getProperties(); //获得系统属性集
		String osName = props.getProperty("os.name");
		if(osName.contains("Windows")) {
			ROUTE_SYMBOL= "\\" ;
		}else {
			ROUTE_SYMBOL="/" ;
		}
	}
	private static final int INDEX_NOT_FOUND = -1;
	private static final String EMPTY = "";
	/**
	 * <p>
	 * The maximum size to which the padding constant(s) can expand.
	 * </p>
	 */
	private static final int PAD_LIMIT = 8192;

	/**
	 * 功能：将半角的符号转换成全角符号.(即英文字符转中文字符)
	 *
	 */
	public static String changeToFull(String str) {
		String source = "1234567890!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_=+\\|[];:'\",<.>/?";
		String[] decode = { "１", "２", "３", "４", "５", "６", "７", "８", "９", "０",
				"！", "＠", "＃", "＄", "％", "︿", "＆", "＊", "（", "）", "ａ", "ｂ",
				"ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ",
				"ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ",
				"Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ",
				"Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ",
				"Ｙ", "Ｚ", "－", "＿", "＝", "＋", "＼", "｜", "【", "】", "；", "：",
				"'", "\"", "，", "〈", "。", "〉", "／", "？" };
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int pos = source.indexOf(str.charAt(i));
			if (pos != -1) {
				result += decode[pos];
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 *
	 * 功能：cs串中是否一个都不包含字符数组searchChars中的字符。
	 *
	 */
	public static boolean containsNone(CharSequence cs, char... searchChars) {
		if (cs == null || searchChars == null) {
			return true;
		}
		int csLen = cs.length();
		int csLast = csLen - 1;
		int searchLen = searchChars.length;
		int searchLast = searchLen - 1;
		for (int i = 0; i < csLen; i++) {
			char ch = cs.charAt(i);
			for (int j = 0; j < searchLen; j++) {
				if (searchChars[j] == ch) {
					if (Character.isHighSurrogate(ch)) {
						if (j == searchLast) {
							// missing low surrogate, fine, like
							// String.indexOf(String)
							return false;
						}
						if (i < csLast
								&& searchChars[j + 1] == cs.charAt(i + 1)) {
							return false;
						}
					} else {
						// ch is in the Basic Multilingual Plane
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 编码为Unicode，格式 '\u0020'.
	 * </p>
	 *
	 */
	public static String unicodeEscaped(char ch) {
		if (ch < 0x10) {
			return "\\u000" + Integer.toHexString(ch);
		} else if (ch < 0x100) {
			return "\\u00" + Integer.toHexString(ch);
		} else if (ch < 0x1000) {
			return "\\u0" + Integer.toHexString(ch);
		}
		return "\\u" + Integer.toHexString(ch);
	}

	/**
	 * <p>
	 * 进行tostring操作，如果传入的是null，返回空字符串。
	 * </p>
	 *
	 */
	public static String toString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	/**
	 * <p>
	 * 进行tostring操作，如果传入的是null，返回指定的默认值。
	 * </p>
	 *
	 */
	public static String toString(Object obj, String nullStr) {
		return obj == null ? nullStr : obj.toString();
	}

	/**
	 * <p>
	 * 只从源字符串中移除指定开头子字符串.
	 * </p>
	 *
	 */
	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	/**
	 * <p>
	 * 只从源字符串中移除指定结尾的子字符串.
	 * </p>
	 *
	 */
	public static String removeEnd(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.endsWith(remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}

	/**
	 * <p>
	 * 将一个字符串重复N次
	 * </p>
	 *
	 */
	public static String repeat(String str, int repeat) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		if (repeat <= 0) {
			return EMPTY;
		}
		int inputLength = str.length();
		if (repeat == 1 || inputLength == 0) {
			return str;
		}
		if (inputLength == 1 && repeat <= PAD_LIMIT) {
			return repeat(str.charAt(0), repeat);
		}

		int outputLength = inputLength * repeat;
		switch (inputLength) {
			case 1:
				return repeat(str.charAt(0), repeat);
			case 2:
				char ch0 = str.charAt(0);
				char ch1 = str.charAt(1);
				char[] output2 = new char[outputLength];
				for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
					output2[i] = ch0;
					output2[i + 1] = ch1;
				}
				return new String(output2);
			default:
				StringBuilder buf = new StringBuilder(outputLength);
				for (int i = 0; i < repeat; i++) {
					buf.append(str);
				}
				return buf.toString();
		}
	}

	/**
	 * <p>
	 * 将一个字符串重复N次，并且中间加上指定的分隔符
	 * </p>
	 *
	 */
	public static String repeat(String str, String separator, int repeat) {
		if (str == null || separator == null) {
			return repeat(str, repeat);
		} else {
			// given that repeat(String, int) is quite optimized, better to rely
			// on it than try and splice this into it
			String result = repeat(str + separator, repeat);
			return removeEnd(result, separator);
		}
	}

	/**
	 * <p>
	 * 将某个字符重复N次.
	 * </p>
	 *
	 */
	public static String repeat(char ch, int repeat) {
		char[] buf = new char[repeat];
		for (int i = repeat - 1; i >= 0; i--) {
			buf[i] = ch;
		}
		return new String(buf);
	}

	/**
	 * <p>
	 * 字符串长度达不到指定长度时，在字符串右边补指定的字符.
	 * </p>
	 *
	 */
	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(repeat(padChar, pads));
	}

	/**
	 * <p>
	 * 扩大字符串长度，从左边补充指定字符
	 * </p>
	 *
	 */
	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	/**
	 * <p>
	 * 扩大字符串长度，从左边补充空格
	 * </p>
	 *
	 */
	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	/**
	 * <p>
	 * 扩大字符串长度，从左边补充指定的字符
	 * </p>
	 *
	 */
	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return repeat(padChar, pads).concat(str);
	}

	/**
	 * <p>
	 * 扩大字符串长度，从左边补充指定的字符
	 * </p>
	 *
	 */
	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	/**
	 * <p>
	 * 扩大字符串长度并将现在的字符串居中，被扩大部分用空格填充。
	 * <p>
	 *
	 */
	public static String center(String str, int size) {
		return center(str, size, ' ');
	}

	/**
	 * <p>
	 * 将字符串长度修改为指定长度，并进行居中显示。
	 * </p>
	 *
	 */
	public static String center(String str, int size, char padChar) {
		if (str == null || size <= 0) {
			return str;
		}
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		str = leftPad(str, strLen + pads / 2, padChar);
		str = rightPad(str, size, padChar);
		return str;
	}

	/**
	 * <p>
	 * 将字符串长度修改为指定长度，并进行居中显示。
	 * </p>
	 *
	 */
	public static String center(String str, int size, String padStr) {
		if (str == null || size <= 0) {
			return str;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		str = leftPad(str, strLen + pads / 2, padStr);
		str = rightPad(str, size, padStr);
		return str;
	}

	/**
	 * <p>
	 * 检查字符串是否全部为小写.
	 * </p>
	 *
	 public static boolean isAllLowerCase(String cs) {
	 if (cs == null || isEmpty(cs)) {
	 return false;
	 }
	 int sz = cs.length();
	 for (int i = 0; i < sz; i++) {
	 if (Character.isLowerCase(cs.charAt(i)) == false) {
	 return false;
	 }
	 }
	 return true;
	 }

	 /**
	 * <p>
	 * 检查是否都是大写.
	 * </p>
	 *
	 */
	public static boolean isAllUpperCase(String cs) {
		if (cs == null || StringUtil.isEmpty(cs)) {
			return false;
		}
		int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isUpperCase(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 反转字符串.
	 * </p>
	 *
	 */
	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuilder(str).reverse().toString();
	}

	/**
	 * <p>
	 * 字符串达不到一定长度时在右边补空白.
	 * </p>
	 *
	 */
	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	/**
	 * 从右边截取字符串.</p>
	 *
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(str.length() - len);
	}

	/**
	 * <p>
	 * 截取一个字符串的前几个.
	 * </p>
	 *
	 */
	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	/**
	 * <p>
	 * 得到tag字符串中间的子字符串，只返回第一个匹配项。
	 * </p>
	 *
	 */
	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	/**
	 * <p>
	 * 得到两个字符串中间的子字符串，只返回第一个匹配项。
	 * </p>
	 *
	 */
	public static String substringBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != INDEX_NOT_FOUND) {
			int end = str.indexOf(close, start + open.length());
			if (end != INDEX_NOT_FOUND) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	/**
	 * <p>
	 * 得到两个字符串中间的子字符串，所有匹配项组合为数组并返回。
	 * </p>
	 *
	 */
	public static String[] substringsBetween(String str, String open,
											 String close) {
		if (str == null || isEmpty(open) || isEmpty(close)) {
			return null;
		}
		int strLen = str.length();
		if (strLen == 0) {
			return new String[0];
		}
		int closeLen = close.length();
		int openLen = open.length();
		List<String> list = new ArrayList<String>();
		int pos = 0;
		while (pos < strLen - closeLen) {
			int start = str.indexOf(open, pos);
			if (start < 0) {
				break;
			}
			start += openLen;
			int end = str.indexOf(close, start);
			if (end < 0) {
				break;
			}
			list.add(str.substring(start, end));
			pos = end + closeLen;
		}
		if (list.isEmpty()) {
			return null;
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 功能：切换字符串中的所有字母大小写。<br/>
	 *
	 */
	public static String swapCase(String str) {
		if (StringUtil.isEmpty(str)) {
			return str;
		}
		char[] buffer = str.toCharArray();

		boolean whitespace = true;

		for (int i = 0; i < buffer.length; i++) {
			char ch = buffer[i];
			if (Character.isUpperCase(ch)) {
				buffer[i] = Character.toLowerCase(ch);
				whitespace = false;
			} else if (Character.isTitleCase(ch)) {
				buffer[i] = Character.toLowerCase(ch);
				whitespace = false;
			} else if (Character.isLowerCase(ch)) {
				if (whitespace) {
					buffer[i] = Character.toTitleCase(ch);
					whitespace = false;
				} else {
					buffer[i] = Character.toUpperCase(ch);
				}
			} else {
				whitespace = Character.isWhitespace(ch);
			}
		}
		return new String(buffer);
	}

	/**
	 * 功能：截取出最后一个标志位之后的字符串.<br/>
	 * 如果sourceStr为empty或者expr为null，直接返回源字符串。<br/>
	 * 如果expr长度为0，直接返回sourceStr。<br/>
	 * 如果expr在sourceStr中不存在，直接返回sourceStr。<br/>
	 *
	 */
	public static String substringAfterLast(String sourceStr, String expr) {
		if (isEmpty(sourceStr) || expr == null) {
			return sourceStr;
		}
		if (expr.length() == 0) {
			return sourceStr;
		}

		int pos = sourceStr.lastIndexOf(expr);
		if (pos == -1) {
			return sourceStr;
		}
		return sourceStr.substring(pos + expr.length());
	}

	/**
	 * 功能：截取出最后一个标志位之前的字符串.<br/>
	 * 如果sourceStr为empty或者expr为null，直接返回源字符串。<br/>
	 * 如果expr长度为0，直接返回sourceStr。<br/>
	 * 如果expr在sourceStr中不存在，直接返回sourceStr。<br/>
	 *
	 */
	public static String substringBeforeLast(String sourceStr, String expr) {
		if (isEmpty(sourceStr) || expr == null) {
			return sourceStr;
		}
		if (expr.length() == 0) {
			return sourceStr;
		}
		int pos = sourceStr.lastIndexOf(expr);
		if (pos == -1) {
			return sourceStr;
		}
		return sourceStr.substring(0, pos);
	}

	/**
	 * 功能：截取出第一个标志位之后的字符串.<br/>
	 * 如果sourceStr为empty或者expr为null，直接返回源字符串。<br/>
	 * 如果expr长度为0，直接返回sourceStr。<br/>
	 * 如果expr在sourceStr中不存在，直接返回sourceStr。<br/>
	 *
	 */
	public static String substringAfter(String sourceStr, String expr) {
		if (isEmpty(sourceStr) || expr == null) {
			return sourceStr;
		}
		if (expr.length() == 0) {
			return sourceStr;
		}

		int pos = sourceStr.indexOf(expr);
		if (pos == -1) {
			return sourceStr;
		}
		return sourceStr.substring(pos + expr.length());
	}

	/**
	 * 功能：截取出第一个标志位之前的字符串.<br/>
	 * 如果sourceStr为empty或者expr为null，直接返回源字符串。<br/>
	 * 如果expr长度为0，直接返回sourceStr。<br/>
	 * 如果expr在sourceStr中不存在，直接返回sourceStr。<br/>
	 * 如果expr在sourceStr中存在不止一个，以第一个位置为准。
	 *
	 */
	public static String substringBefore(String sourceStr, String expr) {
		if (isEmpty(sourceStr) || expr == null) {
			return sourceStr;
		}
		if (expr.length() == 0) {
			return sourceStr;
		}
		int pos = sourceStr.indexOf(expr);
		if (pos == -1) {
			return sourceStr;
		}
		return sourceStr.substring(0, pos);
	}

	/**
	 * 功能：检查这个字符串是不是空字符串。<br/>
	 * 如果这个字符串为null或者trim后为空字符串则返回true，否则返回false。
	 *
	 */
	public static boolean isEmpty(String chkStr) {
		if (chkStr == null) {
			return true;
		} else {
			return "".equals(chkStr.trim()) ? true : false;
		}
	}

	public static List<Integer> getListSplitStringArr(String strArr){
		List<Integer> arrayList = new ArrayList<>();
		if (StringUtil.isNotEmpty(strArr)){
			String[] strings = strArr.split(",");
			for (String s:strings){
				arrayList.add(Integer.valueOf(s));
			}
		}
		return arrayList;
	}

	public static String getUUIDwithoutHorizontal(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public static boolean isNotNull(String chkStr) {
		return !(isEmpty(chkStr)||"null".equals(chkStr));
	}
	public static boolean isNotEmpty(String chkStr) {
		return !isEmpty(chkStr);
	}

	/**正则表达式**/
	private static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
			+ "(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|drop|execute)\\b)";
//b  表示 限定单词边界  比如  select 不通过   1select则是可以的

	private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

	public static boolean isCanSql(String optStr) {
//		String CHECKSQL = "/\\w*((\\%27)|(\\’))((\\%6F)|o|(\\%4F))((\\%72)|r|(\\%52))/ix";
		if(!isEmpty(optStr)&& !sqlPattern.matcher(optStr).find()){//防止sql注入
			return true;
		}else {
			return false;
		}
	}


	public static boolean isMobileNO(String mobiles){

		Pattern p = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

//		System.out.println(m.matches()+"---");

		return m.matches();

	}
	/**
	 *
	 * 如果字符串没有超过最长显示长度返回原字符串，否则从开头截取指定长度并加...返回。
	 *
	 */
	public static String trimString(String str, int length) {
		if (str == null) {
			return "";
		} else if (str.length() > length) {
			return str.substring(0, length - 3) + "...";
		} else {
			return str;
		}
	}

	/**
	 * 是否有特殊符合
	 * @param content
	 * @return
	 */
	public static boolean hasEmoji(String content){
		if(content!=null){
			Pattern pattern = Pattern.compile(
					"[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
			Matcher matcher = pattern.matcher(content);
			if(matcher .find()){
				return true;
			}}
		return false;
	}
}