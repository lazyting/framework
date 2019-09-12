package utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtils extends org.apache.commons.lang3.StringUtils
{
	private static final Log logger = LogFactory.getLog(StringUtils.class);

	public static String newString(String value, int length)
	{
		StringBuilder buffer = new StringBuilder();
		if (value == null)
		{
			return null;
		}
		for (int i = 0; i < length; i++)
		{
			buffer.append(value);
		}
		return buffer.toString();
	}

	public static String newString(char ch, int length)
	{
		return newString(String.valueOf(ch), length);
	}

	public static String copyString(String str, int copyTimes)
	{
		StringBuilder buffer = new StringBuilder();
		if (str == null)
		{
			return null;
		}
		for (int i = 0; i < copyTimes; i++)
		{
			buffer.append(str);
		}
		return buffer.toString();
	}

	public static int getBytesLength(String str, String charsetName)
	{
		if (str == null)
			return -1;
		try
		{
			return str.getBytes(charsetName).length;
		}
		catch (UnsupportedEncodingException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	public static int indexOf(String str, String subStr, int startIndex, int occurrenceTimes)
	{
		int foundCount = 0;
		int index = startIndex;
		int substrLength = subStr.length();
		if (occurrenceTimes <= 0)
		{
			return -1;
		}
		if (str.length() - 1 < startIndex)
		{
			return -1;
		}
		if ("".equals(subStr))
		{
			return 0;
		}
		while (foundCount < occurrenceTimes)
		{
			index = str.indexOf(subStr, index);
			if (index == -1)
			{
				return -1;
			}
			foundCount++;
			index += substrLength;
		}
		return index - substrLength;
	}

	public static int indexOf(String str, String subStr, int occurrenceTimes)
	{
		return indexOf(str, subStr, 0, occurrenceTimes);
	}

	public static int indexOf(String str, String subStr, int fromIndex, boolean caseSensitive)
	{
		if (!caseSensitive)
		{
			return str.toLowerCase(Locale.getDefault()).indexOf(subStr.toLowerCase(Locale.getDefault()), fromIndex);
		}
		return str.indexOf(subStr, fromIndex);
	}

	public static String replace(String str, String searchStr, String replaceStr, boolean caseSensitive)
	{
		int i = 0;
		int j = 0;
		if (str == null)
		{
			return null;
		}
		if ("".equals(str))
		{
			return "";
		}
		if ((searchStr == null) || (searchStr.equals("")))
		{
			return str;
		}
		String newReplaceStr = replaceStr;
		if (replaceStr == null)
		{
			newReplaceStr = "";
		}
		StringBuilder buffer = new StringBuilder();
		while ((j = indexOf(str, searchStr, i, caseSensitive)) > -1)
		{
			buffer.append(str.substring(i, j));
			buffer.append(newReplaceStr);
			i = j + searchStr.length();
		}
		buffer.append(str.substring(i, str.length()));
		return buffer.toString();
	}

	public static String replace(String str, String searchStr, String replaceStr)
	{
		return replace(str, searchStr, replaceStr, true);
	}

	public static String replace(String str, char searchChar, String replaceStr)
	{
		return replace(str, String.valueOf(searchChar), replaceStr, true);
	}

	public static String replace(String str, int beginIndex, String replaceStr)
	{
		if (str == null)
		{
			return null;
		}
		String newReplaceStr = replaceStr;
		if (replaceStr == null)
		{
			newReplaceStr = "";
		}
		StringBuilder buffer = new StringBuilder(str.substring(0, beginIndex));
		buffer.append(newReplaceStr);
		buffer.append(str.substring(beginIndex + newReplaceStr.length()));
		return buffer.toString();
	}

	/** @deprecated */
	@Deprecated
	public static String[] split(String originalString, int splitByteLength)
	{
		return split(originalString, splitByteLength, Charset.defaultCharset().name());
	}

	public static String[] split(String originalString, int splitByteLength, String charsetName)
	{
		if (originalString == null)
		{
			return new String[0];
		}
		if ("".equals(originalString))
		{
			return new String[0];
		}
		if ("".equals(originalString.trim()))
		{
			return new String[] { "" };
		}
		if (splitByteLength <= 1)
		{
			return new String[] { originalString };
		}

		String strText = null;
		int intStartIndex = 0;
		int intEndIndex = 0;
		int index = 0;
		int fixCount = 0;
		String[] arrReturn = null;
		List<String> strList = new ArrayList<String>();
		int loopCount = 0;
		try
		{
			byte[] arrByte = originalString.getBytes(charsetName);

			intEndIndex = 0;
			while (true)
			{
				loopCount++;
				if (loopCount > 10000)
				{
					logger.error("Can't split(\"" + originalString + "\"," + splitByteLength + "). default charset is "
							+ System.getProperty("file.encoding"));

					throw new IllegalStateException("Can't split,loop count is " + loopCount);
				}

				intStartIndex = intEndIndex;
				intEndIndex = intStartIndex + splitByteLength;

				if (intStartIndex >= arrByte.length)
				{
					break;
				}
				if (intEndIndex > arrByte.length)
				{
					intEndIndex = arrByte.length;
					strText = new String(arrByte, intStartIndex, intEndIndex - intStartIndex, charsetName);
					strList.add(strText);
					break;
				}

				fixCount = 0;
				strText = new String(arrByte, intStartIndex, intEndIndex - intStartIndex, charsetName);
				byte[] bytes = strText.getBytes(charsetName);
				if (bytes.length < splitByteLength)
				{
					intEndIndex = intStartIndex + bytes.length;
				}
				for (index = intEndIndex - 1; (index >= intStartIndex) && (arrByte[index] != bytes[(index - intStartIndex)]); index--)
				{
					fixCount++;
				}

				if (fixCount > 0)
				{
					if (fixCount >= intEndIndex)
					{
						fixCount = 0;
						if (logger.isDebugEnabled())
						{
							logger.debug("split length " + splitByteLength + " is too small.");
						}
					}
					intEndIndex -= fixCount;

					strText = new String(arrByte, intStartIndex, intEndIndex - intStartIndex, charsetName);
				}

				if (intStartIndex == intEndIndex)
				{
					logger.error("Can't split(\"" + originalString + "\"," + splitByteLength + "). default charset is "
							+ System.getProperty("file.encoding"));

					throw new IllegalStateException("Can't split(\"" + originalString + "\"," + splitByteLength + "). default charset is "
							+ System.getProperty("file.encoding"));
				}

				strList.add(strText);
			}

			arrReturn = new String[strList.size()];
			strList.toArray(arrReturn);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new IllegalArgumentException(e);
		}
		return arrReturn;
	}

	public static String[] split(String originalString, String delimiterString)
	{
		int index = 0;
		String[] returnArray = null;
		int length = 0;

		if ((originalString == null) || (delimiterString == null) || ("".equals(originalString)))
		{
			return new String[0];
		}

		if (("".equals(originalString)) || ("".equals(delimiterString)) || (originalString.length() < delimiterString.length()))
		{
			return new String[] { originalString };
		}

		String strTemp = originalString;
		while ((strTemp != null) && (!strTemp.equals("")))
		{
			index = strTemp.indexOf(delimiterString);
			if (index == -1)
			{
				break;
			}
			length++;
			strTemp = strTemp.substring(index + delimiterString.length());
		}
		length++;
		returnArray = new String[length];

		strTemp = originalString;
		for (int i = 0; i < length - 1; i++)
		{
			index = strTemp.indexOf(delimiterString);
			returnArray[i] = strTemp.substring(0, index);
			strTemp = strTemp.substring(index + delimiterString.length());
		}
		returnArray[(length - 1)] = strTemp;
		return returnArray;
	}

	public static String rightTrim(String str)
	{
		if (str == null)
		{
			return "";
		}
		int length = str.length();
		for (int i = length - 1; (i >= 0) && (str.charAt(i) == ' '); i--)
		{
			length--;
		}
		return str.substring(0, length);
	}

	public static String leftTrim(String str)
	{
		if (str == null)
		{
			return "";
		}
		int start = 0;
		int i = 0;
		for (int n = str.length(); (i < n) && (str.charAt(i) == ' '); i++)
		{
			start++;
		}
		return str.substring(start);
	}

	public static String absoluteTrim(String str)
	{
		return replace(str, " ", "");
	}

	public static String lowerCase(String str, int beginIndex, int endIndex)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append(str.substring(0, beginIndex));
		buffer.append(str.substring(beginIndex, endIndex).toLowerCase());
		buffer.append(str.substring(endIndex));
		return buffer.toString();
	}

	public static String upperCase(String str, int beginIndex, int endIndex)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append(str.substring(0, beginIndex));
		buffer.append(str.substring(beginIndex, endIndex).toUpperCase());
		buffer.append(str.substring(endIndex));
		return buffer.toString();
	}

	public static String lowerCaseFirstChar(String iString)
	{
		String newString = iString.substring(0, 1).toLowerCase() + iString.substring(1);
		return newString;
	}

	public static String upperCaseFirstChar(String iString)
	{
		String newString = iString.substring(0, 1).toUpperCase() + iString.substring(1);
		return newString;
	}

	public static int timesOf(String str, String subStr)
	{
		int foundCount = 0;
		if ("".equals(subStr))
		{
			return 0;
		}
		int fromIndex = str.indexOf(subStr);
		while (fromIndex != -1)
		{
			foundCount++;
			fromIndex = str.indexOf(subStr, fromIndex + subStr.length());
		}
		return foundCount;
	}

	public static int timesOf(String str, char ch)
	{
		int foundCount = 0;
		int fromIndex = str.indexOf(ch);
		while (fromIndex != -1)
		{
			foundCount++;
			fromIndex = str.indexOf(ch, fromIndex + 1);
		}
		return foundCount;
	}

	public static Map<String, String> toMap(String str, String splitString)
	{
		Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());
		String[] values = split(str, splitString);
		for (int i = 0; i < values.length; i++)
		{
			String tempValue = values[i];
			int pos = tempValue.indexOf('=');
			String key = "";
			String value = "";
			if (pos > -1)
			{
				key = tempValue.substring(0, pos);
				value = tempValue.substring(pos + splitString.length());
			}
			else
			{
				key = tempValue;
			}
			map.put(key, value);
		}
		return map;
	}

	public static String native2ascii(String str)
	{
		char[] ca = str.toCharArray();
		StringBuilder buffer = new StringBuilder(ca.length * 6);
		for (int x = 0; x < ca.length; x++)
		{
			char a = ca[x];
			if (a > 'ÿ')
				buffer.append("\\u").append(Integer.toHexString(a));
			else
			{
				buffer.append(a);
			}
		}
		return buffer.toString();
	}

	public static String concat(Object[] sources)
	{
		if (sources == null)
		{
			return "";
		}
		if (sources.length == 1)
		{
			return String.valueOf(sources[0]);
		}
		StringBuilder sb = new StringBuilder();
		for (Object o : sources)
		{
			sb.append(o);
		}
		return sb.toString();
	}

	/**
	 * 去除字符串中的空格、换行、制表符、回车
	 * 
	 * @param str
	 * @return
	 * 
	 * 
	 */
	public static String replaceBlank(String str)
	{
		String dest = "";
		if (str != null)
		{
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * xss转换之后的String转换
	 *
	 * @author moxg 2013-9-27
	 * @param str
	 * @return
	 */
	public static String xssDecode(String str)
	{

		if ((str == null) || ("".equals(str)))
		{
			return str;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			// logger.info(c+":");
			switch (c)
			{
			case '＞':
				sb.append('>');
				break;
			case '＜':
				sb.append('<');
				break;
			case '‘':
				sb.append("'");
				break;
			case '（':
				sb.append('(');
				break;
			case '）':
				sb.append(')');
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 *
	 * @param s
	 * @return
	 */
	public static String xssEncode(String s)
	{
		if (s == null || "".equals(s))
		{
			return s;
		}
		StringBuilder sb = new StringBuilder();
		if (s.indexOf('>') != -1 || s.indexOf('<') != -1 || s.indexOf('\'') != -1)
		{

			for (int i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);
				switch (c)
				{
				case '>':
					sb.append('＞');// 全角大于号
					break;
				case '<':
					sb.append('＜');// 全角小于号
					break;
				case '\'':
					sb.append('‘');// 全角单引号
					break;
				case '(':
					sb.append('（');// 全角左括号
					break;
				case ')':
					sb.append('）');// 全角右括号
					break;
				// 下面三个不常用
				// case '\"':
				// sb.append('“');//全角双引号
				// break;
				// case '&':
				// sb.append('＆');//全角
				// break;
				// case '\\':
				// sb.append('＼');//全角斜线
				// break;
				// case '#':
				// sb.append('＃');//全角井号
				// break;
				default:
					sb.append(c);
					break;
				}
			}
		}
		else
		{
			sb.append(s);
		}

		return sb.toString();
	}

	public static String trimAll(String str)
	{
		StringBuilder sb = new StringBuilder();
		char c = ' ';
		for (int i = 0; i < str.length(); i++)
		{
			char s = str.charAt(i);
			if (s != c)
			{
				sb.append(s);
			}
		}
		return sb.toString();
	}

	/**
	 * 将字符串转为16进制
	 * 
	 * @param str
	 * @return
	 */
	public static String toHexString(String str)
	{

		byte[] b = str.getBytes();

		String s = "";
		for (int i = 0; i < b.length; i++)
		{
			s = s + Integer.toHexString(b[i]);
		}

		return s;

	}

	/**
	 * 将16进制字符串转换为原始的字符串
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String toStringHex(String str) throws UnsupportedEncodingException
	{

		byte[] b = new byte[str.length() / 2];

		for (int i = 0; i < b.length; i++)
		{
			b[i] = (byte) (0xff & Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16));
		}

		return new String(b, "utf-8");

	}
	
    /**
     * 判断两个字符串是否相等。如果两者都为空，则也判断是相等
     * @param param1
     * @param param2
     * @return
     */
    public static boolean isEquals(String param1, String param2) {
        if (isEmpty(param1) && isEmpty(param2))
            return true;

        if (isEmpty(param1) && isNotEmpty(param2)) {
            return false;
        }

        return param1.equals(param2);
    }
    
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    public static boolean isEmpty(String str) {
        if (isNull(str) || str.length() == 0)
            return true;
        return false;
    }
    
    public static boolean isNull(String str) {
        if (str == null)
            return true;
        return false;
    }
    
    /**
	 * 隐藏并替换字符串中所有的手机号
	 * @param phoneNum
	 * @return
	 */
	public static String hidePhoneNum(String phoneNum){
		if(EmptyUtil.isEmpty(phoneNum)){
			return null;
		}
		Pattern pattern =Pattern.compile("((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}");
		Matcher matcher = pattern.matcher(phoneNum);
		StringBuffer sb = new StringBuffer();
		try {
			while(matcher.find()) {
				String phoneStr = matcher.group();
				phoneStr = phoneStr.substring(0, 3) + "****" + phoneStr.substring(7, phoneStr.length());
				matcher.appendReplacement(sb,phoneStr);
			}
			matcher.appendTail(sb);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 隐藏并替换所有的身份证号码
	 * @param idCardNo
	 * @return
	 */
	public static String hideIdCardNum(String idCardNo){
		if(EmptyUtil.isEmpty(idCardNo)){
			return null;
		}
		Pattern pattern = Pattern.compile("(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)");
		Matcher matcher = pattern.matcher(idCardNo);
		StringBuffer sb = new StringBuffer();
		try {
			while(matcher.find()) {
				String idCardStr = matcher.group();
				int len=idCardStr.length(); 
				if(len>=9){
					idCardStr =  idCardStr.replaceAll("(.{"+(len<12?3:6)+"})(.*)(.{4})", "$1" + "****" + "$3");
				}
				matcher.appendReplacement(sb,idCardStr);
			}
			matcher.appendTail(sb);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}
	
	
	

}
