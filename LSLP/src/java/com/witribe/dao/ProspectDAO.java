/*
 * LeadDAO.java
 *
 * Created on January 8, 2009, 10:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.dao;

import com.witribe.constants.WitribeConstants;
import com.witribe.util.DBUtil;
import com.witribe.vo.LeadVO;
import com.witribe.vo.ProspectVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SC43278
 */
public class ProspectDAO {
    
    /** Creates a new instance of LeadDAO */
    public ProspectDAO() {
    }
    public long getMaxKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT MAX(PROSPECT_ID) from PROSPECT_DETAILS");
            if(rs.next()) {
               maxKey = rs.getLong(1);
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
        }
        return  maxKey;
    }
    public boolean createProspect(ProspectVO objProspectVO,Connection con) throws SQLException,Exception{
      //  boolean status1 = false;
       // boolean status2 = false;
        boolean status = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String reasonCode = null;
        String query = "";
        //con.setAutoCommit(false);
        try{
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
             pstmt = con.prepareStatement(
                    "select REASON_CODE from MST_PROSPECT_REASON where REASON_ID=?");
             pstmt.setInt(1,objProspectVO.getReasonId());
             rs = pstmt.executeQuery();
             if(rs.next()){
               reasonCode = rs.getString(1);
             }
             
             pstmt = con.prepareStatement(
                    "UPDATE PROSPECT_DETAILS SET REASON = ?,CREATE_DATE = ?,TRANSITION_STATE = ?,REASON_COMMENT = ? " +
                    " WHERE  LEAD_ID = ? ");
             pstmt.setString(1, reasonCode);
             pstmt.setTimestamp(2, sqlDate);
             pstmt.setString(3, objProspectVO.getTransitionState());
             pstmt.setString(4, objProspectVO.getReasonComment());      
             pstmt.setString(5, objProspectVO.getLeadId());
             if(!(pstmt.executeUpdate() > WitribeConstants.ZERO)){
                //status1 = true;            
                pstmt = con.prepareStatement(
                        "INSERT INTO PROSPECT_DETAILS (PROSPECT_ID,LEAD_ID,REASON,CREATE_DATE,TRANSITION_STATE,REASON_COMMENT)" +
                        "VALUES " +
                        "(?,?,?,?,?,?)");
                long key = getMaxKey(con);
                pstmt.setLong(1, ++key);
                pstmt.setString(2, objProspectVO.getLeadId());
                pstmt.setString(3, reasonCode);
                pstmt.setTimestamp(4, sqlDate);
                pstmt.setString(5, objProspectVO.getTransitionState());
                pstmt.setString(6, objProspectVO.getReasonComment());
                pstmt.executeUpdate();
              /* if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                    status1 = true;
                }*/
              }
             
            // Added Bhawana on 15th Sep
                 String followUpD=objProspectVO.getFollowUpDate();
                if(followUpD != null && !"".equals(followUpD)){
                    query = ",FOLLOWUP_DATE = to_date(?,'dd-mm-yyyy HH24:MI:SS') where LEAD_ID = ?";
                } else {
                    query = "where LEAD_ID = ?";
                }
                pstmt = con.prepareStatement(
                        "UPDATE LEAD_DETAILS set LEAD_STATUS = ?, LEAD_STATUS_NEW = ?, LEAD_TAG= ? "+query);
               
                pstmt.setString(1,WitribeConstants.PROSPECT_CREATED);
                pstmt.setString(2,reasonCode);
                 pstmt.setString(3,objProspectVO.getTransitionState());
                 if(followUpD != null && !"".equals(followUpD)){
                    pstmt.setString(4,objProspectVO.getFollowUpDate());   
                    pstmt.setString(5,objProspectVO.getLeadId());    
                 } else {
                 pstmt.setString(4,objProspectVO.getLeadId());   
                 }               
            pstmt.executeUpdate();
             // End
           /* if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status2 = true;
            }
           if(status1 && status2){
                con.commit();
            } else{
                con.rollback();
            }*/
            status = true; 
            //con.commit();
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
           DBUtil.closeStatement(pstmt);
           throw e;
        } finally{
            //con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
    }
    public long getHistoryKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT lead_history_sequence.nextval from LEAD_HISTORY");
            if(rs.next()) {
               maxKey = rs.getLong(1);
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
        }
        return  maxKey;
    }
     public boolean createLeadHistory(ProspectVO objProspectVO,Connection con) throws SQLException,Exception{
         boolean status = false;
         boolean status1 = false;
         boolean status2 = false;
         boolean status3 = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String reasonCode = null;
        String query = "";
        con.setAutoCommit(false);
        try{
            long key = getHistoryKey(con);
             pstmt = con.prepareStatement(
                    "select REASON_CODE from MST_PROSPECT_REASON where REASON_ID=?");
             pstmt.setInt(1,objProspectVO.getReasonId());
             rs = pstmt.executeQuery();
             if(rs.next()){
               reasonCode = rs.getString(1);
             }
            pstmt = con.prepareStatement(
                    "INSERT INTO LEAD_HISTORY (LEAD_HISTORY_ID,LEAD_ID,SALESPERSONNEL_ID,MODIFIED_DATE,STATUS,LEAD_COMMENT,TRANSITION_STATE)" +
                    "VALUES " +
                    "(?,?,?,?,?,?,?)");
            
            pstmt.setLong(1, ++key);
            pstmt.setString(2, objProspectVO.getLeadId());
            pstmt.setString(3, objProspectVO.getSalesId());
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(4, sqlDate);
            pstmt.setString(5, reasonCode);
            pstmt.setString(6, objProspectVO.getReasonComment());
            pstmt.setString(7, objProspectVO.getTransitionState());
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
            }
            if(status && objProspectVO.getTransitionState().equalsIgnoreCase(WitribeConstants.TRANSITION_STATE_PROSPECT)){
                status1 = createProspect(objProspectVO,con);
                
            } else {
                // Added Bhawana on 15th Sep
                 String followUpD=objProspectVO.getFollowUpDate();
                if(followUpD != null && !"".equals(followUpD)){
                    query = ",FOLLOWUP_DATE = to_date(?,'dd-mm-yyyy HH24:MI:SS') where LEAD_ID = ?";
                } else {
                    query = "where LEAD_ID = ?";
                }
                pstmt = con.prepareStatement(
                        "UPDATE LEAD_DETAILS set LEAD_STATUS = ?, LEAD_STATUS_NEW = ?, LEAD_TAG= ? "+query);
               
                pstmt.setString(1,WitribeConstants.ASSIGNED);
                pstmt.setString(2,reasonCode);
                 pstmt.setString(3,objProspectVO.getTransitionState());
                 if(followUpD != null && !"".equals(followUpD)){
                    pstmt.setString(4,objProspectVO.getFollowUpDate());   
                    pstmt.setString(5,objProspectVO.getLeadId());    
                 } else {
                 pstmt.setString(4,objProspectVO.getLeadId());   
                 }
                
                if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                    status2 = true;
                }// End
                pstmt = con.prepareStatement("select lead_id from prospect_details where lead_id = ?");
                pstmt.setString(1,objProspectVO.getLeadId());
                rs = pstmt.executeQuery();
                if(rs.next()){
                   pstmt = con.prepareStatement("delete from prospect_details where lead_id = ?") ;
                   pstmt.setString(1,objProspectVO.getLeadId());
                   if(pstmt.execute()){
                       status3 = true;
                   }
                } 
                
            }
            
                          
        } catch(SQLException se){
           con.rollback();
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            con.rollback();
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
           con.commit();
           con.setAutoCommit(true);
           DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
    }  
     public long getProspectRowCount(Connection con,String whereCond) throws SQLException,Exception{
        long rowsCount = WitribeConstants.ROWS_COUNT;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("select count(*) from LEAD_DETAILS l,PROSPECT_DETAILS p "+whereCond);
            if(rs.next()) {
                rowsCount = rs.getLong(1);
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
        }
        return  rowsCount;
    }
      public List getPageProspects(int pageNum,Connection con,String salesId) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        long totalRows;
       String whereCond = " where UPPER(LEAD_STATUS) =UPPER('"+WitribeConstants.PROSPECT_CREATED+"') AND  l.LEAD_ID = p.LEAD_ID AND (l.ASSIGNED_TO = '"+salesId+"' or l.ASSIGNED_TO_TL = '"+salesId+"') ";
       int startRow = 0;
       int endRow = 0;
        try{
            startRow = (pageNum*maxRowCount)+1;
            endRow = (pageNum+1)*maxRowCount+1;
             pstmt = con.prepareStatement("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                   "SELECT  l.LEAD_ID,FIRST_NAME," +
                   "LAST_NAME,EMAIL,CONTACT_NO," +
                   "ADDR_PLOT,ADDR_STREET,SALES_SUBZONE,SALES_ZONE," +
                   "SALES_CITY,SALES_PROVINCE,ADDR_COUNTRY,ADDR_ZIP," +
                    "LEAD_SOURCE," +
                   "PACKAGE_INFO,SERVICE_TYPE,WILLING_PAY_TO_BROADBAND," +
                   "BROADBAND_TYPE ,PRIORITY,LEAD_GENERATED_DATE,p.REASON " +
                   "FROM LEAD_DETAILS l,PROSPECT_DETAILS p " + whereCond +
                     " ORDER BY  l.LEAD_GENERATED_DATE asc ) a WHERE rownum <="+endRow+")"+
                    "WHERE rn >="+startRow,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
             totalRows = getProspectRowCount(con,whereCond);
             rs = pstmt.executeQuery();
             // Check ResultSet's scrollability
            /*
            if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }*/
             objList = new ArrayList();
             int count = WitribeConstants.ZERO;
             while(rs.next()){
               count++;
               if(count>maxRowCount) {
                   objList.add("true");
                   break;
               }
               LeadVO objLeadVO = new LeadVO();
               objLeadVO.setLeadId(rs.getString(1)); 
               objLeadVO.setFirstname(rs.getString(2));
               objLeadVO.setLastname(rs.getString(3));
               objLeadVO.setEmail(rs.getString(4));
               objLeadVO.setContactnumber(rs.getString(5));
               objLeadVO.setPlot(rs.getString(6));
               objLeadVO.setStreet(rs.getString(7));
               objLeadVO.setSubzone(rs.getString(8));
               objLeadVO.setZone(rs.getString(9));
               objLeadVO.setCity(rs.getString(10));
               objLeadVO.setProvince(rs.getString(11));
               objLeadVO.setCountry(rs.getString(12));
               objLeadVO.setZip(rs.getString(13));
               objLeadVO.setLeadsource(rs.getString(14));
               objLeadVO.setPackageinfo(rs.getString(15));
               objLeadVO.setServicetype(rs.getString(16));
               objLeadVO.setWillingPay(rs.getString(17));
               objLeadVO.setBroadbandtype(rs.getString(18));
               objLeadVO.setPriority(rs.getString(19));
               objLeadVO.setCreateDate(rs.getString(20));
               objLeadVO.setReason(rs.getString(21));
               objList.add(objLeadVO);             
               
           
          }   
            
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
           DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(pstmt);
        }
        
        return objList;
        
    }
   public List  getProspectReason(Connection con) throws SQLException,Exception{
       List objList = null;
       objList = new ArrayList();
       Statement stmt = null;
       ResultSet rs = null;
        try{
                stmt = con.createStatement(); 
                rs = stmt.executeQuery("select REASON_ID,REASON_CODE, LEAD_TAG from MST_PROSPECT_REASON");
            while(rs.next()){
                    ProspectVO reasonvo = new ProspectVO();
                    reasonvo.setReasonId(rs.getInt(1));
                    reasonvo.setReasonCode(rs.getString(2));
                    reasonvo.setTransitionState(rs.getString(3));
                    objList.add(reasonvo);
          }     
        } catch(SQLException se){
           DBUtil.closeStatement(stmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(stmt);
            throw e;
        } finally{
           DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(stmt);
        }
       
       return objList;
    }
   public ProspectVO getTransitionState(Connection con,ProspectVO objProspectVO) throws SQLException,Exception{
       PreparedStatement pstmt = null;
       ResultSet rs = null;
        try{
                pstmt = con.prepareStatement("select LEAD_TAG from MST_PROSPECT_REASON where REASON_ID=?");
                pstmt.setInt(1,objProspectVO.getReasonId());
                rs = pstmt.executeQuery();
            while(rs.next()){
                    //ProspectVO reasonvo = new ProspectVO();
                    objProspectVO.setTransitionState(rs.getString(1));
                    
          }     
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
           DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(pstmt);
        }
       
       return objProspectVO;  
   }
}
