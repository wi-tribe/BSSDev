/*
 * CommissionBO.java
 *
 * Created on October 23, 2009, 12:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.commission.bo;

import com.witribe.commission.DAO.CommissionPlanDAO;
import com.witribe.commission.vo.CommissionPlanVO;
import com.witribe.util.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author SC43278
 */
public class CommissionBO {
    
    /** Creates a new instance of CommissionBO */
    public CommissionBO() {
    }
  public List getChannelList() throws SQLException,Exception{
        CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        List channelList = null;
        try{
            con = DBUtil.getConnection();
            channelList = objCommDAO.getChannelList(con);
            
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
        return channelList;  
    }
  
 public List getCityList() throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        List cityList = null;
        try{
            con = DBUtil.getConnection();
            cityList = objCommDAO.getCityList(con);
            
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
        return cityList;  
    }
   public List getZoneList(CommissionPlanVO objCommVO) throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        List cityList = null;
        try{
            con = DBUtil.getConnection();
            cityList = objCommDAO.getZoneList(con,objCommVO);
            
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
        return cityList;  
    }
   
   public List getProductList(CommissionPlanVO objCommVO) throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        List cityList = null;
        try{
            con = DBUtil.getConnection();
            cityList = objCommDAO.getProductList(con,objCommVO);
            
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
        return cityList;  
    }
   public boolean createPlan(CommissionPlanVO objCommVO,HttpServletRequest req) throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        boolean status = false;
        List statusList = new ArrayList();
        try{
            con = DBUtil.getConnection();
            status = objCommDAO.createPlan(con,objCommVO,req);
            
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
      public boolean updatePlan(CommissionPlanVO objCommVO,HttpServletRequest req) throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        boolean status = false;
        List statusList = new ArrayList();
        try{
            con = DBUtil.getConnection();
            status = objCommDAO.updatePlan(con,objCommVO,req);
            
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

      public List getPlanList(CommissionPlanVO objCommVO) throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        List planList = null;
        try{
            con = DBUtil.getConnection();
            planList = objCommDAO.getPlanList(con,objCommVO);
            
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
        return planList;  
    }
   public boolean checkDuplicateRule(CommissionPlanVO objCommVO) throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        boolean status = false;
        List statusList = new ArrayList();
        try{
            con = DBUtil.getConnection();
            status = objCommDAO.checkDuplicateRule(con,objCommVO);
            
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
    public boolean createPaymentRule(CommissionPlanVO objCommVO,HttpServletRequest req) throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objCommDAO.createPaymentRule(con,objCommVO,req);
            
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
    public boolean updatePaymentRule(CommissionPlanVO objCommVO,HttpServletRequest req) throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objCommDAO.updatePaymentRule(con,objCommVO,req);
            
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
    
    public boolean createCommissionRule(CommissionPlanVO objCommVO) throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objCommDAO.createCommissionRule(con,objCommVO);
            
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
    public boolean updateCommissionRule(CommissionPlanVO objCommVO) throws SQLException,Exception{
      CommissionPlanDAO objCommDAO = new CommissionPlanDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objCommDAO.updateCommissionRule(con,objCommVO);
            
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


    public List getPlanList() {
        return null;
    }
    
}
