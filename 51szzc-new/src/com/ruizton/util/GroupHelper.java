package com.ruizton.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据分组工具类
 *
 */
public class GroupHelper {

	/**
	 *  按key集合数据进行分组
     * @param clz			Class
	 * @param fieldName	属性名称
	 * @param dataList		数据集合
	 * @param <T>
	 * @return
     * @throws Exception
     */
	public static <T> Map<Object,T> groupByFieldName(Class<T> clz,String fieldName,List<T> dataList) throws Exception {
		Map<Object,T> map = new HashMap<>();
        if (dataList == null || dataList.size() == 0){
            // 不存在数据的场合，不处理分组
            return map;
        }
		for (T bean : dataList){
			// 取出到分组的值
			Object value = ReflectHelper.getValueByFieldName(bean, fieldName);
			map.put(value, bean);
		}
		return map;
	}

	/**
	 * 按key集合数据进行分组  key 不唯一
	 * @param clz			Class
	 * @param fieldName	属性名称
	 * @param dataList		数据集合
	 * @param <T>
	 * @return
     * @throws Exception
     */
	public static <T> Map<Object,List<T>> groupListByFieldName(Class<T> clz,String fieldName,List<T> dataList) throws Exception {
		
		Map<Object,List<T>> map = new HashMap<>();
		
		for(T bean:dataList) {
			// 取出到分组的值
			Object value = ReflectHelper.getValueByFieldName(bean, fieldName);
			List<T> list = map.get(value);
			if(null == list) {
				list = new ArrayList<T>();
				map.put(value, list);
			}
			list.add(bean);
		}
		
		return map;
	}
}
