package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    private static final String QUERY_CREATE = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
    
    private static final String QUERY_DELETEALL = "DELETE FROM registration WHERE termid = ? AND studentid = ?";
    
    private static final String QUERY_DELETE = "DELETE FROM registration WHERE termid = ? AND studentid = ? AND CRN = ?";
    
    private static final String QUERY_SELECT = "SELECT * FROM registration WHERE studentid = ? AND termid = ? ORDER BY crn";
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                ps=conn.prepareStatement(QUERY_CREATE);
                ps.setInt(1,studentid);
                ps.setInt(2,termid);
                ps.setInt(3,crn);
                
                result=ps.executeUpdate()>0;
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                ps=conn.prepareStatement(QUERY_DELETE);
                ps.setInt(1,studentid);
                ps.setInt(2,termid);
                ps.setInt(3,crn);
                
                result=ps.executeUpdate()>0;
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                ps=conn.prepareStatement(QUERY_DELETEALL);
                ps.setInt(1,studentid);
                ps.setInt(2,termid);
                
                result=ps.executeUpdate()>0;
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public StringBuilder list(int studentid, int termid) {
        
        StringBuilder result=new StringBuilder();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                ps=conn.prepareStatement(QUERY_SELECT);
                ps.setInt(1,studentid);
                ps.setInt(2,termid);
                rs=ps.executeQuery();
                //--------------------------------------
                rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                
                
                while (rs.next()){
                    for (int i = 1; i <= columnCount; i++){
                        result.append(rsmd.getColumnName(i))
                                .append(": ")
                                .append(rs.getString(i))
                                .append(", ");
                    }
                    
                    result.append("\n");
                }
                //--------------------------------------
                
                
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
