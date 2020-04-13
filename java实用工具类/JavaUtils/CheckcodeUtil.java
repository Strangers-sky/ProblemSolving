package com.uuwatch.spider.manager.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证码识别
 * 
 */
public class CheckcodeUtil {

	private static Logger logger = LoggerFactory.getLogger(CheckcodeUtil.class);

	/**
	 * 验证码识别 CMD调用tesseract实现
	 * 
	 * @param imgPath
	 *            验证码图片路径
	 * @return 验证码字符串
	 */
	@SuppressWarnings("resource")
	public static String getCheckCode(String imgPath) {
		String checkCode = null;

		String systemName = System.getProperty("os.name");
		if (!StringUtils.isEmpty(systemName) && systemName.indexOf("Windows") != -1) {
			imgPath = imgPath.replace("/", "\\");
		}

		BufferedReader buf = null;
		Runtime runtime = Runtime.getRuntime();

		String outPath = imgPath.substring(0, imgPath.lastIndexOf("."));

		String command = "tesseract " + imgPath + " " + outPath;

		try {
			Process ps = runtime.exec(command);
			ps.waitFor();
			// 读取文件
			File file = new File(outPath + ".txt");
			buf = new BufferedReader(new FileReader(file));
			String temp = "";
			StringBuffer sb = new StringBuffer();
			while ((temp = buf.readLine()) != null) {
				sb.append(temp);
			}
			// 文字结果
			checkCode = sb.toString();
			if (StringUtils.isNotEmpty(checkCode))
				checkCode = checkCode.replaceAll(" ", "");
		} catch (Exception e) {
			logger.error("验证码识别异常 ：" + e);
		}
		return checkCode;
	}

}
