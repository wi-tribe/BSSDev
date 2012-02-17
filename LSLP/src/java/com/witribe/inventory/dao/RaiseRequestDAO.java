/*
 * RaiseRequestDAO.java
 *
 * Created on February 18, 2009, 4:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.dao;

import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.dao.LeadDAO;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.DistributeInventoryVO;
import com.witribe.inventory.vo.RaiseRequestVO;
import com.witribe.inventory.vo.VoucherVO;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.sales.vo.ShopsVO;
import com.witribe.util.DBUtil;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author SC43278
 */

public class RaiseRequestDAO {
    static boolean csetransfer;
    /** Creates a new instance of RaiseRequestDAO */
    public RaiseRequestDAO() {
    }
    public long getMaxKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT req_id.nextval from dual");
            if(rs.next()) {
                maxKey = rs.getLong(1);
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
        return  maxKey;
    }
    
    
    public long getMaxInvReturnKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT MAX(REQUESTID) from WTB_BRM_ERP_REQUEST_2");
            if(rs.next()) {
                maxKey = rs.getLong(1);
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
        return  maxKey;
    }
    public long getMaxInvTransKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT MAX(REQUESTID) from WTB_BRM_ERP_REQUEST_3");
            if(rs.next()) {
                maxKey = rs.getLong(1);
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
        return  maxKey;
    }
    public boolean createRequest(RaiseRequestVO objRequestVO,Connection con,String salesid,Connection crmcon) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        String shopname = fetchShopName(crmcon,objRequestVO.getShopId());
        String SalesPersonName = fetchName(crmcon,salesid);
        try{
            long key = getMaxKey(con);
            pstmt = con.prepareStatement(
                    "INSERT INTO WTB_BRM_ERP_REQUEST_1 (REQUESTID,REQUESTDATE,SALES_ID,SHOPID," +
                    "INVENTORY_TYPE,INVENTORY_SUBTYPE,MAKE,MODEL," +
                    "QUANTITY_ORDERED,REQUIREDBYDATE,POID_DB,POID_ID0,POID_TYPE,POID_REV,PROCESS_STATUS)" +
                    "VALUES " +
                    "(?,to_char(sysdate,'dd-Mon-yyyy HH24:MI:SS'),?,?,?,?,?,?,?,to_char(to_date(?,'dd-mm-yyyy'),'dd-Mon-yyyy HH24:MI:SS'),1,?,'/wtb_brm_erp_request_1',0,1)");
            
            pstmt.setLong(1, key);
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            //pstmt.setTimestamp(2, sqlDate);
            pstmt.setString(2,salesid);
            pstmt.setString(3,objRequestVO.getShopId());
            pstmt.setString(4,objRequestVO.getInventorytype());
            /*if(objRequestVO.getSubtype()==null || objRequestVO.getSubtypenew() != null){
                pstmt.setString(5,objRequestVO.getSubtypenew());
           } else {*/
               pstmt.setString(5,objRequestVO.getSubtype());
            /*}
                if(objRequestVO.getMake() == null || objRequestVO.getMakenew() != null ){
                   pstmt.setString(6,objRequestVO.getMakenew());
                } else {*/
                    pstmt.setString(6,objRequestVO.getMake());
               /* }
           // pstmt.setString(6,objRequestVO.getMake());
            if(objRequestVO.getModel()==null || objRequestVO.getModelnew() != null){
                pstmt.setString(7,objRequestVO.getModelnew());
            } else {*/
                pstmt.setString(7,objRequestVO.getModel());
            //}
            pstmt.setInt(8,Integer.parseInt(objRequestVO.getNumberofdevices()));
            pstmt.setString(9,objRequestVO.getReqbydate());
            pstmt.setLong(10, key);
            
            if(pstmt.executeUpdate()>0){
                status = true;
                WitribeInventoryBO wbo= new WitribeInventoryBO();
                String mailcontent = WitribeConstants.MAIL_BODY_REQ1;
                String msgContent = WitribeConstants.SMS_BODY_REQ1;
                msgContent = msgContent+"Request ID: "+key;
                msgContent = msgContent+" Request Raised On: "+sqlDate;
                msgContent = msgContent+" Please check the mail for more Details";
                mailcontent = mailcontent+"\nRequest ID			:"+ key;
                mailcontent = mailcontent+"\nRequest Raised On		:"+sqlDate;
                mailcontent = mailcontent+"\nRequest Raised By Team Lead	:"+ salesid+"-"+SalesPersonName;
                mailcontent = mailcontent+"\nRequest Raised By Shop		:" +objRequestVO.getShopId();
                mailcontent = mailcontent+"\nRequest Raised By Shop Name		:" +shopname;
                mailcontent = mailcontent+"\nInventory Type			:" +objRequestVO.getInventorytype();
                //if(objRequestVO.getInventorytype().equals(WitribeConstants.CPE)){
                    
                   /* if(objRequestVO.getSubtype() == null || objRequestVO.getSubtypenew() != null){
                    mailcontent = mailcontent+"\nInventory Sub Type		:" +objRequestVO.getSubtypenew();
                    } else {*/
                    mailcontent = mailcontent+"\nInventory Sub Type		:" +objRequestVO.getSubtype();
                    /*}
                    if(objRequestVO.getModel() == null || objRequestVO.getModelnew() != null){
                    mailcontent = mailcontent+"\nInventory Model			:"+objRequestVO.getModelnew();
                    } else {*/
                     mailcontent = mailcontent+"\nInventory Model			:"+objRequestVO.getModel();   
                    //}
                /*} else {
                    mailcontent = mailcontent+"\nInventory Sub Type		:" +objRequestVO.getSubtyperouter();
                    mailcontent = mailcontent+"\nInventory Model			:"+objRequestVO.getModelrouter();
                }
                    if(objRequestVO.getMake() == null || objRequestVO.getMakenew() != null){
                    mailcontent = mailcontent+"\nInventory Make			:"+objRequestVO.getMakenew();
                    } else {*/
                       mailcontent = mailcontent+"\nInventory Make			:"+objRequestVO.getMake(); 
                    //}
                mailcontent = mailcontent+"\nQuantity Requested		:"+objRequestVO.getNumberofdevices();
                mailcontent = mailcontent+"\nRequired by Date		:"+objRequestVO.getReqbydate();
                mailcontent = mailcontent+"\n"+WitribeConstants.MAIL_BODY_REQ1_END+WitribeConstants.MAIL_FROM;
                wbo.SendMailSms(WitribeConstants.MAIL_SUBJECT_REQ1,mailcontent,con,msgContent);
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
     public boolean inventoryLevelRequest(RaiseRequestVO objRequestVO,Connection con,String salesid,Connection crmcon) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        String shopname = fetchShopName(crmcon,objRequestVO.getShopId());
        String SalesPersonName = fetchName(crmcon,salesid);
        try{
           // long key = getMaxKey(con);
            pstmt = con.prepareStatement(
                    "INSERT INTO WTB_BRM_SHOP_INV_LEVELS (SHOPID,DATE_MODIFIED," +
                    "INVENTORY_TYPE,INVENTORY_SUBTYPE,MAKE,MODEL,MIN_LEVEL,MAX_LEVEL" +
                    ")" +
                    "VALUES " +
                    "(?,sysdate,?,?,?,?,?,?)");
            
           // pstmt.setString(1,salesid);
            pstmt.setString(1,objRequestVO.getShopId());
           //java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            //pstmt.setTimestamp(2,sqlDate);
            pstmt.setString(2,objRequestVO.getInventorytype());
             /*if(objRequestVO.getSubtype() == null || objRequestVO.getSubtypenew() != null ){
                   pstmt.setString(3,objRequestVO.getSubtypenew()); 
                } else {*/
                   pstmt.setString(3,objRequestVO.getSubtype()); 
               /* }
            
            //pstmt.setString(4,objRequestVO.getMake());
             if(objRequestVO.getMake() == null || objRequestVO.getMakenew() != null ){
                   pstmt.setString(4,objRequestVO.getMakenew());
                } else {*/
                    pstmt.setString(4,objRequestVO.getMake());
                /*}
           // pstmt.setString(6,objRequestVO.getMake());
            if(objRequestVO.getModel()==null || objRequestVO.getModelnew() != null){
                pstmt.setString(5,objRequestVO.getModelnew());
            } else {*/
                pstmt.setString(5,objRequestVO.getModel());
            //}
            //pstmt.setString(5,objRequestVO.getModel()); 
            pstmt.setInt(6,Integer.parseInt(objRequestVO.getMinLevel()));
            pstmt.setInt(7,Integer.parseInt(objRequestVO.getMaxLevel()));
            
          
            if(pstmt.executeUpdate()>0){
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
  
    public long getRequestRowCount(Connection con) throws SQLException,Exception{
        long rowsCount = WitribeConstants.ROWS_COUNT;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("select count(*) from WTB_BRM_ERP_REQUEST_1");
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
    public List getRequestStatus(Connection con,int pageNum,String salesid) throws SQLException,Exception{
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
                      "SELECT  REQUESTID,INVENTORY_TYPE, " +
                      "PROCESS_STATUS,QUANTITY_ORDERED, to_char(to_date(requestdate,'dd-Mon-yyyy HH24:Mi:SS'),'dd-Mon-yyyy'), to_char(to_date(REQUIREDBYDATE,'dd-Mon-yyyy HH24:Mi:SS'),'dd-Mon-yyyy'),QUANTITY_PROCESSED " +
                      " FROM WTB_BRM_ERP_REQUEST_1 where SALES_ID=? " +
                      " ORDER BY  REQUESTID desc ) a WHERE rownum <="+endRow+") WHERE rn >="+startRow);
             pstmt.setString(1,salesid);
             rs = pstmt.executeQuery();
           
             totalRows = getRequestRowCount(con);

            pstmt = con.prepareStatement("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                    "SELECT  REQUESTID,INVENTORY_TYPE, " +
                    "PROCESS_STATUS,QUANTITY_ORDERED, to_char(to_date(requestdate,'dd-Mon-yyyy HH24:Mi:SS'),'dd-Mon-yyyy'), to_char(to_date(REQUIREDBYDATE,'dd-Mon-yyyy HH24:Mi:SS'),'dd-Mon-yyyy'),QUANTITY_PROCESSED " +
                    " FROM WTB_BRM_ERP_REQUEST_1 where SALES_ID=? " +
                    " ORDER BY  REQUESTID desc ) a WHERE rownum <="+endRow+") WHERE rn >="+startRow);
            pstmt.setString(1,salesid);
            rs = pstmt.executeQuery();
            
            totalRows = getRequestRowCount(con);

            /*if(pageNum != WitribeConstants.ZERO){
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
                RaiseRequestVO objRequestVO = new RaiseRequestVO();
                objRequestVO.setRequestId(rs.getString(1));
                objRequestVO.setInventorytype(rs.getString(2));
                objRequestVO.setProcessedstatus(rs.getString(3));
                objRequestVO.setNumberofdevices(rs.getString(4));
                objRequestVO.setRequestdate(rs.getString(5));
                objRequestVO.setReqbydate(rs.getString(6));
                objRequestVO.setQuantityprocessed(rs.getString(7));
                objList.add(objRequestVO);
                
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
    public List getCSEInventory(Connection con,int pageNum,String salesid) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        long totalRows;
        int startRow = 0;
        int endRow = 0;
        RaiseRequestVO objInventory = null;
        try{
            startRow = (pageNum*maxRowCount)+1;
            endRow = (pageNum+1)*maxRowCount+1;
            pstmt = con.prepareStatement("SELECT *  FROM (SELECT a.*, rownum RN FROM ( " +
                    "select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,UPPER(poid_type),NVL(salesid,'NA'),mac_addr_wan,nvl(USR_DEFINED4,'NA') "+
                    "from device_t d,wtb_cpe_details_t cpe where cpe.obj_id0=poid_id0 and cpe.salesid = ? and d.state_id in ("+WitribeConstants.INV_STATUS_NEW+","+WitribeConstants.INV_STATUS_ASSIGNED+")" +
                    "UNION " +
                    "select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,UPPER(poid_type),NVL(salesid,'NA'),null,nvl(USR_DEFINED4,'NA') " +
                    "from device_t d,WTB_ROUTER_T router where router.obj_id0=poid_id0 and router.salesid = ? and d.state_id in ("+WitribeConstants.INV_STATUS_NEW+","+WitribeConstants.INV_STATUS_ASSIGNED+")) a WHERE rownum <="+endRow+") WHERE rn >="+startRow);
            pstmt.setString(1,salesid);
            pstmt.setString(2,salesid);
            rs = pstmt.executeQuery();
            
            //totalRows = getRequestRowCount(con);
            /*if(pageNum != WitribeConstants.ZERO){
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
                objInventory = new RaiseRequestVO();
                objInventory.setPoidId(rs.getString(1));
                objInventory.setInventoryId(rs.getString(2));
                objInventory.setSubtype(rs.getString(3));
                objInventory.setChangeStatus(rs.getString(4));
                objInventory.setMake(rs.getString(5));
                objInventory.setModel(rs.getString(6));
                objInventory.setShopId(rs.getString(7));
                //objInventory.setPoidType(rs.getString(9));
                String poidType = rs.getString(8);
                objInventory.setMacaddrwan(rs.getString(10));
                objInventory.setUserdefined4(rs.getString(11));
                if(poidType != null) {
                    if(poidType.indexOf(WitribeConstants.CPE) > -1) {
                        objInventory.setInventorytype(WitribeConstants.CPE);
                    } else if(poidType.indexOf(WitribeConstants.ROUTER) > -1) {
                        objInventory.setInventorytype(WitribeConstants.ROUTER);
                    }
                    
                }
                objInventory.setSalesId(rs.getString(9));
                objList.add(objInventory);
                
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
    public List fetchShopInventory(Connection con,DistributeInventoryVO objInventoryVO,String shopid) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //RaiseRequestForm dform = new RaiseRequestForm();
        DistributeInventoryVO objInventory = null;
        try{
            pstmt = con.prepareStatement("select poid_id0,poid_id0||'+'||mac_addr_wan||'-'||device_id||'-'||model as ddid,sub_type,state_id,manufacturer,model,source,salesid,mac_addr_wan "+
                    "from device_t d,wtb_cpe_details_t cpe where cpe.obj_id0=poid_id0 and source=? and " +
                    "state_id=TO_NUMBER(?) and UPPER(sub_type)=UPPER(?) and UPPER(poid_type) like '%"+objInventoryVO.getInventorytype()+"%'  order by mac_addr_wan ");
            
            pstmt.setString(1,shopid);
            pstmt.setString(2,WitribeConstants.INV_STATUS_AT_SHOP);
            pstmt.setString(3,objInventoryVO.getSubtype());
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            while(rs.next()){
                objInventory = new DistributeInventoryVO();
                objInventory.setPoidId(rs.getString(1));
                objInventory.setInventoryId(rs.getString(2));
                objInventory.setSubtype(rs.getString(3));
                objInventory.setChangeStatus(rs.getString(4));
                objInventory.setMake(rs.getString(5));
                objInventory.setModel(rs.getString(6));
                objInventory.setShopId(rs.getString(7));
                objInventory.setSalesId(rs.getString(8));
                objInventory.setMacaddrwan(rs.getString(9));
                objList.add(objInventory);
                
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
    public List fetchInventoryList(Connection con,DistributeInventoryVO objInventoryVO,String shopid,Connection crmcon) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DistributeInventoryVO objInventory = null;
        String salespersonname = null;
        String shopname = null;
        try{
            pstmt = con.prepareStatement("select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,salesid,mac_addr_wan,cpe.USR_DEFINED4 "+
                    "from device_t d,wtb_cpe_details_t cpe where cpe.obj_id0=poid_id0 and source=? and state_id=TO_NUMBER(?) " +
                    "order by device_id");
            
            pstmt.setString(1,shopid);
            pstmt.setString(2,objInventoryVO.getSearchbystatus());
            // pstmt.setString(3,objInventoryVO.getSubtype());
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            while(rs.next()){
                objInventory = new DistributeInventoryVO();
                objInventory.setPoidId(rs.getString(1));
                objInventory.setInventoryId(rs.getString(2));
                objInventory.setSubtype(rs.getString(3));
                objInventory.setChangeStatus(rs.getString(4));
                objInventory.setMake(rs.getString(5));
                objInventory.setModel(rs.getString(6));
                objInventory.setShopId(rs.getString(7));
                if(objInventory.getShopId() != null){
                    shopname = fetchShopName(crmcon,objInventory.getShopId());  // Added by swapna
                    objInventory.setShopname(shopname);
                }
                objInventory.setSalesId(rs.getString(8));
                objInventory.setMacaddrwan(rs.getString(9));
                objInventory.setItemcode(rs.getString(10));
                if(objInventory.getSalesId() != null){
                    salespersonname = fetchSalesName(con,objInventory.getSalesId());
                    objInventory.setFullname(salespersonname);
                }
                
                
                objList.add(objInventory);
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
    
    public List searchInventoryList(Connection con,DistributeInventoryVO objInventoryVO,String searchinv,Connection crmcon) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DistributeInventoryVO objInventory = null;
        String salespersonname = null;
        String shopname = null;
        String searchBy = objInventoryVO.getSearchby();
        try{
            if("device_id".equalsIgnoreCase(searchBy)){
                pstmt = con.prepareStatement("select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,salesid,mac_addr_wan,cpe.USR_DEFINED4 "+
                        "from device_t d,wtb_cpe_details_t cpe where cpe.obj_id0=poid_id0 and UPPER("+searchBy+") = UPPER('"+searchinv+"') "+
                        "order by device_id");
            } else {
                pstmt = con.prepareStatement("select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,salesid,mac_addr_wan,cpe.USR_DEFINED4 "+
                        "from device_t d,wtb_cpe_details_t cpe where cpe.obj_id0=poid_id0 and UPPER(cpe."+searchBy+") = UPPER('"+searchinv+"') "+
                        "order by device_id");
            }
            //pstmt.setString(1,objInventoryVO.getSearchby());
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            while(rs.next()){
                objInventory = new DistributeInventoryVO();
                objInventory.setPoidId(rs.getString(1));
                objInventory.setInventoryId(rs.getString(2));
                objInventory.setSubtype(rs.getString(3));
                objInventory.setChangeStatus(rs.getString(4));
                objInventory.setMake(rs.getString(5));
                objInventory.setModel(rs.getString(6));
                objInventory.setShopId(rs.getString(7));
                if(objInventory.getShopId() != null){
                    shopname = fetchShopName(crmcon,objInventory.getShopId());
                    objInventory.setShopname(shopname);
                }
                
                objInventory.setSalesId(rs.getString(8));
                objInventory.setMacaddrwan(rs.getString(9));
                objInventory.setItemcode(rs.getString(10));
                if(objInventory.getSalesId() != null){
                    salespersonname = fetchSalesName(con,objInventory.getSalesId());
                    objInventory.setFullname(salespersonname);
                }
                
                
                objList.add(objInventory);
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
    public String fetchSalesName(Connection con,String name) throws SQLException,Exception{
        
        List objNameList = null;
        PreparedStatement pstmt = null;
        String name1 = null;
        ResultSet rs1 = null;
        
        //RaiseRequestForm dform = new RaiseRequestForm();
        DistributeInventoryVO objInventory = null;
        try{
            pstmt = con.prepareStatement("select concat(concat(ANT.first_name,' '),ANT.last_name) as fullname from ACCOUNT_T AT,Account_NAMEINFO_T ANT Where AT.POID_ID0=ANT.OBJ_ID0 and AT.Access_code1 = ? ");
            pstmt.setString(1,name);
            rs1 = pstmt.executeQuery();
            // objNameList = new ArrayList();
            if(rs1.next()){
                objInventory = new DistributeInventoryVO();
                
                objInventory.setFullname(rs1.getString(1));
                
                name1 = objInventory.getFullname();
            }
            
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs1);
            DBUtil.closeStatement(pstmt);
        }
        
        return name1;
        
    }
    
    public List fetchCSETOShop(Connection con,RaiseRequestVO objInventoryVO,String shopid) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //RaiseRequestForm dform = new RaiseRequestForm();
        RaiseRequestVO objInventory = null;
        try{

             pstmt = con.prepareStatement("select sh.CHILD_SALESPERSONNEL_ID,concat(concat(sd.first_name,' '),sd.last_name) "+
                                        "from SALES_HIERARCHY sh,SALESPERSONNEL_DETAILS sd "+
                                        "where sh.CHILD_SALESPERSONNEL_ID = sd.SALESPERSONNEL_ID and SHOP_ID = ? and sd.SALESPERSON_STATUS = 'Active' order by sd.first_name ") ;
             
             pstmt.setString(1,shopid);

             /*pstmt.setString(2,WitribeConstants.INV_STATUS_AT_SHOP);
             pstmt.setString(3,objInventoryVO.getSubtype());*/
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            while(rs.next()){
                objInventory = new RaiseRequestVO();
                objInventory.setSalesId(rs.getString(1));
                objInventory.setFullname(rs.getString(2));
                objList.add(objInventory);
                
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
    public List fetchReturnInventory(Connection con,RaiseRequestVO objInventoryVO,String shopid) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //RaiseRequestForm dform = new RaiseRequestForm();
        RaiseRequestVO objInventory = null;
        try{
            pstmt = con.prepareStatement("select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,UPPER(poid_type),NVL(salesid,'NA'),mac_addr_wan,nvl(USR_DEFINED4,'NA') "+
                    "from device_t d,wtb_cpe_details_t cpe where cpe.obj_id0=poid_id0 and poid_type like '%cpe%' and source=? and " +
                    "state_id in (TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?)) "+
                    "UNION "+
                    "select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,UPPER(poid_type),NVL(salesid,'NA'),null,nvl(USR_DEFINED4,'NA') "+
                    "from device_t d,WTB_ROUTER_T router where router.obj_id0=poid_id0 and poid_type like '%router%' and source=? and " +
                    "state_id in (TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?))");
            
            pstmt.setString(1,shopid);
            pstmt.setString(2,WitribeConstants.INV_STATUS_REFURBISHED);
            pstmt.setString(3,WitribeConstants.INV_STATUS_DAMAGED);
            pstmt.setString(4,WitribeConstants.INV_STATUS_LOST);
            pstmt.setString(5,WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP);
            pstmt.setString(6,WitribeConstants.INV_STATUS_NEW);
            pstmt.setString(7,WitribeConstants.INV_STATUS_AT_SHOP);
            pstmt.setString(8,shopid);
            pstmt.setString(9,WitribeConstants.INV_STATUS_REFURBISHED);
            pstmt.setString(10,WitribeConstants.INV_STATUS_DAMAGED);
            pstmt.setString(11,WitribeConstants.INV_STATUS_LOST);
            pstmt.setString(12,WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP);
            pstmt.setString(13,WitribeConstants.INV_STATUS_NEW);
            pstmt.setString(14,WitribeConstants.INV_STATUS_AT_SHOP);
            // pstmt.setString(2,objInventoryVO.getSubtype());
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            while(rs.next()){
                objInventory = new RaiseRequestVO();
                objInventory.setPoidId(rs.getString(1));
                objInventory.setInventoryId(rs.getString(2));
                objInventory.setSubtype(rs.getString(3));
                objInventory.setChangeStatus(rs.getString(4));
                objInventory.setMake(rs.getString(5));
                objInventory.setModel(rs.getString(6));
                objInventory.setShopId(rs.getString(7));
                //objInventory.setPoidType(rs.getString(9));
                String poidType = rs.getString(8);
                objInventory.setMacaddrwan(rs.getString(10));
                objInventory.setUserdefined4(rs.getString(11));
                if(poidType != null) {
                    if(poidType.indexOf(WitribeConstants.CPE) > -1) {
                        objInventory.setInventorytype(WitribeConstants.CPE);
                    } else if(poidType.indexOf(WitribeConstants.ROUTER) > -1) {
                        objInventory.setInventorytype(WitribeConstants.ROUTER);
                    }
                    
                }
                objInventory.setSalesId(rs.getString(9));
                objList.add(objInventory);
                
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
    public List fetchTransInventory(Connection con,RaiseRequestVO objInventoryVO,String shopid) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //RaiseRequestForm dform = new RaiseRequestForm();
        RaiseRequestVO objInventory = null;
        try{
            pstmt = con.prepareStatement("select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,UPPER(poid_type),salesid,mac_addr_wan,nvl(USR_DEFINED4,'NA') "+
                    "from device_t d,wtb_cpe_details_t cpe where cpe.obj_id0=poid_id0 and poid_type like '%cpe%' and source=? and " +
                    "state_id in (TO_NUMBER(?),TO_NUMBER(?))"+
                    "UNION "+
                    "select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,UPPER(poid_type),salesid,null,nvl(USR_DEFINED4,'NA') "+
                    "from device_t d,WTB_ROUTER_T router where router.obj_id0=poid_id0 and  poid_type like '%router%' and source=? and " +
                    "state_id in (TO_NUMBER(?),TO_NUMBER(?)) order by mac_addr_wan");
            
            pstmt.setString(1,shopid);
            pstmt.setString(2,WitribeConstants.INV_STATUS_AT_SHOP);
            pstmt.setString(3,WitribeConstants.INV_STATUS_NEW);
            pstmt.setString(4,shopid);
            pstmt.setString(5,WitribeConstants.INV_STATUS_AT_SHOP);
            pstmt.setString(6,WitribeConstants.INV_STATUS_NEW);
            // pstmt.setString(2,objInventoryVO.getSubtype());
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            while(rs.next()){
                objInventory = new RaiseRequestVO();
                objInventory.setPoidId(rs.getString(1));
                objInventory.setInventoryId(rs.getString(2));
                objInventory.setSubtype(rs.getString(3));
                objInventory.setChangeStatus(rs.getString(4));
                objInventory.setMake(rs.getString(5));
                objInventory.setModel(rs.getString(6));
                objInventory.setShopId(rs.getString(7));
                //objInventory.setPoidType(rs.getString(9));
                String poidType = rs.getString(8);
                if(poidType != null) {
                    if(poidType.indexOf(WitribeConstants.CPE) > -1) {
                        objInventory.setInventorytype(WitribeConstants.CPE);
                    } else if(poidType.indexOf(WitribeConstants.ROUTER) > -1) {
                        objInventory.setInventorytype(WitribeConstants.ROUTER);
                    }
                    
                }
                objInventory.setSalesId(rs.getString(9));
                objInventory.setMacaddrwan(rs.getString(10));
                objInventory.setUserdefined4(rs.getString(11));
                objList.add(objInventory);
                
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
    
    public List fetchCSETransInventory(Connection con, String shopid, String salesId) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //RaiseRequestForm dform = new RaiseRequestForm();
        RaiseRequestVO objInventory = null;
        try{
            pstmt = con.prepareStatement("select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,UPPER(poid_type),salesid,mac_addr_wan,nvl(USR_DEFINED4,'NA') "+
                    "from device_t d,wtb_cpe_details_t cpe where cpe.obj_id0=poid_id0 and poid_type like '%cpe%' and source=? and " +
                    "state_id = TO_NUMBER(?) and salesid =? "+
                    "UNION "+
                    "select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,UPPER(poid_type),salesid,null,nvl(USR_DEFINED4,'NA') "+
                    "from device_t d,WTB_ROUTER_T router where router.obj_id0=poid_id0 and  poid_type like '%router%' and source=? and " +
                    "state_id = TO_NUMBER(?) and salesid =? ");
            
            pstmt.setString(1,shopid);
            pstmt.setString(2,WitribeConstants.INV_STATUS_NEW);
            pstmt.setString(3,salesId);
            pstmt.setString(4,shopid);
            pstmt.setString(5,WitribeConstants.INV_STATUS_NEW);
            pstmt.setString(6,salesId);
            // pstmt.setString(2,objInventoryVO.getSubtype());
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            csetransfer = true;
            while(rs.next()){
                objInventory = new RaiseRequestVO();
                objInventory.setPoidId(rs.getString(1));
                objInventory.setInventoryId(rs.getString(2));
                objInventory.setSubtype(rs.getString(3));
                objInventory.setChangeStatus(rs.getString(4));
                objInventory.setMake(rs.getString(5));
                objInventory.setModel(rs.getString(6));
                objInventory.setShopId(rs.getString(7));
                //objInventory.setPoidType(rs.getString(9));
                String poidType = rs.getString(8);
                if(poidType != null) {
                    if(poidType.indexOf(WitribeConstants.CPE) > -1) {
                        objInventory.setInventorytype(WitribeConstants.CPE);
                    } else if(poidType.indexOf(WitribeConstants.ROUTER) > -1) {
                        objInventory.setInventorytype(WitribeConstants.ROUTER);
                    }
                    
                }
                objInventory.setSalesId(rs.getString(9));
                objInventory.setMacaddrwan(rs.getString(10));
                objInventory.setUserdefined4(rs.getString(11));
                objList.add(objInventory);
                
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
    public List fetchInventory(Connection con,DistributeInventoryVO objInventoryVO,String shopid) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DistributeInventoryVO objInventory = null;
        try{
            
            pstmt = con.prepareStatement("select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,salesid,UPPER(POID_TYPE)"+
                    "from device_t d,wtb_cpe_details_t cpe where cpe.obj_id0=poid_id0 and POID_TYPE like '%cpe%' and device_id=? and source=? and state_id between TO_NUMBER(?) and TO_NUMBER(?) "+
                    "UNION "+
                    "select poid_id0,device_id,sub_type,state_id,manufacturer,model,source,salesid,UPPER(POID_TYPE)"+
                    "from device_t d,WTB_ROUTER_T router where router.obj_id0=poid_id0 and POID_TYPE like '%router%' and device_id=? and source=? and state_id between TO_NUMBER(?) and TO_NUMBER(?)");
            String[] invArray = objInventoryVO.getInventoryIdArray();
            if(invArray != null && invArray.length > WitribeConstants.ZERO){
                pstmt.setString(1, invArray[0]);
                pstmt.setString(2,shopid);
                pstmt.setString(3,WitribeConstants.INV_STATUS_AT_SHOP);
                pstmt.setString(4,WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP);
                pstmt.setString(5, invArray[0]);
                pstmt.setString(6,shopid);
                pstmt.setString(7,WitribeConstants.INV_STATUS_AT_SHOP);
                pstmt.setString(8,WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP);
                rs = pstmt.executeQuery();
                objList = new ArrayList();
                while(rs.next()){
                    objInventory = new DistributeInventoryVO();
                    objInventory.setPoidId(rs.getString(1));
                    objInventory.setInventoryId(rs.getString(2));
                    objInventory.setSubtype(rs.getString(3));
                    objInventory.setChangeStatus(rs.getString(4));
                    objInventory.setMake(rs.getString(5));
                    objInventory.setModel(rs.getString(6));
                    objInventory.setShopId(rs.getString(7));
                    objInventory.setSalesId(rs.getString(8));
                    String poidType = rs.getString(9);
                    if(poidType != null) {
                        if(poidType.indexOf(WitribeConstants.CPE) > -1) {
                            objInventory.setInventorytype(WitribeConstants.CPE);
                        } else if(poidType.indexOf(WitribeConstants.ROUTER) > -1) {
                            objInventory.setInventorytype(WitribeConstants.ROUTER);
                        }
                        
                    }
                    objList.add(objInventory);
                    
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
        
        return objList;
        
    }
    public List requestVoucherInfo(Connection brmcon,String resType) throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        VoucherVO objVoucherVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
            pstmt = brmcon.prepareStatement(
                    "SELECT VOUCHER_INFO FROM MST_VOUCHER_INFO where RESOURCE_TYPE =?");
            
            pstmt.setString(1,resType);
            rs = pstmt.executeQuery();
            while(rs.next()){
                objVoucherVO = new VoucherVO();
                objVoucherVO.setVoucherInfo(rs.getString(1));
                objList.add(objVoucherVO);
                
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
       public List reqShopsId(Connection con,String salesid)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       ShopsVO objShopVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
             pstmt = con.prepareStatement(
                   "SELECT  sd.SHOP_ID,sd.SHOP_NAME,sd.SHOP_TYPE,sd.ADDR_PLOT," +
                     "sd.ADDR_STREET,sd.ADDR_SUBZONE,sd.ADDR_ZONE,sd.ADDR_CITY,sd.ADDR_PROVINCE," +
                     "sd.ADDR_COUNTRY,sd.ADDR_ZIP,sd.CREATED_DATE,sd.MODIFIED_DATE " +
                     "FROM SHOP_DETAILS sd,SHOP_SALESID_MAPPING SM where SD.SHOP_ID = SM.SHOP_ID AND SM.SALESPERSONNEL_ID = ?"); 
                    
             pstmt.setString(1,salesid);
             rs = pstmt.executeQuery();
            while(rs.next()){
               objShopVO = new ShopsVO();
               objShopVO.setShopId(rs.getString(1));
               objShopVO.setShopname(rs.getString(2));
               objShopVO.setShopType(rs.getString(3));
               objShopVO.setPlot(rs.getString(4));
               objShopVO.setStreet(rs.getString(5));
               objShopVO.setSubzone(rs.getString(6));
               objShopVO.setZone(rs.getString(7));
               objShopVO.setCity(rs.getString(8));
               objShopVO.setProvince(rs.getString(9));
               objShopVO.setCountry(rs.getString(10));
               objShopVO.setZip(rs.getString(11));
               objShopVO.setCreateDate(rs.getString(12));
               objShopVO.setModifiedDate(rs.getString(13));
               objList.add(objShopVO);
                         
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
        public List minMaxLevel(Connection con,RaiseRequestVO objReq)throws SQLException, Exception{
      
        List objReqList = new ArrayList();
        RaiseRequestVO objReqVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
             pstmt = con.prepareStatement("SELECT MIN_LEVEL,MAX_LEVEL " +
                     "FROM WTB_BRM_SHOP_INV_LEVELS WHERE UPPER(SHOPID) = UPPER(?) AND UPPER(INVENTORY_TYPE) =UPPER(?) AND UPPER(INVENTORY_SUBTYPE) = UPPER(?) "+
                     "AND UPPER(MAKE) = UPPER(?) AND UPPER(MODEL) = UPPER(?) ORDER BY DATE_MODIFIED desc"); 
               pstmt.setString(1,objReq.getShopId()) ;    
               pstmt.setString(2,objReq.getInventorytype()) ;
               pstmt.setString(3,objReq.getSubtype());
               pstmt.setString(4,objReq.getMake());
               pstmt.setString(5,objReq.getModel());   
            rs = pstmt.executeQuery();
            while(rs.next()){
               objReqVO = new RaiseRequestVO();
            objReqVO.setMinLevel(rs.getString(1));
            objReqVO.setMaxLevel(rs.getString(2)); 
              objReqList.add(objReqVO) ;          
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
        
        return objReqList;
   }      
    
    public List requestShopsId(Connection con,String salesid)throws SQLException, Exception{

        List objList = null;
        objList = new ArrayList();
        ShopsVO objShopVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
            pstmt = con.prepareStatement(
                    "SELECT  sd.SHOP_ID,sd.SHOP_NAME,sd.SHOP_TYPE,sd.ADDR_PLOT," +
                    "sd.ADDR_STREET,sd.ADDR_SUBZONE,sd.ADDR_ZONE,sd.ADDR_CITY,sd.ADDR_PROVINCE," +
                    "sd.ADDR_COUNTRY,sd.ADDR_ZIP,sd.CREATED_DATE,sd.MODIFIED_DATE " +
                    "FROM SHOP_DETAILS sd,SHOP_SALESID_MAPPING SM where SD.SHOP_ID = SM.SHOP_ID AND SM.SALESPERSONNEL_ID = ? AND sd.SHOP_STATUS = 'Active'");
            
            pstmt.setString(1,salesid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                objShopVO = new ShopsVO();
                objShopVO.setShopId(rs.getString(1));
                objShopVO.setShopname(rs.getString(2));
                objShopVO.setShopType(rs.getString(3));
                objShopVO.setPlot(rs.getString(4));
                objShopVO.setStreet(rs.getString(5));
                objShopVO.setSubzone(rs.getString(6));
                objShopVO.setZone(rs.getString(7));
                objShopVO.setCity(rs.getString(8));
                objShopVO.setProvince(rs.getString(9));
                objShopVO.setCountry(rs.getString(10));
                objShopVO.setZip(rs.getString(11));
                objShopVO.setCreateDate(rs.getString(12));
                objShopVO.setModifiedDate(rs.getString(13));
                objList.add(objShopVO);
                
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
    
   
   public List getTypeInv(Connection con,RaiseRequestVO reqVO)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       RaiseRequestVO reqVo = null;
        PreparedStatement pstmt = null;
        //PreparedStatement pstmt1 = null;
        ResultSet rs = null;
       // ResultSet rs1 = null;
        WitribeConstants wc = new WitribeConstants();
        try{
             pstmt = con.prepareStatement("select distinct MAKE from INV_DETAIL where UPPER(TYPE) = UPPER(?) and UPPER(SUBTYPE) = UPPER(?) "); 
                  pstmt.setString(1,reqVO.getInventorytype());
                  pstmt.setString(2,reqVO.getSubtype());
                rs = pstmt.executeQuery();
                
            while(rs.next()){
               reqVo = new RaiseRequestVO();
               reqVo.setMake(rs.getString(1));
               objList.add(reqVo);
             
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
   public List getTypeInvMake(Connection con,RaiseRequestVO reqVO)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       RaiseRequestVO reqVo = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
             pstmt = con.prepareStatement("select distinct(MODEL) from INV_DETAIL where UPPER(TYPE) = UPPER(?) and UPPER(SUBTYPE) = UPPER(?) and UPPER(MAKE) = UPPER(?) "); 
                  pstmt.setString(1,reqVO.getInventorytype());
                  pstmt.setString(2,reqVO.getSubtype());
                  pstmt.setString(3,reqVO.getMake());
                rs = pstmt.executeQuery();
             
            while(rs.next()){
               reqVo = new RaiseRequestVO();
               reqVo.setModel(rs.getString(1));
               objList.add(reqVo);
             
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
   /* public List getSpecificTypeInv(Connection con,RaiseRequestVO reqVO)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       RaiseRequestVO reqVo = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
             pstmt = con.prepareStatement(
                   "select MAKE,MODEL,INV_TYPE,INV_SUBTYPE from MST_INVENTORY where UPPER(INV_TYPE) = UPPER(?) and UPPER(INV_SUBTYPE) = UPPER(?)"); 
              pstmt.setString(1,reqVO.getInventorytype());
              pstmt.setString(2,reqVO.getSubtype());
                rs = pstmt.executeQuery();
                String make1 = null;
            while(rs.next()){
               reqVo = new RaiseRequestVO();
               if(!(rs.getString(1).equalsIgnoreCase(make1))){
               reqVo.setMake(rs.getString(1));
               make1 = reqVo.getMake();
               }
               reqVo.setModel(rs.getString(2));
               reqVo.setInventorytype(rs.getString(3));
               reqVo.setSubtype(rs.getString(4));              
               objList.add(reqVo);
                         
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
   } */ 
      public List fetchAssignedSales(Connection con,String shopid,String salesid)throws SQLException, Exception{

        List objList = null;
        objList = new ArrayList();
        DistributeInventoryVO objInventoryVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // WitribeConstants wc = new WitribeConstants();
        
        try{
            pstmt = con.prepareStatement(
                    "select sh.SHOP_ID,sh.CHILD_SALESPERSONNEL_ID,sd.SALESPERSONNEL_TYPE,concat(concat(sd.first_name,' '),sd.last_name)"+
                    "from SALES_HIERARCHY sh,SALESPERSONNEL_DETAILS sd  "+
                    "where sh.SHOP_ID=? AND sh.CHILD_SALESPERSONNEL_ID = sd.SALESPERSONNEL_ID "+
                    "AND sh.PARENT_SALESPERSONNEL_ID = ? AND sd.SALESPERSONNEL_TYPE in (TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?)) AND sd.SALESPERSON_STATUS = 'Active'");
            //, SHOP_DETAILS sp     AND sp.SHOP_ID = sh.SHOP_ID    AND sp.SHOP_STATUS = 'Active'
            pstmt.setString(1,shopid);
            pstmt.setString(2,salesid);
            pstmt.setString(3,WitribeConstants.TYPE_CSE);
            pstmt.setString(4,WitribeConstants.TYPE_BO);
            pstmt.setString(5,WitribeConstants.TYPE_NBO);
            rs = pstmt.executeQuery();
            while(rs.next()){
                objInventoryVO = new DistributeInventoryVO();
                objInventoryVO.setShopId(rs.getString(1));
                objInventoryVO.setSalesId(rs.getString(2));
                objInventoryVO.setAssignedTo(rs.getString(3));
                objInventoryVO.setFullname(rs.getString(4));
                objList.add(objInventoryVO);
                
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
    public boolean distributeRequest(DistributeInventoryVO objInventoryVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        try{
            if(objInventoryVO.getAssignedTo() != null) {
                status = changeInvStatus(objInventoryVO,con);
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
    public boolean ReturnRequest(RaiseRequestVO objInventoryVO,Connection brmcon,String SalesPersonId,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        
        // con.setAutoCommit(false);
        brmcon.setAutoCommit(false);
        try{
            //long key = getMaxInvReturnKey(brmcon);
            long key = getMaxKey(brmcon);
            String[] invid = objInventoryVO.getInventoryIdArray();
            StringBuffer inString = new StringBuffer("(");
            String shopname = fetchShopName(con,objInventoryVO.getShopId());
            if(invid != null && invid.length > 0) {
                
                pstmt = brmcon.prepareStatement(
                        " INSERT into WTB_BRM_ERP_Request_2 (REQUESTID, SHOPID,INVENTORY_ID,INVENTORYSTATUS,MAC_ADDR_WAN,REQUESTDATE,POID_DB,POID_ID0,POID_TYPE,POID_REV,PROCESS_STATUS,SALESID,ITEMCODE) "+
                        "VALUES (?,?,?,?,?,to_char(sysdate,'dd-Mon-yyyy HH24:MI:SS'),1,?,'/wtb_brm_erp_request_2',0,1,?,?)");
                pstmt.setLong(1,key);
                pstmt.setString(2, objInventoryVO.getShopId());
                pstmt.setLong(6,key);
                
                String invId= "";
                String invType="";
                String invStatus = "";
                String macaddrwan = "";
                String salesid = "";
                String itemcode="";
                String make = "";
                String model = "";
                StringTokenizer st = null;
                String mailContent = "";
                StringBuffer InventoryId = new StringBuffer();
                StringBuffer MacAddr = new StringBuffer();
                for(int i=0;i<invid.length-1;i++){
                    st = new StringTokenizer(invid[i],"*");
                    invId = st.nextToken();
                    //InventoryId.append(invId);
                    //InventoryId.append(",");
                    invType = st.nextToken();
                    invStatus = st.nextToken();
                    macaddrwan = st.nextToken();
                    //MacAddr.append(macaddrwan);
                    //MacAddr.append(",");
                    salesid = st.nextToken();
                    itemcode=st.nextToken();
                    make = st.nextToken();
                    model = st.nextToken();
                    pstmt.setString(3,invId);
                    // pstmt.setString(4,invType);
                    pstmt.setString(4,invStatus);
                    pstmt.setString(5,macaddrwan);
                    pstmt.setString(7,salesid);
                    pstmt.setString(8,itemcode);
                    mailContent = mailContent+"\n\nInventory ID			:"+invId;
                    mailContent = mailContent+"\nInventory Type			:"+invType;
                    mailContent = mailContent+"\nInventory Status		:"+invStatus;
                    mailContent = mailContent+"\nMAC ADDRESS WAN			:"+macaddrwan;
                    mailContent = mailContent+"\nItem Code			:"+itemcode;
                    mailContent = mailContent+"\nMake 			:"+make;
                    mailContent = mailContent+"\nModel			:"+model;
                    
                    pstmt.addBatch();
                    inString.append("'");
                    inString.append(invId);
                    inString.append("'");
                    inString.append(",");
                    pstmt1 = brmcon.prepareStatement("select state_id from device_t where device_id = ?");
                    pstmt1.setString(1,invId);
                    ResultSet rs = pstmt1.executeQuery();
                    if(rs.next()){
                        
                    }
                    
                }
                st = new StringTokenizer(invid[invid.length-1],"*");
                invId = st.nextToken();
                //InventoryId.append(invId);
                invType = st.nextToken();
                invStatus = st.nextToken();
                macaddrwan = st.nextToken();
                //MacAddr.append(macaddrwan);
                salesid = st.nextToken();
                itemcode=st.nextToken();
                make = st.nextToken();
                model = st.nextToken();
                pstmt.setString(3,invId);
                //pstmt.setString(4,invType);
                pstmt.setString(4,invStatus);
                pstmt.setString(5,macaddrwan);
                pstmt.setString(7,salesid);
                pstmt.setString(8,itemcode);
                mailContent = mailContent+"\n\nInventory ID			:"+invId;
                mailContent = mailContent+"\nInventory Type			:"+invType;
                mailContent = mailContent+"\nInventory Status		:"+invStatus;
                mailContent = mailContent+"\nMAC ADDRESS WAN			:"+macaddrwan;
                mailContent = mailContent+"\nItem Code			:"+itemcode;
                mailContent = mailContent+"\nMake 			:"+make;
                mailContent = mailContent+"\nModel			:"+model;
                pstmt.addBatch();
                inString.append("'");
                inString.append(invId);
                inString.append("'");
                inString.append(")");
                int[] batch=pstmt.executeBatch();
                if(batch.length == invid.length){
                    status = true;
                }
                if(status){
                    status = false;
                    pstmt = brmcon.prepareStatement("update WTB_CPE_DETAILS_T  set SALESID = ''  "+
                            "where obj_id0 in (select poid_id0 from device_t where device_id in "+inString.toString()+" and state_id = "+WitribeConstants.INV_STATUS_NEW+")");
                    
                    if(pstmt.executeUpdate()>0){
                        status = true;
                    }
                }
                if(status){
                    status = false;
                    pstmt = brmcon.prepareStatement("update device_t  set STATE_ID = TO_NUMBER(?) "+
                            "where DEVICE_ID in "+inString.toString()+"AND STATE_ID!="+WitribeConstants.INV_STATUS_ASSIGNED);
                    pstmt.setString(1,WitribeConstants.INV_STATUS_RETURN_ERP);
                    if(pstmt.executeUpdate()>0){
                        status = true;
                    }
                }
                
                if(status) {
                    //con.commit();
                    brmcon.commit();
                    WitribeInventoryBO wbo= new WitribeInventoryBO();
                    java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
                    String mailcontent = WitribeConstants.MAIL_BODY_REQ2;
                    String msgContent = WitribeConstants.SMS_BODY_REQ2;
                    msgContent = msgContent+"Request ID: "+key;
                    msgContent = msgContent+" Request Raised On: "+sqlDate;
                    msgContent = msgContent+" Please check the mail for more Details";
                    String SalesPersonName = fetchName(con,SalesPersonId);
                    mailcontent = mailcontent+"\nRequest ID			:"+ key;
                    mailcontent = mailcontent+"\nRequest Raised On		:"+sqlDate;
                    mailcontent = mailcontent+"\nRequest Raised By Team Lead	:"+ SalesPersonName+"-"+SalesPersonId;
                    mailcontent = mailcontent+"\nRequest Raised By Shop		:" +objInventoryVO.getShopId();
                    mailcontent = mailcontent+"\nShop Name		:" +shopname;
                    mailcontent = mailcontent+mailContent;
                    mailcontent = mailcontent+WitribeConstants.MAIL_BODY_REQ2_END+WitribeConstants.MAIL_FROM;
                    wbo.SendMailSms(WitribeConstants.MAIL_SUBJECT_REQ2,mailcontent,brmcon,msgContent);
                }
                //con.rollback();
                brmcon.commit();
            }
        } catch(SQLException se){
            con.rollback();
            brmcon.rollback();
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            con.rollback();
            brmcon.rollback();
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            //  con.setAutoCommit(true);
            brmcon.commit();
            brmcon.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
    }
    public String fetchShopName(Connection con,String shopid) throws SQLException{
        PreparedStatement pstmt = null;
        String shopname = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("select shop_name from SHOP_DETAILS where shop_id = ?") ;
            pstmt.setString(1,shopid);
            rs = pstmt.executeQuery();
            if(rs.next()){
                shopname = rs.getString(1);
            }
        } finally{
            //  con.setAutoCommit(true);
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
            DBUtil.closeResultSet(rs);
            
        }
        return  shopname;
    }
    
    public String fetchName(Connection con,String salesId) throws SQLException,Exception{
        PreparedStatement pstmt = null;
        String salesPersonName = null;
        ResultSet  rs = null;
        try {
            pstmt = con.prepareStatement("select concat(concat(FIRST_NAME,' '),LAST_NAME) from SALESPERSONNEL_DETAILS where salespersonnel_id = ?") ;
            pstmt.setString(1,salesId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                salesPersonName = rs.getString(1);
            }
        } finally{
            //  con.setAutoCommit(true);
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
            DBUtil.closeResultSet(rs);
            
        }
        return  salesPersonName;
    }
    public boolean transferRequest(RaiseRequestVO objInventoryVO,Connection brmcon,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        PreparedStatement logstatement = null;
        PreparedStatement fetchInfoStmnt = null;
        
        Connection LSLPCon = null;
        LSLPCon = DBUtil.getConnection();
        LSLPCon.setAutoCommit(false);
        //con.setAutoCommit(false);
        //Connection con = null;
        brmcon.setAutoCommit(false);
        try{
            //long key = getMaxInvTransKey(brmcon);
            long key = getMaxKey(brmcon);
            //key++;
            String[] invid = objInventoryVO.getInventoryIdArray();
            StringBuffer inString = new StringBuffer("(");
            String shopname = fetchShopName(con,objInventoryVO.getShopId());
            String toShopName = fetchShopName(con,objInventoryVO.getToShop());
            if(invid != null && invid.length > 0) {
                pstmt = brmcon.prepareStatement(
                        " INSERT into WTB_BRM_ERP_Request_3 (REQUESTID,FROMSHOPID,TOSHOPID,INVENTORY_ID,INVENTORY_TYPE,MAC_ADDR_WAN,POID_DB,POID_ID0,POID_TYPE,POID_REV,PROCESS_STATUS,REQUESTDATE,ITEMCODE) "+
                        "VALUES (?,?,?,?,?,?,1,?,'/wtb_brm_erp_request_3',0,1,to_char(sysdate,'dd-Mon-yyyy HH24:MI:SS'),?)");
                /* Changes Made By PKAasimN  
                 * Date: 9 Jan, 2012
                 * Email: aasim.naveed@pk.wi-tribe.com
                 */
                 
                /*
                 * Changes Stare Here!
                 */
                logstatement = LSLPCon.prepareStatement("insert into inventory_assignment_tracker (inventory_id,from_shop_id,to_shop_id,assignedby,assignedto,assignedfrom,actiondate,device_old_status,device_current_status,device_mac) "+
                        " values(?,?,?,?,?,?,?,?,?,?)");
                
                /*pstmt1 = brmcon.prepareStatement(
                        " INSERT into WTB_BRM_ERP_RESPONSE_3 (REQUESTID,INVENTORY_ID,POID_DB,POID_ID0,POID_TYPE,POID_REV) "+
                        "VALUES (?,?,1,?,'/response3',0)");*/
                pstmt.setLong(1,key);
                //pstmt1.setLong(1,key);
                pstmt.setString(2, objInventoryVO.getShopId());
                pstmt.setString(3, objInventoryVO.getToShop());
                
                // ================================= PKAasimN Custom Code Starts ====================== //
                logstatement.setString(2,objInventoryVO.getShopId());
                logstatement.setString(3,objInventoryVO.getToShop());
                // ================================= PKAasimN Custom Code Ends ====================== //
                pstmt.setLong(7,key);
                //pstmt1.setLong(3,key);
                // pstmt1.setString(2, objInventoryVO.getShopId());
                //pstmt1.setString(3, objInventoryVO.getToShop());
                
                String invId= "";
                String invType="";
                String macaddrwan = "";
                String itemcode = "";
                String make = "";
                String model = "";
                //String invStatus = "";
                StringTokenizer st = null;
                String mailContent = "";
                for(int i=0;i<invid.length-1;i++){
                    st = new StringTokenizer(invid[i],"*");
                    macaddrwan = st.nextToken();
                    invId = st.nextToken();
                    itemcode = st.nextToken();
                    st.nextToken();                   
                    //itemcode = st.nextToken();
                    model = st.nextToken();
                    make = st.nextToken();
                    invType = st.nextToken();
                    //model = st.nextToken();
                    //invStatus = st.nextToken();
                    pstmt.setString(4,invId);
                    
                    // ================================= PKAasimN Custom Code Starts ====================== //
                    logstatement.setString(1,invId);
                    logstatement.setString(4,objInventoryVO.getSalesId());
                    logstatement.setString(5,null);
                    
                    if(invType.equals("CPE"))
                    {
                        String Query = "select * from device_t left join WTB_CPE_DETAILS_T on poid_id0 = obj_id0 where device_id like '"+invId+"'";
                        fetchInfoStmnt = brmcon.prepareStatement(Query);
                        ResultSet rs = fetchInfoStmnt.executeQuery();
                        while(rs.next()) {
                            logstatement.setString(6,rs.getString("salesid"));
                            logstatement.setString(8,rs.getString("state_id"));
                        }
                    }
                    
                    else if(invType.equals("ROUTER"))
                    {
                        fetchInfoStmnt = brmcon.prepareStatement("select * from device_t left join WTB_ROUTER_T on poid_id0 = obj_id0 where device_id like '"+invId+"'");
                        ResultSet rs = fetchInfoStmnt.executeQuery();
                        while(rs.next()) {
                            logstatement.setString(6,rs.getString("salesid"));
                            logstatement.setString(8,rs.getString("state_id"));
                        }
                    }
                    
                    else if(invType.equals("OTHER"))
                    {
                        fetchInfoStmnt = brmcon.prepareStatement("select * from device_t left join WTB_DEVICE_OTHERS_T on poid_id0 = obj_id0 where device_id like '"+invId+"'");
                        ResultSet rs = fetchInfoStmnt.executeQuery();
                        while(rs.next()) {
                            logstatement.setString(6,rs.getString("salesid"));
                            logstatement.setString(8,rs.getString("state_id"));
                        }
                    }
                    
                    java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
                    logstatement.setTimestamp(7,sqlDate);
                    logstatement.setString(9,WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER);
                    logstatement.setString(10,macaddrwan);
                    
                    // ================================= PKAasimN Custom Code Ends ====================== //
                    pstmt.setString(5,invType);
                    pstmt.setString(6,macaddrwan);
                    pstmt.setString(8,itemcode);
                    mailContent = mailContent+"\n\nInventory ID			:"+invId;
                    mailContent = mailContent+"\nInventory Type			:"+invType;
                    mailContent = mailContent+"\nMAC ADDRESS WAN			:"+macaddrwan;
                    mailContent = mailContent+"\nItem Code			:"+itemcode;
                    mailContent = mailContent+"\nMake 			:"+make;
                    mailContent = mailContent+"\nModel			:"+model;
                    
                    //pstmt1.setString(2,invId);
                    //pstmt1.setString(5,invType);
                    //pstmt.setInt(6,1);
                    pstmt.addBatch();
                    logstatement.addBatch();
                    //pstmt1.addBatch();
                    inString.append("'");
                    inString.append(invId);
                    
                    inString.append("'");
                    inString.append(",");
                }
                
                st = new StringTokenizer(invid[invid.length-1],"*");
                macaddrwan = st.nextToken();
                //InventoryId.append(invId);
                invId = st.nextToken();
                itemcode = st.nextToken();
                st.nextToken();   
                model = st.nextToken();
                //MacAddr.append(macaddrwan);
                make = st.nextToken();
                invType = st.nextToken();
                //itemcode = st.nextToken();
                //model = st.nextToken();
                //invStatus = st.nextToken();
                pstmt.setString(4,invId);
                pstmt.setString(5,invType);
                pstmt.setString(6,macaddrwan);
                pstmt.setString(8,itemcode);
                mailContent = mailContent+"\n\nInventory ID			:"+invId;
                mailContent = mailContent+"\nInventory Type			:"+invType;
                mailContent = mailContent+"\nMAC ADDRESS WAN			:"+macaddrwan;
                mailContent = mailContent+"\nItem Code			:"+itemcode;
                mailContent = mailContent+"\nMake             :"+make;
                mailContent = mailContent+"\nModel                :"+model;
                
                // ================================= PKAasimN Custom Code Starts ====================== //
                logstatement.setString(1,invId);
                logstatement.setString(4,objInventoryVO.getSalesId());
                logstatement.setString(5,null);
                
                 if(invType.equals("CPE"))
                    {
                        String Query = "select * from device_t left join WTB_CPE_DETAILS_T on poid_id0 = obj_id0 where device_id like '"+invId+"'";
                        fetchInfoStmnt = brmcon.prepareStatement(Query);
                        ResultSet rs = fetchInfoStmnt.executeQuery();
                        while(rs.next()) {
                            logstatement.setString(6,rs.getString("salesid"));
                            logstatement.setString(8,rs.getString("state_id"));
                        }
                    }
                    
                    else if(invType.equals("ROUTER"))
                    {
                        fetchInfoStmnt = brmcon.prepareStatement("select * from device_t left join WTB_ROUTER_T on poid_id0 = obj_id0 where device_id like '"+invId+"'");
                        ResultSet rs = fetchInfoStmnt.executeQuery();
                        while(rs.next()) {
                            logstatement.setString(6,rs.getString("salesid"));
                            logstatement.setString(8,rs.getString("state_id"));
                        }
                    }
                    
                    else if(invType.equals("OTHER"))
                    {
                        fetchInfoStmnt = brmcon.prepareStatement("select * from device_t left join WTB_DEVICE_OTHERS_T on poid_id0 = obj_id0 where device_id like '"+invId+"'");
                        fetchInfoStmnt.setString(1, invId);
                        ResultSet rs = fetchInfoStmnt.executeQuery();
                        while(rs.next()) {
                            logstatement.setString(6,rs.getString("salesid"));
                            logstatement.setString(8,rs.getString("state_id"));
                        }
                    }
                    
                    java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
                    logstatement.setTimestamp(7,sqlDate);
                    logstatement.setString(9,WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER);
                    logstatement.setString(10,macaddrwan);
                
                // ================================= PKAasimN Custom Code Starts ====================== //
                //pstmt1.setString(2,invId);
                //pstmt1.setString(5,invType);
                //pstmt.setInt(6,1);
                pstmt.addBatch();
                
                logstatement.addBatch();
                //pstmt1.addBatch();
                inString.append("'");
                inString.append(invId);
                inString.append("'");
                inString.append(")");
                int[] batch=pstmt.executeBatch();
                int [] logbatch = logstatement.executeBatch();
                //int[] batch1=pstmt1.executeBatch();
                if(batch.length == invid.length && logbatch.length == invid.length)
                    //&& batch1.length == invid.length
                {
                    status = true;
                }
                if(status){
                    status = false;
                    pstmt = brmcon.prepareStatement("update WTB_CPE_DETAILS_T set SALESID='' where "+
                            " OBJ_ID0 in (select POID_ID0 from device_t where device_id in "+inString.toString()+")");
                    //pstmt.setString(1,WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER);
                    // pstmt.setString(2,objInventoryVO.getToShop());
                    if(pstmt.executeUpdate()>0){
                        status = true;
                    }
                }
                if(status){
                    status = false;
                    pstmt = brmcon.prepareStatement("update device_t  set STATE_ID = TO_NUMBER(?) "+
                            "where DEVICE_ID in "+inString.toString());
                    pstmt.setString(1,WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER);
                    // pstmt.setString(2,objInventoryVO.getToShop());
                    if(pstmt.executeUpdate()>0){
                        status = true;
                    }
                }
                if(status) {
                    //con.commit();
                    brmcon.commit();
                    LSLPCon.commit();
                    WitribeInventoryBO wbo= new WitribeInventoryBO();
                    String mailcontent = WitribeConstants.MAIL_BODY_REQ3;
                    java.sql.Timestamp  sqlDate1 = new java.sql.Timestamp(new java.util.Date().getTime());
                    // Added for SMS by swapna
                    String msgContent = WitribeConstants.SMS_BODY_REQ3;
                    msgContent = msgContent+"Request ID: "+key;
                    msgContent = msgContent+" Request Raised On: "+sqlDate1;
                    msgContent = msgContent+" Please check the mail for more Details";
                    // End
                    String SalesPersonName = fetchName(con,objInventoryVO.getSalesId());
                    mailcontent = mailcontent+"\nRequest ID			:"+ key;
                    mailcontent = mailcontent+"\nRequest Raised On		:"+sqlDate;
                    mailcontent = mailcontent+"\nRequest Raised by		:"+SalesPersonName+"-"+objInventoryVO.getSalesId();
                    mailcontent = mailcontent+"\nFrom Shop			:" +objInventoryVO.getShopId();
                    mailcontent = mailcontent+"\nFrom Shop Name			:" +shopname;
                    mailcontent = mailcontent+"\nTo Shop                :" +objInventoryVO.getToShop();
                    mailcontent = mailcontent+"\nTo Shop Name			:" +toShopName;
                    mailcontent = mailcontent+mailContent;
                    mailcontent = mailcontent+WitribeConstants.MAIL_BODY_REQ3_END+WitribeConstants.MAIL_FROM;
                    LeadDAO ld = new LeadDAO();
                    LeadVO lv = new LeadVO();
                    lv.setAssignedCSE(objInventoryVO.getSalesId());
                    wbo.SendMailSms(WitribeConstants.MAIL_SUBJECT_REQ3,mailcontent,brmcon,msgContent);
                    if(csetransfer) {
                        csetransfer = false;
                        //Connection con = null;
                        //con = DBUtil.getConnection();
                        lv = ld.fetchMailandNumber(lv,con);
                        
                        WitribeBO wtbo = new WitribeBO();
                        mailcontent = mailcontent+"\n NOTE: This mail is the copy which has been sent to " +
                                "Finance Team for transfer of inventory for CSE Transfer";
                        wtbo.sendmail("noreply@wi-tribe.pk",lv.getSalesEmail(),WitribeConstants.MAIL_SUBJECT_REQ3,mailcontent);
                    }
                }

            }
            

            brmcon.commit();
        } catch(SQLException se){
            //con.rollback();
            brmcon.rollback();
            LSLPCon.rollback();
            DBUtil.closeStatement(pstmt);
            DBUtil.closeStatement(logstatement);
            DBUtil.closeStatement(fetchInfoStmnt);
            DBUtil.closeConnection(LSLPCon);
            throw se;
        } catch(Exception e){
            //con.rollback();
            brmcon.rollback();
            LSLPCon.rollback();
            DBUtil.closeStatement(pstmt);
            DBUtil.closeStatement(logstatement);
            DBUtil.closeStatement(fetchInfoStmnt);
            DBUtil.closeConnection(LSLPCon);
            throw e;
        } finally{
            //con.setAutoCommit(true);
            brmcon.commit();
            LSLPCon.commit();
            LSLPCon.setAutoCommit(true);
            brmcon.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
            DBUtil.closeStatement(logstatement);
            DBUtil.closeStatement(fetchInfoStmnt);
            DBUtil.closeConnection(LSLPCon);
        }
        
        return status;
        
    }
    
    public boolean updateInvStatusofCSE(Connection con, String salesId, String shopID)throws SQLException, Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("update device_t a set state_id=8 " +
                    "where poid_id0=(select obj_id0 from wtb_cpe_details_t where a.poid_id0=obj_id0 and " +
                    "salesid=? )and source = ? and state_id=2");
            
            pstmt.setString(1,salesId);
            pstmt.setString(2,shopID);
            // pstmt.setLong(2,Long.parseLong(poidArray[0]));
            

            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
            }
        } catch (SQLException se) {
            throw se;
        } finally {
            DBUtil.closeStatement(pstmt);
        }
        return status;
    }
    
    public boolean changeInvStatus(DistributeInventoryVO objInventoryVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        PreparedStatement fetchSql = null;
        PreparedStatement Stmnt = null;
        con.setAutoCommit(false);
        Connection LSLPCon = null;
       
        try{
            //String[] invpoid = objInventoryVO.getPoidArray();
            //invpoid.substring(0,invpoid.indexOf('-'));
           // objInventoryVO.setPoidArray(invpoid);
            String[] poidArray = objInventoryVO.getPoidArray();
            StringBuffer inString = new StringBuffer("(");
            if(poidArray != null && poidArray.length > WitribeConstants.ZERO) {
                if(poidArray.length >1) {
                    for(int i=0;i<poidArray.length-1;i++){
                        inString.append(poidArray[i]);
                        inString.append(",");
                    }
                    inString.append(poidArray[poidArray.length-1]);
                } else {
                    inString.append(poidArray[0]);
                }
                inString.append(")");
                System.out.println("inString" +inString);
                
                /* Changes Made By PKAasimN  
                 * Date: 12Dec11
                 * Email: aasim.naveed@pk.wi-tribe.com
                 */
                 
                /*
                 * Changes Stare Here!
                 */
                // == Fetching detials of Device for Insertion in Tracker before Updating it === //
                if(objInventoryVO.getInventorytype().equals("CPE"))
                {
                    fetchSql = con.prepareStatement("select * from device_t left join WTB_CPE_DETAILS_T on poid_id0 = obj_id0 where poid_id0 in "+inString.toString());
                }
                
                if(objInventoryVO.getInventorytype().equals("ROUTER"))
                {
                    fetchSql = con.prepareStatement("select * from device_t left join WTB_ROUTER_T on poid_id0 = obj_id0 where poid_id0 in "+inString.toString());                
                }
                
                if(objInventoryVO.getInventorytype().equals("OTHER"))
                {
                    fetchSql = con.prepareStatement("select * from device_t left join WTB_DEVICE_OTHERS_T on poid_id0 = obj_id0 where poid_id0 in "+inString.toString());
                }
                
                ResultSet rs = fetchSql.executeQuery();
                // =============================================================================//
                pstmt = con.prepareStatement(
                        " update device_t set state_id =? where poid_id0 In "+inString.toString());
                
                pstmt.setInt(1,Integer.parseInt(objInventoryVO.getChangeStatus()));
                // pstmt.setLong(2,Long.parseLong(poidArray[0]));
                
                if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                    status = true;
                }
                if(objInventoryVO.getAssignedTo() != null && status) {
                    status = updateSalesId(objInventoryVO,con);
                }
                
                //=====================Inserting Values =========================//                
                // TODO: Insert record in cpe_details_tracker                        
                            LSLPCon = DBUtil.getConnection();
                             LSLPCon.setAutoCommit(false);
                            while(rs.next()) {
                         /*       
                                String Query = "insert into inventory_assignment_tracker"
                                        + "(inventory_id,from_shop_id,to_shop_id,assignedby,assignedto,assignedfrom,actiondate,device_old_status,device_current_status,device_model,device_subtype,device_mac) "
                                        + " values('"+rs.getString("device_id")+"','"+objInventoryVO.getShopId()+"','"+objInventoryVO.getShopId()
                                        +"','"+ objInventoryVO.getSalesId()+"','"+objInventoryVO.getAssignedTo()+"',"+rs.getString("salesid") +",SYSDATE,"+rs.getString("state_id")
                                        +","+objInventoryVO.getChangeStatus()+",'"+rs.getString("model")+"','"+rs.getString("sub_type")+"','"+rs.getString("mac_addr_wan")+"'"
                                        +")";
                                PreparedStatement Stmnt = LSLPCon.prepareStatement(Query);
                                Stmnt.executeQuery();
                            }           
                
                // =============================================================//
               /* String[] invs = objInventoryVO.getInventoryIdArray();
                if(status) {
                     pstmt = con.prepareStatement("insert into inventory_assignment_tracker (inventory_id,from_shop_id,to_shop_id,assignedby,assignedto,assignedfrom,actiondate,device_old_status,device_current_status,device_mac) values "+
                             " (?,?,?,?,?,?,?,?,?,?)");
                     
                      pstmt.setString(1,invs[0]);
                      pstmt.setString(2,objInventoryVO.getOldStatus());
                      pstmt.setString(3,objInventoryVO.getChangeStatus());
                      java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
                      pstmt.setTimestamp(4,sqlDate);
                      pstmt.setString(5,objInventoryVO.getAssignedTo());
                      if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                        status = true;
                        }
                   
                }*/
                if(!objInventoryVO.getChangeStatus().equals("2") && !objInventoryVO.getChangeStatus().equals("1"))
                {
                    objInventoryVO.setAssignedTo(rs.getString("salesid"));
                }
                
                Stmnt = LSLPCon.prepareStatement("insert into inventory_assignment_tracker (inventory_id,from_shop_id,to_shop_id,assignedby,assignedto,assignedfrom,actiondate,device_old_status,device_current_status,device_mac) values "+
                             " (?,?,?,?,?,?,?,?,?,?)");
                     
                      Stmnt.setString(1,rs.getString("device_id"));
                      Stmnt.setString(2,objInventoryVO.getShopId());
                      Stmnt.setString(3,objInventoryVO.getShopId());
                      Stmnt.setString(4,objInventoryVO.getSalesId());
                      Stmnt.setString(5,objInventoryVO.getAssignedTo());
                      Stmnt.setString(6,rs.getString("salesid"));
                      java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
                      Stmnt.setTimestamp(7,sqlDate);
                      Stmnt.setString(8,rs.getString("state_id"));
                      Stmnt.setString(9,objInventoryVO.getChangeStatus());
                      Stmnt.setString(10,rs.getString("mac_addr_wan"));
                      Stmnt.executeQuery();
                    }

                 rs.close();
                //con.rollback();
                            
                /*
                 * Changes end Here!
                 */
            }
        } catch(SQLException se){
            con.rollback();
            LSLPCon.rollback();
            DBUtil.closeStatement(pstmt);
            DBUtil.closeStatement(fetchSql);
            DBUtil.closeStatement(Stmnt);
            DBUtil.closeConnection(LSLPCon);
            throw se;
        } catch(Exception e){
            con.rollback();
            LSLPCon.rollback();
            DBUtil.closeStatement(pstmt);
            DBUtil.closeStatement(fetchSql);
            DBUtil.closeStatement(Stmnt);
            DBUtil.closeConnection(LSLPCon);
            throw e;
        } finally{
            con.commit();
            LSLPCon.commit();
            con.setAutoCommit(true);
            LSLPCon.close();
            DBUtil.closeStatement(pstmt);
            DBUtil.closeStatement(fetchSql);
            DBUtil.closeStatement(Stmnt);
            DBUtil.closeConnection(LSLPCon);
        }
        
        return status;
        
    }
    public boolean updateSalesId(DistributeInventoryVO objInventoryVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        try {
            String[] poidArray = objInventoryVO.getPoidArray();
            StringBuffer inString = new StringBuffer("(");
            if(poidArray != null && poidArray.length > WitribeConstants.ZERO) {
                if(poidArray.length >1) {
                    for(int i=0;i<poidArray.length-1;i++){
                        inString.append(poidArray[i]);
                        inString.append(",");
                    }
                    inString.append(poidArray[poidArray.length-1]);
                } else {
                    inString.append(poidArray[0]);
                }
                inString.append(")");
                
                if(WitribeConstants.CPE.equalsIgnoreCase(objInventoryVO.getInventorytype())) {
                    
                    pstmt = con.prepareStatement("update wtb_cpe_details_t set salesid = ? "+
                            " where obj_id0 In "+inString.toString());
                    if(Integer.parseInt(objInventoryVO.getChangeStatus())==1){
                        pstmt.setString(1,"");
                    }else{
                        pstmt.setString(1,objInventoryVO.getAssignedTo());
                    }
                    if(pstmt.executeUpdate()>0){
                        status = true;
                    }
                } else if(WitribeConstants.ROUTER.equalsIgnoreCase(objInventoryVO.getInventorytype())) {
                    
                    pstmt = con.prepareStatement("update WTB_ROUTER_T set salesid = ? "+
                            " where obj_id0 IN "+inString.toString());
                    if(Integer.parseInt(objInventoryVO.getChangeStatus())==1){
                        pstmt.setString(1,"");
                    }else{
                        pstmt.setString(1,objInventoryVO.getAssignedTo());
                    }
                    if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                        status = true;
                    }
                } else {
                    //for other scenario
                }
            }
        }finally {
            DBUtil.closeStatement(pstmt);
        }
        return status;
    }
    public boolean sendMessage(Connection brmcon,String msgtext,String subject,String number) throws SQLException,Exception{
        PreparedStatement pstmt = null;
        boolean status = false;
        try{
            pstmt = brmcon.prepareStatement("INSERT INTO WTB_SMS_DETAILS (CALLING_PROGRAM,MOBILE_NUMBER,ERROR_NUMBER,MESSAGE_TXT,SUBJECT_TXT,STATUS,RUN_NUMBER,MSG_SENT_TIME) VALUES('LSLP',?,0,?,?,0,0,sysdate)");
            pstmt.setString(1,number);
            pstmt.setString(2,msgtext);
            pstmt.setString(3,subject);
            if(pstmt.executeUpdate()>0){
                status = true;
            }
            
        } catch(Exception e) {
            LogUtil.error("Exception: SMS Record with text "+msgtext+" not inserted  because of "+e.getMessage(),this.getClass());
        }
        return status;
    }
    //Added By bhawna on 21st oct
    
    public long getVoucherReqKey(Connection brmcon) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = brmcon.createStatement();
            rs = stmt.executeQuery("SELECT VOUCHER_REQUEST_ID.nextval from dual");
            if(rs.next()) {
                maxKey = rs.getLong(1);
                System.out.println(maxKey);
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
        return  maxKey;
    }
    public boolean createVoucherRequest(Connection brmcon,VoucherVO objReqVO,String salesid) throws SQLException, Exception {
        boolean status = false;
        PreparedStatement pstmt = null;
        try{
            long key = getVoucherReqKey(brmcon);
            pstmt = brmcon.prepareStatement("INSERT INTO VOUCHER_REQUEST (REQUEST_ID,VOUCHER_TYPE,RESOURCE_TYPE,QUANTITY_ORDERED,VOUCHER_INFO,REQUEST_DATE,SALESPERSONNEL_ID,PROCESS_STATUS,SHOP_ID) VALUES (?,?,?,?,?,sysdate,?,1,?)");
            pstmt.setLong(1,key);
            pstmt.setString(2,objReqVO.getVoucherType());
            pstmt.setString(3,objReqVO.getResType());
            pstmt.setString(4,objReqVO.getQuantity());
            pstmt.setString(5,objReqVO.getVoucherInfo());
            pstmt.setString(6,salesid);
            pstmt.setString(7,objReqVO.getShopId());
            
            if(pstmt.executeUpdate()>0){
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

    //end 21st oct

     public boolean updateMinMaxLevel(RaiseRequestVO reqVO,Connection brmcon) throws SQLException, Exception {
        boolean status = false;
        PreparedStatement pstmt = null;
        try{
            long key = getVoucherReqKey(brmcon);
            pstmt = brmcon.prepareStatement("update WTB_BRM_SHOP_INV_LEVELS set MIN_LEVEL = ?, MAX_LEVEL = ? where INVENTORY_TYPE = ? "+
                        " and INVENTORY_SUBTYPE = ? and MAKE = ? and MODEL = ? and SHOPID = ?");
            pstmt.setString(1,reqVO.getMinLevel());
            pstmt.setString(2,reqVO.getMaxLevel());
            pstmt.setString(3,reqVO.getInventorytype());
            pstmt.setString(4,reqVO.getSubtype());
            pstmt.setString(5,reqVO.getMake());
            pstmt.setString(6,reqVO.getModel());   
            pstmt.setString(7,reqVO.getShopId());
            
            if(pstmt.executeUpdate()>0){
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
  public boolean mailSendingRequest(Connection brmcon,RaiseRequestVO reqVO) throws SQLException,Exception{
        PreparedStatement pstmt = null;
        boolean status = false;
        try{
            pstmt = brmcon.prepareStatement("INSERT INTO MAIL_SEND (MAIL_CONFIG_ID,SALESPERSONNEL_ID,MAIL_ENABLED,MODIFIED_DATE) VALUES(MAIL_CONFIG_ID.nextval,?,?,?)");
           
            pstmt.setString(1,reqVO.getSalesId());
            if(reqVO.getMailSending().equalsIgnoreCase("true")){
            pstmt.setString(2,"Y");
            } else {
               pstmt.setString(2,"N");  
            }
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(3,sqlDate);
           if(pstmt.executeUpdate()>0){
                status = true;
           }
            
        } catch(Exception e) {
          LogUtil.error("Exception:"+e.getMessage(),this.getClass()); 
        }
        return status;
    }

  public List getTransferProvList(Connection con, SalesPersonnelVO objsalesVO) throws SQLException,Exception
    {
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //int role = Integer.parseInt(objsalesVO.getSalestype());
        String query = null;
        //String country = objsalesVO.getCountry();
        String salesId = objsalesVO.getSalesId();
        //String city = objsalesVO.getCity();
       // String zone = objsalesVO.getZone();
        try{
           // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            
            pstmt = con.prepareStatement("select sh.CHILD_SALESPERSONNEL_ID,concat(concat(sd.first_name,' '),sd.last_name) from SALES_HIERARCHY sh,SALESPERSONNEL_DETAILS sd "+
                        "where sd.SALESPERSONNEL_ID = sh.CHILD_SALESPERSONNEL_ID and sh.PARENT_SALESPERSONNEL_ID = ? ");
            pstmt.setString(1,salesId);
           
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            while(rs.next())
            {   
               SalesPersonnelVO objsales = new SalesPersonnelVO();
               objsales.setChildSalesId(rs.getString(1));
               objsales.setFullname(rs.getString(2));
               objList.add(objsales);
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
  public List getAdminSalesList(Connection con, SalesPersonnelVO objsalesVO) throws SQLException,Exception
    {
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //int role = Integer.parseInt(objsalesVO.getSalestype());
        String query = null;
        //String country = objsalesVO.getCountry();
        String salesId = objsalesVO.getSalesId();
        //String city = objsalesVO.getCity();
       // String zone = objsalesVO.getZone();
        try{
           // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            
            pstmt = con.prepareStatement("select SALESPERSONNEL_ID,concat(concat(first_name,' '),last_name) from SALESPERSONNEL_DETAILS where "+
                        " SALESPERSONNEL_TYPE = 1");
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            while(rs.next())
            {   
               SalesPersonnelVO objsales = new SalesPersonnelVO();
               objsales.setChildSalesId(rs.getString(1));
               objsales.setFullname(rs.getString(2));
               objList.add(objsales);
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
    
  public List getInvList(Connection con)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       RaiseRequestVO reqVo = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
             pstmt = con.prepareStatement("select distinct(TYPE) from INV_DETAIL"); 
                rs = pstmt.executeQuery();
            while(rs.next()){
               reqVo = new RaiseRequestVO();
               reqVo.setInventorytype(rs.getString(1));
               objList.add(reqVo);
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
  public List getSubtypeList(Connection con,RaiseRequestVO objReqVO)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       RaiseRequestVO reqVo = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
             pstmt = con.prepareStatement("select distinct(SUBTYPE) from INV_DETAIL where UPPER(TYPE) = UPPER(?)"); 
             pstmt.setString(1,objReqVO.getInventorytype());
                rs = pstmt.executeQuery();
            while(rs.next()){
               reqVo = new RaiseRequestVO();
               reqVo.setSubtype(rs.getString(1));
               objList.add(reqVo);
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
