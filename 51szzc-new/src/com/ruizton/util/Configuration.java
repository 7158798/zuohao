package com.ruizton.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

 
/**
 * @author lu.li
 *
 */
public class Configuration extends PropertyPlaceholderConfigurer {
    private static final Map<String, String> propertiesMap = new HashMap<String, String>();

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws
			BeansException {
		super.processProperties(beanFactoryToProcess, props);

		for (Object key : props.keySet()) {
			String keyStr = key.toString();

			propertiesMap.put(keyStr, props.getProperty(keyStr));
		}
	}

	public static String getValue(String key) {
		return propertiesMap.get(key);
	}

	public static String getValue(String key, String def) {
		String value = getValue(key);

		return value != null ? value : def;
	}
}
