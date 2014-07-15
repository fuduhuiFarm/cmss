package com.shop.cms.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Json {
	public static String toJson(Object obj) {
		String s = castToJson(obj);
		if (s != null) {
			return s;
		}
		return toJson(getAttributes(obj));
	}

	public static String toJson(Map<String, Object> map) {
		String result = "";
		for (String name : map.keySet()) {
			Object value = map.get(name);
			String s = castToJson(value);
			if (s != null) {
				result = result + "\"" + name + "\":" + s + ",";
			} else if ((value instanceof List)) {
				String v = toJson((List) value);
				result = result + "\"" + name + "\":" + v + ",";
			} else if ((value instanceof Object[])) {
				String v = toJson((Object[]) value);
				result = result + "\"" + name + "\":" + v + ",";
			} else if ((value instanceof Map)) {
				Map attr = castMap((Map) value);
				attr = removeListAttr(attr);
				result = result + "\"" + name + "\":" + toJson(attr) + ",";
			} else if (!value.getClass().getName().startsWith("java")) {
				Map attr = getAttributes(value);
				attr = removeListAttr(attr);
				result = result + "\"" + name + "\":" + toJson(attr) + ",";
			} else {
				result = result + "\"" + name + "\":" + "\"" + value.toString() + "\",";
			}
		}
		if (result.length() == 0) {
			return "{}";
		}
		return "{" + result.substring(0, result.length() - 1) + "}";
	}

	public static String toJson(Object[] aa) {
		if (aa.length == 0) {
			return "[]";
		}
		String result = "";
		Object[] arrayOfObject = aa;
		int j = aa.length;
		for (int i = 0; i < j; i++) {
			Object obj = arrayOfObject[i];
			String s = castToJson(obj);
			if (s != null) {
				result = result + s + ",";
			} else if ((obj instanceof Map)) {
				Map map = castMap((Map) obj);
				map = removeListAttr(map);
				result = result + toJson(map) + ",";
			} else {
				Map attr = getAttributes(obj);
				attr = removeListAttr(attr);
				result = result + toJson(attr) + ",";
			}
		}
		return "[" + result.substring(0, result.length() - 1) + "]";
	}

	public static String toJson(List<?> ll) {
		return toJson(ll.toArray());
	}

	public static Map<String, Object> getAttributes(Object obj) {
		Class c = obj.getClass();
		try {
			Method method = c.getMethod("isProxy", new Class[0]);
			Boolean result = (Boolean) method.invoke(obj, new Object[0]);
			if (result.booleanValue())
				c = c.getSuperclass();
		} catch (Exception localException) {
		}
		Map attr = new HashMap();

		for (Field f : c.getFields()) {
			try {
				Object value = f.get(obj);
				attr.put(f.getName(), value);
			} catch (Exception localException1) {
			}
		}
		for (Method m : c.getDeclaredMethods()) {
			String mname = m.getName();
			if (mname.equals("getClass"))
				continue;
			if (mname.startsWith("get")) {
				String pname = mname.substring(3);
				if (pname.length() == 1)
					pname = pname.toLowerCase();
				else {
					pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
				}
				try {
					Object value = m.invoke(obj, new Object[0]);
					attr.put(pname, value);
				} catch (Exception localException2) {
				}
			} else if (mname.startsWith("is")) {
				String pname = mname.substring(2);
				if (pname.length() == 1)
					pname = pname.toLowerCase();
				else {
					pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
				}
				try {
					Object value = m.invoke(obj, new Object[0]);
					attr.put(pname, value);
				} catch (Exception localException3) {
				}
			}
		}
		return (Map<String, Object>) attr;
	}

	private static String castToJson(Object obj) {
		if (obj == null)
			return "null";
		if ((obj instanceof Boolean))
			return obj.toString();
		if (((obj instanceof Integer)) || ((obj instanceof Long)) || ((obj instanceof Float))
				|| ((obj instanceof Double)) || ((obj instanceof Short)) || ((obj instanceof BigInteger))
				|| ((obj instanceof BigDecimal)))
			return obj.toString();
		if ((obj instanceof String)) {
			String v = (String) obj;
			v = v.replaceAll("\\\\", "\\\\\\\\");
			v = v.replaceAll("\n", "\\\\n");
			v = v.replaceAll("\r", "\\\\r");
			v = v.replaceAll("\"", "\\\\\"");
			v = v.replaceAll("'", "\\\\'");
			return "\"" + v + "\"";
		}
		if ((obj instanceof java.sql.Date)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date v = (java.sql.Date) obj;
			String s = df.format(new java.util.Date(v.getTime()));
			return "\"" + s + "\"";
		}
		if ((obj instanceof java.util.Date)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date v = (java.util.Date) obj;
			String s = df.format(v);
			return "\"" + s + "\"";
		}
		if ((obj instanceof Timestamp)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Timestamp v = (Timestamp) obj;
			String s = df.format(new java.util.Date(v.getTime()));
			return "\"" + s + "\"";
		}
		return null;
	}

	public static Map<String, Object> castMap(Map<?, ?> map) {
		Map newMap = new HashMap();
		for (Iterator localIterator = map.keySet().iterator(); localIterator.hasNext();) {
			Object key = localIterator.next();
			newMap.put(key.toString(), map.get(key));
		}
		return newMap;
	}

	private static Map<String, Object> removeListAttr(Map<String, Object> map) {
		Map newMap = new HashMap();
		for (Map.Entry en : map.entrySet()) {
			if (!(en.getValue() instanceof List)) {
				newMap.put((String) en.getKey(), en.getValue());
			}
		}
		return newMap;
	}

}
