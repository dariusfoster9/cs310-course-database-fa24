package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {
                ResultSetMetaData rsmd=rs.getMetaData();
                int ColumnCount=rsmd.getColumnCount();
                //----------------------------------
                while(rs.next()){
                    JsonObject jsonObject=new JsonObject();
                    for(int i=1; i<=ColumnCount; i++){
                        String ColumnName=rsmd.getColumnName(i);
                        Object value=rs.getObject(i);
                        jsonObject.put(ColumnName,value);
                    }
                    records.add(jsonObject);

                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}
