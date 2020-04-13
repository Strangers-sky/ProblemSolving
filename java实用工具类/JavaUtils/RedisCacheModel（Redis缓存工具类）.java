package com.uuwatch.core.proxy.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.uuwatch.core.proxy.util.RedisCacheModel;

/**
 * redis缓存工具类
 * @author heyuanbing
 *
 */
public class RedisCacheModel {

	private String fieldName;
	private String fieldValue;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	public static List<RedisCacheModel> getObjAttr(Object obj)  
	{  
		List<RedisCacheModel> cmls=new ArrayList<RedisCacheModel>();
	    // 获取对象obj的所有属性域  
	    Field[] fields = obj.getClass().getDeclaredFields();  
	      
	    for (Field field : fields)  
	    {  
	    	
	    	RedisCacheModel cm=new RedisCacheModel();
	        // 对于每个属性，获取属性名  
	        String varName = field.getName();  
	        try  
	        {  
	            boolean access = field.isAccessible();  
	            if(!access) field.setAccessible(true);  
	              
	            //从obj中获取field变量  
	            Object o = field.get(obj);  
	            
	            cm.setFieldName(varName);
	            cm.setFieldValue(String.valueOf(o));
	            cmls.add(cm);
	            
	            if(!access) field.setAccessible(false);  
	        }  
	        catch (Exception ex)  
	        {  
	           // ex.printStackTrace();  
	           return cmls;
	        }  
	    }
	    return cmls;
	} 
	
	
}
