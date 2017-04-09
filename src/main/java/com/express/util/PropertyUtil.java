package com.express.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {

	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

	private static final String RESOURCENAME = "express.zh_CN.properties";

	private static ResourceBundle bundle = null;

	/**
	 * 读取工程目录下的properties文件路径，现在还没头绪
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String getProperty(String key) throws IOException {
		if (bundle == null) {
			InputStream pis = PropertyUtil.class.getClassLoader().getResourceAsStream(RESOURCENAME);
			bundle = new PropertyResourceBundle(pis);
			pis.close();
			try {
				return bundle.getString(key);
			} catch (MissingResourceException e1) {
				logger.error("There is no " + key + " in " + RESOURCENAME);
				throw e1;
			}
		} else {
			return bundle.getString(key);
		}
	}

}
