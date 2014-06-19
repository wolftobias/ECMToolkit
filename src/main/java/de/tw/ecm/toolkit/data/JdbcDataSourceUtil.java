package main.java.de.tw.ecm.toolkit.data;

public class JdbcDataSourceUtil {

    public static String createSelectStatement(String table, String... cols) {
        String queryString = "SELECT ";
        
        if(cols != null && cols.length > 0) {
	        for (int i = 0;i < cols.length; i++) {
	        	queryString += cols[i];
	            queryString += ", ";
	        }
        }else
        	queryString += "*";
        
        queryString += " FROM " + table;
        return queryString.toUpperCase();
    }	

}
