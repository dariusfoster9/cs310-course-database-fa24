package edu.jsu.mcis.cs310.coursedb.dao;


import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class SectionDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        JsonArray jArray=new JsonArray();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                ps=conn.prepareStatement(QUERY_FIND);
                ps.setInt(1,termid);
                ps.setString(2,subjectid);
                ps.setString(3,num);
                rs=ps.executeQuery();
                //-----------------------------------
               while(rs.next()){
                   JsonObject jObj=new JsonObject();
                   jObj.put("termid",rs.getInt("termid"));
                   jObj.put("subjectid",rs.getString("subjectid"));
                   jObj.put("num",rs.getString("num"));
                   jArray.add(jObj);
                   result=jArray.toString();
               }
                //------------------------------------
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}