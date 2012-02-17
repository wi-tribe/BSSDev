/*
 * SalesPersonnelDAO.java
 *
 * Created on January 28, 2009, 12:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.dao;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.Poid;
import com.portal.pcm.PortalContext;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.FldAacSource;
import com.portal.pcm.fields.FldAccessCode1;
import com.portal.pcm.fields.FldAccessCode2;
import com.portal.pcm.fields.FldAddress;
import com.portal.pcm.fields.FldArgs;
import com.portal.pcm.fields.FldBusinessType;
import com.portal.pcm.fields.FldCity;
import com.portal.pcm.fields.FldCountry;
import com.portal.pcm.fields.FldCurrency;
import com.portal.pcm.fields.FldDescr;
import com.portal.pcm.fields.FldEmailAddr;
import com.portal.pcm.fields.FldFirstName;
import com.portal.pcm.fields.FldFlags;
import com.portal.pcm.fields.FldLastName;
import com.portal.pcm.fields.FldLogin;
import com.portal.pcm.fields.FldPasswd;
import com.portal.pcm.fields.FldPasswdClear;
import com.portal.pcm.fields.FldPhone;
import com.portal.pcm.fields.FldPoid;
import com.portal.pcm.fields.FldProcessedFlag;
import com.portal.pcm.fields.FldProgramName;
import com.portal.pcm.fields.FldResults;
import com.portal.pcm.fields.FldState;
import com.portal.pcm.fields.FldStatus;
import com.portal.pcm.fields.FldStatusFlags;
import com.portal.pcm.fields.FldStatuses;
import com.portal.pcm.fields.FldTemplate;
import com.portal.pcm.fields.FldTitle;
import com.portal.pcm.fields.FldZip;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.exception.DuplicateUserException;
import com.witribe.sales.vo.ChannelVO;
import com.witribe.sales.vo.SalesHierarchyVO;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.sales.vo.ShopsVO;
import com.witribe.util.DBUtil;
import com.witribe.util.LogUtil;
import com.witribe.vo.CityVO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SC43278
 */
public class SalesPersonnelDAO {
    
