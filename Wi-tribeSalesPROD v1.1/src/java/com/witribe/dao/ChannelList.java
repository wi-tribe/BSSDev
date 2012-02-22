/*
 * ChannelList.java
 *
 * Created on January 16, 2010, 6:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.dao;

import com.witribe.util.DBUtil;

import com.witribe.vo.CityVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.witribe.vo.planList;
import com.witribe.vo.paymentRule;
import com.witribe.vo.commissionRule;
/**
 *
 * @author SC43278
 */
public class ChannelList {
    
    private List lChannelList;
    private List lPlanList;
    private List lPayRule;
    private List lCommissionRule;
    /** Creates a new instance of ChannelList */
    public ChannelList() {
        lChannelList = null;
    }

    
    public boolean createNewChannel(String ChannelName) throws SQLException,Exception {
        boolean retValue = false;
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int iMaxChannelId = -1;
        try {
            conn = DBUtil.getConnection();
            if(conn != null && !conn.isClosed() && !conn.isReadOnly()) {
                pstmt = conn.prepareStatement("SELECT max(CHANNEL_ID)+1 FROM MST_CHANNEL");
                rs = pstmt.executeQuery();
                while(rs.next()) {
                   iMaxChannelId = rs.getInt(1) ;
                }
                rs.close();
                pstmt.close();
                pstmt = conn.prepareStatement("INSERT INTO MST_CHANNEL VALUES(" + iMaxChannelId + ",'" + ChannelName +"')");
                int icount = pstmt.executeUpdate();
                if(icount == 1) {
                    retValue = true;
                } else {
                    retValue = false;
                }
                        
            }
            
        } 
        catch (SQLException ex) {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw ex;
        }catch (Exception ex) {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw ex;
        }finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            DBUtil.closeConnection(conn);
        }
        return retValue;
    }
    
    public List getChannels()throws SQLException,Exception{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        lChannelList = null;
        
        try{
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement("SELECT CHANNEL_ID,CHANNEL_NAME FROM MST_CHANNEL");
            rs = pstmt.executeQuery();
            lChannelList = new ArrayList();
            
            
            while(rs.next())
            {   
               //
                lChannelList.add(rs.getString(2));
            }
        }catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            DBUtil.closeConnection(con);
        }
        
        return lChannelList;
    }
    public List getPlans()throws SQLException,Exception
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        lPlanList = null; 
        String PLAN;
                try{
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement("select to_char(cp.id),description,decode(nvl(COMMISSION_MODE ,0),1,'Market',2,'Product',3,'Market+Product',0,'Nil'),channel_id,decode(status,1,'Active',2,'In-Active'),plan_start_date,plan_end_date from COMMISSION_PLAN_TBL cp,CHANNEL_CITY_ZONE_PLAN_TBL ccz where cp.id=ccz.commission_plan_id order by cp.id");
            rs = pstmt.executeQuery();
            lPlanList = new ArrayList();
            
            
            while(rs.next())
            {   
               //
                planList objpList=new planList(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
                lPlanList.add(objpList);
            }
            }catch(SQLException se){
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
                throw e;
            } finally{
                DBUtil.closeResultSet(rs);
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
            }
     return lPlanList;
    }
   public List getPayRule(String plan_id)throws SQLException,Exception
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        lPayRule = null; 
        String payrule;
                try{
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement("select prd.id,decode(duration_measure,1,'Days',2,'Months',3,'Years'),Duration,Payment_Amount,rule_type from  PAYMENT_RULE_TBL pr, PAYMENT_RULE_DETAIL_TBL prd,COMMISSION_RULE_TBL cr where pr.id=prd.payment_id and cr.payment_rule_id(+)=pr.id and cr.plan_id=to_number("+plan_id+")");
            rs = pstmt.executeQuery();
            lPayRule = new ArrayList();
            
            
            while(rs.next())
            {   
               //
                paymentRule objpayRule=new paymentRule(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
                lPayRule.add(objpayRule);
            }
            }catch(SQLException se){
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
                throw e;
            } finally{
                DBUtil.closeResultSet(rs);
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
            }
     return lPayRule;
    }
    public boolean validate_Plan(String plan_id)throws SQLException,Exception
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt=0;
        String payrule;
                try{
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement("select distinct rule_type from COMMISSION_RULE_TBL where plan_id=to_number("+plan_id+")");
            rs = pstmt.executeQuery();
            lPayRule = new ArrayList();
            
            
            while(rs.next())
            {   
               //
               cnt=cnt+1;
            }
            if(cnt<3)
            {
                return false;
            }    
            else
            {
                return true;
            }
   
            }catch(SQLException se){
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
                throw e;
            } finally{
                DBUtil.closeResultSet(rs);
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
            }
    }

    public List getPlan(String plan_id)throws SQLException,Exception
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        lPlanList = null; 
        String PLAN;
                try{
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement("select to_char(cp.id),description,decode(nvl(COMMISSION_MODE ,0),1,'Market',2,'Product',3,'Market+Product',0,'Nil'),channel_id,decode(status,1,'Active',2,'In-Active'),to_char(plan_start_date,'dd/MM/yyyy'),to_char(plan_end_date,'dd/MM/yyyy') from COMMISSION_PLAN_TBL cp,CHANNEL_CITY_ZONE_PLAN_TBL ccz where cp.id=ccz.commission_plan_id and cp.id=to_number("+plan_id+")");
            rs = pstmt.executeQuery();
            lPlanList = new ArrayList();
            while(rs.next())
            {   
               //
                planList objpList=new planList(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
                lPlanList.add(objpList);
            }
            }catch(SQLException se){
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
                throw e;
            } finally{
                DBUtil.closeResultSet(rs);
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
            }
     return lPlanList;
    }
    public List getCommissionRule(String plan_id)throws SQLException,Exception
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        lCommissionRule = null; 
        String PLAN;
                try{
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement("select c.id ,decode(b.rule_type,1,'Basic',3,'Retention',4,'Deduction'),c.eligibility_from,c.eligibility_to,c.amount ,c.step_id ,decode(a.commission_mode,1,'Market',2,'Product',3,'Market+Product'),b.ELIGIBILITY_TARGET,b.MIN_ELIGIBILITY,b.TARGET_MEASURE,b.COMMISSION_TYPE,b.ELIGIBILITY_PERIOD,eligibility_measure,b.id    from   COMMISSION_PLAN_TBL a,   COMMISSION_RULE_TBL b ,   COMMISSION_RULE_DETAIL_TBL c   where a.id=b.plan_id and b.id=c.rule_id(+) and a.id="+plan_id+" order by c.step_id");
            rs = pstmt.executeQuery();
            lCommissionRule = new ArrayList();
            
            
            while(rs.next())
            {   
               
                commissionRule objCommiRuleList=new commissionRule(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14));
                lCommissionRule.add(objCommiRuleList);
            }
            }catch(SQLException se){
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
                throw se;
            } catch(Exception e){
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
                throw e;
            } finally{
                DBUtil.closeResultSet(rs);
                DBUtil.closeStatement(pstmt);
                DBUtil.closeConnection(con);
            }
     return lCommissionRule;
    }
public String Applies_To(String id)
{
    
   if(id==null)
   {    
       return "NA";
   }    
    if((Integer.parseInt(id)>=1 && Integer.parseInt(id)<=6) || Integer.parseInt(id)>9)
    {
        return "CSE";
    }
    if(Integer.parseInt(id)==7)
    {
        return "TL";
    }    
    if(Integer.parseInt(id)==8)
    {
        return "RSM";
    }    

    if(Integer.parseInt(id)==9)
    {
        return "SD";
    }
 
     return "NA";
}
}
