/*
 * WitribeBO.java
 *
 * Created on January 8, 2009, 10:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.bo;



import java.io.IOException;
import com.witribe.constants.WitribeConstants;
import com.witribe.ctutil.CTUtil;
import com.witribe.dao.HotspotDAO;
import com.witribe.dao.LeadDAO;
import com.witribe.dao.ProspectDAO;
import com.witribe.dao.UserDAO;
import com.witribe.exception.DuplicateUserException;
import com.witribe.util.DBUtil;
import com.witribe.vo.HotspotVO;
import com.witribe.vo.LeadVO;
import com.witribe.vo.ProspectVO;
import com.witribe.vo.UserRegVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.witribe.dao.ProvinceDAO;
import com.witribe.dao.CityDAO;
import com.witribe.dao.ZoneDAO;
import com.witribe.dao.SubzoneDAO;
import com.witribe.inventory.dao.RaiseRequestDAO;
import com.witribe.sales.dao.SalesPersonnelDAO;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadHistoryVO;
import java.util.ArrayList;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author SC43278
 */
public class WitribeBO {
    
    /** Creates a new instance of WitribeBO */
    public WitribeBO() {
    }
    public List createLead(LeadVO objLeadVO, String salesId, String salespersonId) throws SQLException, Exception{
        
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        Connection brmcon = null;
        List statusList = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            brmcon = DBUtil.getBRMConnection();
            //objLeadVO.setAddress();
            objLeadVO.setAssignedCSE("");
            objLeadVO.setAssignedTL("");
            String  unAssign= WitribeConstants.UNASSIGNED;
            objLeadVO.setLeadStatus(unAssign);
            if(salesId==null)
            {
                objLeadVO = objLeadDAO.assignCSE(objLeadVO,con);
            }
            else
            {
                objLeadVO = objLeadDAO.assignSelfCSE(objLeadVO,salesId,con);
                 if(objLeadVO.getAssignedCSE().equals("")){
                        objLeadVO = objLeadDAO.assignCSE(objLeadVO,con);
                    }
               /* objLeadVO.setAssignedCSE(cse);
                objLeadVO.setLeadStatus(WitribeConstants.ASSIGNED);
                objLeadVO = objLeadDAO.fetchMailandNumber(objLeadVO,con);*/
            }
            if(objLeadVO.getAssignedCSE().equals("")){
                objLeadVO = objLeadDAO.assignTL(objLeadVO,con);
             }
           status = objLeadDAO.createLead(objLeadVO, con,salespersonId);
           if(status){
             statusList =  SendMailToSales(objLeadVO,con);
            }
            /*if(!assignCSE(objLeadVO,con)){
              assignTL(objLeadVO,con); }
             sendMail()*/
        }catch(SQLException se){
           DBUtil.closeConnection(con);
           DBUtil.closeConnection(brmcon);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
           DBUtil.closeConnection(brmcon);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
        }
        return statusList;
    }
    public LeadVO checkDuplicateLead(LeadVO objLeadVO) throws SQLException,Exception{
      LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        LeadVO duplicateLeadVo = null;
        try{
            con = DBUtil.getConnection();
            duplicateLeadVo = objLeadDAO.checkDuplicateLead(objLeadVO,con);
            
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return duplicateLeadVo;  
    }
   public List assignSalesPerson(LeadVO objLeadVO) throws SQLException, Exception{
        
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        Connection brmcon = null;
        boolean status = false;
        List statusList = null;
        try{
            con = DBUtil.getConnection();
            brmcon = DBUtil.getBRMConnection();
            status = objLeadDAO.updateSalesPerson(objLeadVO,con);
            if(status) {
             statusList = SendMailToSales(objLeadVO,con);   
            }
        }catch(SQLException se){
           DBUtil.closeConnection(con);
           DBUtil.closeConnection(brmcon);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
           DBUtil.closeConnection(brmcon);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
        }
        return statusList;
    }
    public boolean updateLeadStatus(LeadVO objLeadVO) throws SQLException, Exception{
        
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objLeadDAO.updateLeadStatus(objLeadVO,con);
            
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
   /* public List getLeads() throws SQLException,Exception{
        List objList = null;
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objLeadDAO.getLeads(con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }*/
    public List getNextLeads(SalesPersonnelVO objSalesVO,int pageCount) throws SQLException,Exception{
        List objList = null;
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objLeadDAO.getNextLeads(con,objSalesVO,pageCount);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
   public List  getNextAccountLeads(LeadVO objLeadVO,SalesPersonnelVO ObjSalesVO) throws SQLException,Exception{
        List objList = null;
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objLeadDAO.getNextAccountLeads(con,objLeadVO,ObjSalesVO);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List  getSearchedLeads(LeadVO objLeadVO,SalesPersonnelVO objsalesVO) throws SQLException,Exception{
        List objList = null;
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objLeadDAO.getSearchedLeads(con,objLeadVO,objsalesVO);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
     public List  getSearchedLeadsBy(LeadVO objLeadVO,String searchlead,SalesPersonnelVO salesVO) throws SQLException,Exception{
        List objList = null;
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objLeadDAO.getSearchedLeadsBy(con,objLeadVO,searchlead,salesVO);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
     public List getLeadsToReassign(SalesPersonnelVO objSalesVO,int pageCount) throws SQLException,Exception{
        List objList = null;
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objLeadDAO.getLeadsToReassign(con,objSalesVO,pageCount);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List getNextUsers(int pageCount) throws SQLException,Exception{
        List objList = null;
        UserDAO objUserDAO = new UserDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objUserDAO.getNextUsers(con,pageCount);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
    public LeadVO getLead(String leadId) throws SQLException,Exception{
        LeadVO objLeadVO = null;
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objLeadVO = objLeadDAO.getLead(leadId, con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objLeadVO;
    }
    
    public boolean updateLeadAddress(LeadVO objLeadVO) throws SQLException,Exception{
        boolean stat = false;
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            stat= objLeadDAO.updateLeadAddress(objLeadVO,con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return stat;
    }
    public UserRegVO getUser(String userId) throws SQLException,Exception{
        UserRegVO objUserVO = null;
        UserDAO objUserDAO = new UserDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objUserVO = objUserDAO.getUser(userId, con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objUserVO;
    }
    
    public List getUsers() throws SQLException,Exception{
        List objList = null;
        UserDAO objUserDAO = new UserDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objUserDAO.getUsers(con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
        
      }
    
    public boolean createProspect(ProspectVO objProspectVO) throws SQLException, Exception{
        
        ProspectDAO objProspectDAO = new ProspectDAO();
        Connection con = null;
        boolean status = false;
        con.setAutoCommit(false);
        try{
            con = DBUtil.getConnection();
            status = objProspectDAO.createProspect(objProspectVO, con);
            con.commit();
        }catch(SQLException se){
           con.rollback();
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           con.rollback();
           DBUtil.closeConnection(con);
           throw e;
        }
        finally{
            con.setAutoCommit(true);
            DBUtil.closeConnection(con);
        }
        return status;
    }
    //Added by Swapna on sep 9th
    public boolean createLeadHistory(ProspectVO objProspectVO) throws SQLException, Exception{
        
        ProspectDAO objProspectDAO = new ProspectDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objProspectDAO.createLeadHistory(objProspectVO, con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }// ended
    public boolean updateAccountInfo(LeadVO objLeadVO) throws SQLException, Exception{
        
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objLeadDAO.updateAccountInfo(objLeadVO, con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
    public boolean loginHotspot(HotspotVO objHotspotVO) throws SQLException, Exception{
        
        HotspotDAO objHotspotDAO = new HotspotDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objHotspotDAO.loginHotspot(objHotspotVO, con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
    public List getProspects() throws SQLException,Exception{
        List objList = null;
        ProspectDAO objProspectDAO = new ProspectDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
           // objList = objProspectDAO.getProspects(con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }

	public List getPageProspects(int pageNum,String salesid) throws SQLException,Exception{
        List objList = null;
        ProspectDAO objProspectDAO = new ProspectDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objProspectDAO.getPageProspects(pageNum,con,salesid);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
        public boolean createUser(UserRegVO objUserRegVO) throws SQLException,DuplicateUserException, Exception{
        
        UserDAO objUserDAO = new UserDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objUserDAO.createUser(objUserRegVO, con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
             throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
        
     /*   public UserRegVO validateUser(UserRegVO objUserRegVO) throws SQLException,DuplicateUserException, Exception{
        
        UserDAO objUserDAO = new UserDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objUserRegVO = objUserDAO.validateUser(objUserRegVO, con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
             throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
      return objUserRegVO;
    }*/
        public SalesPersonnelVO validateUser(SalesPersonnelVO objSalesVO) throws SQLException,DuplicateUserException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objSalesVO = objSalesDAO.validateUser(objSalesVO, con);
            if(WitribeConstants.TYPE_ADMIN.equals(objSalesVO.getSalestype())) {
                objSalesVO.setNsmAvailable(objSalesDAO.isNsmAvailable(con));
            }
         }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
             throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objSalesVO;
    }
     public SalesPersonnelVO validateForgotpwd(SalesPersonnelVO objSalesVO) throws SQLException,DuplicateUserException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objSalesVO = objSalesDAO.validateForgotpwd(objSalesVO, con);
            /*if(WitribeConstants.TYPE_ADMIN.equals(objSalesVO.getSalestype())) {
                objSalesVO.setNsmAvailable(objSalesDAO.isNsmAvailable(con));
            }*/
         }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
             throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objSalesVO;
    }
    public List GetChildSalesList(SalesPersonnelVO objSales) throws SQLException,Exception{
        List objList = null;
        LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = objLeadDAO.GetChildSalesList(objSales,con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
     
        public List getProvinces()throws SQLException,Exception{
            
        List objList = null;
        ProvinceDAO objProvinceDAO = new ProvinceDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objProvinceDAO.getProvinces(con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
            
        }
        
        public List getCities()throws SQLException,Exception{
         
            List objList = null;
            CityDAO objCityDAO = new CityDAO();
            Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objCityDAO.getCities(con);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
        }
        //Added by swapna, Changes in Lead Source and Current ISP
         public List getLeadSource()throws SQLException,Exception{
            
            List objList = null;
            LeadDAO objsourceDAO = new LeadDAO();
            Connection con = null;
            
             try{
                con = DBUtil.getConnection();
                objList = objsourceDAO.getLeadSource(con);
            }catch(SQLException se){
               DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
               DBUtil.closeConnection(con);
                throw e;
            }
            finally{
                DBUtil.closeConnection(con);
            }
            return objList;

            }
          public List getISPList()throws SQLException,Exception{
            
            List objList = null;
            LeadDAO objsourceDAO = new LeadDAO();
            Connection con = null;
            
             try{
                con = DBUtil.getConnection();
                objList = objsourceDAO.getISPList(con);
            }catch(SQLException se){
               DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
               DBUtil.closeConnection(con);
                throw e;
            }
            finally{
                DBUtil.closeConnection(con);
            }
            return objList;

            }
         
         // Ended
          
          
      public List getProspectReason()throws SQLException,Exception{
            
            List objList = null;
            ProspectDAO objreasonDAO = new ProspectDAO();
            Connection con = null;
            
             try{
                con = DBUtil.getConnection();
                objList = objreasonDAO.getProspectReason(con);
            }catch(SQLException se){
               DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
               DBUtil.closeConnection(con);
                throw e;
            }
            finally{
                DBUtil.closeConnection(con);
            }
            return objList;

            }
      
          public LeadHistoryVO getTransState(LeadHistoryVO transVO)throws SQLException,Exception{
            
            LeadHistoryVO transState = null;
            LeadDAO objLeadDAO = new LeadDAO();
            Connection con = null;
            
             try{
                con = DBUtil.getConnection();
                transState = objLeadDAO.getTransState(con,transVO);
            }catch(SQLException se){
               DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
               DBUtil.closeConnection(con);
                throw e;
            }
            finally{
                DBUtil.closeConnection(con);
            }
            return transState;

            }
       public List getSubAddress(String type, String address,String flag)throws SQLException,Exception{
         
            List objList = null;
            CityDAO objCityDAO = new CityDAO();
            Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objCityDAO.getSubAddress(con,type,address,flag);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
        } 
       
      public List getSubAddressByRole(String type,String flag,List objlst,SalesPersonnelVO objsale)throws SQLException,Exception{
         
            List objList = null;
            CityDAO objCityDAO = new CityDAO();
            Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objCityDAO.getSubAddressByRole(con,type,flag,objlst,objsale);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
        } 
    public List getTransferProvList(SalesPersonnelVO objsales)throws SQLException,Exception{
            List objList = null;
            RaiseRequestDAO objProvDAO = new RaiseRequestDAO();
            Connection con = null;
            boolean status = false;
            try{
                con = DBUtil.getConnection();
                objList = objProvDAO.getTransferProvList(con,objsales);
            }catch(SQLException se){
               DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
               DBUtil.closeConnection(con);
                throw e;
            }
            finally{
                DBUtil.closeConnection(con);
            }
            return objList;
            }
    
        public List getAdminSalesList(SalesPersonnelVO objsales)throws SQLException,Exception{
            List objList = null;
            RaiseRequestDAO objProvDAO = new RaiseRequestDAO();
            Connection con = null;
            boolean status = false;
            try{
                con = DBUtil.getConnection();
                objList = objProvDAO.getAdminSalesList(con,objsales);
            }catch(SQLException se){
               DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
               DBUtil.closeConnection(con);
                throw e;
            }
            finally{
                DBUtil.closeConnection(con);
            }
            return objList;
            }
      public List getCityList(SalesPersonnelVO objsales)throws SQLException,Exception{
         
            List objList = null;
            CityDAO objCityDAO = new CityDAO();
            Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objCityDAO.getCityList(con,objsales);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
        } 
      public List getZoneList(SalesPersonnelVO objsales)throws SQLException,Exception{
         
            List objList = null;
            CityDAO objCityDAO = new CityDAO();
            Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objCityDAO.getZoneList(con,objsales);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
        } 
      public List getSubzoneList(SalesPersonnelVO objsales)throws SQLException,Exception{
         
            List objList = null;
            CityDAO objCityDAO = new CityDAO();
            Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objCityDAO.getSubzoneList(con,objsales);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
        } 
       public List getZones()throws SQLException, Exception{
            List objList = null;
            ZoneDAO objZoneDAO = new ZoneDAO();
            Connection con = null;
            boolean status = false;
            try{
                con = DBUtil.getConnection();
                objList = objZoneDAO.getZones(con);
            }catch(SQLException se){
               DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
               DBUtil.closeConnection(con);
                throw e;
            }
            finally{
                DBUtil.closeConnection(con);
            }
            return objList;
            }
        
        public List getSubzones()throws SQLException, Exception{
            
            List objList = null;
            SubzoneDAO objSubzoneDAO = new SubzoneDAO();
            Connection con = null;
            boolean status = false;
            try{
                con = DBUtil.getConnection();
                objList = objSubzoneDAO.getSubzones(con);
            }catch(SQLException se){
               DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
               DBUtil.closeConnection(con);
                throw e;
            }
            finally{
                DBUtil.closeConnection(con);
            }
            return objList;
            }
    public String SalesType(String sales_type)
    {
     WitribeConstants wc = new WitribeConstants();
     int st = Integer.parseInt(sales_type);
     switch(st)
     {
         case 0:
             return WitribeConstants.ADMIN;
         case 1:
             return WitribeConstants.SALES_DIRECTOR;

         case 2:
             return WitribeConstants.RSM;

         case 3:
             return WitribeConstants.TL;

         case 4:
             return WitribeConstants.CSE;

         case 5:
             return WitribeConstants.BO;

         case 6:
             return WitribeConstants.NBO;

         /*case 7:
             return "KIOSKS";*/
             
         default:
             break;
      }
     return null;
    }

    public List SendMailToSales(LeadVO objLeadVO,Connection brmcon) throws Exception {
        boolean smsStatus = false;
        boolean mailStatus = false;
        LeadDAO ldao = new LeadDAO();
        List statusList = new ArrayList();
        String salesEmail = objLeadVO.getSalesEmail();
        if(salesEmail != null && !"".equals(salesEmail)){
             StringBuffer mailBody = new StringBuffer();
              try {
             mailStatus = ldao.sendMail(brmcon,mailBody,objLeadVO);
             //Commented code for mail send through Email listener.
               /*
                mailBody.append(WitribeConstants.MAIL_BODY_LEAD);
                mailBody.append(WitribeConstants.NEW_LINE);
                mailBody.append(WitribeConstants.MAIL_BODY_LEAD_ID);
                mailBody.append(objLeadVO.getLeadId());
                mailBody.append(WitribeConstants.NEW_LINE);
                mailBody.append(WitribeConstants.MAIL_BODY_LEAD_NAME);
                mailBody.append(objLeadVO.getFirstname()+" "+objLeadVO.getLastname());
                mailBody.append(WitribeConstants.NEW_LINE);
                mailBody.append(WitribeConstants.MAIL_BODY_LEAD_CONTACT);
                mailBody.append(objLeadVO.getContactnumber());
                mailBody.append(WitribeConstants.NEW_LINE);
                mailBody.append(WitribeConstants.MAIL_BODY_LEAD_SUBZONE);
                mailBody.append(objLeadVO.getSubzone());
                mailBody.append(WitribeConstants.NEW_LINE);
                mailBody.append(WitribeConstants.MAIL_BODY_LEAD_ZONE);
                mailBody.append(objLeadVO.getZone());
                mailBody.append(WitribeConstants.NEW_LINE);
                mailBody.append(WitribeConstants.MAIL_BODY_LEAD_CITY);
                mailBody.append(objLeadVO.getCity());
                try {
                //MailUtil.sendMail("",objLeadVO.getSalesEmail(),WitribeConstants.MAIL_SUBJECT_LEAD,mailBody.toString());
                   // sendmail("satyam@wi-tribe.pk",objLeadVO.getSalesEmail(),WitribeConstants.MAIL_SUBJECT_LEAD,mailBody.toString());
                  //  Emailer email = new Emailer();
                   // email.sendEmail("satyam@wi-tribe.pk",objLeadVO.getSalesEmail(),WitribeConstants.MAIL_SUBJECT_LEAD,mailBody.toString());
                     sendmail("satyam@wi-tribe.pk",objLeadVO.getSalesEmail(),WitribeConstants.MAIL_SUBJECT_LEAD,mailBody.toString()+WitribeConstants.MAIL_FROM);*/
                 } catch (Exception e) {
                     LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
                     e.printStackTrace();
                     statusList.add(WitribeConstants.MAIL_FAILED);
                }
                smsStatus = ldao.sendMsg(brmcon,mailBody,objLeadVO);
               /* SMSUtil  sms = new SMSUtil(WitribeConstants.MODULE_NAME);
                 try {
                    // sms.sendMessage("now"/YYYY-MM-DD hh:mm:ss","Message","subject","to number");
                    smsStatus = sms.sendMessage("now",mailBody.toString().replaceAll(WitribeConstants.NEW_LINE," "),WitribeConstants.MAIL_SUBJECT_LEAD,objLeadVO.getSalesContact());
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
               
            }
                
        return statusList;
    }
    
    
    public String CheckCoverage(String house, String streetno, String zone, String city)
    {
        String data=null;
        try {
            CTUtil ct = new CTUtil();
            data = ct.checkCoverage(house, streetno, zone, city);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return data;
    
    
    }
    public ProspectVO getTransitionState(ProspectVO objProspectVO) throws Exception{
        ProspectVO transitionState = null;
        Connection con = null;
        ProspectDAO reasonDAO = new ProspectDAO();
        try{
            con = DBUtil.getConnection();
            transitionState = reasonDAO.getTransitionState(con,objProspectVO);
            
        } catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
             throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return transitionState;
    }
    public boolean sendmail(String from,String to,String subject,String content)
    {
        Properties props = System.getProperties();
        Properties prop = new Properties();
        String host = null;
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream("Lslp.properties"));
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    // Setup mail server
        host = prop.getProperty("smtp.host");
   // props.put("mail.smtp.host", "10.1.82.10");
    props.put("mail.smtp.host",host);    
    props.put("mail.smtp.auth", "true");
    props.put("mail.debug", "true");
   // if(from==null)
        from=prop.getProperty("smtp.from");

    // Get session
    Session session = Session.getDefaultInstance(props, null);
    session.setDebug(true);

    // Define message
    MimeMessage message = new MimeMessage(session);
        try {

            // Set the from address
            message.setFrom(new InternetAddress(from));

            // Set the to address
            message.addRecipient(Message.RecipientType.TO, 
              new InternetAddress(to));

            // Set the subject
            message.setSubject(subject);

            // Set the content
            message.setText(content);

    // Send message
            Transport transport = session.getTransport("smtp");
            String username = prop.getProperty("smtp.user");
            String password = prop.getProperty("smtp.pass");
                transport.connect(host,username,password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();            
        } catch (AddressException ex) {
            ex.printStackTrace();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }            
    //Transport.send(message);
    System.out.println("Done..sending mail");
    return true;
    }
       
    
     public boolean sendmail(String from,String to,String subject,String content, String cc)
    {
        Properties props = System.getProperties();
        Properties prop = new Properties();
        String host = null;
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream("Lslp.properties"));
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    // Setup mail server
        host = prop.getProperty("smtp.host");
   // props.put("mail.smtp.host", "10.1.82.10");
    props.put("mail.smtp.host",host);    
    props.put("mail.smtp.auth", "true");
    props.put("mail.debug", "true");
   // if(from==null)
        from=prop.getProperty("smtp.from");

    // Get session
    Session session = Session.getDefaultInstance(props, null);
    session.setDebug(true);

    // Define message
    MimeMessage message = new MimeMessage(session);
        try {

            // Set the from address
            message.setFrom(new InternetAddress(from));

            // Set the to address
            message.addRecipient(Message.RecipientType.TO, 
              new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC,
              new InternetAddress(cc));

            // Set the subject
            message.setSubject(subject);

            // Set the content
            message.setText(content);

    // Send message
            Transport transport = session.getTransport("smtp");
            String username = prop.getProperty("smtp.user");
            String password = prop.getProperty("smtp.pass");
                transport.connect(host,username,password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();            
        } catch (AddressException ex) {
            ex.printStackTrace();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }            
    //Transport.send(message);
    System.out.println("Done..sending mail");
    return true;
    }
  public List getLeadStatusHistory(LeadHistoryVO objLeadHistory) throws SQLException,Exception{
      LeadDAO objLeadDAO = new LeadDAO();
        Connection con = null;
       List statusList = new ArrayList();
        try{
            con = DBUtil.getConnection();
            statusList = objLeadDAO.getLeadStatusHistory(objLeadHistory,con);
                 
        }catch(SQLException se){
           DBUtil.closeConnection(con);
           
           se.printStackTrace();
           
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
           e.printStackTrace();
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return statusList;  
    }
}