    /** Creates a new instance of SalesPersonnelDAO */
    public SalesPersonnelDAO() {
    }
    public long getMaxKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT MAX(TO_NUMBER(SALESPERSONNEL_ID)) from SALESPERSONNEL_DETAILS");
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
    public boolean checkDuplicate(Connection con,String userId) throws SQLException,Exception{
        boolean isDuplicate = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT USERID from SALESPERSONNEL_DETAILS where UPPER(USERID)=UPPER(?)");
            pstmt.setString(1,userId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                isDuplicate = true;
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
        return  isDuplicate;
    }
    
    //  Added by Muralidhar
    
     public boolean checkDuplicateFromBRM(Connection con,String userId) throws SQLException,Exception{
        boolean isDuplicate = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String poidtype = null;
        try{
            poidtype = "/service/admin_client";
            pstmt = con.prepareStatement("SELECT LOGIN from SERVICE_T where UPPER(LOGIN)=UPPER(?) and UPPER(POID_TYPE) = UPPER(?) ");
            pstmt.setString(1,userId);
            pstmt.setString(2,poidtype);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                isDuplicate = true;
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
        return  isDuplicate;
    }
     // End
    
    public boolean checkDuplicateLocation(Connection con,SalesPersonnelVO salesVo) throws SQLException,Exception{
        boolean isDuplicate = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String city = salesVo.getCity();
            String zone = salesVo.getZone();
            String country = salesVo.getCountry();
            if(salesVo.getSalesId() != null) {
                salesVo = getSale(salesVo.getSalesId(),con);
                //     country = salesVo.getCountry();
                //    city = salesVo.getCity();
            }
            
            if(salesVo.getSalestype().equals(WitribeConstants.TYPE_RSM)) {
                pstmt = con.prepareStatement("SELECT SALESPERSONNEL_ID FROM SALESPERSONNEL_DETAILS "+
                        " WHERE SALESPERSONNEL_ID IN ("+
                        " SELECT SALESPERSONNEL_ID from SALES_LOCATION"+
                        " where UPPER(LOCATION) = UPPER(?)) AND SALESPERSONNEL_TYPE = 2 AND  UPPER(ADDR_COUNTRY) = UPPER(?)  ");
                country = salesVo.getCountry();
                pstmt.setString(1,city);
                pstmt.setString(2,country);
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    isDuplicate = true;
                }
            } else if (salesVo.getSalestype().equals(WitribeConstants.TYPE_TL)){
                pstmt = con.prepareStatement("SELECT SALESPERSONNEL_ID FROM SALESPERSONNEL_DETAILS "+
                        " WHERE SALESPERSONNEL_ID IN ("+
                        " SELECT SALESPERSONNEL_ID from SALES_LOCATION"+
                        " where UPPER(LOCATION) = UPPER(?)) AND SALESPERSONNEL_TYPE = 3  AND  UPPER(ADDR_CITY) = UPPER(?)"+
                        " AND  UPPER(ADDR_COUNTRY) = UPPER(?)");
                pstmt.setString(1,zone);
                pstmt.setString(2,salesVo.getCity());
                pstmt.setString(3,salesVo.getCountry());
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    isDuplicate = true;
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
        return  isDuplicate;
    }
   /* public boolean checkDuplicateCity(Connection con,SalesPersonnelVO salesVo) throws SQLException,Exception{
        boolean isDuplicate = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT distinct SALESPERSONNEL_ID from SALESPERSONNEL_DETAILS " +
                    "where UPPER(SALESPERSONNEL_TYPE)=UPPER(?) AND " +
                    "UPPER(ADDR_CITY) = UPPER(?) ");
            pstmt.setString(1,salesVo.getSalestype());
            pstmt.setString(2,salesVo.getCity());
            rs = pstmt.executeQuery();
            if(rs.next()) {
               isDuplicate = true;
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
        return  isDuplicate;
    }
    public boolean checkDuplicateZone(Connection con,SalesPersonnelVO salesVo) throws SQLException,Exception{
        boolean isDuplicate = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT distinct SALESPERSONNEL_ID from SALESPERSONNEL_DETAILS " +
                    "where UPPER(SALESPERSONNEL_TYPE)=UPPER(?) AND " +
                    "UPPER(ADDR_CITY) = UPPER(?) AND UPPER(ADDR_ZONE) = UPPER(?)");
            pstmt.setString(1,salesVo.getSalestype());
            pstmt.setString(2,salesVo.getCity());
            pstmt.setString(3,salesVo.getZone());
            rs = pstmt.executeQuery();
            if(rs.next()) {
               isDuplicate = true;
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
        return  isDuplicate;
    }*/
    public boolean createSale(SalesPersonnelVO objSalesVO,Connection con,Connection brmcon) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        con.setAutoCommit(false);
        try{
            
            long key = getMaxKey(con);
            ++key;
            boolean duplicate = checkDuplicate(con,objSalesVO.getUserId());
            boolean brmduplicate = checkDuplicateFromBRM(brmcon,objSalesVO.getUserId());
            if(duplicate || brmduplicate)
                throw new DuplicateUserException();
            
            pstmt = con.prepareStatement(
                    "INSERT INTO SALESPERSONNEL_DETAILS (SALESPERSONNEL_ID,SALESPERSONNEL_TYPE,"  +
                    " FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE," +
                    " ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                    " ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,FIRST_NAME,LAST_NAME,CHANNEL_TYPE,SALESPERSON_STATUS) " +
                    " VALUES " +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            String fullname = objSalesVO.getFirstname()+" "+objSalesVO.getLastname();
            
            pstmt.setLong(1, key);
            pstmt.setString(2, objSalesVO.getSalestype() );
            pstmt.setString(3, fullname);
            pstmt.setString(4, objSalesVO.getContactnumber());
            pstmt.setString(5, objSalesVO.getEmail());
            pstmt.setString(6, objSalesVO.getUserId());
            pstmt.setString(7, objSalesVO.getPassword());
            pstmt.setString(8, objSalesVO.getPlot());
            pstmt.setString(9, objSalesVO.getStreet());
            pstmt.setString(10, objSalesVO.getSubzone());
            pstmt.setString(11, objSalesVO.getZone());
            pstmt.setString(12, objSalesVO.getCity());
            pstmt.setString(13, objSalesVO.getProvince());
            pstmt.setString(14, objSalesVO.getCountry());
            pstmt.setString(15, objSalesVO.getZip());
            
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(16, sqlDate);
            pstmt.setString(17, objSalesVO.getFirstname());
            pstmt.setString(18, objSalesVO.getLastname());
            if(objSalesVO.getSalestype().equalsIgnoreCase(WitribeConstants.TYPE_CSE)){
                pstmt.setString(19, objSalesVO.getChanneltype());
            }else{
                pstmt.setString(19,"");
            }
            pstmt.setString(20, "Active");
          
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
            }
            
            String salesType = objSalesVO.getSalestype();
            if (WitribeConstants.TYPE_RSM.equalsIgnoreCase(salesType) ||
                    WitribeConstants.TYPE_TL.equalsIgnoreCase(salesType)||
                    WitribeConstants.TYPE_CSE.equalsIgnoreCase(salesType)) {
                if(status){
                    status = false;
                    pstmt = con.prepareStatement("INSERT INTO SALES_LOCATION (SALESPERSONNEL_ID,LOCATION,CREATE_DATE)" +
                            " VALUES  (?,?,?)");
                    
                    pstmt.setString(1,String.valueOf(key));
                    if(WitribeConstants.TYPE_RSM.equalsIgnoreCase(salesType)) {
                        pstmt.setString(2, objSalesVO.getCity());
                    } else if (WitribeConstants.TYPE_TL.equalsIgnoreCase(salesType)) {
                        pstmt.setString(2, objSalesVO.getZone());
                    }else if (WitribeConstants.TYPE_CSE.equalsIgnoreCase(salesType)) {
                        pstmt.setString(2, objSalesVO.getSubzone());
                    }
                    pstmt.setTimestamp(3, sqlDate);
                    
                    if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                        status = true;
                    }
                }
            }
            if(status) {  
                Integer result =0;
               if(salesType.equalsIgnoreCase("4") || salesType.equalsIgnoreCase("-1") || salesType.equalsIgnoreCase("2") || salesType.equalsIgnoreCase("3")) {
                   Properties properties = new Properties();
                   properties.load(this.getClass().getClassLoader().getResourceAsStream("Infranet.properties"));
                   PortalContext ctx = new PortalContext(properties);
                   ctx.connect();
                   FList inFlist = new FList();
                   inFlist.set(FldPoid.getInst(),new Poid(1, -1, "/account"));
                   inFlist.set(FldLogin.getInst(),objSalesVO.getUserId());
                   inFlist.set(FldPasswdClear.getInst(),objSalesVO.getPassword());
                   String strSalesType = "";
                    if(salesType.equalsIgnoreCase("4")){
                       strSalesType = "CSE";
                    }
                    if(salesType.equalsIgnoreCase("-1")){
                       strSalesType = "CSR";
                    }
                   // Added by Muralidhar
                   if(salesType.equalsIgnoreCase("2")){
                       strSalesType = "RSM";
                    }
                   if(salesType.equalsIgnoreCase("3")){
                       strSalesType = "TL";
                    }
                   // End
                   inFlist.set(FldTitle.getInst(),strSalesType);
                   inFlist.set(FldFirstName.getInst(),objSalesVO.getFirstname());
                   inFlist.set(FldLastName.getInst(),objSalesVO.getLastname());
                   inFlist.set(FldPhone.getInst(),objSalesVO.getContactnumber());
                   inFlist.set(FldEmailAddr.getInst(),objSalesVO.getEmail());
                   inFlist.set(FldCountry.getInst(),objSalesVO.getCountry());
                   inFlist.set(FldZip.getInst(),objSalesVO.getZip());
                   inFlist.set(FldState.getInst(),objSalesVO.getProvince());
                   inFlist.set(FldCity.getInst(),objSalesVO.getCity());
                   inFlist.set(FldAddress.getInst(),objSalesVO.getPlot()+"   "+objSalesVO.getStreet()+"   "+objSalesVO.getSubzone()+"   "+objSalesVO.getZone());
                   inFlist.set(FldAccessCode1.getInst(),Long.toString(key));
                   inFlist.set(FldAccessCode2.getInst(),objSalesVO.getChanneltype());
                   inFlist.set(FldAacSource.getInst(),"");
                   inFlist.set(FldCurrency.getInst(),586);
                   inFlist.set(FldBusinessType.getInst(),6);
                    FList out = new FList();
                    out = ctx.opcode(100060, inFlist);
                    result = out.get(FldProcessedFlag.getInst());
                    if(result.intValue()== 1)
                    con.commit();
                }else{
                    con.commit();
                }                  
                
            }
            con.rollback();
        } catch(SQLException se){
            con.rollback();
            DBUtil.closeStatement(pstmt);
            throw se;
        }catch(DuplicateUserException se){
            con.rollback();
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            con.rollback();
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
    }
    
  
    public SalesPersonnelVO getSale(String salesId,Connection con) throws SQLException,Exception{
        SalesPersonnelVO objSalesVO = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        try{
            pstmt = con.prepareStatement(
                    "SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE," +
                    "FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE," +
                    "ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                    "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE,FIRST_NAME,LAST_NAME,CHANNEL_TYPE FROM SALESPERSONNEL_DETAILS WHERE  SALESPERSONNEL_ID = ?");
            pstmt1 = con.prepareStatement("SELECT LOCATION FROM SALES_LOCATION WHERE " +
                                "SALESPERSONNEL_ID= ? ");  
            pstmt.setString(1,salesId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                //objSalesVO.setFullname(rs.getString(3));
                objSalesVO.setFullname(rs.getString(18)+" "+rs.getString(19));
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
                objSalesVO.setCreateDate(rs.getString(16));
                objSalesVO.setModifiedDate(rs.getString(17));
                objSalesVO.setFirstname(rs.getString(18));
                objSalesVO.setLastname(rs.getString(19));
                objSalesVO.setChanneltype(rs.getString(20));
                StringBuffer cityString = new StringBuffer("");
                StringBuffer zoneString = new StringBuffer("");
                StringBuffer subZoneString = new StringBuffer("");
                if((WitribeConstants.TYPE_RSM).equals(objSalesVO.getSalestype())) {
                        pstmt1.setString(1,objSalesVO.getSalesId());
                        rs1 = pstmt1.executeQuery();
                        while(rs1.next()) {
                            cityString.append(rs1.getString(1));
                            cityString.append(", ");
                        }
                         if(cityString.length()>0) {
                            String str = cityString.toString();
                            str = str.substring(0,str.length()-2);
                            objSalesVO.setCity(str);
                         }
                } else if((WitribeConstants.TYPE_TL).equals(objSalesVO.getSalestype())) {
                        pstmt1.setString(1,objSalesVO.getSalesId());
                        rs1 = pstmt1.executeQuery();
                        while(rs1.next()) {
                            zoneString.append(rs1.getString(1));
                            zoneString.append(", ");
                        }
                        if(zoneString.length()>0) {
                            String str = zoneString.toString();
                            str = str.substring(0,str.length()-2);
                            objSalesVO.setZone(str);
                        }
                } else if((WitribeConstants.TYPE_CSE).equals(objSalesVO.getSalestype())) {
                        pstmt1.setString(1,objSalesVO.getSalesId());
                        rs1 = pstmt1.executeQuery();
                        while(rs1.next()) {
                            subZoneString.append(rs1.getString(1));
                            subZoneString.append(", ");
                        }
                        if(subZoneString.length()>0) {
                            String str = subZoneString.toString();
                            str = str.substring(0,str.length()-2);
                            objSalesVO.setSubzone(str);
                        }
                }
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
            DBUtil.closeResultSet(rs1);
            DBUtil.closeStatement(pstmt1);            
        }
        if(objSalesVO.getSubzone() == null || "".equals(objSalesVO.getSubzone())){
            objSalesVO.setSubzone(" ");
        } 
        if(objSalesVO.getZone() == null || "".equals(objSalesVO.getZone())){
            objSalesVO.setZone(" ");
        }
        if(objSalesVO.getCity() == null || "".equals(objSalesVO.getCity())){
            objSalesVO.setCity(" ");
        }
        return objSalesVO;
        
    }
    public List getSales(Connection con) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        SalesPersonnelVO objSalesVO = null;
        ResultSet rs = null;        
        try{
            pstmt = con.prepareStatement(
                    "SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE," +
                    "FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE," +
                    "ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                    "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS" +
                    " WHERE SALESPERSONNEL_TYPE NOT IN (?)  ORDER BY CREATED_DATE asc");
            pstmt.setString(1,WitribeConstants.TYPE_ADMIN);
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            int count = WitribeConstants.ZERO;
            while(rs.next()){
                objSalesVO = new SalesPersonnelVO();
                
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                //objSalesVO.setFullname(rs.getString(3));
                objSalesVO.setFullname(rs.getString(18)+" "+rs.getString(19));
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
                objSalesVO.setCreateDate(rs.getString(16));
                objSalesVO.setModifiedDate(rs.getString(17));
                objSalesVO.setFirstname(rs.getString(18));
                objSalesVO.setLastname(rs.getString(19));
                objList.add(objSalesVO);
                count++;
                if(count == WitribeConstants.MAX_ROW_COUNT)
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
    public long getSaleRowCount(Connection con,String queryCont) throws SQLException,Exception{
        long rowsCount = WitribeConstants.ROWS_COUNT;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.prepareStatement("SELECT count(*) from SALESPERSONNEL_DETAILS WHERE "+queryCont);
           
            rs = stmt.executeQuery();
            if(rs.next()) {
                rowsCount = rs.getInt(1);
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
    public List getNextSales(Connection con,int pageNum,SalesPersonnelVO objSales) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; 
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;         
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        long totalRows;
        int startRow = 0;
        int endRow = 0;
        try{
            startRow = (pageNum*maxRowCount)+1;
            endRow = (pageNum+1)*maxRowCount+1;
            int role = Integer.parseInt(objSales.getSalestype());
            String salesId = objSales.getSalesId();
            String country = objSales.getCountry();
            String city = objSales.getCity();
            String zone = objSales.getZone();
            String queryCont = "";
                      
         if (role == Integer.parseInt(WitribeConstants.TYPE_ADMIN)) {
            queryCont = " SALESPERSONNEL_TYPE >  "+WitribeConstants.TYPE_ADMIN +" OR SALESPERSONNEL_TYPE <"+WitribeConstants.TYPE_ADMIN;
        } else if (role == Integer.parseInt(WitribeConstants.TYPE_NSM)) {
            queryCont = " (SALESPERSONNEL_TYPE > "+WitribeConstants.TYPE_NSM+" OR SALESPERSONNEL_TYPE <"+WitribeConstants.TYPE_ADMIN+") AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"')";
        }else if (role == Integer.parseInt(WitribeConstants.TYPE_RSM)) {
            queryCont = " (SALESPERSONNEL_TYPE > "+WitribeConstants.TYPE_RSM+" OR SALESPERSONNEL_TYPE <"+WitribeConstants.TYPE_ADMIN+") AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND " +
                    "UPPER(ADDR_CITY) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+")";
        }else if (role == Integer.parseInt(WitribeConstants.TYPE_TL)) {
            queryCont = " (SALESPERSONNEL_TYPE > "+WitribeConstants.TYPE_TL+" OR SALESPERSONNEL_TYPE <"+WitribeConstants.TYPE_ADMIN+")  AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND " +
                    "UPPER(ADDR_CITY) =UPPER('"+city+"')"+" AND " +
                    "UPPER(ADDR_ZONE) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+")";
        }
            
            pstmt = con.prepareStatement("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                    "SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE," +
                    "FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE,"+
                    "ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                    "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE,FIRST_NAME,LAST_NAME,CHANNEL_TYPE,SALESPERSON_STATUS FROM SALESPERSONNEL_DETAILS " +
                    "WHERE UPPER(SALESPERSON_STATUS) =UPPER('Active') AND "+queryCont+"   ORDER BY ADDR_CITY,SALESPERSONNEL_TYPE,ADDR_ZONE,ADDR_SUBZONE ) a WHERE rownum <="+endRow+") WHERE rn >="+startRow);
            pstmt1 = con.prepareStatement("SELECT LOCATION FROM SALES_LOCATION WHERE " +
                                "SALESPERSONNEL_ID= ? ");           
            rs = pstmt.executeQuery();
     
            totalRows = getSaleRowCount(con,queryCont);
           /* if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }*/
            
            objList = new ArrayList();
            int count = WitribeConstants.ZERO;
            while(rs.next()){
                SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
                count++;
               if(count>maxRowCount) {
                   objList.add("true");
                   break;
               }
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                //objSalesVO.setFullname(rs.getString(3));
                objSalesVO.setFullname(rs.getString(18)+" "+rs.getString(19));
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
                objSalesVO.setCreateDate(rs.getString(16));
                objSalesVO.setModifiedDate(rs.getString(17));
                objSalesVO.setFirstname(rs.getString(18));
                objSalesVO.setLastname(rs.getString(19));
                objSalesVO.setChanneltype(rs.getString(20));
                objSalesVO.setSalespersonstatus(rs.getString(21));
                StringBuffer cityString = new StringBuffer("");
                StringBuffer zoneString = new StringBuffer("");
                StringBuffer subZoneString = new StringBuffer("");
                if((WitribeConstants.TYPE_RSM).equals(objSalesVO.getSalestype())) {
                        pstmt1.setString(1,objSalesVO.getSalesId());
                        rs1 = pstmt1.executeQuery();
                        while(rs1.next()) {
                            cityString.append(rs1.getString(1));
                            cityString.append(", ");
                        }
                        String str = cityString.toString();
                        if(!str.equalsIgnoreCase(""))
                        str = str.substring(0,str.length()-2);
                        objSalesVO.setCity(str);
                } else if((WitribeConstants.TYPE_TL).equals(objSalesVO.getSalestype())) {
                        pstmt1.setString(1,objSalesVO.getSalesId());
                        rs1 = pstmt1.executeQuery();
                        while(rs1.next()) {
                            zoneString.append(rs1.getString(1));
                            zoneString.append(", ");
                        }
                        String str = zoneString.toString();
                        if(!str.equalsIgnoreCase(""))
                        str = str.substring(0,str.length()-2);
                        objSalesVO.setZone(str);
                }else if((WitribeConstants.TYPE_CSE).equals(objSalesVO.getSalestype())) {
                        pstmt1.setString(1,objSalesVO.getSalesId());
                        rs1 = pstmt1.executeQuery();
                        while(rs1.next()) {
                            subZoneString.append(rs1.getString(1));
                            subZoneString.append(", ");
                        }
                        if(subZoneString.length()>0) {
                            String str = subZoneString.toString();
                            if(!str.equalsIgnoreCase(""))
                            str = str.substring(0,str.length()-2);
                            objSalesVO.setSubzone(str);
                        }
                }
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
            DBUtil.closeResultSet(rs1);            
            DBUtil.closeStatement(pstmt1);
        }
        
        return objList;
        
    }
    public List getSalesLocation(Connection con,int pageNum,SalesPersonnelVO objSales) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;        
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        long totalRows;
        int startRow = 0;
        int endRow = 0;
        try{
            startRow = (pageNum*maxRowCount)+1;
            endRow = (pageNum+1)*maxRowCount+1;
            pstmt = con.prepareStatement("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                   "SELECT  sd.SALESPERSONNEL_ID,SALESPERSONNEL_TYPE," +
                    "FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE,"+
                    "ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE,ADDR_COUNTRY,ADDR_ZIP,sl.LOCATION,FIRST_NAME,LAST_NAME " +
                    " from SALESPERSONNEL_DETAILS sd,SALES_LOCATION sl " +
                    "where sd.SALESPERSONNEL_ID = sl.SALESPERSONNEL_ID and ((sd.SALESPERSONNEL_TYPE ="+WitribeConstants.TYPE_RSM+
                    "and UPPER(sd.ADDR_CITY) <> UPPER(sl.LOCATION)) or (sd.SALESPERSONNEL_TYPE = "+WitribeConstants.TYPE_TL+" and UPPER(sd.ADDR_ZONE) <> UPPER(sl.LOCATION))" +
                    "or (sd.SALESPERSONNEL_TYPE ="+ WitribeConstants.TYPE_CSE +"and UPPER(sd.ADDR_SUBZONE) <> UPPER(sl.LOCATION))) " +
                    "order by TO_NUMBER(sd.SALESPERSONNEL_ID) ) a WHERE rownum <="+endRow+") WHERE rn >="+startRow);
           
            rs = pstmt.executeQuery();
     
          
            objList = new ArrayList();
            int count = WitribeConstants.ZERO;
            while(rs.next()){
                SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
                count++;
               if(count>maxRowCount) {
                   objList.add("true");
                   break;
               }
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                //objSalesVO.setFullname(rs.getString(3));
                objSalesVO.setFullname(rs.getString(17)+" "+rs.getString(18));
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
                objSalesVO.setLocation(rs.getString(16));
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
        
        return objList;
        
    }
    public boolean modifySalesInfo( SalesPersonnelVO objSalesVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        String role = objSalesVO.getSalestype();
        con.setAutoCommit(false);
        boolean pwdChange = false;       
        try{
            if(objSalesVO.getUserId() == null) {//updating selected sales person
                
                pstmt = con.prepareStatement(
                        "UPDATE SALESPERSONNEL_DETAILS set " +
                        "FULL_NAME = ?,CONTACT_NUMBER = ?,EMAIL = ?,ADDR_PLOT = ?,ADDR_STREET = ?,MODIFIED_DATE = ?,FIRST_NAME = ?, LAST_NAME = ? "+
                        " where SALESPERSONNEL_ID = ?");
                String fullName=objSalesVO.getFirstname()+" "+objSalesVO.getLastname();
                // pstmt.setString(1, objSalesVO.getSalestype());
                pstmt.setString(1, fullName);
                pstmt.setString(2, objSalesVO.getContactnumber());
                pstmt.setString(3, objSalesVO.getEmail());
               // pstmt.setString(4, objSalesVO.getUserId());
               // pstmt.setString(5, objSalesVO.getPassword());
                pstmt.setString(4, objSalesVO.getPlot());
                pstmt.setString(5, objSalesVO.getStreet());
                pstmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, objSalesVO.getFirstname());
                pstmt.setString(8, objSalesVO.getLastname());
                pstmt.setString(9, objSalesVO.getSalesId());
            } else {//Updating salesperson profile who has logged in
                pwdChange = true;
                pstmt = con.prepareStatement(
                        "UPDATE SALESPERSONNEL_DETAILS set " +
                        "FULL_NAME = ?,CONTACT_NUMBER = ?,EMAIL = ?,USERID=?, PASSWORD=?,ADDR_PLOT = ?,ADDR_STREET = ?,MODIFIED_DATE = ?,FIRST_NAME = ?, LAST_NAME = ? "+
                        " where SALESPERSONNEL_ID = ?");
                String fullName=objSalesVO.getFirstname()+" "+objSalesVO.getLastname();
                // pstmt.setString(1, objSalesVO.getSalestype());
                pstmt.setString(1, fullName);
                pstmt.setString(2, objSalesVO.getContactnumber());
                pstmt.setString(3, objSalesVO.getEmail());
                pstmt.setString(4, objSalesVO.getUserId());
                pstmt.setString(5, objSalesVO.getPassword());
                pstmt.setString(6, objSalesVO.getPlot());
                pstmt.setString(7, objSalesVO.getStreet());
                pstmt.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
                pstmt.setString(9, objSalesVO.getFirstname());
                pstmt.setString(10, objSalesVO.getLastname());
                pstmt.setString(11, objSalesVO.getSalesId());
                
            }
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
                 //Production Support: This code is added to update CSE,CSR profile with encrypted password added on 30th july 09
                
                 if(status && (role.equals(WitribeConstants.TYPE_CSE) || role.equals(WitribeConstants.TYPE_CSR))){
                    setUserNamePwdAddress(objSalesVO,pwdChange,con);
                    if(!(updateSalesinfoOnBRM(objSalesVO))){
                       con.rollback();
                       status=false;
                    } 
                    
                 } 
               
            }
            con.commit();
            
        } catch(SQLException se){
            con.rollback();
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            con.rollback();
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeStatement(pstmt);
            con.commit();
            con.setAutoCommit(true) ;
        }
        
        return status;
        
        
    }
    public boolean deleteSalesInfo(Connection con,String[] check) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
         con.setAutoCommit(false);
        try{
            
            StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");
                pstmt = con.prepareStatement("DELETE SALES_LOCATION where TO_NUMBER(SALESPERSONNEL_ID) IN "+inString.toString());
                pstmt.executeUpdate();
                pstmt = con.prepareStatement(
                        "DELETE SALESPERSONNEL_DETAILS where TO_NUMBER(SALESPERSONNEL_ID) IN "+inString.toString());
                pstmt.executeUpdate();
                status = true;
                con.commit();
             }
        } catch(SQLException se){
            status = false;
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{ 
            con.rollback();
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
        
    }
     public boolean closeCseInfo(Connection con,String[] check,Connection brmcon) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        SalesPersonnelDAO objSalesDAO = null;
        con.setAutoCommit(false);
        brmcon.setAutoCommit(false);
        try{
            
           /* StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");*/
             if(check[0] != null) {
                pstmt = con.prepareStatement("UPDATE SALESPERSONNEL_DETAILS SET SALESPERSON_STATUS = 'Closed'  WHERE  TO_NUMBER(SALESPERSONNEL_ID) = TO_NUMBER(?) ");
                pstmt.setString(1,check[0]);
                pstmt.executeUpdate();
                status = true;
                if(status) {
                    objSalesDAO = new SalesPersonnelDAO();
                    status = objSalesDAO.removeLeadsFromCse(con,check,brmcon);
                      
                    if(status){
                          objSalesDAO = new SalesPersonnelDAO();
                             status = objSalesDAO.removeInventoryFromCse(con,check,brmcon);
                       
                          }
                   String CseStatus ="Closed"; 
                    if(!(updateCseStatusBRM(check, CseStatus))){
                    status=false;
                    
                }
                 status = true; 
                 con.commit();
                 brmcon.commit();    
                }
                
             }
        } finally{ 
            con.rollback();
            con.setAutoCommit(true);
            brmcon.rollback();
            brmcon.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
        
    }
       public boolean activeCseInfo(Connection con,String[] check) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
         con.setAutoCommit(false);
        try{
            
            StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");
                pstmt = con.prepareStatement("UPDATE SALESPERSONNEL_DETAILS SET SALESPERSON_STATUS = 'Active' where UPPER( SALESPERSON_STATUS) = UPPER('Inactive') AND  TO_NUMBER(SALESPERSONNEL_ID) IN "+inString.toString());
                pstmt.executeUpdate();
                String CseStatus ="Active"; 
                    if(!(updateCseStatusBRM(check, CseStatus))){
                    status=false;
                }
                status = true;
                con.commit();
             }
        } catch(SQLException se){
            status = false;
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{ 
            con.rollback();
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
        
    }
       
        public boolean inactiveCseInfo(Connection con,String[] check,Connection brmcon) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
         con.setAutoCommit(false);
        try{
            
            StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");
                pstmt = con.prepareStatement("UPDATE SALESPERSONNEL_DETAILS SET SALESPERSON_STATUS = 'Inactive' where UPPER( SALESPERSON_STATUS) = UPPER('Active') AND  TO_NUMBER(SALESPERSONNEL_ID) IN "+inString.toString());
                pstmt.executeUpdate();
                String CseStatus ="Inactive"; 
                if(!(updateCseStatusBRM(check, CseStatus))){
                status=false;
                }
                status = true;
                con.commit();
             }
        } catch(SQLException se){
            status = false;
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{ 
            con.rollback();
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
         }
         public boolean activeShopInfo(Connection con,String[] check,Connection brmcon) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        SalesPersonnelDAO objSalesDAO = null;
        
         con.setAutoCommit(false);
        try{
            
         /*   StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");*/
                  if(check[0] != null) {
                pstmt = con.prepareStatement("UPDATE SHOP_DETAILS SET SHOP_STATUS = 'Active' where UPPER( SHOP_STATUS) = UPPER('Inactive') AND  SHOP_ID = ? " );
                pstmt.setString(1,check[0]);
                pstmt.executeUpdate();
                 status = true;
                if(status) {
                  objSalesDAO = new SalesPersonnelDAO();
                  status = objSalesDAO.activateCseOfShop(con,check,brmcon);
                
               }
                status = true;
                con.commit();
             }
       
        } finally{ 
            con.rollback();
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
         }
         public boolean inactiveShopInfo(Connection con,String[] check,Connection brmcon) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
          SalesPersonnelDAO objSalesDAO = null;
       
         con.setAutoCommit(false);
        try{
            
          /*  StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");*/
               if(check[0] != null) {
                pstmt = con.prepareStatement("UPDATE SHOP_DETAILS SET SHOP_STATUS = 'Inactive' where UPPER( SHOP_STATUS) = UPPER('Active') AND  SHOP_ID = ? ");
                 pstmt.setString(1,check[0]);
                pstmt.executeUpdate();
                 status = true;
                if(status) {
                  objSalesDAO = new SalesPersonnelDAO();
                   status = objSalesDAO.inactivateCseOfShop(con,check,brmcon);
                
                }
                status = true;
                con.commit();
             }
        
        } finally{ 
            con.rollback();
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
         }
         
       public boolean inactivateCseOfShop(Connection con,String[] check,Connection brmcon) throws SQLException,Exception{
        boolean status = false;
        List objList = null;
        objList = new ArrayList();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
         con.setAutoCommit(false);
        try{
            
           /* StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");*/
              if(check[0] != null) {
                pstmt = con.prepareStatement("UPDATE SALESPERSONNEL_DETAILS SET SALESPERSON_STATUS = 'Inactive' where UPPER( SALESPERSON_STATUS) = UPPER('Active')" +
                        " AND SALESPERSONNEL_ID IN (select CHILD_SALESPERSONNEL_ID from SALES_HIERARCHY where  SHOP_ID = ?)");
                  pstmt.setString(1,check[0]);
                  pstmt.executeUpdate();
               pstmt1= con.prepareStatement("SELECT CHILD_SALESPERSONNEL_ID from SALES_HIERARCHY where  SHOP_ID =? ");
                 pstmt1.setString(1,check[0]);
                 ResultSet rs = pstmt1.executeQuery();
                    while(rs.next()){
                    SalesHierarchyVO shvo = new SalesHierarchyVO();
                    shvo.setChild_salespersonnel_id(rs.getString(1));
                    objList.add(shvo); 
                    }
                String CseStatus ="Inactive"; 
                if(!(updateCseStatusForShopBRM(check,CseStatus,objList))){
                status=false;
                }
                status = true;
                con.commit();
             }
        
        } finally{ 
            con.rollback();
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
         }

    public boolean activateCseOfShop(Connection con,String[] check,Connection brmcon) throws SQLException,Exception{
        boolean status = false;
        List objList = null;
        objList = new ArrayList();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
         con.setAutoCommit(false);
        try{
            
           /* StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");*/
             if(check[0] != null) {
                pstmt = con.prepareStatement("UPDATE SALESPERSONNEL_DETAILS SET SALESPERSON_STATUS = 'Active' where UPPER( SALESPERSON_STATUS) = UPPER('Inactive')" +
                        " AND TO_NUMBER(SALESPERSONNEL_ID) IN (select CHILD_SALESPERSONNEL_ID from SALES_HIERARCHY where  SHOP_ID =? )");
                pstmt.setString(1,check[0]);
                pstmt.executeUpdate();
                 pstmt1= con.prepareStatement("SELECT CHILD_SALESPERSONNEL_ID from SALES_HIERARCHY where  SHOP_ID =?");
                    pstmt1.setString(1,check[0]);
                  ResultSet rs = pstmt1.executeQuery();
                 while(rs.next()){
                    SalesHierarchyVO shvo = new SalesHierarchyVO();
                    shvo.setChild_salespersonnel_id(rs.getString(1));
                    objList.add(shvo); 
                    }
                String CseStatus ="Active"; 
                if(!(updateCseStatusForShopBRM(check, CseStatus,objList))){
                status=false;
                }
                status = true;
                con.commit();
             }
        
        } finally{ 
            con.rollback();
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
         }
       public boolean updateCseStatusBRM(String[] check, String CseStatus)throws Exception 
         {
        Integer result = 0;
       // String[] subzone = null;
       
        StringBuffer inString = new StringBuffer("(");
        Poid Account_obj=null; 
        Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("Infranet.properties"));
            PortalContext ctx = new PortalContext(properties);
            ctx.connect();
        
      /*  if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");
        
                  */
       
                                
                
                try {
          /*  if(objSalesVO.getSubzone() != null){
               subzone = objSalesVO.getSubzone().split(",");
               
            }*/
            
           
            FList inFlist = new FList();
            FList flOrder =new FList();
            inFlist.set(FldPoid.getInst(),new Poid(1, -1, "/search"));
            inFlist.set(FldFlags.getInst(), 256);
             StringBuffer template = new StringBuffer(1024);
              template.append("select X from /account where F1 = V1 ");
             inFlist.set(FldTemplate.getInst(), template.toString());
              SparseArray args = new SparseArray();
               SparseArray Results = new SparseArray();
               FList subFlist = new FList();
              //FList subFlist1 = new FList();
              subFlist.set(FldAccessCode1.getInst(),check[0]);
              args.add(1,subFlist);
              inFlist.set(FldArgs.getInst(),args);
              inFlist.set(FldResults.getInst());
              FList out1 = new FList();
          
              out1 = ctx.opcode(7, inFlist);
             out1.dump();
              Results=out1.get(FldResults.getInst());
              flOrder=Results.elementAt(0);
              Account_obj = flOrder.get(FldPoid.getInst());
        }   
         catch (EBufException ex) {
           // ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        } 
        catch (Exception ex) {
            //ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        }
                  
              
              int Status_flag=0;        
                         
             if (CseStatus.equals("Inactive")){
                   Status_flag=10102;
              }
              else if(CseStatus.equals("Active")){
                  Status_flag=10100;
              }
              else {
                 Status_flag=10103;
              }                  
                  
             /* 
              0 PIN_FLD_POID           POID [0] 0.0.0.1 /account 18679 17
              0 PIN_FLD_PROGRAM_NAME    STR [0] "Customer Center"
              0 PIN_FLD_DESCR           STR [0] "[Sep 16, 2009 4:46 PM  Bill not paid]\n"
              0 PIN_FLD_STATUSES      ARRAY [0] allocated 20, used 2
              1     PIN_FLD_STATUS_FLAGS    INT [0] 4
              1     PIN_FLD_STATUS         ENUM [0] 10102
                       */ 
             
             
             try{
              FList flUpdate=new FList();
             FList Status=new FList();
             
             SparseArray Statuses=new SparseArray();
             
             flUpdate.set(FldPoid.getInst(),Account_obj);
             flUpdate.set(FldProgramName.getInst(),"CRM");
             flUpdate.set(FldDescr.getInst(),"Changing CSE Status From LSLP");
             Status.set(FldStatusFlags.getInst(),4);
             Status.set(FldStatus.getInst(),Status_flag);
             Statuses.add(1,Status);
             flUpdate.set(FldStatuses.getInst(),Statuses);
            FList flUpdateOut = ctx.opcode(62, flUpdate);
             }             
        catch (EBufException ex) {
           // ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        } 
        catch (Exception ex) {
            //ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        }
         return true; 
         } 
       public boolean updateCseStatusForShopBRM(String[] check, String CseStatus,List objList)throws Exception 
         {
        Integer i = 0;
        Integer result = 0;
       // String[] subzone = null;
      // String CseStatus = CseStatus;
      //  StringBuffer inString = new StringBuffer("(");
        Poid Account_obj=null; 
        Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("Infranet.properties"));
            PortalContext ctx = new PortalContext(properties);
            ctx.connect();
        
       /*   if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");*/
        
     
       int Salessize = objList.size();
           for(int j=0;j<Salessize;j++){
                try {
          /*  if(objSalesVO.getSubzone() != null){
               subzone = objSalesVO.getSubzone().split(",");
               
            }*/
           
            SalesHierarchyVO shvo = (SalesHierarchyVO)objList.get(j);
          
            FList inFlist = new FList();
            FList flOrder =new FList();
            inFlist.set(FldPoid.getInst(),new Poid(1, -1, "/search"));
            inFlist.set(FldFlags.getInst(), 256);
            StringBuffer template = new StringBuffer(1024);
            template.append("select X from /account where F1 = V1 ");
            inFlist.set(FldTemplate.getInst(), template.toString());
            SparseArray args = new SparseArray();
            SparseArray Results = new SparseArray();
            FList subFlist = new FList();
              //FList subFlist1 = new FList();
            subFlist.set(FldAccessCode1.getInst(),shvo.getChild_salespersonnel_id());
            
            args.add(1,subFlist);
            inFlist.set(FldArgs.getInst(),args);
            inFlist.set(FldResults.getInst());
            System.out.println(inFlist.toString());
            inFlist.dump();
            FList out1 = new FList();
             out1 = ctx.opcode(7, inFlist);
             out1.dump();
             if(out1.hasField(FldResults.getInst())){ 
             Results=out1.get(FldResults.getInst());
              flOrder=Results.elementAt(0);
              Account_obj = flOrder.get(FldPoid.getInst());
             }
      }
       
         catch (EBufException ex) {
           // ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        } 
        catch (Exception ex) {
            //ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        }
                  
       
              int Status_flag=0;        
                         
             if (CseStatus.equals("Inactive")){
                   Status_flag=10102;
              }
              else if(CseStatus.equals("Active")){
                  Status_flag=10100;
              }
               
             /* 
              0 PIN_FLD_POID           POID [0] 0.0.0.1 /account 18679 17
              0 PIN_FLD_PROGRAM_NAME    STR [0] "Customer Center"
              0 PIN_FLD_DESCR           STR [0] "[Sep 16, 2009 4:46 PM  Bill not paid]\n"
              0 PIN_FLD_STATUSES      ARRAY [0] allocated 20, used 2
              1     PIN_FLD_STATUS_FLAGS    INT [0] 4
              1     PIN_FLD_STATUS         ENUM [0] 10102
                       */ 
             
             
             try{
              FList flUpdate=new FList();
             FList Status=new FList();
             
             SparseArray Statuses=new SparseArray();
             
             flUpdate.set(FldPoid.getInst(),Account_obj);
             flUpdate.set(FldProgramName.getInst(),"CRM");
             flUpdate.set(FldDescr.getInst(),"Changing CSE Status From LSLP");
             Status.set(FldStatusFlags.getInst(),4);
             Status.set(FldStatus.getInst(),Status_flag);
             Statuses.add(1,Status);
             flUpdate.set(FldStatuses.getInst(),Statuses);
            FList flUpdateOut = ctx.opcode(62, flUpdate);
             }             
        catch (EBufException ex) {
           // ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        } 
        catch (Exception ex) {
            //ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        }
         
         } 
      return true; 
       }
     public boolean deleteSalesLocation(Connection con,String[] check) throws SQLException,Exception {
        boolean status = false;
        PreparedStatement pstmt = null;
        try{
            
            if(check != null && check.length > WitribeConstants.ZERO) {
               String[] salesIdLocation= check[0].split("#");
            if(salesIdLocation != null && salesIdLocation.length == 2) {
                pstmt = con.prepareStatement("DELETE SALES_LOCATION WHERE SALESPERSONNEL_ID = ? AND UPPER(LOCATION) = UPPER(?)");
                pstmt.setString(1,salesIdLocation[0]);
                pstmt.setString(2,salesIdLocation[1]);
                pstmt.executeUpdate();
                status = true;
             }
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
    
    public boolean CheckMappingforSalesInfo(Connection con,String[] check) throws SQLException,Exception{
        boolean status = true;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
                
        try{
            pstmt = con.prepareStatement("SELECT * FROM SALES_HIERARCHY WHERE PARENT_SALESPERSONNEL_ID =? OR CHILD_SALESPERSONNEL_ID =?");            
            pstmt.setString(1,check[0]);
            pstmt.setString(2,check[0]);
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                status = false;
                return status;            
            }
            pstmt = con.prepareStatement("SELECT * FROM SHOP_SALESID_MAPPING WHERE SALESPERSONNEL_ID=?");            
            pstmt.setString(1,check[0]);            
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                status = false;
                return status;
            }
            
            pstmt = con.prepareStatement("SELECT * FROM LEAD_DETAILS WHERE LEAD_STATUS='Assigned' AND ASSIGNED_TO=? OR ASSIGNED_TO_TL=?");            
            pstmt.setString(1,check[0]); 
            pstmt.setString(2,check[0]);
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                status = false;
                return status;
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
   public boolean removeLeadsFromCse(Connection con,String[] check, Connection brmcon) throws SQLException,Exception{
        boolean status = true;
        PreparedStatement pstmt = null;
        SalesPersonnelDAO objSalesDAO = null;
              
        try{
            /* StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");*/
        
         if(check[0] != null){
             pstmt = con.prepareStatement("UPDATE LEAD_DETAILS SET ASSIGNED_TO_TL = (SELECT PARENT_SALESPERSONNEL_ID FROM SALES_HIERARCHY WHERE TO_NUMBER(CHILD_SALESPERSONNEL_ID) = TO_NUMBER(?)) , " +
                "ASSIGNED_TO = '', LEAD_STATUS = 'Un Assigned' WHERE LEAD_ID  IN (SELECT LEAD_ID FROM LEAD_DETAILS WHERE TO_NUMBER(ASSIGNED_TO) = TO_NUMBER(?)) ");
             pstmt.setString(1,check[0]);
             pstmt.setString(2,check[0]);
             pstmt.executeUpdate();
             status = true;
                     
         }
     
        }finally{
             DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
}


 public boolean removeInventoryFromCse(Connection con,String[] check, Connection brmcon) throws SQLException,Exception{
        boolean status = true;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;   
        try{
            /* StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");*/
        
          if(check[0] != null){
             pstmt = brmcon.prepareStatement("update device_t set state_id=1 where poid_id0 in(select obj_id0 from wtb_cpe_details_t where salesid = ?) and state_id=2 ");
             pstmt.setString(1,check[0]);
             pstmt1 = brmcon.prepareStatement( "update wtb_cpe_details_t set salesid = null where salesid = ? ");
             pstmt1.setString(1,check[0]);
             pstmt.executeUpdate();
             pstmt1.executeUpdate();
             status = true;        

         }
     
        }finally{
             DBUtil.closeStatement(pstmt);
             DBUtil.closeStatement(pstmt1);
        }
        
        return status;
        
         }


public List getNextCse(Connection con,int pageNum,SalesPersonnelVO objSales) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; 
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;         
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        long totalRows;
        int startRow = 0;
        int endRow = 0;
        try{
            startRow = (pageNum*maxRowCount)+1;
            endRow = (pageNum+1)*maxRowCount+1;
            int role = Integer.parseInt(objSales.getSalestype());
            String salesId = objSales.getSalesId();
            String country = objSales.getCountry();
            String city = objSales.getCity();
            String zone = objSales.getZone();
            String queryCont = "";
                      
         if (role == Integer.parseInt(WitribeConstants.TYPE_ADMIN)) {
            queryCont = " SALESPERSONNEL_TYPE >  "+WitribeConstants.TYPE_ADMIN +" OR SALESPERSONNEL_TYPE <"+WitribeConstants.TYPE_ADMIN;
        } else if (role == Integer.parseInt(WitribeConstants.TYPE_NSM)) {
            queryCont = " (SALESPERSONNEL_TYPE > "+WitribeConstants.TYPE_NSM+" OR SALESPERSONNEL_TYPE <"+WitribeConstants.TYPE_ADMIN+") AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"')";
        }else if (role == Integer.parseInt(WitribeConstants.TYPE_RSM)) {
            queryCont = " (SALESPERSONNEL_TYPE > "+WitribeConstants.TYPE_RSM+" OR SALESPERSONNEL_TYPE <"+WitribeConstants.TYPE_ADMIN+") AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND " +
                    "UPPER(ADDR_CITY) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+")";
        }else if (role == Integer.parseInt(WitribeConstants.TYPE_TL)) {
            queryCont = " (SALESPERSONNEL_TYPE > "+WitribeConstants.TYPE_TL+" OR SALESPERSONNEL_TYPE <"+WitribeConstants.TYPE_ADMIN+")  AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND " +
                    "UPPER(ADDR_CITY) =UPPER('"+city+"')"+" AND " +
                    "UPPER(ADDR_ZONE) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesId+")";
        }
            
           pstmt = con.prepareStatement("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                    "SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE," +
                    "FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE,"+
                    "ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                    "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE,FIRST_NAME,LAST_NAME,CHANNEL_TYPE,SALESPERSON_STATUS FROM SALESPERSONNEL_DETAILS  " +
                    "WHERE SALESPERSONNEL_TYPE = ? AND "+queryCont+" AND UPPER(SALESPERSON_STATUS) not in (UPPER(?)) ORDER BY ADDR_CITY,SALESPERSONNEL_TYPE,ADDR_ZONE,ADDR_SUBZONE ) a WHERE rownum <="+endRow+") WHERE rn >="+startRow);
           
            pstmt1 = con.prepareStatement("SELECT LOCATION FROM SALES_LOCATION WHERE " +
                                "SALESPERSONNEL_ID= ? "); 
            
            pstmt.setString(1,WitribeConstants.TYPE_CSE);
            pstmt.setString(2,"Closed");
            rs = pstmt.executeQuery();
            
            totalRows = getSaleRowCount(con,queryCont);
           /* if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }*/
            
            objList = new ArrayList();
            int count = WitribeConstants.ZERO;
            while(rs.next()){
                SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
                count++;
               if(count>maxRowCount) {
                   objList.add("true");
                   break;
               }
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                //objSalesVO.setFullname(rs.getString(3));
                objSalesVO.setFullname(rs.getString(18)+" "+rs.getString(19));
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
                objSalesVO.setCreateDate(rs.getString(16));
                objSalesVO.setModifiedDate(rs.getString(17));
                objSalesVO.setFirstname(rs.getString(18));
                objSalesVO.setLastname(rs.getString(19));
                objSalesVO.setChanneltype(rs.getString(20));
                objSalesVO.setSalespersonstatus(rs.getString(21));
                StringBuffer cityString = new StringBuffer("");
                StringBuffer zoneString = new StringBuffer("");
                StringBuffer subZoneString = new StringBuffer("");
                if((WitribeConstants.TYPE_RSM).equals(objSalesVO.getSalestype())) {
                        pstmt1.setString(1,objSalesVO.getSalesId());
                        rs1 = pstmt1.executeQuery();
                        while(rs1.next()) {
                            cityString.append(rs1.getString(1));
                            cityString.append(", ");
                        }
                        String str = cityString.toString();
                        if(!str.equalsIgnoreCase(""))
                        str = str.substring(0,str.length()-2);
                        objSalesVO.setCity(str);
                } else if((WitribeConstants.TYPE_TL).equals(objSalesVO.getSalestype())) {
                        pstmt1.setString(1,objSalesVO.getSalesId());
                        rs1 = pstmt1.executeQuery();
                        while(rs1.next()) {
                            zoneString.append(rs1.getString(1));
                            zoneString.append(", ");
                        }
                        String str = zoneString.toString();
                        if(!str.equalsIgnoreCase(""))
                        str = str.substring(0,str.length()-2);
                        objSalesVO.setZone(str);
                }else if((WitribeConstants.TYPE_CSE).equals(objSalesVO.getSalestype())) {
                        pstmt1.setString(1,objSalesVO.getSalesId());
                        rs1 = pstmt1.executeQuery();
                        while(rs1.next()) {
                            subZoneString.append(rs1.getString(1));
                            subZoneString.append(", ");
                        }
                        if(subZoneString.length()>0) {
                            String str = subZoneString.toString();
                            if(!str.equalsIgnoreCase(""))
                            str = str.substring(0,str.length()-2);
                            objSalesVO.setSubzone(str);
                        }
                }
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
            DBUtil.closeResultSet(rs1);            
            DBUtil.closeStatement(pstmt1);
        }
        
        return objList;
        
    }
    public boolean CheckMappingforSalesLocation(Connection con,String[] check) throws SQLException,Exception{
        boolean status = true;
        PreparedStatement pstmt = null;
        ResultSet rs = null;                
        try{
            
            if(check != null && check[0] != null) {
            String[] salesIdLocation= check[0].split("#");
            if(salesIdLocation != null && salesIdLocation.length == 2) {
                pstmt = con.prepareStatement("SELECT * FROM SALES_HIERARCHY sh,SALESPERSONNEL_DETAILS sd " +
                        "WHERE PARENT_SALESPERSONNEL_ID = ? and sd.SALESPERSONNEL_ID = sh.CHILD_SALESPERSONNEL_ID" +
                        " and  (UPPER(sd.ADDR_CITY) = UPPER(?) OR  UPPER(sd.ADDR_ZONE) = UPPER(?))");            
                pstmt.setString(1,salesIdLocation[0]);
                pstmt.setString(2,salesIdLocation[1]);
                pstmt.setString(3,salesIdLocation[1]);
                rs = pstmt.executeQuery();
                if(rs.next())
                {
                    status = false;
                    return status;            
                }
                pstmt = con.prepareStatement("SELECT * FROM SHOP_SALESID_MAPPING sm,SHOP_DETAILS sd " +
                        "WHERE SALESPERSONNEL_ID = ? and sd.SHOP_ID = sm.SHOP_ID " +
                        "and  (UPPER(sd.ADDR_CITY) = UPPER(?) OR  UPPER(sd.ADDR_ZONE) = UPPER(?))");            
                pstmt.setString(1,salesIdLocation[0]);
                pstmt.setString(2,salesIdLocation[1]);
                pstmt.setString(3,salesIdLocation[1]);           
                rs = pstmt.executeQuery();
                if(rs.next())
                {
                    status = false;
                    return status;
                }

                pstmt = con.prepareStatement("SELECT * FROM LEAD_DETAILS WHERE ASSIGNED_TO_TL=?  " +
                        "and (UPPER(ADDR_ZONE) = UPPER(?) OR UPPER(ADDR_CITY) = UPPER(?))");            
                pstmt.setString(1,salesIdLocation[0]);
                pstmt.setString(2,salesIdLocation[1]);
                pstmt.setString(3,salesIdLocation[1]);
                rs = pstmt.executeQuery();
                if(rs.next())
                {
                    status = false;
                    return status;
                }
            }
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
    
    public List SalesforParent(Connection con, HttpServletRequest req)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SalesPersonnelVO objSalesVO = null;
        HttpSession usersession = (HttpSession)req.getSession(true);
        int role =Integer.parseInt((String)usersession.getAttribute(wc.ROLE));
        String Salesid = (String)usersession.getAttribute(wc.SALES_ID);
        String Province = (String)usersession.getAttribute(wc.ADDR_PROVINCE);
        String City = (String)usersession.getAttribute(wc.ADDR_CITY);
        String Zone = (String)usersession.getAttribute(wc.ADDR_ZONE);
        String SubZone = (String)usersession.getAttribute(wc.ADDR_SUBZONE);
        String QueryCont = "";

        if (role == Integer.parseInt(wc.TYPE_ADMIN) || role == Integer.parseInt(wc.TYPE_NSM)) {
            QueryCont = "SALESPERSONNEL_TYPE > 0 AND SALESPERSONNEL_TYPE < 4";
        }
        
        else if (role == Integer.parseInt(wc.TYPE_RSM)) {
            QueryCont = "SALESPERSONNEL_TYPE > 1 AND SALESPERSONNEL_TYPE < 4 AND UPPER(ADDR_CITY) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+Salesid+")";
        }
        
        else if (role == Integer.parseInt(wc.TYPE_TL)) {
            QueryCont = "SALESPERSONNEL_TYPE = 3 AND UPPER(ADDR_CITY) =UPPER('"+City+"')"+" AND UPPER(ADDR_ZONE) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+Salesid+")";
        }
        
        try{
            pstmt = con.prepareStatement(
                    "SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE," +
                    "FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD," +
                    "ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE," +
                    "ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                    "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE," +
                    "MODIFIED_DATE,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS WHERE "+QueryCont+" ORDER BY SALESPERSONNEL_TYPE, FIRST_NAME");
             /*SALESPERSONNEL_TYPE > ? and SALESPERSONNEL_TYPE < ?
             pstmt.setString(1,wc.TYPE_ADMIN);
             pstmt.setString(2,wc.TYPE_CSE);*/
            rs = pstmt.executeQuery();
            while(rs.next()){
                
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                //objSalesVO.setFullname(rs.getString(3));
                objSalesVO.setFullname(rs.getString(18)+" "+rs.getString(19));
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
                objSalesVO.setCreateDate(rs.getString(16));
                objSalesVO.setModifiedDate(rs.getString(17));
                objSalesVO.setFirstname(rs.getString(18));
                objSalesVO.setLastname(rs.getString(19));
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
        
        return objList;
    }
    
    public List SalesforChild(Connection con, HttpServletRequest req)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SalesPersonnelVO objSalesVO = null;
        HttpSession usersession = (HttpSession)req.getSession(true);
        int role =Integer.parseInt((String)usersession.getAttribute(wc.ROLE));
        String Province = (String)usersession.getAttribute(wc.ADDR_PROVINCE);
        String City = (String)usersession.getAttribute(wc.ADDR_CITY);
        String Zone = (String)usersession.getAttribute(wc.ADDR_ZONE);
        String SubZone = (String)usersession.getAttribute(wc.ADDR_SUBZONE);
        
        String QueryCont = "";
        if (role == Integer.parseInt(wc.TYPE_ADMIN) || role == Integer.parseInt(wc.TYPE_NSM)) {
            QueryCont = "SALESPERSONNEL_TYPE > 1";
        }
        
        else if (role == Integer.parseInt(wc.TYPE_RSM)) {
            QueryCont = "SALESPERSONNEL_TYPE > 2 AND UPPER(ADDR_PROVINCE) =UPPER('"+Province+"')";
        }
        
        else if (role == Integer.parseInt(wc.TYPE_TL)) {
            QueryCont = "SALESPERSONNEL_TYPE > 3 AND SALESPERSONNEL_TYPE < 7 AND UPPER(ADDR_PROVINCE) =UPPER('"+Province+"')"+" AND UPPER(ADDR_ZONE) =UPPER('"+Zone+"')";
        }
        
        try{
            pstmt = con.prepareStatement(
                    "SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE," +
                    "FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE," +
                    "ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                    "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS WHERE SALESPERSONNEL_ID NOT IN( SELECT CHILD_SALESPERSONNEL_ID FROM SALES_HIERARCHY) AND "+QueryCont+" ORDER BY FIRST_NAME");
            //pstmt.setString(1,wc.TYPE_NSM);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                //objSalesVO.setFullname(rs.getString(3));
                objSalesVO.setFullname(rs.getString(18)+" "+rs.getString(19));
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
                objSalesVO.setCreateDate(rs.getString(16));
                objSalesVO.setModifiedDate(rs.getString(17));
                objSalesVO.setFirstname(rs.getString(18));
                objSalesVO.setLastname(rs.getString(19));
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
        
        return objList;
    }
    public List getRSM(Connection con,SalesPersonnelVO objSalesVO)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String role =objSalesVO.getSalestype();
        String city = objSalesVO.getCity();
        String country = objSalesVO.getCountry();
        String salesId = objSalesVO.getSalesId();
        
        
        String QueryCont = "";
        if (role.equals(wc.TYPE_ADMIN)) {
            QueryCont = "SALESPERSONNEL_TYPE = 2 ";
            
        } else if (role.equals(wc.TYPE_NSM)) {
            QueryCont = "SALESPERSONNEL_TYPE = 2 AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"')";
        }else if (role.equals(wc.TYPE_RSM)) {
            QueryCont = "SALESPERSONNEL_TYPE = 2 AND SALESPERSONNEL_ID = "+salesId;
        }
        
        try{
            pstmt = con.prepareStatement(
                    "SELECT  SALESPERSONNEL_ID,FULL_NAME,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS WHERE "+QueryCont);
            //pstmt.setString(1,wc.TYPE_NSM);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                //objSalesVO.setFullname(rs.getString(2));
                objSalesVO.setFullname(rs.getString(3)+" "+rs.getString(4));
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
        
        return objList;
    }
    public List getRSMList(Connection con,SalesPersonnelVO objSalesVO)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String role =objSalesVO.getSalestype();
        String city = objSalesVO.getCity();
        String country = objSalesVO.getCountry();
        String salesId = objSalesVO.getSalesId();
        
        
        String QueryCont = "";
        if (role.equals(wc.TYPE_ADMIN)) {
            QueryCont = "";
            
        } else if (role.equals(wc.TYPE_NSM)) {
            QueryCont = " AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"')";
        }else if (role.equals(wc.TYPE_RSM)) {
            QueryCont = " AND SALESPERSONNEL_ID = "+salesId;
        }
        
        try{
            pstmt = con.prepareStatement(
                    "SELECT  SALESPERSONNEL_ID,FULL_NAME,ADDR_PLOT,ADDR_STREET, ADDR_SUBZONE, ADDR_ZONE, " +
                    "ADDR_CITY, ADDR_PROVINCE, ADDR_COUNTRY, ADDR_ZIP,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS WHERE SALESPERSONNEL_TYPE = 2"+QueryCont+" ORDER BY FIRST_NAME");
            //pstmt.setString(1,wc.TYPE_NSM);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                //objSalesVO.setFullname(rs.getString(2));
                objSalesVO.setFullname(rs.getString(11)+" "+rs.getString(12));
                objSalesVO.setPlot(rs.getString(3));
                objSalesVO.setStreet(rs.getString(4));
                objSalesVO.setSubzone(rs.getString(5));
                objSalesVO.setZone(rs.getString(6));
                objSalesVO.setCity(rs.getString(7));
                objSalesVO.setProvince(rs.getString(8));
                objSalesVO.setCountry(rs.getString(9));
                objSalesVO.setZip(rs.getString(10));
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
        
        return objList;
    }
    public List getTlList(Connection con,SalesPersonnelVO objSalesVO)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;        
        String role =objSalesVO.getSalestype();
        String city = objSalesVO.getCity();
        String country = objSalesVO.getCountry();
        String zone = objSalesVO.getZone();
        String salesId = objSalesVO.getSalesId();
        
        String QueryCont = "";
        if (role.equals(wc.TYPE_ADMIN)) {
            QueryCont = "SALESPERSONNEL_TYPE = 3 ";
            
        } else if (role.equals(wc.TYPE_NSM)) {
            QueryCont = "SALESPERSONNEL_TYPE = 3 AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"')";
        } else if (role.equals(wc.TYPE_RSM)) {
            QueryCont = "SALESPERSONNEL_TYPE = 3 AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND UPPER(ADDR_CITY) IN " +
                    "(SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID ="+salesId+"  )";
        }else if (role.equals(wc.TYPE_TL)) {
            QueryCont = "SALESPERSONNEL_TYPE = 3 AND SALESPERSONNEL_ID = "+salesId;
        }
        try{
            pstmt = con.prepareStatement(
                    "SELECT  SALESPERSONNEL_ID,FULL_NAME,ADDR_PLOT,ADDR_STREET, ADDR_SUBZONE, ADDR_ZONE, " +
                    "ADDR_CITY, ADDR_PROVINCE, ADDR_COUNTRY, ADDR_ZIP,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS WHERE "+QueryCont+" ORDER BY FIRST_NAME");
            //pstmt.setString(1,wc.TYPE_NSM);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                //objSalesVO.setFullname(rs.getString(2));
                objSalesVO.setFullname(rs.getString(11)+" "+rs.getString(12));
                objSalesVO.setPlot(rs.getString(3));
                objSalesVO.setStreet(rs.getString(4));
                objSalesVO.setSubzone(rs.getString(5));
                objSalesVO.setZone(rs.getString(6));
                objSalesVO.setCity(rs.getString(7));
                objSalesVO.setProvince(rs.getString(8));
                objSalesVO.setCountry(rs.getString(9));
                objSalesVO.setZip(rs.getString(10));
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
        
        return objList;
    }
    public List getCseList(Connection con,SalesPersonnelVO objSalesVO)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;        
        String role =objSalesVO.getSalestype();
        String city = objSalesVO.getCity();
        String country = objSalesVO.getCountry();
        String zone = objSalesVO.getZone();
        String salesId = objSalesVO.getSalesId();
        
        String QueryCont = "";
        if (role.equals(wc.TYPE_ADMIN)) {
            QueryCont = "SALESPERSONNEL_TYPE = 4 ";
            
        } else if (role.equals(wc.TYPE_NSM)) {
            QueryCont = "SALESPERSONNEL_TYPE = 4 AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"')";
        } else if (role.equals(wc.TYPE_RSM)) {
            QueryCont = "SALESPERSONNEL_TYPE = 4 AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND UPPER(ADDR_CITY) IN " +
                    "(SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID ="+salesId+"  )";
        }else if (role.equals(wc.TYPE_TL)) {
            QueryCont = "SALESPERSONNEL_TYPE = 4 AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND UPPER(ADDR_CITY) =UPPER('"+city+"')" +
                    " AND UPPER(ADDR_ZONE) IN (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID ="+salesId+"  )";
        }
        try{
            pstmt = con.prepareStatement(
                    "SELECT  SALESPERSONNEL_ID,FULL_NAME,ADDR_PLOT,ADDR_STREET, ADDR_SUBZONE, ADDR_ZONE, " +
                    "ADDR_CITY, ADDR_PROVINCE, ADDR_COUNTRY, ADDR_ZIP,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS WHERE  "+QueryCont+" AND UPPER(SALESPERSON_STATUS) = UPPER('Active')  ORDER BY FIRST_NAME");
            //pstmt.setString(1,wc.TYPE_NSM);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                //objSalesVO.setFullname(rs.getString(2));
                objSalesVO.setFullname(rs.getString(11)+" "+rs.getString(12));
                objSalesVO.setPlot(rs.getString(3));
                objSalesVO.setStreet(rs.getString(4));
                objSalesVO.setSubzone(rs.getString(5));
                objSalesVO.setZone(rs.getString(6));
                objSalesVO.setCity(rs.getString(7));
                objSalesVO.setProvince(rs.getString(8));
                objSalesVO.setCountry(rs.getString(9));
                objSalesVO.setZip(rs.getString(10));
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
        
        return objList;
    }
    public List getTL(Connection con,SalesPersonnelVO objSalesVO)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;        
        String role =objSalesVO.getSalestype();
        String city = objSalesVO.getCity();
        String country = objSalesVO.getCountry();
        String zone = objSalesVO.getZone();
        String salesId = objSalesVO.getSalesId();
        
        String QueryCont = "";
        if (role.equals(wc.TYPE_ADMIN)) {
            QueryCont = "SALESPERSONNEL_TYPE = 3 ";
            
        } else if (role.equals(wc.TYPE_NSM)) {
            QueryCont = "SALESPERSONNEL_TYPE = 3 AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"')";
        } else if (role.equals(wc.TYPE_RSM)) {
            QueryCont = "SALESPERSONNEL_TYPE = 3 AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND UPPER(ADDR_CITY) IN " +
                    "(SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID ="+salesId+"  )";
        }else if (role.equals(wc.TYPE_TL)) {
            QueryCont = "SALESPERSONNEL_TYPE = 3  AND SALESPERSONNEL_ID = "+salesId ;
        }
        try{
            pstmt = con.prepareStatement(
                    "SELECT  SALESPERSONNEL_ID,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS WHERE "+QueryCont);
            //pstmt.setString(1,wc.TYPE_NSM);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                //objSalesVO.setFullname(rs.getString(2));
                objSalesVO.setFullname(rs.getString(2)+" "+rs.getString(3));
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
        
        return objList;
    }
    public List getCSE(Connection con,ChannelVO objChannelVO,String salesId)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;        
        String role =objChannelVO.getSalestype();
        String city = objChannelVO.getCity();
        String country = objChannelVO.getCountry();
        String zone = objChannelVO.getZone();
        //String salesId = objSalesVO.getSalesId();
         String QueryCont = "";
         if (role.equals(wc.TYPE_TL)) {
           // QueryCont = "SALESPERSONNEL_TYPE = 3 AND SALESPERSONNEL_ID = "+salesId;
          
        try{
            pstmt = con.prepareStatement(
                    "select sh.CHILD_SALESPERSONNEL_ID,sd.SALESPERSONNEL_TYPE,sd.CHANNEL_TYPE,sd.FIRST_NAME,sd.LAST_NAME "+
                    "from SALES_HIERARCHY sh,SALESPERSONNEL_DETAILS sd "+
                    "where sh.CHILD_SALESPERSONNEL_ID = sd.SALESPERSONNEL_ID "+
                    "AND sh.PARENT_SALESPERSONNEL_ID =?  AND sd.SALESPERSON_STATUS = 'Active' AND sd.SALESPERSONNEL_TYPE in (?) order by sd.first_name");
            //pstmt.setString(1,wc.TYPE_NSM);
            
            pstmt.setString(1,salesId);
            pstmt.setString(2,WitribeConstants.TYPE_CSE);
            rs = pstmt.executeQuery();
            while(rs.next()){
                
                SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                objSalesVO.setChanneltype(rs.getString(3));
                //objSalesVO.setFullname(rs.getString(4));           
                objSalesVO.setFullname(rs.getString(4)+" "+rs.getString(5));
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
         return objList;
        }  else if (role.equals(wc.TYPE_ADMIN)) {
            QueryCont = "SALESPERSONNEL_TYPE in (?) ";          
        } else if (role.equals(wc.TYPE_NSM)) {
            QueryCont = "SALESPERSONNEL_TYPE in (?) AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"')";
              
        } else if (role.equals(wc.TYPE_RSM)) {
            QueryCont = "SALESPERSONNEL_TYPE in (?) AND UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND UPPER(ADDR_CITY) = UPPER('"+city+"')";
        } 
            try{
              pstmt = con.prepareStatement(
                    "select SALESPERSONNEL_ID,SALESPERSONNEL_TYPE,CHANNEL_TYPE,FIRST_NAME,LAST_NAME "+
                    "from SALESPERSONNEL_DETAILS where "+QueryCont +"ORDER BY FIRST_NAME");
              pstmt.setString(1,WitribeConstants.TYPE_CSE);
              rs = pstmt.executeQuery();
              while(rs.next()){
                SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                objSalesVO.setChanneltype(rs.getString(3));
                //objSalesVO.setFullname(rs.getString(4));
                objSalesVO.setFullname(rs.getString(4)+" "+rs.getString(5));
                objList.add(objSalesVO);
            }
            }catch(SQLException se){
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
    
     public List getChannelList(Connection con,ChannelVO objChannelVO,String salesId)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;        
        String role =objChannelVO.getSalestype();
        String city = objChannelVO.getCity();
        String country = objChannelVO.getCountry();
        String zone = objChannelVO.getZone();
        //String salesId = objSalesVO.getSalesId();
                  
        try{
            pstmt = con.prepareStatement(
                    "select SALESPERSONNEL_ID,SALESPERSONNEL_TYPE,CHANNEL_TYPE,FIRST_NAME,LAST_NAME "+
                    "from SALESPERSONNEL_DETAILS "+
                    "where SALESPERSONNEL_ID =?  AND SALESPERSONNEL_TYPE in (?) ORDER BY FIRST_NAME");
            //pstmt.setString(1,wc.TYPE_NSM);
            
            pstmt.setString(1,salesId);
            pstmt.setString(2,WitribeConstants.TYPE_CSE);
            rs = pstmt.executeQuery();
            while(rs.next()){
                
                SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                objSalesVO.setChanneltype(rs.getString(3));
                //objSalesVO.setFullname(rs.getString(4));
                objSalesVO.setFullname(rs.getString(4)+" "+rs.getString(5));                
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
         return objList;
        
    }
     
      public boolean modifyChannelType(Connection con,ChannelVO objChannelVO)throws SQLException, Exception{
         boolean status = false;
        PreparedStatement pstmt = null;
        try{
            
                pstmt = con.prepareStatement(
                        "UPDATE SALESPERSONNEL_DETAILS set " +
                        " CHANNEL_TYPE = ? "+
                        " where SALESPERSONNEL_ID = ?");
                
                pstmt.setString(1,objChannelVO.getChanneltype());
                pstmt.setString(2, objChannelVO.getSalesIdChannel());
                
           if(pstmt.executeUpdate() > WitribeConstants.ZERO){
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
    public List getUnAssignedCities(Connection con)throws SQLException, Exception{
        List objCityList = null;
        objCityList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            /* pstmt = con.prepareStatement(
                   "Select ADDR_CITY from CITY where  ADDR_CITY not in ("+
                   "Select distinct ADDR_CITY from SALESPERSONNEL_DETAILS "+
                   " Where SALESPERSONNEL_TYPE = 2)");
             //pstmt.setString(1,wc.TYPE_NSM);*/
            pstmt = con.prepareStatement("SELECT ADDR_CITY from CITY where  UPPER(ADDR_CITY) not in ("+
                    " SELECT UPPER(LOCATION) from SALES_LOCATION WHERE SALESPERSONNEL_ID IN"+
                    " (SELECT SALESPERSONNEL_ID from SALESPERSONNEL_DETAILS"+
                    " Where SALESPERSONNEL_TYPE = 2))");
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                
                CityVO objCityVO= new CityVO();
                objCityVO.setAddr_city(rs.getString(1));
                objCityList.add(objCityVO);
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
        
        return objCityList;
    }
    public boolean addLocation(Connection con,SalesPersonnelVO objSalesVO)throws SQLException, Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        
        try{
            pstmt = con.prepareStatement("INSERT INTO SALES_LOCATION (SALESPERSONNEL_ID,LOCATION,CREATE_DATE)" +
                    " VALUES  (?,?,?)");
            
            pstmt.setString(1, objSalesVO.getSalesId());
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(3, sqlDate);
            String []saleslocation = objSalesVO.getSalesLocation();
            for(int i=0;i<saleslocation.length-1;i++){
                if(saleslocation[saleslocation.length-1]!="");
                {
                    pstmt.setString(2, saleslocation[i]);
                    pstmt.addBatch();
                }
            }
            if(saleslocation[saleslocation.length-1]!="");
            {
                pstmt.setString(2, saleslocation[saleslocation.length-1]);
                pstmt.addBatch();
                int[] batch=pstmt.executeBatch();
                if(batch.length == saleslocation.length){
                      status = true;
                    }
            }
           /* if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
            }*/
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
   /*public boolean addRsmCity(Connection con,SalesPersonnelVO objSalesVO)throws SQLException, Exception{
       boolean status = false;
       PreparedStatement pstmt = null;
    
       try{
           String city = objSalesVO.getCity();
           objSalesVO = getSale(objSalesVO.getSalesId(),con);
           objSalesVO.setCity(city);
           createSale(objSalesVO,con);
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
   public boolean addTlZone(Connection con,SalesPersonnelVO objSalesVO)throws SQLException, Exception{
       boolean status = false;
       PreparedStatement pstmt = null;
    
       try{
           String zone = objSalesVO.getZone();
           objSalesVO = getSale(objSalesVO.getSalesId(),con);
           objSalesVO.setZone(zone);
           if(!checkDuplicateZone(con, objSalesVO)) {
            status = createSale(objSalesVO,con);
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
   }*/
   public List shopsForSalesId(Connection con,ShopsVO objShopVO)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SalesPersonnelVO objSalesVO = null;
       
        try{
            pstmt = con.prepareStatement(
                    "SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE," +
                    "FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE," +
                    "ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                    "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE,FIRST_NAME,LAST_NAME from SALESPERSONNEL_DETAILS sd,SHOP_DETAILS shd "+
                    "where  sd.SALESPERSONNEL_TYPE =? ");
                    
	
            pstmt.setString(1,wc.TYPE_TL); 
            
            rs = pstmt.executeQuery();
            while(rs.next()){
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                //objSalesVO.setFullname(rs.getString(3));
                objSalesVO.setFullname(rs.getString(3)+" "+rs.getString(4));
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
                objSalesVO.setCreateDate(rs.getString(16));
                objSalesVO.setModifiedDate(rs.getString(17));
                objSalesVO.setFirstname(rs.getString(3));
                objSalesVO.setLastname(rs.getString(4));
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
        
        return objList;
    }
    
   public List SalesforChildbyLead(Connection con, String salesid, String salestype)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SalesPersonnelVO objSalesVO = null;
        WitribeBO wbo = new WitribeBO();
        
       /*HttpSession usersession = (HttpSession)req.getSession(true);
       int role =Integer.parseInt((String)usersession.getAttribute(wc.ROLE));
       String Province = (String)usersession.getAttribute(wc.ADDR_PROVINCE);
       String City = (String)usersession.getAttribute(wc.ADDR_CITY);
       String Zone = (String)usersession.getAttribute(wc.ADDR_ZONE);
       String SubZone = (String)usersession.getAttribute(wc.ADDR_SUBZONE);*/
        
        String Query= "";
        //System.out.println(salesid);
        //System.out.println(salestype);
        if (salestype.equalsIgnoreCase(wc.SALES_DIRECTOR)) {
            //   QueryCont = "SALESPERSONNEL_TYPE > 1";
            int childsalestype = 2;
            Query = " SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE, FULL_NAME,CONTACT_NUMBER,EMAIL,USERID," +
                    " PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE, ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE, ADDR_COUNTRY," +
                    " ADDR_ZIP,CREATED_DATE,MODIFIED_DATE,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS WHERE SALESPERSONNEL_TYPE =" +childsalestype+
                    "  AND SALESPERSONNEL_ID NOT IN( SELECT CHILD_SALESPERSONNEL_ID FROM SALES_HIERARCHY) " +
                    " AND UPPER(ADDR_COUNTRY) IN (SELECT UPPER(ADDR_COUNTRY) FROM SALESPERSONNEL_DETAILS WHERE SALESPERSONNEL_ID ="+salesid+")";
            
        }
        
        else if (salestype.equalsIgnoreCase(wc.RSM)) {
            //      QueryCont = " > 2 AND UPPER(ADDR_PROVINCE) =UPPER('"+Province+"')";
            int childsalestype = 3;
            Query = "SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE, FULL_NAME,CONTACT_NUMBER,EMAIL,USERID," +
                    "PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE, ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE, ADDR_COUNTRY," +
                    "ADDR_ZIP,CREATED_DATE,MODIFIED_DATE,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS WHERE SALESPERSONNEL_TYPE =" +childsalestype+
                    " AND SALESPERSONNEL_ID NOT IN( SELECT CHILD_SALESPERSONNEL_ID FROM SALES_HIERARCHY) " +
                    "AND UPPER(ADDR_CITY) IN (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID ="+salesid+")";
        }
        
        else if (salestype.equalsIgnoreCase(wc.TL)) {
            int childsalestype = 4;
            Query = "SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE, FULL_NAME,CONTACT_NUMBER,EMAIL,USERID," +
                    " PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE, ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE, ADDR_COUNTRY," +
                    " ADDR_ZIP,CREATED_DATE,MODIFIED_DATE,FIRST_NAME,LAST_NAME FROM SALESPERSONNEL_DETAILS WHERE SALESPERSONNEL_TYPE >=" +childsalestype+
                    " AND SALESPERSONNEL_ID NOT IN( SELECT CHILD_SALESPERSONNEL_ID FROM SALES_HIERARCHY) " +
                    " AND  UPPER(SALESPERSON_STATUS)= UPPER('Active') AND UPPER(ADDR_CITY) = (SELECT UPPER(ADDR_CITY) FROM SALESPERSONNEL_DETAILS WHERE SALESPERSONNEL_ID ="+salesid+") " +
                    " AND UPPER(ADDR_ZONE) IN (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID = "+salesid+")";
            //    QueryCont = "SALESPERSONNEL_TYPE > 3 AND SALESPERSONNEL_TYPE < 7 AND UPPER(ADDR_PROVINCE) =UPPER('"+Province+"')"+" AND UPPER(ADDR_ZONE) =UPPER('"+Zone+"')";
        }
        
        try{
            pstmt = con.prepareStatement(Query);
            //pstmt.setString(1,wc.TYPE_NSM);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                //objSalesVO.setFullname(rs.getString(3));
                objSalesVO.setFullname(rs.getString(18)+" "+rs.getString(19));
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
                objSalesVO.setCreateDate(rs.getString(16));
                objSalesVO.setModifiedDate(rs.getString(17));
                objSalesVO.setFirstname(rs.getString(18));
                objSalesVO.setLastname(rs.getString(19));
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
        
        return objList;
    }
    public SalesPersonnelVO validateUser(SalesPersonnelVO objSalesVO,Connection con) throws SQLException,Exception{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select USERID,PASSWORD,SALESPERSONNEL_TYPE," +
                    "ADDR_PLOT, ADDR_STREET, ADDR_SUBZONE, ADDR_ZONE, ADDR_CITY, " +
                    "ADDR_PROVINCE, ADDR_COUNTRY, ADDR_ZIP,SALESPERSONNEL_ID,FIRST_NAME,LAST_NAME,CHANNEL_TYPE " +
                    "from SALESPERSONNEL_DETAILS where USERID=? and PASSWORD=? and UPPER(SALESPERSON_STATUS) = UPPER('Active')");
            pstmt.setString(1,objSalesVO.getUserId());
            pstmt.setString(2,objSalesVO.getPassword());
            
            rs = pstmt.executeQuery();
            if(rs.next()) {
               objSalesVO.setSalestype(rs.getString(3));
               objSalesVO.setPlot(rs.getString(4));
               objSalesVO.setStreet(rs.getString(5));
               objSalesVO.setSubzone(rs.getString(6));
               objSalesVO.setZone(rs.getString(7));
               objSalesVO.setCity(rs.getString(8));
               objSalesVO.setProvince(rs.getString(9));
               objSalesVO.setCountry(rs.getString(10));
               objSalesVO.setZip(rs.getString(11));
               objSalesVO.setSalesId(rs.getString(12));
               //objSalesVO.setFullname(rs.getString(13));
               objSalesVO.setFullname(rs.getString(13)+" "+rs.getString(14));
               objSalesVO.setChanneltype(rs.getString(15));
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
        return  objSalesVO;
    }
    public SalesPersonnelVO validateForgotpwd(SalesPersonnelVO objSalesVO,Connection con) throws SQLException,Exception{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select USERID,PASSWORD,SALESPERSONNEL_TYPE," +
                    "ADDR_PLOT, ADDR_STREET, ADDR_SUBZONE, ADDR_ZONE, ADDR_CITY, " +
                    "ADDR_PROVINCE, ADDR_COUNTRY, ADDR_ZIP,SALESPERSONNEL_ID,FULL_NAME,EMAIL " +
                    "from SALESPERSONNEL_DETAILS where USERID=?");
            pstmt.setString(1,objSalesVO.getUserId());
            //pstmt.setString(2,objSalesVO.getPassword());
            rs = pstmt.executeQuery();
            if(rs.next()) {
               objSalesVO.setPassword(rs.getString(2));
               objSalesVO.setSalestype(rs.getString(3));
               objSalesVO.setPlot(rs.getString(4));
               objSalesVO.setStreet(rs.getString(5));
               objSalesVO.setSubzone(rs.getString(6));
               objSalesVO.setZone(rs.getString(7));
               objSalesVO.setCity(rs.getString(8));
               objSalesVO.setProvince(rs.getString(9));
               objSalesVO.setCountry(rs.getString(10));
               objSalesVO.setZip(rs.getString(11));
               objSalesVO.setSalesId(rs.getString(12));
               objSalesVO.setFullname(rs.getString(13));
               objSalesVO.setEmail(rs.getString(14));
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
        return  objSalesVO;
    }
    public boolean isNsmAvailable(Connection con) throws SQLException, Exception{
        boolean isNsmAvailable = false;
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select * from SALESPERSONNEL_DETAILS where SALESPERSONNEL_TYPE  = ?");
            pstmt.setString(1,wc.TYPE_NSM);
            rs = pstmt.executeQuery();
            if(rs.next()){
                isNsmAvailable = true;
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
           
        return isNsmAvailable;
    }
    
    public boolean updateShopforSales(String salesId, String shopId) {
        Integer result = 0;
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("Infranet.properties"));
            PortalContext ctx = new PortalContext(properties);
            ctx.connect();
            FList inFlist = new FList();
            inFlist.set(FldPoid.getInst(),new Poid(1, -1, "/account"));
            inFlist.set(FldAccessCode1.getInst(),salesId);
            inFlist.set(FldAacSource.getInst(),shopId);
            FList out = new FList();
            out = ctx.opcode(100059, inFlist);
            result = out.get(FldProcessedFlag.getInst());
        } catch (EBufException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        
        if(result.intValue()== 1)
            return true;
        else
            return false;
    }
    //Production Support: This method is added to update CSE,CSR profile with encrypted password added on 30th july 09
     public boolean updateSalesinfoOnBRM(SalesPersonnelVO objSalesVO) {
        Integer result = 0;
       // String[] subzone = null;
        try {
          /*  if(objSalesVO.getSubzone() != null){
               subzone = objSalesVO.getSubzone().split(",");
               
            }*/
            
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("Infranet.properties"));
            PortalContext ctx = new PortalContext(properties);
            ctx.connect();
            FList inFlist = new FList();
            inFlist.set(FldPoid.getInst(),new Poid(1, -1, "/account"));
            inFlist.set(FldAccessCode1.getInst(),objSalesVO.getSalesId());
            inFlist.set(FldAddress.getInst(),objSalesVO.getPlot()+"   "+objSalesVO.getStreet()+"   "+objSalesVO.getSubzone()+"   "+objSalesVO.getZone());
            inFlist.set(FldCity.getInst(),objSalesVO.getCity());
            inFlist.set(FldEmailAddr.getInst(),objSalesVO.getEmail());
            inFlist.set(FldFirstName.getInst(),objSalesVO.getFirstname());
            inFlist.set(FldLastName.getInst(),objSalesVO.getLastname());
            inFlist.set(FldState.getInst(),objSalesVO.getProvince());
            inFlist.set(FldZip.getInst(),(objSalesVO.getZip() != null? objSalesVO.getZip():""));
            inFlist.set(FldPhone.getInst(),objSalesVO.getContactnumber());
            inFlist.set(FldPasswd.getInst(),objSalesVO.getPassword());
            FList out = new FList();
            out = ctx.opcode(100059, inFlist);
            result = out.get(FldProcessedFlag.getInst());
            if(result.intValue()== 1) {
                return true;
            } else {
                return false;
            }
        } catch (EBufException ex) {
           // ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        } catch (IOException ex) {
           // ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        }catch (Exception ex) {
            //ex.printStackTrace();
            LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
            return false;
        }
        
        
    }
    
     public List getUnAssignedLocationList(Connection con, SalesPersonnelVO objSalesVo)throws SQLException,Exception{
        List objList = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
           // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            
            if(objSalesVo!= null) {
                String salesType = objSalesVo.getSalestype();
                if(salesType!= null || "".equals(salesType)) {
                    if(salesType.equals(WitribeConstants.TYPE_CSE)) {
                        /*pstmt =  con.prepareStatement("SELECT SUB_ADDRESS FROM ADDRESS_MAPPING   WHERE TYPE = "+WitribeConstants.TYPE_CSE+
                                "  AND ADDRESS = ?  AND SUB_ADDRESS NOT IN (SELECT LOCATION  FROM SALES_LOCATION WHERE " +
                                "SALESPERSONNEL_ID in (SELECT SALESPERSONNEL_ID from SALESPERSONNEL_DETAILS WHERE " +
                                "SALESPERSONNEL_TYPE="+WitribeConstants.TYPE_CSE+" AND UPPER(ADDR_CITY)=upper(?) and UPPER(ADDR_ZONE)=upper(?)))");*/
                        pstmt =  con.prepareStatement("SELECT SUB_ADDRESS FROM ADDRESS_MAPPING   WHERE TYPE = "+WitribeConstants.TYPE_CSE+
                                "  AND UPPER(ADDRESS) = UPPER(?) and sub_address not in(select location from sales_location where salespersonnel_id=?)");
                        pstmt.setString(1,objSalesVo.getZone());
                        pstmt.setString(2,objSalesVo.getSalesId());
                        /*pstmt.setString(2,objSalesVo.getCity());
                        pstmt.setString(3,objSalesVo.getZone());*/
                        rs = pstmt.executeQuery();
                    } 
                    if(salesType.equals(WitribeConstants.TYPE_TL)) {
                        pstmt =  con.prepareStatement("SELECT SUB_ADDRESS FROM ADDRESS_MAPPING   WHERE TYPE = "+WitribeConstants.TYPE_TL+
                                " AND  UPPER(ADDRESS)=UPPER(?) AND SUB_ADDRESS NOT IN (SELECT LOCATION  FROM SALES_LOCATION WHERE SALESPERSONNEL_ID IN "+
                                "(SELECT SALESPERSONNEL_ID FROM SALESPERSONNEL_DETAILS WHERE SALESPERSONNEL_TYPE="+WitribeConstants.TYPE_TL+" AND UPPER(ADDR_CITY)=UPPER(?)))");
                        pstmt.setString(1,objSalesVo.getCity());
                        pstmt.setString(2,objSalesVo.getCity());
                        rs = pstmt.executeQuery();
                    }
                    if(salesType.equals(WitribeConstants.TYPE_RSM)) {
                        pstmt =  con.prepareStatement("SELECT SUB_ADDRESS FROM ADDRESS_MAPPING   WHERE TYPE = " +WitribeConstants.TYPE_RSM+
                                " AND SUB_ADDRESS NOT IN (SELECT LOCATION  FROM SALES_LOCATION WHERE SALESPERSONNEL_ID IN " +
                                "(SELECT SALESPERSONNEL_ID FROM SALESPERSONNEL_DETAILS WHERE SALESPERSONNEL_TYPE=2))");
                       rs = pstmt.executeQuery();
                    }
                }   
            }
            if(rs!= null){              
                while(rs.next()) {  
                    objList.add(rs.getString(1));
                }
            }
        }catch(SQLException se){
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
     
    public SalesPersonnelVO setUserNamePwdAddress(SalesPersonnelVO objSalesVO,boolean pwdChange,Connection con) throws SQLException,Exception{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select USERID,PASSWORD,ADDR_SUBZONE,ADDR_ZONE " +
                    "from SALESPERSONNEL_DETAILS where SALESPERSONNEL_ID=?");
            pstmt.setString(1,objSalesVO.getSalesId());
            rs = pstmt.executeQuery();
            if(rs.next()) {
               if(!pwdChange) {
                   objSalesVO.setUserId(rs.getString(1));
                   objSalesVO.setPassword(rs.getString(2)); 
               }
               objSalesVO.setSubzone(rs.getString(3));
               objSalesVO.setZone(rs.getString(4));
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
        return  objSalesVO;
    }

   public long getMaxChnKey(Connection con) throws SQLException,Exception {
     PreparedStatement pstmt = null;
        ResultSet rs = null;
        long maxKey = 0;
        try{
            pstmt = con.prepareStatement("select max(CHANNEL_ID) from MST_CHANNEL");
            rs = pstmt.executeQuery();
            if(rs.next()){
                maxKey = rs.getInt(1);
            }
            
        }catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return maxKey;
   }
     public long getMaxChnMapKey(Connection con) throws SQLException,Exception {
     PreparedStatement pstmt = null;
        ResultSet rs = null;
        long maxKey = 0;
        try{
            pstmt = con.prepareStatement("select max(mapping_id) from MST_CHANNEL_SALESTYPE_MAPPING");
            rs = pstmt.executeQuery();
            if(rs.next()){
                maxKey = rs.getInt(1);
            }
            
        }catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return maxKey;
   }
   
  public boolean addChannelList(Connection con, ChannelVO objChnVo)throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        con.setAutoCommit(false);
        String channelName = objChnVo.getChannelName();
        String chnId = objChnVo.getChannelId();
        try{
           // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            long maxkey = getMaxChnKey(con);
            long maxMapkey=getMaxChnMapKey(con);
            maxkey = maxkey+1;
            maxMapkey=maxMapkey+1;
            if("-1".equals(chnId)) {
                pstmt = con.prepareStatement("insert into MST_CHANNEL (CHANNEL_ID,CHANNEL_NAME) values (?,?)");
                pstmt.setLong(1,maxkey);
                pstmt.setString(2,channelName);
                if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
                }
                if(status==true)
                {
                   pstmt = con.prepareStatement("insert into MST_CHANNEL_SALESTYPE_MAPPING(MAPPING_ID,SalesType_Id,channelType_id)values(?,4,?)"); 
                   pstmt.setLong(1,maxMapkey);
                   pstmt.setLong(2,maxkey);    
                    if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                    status = true;
                    }                   
                }    
            } else {
                 pstmt = con.prepareStatement("update MST_CHANNEL set CHANNEL_NAME = ? where CHANNEL_ID = ?");
                 pstmt.setString(1,channelName);
                 pstmt.setString(2,chnId);
                 if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
                }
               con.commit();  
            }
        }catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
    }
  public List getNewChannelList(Connection con, ChannelVO objChnVo)throws SQLException,Exception{
        List chnList = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
           // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            long maxkey = getMaxChnKey(con);
            //if(objChnVo!= null) {
                //String channelType = objChnVo.getChanneltype();
                pstmt = con.prepareStatement("select CHANNEL_ID,CHANNEL_NAME from  MST_CHANNEL order by CHANNEL_ID");
                rs = pstmt.executeQuery();
                while(rs.next()){
                   ChannelVO ChnVo = new ChannelVO();
                   ChnVo.setChannelId(rs.getString(1));
                   ChnVo.setChannelName(rs.getString(2));
                   chnList.add(ChnVo);
                }
                
           // }
        }catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return chnList; 
  }
  
  //Code Added by Muralidhar
  public List getCityList(Connection con)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        PreparedStatement pstmt = null;
        SalesPersonnelVO objSalesVO = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select address,sub_address from ADDRESS_MAPPING where type='2'");
           // pstmt = con.prepareStatement("select distinct(CITY) from  CITY_ZONE_TBL where Zone='All'");
            rs = pstmt.executeQuery();
            while(rs.next()){
                objSalesVO = new SalesPersonnelVO();
                objSalesVO.setProvince(rs.getString(1));//provance
                objSalesVO.setCity(rs.getString(2));//city                
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
        
        return objList;
    }
  
}
