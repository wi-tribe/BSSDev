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
import com.witribe.inventory.dao.RaiseRequestDAO;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.util.DBUtil;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadHistoryVO;
import com.witribe.vo.LeadISPVO;
import com.witribe.vo.LeadSourceVO;
import com.witribe.vo.LeadVO;
import customfields.WtbFldCommunicationType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.portal.pcm.FList;
import com.portal.pcm.Poid;
import com.portal.pcm.PortalOp;
import com.portal.pcm.PortalContext;
import com.portal.pcm.*;
import com.portal.pcm.fields.*;
import com.witribe.constants.WitribeConstants;
import java.util.Properties;


/**
 *
 * @author SC43278
 */
public class LeadDAO {
    
    /** Creates a new instance of LeadDAO */
    public LeadDAO() {
    }
    public long getMaxKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT MAX(LEAD_ID) from LEAD_DETAILS");
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
    public LeadVO assignSelfCSE(LeadVO objLeadVO,String salesId, Connection con) throws SQLException,Exception{
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT  sd.SALESPERSONNEL_ID,sd.EMAIL,sd.CONTACT_NUMBER FROM SALESPERSONNEL_DETAILS sd," +
                    "SALES_LOCATION sl Where  sd.SALESPERSONNEL_ID  = ? AND sd.SALESPERSONNEL_ID = sl.SALESPERSONNEL_ID" +
                    " AND UPPER(sl.LOCATION)= UPPER(?) AND UPPER(ADDR_ZONE)= UPPER(?) AND  UPPER(ADDR_CITY)= UPPER(?)" +
                    " AND UPPER(ADDR_COUNTRY)= UPPER(?) AND CHANNEL_TYPE IN(1)");
            pstmt.setString(1,salesId);
            pstmt.setString(2,objLeadVO.getSubzone());
            pstmt.setString(3,objLeadVO.getZone());
            pstmt.setString(4,objLeadVO.getCity());
            pstmt.setString(5,objLeadVO.getCountry());
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
               /* String cse = rs.getString(1);
                String email = rs.getString(2);
                String contact = rs.getString(3);*/
                objLeadVO.setAssignedCSE(salesId);
                objLeadVO.setSalesEmail( rs.getString(2));
                objLeadVO.setLeadStatus(WitribeConstants.ASSIGNED);
                objLeadVO.setSalesContact( rs.getString(3));
               /* if(!rs.next()) {
                    objLeadVO.setAssignedCSE(cse);
                    objLeadVO.setSalesEmail(email);
                    objLeadVO.setLeadStatus(WitribeConstants.ASSIGNED);
                    objLeadVO.setSalesContact(contact);
                }*/
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return  objLeadVO;
    }
    public LeadVO assignCSE(LeadVO objLeadVO,Connection con) throws SQLException,Exception{
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT  sd.SALESPERSONNEL_ID,sd.EMAIL,sd.CONTACT_NUMBER FROM SALESPERSONNEL_DETAILS sd," +
                    "SALES_LOCATION sl Where  SALESPERSONNEL_TYPE  = ? AND sd.SALESPERSONNEL_ID = sl.SALESPERSONNEL_ID" +
                    " AND UPPER(sl.LOCATION)= UPPER(?) AND UPPER(ADDR_ZONE)= UPPER(?) AND  UPPER(ADDR_CITY)= UPPER(?)" +
                    " AND UPPER(ADDR_COUNTRY)= UPPER(?) AND CHANNEL_TYPE IN(1) AND UPPER(SALESPERSON_STATUS) = UPPER('Active') ");
            pstmt.setInt(1,Integer.parseInt(WitribeConstants.TYPE_CSE));
            pstmt.setString(2,objLeadVO.getSubzone());
            pstmt.setString(3,objLeadVO.getZone());
            pstmt.setString(4,objLeadVO.getCity());
            pstmt.setString(5,objLeadVO.getCountry());
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                String cse = rs.getString(1);
                String email = rs.getString(2);
                String contact = rs.getString(3);
                if(!rs.next()) {
                    objLeadVO.setAssignedCSE(cse);
                    objLeadVO.setSalesEmail(email);
                    objLeadVO.setLeadStatus(WitribeConstants.ASSIGNED);
                    objLeadVO.setSalesContact(contact);
                }
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return  objLeadVO;
    }
    
    public LeadVO checkDuplicateLead(LeadVO objLeadVO,Connection con) throws SQLException,Exception{
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String Email = objLeadVO.getEmail();
        String contactno = objLeadVO.getContactnumber();
        String dbemail = null;
        String dbcontactno = null;
        LeadVO duplicateLeadVo = null;
        RaiseRequestDAO reqdao = new RaiseRequestDAO();
        try{
            pstmt = con.prepareStatement("select ld.lead_id,ld.email,ld.CONTACT_NO,ld.salespersonnel_id,ld.LEAD_GENERATED_DATE from LEAD_DETAILS ld where  ld.email = ? and ld.contact_no = ?");
            pstmt.setString(1,Email);
            pstmt.setString(2,contactno);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                duplicateLeadVo = new LeadVO();
                dbemail = rs.getString(2);
                dbcontactno = rs.getString(3);
                if(Email.equalsIgnoreCase(dbemail) && contactno.equalsIgnoreCase(dbcontactno)){
                    
                    duplicateLeadVo.setLeadId(rs.getString(1));
                    duplicateLeadVo.setEmail(rs.getString(2));
                    duplicateLeadVo.setContactnumber(rs.getString(3));
                    duplicateLeadVo.setSalesId(rs.getString(4));
                    if(duplicateLeadVo.getSalesId() != " "){
                        String salesPersonName = reqdao.fetchName(con,duplicateLeadVo.getSalesId());
                        duplicateLeadVo.setFullname(salesPersonName);
                    } else {
                        duplicateLeadVo.setFullname(" ");
                    }
                    duplicateLeadVo.setCreateDate(rs.getString(5));                           
                } 
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return  duplicateLeadVo;
    }
    public LeadVO checkDuplicateLeadByOnline(LeadVO objLeadVO,Connection con) throws SQLException,Exception{
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String Email = objLeadVO.getEmail();
        String contactno = objLeadVO.getContactnumber();
        String dbemail = null;
        String dbcontactno = null;
        LeadVO duplicateLeadVo = null;
        try{
            pstmt = con.prepareStatement("select ld.lead_id,ld.email,ld.CONTACT_NO,ld.LEAD_GENERATED_DATE from LEAD_DETAILS ld where ld.email = ? and ld.contact_no = ?");
            pstmt.setString(1,Email);
            pstmt.setString(2,contactno);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                duplicateLeadVo = new LeadVO();
                dbemail = rs.getString(2);
                dbcontactno = rs.getString(3);
                if(Email.equalsIgnoreCase(dbemail) && contactno.equalsIgnoreCase(dbcontactno)){
                    
                    duplicateLeadVo.setLeadId(rs.getString(1));
                    duplicateLeadVo.setEmail(rs.getString(2));
                    duplicateLeadVo.setContactnumber(rs.getString(3));
                    duplicateLeadVo.setCreateDate(rs.getString(4));                           
                } 
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return  duplicateLeadVo;
    }
    public LeadVO assignTL(LeadVO objLeadVO,Connection con) throws SQLException,Exception{
       
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT  sd.SALESPERSONNEL_ID,sd.EMAIL,sd.CONTACT_NUMBER FROM SALESPERSONNEL_DETAILS sd,SALES_LOCATION sl Where "+
                    " SALESPERSONNEL_TYPE  = ? AND sd.SALESPERSONNEL_ID = sl.SALESPERSONNEL_ID"+
                    " AND  UPPER(sl.LOCATION)= UPPER(?) AND  UPPER(ADDR_CITY)= UPPER(?)"+
                    " AND UPPER(ADDR_COUNTRY)= UPPER(?) ");
            pstmt.setInt(1,Integer.parseInt(WitribeConstants.TYPE_TL));
            pstmt.setString(2,objLeadVO.getZone());
            pstmt.setString(3,objLeadVO.getCity());
            pstmt.setString(4,objLeadVO.getCountry());
            rs = pstmt.executeQuery();
                       
            if(rs.next()) {
               objLeadVO.setAssignedTL(rs.getString(1));
               objLeadVO.setSalesEmail(rs.getString(2));
               objLeadVO.setSalesContact(rs.getString(3));
            } else {
                pstmt = con.prepareStatement("SELECT  sd.SALESPERSONNEL_ID,sd.EMAIL,sd.CONTACT_NUMBER FROM SALESPERSONNEL_DETAILS sd,SALES_LOCATION sl Where "+
                    " SALESPERSONNEL_TYPE  = ? AND sd.SALESPERSONNEL_ID = sl.SALESPERSONNEL_ID"+
                    " AND  UPPER(sl.LOCATION)= UPPER(?) "+
                    " AND UPPER(ADDR_COUNTRY)= UPPER(?) ");
            pstmt.setInt(1,Integer.parseInt(WitribeConstants.TYPE_RSM));
            pstmt.setString(2,objLeadVO.getCity());
            pstmt.setString(3,objLeadVO.getCountry());
            rs = pstmt.executeQuery();
                       
            if(rs.next()) {
               objLeadVO.setAssignedTL(rs.getString(1));
               objLeadVO.setSalesEmail(rs.getString(2));
               objLeadVO.setSalesContact(rs.getString(3));
            } else {
                pstmt = con.prepareStatement("SELECT  SALESPERSONNEL_ID,EMAIL,CONTACT_NUMBER FROM SALESPERSONNEL_DETAILS Where " +
                    "SALESPERSONNEL_TYPE  = ? AND "+
                    " UPPER(ADDR_COUNTRY)= UPPER(?) ");
            pstmt.setInt(1,Integer.parseInt(WitribeConstants.TYPE_NSM));
            pstmt.setString(2,objLeadVO.getCountry());
            rs = pstmt.executeQuery();
                       
            if(rs.next()) {
               objLeadVO.setAssignedTL(rs.getString(1));
               objLeadVO.setSalesEmail(rs.getString(2));
               objLeadVO.setSalesContact(rs.getString(3));
            }
            }
                
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return  objLeadVO;
    }
     public boolean createLead(LeadVO objLeadVO,Connection con,String salesId) throws SQLException,Exception{
        boolean status = false;
        //boolean status1 = false;
       // boolean status2 = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String reasonCode = null;
        con.setAutoCommit(false);
        try{
            long key = getMaxKey(con);
           /* pstmt = con.prepareStatement(
                    "INSERT INTO LEAD_DETAILS (LEAD_ID,CHANNEL,SALUTATION,FIRST_NAME," +
                   "LAST_NAME,CNIC_ID,JOB_OR_DEG,OCCUPATION_OR_ORG,EMAIL,CONTACT_NO," +
                   "ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE,ADDR_COUNTRY,ADDR_ZIP," +
                   "LEAD_SOURCE,PACKAGE_INFO,CURRENT_ISP,SERVICE_TYPE,WILLING_PAY_TO_BROADBAND," +
                   "BROADBAND_TYPE ,CURRENT_SPEED,CURRENT_VOLUME_LIMIT," +
                   "INTERNET_MONTHLY_SPEND,QUERY,PRIORITY,LEAD_GENERATED_DATE,ADDRESS," +
                    "ASSIGNED_TO,ASSIGNED_ON, ASSIGNED_TO_TL,LEAD_STATUS,ADDR_FLG ) " +
                    "VALUES " +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");*/
            
            pstmt = con.prepareStatement(
                    "INSERT INTO LEAD_DETAILS (LEAD_ID,CHANNEL,SALUTATION,FIRST_NAME," +
                   "LAST_NAME,CNIC_ID,JOB_OR_DEG,OCCUPATION_OR_ORG,EMAIL,CONTACT_NO," +
                   "ADDR_PLOT,ADDR_STREET,SALES_SUBZONE,SALES_ZONE,SALES_CITY,SALES_PROVINCE,ADDR_COUNTRY,ADDR_ZIP," +
                   "LEAD_SOURCE,PACKAGE_INFO,CURRENT_ISP,SERVICE_TYPE,WILLING_PAY_TO_BROADBAND," +
                   "BROADBAND_TYPE ,CURRENT_SPEED,CURRENT_VOLUME_LIMIT," +
                   "INTERNET_MONTHLY_SPEND,QUERY,PRIORITY,LEAD_GENERATED_DATE,ADDRESS," +
                    "ASSIGNED_TO,ASSIGNED_ON, ASSIGNED_TO_TL,LEAD_STATUS,ADDR_FLG,LEAD_ADDRESS,SALESPERSONNEL_ID,LEAD_TAG,LEAD_STATUS_NEW,REFERRED_BY) " +
                    "VALUES " +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            pstmt.setLong(1, ++key);
            pstmt.setString(2, objLeadVO.getChanneltype());
            pstmt.setString(3, objLeadVO.getSalutation());
            pstmt.setString(4, objLeadVO.getFirstname());
            pstmt.setString(5, objLeadVO.getLastname());
            pstmt.setString(6, objLeadVO.getCNICid());
            pstmt.setString(7, objLeadVO.getJobtitle());
            pstmt.setString(8, objLeadVO.getOccupation());
            pstmt.setString(9, objLeadVO.getEmail());
            pstmt.setString(10, objLeadVO.getContactnumber());
             
           pstmt.setString(11, objLeadVO.getPlot());
            pstmt.setString(12, objLeadVO.getStreet());
            if(!objLeadVO.getSubzone().equalsIgnoreCase("other")){
            pstmt.setString(13, objLeadVO.getSubzone());            
            } else {
              pstmt.setString(13, objLeadVO.getOthersubzone());               
            }
            if(!objLeadVO.getZone().equalsIgnoreCase("other")){
            pstmt.setString(14, objLeadVO.getZone());
            
            } else {
                pstmt.setString(14, objLeadVO.getOtherzone()); 
              
            }
            pstmt.setString(15, objLeadVO.getCity());
            pstmt.setString(16, objLeadVO.getProvince());
            pstmt.setString(17, objLeadVO.getCountry());
            pstmt.setString(18, objLeadVO.getZip());            
            pstmt.setString(19, objLeadVO.getLeadsource());
            pstmt.setString(20, objLeadVO.getPackageinfo());
            pstmt.setString(21, objLeadVO.getNameofISP());
            pstmt.setString(22, objLeadVO.getServicetype());
            pstmt.setString(23, objLeadVO.getWillingPay());//willing to pay to broadband
            pstmt.setString(24, objLeadVO.getBroadbandtype());
            pstmt.setString(25, objLeadVO.getCurrentSpeed());//current speed
            pstmt.setString(26, objLeadVO.getVolumelimit());
            pstmt.setString(27, objLeadVO.getMonthlyspend());//monthly spend
            pstmt.setString(28, objLeadVO.getQuery());
            pstmt.setString(29, objLeadVO.getPriority());
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(30, sqlDate);
            pstmt.setString(31, objLeadVO.getAddress());
            
            pstmt.setString(32, objLeadVO.getAssignedCSE());
            pstmt.setTimestamp(33, sqlDate);
            pstmt.setString(34, objLeadVO.getAssignedTL());
            pstmt.setString(35, objLeadVO.getLeadStatus());
            if(objLeadVO.getSubzone().equalsIgnoreCase("other") && objLeadVO.getZone().equalsIgnoreCase("other")){
            pstmt.setInt(36, 2);
            } else if(objLeadVO.getSubzone().equalsIgnoreCase("other")){
               pstmt.setInt(36, 1); 
            } else {
               pstmt.setInt(36, 0); 
            }
            pstmt.setString(37, objLeadVO.getLeadaddress());
            pstmt.setString(38,salesId);
            //pstmt.setString(39,objLeadVO.getReqISP());
            pstmt.setString(39,objLeadVO.getTransitionState());
            reasonCode = getReasonCode(con,objLeadVO);
            pstmt.setString(40,reasonCode);
             pstmt.setString(41,objLeadVO.getReferredBy());
          
            if(pstmt.executeUpdate()>WitribeConstants.ZERO){
                status = true;
            }
            objLeadVO.setLeadId(String.valueOf(key));
            ProspectDAO prospectdao = new ProspectDAO();
            long propectkey = prospectdao.getMaxKey(con);
            long historykey = prospectdao.getHistoryKey(con);
            reasonCode = getReasonCode(con,objLeadVO);
            if(status){
                
              pstmt = con.prepareStatement(
                    "INSERT INTO LEAD_HISTORY (LEAD_HISTORY_ID,LEAD_ID,SALESPERSONNEL_ID,MODIFIED_DATE,STATUS,LEAD_COMMENT,TRANSITION_STATE)" +
                    "VALUES " +
                    "(?,?,?,?,?,?,?)");
            
            pstmt.setLong(1, ++historykey);
            pstmt.setString(2, objLeadVO.getLeadId());
            pstmt.setString(3, salesId);
            java.sql.Timestamp  sqlDate1 = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(4, sqlDate1);
            pstmt.setString(5, reasonCode);
            pstmt.setString(6, objLeadVO.getReasonComment());
            pstmt.setString(7, objLeadVO.getTransitionState());
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
            }   
            }
            if(status && (objLeadVO.getTransitionState().equalsIgnoreCase(WitribeConstants.TRANSITION_STATE_PROSPECT))){
            pstmt = con.prepareStatement(
                    "INSERT INTO PROSPECT_DETAILS (PROSPECT_ID,LEAD_ID,REASON,CREATE_DATE,TRANSITION_STATE,REASON_COMMENT)" +
                    "VALUES " +
                    "(?,?,?,?,?,?)");
            
            pstmt.setLong(1, ++propectkey);
            pstmt.setString(2,objLeadVO.getLeadId());
            pstmt.setString(3,reasonCode);
            java.sql.Timestamp  sqlDate1 = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(4, sqlDate1);
            pstmt.setString(5,objLeadVO.getTransitionState());
            pstmt.setString(6,"Created");  
            pstmt.executeUpdate();
           /* if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status1 = true;
            }*/
            
            pstmt = con.prepareStatement(
                    "UPDATE LEAD_DETAILS set LEAD_STATUS = ? where LEAD_ID = ?");
             pstmt.setString(1,WitribeConstants.PROSPECT_CREATED);
             pstmt.setString(2,objLeadVO.getLeadId());  
             pstmt.executeUpdate();
             //con.commit();
           /* if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status2 = true;
            }
            if(status1 && status2){
                con.commit();
            } else{
                con.rollback();
            }*/
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
     public String getReasonCode(Connection con,LeadVO objLeadVO) throws SQLException,Exception{
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         String reasonCode = null;
         try{
          pstmt = con.prepareStatement(
                    "select REASON_CODE from MST_PROSPECT_REASON where REASON_ID=?");
             pstmt.setInt(1,objLeadVO.getReasonId());
             rs = pstmt.executeQuery();
             if(rs.next()){
               reasonCode = rs.getString(1);
             }
         } catch(SQLException se){
           //con.rollback();
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            //con.rollback();
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
          //con.setAutoCommit(true);
           DBUtil.closeStatement(pstmt);
        } 
             return reasonCode;
     }
    public long getLeadRowCount(Connection con,String query) throws SQLException,Exception{
        long rowsCount = WitribeConstants.ROWS_COUNT;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT count(*) "+query);
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
    public List getNextLeads(Connection con,SalesPersonnelVO objSalesVO,int pageNum) throws SQLException,Exception{
        List objList = null;
        Statement pstmt = null;
        ResultSet rs = null;
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        long totalRows;
        int startRow = 0;
        int endRow = 0;
        try{
            startRow = (pageNum*maxRowCount)+1;
            endRow = (pageNum+1)*maxRowCount+1;
            pstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
           
            String query = " FROM LEAD_DETAILS ";
            int role = Integer.parseInt(objSalesVO.getSalestype());
            if(role >= WitribeConstants.FOUR) {
                query += " WHERE UPPER(LEAD_STATUS) NOT IN (UPPER('"+WitribeConstants.UNASSIGNED+"'),UPPER('"+WitribeConstants.ACCOUNT_CREATED+"'),UPPER('"+WitribeConstants.PROSPECT_CREATED+"')) AND ASSIGNED_TO = "+objSalesVO.getSalesId();
            } else if(role > WitribeConstants.ZERO && role < WitribeConstants.FOUR){
                query += " WHERE UPPER(LEAD_STATUS) =UPPER('"+WitribeConstants.UNASSIGNED+"') AND ASSIGNED_TO_TL = "+objSalesVO.getSalesId();
            } else {
                 query += " WHERE UPPER(LEAD_STATUS) =UPPER('"+WitribeConstants.UNASSIGNED+"') AND NVL(ASSIGNED_TO_TL,' ')=' ' AND NVL(ASSIGNED_TO,' ')=' ' ";
            }
            query+= " ORDER BY  LEAD_GENERATED_DATE asc";
            rs = pstmt.executeQuery("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                    "SELECT  LEAD_ID,FIRST_NAME," +
                    "LAST_NAME,EMAIL,CONTACT_NO," +
                    "ADDR_PLOT,ADDR_STREET,SALES_SUBZONE,SALES_ZONE,SALES_CITY,SALES_PROVINCE,ADDR_COUNTRY,ADDR_ZIP," +
                    "LEAD_SOURCE,PACKAGE_INFO,SERVICE_TYPE,WILLING_PAY_TO_BROADBAND," +
                    "BROADBAND_TYPE ,PRIORITY,LEAD_GENERATED_DATE,LEAD_STATUS,ASSIGNED_TO,ASSIGNED_TO_TL "+query+" ) a WHERE rownum <="+endRow+")"+
                    "WHERE rn >="+startRow);
            // Check ResultSet's scrollability
          
            totalRows = getLeadRowCount(con,query);
          /*  if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }*/
            
            objList = new ArrayList();
            int count=WitribeConstants.ZERO;
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
                objLeadVO.setLeadStatus(rs.getString(21));
                objLeadVO.setAssignedCSE(rs.getString(22));
                objLeadVO.setAssignedTL(rs.getString(23));
                
                objList.add(objLeadVO);
                
               
            }
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            DBUtil.closeResultSet(rs);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            DBUtil.closeResultSet(rs);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return objList;
        
    }
    public List getNextAccountLeads(Connection con,LeadVO objLVO,SalesPersonnelVO ObjSalesVO) throws SQLException,Exception{
        List objList = null;
        Statement pstmt = null;
        ResultSet rs = null;
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        int role = Integer.parseInt(ObjSalesVO.getSalestype());
        String query = null;
        String country = ObjSalesVO.getCountry();
        String salesId = ObjSalesVO.getSalesId();
        String city = ObjSalesVO.getCity();
        String zone = ObjSalesVO.getZone();
        String fdate = objLVO.getFromDate();
        String tdate = objLVO.getToDate();
       // long totalRows;
        //int startRow = 0;
        //int endRow = 0;
        try{
          //  startRow = (pageNum*maxRowCount)+1;
           // endRow = (pageNum+1)*maxRowCount+1;
            pstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
           // added channeltype and created by and salesperson name
            //query = " FROM LEAD_DETAILS ld,SalesPersonnel_details sd where (sd.SALESPERSONNEL_ID = ld.ASSIGNED_TO or sd.SALESPERSONNEL_ID = ld.ASSIGNED_TO_TL) and ";
            query = " FROM LEAD_DETAILS ld,SalesPersonnel_details sd1, salespersonnel_details sd2,salespersonnel_details sd3 "+
                            " where sd1.SALESPERSONNEL_ID(+) = ld.ASSIGNED_TO and sd2.SALESPERSONNEL_ID(+) = ld.ASSIGNED_TO_TL and sd3.SALESPERSONNEL_ID(+) = ld.SALESPERSONNEL_ID ";
            query += " and (ld.LEAD_GENERATED_DATE between to_date('"+fdate+"','dd/mm/yyyy') and (to_date('"+tdate+"','dd/mm/yyyy'))+1) and ";
            query += " (UPPER(ld.LEAD_STATUS) =UPPER('"+WitribeConstants.ACCOUNT_CREATED+"'))  ";
            if (role == Integer.parseInt(WitribeConstants.TYPE_ADMIN)) {
                query += " and (UPPER(ld.ADDR_COUNTRY) =UPPER('"+WitribeConstants.COUNTRY+"')) ";
            } else if (role == Integer.parseInt(WitribeConstants.TYPE_NSM)) {
                query += " AND (UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"'))";
            }else if (role == Integer.parseInt(WitribeConstants.TYPE_RSM)) {
                query += "  AND (UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"')) AND " +
                        "(UPPER(ld.SALES_CITY) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+"))";
            }else if (role == Integer.parseInt(WitribeConstants.TYPE_TL)) {
                query += "  AND (UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"')) AND " +
                        "(UPPER(ld.SALES_CITY) =UPPER('"+city+"'))"+" AND " +
                        "(UPPER(ld.SALES_ZONE) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+"))";
            } else if (role == Integer.parseInt(WitribeConstants.TYPE_CSE)) {
                query += "  AND UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"') AND " +
                        "UPPER(ld.SALES_CITY) =UPPER('"+city+"')"+" AND " + "UPPER(ld.SALES_ZONE) =UPPER('"+zone+"') "+
                        "and UPPER(ld.SALES_SUBZONE) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+")";
            }
            query+= " ORDER BY SALES_CITY,SALES_ZONE,SALES_SUBZONE";
            rs = pstmt.executeQuery("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                    "SELECT  ld.LEAD_ID,ld.FIRST_NAME, "+
                    "ld.LAST_NAME,ld.EMAIL,ld.CONTACT_NO, "+
                    "ld.ADDR_PLOT,ld.ADDR_STREET,ld.SALES_SUBZONE,ld.SALES_ZONE,ld.SALES_CITY,ld.SALES_PROVINCE,ld.ADDR_COUNTRY,ld.ADDR_ZIP, "+
                    "ld.LEAD_SOURCE,ld.PACKAGE_INFO,ld.SERVICE_TYPE,ld.CNIC_ID, "+
                    "ld.BROADBAND_TYPE ,ld.PRIORITY,ld.LEAD_GENERATED_DATE,ld.LEAD_STATUS,ld.ASSIGNED_TO,ld.ASSIGNED_TO_TL,ACCOUNT_NO,ACCOUNT_DATE,concat(concat(sd1.FIRST_NAME,' '),sd1.last_name) csename,concat(concat(sd2.FIRST_NAME,' '),sd2.last_name) tlname, "+
                    "ld.SALESPERSONNEL_ID ,concat(concat(sd3.FIRST_NAME,' '),sd3.last_name) as SalesPersonName,ld.CHANNEL "+query+" ) a )");
            // Check ResultSet's scrollability
          
            //totalRows = getLeadRowCount(con,query);
          /*  if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }*/
            
            objList = new ArrayList();
            int count=WitribeConstants.ZERO;
            while(rs.next()){
              /*  count++;
               if(count>maxRowCount) {
                   objList.add("true");
                   break;
               }*/
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
                objLeadVO.setLeadStatus(rs.getString(21));
                objLeadVO.setAssignedCSE(rs.getString(22));
                objLeadVO.setAssignedTL(rs.getString(23));
                objLeadVO.setAccountNo(rs.getString(24));
                objLeadVO.setAcctDate(rs.getString(25));
                objLeadVO.setCsename(rs.getString(26));
                objLeadVO.setTlname(rs.getString(27));
                objLeadVO.setSalesId(rs.getString(28));
                objLeadVO.setFullname(rs.getString(29));
                objLeadVO.setChanneltype(rs.getString(30));
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
   public List getSearchedLeadsBy(Connection con,LeadVO objLVO,String searchlead,SalesPersonnelVO ObjSalesVO) throws SQLException,Exception{
        List objList = null;
        Statement pstmt = null;
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        ResultSet rs = null;
        String searchby = objLVO.getSearchby();
        int role = Integer.parseInt(ObjSalesVO.getSalestype());
        String country = ObjSalesVO.getCountry();
        String salesId = ObjSalesVO.getSalesId();
        String city = ObjSalesVO.getCity();
        String zone = ObjSalesVO.getZone();
        String fdate = objLVO.getFromDate();
        String tdate = objLVO.getToDate();
        //long totalRows;
        //int startRow = 0;
        //int endRow = 0;
        try{
            //startRow = (pageNum*maxRowCount)+1;
           // endRow = (pageNum+1)*maxRowCount+1;
            pstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
           //// added channeltype and created by and salesperson name
            String query = " FROM LEAD_DETAILS ld,SalesPersonnel_details sd1, salespersonnel_details sd2,salespersonnel_details sd3 "+
                            " where sd1.SALESPERSONNEL_ID(+) = ld.ASSIGNED_TO and sd2.SALESPERSONNEL_ID(+) = ld.ASSIGNED_TO_TL and sd3.SALESPERSONNEL_ID(+) = ld.SALESPERSONNEL_ID ";
            /*query += " WHERE (UPPER(first_name) like UPPER('%"+searchlead+"%') or UPPER(last_name) like UPPER('%"+searchlead+"%') or "+
                        "UPPER(lead_id) like UPPER('%"+searchlead+"%') or UPPER(cnic_id) like UPPER('%"+searchlead+"%') or UPPER(email) like UPPER('%"+searchlead+"%') or "+
                        "UPPER(contact_no) like UPPER('%"+searchlead+"%') or UPPER(SALES_SUBZONE) like UPPER('%"+searchlead+"%') or UPPER(SALES_SUBZONE) like UPPER('%"+searchlead+"%')"+
                        "or UPPER(SALES_ZONE) like UPPER('%"+searchlead+"%') or UPPER(SALES_CITY) like UPPER('%"+searchlead+"%') "+
                        "or UPPER(SALES_PROVINCE) like UPPER('%"+searchlead+"%') or UPPER(ASSIGNED_TO) like UPPER('%"+searchlead+"%') "+
                        "or UPPER(ASSIGNED_TO_TL) like UPPER('%"+searchlead+"%') or UPPER(LEAD_STATUS) like UPPER('%"+searchlead+"%')) ";*/
            
             if(searchlead.equals("*"))
              query += " ";  
           else
            query += "  and (UPPER(ld."+searchby+") like  UPPER('%"+searchlead+"%'))  "  ; 
             if (role == Integer.parseInt(WitribeConstants.TYPE_ADMIN)) {
                query += " and (UPPER(ld.ADDR_COUNTRY) =UPPER('"+WitribeConstants.COUNTRY+"')) ";
            } else if (role == Integer.parseInt(WitribeConstants.TYPE_NSM)) {
                query += "  AND (UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"'))";
            }else if (role == Integer.parseInt(WitribeConstants.TYPE_RSM)) {
                query += " AND (UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"')) AND " +
                        "(UPPER(ld.SALES_CITY) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+"))";
            }else if (role == Integer.parseInt(WitribeConstants.TYPE_TL)) {
                query += " AND (UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"')) AND " +
                        "(UPPER(ld.SALES_CITY) =UPPER('"+city+"'))"+" AND " +
                        "(UPPER(ld.SALES_ZONE) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+"))";
            } else if (role == Integer.parseInt(WitribeConstants.TYPE_CSE)) {
                query += " AND UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"') AND " +
                        "UPPER(ld.SALES_CITY) =UPPER('"+city+"')"+" AND " + "UPPER(ld.SALES_ZONE) =UPPER('"+zone+"') "+
                        "and UPPER(ld.SALES_SUBZONE) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+")";
            }
            query+= " ORDER BY  SALES_CITY,SALES_ZONE,SALES_SUBZONE,LEAD_STATUS, ASSIGNED_TO, ASSIGNED_TO_TL asc";
            
                        
            rs = pstmt.executeQuery("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                    "SELECT  ld.LEAD_ID,ld.FIRST_NAME, "+
                    "ld.LAST_NAME,ld.EMAIL,ld.CONTACT_NO, "+
                    "ld.ADDR_PLOT,ld.ADDR_STREET,ld.SALES_SUBZONE,ld.SALES_ZONE,ld.SALES_CITY,ld.SALES_PROVINCE,ld.ADDR_COUNTRY,ld.ADDR_ZIP, "+
                    "ld.LEAD_SOURCE,ld.PACKAGE_INFO,ld.SERVICE_TYPE,ld.CNIC_ID, "+
                    "ld.BROADBAND_TYPE ,ld.PRIORITY,ld.LEAD_GENERATED_DATE,ld.LEAD_STATUS,ld.ASSIGNED_TO,ld.ASSIGNED_TO_TL,concat(concat(sd1.FIRST_NAME,' '),sd1.last_name) csename,concat(concat(sd2.FIRST_NAME,' '),sd2.last_name) tlname, "+
                    "ld.SALESPERSONNEL_ID ,concat(concat(sd3.FIRST_NAME,' '),sd3.last_name) as SalesPersonName,ld.CHANNEL "+query+" ) a )");
                    
            // Check ResultSet's scrollability
          
            //totalRows = getLeadRowCount(con,query);
          /*  if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }*/
            
            objList = new ArrayList();
            int count=WitribeConstants.ZERO;
            while(rs.next()){
               /* count++;
               if(count>maxRowCount) {
                   objList.add("true");
                   break;
               }*/
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
                objLeadVO.setCNICid(rs.getString(17));
                objLeadVO.setBroadbandtype(rs.getString(18));
                objLeadVO.setPriority(rs.getString(19));
                objLeadVO.setCreateDate(rs.getString(20));
                objLeadVO.setLeadStatus(rs.getString(21));
                objLeadVO.setAssignedCSE(rs.getString(22));
                objLeadVO.setAssignedTL(rs.getString(23));
                objLeadVO.setCsename(rs.getString(24));
                objLeadVO.setTlname(rs.getString(25));
                objLeadVO.setSalesId(rs.getString(26));
                objLeadVO.setFullname(rs.getString(27));
                objLeadVO.setChanneltype(rs.getString(28));
                objList.add(objLeadVO);
                
               
            }
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeStatement(pstmt);
            DBUtil.closeResultSet(rs);
            
        }
        
        return objList;
        
    }
    
     public List getSearchedLeads(Connection con,LeadVO objLVO,SalesPersonnelVO ObjSalesVO) throws SQLException,Exception{
        List objList = null;
        Statement pstmt = null;
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        //String searchby = objLVO.getSearchby();
        String fdate = objLVO.getFromDate();
        String tdate = objLVO.getToDate();
        String searchBy = objLVO.getSearchby();
        ResultSet rs = null;
        int role = Integer.parseInt(ObjSalesVO.getSalestype());
        String query = null;
        String country = ObjSalesVO.getCountry();
        String salesId = ObjSalesVO.getSalesId();
        String city = ObjSalesVO.getCity();
        String zone = ObjSalesVO.getZone();
        //long totalRows;
        //int startRow = 0;
        //int endRow = 0;
        try{
            //startRow = (pageNum*maxRowCount)+1;
           // endRow = (pageNum+1)*maxRowCount+1;
            pstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
           // added channeltype and created by and salesperson name
            //query = " FROM LEAD_DETAILS ld,SalesPersonnel_details sd where (sd.SALESPERSONNEL_ID = ld.ASSIGNED_TO or sd.SALESPERSONNEL_ID = ld.ASSIGNED_TO_TL) and  ";
            query = "FROM LEAD_DETAILS ld,SalesPersonnel_details sd1, salespersonnel_details sd2,salespersonnel_details sd3 "+
                            " where sd1.SALESPERSONNEL_ID(+) = ld.ASSIGNED_TO and sd2.SALESPERSONNEL_ID(+) = ld.ASSIGNED_TO_TL and sd3.SALESPERSONNEL_ID(+) = ld.SALESPERSONNEL_ID ";
            query += " and (ld.LEAD_GENERATED_DATE between to_date('"+fdate+"','dd/mm/yyyy') and (to_date('"+tdate+"','dd/mm/yyyy'))+1)  ";
             if (role == Integer.parseInt(WitribeConstants.TYPE_ADMIN)) {
                query += " and (UPPER(ld.ADDR_COUNTRY) =UPPER('"+WitribeConstants.COUNTRY+"')) ";
            } else if (role == Integer.parseInt(WitribeConstants.TYPE_NSM)) {
                //query += " and (sd.SALESPERSONNEL_TYPE > "+WitribeConstants.TYPE_NSM+" OR sd.SALESPERSONNEL_TYPE <"+WitribeConstants.TYPE_ADMIN+") AND (UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"'))";
                query += " AND (UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"'))";
            }else if (role == Integer.parseInt(WitribeConstants.TYPE_RSM)) {
                query += " AND (UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"')) AND " +
                        "(UPPER(ld.SALES_CITY) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+"))";
            }else if (role == Integer.parseInt(WitribeConstants.TYPE_TL)) {
                query += " AND (UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"')) AND " +
                        "(UPPER(ld.SALES_CITY) =UPPER('"+city+"'))"+" AND " +
                        "(UPPER(ld.SALES_ZONE) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+"))";
            } else if (role == Integer.parseInt(WitribeConstants.TYPE_CSE)) {
                query += " AND UPPER(ld.ADDR_COUNTRY) =UPPER('"+country+"') AND " +
                        "UPPER(ld.SALES_CITY) =UPPER('"+city+"')"+" AND " + "UPPER(ld.SALES_ZONE) =UPPER('"+zone+"') "+
                        "and UPPER(ld.SALES_SUBZONE) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+")";
            }
            
            query+= " ORDER BY  SALES_CITY,SALES_ZONE,SALES_SUBZONE,LEAD_STATUS, ASSIGNED_TO, ASSIGNED_TO_TL asc";
            
                        
             rs = pstmt.executeQuery("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                    "SELECT  ld.LEAD_ID,ld.FIRST_NAME, "+
                    "ld.LAST_NAME,ld.EMAIL,ld.CONTACT_NO, "+
                    "ld.ADDR_PLOT,ld.ADDR_STREET,ld.SALES_SUBZONE,ld.SALES_ZONE,ld.SALES_CITY,ld.SALES_PROVINCE,ld.ADDR_COUNTRY,ld.ADDR_ZIP, "+
                    "ld.LEAD_SOURCE,ld.PACKAGE_INFO,ld.SERVICE_TYPE,ld.CNIC_ID, "+
                    "ld.BROADBAND_TYPE ,ld.PRIORITY,ld.LEAD_GENERATED_DATE,ld.LEAD_STATUS,ld.ASSIGNED_TO,ld.ASSIGNED_TO_TL,concat(concat(sd1.FIRST_NAME,' '),sd1.last_name) csename,concat(concat(sd2.FIRST_NAME,' '),sd2.last_name) tlname, "+
                    "ld.SALESPERSONNEL_ID ,concat(concat(sd3.FIRST_NAME,' '),sd3.last_name) as SalesPersonName,ld.CHANNEL "+query+" ) a )");
                    
            // Check ResultSet's scrollability
          
            //totalRows = getLeadRowCount(con,query);
          /*  if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }*/
            
          objList = new ArrayList();
            int count=WitribeConstants.ZERO;
            while(rs.next()){
               /* count++;
               if(count>maxRowCount) {
                   objList.add("true");
                   break;
               }*/
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
                objLeadVO.setCNICid(rs.getString(17));
                objLeadVO.setBroadbandtype(rs.getString(18));
                objLeadVO.setPriority(rs.getString(19));
                objLeadVO.setCreateDate(rs.getString(20));
                objLeadVO.setLeadStatus(rs.getString(21));
                objLeadVO.setAssignedCSE(rs.getString(22));
                objLeadVO.setAssignedTL(rs.getString(23));
                objLeadVO.setCsename(rs.getString(24));
                objLeadVO.setTlname(rs.getString(25));
                objLeadVO.setSalesId(rs.getString(26));
                objLeadVO.setFullname(rs.getString(27));
                objLeadVO.setChanneltype(rs.getString(28));
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
    public List getLeadsToReassign(Connection con,SalesPersonnelVO objSalesVO,int pageNum) throws SQLException,Exception{
        List objList = null;
        Statement pstmt = null;
        ResultSet rs = null;
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        long totalRows;
        try{
            pstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //ScrollableResultSet sr= pstmt.executeQuery();
            String query = " FROM LEAD_DETAILS,SALES_HIERARCHY " +
                    " WHERE PARENT_SALESPERSONNEL_ID = "+objSalesVO.getSalesId()+" and ASSIGNED_TO = CHILD_SALESPERSONNEL_ID AND UPPER(LEAD_STATUS) NOT IN (UPPER('"+WitribeConstants.UNASSIGNED+"'),UPPER('"+WitribeConstants.ACCOUNT_CREATED+"'),UPPER('"+WitribeConstants.PROSPECT_CREATED+"')) ORDER BY  LEAD_GENERATED_DATE asc";
            int role = Integer.parseInt(objSalesVO.getSalestype());
             
            rs = pstmt.executeQuery("SELECT  LEAD_ID,FIRST_NAME,LAST_NAME,EMAIL,CONTACT_NO," +
                    " ADDR_PLOT,ADDR_STREET,SALES_SUBZONE,SALES_ZONE,SALES_CITY,SALES_PROVINCE,ADDR_COUNTRY,ADDR_ZIP," +
                    " LEAD_SOURCE,PACKAGE_INFO,SERVICE_TYPE,WILLING_PAY_TO_BROADBAND," +
                    " BROADBAND_TYPE ,PRIORITY,LEAD_GENERATED_DATE "+query);
            // Check ResultSet's scrollability
          
            totalRows = getLeadRowCount(con,query);
            if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }
            
            objList = new ArrayList();
            int count=WitribeConstants.ZERO;
            while(rs.next()){
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
                objList.add(objLeadVO);
                count++;
                if(count==maxRowCount)
                    break;
            }
            if(rs.next()){
                objList.add("true");
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
    
     public LeadVO getLead(String LeadId,Connection con) throws SQLException,Exception{
        LeadVO objLeadVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement(
                   "SELECT  LEAD_ID,FIRST_NAME," +
                   "LAST_NAME,EMAIL,CONTACT_NO," +
                   "SALES_SUBZONE,SALES_ZONE,SALES_CITY,SALES_PROVINCE,ADDR_COUNTRY,ADDR_ZIP," +
                     "LEAD_SOURCE,PACKAGE_INFO,SERVICE_TYPE,WILLING_PAY_TO_BROADBAND," +
                   "BROADBAND_TYPE ,PRIORITY,LEAD_GENERATED_DATE," +
                     "CNIC_ID, JOB_OR_DEG,OCCUPATION_OR_ORG,SALUTATION,LEAD_STATUS,LEAD_STATUS_NEW, ACCOUNT_NO, ORDER_ID,to_char(FOLLOWUP_DATE,'dd-Mon-yyyy HH24:MI'), "+
                     " REFERRED_BY FROM LEAD_DETAILS WHERE LEAD_ID = ?"); 
             pstmt.setString(1,LeadId);
             rs = pstmt.executeQuery();
            if(rs.next()){
               objLeadVO = new LeadVO();
               objLeadVO.setLeadId(rs.getString(1)); 
               objLeadVO.setFirstname(rs.getString(2));
               objLeadVO.setLastname(rs.getString(3));
               objLeadVO.setEmail(rs.getString(4));
               objLeadVO.setContactnumber(rs.getString(5));
               
               //objLeadVO.setPlot(rs.getString(6));
               //objLeadVO.setStreet(rs.getString(7));
               objLeadVO.setSubzone(rs.getString(6));
               objLeadVO.setZone(rs.getString(7));
               objLeadVO.setCity(rs.getString(8));
               objLeadVO.setProvince(rs.getString(9));
               objLeadVO.setCountry(rs.getString(10));
               objLeadVO.setZip(rs.getString(11));
                              
               objLeadVO.setLeadsource(rs.getString(12));
               objLeadVO.setPackageinfo(rs.getString(13));
               objLeadVO.setServicetype(rs.getString(14));
               objLeadVO.setWillingPay(rs.getString(15));
               objLeadVO.setBroadbandtype(rs.getString(16));
               objLeadVO.setPriority(rs.getString(17));
               objLeadVO.setCNICid(rs.getString(18));
               objLeadVO.setCreateDate(rs.getString(19));
               objLeadVO.setJobtitle(rs.getString(20));
               objLeadVO.setOccupation(rs.getString(21));
               objLeadVO.setSalutation(rs.getString(22));
               objLeadVO.setLeadStatus(rs.getString(23));
              // objLeadVO.setLeadStatusNew(rs.getString(24));
               objLeadVO.setReasonCode(rs.getString(24));
               objLeadVO.setAccountNo(rs.getString(25));
               objLeadVO.setOrderId(rs.getString(26));
               objLeadVO.setFollowUpDate(rs.getString(27));
               objLeadVO.setReferredBy(rs.getString(28));
               
                                     
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
        
        return objLeadVO;
        
    }
     
    public boolean updateAccountInfo( LeadVO objLeadVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        con.setAutoCommit(false);
        try{
            pstmt = con.prepareStatement(
                    "UPDATE LEAD_DETAILS set ACCOUNT_NO = ?,ACCOUNT_DATE = ?, LEAD_STATUS = ?," +
                    "PREPAID_OR_POSTPAID =?,CNIC_ID = ?,ORDER_ID = ?, LEAD_STATUS_NEW = ?, LEAD_TAG = ?  where LEAD_ID = ?");
            
            pstmt.setString(1, objLeadVO.getAccountNo());
            pstmt.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
            pstmt.setString(3, WitribeConstants.CUSTOMER_INITIATED);
            pstmt.setString(4, objLeadVO.getAcctType());
            pstmt.setString(5, objLeadVO.getCNICid());
            pstmt.setString(6, objLeadVO.getOrderId());
            pstmt.setString(7, WitribeConstants.JOB_STATUS_INITIATED);
            pstmt.setString(8, WitribeConstants.LEAD_TAG_INITIATED);
            pstmt.setString(9, objLeadVO.getLeadId());
            
            if(pstmt.executeUpdate()>WitribeConstants.ZERO){
                status = true;
            }
            ProspectDAO prospectdao = new ProspectDAO();
            //long propectkey = prospectdao.getMaxKey(con);
            long historykey = prospectdao.getHistoryKey(con);
            //reasonCode = getReasonCode(con,objLeadVO);
            if(status){
                
              pstmt = con.prepareStatement(
                    "INSERT INTO LEAD_HISTORY (LEAD_HISTORY_ID,LEAD_ID,SALESPERSONNEL_ID,MODIFIED_DATE,STATUS,LEAD_COMMENT,TRANSITION_STATE)" +
                    "VALUES " +
                    "(?,?,?,?,?,?,?)");
            
            pstmt.setLong(1, ++historykey);
            pstmt.setString(2, objLeadVO.getLeadId());
            pstmt.setString(3, objLeadVO.getSalesId());
            java.sql.Timestamp  sqlDate1 = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(4, sqlDate1);
            pstmt.setString(5, objLeadVO.getReasonCode());
            pstmt.setString(6, WitribeConstants.COMMENT_ACCOUNT_NUMBER+objLeadVO.getAccountNo()+WitribeConstants.COMMENT_ACCOUNT_NUMBER_CREATED);
            pstmt.setString(7, objLeadVO.getTransitionState());
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
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
    
    public boolean updateLeadAddress( LeadVO objLeadVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        try{
            pstmt = con.prepareStatement(
                    "UPDATE LEAD_DETAILS set ADDR_ZONE = ?,ADDR_CITY = ?, ADDR_SUBZONE = ?," +
                    "ADDR_PLOT =?,ADDR_STREET = ?,ADDR_PROVINCE = ? where LEAD_ID = ?");
            
            pstmt.setString(1, objLeadVO.getZone());
            pstmt.setString(2, objLeadVO.getCity());
            pstmt.setString(3, objLeadVO.getSubzone());
            pstmt.setString(4, objLeadVO.getPlot());
            pstmt.setString(5, objLeadVO.getStreet());
            pstmt.setString(6, objLeadVO.getProvince());
            pstmt.setString(7, objLeadVO.getLeadId());
            
            if(pstmt.executeUpdate()>WitribeConstants.ZERO){
                status = true;
            }
                    
                
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
           DBUtil.closeStatement(pstmt);
        }
        
        return status;
           
        
    }
     public boolean updateSalesPerson( LeadVO objLeadVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            if((WitribeConstants.TYPE_CSE).equals(objLeadVO.getSalesType())) {
            pstmt = con.prepareStatement(
                    "UPDATE LEAD_DETAILS set ASSIGNED_TO = ?,ASSIGNED_ON = ?,LEAD_STATUS = ? where LEAD_ID = ?");
                pstmt.setString(1, objLeadVO.getAssignTo());
                pstmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                pstmt.setString(3, WitribeConstants.ASSIGNED);
                pstmt.setString(4, objLeadVO.getLeadId());
            } else {
                pstmt = con.prepareStatement(
                    "UPDATE LEAD_DETAILS set ASSIGNED_TO_TL = ? where LEAD_ID = ?");
                pstmt.setString(1, objLeadVO.getAssignTo());
                pstmt.setString(2, objLeadVO.getLeadId());
            }
            
           
            if(pstmt.executeUpdate()>WitribeConstants.ZERO){
                status = true;
            }
            pstmt = con.prepareStatement(
                    "SELECT EMAIL,CONTACT_NUMBER FROM SALESPERSONNEL_DETAILS WHERE SALESPERSONNEL_ID = ?");
            pstmt.setLong(1, Long.parseLong(objLeadVO.getAssignTo()));
            rs = pstmt.executeQuery();
            if(rs.next()) {
                objLeadVO.setSalesEmail(rs.getString(1));
                objLeadVO.setSalesContact(rs.getString(2));
              }
            pstmt = con.prepareStatement(
                    "SELECT FIRST_NAME,LAST_NAME,EMAIL,CONTACT_NO FROM LEAD_DETAILS WHERE LEAD_ID = ?");
            pstmt.setLong(1, Long.parseLong(objLeadVO.getLeadId()));
            rs = pstmt.executeQuery();
            if(rs.next()) {
                objLeadVO.setFirstname(rs.getString(1));
                objLeadVO.setLastname(rs.getString(2));
                objLeadVO.setEmail(rs.getString(3));
                objLeadVO.setContactnumber(rs.getString(4));
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
        
        return status;
           
        
    }
    public boolean updateLeadStatus( LeadVO objLeadVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        String reasonCode = null;
        con.setAutoCommit(false);
        try{
            String query = "";
            if(objLeadVO.getAmount() != null && !"".equals(objLeadVO.getAmount()) ) {
                query = "UPDATE LEAD_DETAILS set LEAD_STATUS = ?, LEAD_STATUS_NEW = ?,LEAD_TAG = ?, AMOUNT_COLLECTED = "+objLeadVO.getAmount()+" where LEAD_ID = ?";
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, objLeadVO.getLeadStatus());
                pstmt.setString(2,objLeadVO.getReasonCode());
                pstmt.setString(3,objLeadVO.getTransitionState());
                pstmt.setString(4, objLeadVO.getLeadId());
            } else {
               query =  "UPDATE LEAD_DETAILS set LEAD_STATUS = ?, FOLLOWUP_DATE = ? where LEAD_ID = ?";
               pstmt = con.prepareStatement(query);
               pstmt.setString(1, objLeadVO.getLeadStatus());
               pstmt.setString(2,objLeadVO.getFollowUpDate());
               pstmt.setString(3, objLeadVO.getLeadId());
            }
            
            
            
            if(pstmt.executeUpdate()>WitribeConstants.ZERO){
                status = true;
            }
            ProspectDAO prospectdao = new ProspectDAO();
            //long propectkey = prospectdao.getMaxKey(con);
            long historykey = prospectdao.getHistoryKey(con);
            //reasonCode = getReasonCode(con,objLeadVO);
            if(status){
                
              pstmt = con.prepareStatement(
                    "INSERT INTO LEAD_HISTORY (LEAD_HISTORY_ID,LEAD_ID,SALESPERSONNEL_ID,MODIFIED_DATE,STATUS,LEAD_COMMENT,TRANSITION_STATE)" +
                    "VALUES " +
                    "(?,?,?,?,?,?,?)");
            
            pstmt.setLong(1, ++historykey);
            pstmt.setString(2, objLeadVO.getLeadId());
            pstmt.setString(3, objLeadVO.getSalesId());
            java.sql.Timestamp  sqlDate1 = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(4, sqlDate1);
            pstmt.setString(5, objLeadVO.getReasonCode());
            pstmt.setString(6, objLeadVO.getReasonComment());
            pstmt.setString(7, objLeadVO.getTransitionState());
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
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
    public List GetChildSalesList(SalesPersonnelVO objSales,Connection con)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       List objParentList = new ArrayList();
       PreparedStatement pstmt = null;
       SalesPersonnelVO objSalesVO = null;
       SalesPersonnelVO objCurSalesVO = null;
       ResultSet rs = null;
        try{
             objCurSalesVO = getCurrentSalesForLead(objSales,con);
             String whereCond = "";
             if(objCurSalesVO != null && objCurSalesVO.getSalesId() != null) {
                 whereCond += " AND s.SALESPERSONNEL_ID <>  "+objCurSalesVO.getSalesId();
                 objParentList.add(objCurSalesVO);
             }
            if(!WitribeConstants.TYPE_ADMIN.equalsIgnoreCase(objSales.getSalestype())) {
                pstmt = con.prepareStatement(" SELECT s.SALESPERSONNEL_ID,s.SALESPERSONNEL_TYPE,s.FULL_NAME,s.CONTACT_NUMBER,s.EMAIL,s.USERID,s.PASSWORD,s.ADDR_PLOT," +
                     " s.ADDR_STREET,s.ADDR_SUBZONE,s.ADDR_ZONE,s.ADDR_CITY,s.ADDR_PROVINCE,s.ADDR_COUNTRY,s.ADDR_ZIP,s.FIRST_NAME,s.LAST_NAME " +
                     " FROM SALESPERSONNEL_DETAILS s,SALES_HIERARCHY sh " +
                     " WHERE s.SALESPERSON_STATUS ='Active' AND  s.SALESPERSONNEL_TYPE NOT IN ("+WitribeConstants.TYPE_BO+","+WitribeConstants.TYPE_NBO+") AND sh.PARENT_SALESPERSONNEL_ID = ? AND sh.CHILD_SALESPERSONNEL_ID = s.SALESPERSONNEL_ID"+whereCond); 
                 pstmt.setString(1,objSales.getSalesId());
            } else {
                    pstmt = con.prepareStatement(" SELECT s.SALESPERSONNEL_ID,s.SALESPERSONNEL_TYPE,s.FULL_NAME,s.CONTACT_NUMBER,s.EMAIL,s.USERID,s.PASSWORD,s.ADDR_PLOT," +
                     " s.ADDR_STREET,s.ADDR_SUBZONE,s.ADDR_ZONE,s.ADDR_CITY,s.ADDR_PROVINCE,s.ADDR_COUNTRY,s.ADDR_ZIP,s.FIRST_NAME,s.LAST_NAME " +
                     " FROM SALESPERSONNEL_DETAILS s " +
                     " WHERE s.SALESPERSONNEL_TYPE = "+WitribeConstants.TYPE_NSM); 
                
            }
             
            
             rs = pstmt.executeQuery();
            while(rs.next()){
                 
               objSalesVO = new SalesPersonnelVO();
               objSalesVO.setSalesId(rs.getString(1));
               objSalesVO.setSalestype(rs.getString(2));
               objSalesVO.setFullname(rs.getString(3));
               objSalesVO.setContactnumber(rs.getString(4));
               objSalesVO.setEmail(rs.getString(5));
               objSalesVO.setUserId(rs.getString(6));
               objSalesVO.setPassword(rs.getString(7));
               objSalesVO.setPlot(rs.getString(8));
               objSalesVO.setStreet(rs.getString(9));
               objSalesVO.setSubzone(rs.getString(10));
               objSalesVO.setZone(rs.getString(11));
               objSalesVO.setCity(rs.getString(12));
               objSalesVO.setProvince(rs.getString(13));
               objSalesVO.setCountry(rs.getString(14));
               objSalesVO.setZip(rs.getString(15));
               objSalesVO.setFirstname(rs.getString(16));
               objSalesVO.setLastname(rs.getString(17));
               //objSalesVO.setCreateDate(rs.getString(16));
               //objSalesVO.setModifiedDate(rs.getString(17));
               objList.add(objSalesVO);
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
       objParentList.add(objList);
       return objParentList;
   }
   public SalesPersonnelVO getCurrentSalesForLead(SalesPersonnelVO objSales,Connection con)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       PreparedStatement pstmt = null;
       SalesPersonnelVO objSalesVO = null;
       ResultSet rs = null;
        try{
                pstmt = con.prepareStatement(" SELECT s.SALESPERSONNEL_ID,s.SALESPERSONNEL_TYPE,s.FULL_NAME,s.CONTACT_NUMBER,s.EMAIL,s.USERID,s.PASSWORD,s.ADDR_PLOT," +
                     " s.ADDR_STREET,s.ADDR_SUBZONE,s.ADDR_ZONE,s.ADDR_CITY,s.ADDR_PROVINCE,s.ADDR_COUNTRY,s.ADDR_ZIP,s.FIRST_NAME,s.LAST_NAME " +
                     " FROM SALESPERSONNEL_DETAILS s,LEAD_DETAILS ld " +
                     " WHERE ld.LEAD_ID = ? AND (ld.ASSIGNED_TO = s.SALESPERSONNEL_ID OR (ld.ASSIGNED_TO IS NULL AND ld.ASSIGNED_TO_TL = s.SALESPERSONNEL_ID))"); 
                 pstmt.setString(1,objSales.getLeadId());
          
             //pstmt.setInt(1,Integer.parseInt(objSales.getSalestype()));
             //Here sales id is the leadId
            
             rs = pstmt.executeQuery();
            if(rs.next()){
                 
               objSalesVO = new SalesPersonnelVO();
               objSalesVO.setSalesId(rs.getString(1));
               objSalesVO.setSalestype(rs.getString(2));
               objSalesVO.setFullname(rs.getString(3));
               objSalesVO.setContactnumber(rs.getString(4));
               objSalesVO.setEmail(rs.getString(5));
               objSalesVO.setUserId(rs.getString(6));
               objSalesVO.setPassword(rs.getString(7));
               objSalesVO.setPlot(rs.getString(8));
               objSalesVO.setStreet(rs.getString(9));
               objSalesVO.setSubzone(rs.getString(10));
               objSalesVO.setZone(rs.getString(11));
               objSalesVO.setCity(rs.getString(12));
               objSalesVO.setProvince(rs.getString(13));
               objSalesVO.setCountry(rs.getString(14));
               objSalesVO.setZip(rs.getString(15));
               objSalesVO.setFirstname(rs.getString(16));
               objSalesVO.setLastname(rs.getString(17));
               

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
       
       return objSalesVO;

   }
public LeadVO fetchMailandNumber(LeadVO objLeadVO,Connection con) throws SQLException,Exception{
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;       
        try{
            pstmt = con.prepareStatement("SELECT  EMAIL,CONTACT_NUMBER FROM SALESPERSONNEL_DETAILS Where " +
                    "SALESPERSONNEL_ID  = ? ");
            pstmt.setString(1,objLeadVO.getAssignedCSE());
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                
                objLeadVO.setSalesEmail(rs.getString(1));
                objLeadVO.setSalesContact(rs.getString(2));
                
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return objLeadVO;
    }   

//Code added for Mail sending through Listener - Muralidhar
  public boolean sendMail(Connection con,StringBuffer mailBody,LeadVO objLeadVO) throws SQLException,Exception
    {
       //PreparedStatement pstmt = null;
        boolean status = false;
             try{
                  String ParamValues = mailparamvalue(con, objLeadVO);
                  if(ParamValues != null){
                  Properties properties = new Properties();
                  properties.load(this.getClass().getClassLoader().getResourceAsStream("Infranet.properties"));
                  PortalContext ctx = new PortalContext(properties);
                  ctx.connect();
                  FList inFlist = new FList();
                   inFlist.set(FldPoid.getInst(),new Poid(1, -1L, "/wtb_email_details"));
                   inFlist.set(FldAccountObj.getInst(),new Poid(1, -1, "/account"));
		   inFlist.set(FldEmailAddr.getInst(),"no-reply@wi-tribe.pk");
		   inFlist.set(FldFlags.getInst(),0);
		   inFlist.set(FldName.getInst(),"wi-tribe");
                   inFlist.set(FldDescr.getInst(),ParamValues);
		   inFlist.set(FldTypeStr.getInst(),"16");
                   inFlist.set(WtbFldCommunicationType.getInst(),"1");
                   inFlist.dump();
                   FList out = new FList();
                   out = ctx.opcode(PortalOp.CREATE_OBJ, inFlist);
                   out.dump();
               } 
             }
            catch(Exception e){
            throw e;
            }
         return status;
        
    }  
  public String mailparamvalue(Connection con, LeadVO objLeadVO) throws SQLException,Exception
     {
        PreparedStatement pstmt = null;
        ResultSet rs = null;  
        String ParamValues = null;
        try{
             pstmt = con.prepareStatement("select 'LeadID='||ld.Lead_Id ||'||LeadName='|| ld.First_Name ||''|| ld.Last_Name ||"+
                                         "'||LeadContactNo='||ld.Contact_No||'||LeadSubZone='||ld.Sales_Subzone||'||LeadZone='||ld.Sales_Zone||"+
                                         "'||LeadCity='||ld.Sales_City ||'||MAILSendTo='||sd.EMAIL as PARAM_VALUES"+
                                         " from Lead_Details ld, Salespersonnel_Details sd where "+
                                         " ((ld.ASSIGNED_TO=sd.Salespersonnel_id AND  ld.ASSIGNED_TO IS NOT NULL)"+
                                         " OR (ld.ASSIGNED_TO_TL=sd.Salespersonnel_id) AND ld.ASSIGNED_TO IS NULL) and ld.LEAD_ID= "+objLeadVO.getLeadId());
                    
           /* pstmt = con.prepareStatement("select 'LeadID='||ld.Lead_Id ||'||LeadName='|| ld.First_Name ||''|| ld.Last_Name ||"+
                                         "'||LeadContactNo='||ld.Contact_No||'||LeadSubZone='||ld.Sales_Subzone||'||LeadZone='||ld.Sales_Zone||"+
                                         "'||LeadCity='||ld.Sales_City ||'||SMSSendTo='||sd.Contact_Number as PARAM_VALUES"+
                                         " from Lead_Details ld, Salespersonnel_Details sd where ld.Assigned_To=sd.Salespersonnel_id and ld.Lead_Id= "+objLeadVO.getLeadId());*/
            String sql= "select 'LeadID='||ld.Lead_Id ||'||LeadName='|| ld.First_Name ||''|| ld.Last_Name ||"+
                                         "'||LeadContactNo='||ld.Contact_No||'||LeadSubZone='||ld.Sales_Subzone||'||LeadZone='||ld.Sales_Zone||"+
                                         "'||LeadCity='||ld.Sales_City ||'||MAILSendTo='||sd.EMAIL as PARAM_VALUES"+
                                         " from Lead_Details ld, Salespersonnel_Details sd where "+
                                         " ((ld.ASSIGNED_TO=sd.Salespersonnel_id AND  ld.ASSIGNED_TO IS NOT NULL)"+
                                         " OR (ld.ASSIGNED_TO_TL=sd.Salespersonnel_id) AND ld.ASSIGNED_TO IS NULL) and ld.LEAD_ID= ";
            
            System.out.println("sql:"+sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
            ParamValues = rs.getString("PARAM_VALUES");
                
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return ParamValues;
    }   
// --code ended by Muralidhar--
// -- Code Added by Muralidhar --
/*      
0 PIN_FLD_POID                      POID [0] 0.0.0.1 /wtb_email_details -1 1
0 PIN_FLD_ACCOUNT_OBJ               POID [0] 0.0.0.1 /account 30155 10
0 PIN_FLD_EMAIL_ADDR                 STR [0] "no-reply@wi-tribe.pk"
0 PIN_FLD_FLAGS                      INT [0] 0
0 PIN_FLD_NAME                       STR [0] "wi-tribe"
0 PIN_FLD_TYPE_STR                   STR [0] "2"
0 WTB_FLD_COMMUNICATION_TYPE         STR [0] "2"
       */
     public boolean sendMsg(Connection con,StringBuffer mailBody,LeadVO objLeadVO) throws SQLException,Exception
    {
       //PreparedStatement pstmt = null;
        boolean status = false;
             try{
                  String ParamValues = smsparamvalue(con, objLeadVO);
                  if(ParamValues != null){
                  Properties properties = new Properties();
                  properties.load(this.getClass().getClassLoader().getResourceAsStream("Infranet.properties"));
                  PortalContext ctx = new PortalContext(properties);
                  ctx.connect();
                  FList inFlist = new FList();
                   inFlist.set(FldPoid.getInst(),new Poid(1, -1L, "/wtb_email_details"));
                   inFlist.set(FldAccountObj.getInst(),new Poid(1, -1, "/account"));
		   inFlist.set(FldEmailAddr.getInst(),"no-reply@wi-tribe.pk");
		   inFlist.set(FldFlags.getInst(),0);
		   inFlist.set(FldName.getInst(),"wi-tribe");
                   inFlist.set(FldDescr.getInst(),ParamValues);
		   inFlist.set(FldTypeStr.getInst(),"15");
                   inFlist.set(WtbFldCommunicationType.getInst(),"2");
                   inFlist.dump();
                   FList out = new FList();
                   out = ctx.opcode(PortalOp.CREATE_OBJ, inFlist);
                   out.dump();
               } 
             }
            catch(Exception e){
            throw e;
            }
         return status;
        
    }  
    public String smsparamvalue(Connection con, LeadVO objLeadVO) throws SQLException,Exception
     {
        PreparedStatement pstmt = null;
        ResultSet rs = null;  
        String ParamValues = null;
        try{
             pstmt = con.prepareStatement("select 'LeadID='||ld.Lead_Id ||'||LeadName='|| ld.First_Name ||''|| ld.Last_Name ||"+
                                         "'||LeadContactNo='||ld.Contact_No||'||LeadSubZone='||ld.Sales_Subzone||'||LeadZone='||ld.Sales_Zone||"+
                                         "'||LeadCity='||ld.Sales_City ||'||SMSSendTo='||sd.Contact_Number as PARAM_VALUES"+
                                         " from Lead_Details ld, Salespersonnel_Details sd where "+
                                         " ((ld.ASSIGNED_TO=sd.Salespersonnel_id AND  ld.ASSIGNED_TO IS NOT NULL)"+
                                         " OR (ld.ASSIGNED_TO_TL=sd.Salespersonnel_id) AND ld.ASSIGNED_TO IS NULL) and ld.LEAD_ID= "+objLeadVO.getLeadId());
                    
           /* pstmt = con.prepareStatement("select 'LeadID='||ld.Lead_Id ||'||LeadName='|| ld.First_Name ||''|| ld.Last_Name ||"+
                                         "'||LeadContactNo='||ld.Contact_No||'||LeadSubZone='||ld.Sales_Subzone||'||LeadZone='||ld.Sales_Zone||"+
                                         "'||LeadCity='||ld.Sales_City ||'||SMSSendTo='||sd.Contact_Number as PARAM_VALUES"+
                                         " from Lead_Details ld, Salespersonnel_Details sd where ld.Assigned_To=sd.Salespersonnel_id and ld.Lead_Id= "+objLeadVO.getLeadId());*/
            String sql= "select 'LeadID='||ld.Lead_Id ||'||LeadName='|| ld.First_Name ||''|| ld.Last_Name ||"+
                                         "'||LeadContactNo='||ld.Contact_No||'||LeadSubZone='||ld.Sales_Subzone||'||LeadZone='||ld.Sales_Zone||"+
                                         "'||LeadCity='||ld.Sales_City ||'||SMSSendTo='||sd.Contact_Number as PARAM_VALUES"+
                                         " from Lead_Details ld, Salespersonnel_Details sd where "+
                                         " ((ld.ASSIGNED_TO=sd.Salespersonnel_id AND  ld.ASSIGNED_TO IS NOT NULL)"+
                                         " OR (ld.ASSIGNED_TO_TL=sd.Salespersonnel_id) AND ld.ASSIGNED_TO IS NULL) and ld.LEAD_ID= ";
            
            System.out.println("sql:"+sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
            ParamValues = rs.getString("PARAM_VALUES");
                
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return ParamValues;
    }   
    //-- Code Ended by Muralidhar --
    //-- Code Commented by Muralidhar --   
    /* try{
            pstmt = con.prepareStatement("INSERT INTO WTB_SMS_DETAILS (CALLING_PROGRAM,MOBILE_NUMBER,ERROR_NUMBER,MESSAGE_TXT,SUBJECT_TXT,STATUS,RUN_NUMBER,MSG_SENT_TIME) VALUES('LSLP',?,0,?,?,0,0,sysdate)");
            pstmt.setString(2,mailBody.toString().replaceAll(WitribeConstants.NEW_LINE," "));
            pstmt.setString(3,WitribeConstants.MAIL_SUBJECT_LEAD);
            pstmt.setString(1,objLeadVO.getSalesContact());
           if(pstmt.executeUpdate()>0){
                status = true;
           }
            
        } catch(Exception e) {
          LogUtil.error("Exception: SMS Record with text "+mailBody.toString()+" not inserted  because of "+e.getMessage(),this.getClass()); 
        }
        return status;
    } *///-- Code Commented ended by Muralidhar --
         
    public List getLeadSource(Connection con) throws SQLException,Exception{
       List objList = null;
       objList = new ArrayList();
       Statement stmt = null;
       ResultSet rs = null;
        try{
                stmt = con.createStatement(); 
                rs = stmt.executeQuery("select LEAD_SOURCE_ID,LEAD_SOURCE, DISPLAY_STATUS from MST_LEAD_SOURCE WHERE DISPLAY_STATUS = 1");
            while(rs.next()){
                    LeadSourceVO leadsourcevo = new LeadSourceVO();
                    leadsourcevo.setLeadSourceId(rs.getInt(1));
                    leadsourcevo.setLeadSourceName(rs.getString(2));
                    leadsourcevo.setDisplayStatus(rs.getInt(3));
                    objList.add(leadsourcevo);
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
    
     public List  getISPList(Connection con) throws SQLException,Exception{
       List objList = null;
       objList = new ArrayList();
       Statement stmt = null;
       ResultSet rs = null;
        try{
                stmt = con.createStatement(); 
                rs = stmt.executeQuery("select ISP_ID,CURRENT_ISP, DISPLAY_STATUS from MST_ISP WHERE DISPLAY_STATUS = 1");
            while(rs.next()){
                    LeadISPVO leadisp = new LeadISPVO();
                    leadisp.setISP_Id(rs.getInt(1));
                    leadisp.setReqISP(rs.getString(2));
                    leadisp.setDisplayStatus(rs.getInt(3));
                    objList.add(leadisp);
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
   public List getLeadStatusHistory(LeadHistoryVO objLeadHistoryVO,Connection con) throws SQLException,Exception
    {
      PreparedStatement pstmt = null;
        ResultSet rs = null;
        List statusList = null;
        //String leadID= objLeadHistoryVO.getLeadID();
        try{
            pstmt = con.prepareStatement("SELECT LEAD_ID, SALESPERSONNEL_ID, MODIFIED_DATE, LEAD_COMMENT, STATUS,TRANSITION_STATE  from LEAD_HISTORY " +
                    " where LEAD_ID = ? order by MODIFIED_DATE desc");
  
             pstmt.setString(1,objLeadHistoryVO.getLeadID());
             rs= pstmt.executeQuery();
            
             statusList = new ArrayList();
             while(rs.next()) {
                 
               LeadHistoryVO objLeadHistory = new LeadHistoryVO();
               objLeadHistory.setLeadID(rs.getString(1)); 
               objLeadHistory.setSalesPersonID(rs.getString(2));
               objLeadHistory.setModifiedDate(rs.getString(3));
               //objLeadHistory.setLeadHistoryID(rs.getString(4));
               objLeadHistory.setComments(rs.getString(4));
               objLeadHistory.setStatus(rs.getString(5));
               objLeadHistory.setTransitionState(rs.getString(6));
               
               statusList.add(objLeadHistory);
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            
            
            se.printStackTrace();
            
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            
             e.printStackTrace();
            
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return statusList;
    } 
         
  public LeadHistoryVO getTransState(Connection con,LeadHistoryVO objLeadHistoryVO) throws SQLException,Exception
    {
      PreparedStatement pstmt = null;
        ResultSet rs = null;
        LeadHistoryVO objLeadHistory = null;
        //String leadID= objLeadHistoryVO.getLeadID();
        try{
            pstmt = con.prepareStatement("SELECT TRANSITION_STATE  from LEAD_HISTORY " +
                    " where LEAD_ID = ? order by MODIFIED_DATE desc");
  
             pstmt.setString(1,objLeadHistoryVO.getLeadID());
             rs= pstmt.executeQuery();
            
             if(rs.next()) {
                 
               objLeadHistory = new LeadHistoryVO();
               objLeadHistory.setTransitionState(rs.getString(1));
        
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return objLeadHistory;
    } 
    
}
