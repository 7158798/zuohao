package com.ruizton.util;


import com.ruizton.main.log.LOG;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ReflectHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/27.
 */
public class ReflectHelper {

    private ReflectHelper() {
    }

    /**
     * Gets field by field name.
     *
     * @param obj       the obj
     * @param fieldName the field name
     * @return the field by field name
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * Gets value by field name.
     *
     * @param obj       the obj
     * @param fieldName the field name
     * @return the value by field name
     * @throws SecurityException the security exception
     * @throws SecurityException the security exception
     * @throws SecurityException the security exception
     * @throws SecurityException the security exception
     */
    public static <T> T getValueByFieldName(Object obj, String fieldName)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        Field field = getFieldByFieldName(obj, fieldName);
        T value = null;

        if (field != null) {
            if (field.isAccessible()) {
                value = (T) field.get(obj);
            } else {
                field.setAccessible(true);
                value = (T) field.get(obj);
                field.setAccessible(false);
            }
        }

        return value;
    }

    /**
     * Sets value by field name.
     *
     * @param obj       the obj
     * @param fieldName the field name
     * @param value     the value
     * @throws SecurityException the security exception
     * @throws SecurityException the security exception
     * @throws SecurityException the security exception
     * @throws SecurityException the security exception
     */
    public static void setValueByFieldName(Object obj, String fieldName, Object value)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        try {
            Field field = obj.getClass().getDeclaredField(fieldName);

            if (field != null) {
                if (field.isAccessible()) {
                    field.set(obj, value);
                } else {
                    field.setAccessible(true);
                    field.set(obj, value);
                    field.setAccessible(false);
                }
            }

        } catch (Exception ex) {

        }
    }

    /**
     * Gets class.
     *
     * @param className the class name
     * @return the class
     */
    public static Class getClass(String className) {
        Class cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            LOG.e(ReflectHelper.class, "getClass" + className, ex);
        }
        return cls;
    }


    /**
     * New class instance.
     *
     * @param <T>       the type parameter
     * @param className the class name
     * @return the t
     */
    public static <T extends Object> T newClassInstance(String className) {

        T t = null;
        Class<T> cls = ReflectHelper.getClass(className);

        if (cls != null) {
            try {
                t = cls.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LOG.e(ReflectHelper.class, "newClassInstance" + className, e);
            }
        }

        return t;
    }

    /**
     * New class instance.
     *
     * @param <T>            the type parameter
     * @param className      the class name
     * @param parameterTypes the parameter types
     * @param paras          the paras
     * @return the t
     */
    public static <T extends Object> T newClassInstance(String className, Class[] parameterTypes, Object[] paras) {

        T t = null;
        Class cls = ReflectHelper.getClass(className);
        if (cls != null) {
            try {
                Constructor<T> constructor = cls.getConstructor(parameterTypes);
                if (constructor != null) {
                    t = constructor.newInstance(paras);
                }
            } catch (SecurityException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t;
    }


    /**
     * New class instance.
     *
     * @param <T>       the type parameter
     * @param className the class name
     * @param paras     the paras
     * @return the t
     */
    public static <T extends Object> T newClassInstance(String className, Object[] paras) {

        T t = null;

        if (paras != null) {
            int length = paras.length;
            Class[] parameterTypes = new Class[length];

            for (int i = 0; i < length; ++i) {
                parameterTypes[i] = paras[i].getClass();
                LOG.d(ReflectHelper.class, "classParas[i]=" + parameterTypes[i]);
            }

            t = newClassInstance(className, parameterTypes, paras);
        }

        return t;
    }


    /**
     * Is base date type.
     *
     * @param field the field
     * @return the boolean
     */
    public static boolean isBaseDateType(Field field) {

        Class<?> clazz = field.getType();

        return clazz.equals(String.class)
                || clazz.equals(Integer.class)
                || clazz.equals(Byte.class)
                || clazz.equals(Long.class)
                || clazz.equals(Double.class)
                || clazz.equals(Float.class)
                || clazz.equals(Character.class)
                || clazz.equals(Short.class)
                || clazz.equals(Boolean.class)
                || clazz.equals(Date.class)
                || clazz.equals(Date.class)
                || clazz.equals(java.sql.Date.class)
                || clazz.isPrimitive();
    }

    /**
     * 主要提供给查看API 列表完整路径的工具
     * <p>
     * Print all static field value.
     *
     * @param context the context
     * @param clz     the clz
     * @throws IllegalAccessException the illegal access exception
     */
    public static void printAllStaticFieldValue(Object context, Class<?> clz) throws IllegalAccessException {

        LOG.dStart(context, clz.getName());

        Field[] fields = clz.getDeclaredFields();

        for (Field field : fields) {
            LOG.d(context, field.getName() + " = " + field.get(clz));
        }

        LOG.dEnd(context, clz.getName());
    }


    /**
     * Gets field set.
     *
     * @param obj the obj
     * @return the field set
     */
    public static Set<Field> getFieldSet(Object obj) {

        Set<Field> fieldSet = new HashSet<>();
        Set<String> fieldNameSet = new HashSet<>();

        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {

            Field[] fields = superClass.getDeclaredFields();

            for (Field field : fields) {
                String fieldName = field.getName();

                if (!fieldNameSet.contains(fieldName)) {
                    fieldNameSet.add(fieldName);
                    fieldSet.add(field);
                } else {
                    LOG.d("", "skip: parent class exist same field.");
                }
            }
        }

        LOG.d("", "size=" + fieldSet.size());
        return fieldSet;
    }


    /**
     * Update final static value.
     *
     * @param field    the field
     * @param newValue the new value
     */
    public static void updateFinalStaticValue(Field field, Object newValue) {

        try {
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, newValue);
        } catch (Exception e) {
            LOG.e(ReflectHelper.class, "e", e);
        }
    }
}
