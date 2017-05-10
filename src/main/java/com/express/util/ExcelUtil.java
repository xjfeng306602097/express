package com.express.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class ExcelUtil {

	/**
	 * 
	 * @param titles
	 *            标题list
	 * @param row
	 *            对应的行
	 * @param style
	 *            单元格样式
	 */
	public static void setTitle(List<String> titles, HSSFRow row, HSSFCellStyle style) {
		HSSFCell cell = null;
		for (int i = 0; i < titles.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(titles.get(i));
			cell.setCellStyle(style);
		}
	}

	public static <T> void setRowContent(HSSFRow row, T obj)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException, NoSuchMethodException, SecurityException {
		Field[] fields = obj.getClass().getDeclaredFields();
		Method[] methods = obj.getClass().getDeclaredMethods();
		int i = 0;
		for (Field field : fields) {
			try {
				String fieldType = field.getType().getSimpleName();
				String fieldGetName = parseGetName(field.getName());
				Object value = null;
				if (!checkGetMet(methods, fieldGetName)) {
					continue;
				}
				Method fieldGetMet = obj.getClass().getMethod(fieldGetName, new Class[] {});
				value = fieldGetMet.invoke(obj, new Object[] {});
				if (value != null){
					if (fieldType.equals("Date")){
						row.createCell(i).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) value));
					} else if (fieldType.equalsIgnoreCase("Long")) {
						row.createCell(i).setCellValue(Long.parseLong(value.toString()));
					} else if (fieldType.equalsIgnoreCase("Double")) {
						row.createCell(i).setCellValue(Double.parseDouble(value.toString()));
					} else if (fieldType.equals("Integer")||fieldType.equals("int")){
						row.createCell(i).setCellValue(Integer.parseInt(value.toString()));
					} else if ("String".equals(fieldType)){
						row.createCell(i).setCellValue(value.toString());
					} else {
						row.createCell(i).setCellValue("");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
	}

	private static String parseGetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		int startIndex = 0;
		if (fieldName.charAt(0) == '_')
			startIndex = 1;
		return "get" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
				+ fieldName.substring(startIndex + 1);
	}

	/**
	 * 判断是否存在对应属性的get方法
	 * 
	 * @param methods
	 * @param fieldGetMet
	 * @return
	 */
	public static boolean checkGetMet(Method[] methods, String fieldGetMet) {
		for (Method met : methods) {
			if (fieldGetMet.equals(met.getName())) {
				return true;
			}
		}
		return false;
	}
	
	/** 
     * 格式化string为Date 
     *  
     * @param datestr 
     * @return date 
     */  
    public static Date parseDate(String datestr) {  
        if (null == datestr || "".equals(datestr)) {  
            return null;  
        }  
        try {  
            String fmtstr = null;  
            if (datestr.indexOf(':') > 0) {  
                fmtstr = "yyyy-MM-dd HH:mm:ss";  
            } else {  
                fmtstr = "yyyy-MM-dd";  
            }  
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr);  
            return sdf.parse(datestr);  
        } catch (Exception e) {  
            return null;  
        }  
    } 
	
}
