/*
 * WitribeInventoryBO.java
 *
 * Created on February 18, 2009, 4:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.bo;

import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.dao.RaiseRequestDAO;
import com.witribe.inventory.vo.DistributeInventoryVO;
import com.witribe.inventory.vo.RaiseRequestVO;
import com.witribe.inventory.vo.VoucherVO;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.util.DBUtil;
import com.witribe.util.LogUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author SC43278
 */
public class WitribeInventoryBO {
    
    /** Creates a new instance of WitribeInventoryBO */
    public WitribeInventoryBO() {
    }
    public List createRequest(RaiseRequestVO objRequestVO,String salesid) throws SQLException, Exception{
        
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        Connection crmcon = null;
        boolean status = false;
        List statusList = new ArrayList();
        try{
            con = DBUtil.getBRMConnection();
            crmcon = DBUtil.getConnection();
            status = objRequestDAO.createRequest(objRequestVO, con,salesid,crmcon);
            if(status) {
                //   statusList = SendMailSms(WitribeConstants.MAIL_SUBJECT_REQ1,WitribeConstants.MAIL_BODY_REQ1);
            }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(crmcon);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(crmcon);
            throw e;
        }  finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(crmcon);
        }
        return statusList;
    }
     public boolean inventoryLevelRequest(RaiseRequestVO objRequestVO,String salesid) throws SQLException, Exception{
        
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        Connection crmcon = null;
        boolean status = false;
        //List statusList = new ArrayList();
        try{            
            con = DBUtil.getBRMConnection();
            crmcon = DBUtil.getConnection();
            status = objRequestDAO.inventoryLevelRequest(objRequestVO, con,salesid,crmcon);
            if(status) {
             //   statusList = SendMailSms(WitribeConstants.MAIL_SUBJECT_REQ1,WitribeConstants.MAIL_BODY_REQ1);
            }
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }  finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(crmcon);
        }
        return status;
    }
     
    public boolean updateMinMaxLevel(RaiseRequestVO objRequestVO) throws SQLException, Exception{
        
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        Connection crmcon = null;
        boolean status = false;
        //List statusList = new ArrayList();
        try{            
            con = DBUtil.getBRMConnection();
            //crmcon = DBUtil.getConnection();
            status = objRequestDAO.updateMinMaxLevel(objRequestVO, con);
            if(status) {
             //   statusList = SendMailSms(WitribeConstants.MAIL_SUBJECT_REQ1,WitribeConstants.MAIL_BODY_REQ1);
            }
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }  finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(crmcon);
        }
        return status;
    }
    public List getRequestStatus(int pageNum,String salesid) throws SQLException,Exception{
        List objList = null;
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            objList = objRequestDAO.getRequestStatus(con,pageNum,salesid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
    public List getCSEInventory(int pageNum,String salesid) throws SQLException,Exception{
        List objList = null;
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            objList = objRequestDAO.getCSEInventory(con,pageNum,salesid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List fetchShopInventory(DistributeInventoryVO objInventoryVO,String shopid) throws SQLException,Exception{
        List objList = null;
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            objList = objRequestDAO.fetchShopInventory(con,objInventoryVO,shopid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
    public List fetchInventoryList(DistributeInventoryVO objInventoryVO,String shopid) throws SQLException,Exception{
        List objList = null;
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        Connection crmcon = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            crmcon = DBUtil.getConnection();
            objList = objRequestDAO.fetchInventoryList(con,objInventoryVO,shopid,crmcon);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        }finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
    public List searchInventoryList(DistributeInventoryVO objInventoryVO,String searchinv) throws SQLException,Exception{
        List objList = null;
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        Connection crmcon = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            crmcon = DBUtil.getConnection();
            objList = objRequestDAO.searchInventoryList(con,objInventoryVO,searchinv,crmcon);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        }finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(crmcon);
        }
        return objList;
    }
    public List fetchCSETOShop(RaiseRequestVO objReqVO,String shopid) throws SQLException,Exception{
        List objList = null;
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objRequestDAO.fetchCSETOShop(con,objReqVO,shopid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List fetchReturnInventory(RaiseRequestVO objInventoryVO,String shopid) throws SQLException,Exception{
        List objList = null;
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            objList = objRequestDAO.fetchReturnInventory(con,objInventoryVO,shopid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        }finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List fetchTransInventory(RaiseRequestVO objInventoryVO,String shopid) throws SQLException,Exception{
        List objList = null;
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            objList = objRequestDAO.fetchTransInventory(con,objInventoryVO,shopid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        }finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List fetchInventory(DistributeInventoryVO objInventoryVO,String shopid) throws SQLException,Exception{
        List objList = null;
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            objList = objRequestDAO.fetchInventory(con,objInventoryVO,shopid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        }finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List requestVoucherInfo(String resType)throws Exception {
        List objList = new ArrayList();
        RaiseRequestDAO requestDAO = new RaiseRequestDAO();
        Connection brmcon = null;
        try{
            brmcon = DBUtil.getBRMConnection();
            objList = requestDAO.requestVoucherInfo(brmcon,resType);
        }catch(SQLException se){
            DBUtil.closeConnection(brmcon);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(brmcon);
            throw e;
        } finally{
            DBUtil.closeConnection(brmcon);
        }
        return objList;
    }
    //Added by bhawna on 21st oct
    public List createVoucherRequest(VoucherVO objVouchVO,String salesid) throws SQLException, Exception{
        
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection brmcon = null;
        boolean status = false;
        List statusList = new ArrayList();
        try{
            brmcon = DBUtil.getBRMConnection();
            status = objRequestDAO.createVoucherRequest(brmcon,objVouchVO,salesid);
            
        }catch(SQLException se){
            DBUtil.closeConnection(brmcon);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(brmcon);
            throw e;
        }  finally{
            DBUtil.closeConnection(brmcon);
        }
        return statusList;
    }
    //end 21st oct
    public List requestShopsId(String salesid)throws Exception{
        List objList = new ArrayList();
        RaiseRequestDAO requestDAO = new RaiseRequestDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = requestDAO.requestShopsId(con,salesid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }

    public List reqShopsId(String salesid)throws Exception{
        List objList = new ArrayList();
        RaiseRequestDAO requestDAO = new RaiseRequestDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = requestDAO.reqShopsId(con,salesid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }

     public List getTypeInv(RaiseRequestVO reqVO)throws Exception{
        List objList = new ArrayList();
        RaiseRequestDAO requestDAO = new RaiseRequestDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = requestDAO.getTypeInv(con,reqVO);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }

    public List getTypeInvMake(RaiseRequestVO reqVO)throws Exception{
        List objList = new ArrayList();
        RaiseRequestDAO requestDAO = new RaiseRequestDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = requestDAO.getTypeInvMake(con,reqVO);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
 /* public List getSpecificTypeInv(RaiseRequestVO reqVO)throws Exception{
        List objList = new ArrayList();
        RaiseRequestDAO requestDAO = new RaiseRequestDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = requestDAO.getSpecificTypeInv(con,reqVO);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }*/
        public List fetchAssignedSales(String shopid,String salesid) throws SQLException,Exception{

        List objList = null;
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objRequestDAO.fetchAssignedSales(con,shopid,salesid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
    public boolean distributeRequest(DistributeInventoryVO objInventoryVO) throws SQLException, Exception{
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            status = objRequestDAO.distributeRequest(objInventoryVO, con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;     
    }
    public List ReturnRequest(RaiseRequestVO objInventoryVO,String SalesPersonId) throws SQLException, Exception{
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        Connection brmcon = null;
        boolean status = false;
        List statusList = new ArrayList();
        try{
            brmcon = DBUtil.getBRMConnection();
            con = DBUtil.getConnection();
            //con = DBUtil.getConnection();
            //made DB as BRM for inventory.
            status = objRequestDAO.ReturnRequest(objInventoryVO, brmcon,SalesPersonId,con);
            if(status) {
                // statusList = SendMailSms(WitribeConstants.MAIL_SUBJECT_REQ2,WitribeConstants.MAIL_BODY_REQ2);
            }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
            
        }
        return statusList;
    }

    public List getMinMaxLevel(RaiseRequestVO objReq)throws Exception{
         RaiseRequestDAO requestDAO = new RaiseRequestDAO();
         //RaiseRequestVO objReqVO = null;
         List objListShop = null;
        Connection con = null;
        try{
            con = DBUtil.getBRMConnection();
            objListShop = requestDAO.minMaxLevel(con,objReq);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objListShop;
    }
    
    public List transferRequest(RaiseRequestVO objInventoryVO) throws SQLException, Exception{
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        Connection brmcon = null;
        boolean status = false;
        List statusList = new ArrayList();
        try{
            brmcon = DBUtil.getBRMConnection();
            con = DBUtil.getConnection();
            //con = DBUtil.getConnection();
            status = objRequestDAO.transferRequest(objInventoryVO, brmcon,con);
            if(status) {
                //  statusList = SendMailSms(WitribeConstants.MAIL_SUBJECT_REQ2,WitribeConstants.MAIL_BODY_REQ2);
            }
        }catch(SQLException se){
            DBUtil.closeConnection(brmcon);
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(brmcon);
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
        }
        return statusList;
    }
    
    
    public boolean changeInvStatus(DistributeInventoryVO objInventoryVO) throws SQLException, Exception{
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            status = objRequestDAO.changeInvStatus(objInventoryVO, con);
            /*if(!assignCSE(objLeadVO,con)){
              assignTL(objLeadVO,con); }
             sendMail()*/
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    public boolean mailSendingRequest(RaiseRequestVO reqVO) throws SQLException, Exception{
       RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            status = objRequestDAO.mailSendingRequest(con,reqVO);
            /*if(!assignCSE(objLeadVO,con)){
              assignTL(objLeadVO,con); }
             sendMail()*/
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
    public boolean transferCSE(RaiseRequestVO objInventoryVO) throws SQLException, Exception{
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        Connection salescon = null;
        List objList = null;
        RaiseRequestVO objInventory = null;
        boolean status = false;
        try{
            con = DBUtil.getBRMConnection();
            salescon = DBUtil.getConnection();
            objList =objRequestDAO.fetchCSETransInventory(con,objInventoryVO.getShopId(),objInventoryVO.getSalesId());
            String []InvArry = new String[objList.size()];
            int invcounter = 0;
            WitribeSalesBO salesbo = new WitribeSalesBO();
            status = salesbo.updateShopforSalesHierarchy(salescon,objInventoryVO.getSalesId(),objInventoryVO.getToShop());
            for (int i=0;i<objList.size();i++) {
                objInventory = (RaiseRequestVO)objList.get(i);
                InvArry[i]=objInventory.getInventoryId()+"*"+objInventory.getInventorytype()+"*"+objInventory.getChangeStatus()+"*"+objInventory.getMacaddrwan()+"*"+objInventory.getUserdefined4();
            }
            objInventoryVO.setInventoryIdArray(InvArry);
            if(objList.size()>0 && status) {
                con.setAutoCommit(false);
                status = objRequestDAO.updateInvStatusofCSE(con,objInventoryVO.getSalesId(),objInventoryVO.getShopId());
            }
            if(status) {
                status = objRequestDAO.transferRequest(objInventoryVO,con,salescon);
                
            }
            /*if(!assignCSE(objLeadVO,con)){
              assignTL(objLeadVO,con); }
             sendMail()*/
        }catch(SQLException se){
            con.rollback();
            salescon.rollback();
            salescon.setAutoCommit(true);
            con.setAutoCommit(true);
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            con.rollback();
            salescon.rollback();
            salescon.setAutoCommit(true);
            con.setAutoCommit(true);
            DBUtil.closeConnection(con);
            throw e;
        }finally {
            con.setAutoCommit(true);
            salescon.rollback();
            salescon.setAutoCommit(true);
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(salescon);
        }
        return status;
    }
    public List SendMailSms(String subject,String content,Connection brmcon,String smsContent) throws Exception {
        boolean smsStatus = false;
        RaiseRequestDAO requestDao = new RaiseRequestDAO();
        List statusList = new ArrayList();
        Properties lslpProps = new Properties();
        lslpProps.load(this.getClass().getClassLoader().getResourceAsStream("Lslp.properties"));
        String toMail = (String)lslpProps.get("mail.finance");
        String toContact = (String)lslpProps.get("contact.finance");
        try {
            //MailUtil.sendMail("",toMail,subject,content);
            WitribeBO wbo = new WitribeBO();
            wbo.sendmail("satyam@wi-tribe.pk",toMail,subject,content);
        } catch (Exception e) {
            LogUtil.error("Exception "+e.getMessage(),this.getClass());
            statusList.add(WitribeConstants.MAIL_FAILED);
        }
        try{
            smsStatus = requestDao.sendMessage(brmcon,smsContent,subject,toContact);
        } catch(Exception e) {
            LogUtil.error("Exception "+e.getMessage(),this.getClass(),e);
        }
        
               /* SMSUtil  sms = new SMSUtil(WitribeConstants.MODULE_NAME);
                 try {
                    // sms.sendMessage("now"/YYYY-MM-DD hh:mm:ss","Message","subject","to number");
                    smsStatus = sms.sendMessage("now",content,subject,toContact);
                } catch (Exception e) {
                    LogUtil.error("Exception "+e.getMessage(),this.getClass(),e);
                
                }
                if(!smsStatus) {
                    if(sms != null) {
                        String []res = sms.getMessageResponses();
                        if(res != null && res.length > 0 && res[0] != null) {
                            statusList.add(WitribeConstants.SMS_FAILED+" "+res[0].split(":")[1]);
                        } else {
                            statusList.add(WitribeConstants.SMS_FAILED);
                        }
                    } else {
                        statusList.add(WitribeConstants.SMS_FAILED);
                    }
                }*/
        
        
        return statusList;
    }
    public List getInvList() throws SQLException, Exception{
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        Connection brmcon = null;
        List statusList = new ArrayList();
        try{
            //brmcon = DBUtil.getBRMConnection();
            con = DBUtil.getConnection();
            //con = DBUtil.getConnection();
            statusList = objRequestDAO.getInvList(con);
            
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
        }
        return statusList;
    }
    
    public List getSubtypeList(RaiseRequestVO objInventoryVO) throws SQLException, Exception{
        RaiseRequestDAO objRequestDAO = new RaiseRequestDAO();
        Connection con = null;
        Connection brmcon = null;
        List statusList = new ArrayList();
        try{
            //brmcon = DBUtil.getBRMConnection();
            con = DBUtil.getConnection();
            //con = DBUtil.getConnection();
            statusList = objRequestDAO.getSubtypeList(con,objInventoryVO);
            
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
        }
        return statusList;
    }
}
