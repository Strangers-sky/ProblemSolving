package com.uuwatch.spider.manager.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断一个文本中是否含有Unicode编码的内容，并转化为中文。
 */
public class UnicodeUtils {

	public static String convertUnicode(String ori) {
		char aChar;
		int len = ori.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = ori.charAt(x++);
			if (aChar == '\\') {
				aChar = ori.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = ori.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);

		}
		return outBuffer.toString();
	}

	/**
	 * 匹配到10个Unicode编码的文本，则说明包含Unicode
	 * 
	 * @param txt
	 *            需要判断的文本
	 */
	public static boolean isHaveUnicode(String txt) {
		int num = regNums(txt);
		if (num > 10) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 匹配到的数量
	 */
	public static int regNums(String txt) {
		int num = 0;
		// Unicode文本匹配，专用正则
		String regEx = "\\\\u[4E00-9FA5][4E00-9FA5][4E00-9FA5][4E00-9FA5]";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// group分组,用()分组
		Matcher m = pattern.matcher(txt);
		while (m.find()) {
			// Test.convertUnicode(m.group());
			num++;
		}
		return num;
	}

	public static void main(String[] args) {
		String temp = "“\u5317\u4eac\u767e\u5ea6\u7f51\u8baf\u79d1\u6280\u6709\u9650\u516c\u53f8”";
		System.out.println(convertUnicode(temp));
	}
}
