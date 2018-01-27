package com.jucaifu.common.property;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Created by zhaiyz on 15-12-7.
 */
public class PropertiesUtils extends PropertyPlaceholderConfigurer {

    private static final Map<String, String> propertiesMap = new HashMap<>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws
            BeansException {
        super.processProperties(beanFactoryToProcess, props);

        for (Object key : props.keySet()) {
            String keyStr = key.toString();

            propertiesMap.put(keyStr, props.getProperty(keyStr));
        }
    }

    public static String getProperty(String key) {
        return propertiesMap.get(key);
    }

    public static String getProperty(String key, String def) {
        String value = getProperty(key);

        return value != null ? value : def;
    }
}
