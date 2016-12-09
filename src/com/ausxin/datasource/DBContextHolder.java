package com.ausxin.datasource;

public class DBContextHolder {
	
	 public static final String DATA_SOURCE = "dataSource";  
	 public static final String MYSQL_DATA_SOURCE = "mysqlDataSource";
	 
	 private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
	 
	 public static void setDBType(String dbType) {  
		 contextHolder.set(dbType);  
	 }  
	 
	 public static String getDBType() {  
		 return contextHolder.get(); 
	 }  
	
	 public static void clearDBType() { 
	     contextHolder.remove();  
	 }  


}
