package com.uuwatch.spider.manager.util;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import bsh.This;

/**
 * 读取配置文件工具类
 * @author IYFT
 *
 */
public class ReadProperties {
	
	public static String filePath = "/config/system.properties";  // 配置文件路径

	/**
	 * 获取配置文件中的属性
	 * @param key
	 * 			属性文件中的key
	 * @return
	 */
	public static String getProperty(final String key) {
		final Properties properties = new Properties();
		String value = "";
		InputStream in = null;
		try {
			
			in = new BufferedInputStream(This.class.getResourceAsStream(filePath));

			properties.load(in);

			value = properties.getProperty(key);

		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		return value;
	}

	public static void main(String[] args) {
		// 测试读取
		final String start = ReadProperties.getProperty("abc");
		System.out.println(start);
	}

}
