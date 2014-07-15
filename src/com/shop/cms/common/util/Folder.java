package com.shop.cms.common.util;

public class Folder  {
	//创建文件夹

	public static void createDir(String path) {
		java.io.File dir = new java.io.File(path);
		if (!dir.exists()) {
			dir.mkdir();// 如果文件夹不存在，则创建

		}
	}
	
	//根据日期创建文件夹

	public static String DateFormatFolder(String path){
		String yearFormat=DateFormat.getDate("yyyy");
		String monthFormat=DateFormat.getDate("MM");
		String dayFormat=DateFormat.getDate("dd");
		createDir(path+"/"+yearFormat);
		createDir(path+"/"+yearFormat+"/"+monthFormat);
		createDir(path+"/"+yearFormat+"/"+monthFormat+"/"+dayFormat);
		return path+"/"+yearFormat+"/"+monthFormat+"/"+dayFormat+"/";
	}
	
	public static void main(String[] args) {
		System.out.println(Folder.DateFormatFolder("d:/test"));
	}
	
	
}
