package com.jucaifu.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.jucaifu.common.log.LOG;

/**
 * PropertiesHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/29.
 */
public class PropertiesHelper {

    private static PropertiesHelper sInstance = null;

    private Map<String, Properties> propMap = null;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PropertiesHelper getInstance() {

        if (sInstance == null) {
            synchronized (PropertiesHelper.class) {
                if (sInstance == null) {
                    sInstance = new PropertiesHelper();
                }
            }
        }

        return sInstance;
    }

    private PropertiesHelper() {
        this.propMap = new HashMap<String, Properties>();
    }


    /**
     * Load properties.
     *
     * @param name the name
     * @return the properties
     */
    public Properties load(String name) {

        Properties prop = null;

        if (this.propMap.get(name) != null) {

            prop = this.propMap.get(name);

        } else {

            try {
                InputStream inStream = PropertiesHelper.class.getResourceAsStream("/" + name);
                if (inStream != null) {
                    prop = new Properties();
                    prop.load(inStream);
                    this.propMap.put(name, prop);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return prop;
    }


    /**
     * Print all.
     *
     * @param name the name
     */
    public void printAll(String name) {

        Properties pps = load(name);

        if (pps != null) {

            Enumeration ppsEnums = pps.propertyNames();

            while (ppsEnums.hasMoreElements()) {
                String strKey = (String) ppsEnums.nextElement();
                String value = pps.getProperty(strKey);
                LOG.d(this, strKey + "=" + value);
            }

        } else {
            LOG.e(this, "Properties < " + name + " >not exist", new Exception(""));
        }
    }
}
