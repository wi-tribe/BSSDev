/*
 * CommissionPlanDAO.java
 *
 * Created on October 23, 2009, 12:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.commission.DAO;

import com.witribe.commission.vo.CommissionPlanVO;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SC43278
 */
public class CommissionPlanDAO {
    
    /** Creates a new instance of CommissionPlanDAO */
    public CommissionPlanDAO() {
    }
    public List getChannelList(Connection con)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        PreparedStatement pstmt = null;
        CommissionPlanVO objCommVO = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select ID,CHANNEL_TYPE from  V_SALES_HEIRARCHY_CHANNEL_TBL where  UPPER(DESIGNATION) = UPPER(?)");
            pstmt.setString(1,"CSE");
            rs = pstmt.executeQuery();
            while(rs.next()){
                
                objCommVO = new CommissionPlanVO();
                objCommVO.setChannelId(rs.getString(1));
                objCommVO.setChannelType(rs.getString(2));
                objList.add(objCommVO);
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
    
    public List getCityList(Connection con)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        PreparedStatement pstmt = null;
        CommissionPlanVO objCommVO = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select distinct(CITY) from  CITY_ZONE_TBL where Zone='All'");
            rs = pstmt.executeQuery();
            while(rs.next()){
                objCommVO = new CommissionPlanVO();
                objCommVO.setCity(rs.getString(1));
                objList.add(objCommVO);
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
    public List getPlanList(Connection con)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        PreparedStatement pstmt = null;
        CommissionPlanVO objCommVO = null;
        String PLAN;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select to_char(id),description,'Market',decode(status,1,'Active',2,'In-Active') from COMMISSION_PLAN_TBL");
            rs = pstmt.executeQuery();
            while(rs.next()){
                PLAN="";
                objCommVO = new CommissionPlanVO();
                PLAN.concat(rs.getString(1));
                PLAN.concat("$");
                PLAN.concat(rs.getString(2));
                PLAN.concat("$");
                PLAN.concat(rs.getString(3));
                PLAN.concat("$");
                PLAN.concat(rs.getString(4));
                objCommVO.setPlan(PLAN);
                objList.add(objCommVO);
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
    public List getZoneList(Connection con,CommissionPlanVO objCommVO)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select ZONE from  CITY_ZONE_TBL where CITY = ?");
            pstmt.setString(1,objCommVO.getCity());
            rs = pstmt.executeQuery();
            while(rs.next()){
                objCommVO = new CommissionPlanVO();
                objCommVO.setZone(rs.getString(1));
                objList.add(objCommVO);
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
    
    public List getProductList(Connection con,CommissionPlanVO objCommVO)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select ID,PRODUCT_NAME from V_Product_Tbl where CITY = ?");
            //and UPPER(PRODUCT_STATUS) =UPPER('Active')
            pstmt.setString(1,objCommVO.getCity());
            rs = pstmt.executeQuery();
            while(rs.next()){
                objCommVO = new CommissionPlanVO();
                objCommVO.setProductId(rs.getString(1));
                objCommVO.setProductName(rs.getString(2));
                objList.add(objCommVO);
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
    public long getMaxKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COMMISSION_PLAN_TBL_SEQ.NEXTVAL from dual");
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
    public long getMaxProductKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT CHANNEL_CITY_ZONE_PLAN_TBL_SEQ.NEXTVAL from dual");
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
        public boolean createPlan(Connection con,CommissionPlanVO objCommVO,HttpServletRequest req)throws SQLException, Exception{
        List objList = null;
        boolean status = false;
        HttpSession userSession =  (HttpSession)req.getSession(true);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String Description = objCommVO.getSalesType();
        String channelId = objCommVO.getChannelId();
        String plan_type=objCommVO.getPlanType();//---Added by Murali
        String valid_from=objCommVO.getValidFrom();//---Added by Murali
        String valid_to=objCommVO.getValidTo();//---Added by Murali
        String planstatus=objCommVO.getStatus();//---Added by Murali
        String channelType = null;
        if(plan_type.equals("2"))
        {
          objCommVO.setCity("All");
        }    
        if(channelId.equals("1")){
            channelType = "Direct Sale";
        } else if(channelId.equals("2")){
            channelType = "Shop-Sales Executive";
        } else if(channelId.equals("3")){
            channelType = "BD-Business Development";
        } else if(channelId.equals("4")){
            channelType = "Kiosks";
        } else if(channelId.equals("5")){
            channelType = "BO";
        } else if(channelId.equals("6")){
            channelType = "NBO";
        } else if(channelId.equals("")){
            channelType = "";
        }

        if("1".equals(objCommVO.getSalesType())){
           // Description = "City"+objCommVO.getCity()+",Zone"+objCommVO.getZone();
        }
        if(objCommVO.getZone().equalsIgnoreCase("All") && plan_type.equals("1") ){
            objCommVO.setProductId("-1");
        }
        con.setAutoCommit(false);

        
        //"Plan selected for City"+objCommVO.getCity()+",Zone"+objCommVO.getZone()+"for salespersonnel type"
        try{
                 long key = getMaxKey(con);
                 long key1 = getMaxProductKey(con);
                 Description =  objCommVO.getPlanName();
                 
                String salesType = objCommVO.getSalesType();
                if(salesType==null)
                {
                   salesType=channelId; 
                }    
                if(salesType.equals("7")){
                    salesType = "TL";
                } else if(salesType.equals("8")){
                    salesType = "RSM";
                } else if(salesType.equals("9")){
                    salesType = "SD";
                } else {
                    salesType = "CSE";
                }
            String plan_id = getCommPlanId(con,objCommVO,channelType,salesType);
            if(plan_id == null){
                userSession.setAttribute("PlanId",key);
                pstmt = con.prepareStatement("insert into COMMISSION_PLAN_TBL (ID, DESCRIPTION, STATUS,COMMISSION_MODE,plan_start_date,plan_end_date) values(?,?,?,to_number(?),trunc(trunc(to_date(?,'dd-mm-yyyy HH24:MI:SS')),'MONTH'),last_day(to_date(?,'dd-mm-yyyy HH24:MI:SS')))");
                //and UPPER(PRODUCT_STATUS) =UPPER('Active')
                pstmt.setLong(1,key);
                pstmt.setString(2,Description);
                pstmt.setString(3,planstatus);
                pstmt.setString(4,plan_type);
                pstmt.setString(5,valid_from);
                pstmt.setString(6,valid_to);                
                
                
                if(pstmt.executeUpdate()>0){
                    status = true;
                }
                if(status){
                    if(!(salesType.equalsIgnoreCase("CSE"))){
                        pstmt = con.prepareStatement("insert into CHANNEL_CITY_ZONE_PLAN_TBL (ID,CHANNEL_ID,CITY_ZONE_ID,COMMISSION_PLAN_ID) "+
                                " values (?,(select ID from V_SALES_HEIRARCHY_CHANNEL_TBL where CHANNEL_TYPE is null and UPPER(DESIGNATION) = UPPER(?)),(select ID from CITY_ZONE_TBL where UPPER(city) = UPPER(?) and UPPER(zone) = UPPER(?)),?)");
                        pstmt.setLong(1,key1);
                        //pstmt.setString(2,channelType);
                        pstmt.setString(2,salesType);
                        pstmt.setString(3,objCommVO.getCity());
                        pstmt.setString(4,objCommVO.getZone());
                        pstmt.setLong(5,key);
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    } else {
                        pstmt = con.prepareStatement("insert into CHANNEL_CITY_ZONE_PLAN_TBL (ID,CHANNEL_ID,CITY_ZONE_ID,COMMISSION_PLAN_ID) "+
                                " values (?,(select ID from V_SALES_HEIRARCHY_CHANNEL_TBL where UPPER(CHANNEL_TYPE) = UPPER(?) and UPPER(DESIGNATION) = UPPER(?)),(select ID from CITY_ZONE_TBL where UPPER(city) = UPPER(?) and UPPER(zone) = UPPER(?)),?)");
                        pstmt.setLong(1,key1);
                        pstmt.setString(2,channelType);
                        pstmt.setString(3,salesType);
                        pstmt.setString(4,objCommVO.getCity());
                        pstmt.setString(5,objCommVO.getZone());
                        pstmt.setLong(6,key);
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    }
                }
    //            if(status && !(objCommVO.getProductId().equals("-1"))){
                   if(status==true)
                   {    
                    pstmt = con.prepareStatement("insert into CITY_PRODUCT_PLAN_TBL (ID,CITY_ZONE_PLAN_ID,PRODUCT_ID) "+
                            " values (CITY_PRODUCT_PLAN_TBL_SEQ.NEXTVAL,?,?)");
                    
                    pstmt.setLong(1,key1);
                    pstmt.setString(2,objCommVO.getProductId());
                    if(pstmt.executeUpdate()>0){
                        status = true;
                    }
                   } 
//                }
                con.commit();
            } else {
                userSession =  (HttpSession)req.getSession(true);
                userSession.setAttribute("PlanId",plan_id);
            }
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw e;
        } finally{
            con.commit();
            con.setAutoCommit(true);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
    }

    public boolean createPlan1(Connection con,CommissionPlanVO objCommVO,HttpServletRequest req)throws SQLException, Exception{
        List objList = null;
        boolean status = false;
        HttpSession userSession =  (HttpSession)req.getSession(true);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String Description = objCommVO.getSalesType();
        String channelId = objCommVO.getChannelId();
        String plan_type=objCommVO.getPlanType();//---Added by Murali
        if(plan_type==null)
        {
           plan_type="";
        }
 
        String valid_from=objCommVO.getValidFrom();//---Added by Murali
        String valid_to=objCommVO.getValidTo();//---Added by Murali
        String planstatus=objCommVO.getStatus();
        String channelType = null;
        String plan_id="";
        if(plan_type.equals("2"))
        {
          objCommVO.setCity("All");
        }    
        if(channelId.equals("1")){
            channelType = "Direct Sale";
        } else if(channelId.equals("2")){
            channelType = "Shop-Sales Executive";
        } else if(channelId.equals("3")){
            channelType = "BD-Business Development";
        } else if(channelId.equals("4")){
            channelType = "Kiosks";
        } else if(channelId.equals("5")){
            channelType = "BO";
        } else if(channelId.equals("6")){
            channelType = "NBO";
        } else if(channelId.equals("")){
            channelType = "";
        }

        if("1".equals(objCommVO.getSalesType())){
           // Description = "City"+objCommVO.getCity()+",Zone"+objCommVO.getZone();
        }
        if(objCommVO.getZone().equalsIgnoreCase("All") && plan_type.equals("1") ){
            objCommVO.setProductId("-1");
        }
        con.setAutoCommit(false);

        
        //"Plan selected for City"+objCommVO.getCity()+",Zone"+objCommVO.getZone()+"for salespersonnel type"
        try{
                 long key = getMaxKey(con);
                 long key1 = getMaxProductKey(con);
                 Description =  objCommVO.getPlanName();
                 
                String salesType = objCommVO.getSalesType();
                if(salesType==null)
                {
                   salesType=channelId; 
                }    
                if(salesType.equals("7")){
                    salesType = "TL";
                } else if(salesType.equals("8")){
                    salesType = "RSM";
                } else if(salesType.equals("9")){
                    salesType = "SD";
                } else {
                    salesType = "CSE";
                }
            plan_id=objCommVO.getPlanId();
            if(plan_id==null)
            {
               plan_id=""; 
            }
  
            if(plan_id.length()>0)
            {
                pstmt=con.prepareStatement("update COMMISSION_PLAN_TBL set plan_start_date=trunc(trunc(to_date(?,'dd-mm-yyyy HH24:MI:SS')),'MONTH'),plan_end_date=last_day(to_date(?,'dd-mm-yyyy HH24:MI:SS')),status=? where id=to_number(?)");
             // pstmt.setString(1,Description);
                 pstmt.setString(1,valid_from);
                pstmt.setString(2,valid_to); 
                pstmt.setString(3,planstatus); 
                pstmt.setString(4,plan_id); 
                if(pstmt.executeUpdate()>0){
                    status = true;
                }
                  if(status){
                    if(!(salesType.equalsIgnoreCase("CSE"))){
                                                    
                        pstmt = con.prepareStatement("Update CHANNEL_CITY_ZONE_PLAN_TBL set channel_id=(select ID from V_SALES_HEIRARCHY_CHANNEL_TBL where CHANNEL_TYPE is null and UPPER(DESIGNATION) = UPPER(?)),CITY_ZONE_ID=(select ID from CITY_ZONE_TBL where UPPER(city) = UPPER(?) and UPPER(zone) = UPPER(?)) where COMMISSION_PLAN_ID=to_number(?)");
                   //    pstmt.setLong(1,key1);
                        //pstmt.setString(2,channelType);
                        pstmt.setString(1,salesType);
                        pstmt.setString(2,objCommVO.getCity());
                        pstmt.setString(3,objCommVO.getZone());
                        pstmt.setString(4,plan_id);
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    } else {
                        pstmt = con.prepareStatement("Update CHANNEL_CITY_ZONE_PLAN_TBL set CHANNEL_ID=(select ID from V_SALES_HEIRARCHY_CHANNEL_TBL where UPPER(CHANNEL_TYPE) = UPPER(?) and UPPER(DESIGNATION) = UPPER(?)),"+
                                            "CITY_ZONE_ID=(select ID from CITY_ZONE_TBL where UPPER(city) = UPPER(?) and UPPER(zone) = UPPER(?))"+
                                            " where COMMISSION_PLAN_ID=to_number(?)");
                        pstmt.setString(1,channelType);
                        pstmt.setString(2,salesType);
                        pstmt.setString(3,objCommVO.getCity());
                        pstmt.setString(4,objCommVO.getZone());
                        pstmt.setString(5,plan_id);
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    }
                }   
                   if(status)
                  {
                    pstmt = con.prepareStatement("Update CITY_PRODUCT_PLAN_TBL set PRODUCT_ID=to_number(?) where CITY_ZONE_PLAN_ID=to_number(?)");
                    pstmt.setString(1,objCommVO.getProductId());
                    pstmt.setString(2,plan_id);
                    if(pstmt.executeUpdate()>0){
                        status = true;
                    }
                }
                userSession =  (HttpSession)req.getSession(true);
                userSession.setAttribute("PlanId",plan_id);
                con.commit();
            }   
            else
            {    
            plan_id = getCommPlanId(con,objCommVO,channelType,salesType);
            
         if(plan_id == null){
                userSession.setAttribute("PlanId",key);
                pstmt = con.prepareStatement("insert into COMMISSION_PLAN_TBL (ID, DESCRIPTION, STATUS,COMMISSION_MODE,plan_start_date,plan_end_date) values(?,?,?,to_number(?),trunc(trunc(to_date(?,'dd-mm-yyyy HH24:MI:SS')),'MONTH'),last_day(to_date(?,'dd-mm-yyyy HH24:MI:SS')))");
                //and UPPER(PRODUCT_STATUS) =UPPER('Active')
                pstmt.setLong(1,key);
                pstmt.setString(2,Description);
                pstmt.setString(3,planstatus);
                pstmt.setString(4,plan_type);
                pstmt.setString(5,valid_from);
                pstmt.setString(6,valid_to);                
                if(pstmt.executeUpdate()>0){
                    status = true;
                }
                if(status){
                    if(!(salesType.equalsIgnoreCase("CSE"))){
                        pstmt = con.prepareStatement("insert into CHANNEL_CITY_ZONE_PLAN_TBL (ID,CHANNEL_ID,CITY_ZONE_ID,COMMISSION_PLAN_ID) "+
                                " values (?,(select ID from V_SALES_HEIRARCHY_CHANNEL_TBL where CHANNEL_TYPE is null and UPPER(DESIGNATION) = UPPER(?)),(select ID from CITY_ZONE_TBL where UPPER(city) = UPPER(?) and UPPER(zone) = UPPER(?)),?)");
                        pstmt.setLong(1,key1);
                        //pstmt.setString(2,channelType);
                        pstmt.setString(2,salesType);
                        pstmt.setString(3,objCommVO.getCity());
                        pstmt.setString(4,objCommVO.getZone());
                        pstmt.setLong(5,key);
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    } else {
                        pstmt = con.prepareStatement("insert into CHANNEL_CITY_ZONE_PLAN_TBL (ID,CHANNEL_ID,CITY_ZONE_ID,COMMISSION_PLAN_ID) "+
                                " values (?,(select ID from V_SALES_HEIRARCHY_CHANNEL_TBL where UPPER(CHANNEL_TYPE) = UPPER(?) and UPPER(DESIGNATION) = UPPER(?)),(select ID from CITY_ZONE_TBL where UPPER(city) = UPPER(?) and UPPER(zone) = UPPER(?)),?)");
                        pstmt.setLong(1,key1);
                        pstmt.setString(2,channelType);
                        pstmt.setString(3,salesType);
                        pstmt.setString(4,objCommVO.getCity());
                        pstmt.setString(5,objCommVO.getZone());
                        pstmt.setLong(6,key);
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    }
                }
             //   if(status && !(objCommVO.getProductId().equals("-1"))){
                  if(status)
                  {
                    pstmt = con.prepareStatement("insert into CITY_PRODUCT_PLAN_TBL (ID,CITY_ZONE_PLAN_ID,PRODUCT_ID) "+
                            " values (CITY_PRODUCT_PLAN_TBL_SEQ.NEXTVAL,?,?)");
                    
                    pstmt.setLong(1,key1);
                    pstmt.setString(2,objCommVO.getProductId());
                    if(pstmt.executeUpdate()>0){
                        status = true;
                    }
                }
                con.commit();
                userSession =  (HttpSession)req.getSession(true);
                userSession.setAttribute("PlanId",key);
               } 
                else {
                userSession =  (HttpSession)req.getSession(true);
                userSession.setAttribute("Plan_Id",key);
              }
            }
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw e;
        } finally{
            con.commit();
            con.setAutoCommit(true);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }

        return status;
    }
        public boolean updatePlan(Connection con,CommissionPlanVO objCommVO,HttpServletRequest req)throws SQLException, Exception{
        List objList = null;
        boolean status = false;
        HttpSession userSession =  (HttpSession)req.getSession(true);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String Description = objCommVO.getSalesType();
        String channelId = objCommVO.getChannelId();
        String plan_type=objCommVO.getPlanType();//---Added by Murali
        if(plan_type==null)
        {
           plan_type="";
        }
 
        String valid_from=objCommVO.getValidFrom();//---Added by Murali
        String valid_to=objCommVO.getValidTo();//---Added by Murali
        String planstatus=objCommVO.getStatus();
        String channelType = null;
        String plan_id="";
        if(plan_type.equals("2"))
        {
          objCommVO.setCity("All");
        }    
        if(channelId.equals("1")){
            channelType = "Direct Sale";
        } else if(channelId.equals("2")){
            channelType = "Shop-Sales Executive";
        } else if(channelId.equals("3")){
            channelType = "BD-Business Development";
        } else if(channelId.equals("4")){
            channelType = "Kiosks";
        } else if(channelId.equals("5")){
            channelType = "BO";
        } else if(channelId.equals("6")){
            channelType = "NBO";
        } else if(channelId.equals("")){
            channelType = "";
        }

        if("1".equals(objCommVO.getSalesType())){
           // Description = "City"+objCommVO.getCity()+",Zone"+objCommVO.getZone();
        }
        if(objCommVO.getZone().equalsIgnoreCase("All") && plan_type.equals("1") ){
            objCommVO.setProductId("-1");
        }
        con.setAutoCommit(false);

        
        //"Plan selected for City"+objCommVO.getCity()+",Zone"+objCommVO.getZone()+"for salespersonnel type"
        try{
           //      long key = getMaxKey(con);
           //      long key1 = getMaxProductKey(con);
                 Description =  objCommVO.getPlanName();
                String salesType = objCommVO.getSalesType();
                if(salesType==null)
                {
                   salesType=channelId; 
                }    
                if(salesType.equals("7")){
                    salesType = "TL";
                } else if(salesType.equals("8")){
                    salesType = "RSM";
                } else if(salesType.equals("9")){
                    salesType = "SD";
                } else {
                    salesType = "CSE";
                }
            plan_id=objCommVO.getPlanId();
            if(plan_id==null)
            {
               plan_id=""; 
            }
  
            if(plan_id.length()>0)
            {
                pstmt=con.prepareStatement("update COMMISSION_PLAN_TBL set plan_start_date=trunc(trunc(to_date(?,'dd-mm-yyyy HH24:MI:SS')),'MONTH'),plan_end_date=last_day(to_date(?,'dd-mm-yyyy HH24:MI:SS')),status=? where id=to_number(?)");
             // pstmt.setString(1,Description);
                 pstmt.setString(1,valid_from);
                pstmt.setString(2,valid_to); 
                pstmt.setString(3,planstatus); 
                pstmt.setString(4,plan_id); 
                if(pstmt.executeUpdate()>0){
                    status = true;
                }
                  if(status){
                    if(!(salesType.equalsIgnoreCase("CSE"))){
                                                    
                        pstmt = con.prepareStatement("Update CHANNEL_CITY_ZONE_PLAN_TBL set channel_id=(select ID from V_SALES_HEIRARCHY_CHANNEL_TBL where CHANNEL_TYPE is null and UPPER(DESIGNATION) = UPPER(?)),CITY_ZONE_ID=(select ID from CITY_ZONE_TBL where UPPER(city) = UPPER(?) and UPPER(zone) = UPPER(?)) where COMMISSION_PLAN_ID=to_number(?)");
                   //    pstmt.setLong(1,key1);
                        //pstmt.setString(2,channelType);
                        pstmt.setString(1,salesType);
                        pstmt.setString(2,objCommVO.getCity());
                        pstmt.setString(3,objCommVO.getZone());
                        pstmt.setString(4,plan_id);
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    } else {
                        pstmt = con.prepareStatement("Update CHANNEL_CITY_ZONE_PLAN_TBL set CHANNEL_ID=(select ID from V_SALES_HEIRARCHY_CHANNEL_TBL where UPPER(CHANNEL_TYPE) = UPPER(?) and UPPER(DESIGNATION) = UPPER(?)),"+
                                            "CITY_ZONE_ID=(select ID from CITY_ZONE_TBL where UPPER(city) = UPPER(?) and UPPER(zone) = UPPER(?))"+
                                            " where COMMISSION_PLAN_ID=to_number(?)");
                        pstmt.setString(1,channelType);
                        pstmt.setString(2,salesType);
                        pstmt.setString(3,objCommVO.getCity());
                        pstmt.setString(4,objCommVO.getZone());
                        pstmt.setString(5,plan_id);
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    }
                }   
                   if(status)
                  {
                    pstmt = con.prepareStatement("Update CITY_PRODUCT_PLAN_TBL set PRODUCT_ID=to_number(?) where CITY_ZONE_PLAN_ID=to_number(?)");
                    pstmt.setString(1,objCommVO.getProductId());
                    pstmt.setString(2,plan_id);
                    if(pstmt.executeUpdate()>0){
                        status = true;
                    }
                }
                userSession =  (HttpSession)req.getSession(true);
                userSession.setAttribute("PlanId",plan_id);
                con.commit();
 
            }
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw e;
        } finally{
            con.commit();
            con.setAutoCommit(true);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }

        return status;
    }

    public String getCommPlanId(Connection con,CommissionPlanVO objCommVO,String ChannelType,String SalesType)throws SQLException,Exception{
        ResultSet rs = null;
        String zone =objCommVO.getZone() ;
        PreparedStatement pstmt = null;
        String plan_id = null;
        try{
            
            if(!zone.equalsIgnoreCase("All")){
                 if(SalesType.equalsIgnoreCase("CSE")){
                pstmt = con.prepareStatement("select commission_Plan_id from CHANNEL_CITY_ZONE_PLAN_TBL czp, CITY_PRODUCT_PLAN_TBL cp "+
                        " where czp.id =cp.CITY_ZONE_PLAN_ID "+
                        " and cp.product_id=? "+
                        " and czp.city_zone_id = (select id from CITY_ZONE_TBL where UPPER(city)=UPPER(?) and UPPER(zone)=UPPER(?)) "+
                        " and czp.channel_id=(select id from V_SALES_HEIRARCHY_CHANNEL_TBL where UPPER(CHANNEL_TYPE) = UPPER(?) and UPPER(DESIGNATION) = UPPER(?))");
                
                pstmt.setString(1,objCommVO.getProductId());
                pstmt.setString(2,objCommVO.getCity());
                pstmt.setString(3,zone);
                pstmt.setString(4,ChannelType);
                pstmt.setString(5,SalesType);
                 } else {
                   pstmt = con.prepareStatement("select commission_Plan_id from CHANNEL_CITY_ZONE_PLAN_TBL czp, CITY_PRODUCT_PLAN_TBL cp "+
                        " where czp.id =cp.CITY_ZONE_PLAN_ID "+
                        " and cp.product_id=? "+
                        " and czp.city_zone_id = (select id from CITY_ZONE_TBL where UPPER(city)=UPPER(?) and UPPER(zone)=UPPER(?)) "+
                        " and czp.channel_id=(select id from V_SALES_HEIRARCHY_CHANNEL_TBL where CHANNEL_TYPE is null and UPPER(DESIGNATION) = UPPER(?))");
                
                pstmt.setString(1,objCommVO.getProductId());
                pstmt.setString(2,objCommVO.getCity());
                pstmt.setString(3,zone);
                //pstmt.setString(4,ChannelType);
                pstmt.setString(4,SalesType);  
                 }
            } else {
                if(!SalesType.equalsIgnoreCase("CSE")){
                    pstmt = con.prepareStatement("select commission_Plan_id from CHANNEL_CITY_ZONE_PLAN_TBL czp "+
                            " where czp.city_zone_id = (select id from CITY_ZONE_TBL where UPPER(city)=UPPER(?) and UPPER(zone)=UPPER(?)) "+
                            " and czp.channel_id=(select id from V_SALES_HEIRARCHY_CHANNEL_TBL where UPPER(CHANNEL_TYPE) is null and UPPER(DESIGNATION) = UPPER(?))");
                    
                    pstmt.setString(1,objCommVO.getCity());
                    pstmt.setString(2,zone);
                    //pstmt.setString(3,ChannelType);
                    pstmt.setString(3,SalesType);
                } else {
                    pstmt = con.prepareStatement("select commission_Plan_id from CHANNEL_CITY_ZONE_PLAN_TBL czp "+
                            " where czp.city_zone_id = (select id from CITY_ZONE_TBL where UPPER(city)=UPPER(?) and UPPER(zone)=UPPER(?)) "+
                            " and czp.channel_id=(select id from V_SALES_HEIRARCHY_CHANNEL_TBL where UPPER(CHANNEL_TYPE) = UPPER(?) and UPPER(DESIGNATION) = UPPER(?))");
                    
                    pstmt.setString(1,objCommVO.getCity());
                    pstmt.setString(2,zone);
                    pstmt.setString(3,ChannelType);
                    pstmt.setString(4,SalesType);
                }
            }
            rs = pstmt.executeQuery();
            if(rs.next()){
                plan_id = rs.getString(1);
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
        return plan_id;
    }
    public long getMaxPaymentKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT PAYMENT_RULE_SEQ.NEXTVAL from dual");
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
    public boolean createPaymentRule(Connection con,CommissionPlanVO objCommVO,HttpServletRequest req)throws SQLException, Exception{
        List objList = null;
        boolean status = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String Description = null;
        String planid=objCommVO.getPlanId();
         
            HttpSession userSession =  (HttpSession)req.getSession(true);
            if("1".equals(objCommVO.getDurationMeasure())){
                Description = "Duration Measure in Days";
            } else if("2".equals(objCommVO.getDurationMeasure())){
                Description = "Duration Measure in Months";
            } else {
                Description = "Duration Measure in Years";
            }
        con.setAutoCommit(false);
        String[] duration = objCommVO.getDuration();
        String[] payAmt = objCommVO.getPaymentAmount();
        try{
            long key = getMaxPaymentKey(con);
            userSession.setAttribute("paymentRuleId",key);
            pstmt = con.prepareStatement("insert into PAYMENT_RULE_TBL (ID, DESCRIPTION, DURATION_MEASURE) values(?,?,?)");
            //and UPPER(PRODUCT_STATUS) =UPPER('Active')
            pstmt.setLong(1,key);
            pstmt.setString(2,Description);
            pstmt.setString(3,objCommVO.getDurationMeasure());
            if(pstmt.executeUpdate()>0){
                status = true;
            }
            if(status){
                for(int i=0;i<duration.length;i++) {
                    if(duration[i] != "" && payAmt[i] != ""){
                        pstmt = con.prepareStatement("insert into PAYMENT_RULE_DETAIL_TBL (ID,PAYMENT_ID,DURATION,PAYMENT_AMOUNT) "+
                                " values (PAYMENT_RULE_DETAIL_SEQ.NEXTVAL,?,?,?)");
                        
                        pstmt.setLong(1,key);
                        pstmt.setString(2,duration[i]);
                        pstmt.setString(3,payAmt[i]);
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    }
                }
            }
            con.commit();
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw e;
        } finally{
            con.commit();
            con.setAutoCommit(true);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
    }
        public boolean updatePaymentRule(Connection con,CommissionPlanVO objCommVO,HttpServletRequest req)throws SQLException, Exception{
        List objList = null;
        boolean status = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String Description = null;
        String planid=objCommVO.getPlanId();
        con.setAutoCommit(false);
        String[] duration = objCommVO.getDuration();
        String[] payAmt = objCommVO.getPaymentAmount();
        String idlist=objCommVO.getPaymentId();
        String [] ids=idlist.split("[*]");
        try{
 
                for(int i=0;i<ids.length;i++) {
                    if(duration[i] != "" && payAmt[i] != ""){
                        pstmt = con.prepareStatement("update PAYMENT_RULE_DETAIL_TBL set duration=?,PAYMENT_AMOUNT=? where id=to_number(?)");
                        pstmt.setString(1,duration[i]);
                        pstmt.setString(2,payAmt[i]);
                        pstmt.setString(3,ids[i]);                        
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    }
              
            }
               if (ids.length<payAmt.length)
               {    
                for(int j=ids.length;j<payAmt.length;j++) {
                    if(duration[j] != "" && payAmt[j] != ""){
                        pstmt = con.prepareStatement("insert into PAYMENT_RULE_DETAIL_TBL (ID,PAYMENT_ID,DURATION,PAYMENT_AMOUNT) "+
                                " values (PAYMENT_RULE_DETAIL_SEQ.NEXTVAL,(select payment_id from PAYMENT_RULE_DETAIL_TBL where id=?),?,?)");
                        pstmt.setString(1,ids[0]);                        
                        pstmt.setString(2,duration[j]);
                        pstmt.setString(3,payAmt[j]);
                        
                        if(pstmt.executeUpdate()>0){
                            status = true;
                        }
                    }
              
                }
               }
                HttpSession userSession =  (HttpSession)req.getSession(true);                
                userSession =  (HttpSession)req.getSession(true);
                userSession.setAttribute("PlanId",planid);  
                userSession.setAttribute("paymentRuleId","-1");    
            con.commit();
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw e;
        } finally{
            con.commit();
            con.setAutoCommit(true);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
    }

    public long getMaxCommRuleKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COMMISSION_RULE_SEQ.NEXTVAL from dual");
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
    public boolean createCommissionRule(Connection con,CommissionPlanVO objCommVO)throws SQLException, Exception{
        List objList = null;
        boolean status = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        con.setAutoCommit(false);
        
        //"Plan selected for City"+objCommVO.getCity()+",Zone"+objCommVO.getZone()+"for salespersonnel type"
        try{
            long key = getMaxCommRuleKey(con);
            //String[] stepid = objCommVO.getStepId();
            String[] elfrom = objCommVO.getEligibilityFrom();
            String[] elto = objCommVO.getEligibilityTo();
            String[] amount = objCommVO.getAmount();
            
            pstmt = con.prepareStatement("insert into COMMISSION_RULE_TBL (ID, PLAN_ID, RULE_TYPE,TARGET_MEASURE,COMMISSION_TYPE,ELIGIBILITY_PERIOD,ELIGIBILITY_TARGET, "+
                    " MIN_ELIGIBILITY,PAYMENT_RULE_ID,ELIGIBILITY_MEASURE) values (?,?,?,?,?,?,?,?,?,?)");
            //and UPPER(PRODUCT_STATUS) =UPPER('Active')
            if(!(objCommVO.getCommissionType().equalsIgnoreCase("Deduction"))){
                pstmt.setLong(1,key);
                pstmt.setString(2,objCommVO.getPlanId());
                if(objCommVO.getCommissionType().equalsIgnoreCase("Basic")){
                    pstmt.setString(3,"1");
                } else if(objCommVO.getCommissionType().equalsIgnoreCase("Promotional")){
                    pstmt.setString(3,"2");
                } else if(objCommVO.getCommissionType().equalsIgnoreCase("Retention")){
                    pstmt.setString(3,"3");
                } else if(objCommVO.getCommissionType().equalsIgnoreCase("Deduction")){
                    pstmt.setString(3,"4");
                }
                if(objCommVO.getCommissionType().equalsIgnoreCase("Retention")){
                    pstmt.setString(4,"2");
                } else {
                    pstmt.setString(4,objCommVO.getTargetMeasure());
                }
                pstmt.setString(5,objCommVO.getCommType());
                if(objCommVO.getEligibilityPeriod() != ""){
                    pstmt.setString(6,objCommVO.getEligibilityPeriod());
                } else {
                    pstmt.setString(6,"-1");
                }
                if(objCommVO.getCommissionType().equalsIgnoreCase("Retention")){
                    pstmt.setString(7,"-1");
                    pstmt.setString(8,"-1");
                } else {
                    pstmt.setString(7,objCommVO.getEligibilityTarget());
                    pstmt.setString(8,objCommVO.getMinEligibility());
                }
                pstmt.setString(9,objCommVO.getPaymentId());
                if(objCommVO.getCommissionType().equalsIgnoreCase("Basic") || objCommVO.getCommissionType().equalsIgnoreCase("Promotional")){
                    pstmt.setString(10,objCommVO.getEligibilityMeasurePer());
                } else {
                    pstmt.setString(10,objCommVO.getEligibilityMeasure());
                }
            } else {
                pstmt.setLong(1,key);
                pstmt.setString(2,objCommVO.getPlanId());
                pstmt.setString(3,"4");
                pstmt.setString(4,"-1");
                pstmt.setString(5,"-1");
                pstmt.setString(6,objCommVO.getEligibilityPeriod());
                pstmt.setString(7,"-1");
                pstmt.setString(8,"-1");
                pstmt.setString(9,objCommVO.getPaymentId());
                pstmt.setString(10,objCommVO.getEligibilityMeasure());
            }
            if(pstmt.executeUpdate()>0){
                status = true;
            }
            if(!(objCommVO.getCommissionType().equalsIgnoreCase("Deduction"))){
                if(status){
                    int count = 1;
                    for(int i=0;i<elfrom.length;i++){
                        
                        if(elfrom[i] != ""){
                            
                            pstmt = con.prepareStatement("insert into COMMISSION_RULE_DETAIL_TBL (ID,RULE_ID,STEP_ID,ELIGIBILITY_FROM, ELIGIBILITY_TO ,AMOUNT ) "+
                                    " values (COMMISSION_RULE_DETAIL_SEQ.NEXTVAL,?,?,?,?,?)");
                            
                            pstmt.setLong(1,key);
                            pstmt.setInt(2,count);
                            pstmt.setString(3,elfrom[i]);
                            if(elto[i].equals("")){
                                pstmt.setString(4,"99999");
                            } else {
                                pstmt.setString(4,elto[i]);
                            }
                            pstmt.setString(5,amount[i]);
                            if(pstmt.executeUpdate()>0){
                                status = true;
                            }
                            count = count + 1;
                        }
                        
                    }
                }
            }
            con.commit();
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw e;
        } finally{
            con.commit();
            con.setAutoCommit(true);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
    }
    public boolean checkDuplicateRule(Connection con,CommissionPlanVO objCommVO)throws SQLException, Exception{
        List objList = null;
        boolean status = false;
        boolean status1 = false;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        String city ="";
        String plantype=objCommVO.getPlanType();
        if(!plantype.equals("2"))
        {    
           city = objCommVO.getCity();
        }
        else
        {
            city ="All";
        }    
        
        
        String zone = objCommVO.getZone();
        String channelId = objCommVO.getChannelId();
        String salesType = objCommVO.getSalesType();
        String productId = objCommVO.getProductId();
        String commType = objCommVO.getCommType();

        String channelType = null;
        if(channelId.equals("1")){
            channelType = "Direct Sale";
        } else if(channelId.equals("2")){
            channelType = "Shop-Sales Executive";
        } else if(channelId.equals("3")){
            channelType = "BD-Business Development";
        } else if(channelId.equals("4")){
            channelType = "Kiosks";
        } else if(channelId.equals("5")){
            channelType = "BO";
        } else if(channelId.equals("6")){
            channelType = "NBO";
        } else if(channelId.equals("")){
            channelType = "";
        }
        if(salesType.equals("7")){
            salesType = "TL";
        } else if(salesType.equals("8")){
            salesType = "RSM";
        } else if(salesType.equals("9")){
            salesType = "SD";
        } else {
            salesType = "CSE";
        }
        try{
            
            /*pstmt = con.prepareStatement("select ID,CITY_ZONE_PLAN_ID,PRODUCT_ID from CITY_PRODUCT_PLAN_TBL where CITY_ZONE_PLAN_ID in "+
                   " (select id from CHANNEL_CITY_ZONE_PLAN_TBL where CITY_ZONE_ID in (select ID from CITY_ZONE_TBL "+
                    " where UPPER(CITY) = UPPER(?) and UPPER(ZONE) = UPPER(?)) and CHANNEL_ID in (select ID from v_sales_heirarchy_channel_tbl "+
                    " where UPPER(CHANNEL_TYPE) =UPPER(?) and UPPER(DESIGNATION) =UPPER(?))) and PRODUCT_ID = ? ");
            //and UPPER(PRODUCT_STATUS) =UPPER('Active')*/
            
            if(!zone.equalsIgnoreCase("All")){
                if(!salesType.equalsIgnoreCase("CSE")){
                    pstmt1 = con.prepareStatement("select * from  CHANNEL_CITY_ZONE_PLAN_TBL cc,COMMISSION_RULE_TBL cr where cc.ID = cr.PLAN_ID and cc.city_zone_id in (select id from city_zone_tbl where UPPER(city) = UPPER(?) and UPPER(zone) = UPPER(?)) "+
                            " and cc.channel_id in (select id from V_SALES_HEIRARCHY_CHANNEL_TBL where CHANNEL_TYPE is null and UPPER(DESIGNATION) = UPPER(?)) and cr.RULE_TYPE = ?");
                    pstmt1.setString(1,city);
                    pstmt1.setString(2,"All");
                    pstmt1.setString(3,salesType);
                     pstmt1.setString(4,commType);
                    rs1 = pstmt1.executeQuery();
                    if(rs1.next()){
                        status = false;
                    } else {
                        status = true;
                    }
                } else {
                    pstmt1 = con.prepareStatement("select * from  CHANNEL_CITY_ZONE_PLAN_TBL cc,COMMISSION_RULE_TBL cr where cc.ID = cr.PLAN_ID and cc.city_zone_id in (select id from city_zone_tbl where UPPER(city) = UPPER(?) and UPPER(zone) = UPPER(?)) "+
                            " and cc.channel_id in (select id from V_SALES_HEIRARCHY_CHANNEL_TBL where UPPER(CHANNEL_TYPE) = UPPER(?) and UPPER(DESIGNATION) = UPPER(?)) and cr.RULE_TYPE = ?");
                    pstmt1.setString(1,city);
                    pstmt1.setString(2,"All");
                    pstmt1.setString(3,channelType);
                    pstmt1.setString(4,salesType);
                     pstmt1.setString(5,commType);
                    rs1 = pstmt1.executeQuery();
                    if(rs1.next()){
                        status = false;
                    } else {
                        status = true;
                    }
                } 
                if(status){
                    pstmt = con.prepareStatement("select * from CHANNEL_CITY_ZONE_PLAN_TBL ccp ,COMMISSION_PLAN_TBL cp, CITY_ZONE_TBL cz where "+
                                "ccp.CITY_ZONE_ID = cz.id and ccp.COMMISSION_PLAN_ID = cp.id and UPPER(cz.city) = UPPER(?) and UPPER(cz.zone) = UPPER(?)");
                     pstmt.setString(1,city);
                    pstmt.setString(2,"All");
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                      status = false;  
                    } else {
                      status = true;  
                    }
                }
                   if(status){
                    if(!salesType.equalsIgnoreCase("CSE")){
                    pstmt = con.prepareStatement("select rule_type from COMMISSION_RULE_TBL where plan_id in (select CITY_ZONE_PLAN_ID from CITY_PRODUCT_PLAN_TBL where CITY_ZONE_PLAN_ID in "+
                            " (select id from CHANNEL_CITY_ZONE_PLAN_TBL where CITY_ZONE_ID in (select ID from CITY_ZONE_TBL "+
                            " where UPPER(CITY) = UPPER(?) and UPPER(ZONE) = UPPER(?)) and CHANNEL_ID in (select ID from v_sales_heirarchy_channel_tbl "+
                            " where CHANNEL_TYPE is null and UPPER(DESIGNATION) =UPPER(?))) and PRODUCT_ID = ? and RULE_TYPE = ?)");
                    pstmt.setString(1,city);
                    pstmt.setString(2,zone);
                    //pstmt.setString(3,channelType);
                    pstmt.setString(3,salesType);
                    pstmt.setString(4,productId);
                    pstmt.setString(5,commType);    
                    } else {
                    pstmt = con.prepareStatement("select rule_type from COMMISSION_RULE_TBL where plan_id in (select CITY_ZONE_PLAN_ID from CITY_PRODUCT_PLAN_TBL where CITY_ZONE_PLAN_ID in "+
                            " (select id from CHANNEL_CITY_ZONE_PLAN_TBL where CITY_ZONE_ID in (select ID from CITY_ZONE_TBL "+
                            " where UPPER(CITY) = UPPER(?) and UPPER(ZONE) = UPPER(?)) and CHANNEL_ID in (select ID from v_sales_heirarchy_channel_tbl "+
                            " where UPPER(CHANNEL_TYPE) =UPPER(?) and UPPER(DESIGNATION) =UPPER(?))) and PRODUCT_ID = ? and RULE_TYPE = ?)");
                    pstmt.setString(1,city);
                    pstmt.setString(2,zone);
                    pstmt.setString(3,channelType);
                    pstmt.setString(4,salesType);
                    pstmt.setString(5,productId);
                    pstmt.setString(6,commType);
                    }
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        status = false;
                    } else {
                        status = true;
                    }
                }
            } else {
                if(!salesType.equalsIgnoreCase("CSE")){
                    pstmt1 = con.prepareStatement("select * from  CHANNEL_CITY_ZONE_PLAN_TBL cc,COMMISSION_RULE_TBL cr where cc.ID = cr.PLAN_ID and cc.city_zone_id in (select id from city_zone_tbl where UPPER(city) = UPPER(?)) "+
                            " and cc.channel_id in (select id from V_SALES_HEIRARCHY_CHANNEL_TBL where UPPER(CHANNEL_TYPE) is null and UPPER(DESIGNATION) = UPPER(?)) and cr.RULE_TYPE = ?");
                    pstmt1.setString(1,city);
                    pstmt1.setString(2,salesType);
                     pstmt1.setString(3,commType);
                    rs1 = pstmt1.executeQuery();
                    if(rs1.next()){
                        status = false;
                    } else {
                        status = true;
                    }
                } else {
                    pstmt1 = con.prepareStatement("select * from  CHANNEL_CITY_ZONE_PLAN_TBL cc,COMMISSION_RULE_TBL cr where cc.ID = cr.PLAN_ID and cc.city_zone_id in (select id from city_zone_tbl where UPPER(city) = UPPER(?)) "+
                            " and cc.channel_id in (select id from V_SALES_HEIRARCHY_CHANNEL_TBL where UPPER(CHANNEL_TYPE) = UPPER(?) and UPPER(DESIGNATION) = UPPER(?)) and cr.RULE_TYPE = ?");
                    pstmt1.setString(1,city);
                    pstmt1.setString(2,channelType);
                    pstmt1.setString(3,salesType);
                     pstmt1.setString(4,commType);
                    rs1 = pstmt1.executeQuery();
                    if(rs1.next()){
                        status = false;
                    } else {
                        status = true;
                    }
                }
                
                if(status){
                    
                    pstmt = con.prepareStatement("select * from CHANNEL_CITY_ZONE_PLAN_TBL ccp,CITY_PRODUCT_PLAN_TBL cpp ,COMMISSION_PLAN_TBL cp, CITY_ZONE_TBL cz where "+
                            " ccp.CITY_ZONE_ID = cz.id and ccp.COMMISSION_PLAN_ID = cpp.CITY_ZONE_PLAN_ID and UPPER(cz.city) = UPPER(?) and cpp.PRODUCT_ID = ?");
                    pstmt.setString(1,city);
                    pstmt.setString(2,productId);
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        status = false;
                    } else {
                        status = true;
                    }
                }
                if(status){
                    if(!salesType.equalsIgnoreCase("CSE")){
                        pstmt = con.prepareStatement("select rule_type from COMMISSION_RULE_TBL where plan_id in (select id from CHANNEL_CITY_ZONE_PLAN_TBL where CITY_ZONE_ID in (select ID from CITY_ZONE_TBL "+
                                " where UPPER(CITY) = UPPER(?) and UPPER(ZONE) = UPPER(?)) and CHANNEL_ID in (select ID from v_sales_heirarchy_channel_tbl "+
                                " where CHANNEL_TYPE is null and UPPER(DESIGNATION) =UPPER(?))) and RULE_TYPE = ?");
                        pstmt.setString(1,city);
                        pstmt.setString(2,zone);
                        pstmt.setString(3,salesType);
                        pstmt.setString(4,commType);
                    } else {
                        pstmt = con.prepareStatement("select rule_type from COMMISSION_RULE_TBL where plan_id in (select id from CHANNEL_CITY_ZONE_PLAN_TBL where CITY_ZONE_ID in (select ID from CITY_ZONE_TBL "+
                                " where UPPER(CITY) = UPPER(?) and UPPER(ZONE) = UPPER(?)) and CHANNEL_ID in (select ID from v_sales_heirarchy_channel_tbl "+
                                " where UPPER(CHANNEL_TYPE) = UPPER(?) and UPPER(DESIGNATION) =UPPER(?))) and RULE_TYPE = ?");
                        pstmt.setString(1,city);
                        pstmt.setString(2,zone);
                        pstmt.setString(3,channelType);
                        pstmt.setString(4,salesType);
                        pstmt.setString(5,commType);
                    }
                    
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        status = false;
                    } else {
                        status = true;
                    }
                }
            }
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw e;
        } finally{
            //con.commit();
            //con.setAutoCommit(true);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
    }

    public List getPlanList(Connection con, CommissionPlanVO objCommVO) {
        return null;
    }
    public List getPlan(String plan_id)throws SQLException,Exception
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List lPlanList = null; 
        String PLAN;
        try{
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement("select channel_id,nvl(COMMISSION_MODE ,1),city,product_id,rule_type,to_char(cp.plan_start_date,'dd-mm-yyyy'),to_char(cp.plan_end_date,'dd-mm-yyyy'),description,status from COMMISSION_PLAN_TBL cp,CHANNEL_CITY_ZONE_PLAN_TBL cczp,CITY_PRODUCT_PLAN_TBL cpp,COMMISSION_RULE_TBL cr,CITY_ZONE_TBL cz where cp.id=cczp.commission_plan_id and cczp.id=city_zone_plan_id and cp.id=cr.plan_id(+) and city_zone_id=cz.id and cp.id=to_number("+plan_id+")");
            rs = pstmt.executeQuery();
            lPlanList = new ArrayList();
            
            
            while(rs.next())
            {   
             
                CommissionPlanVO objpList=new CommissionPlanVO();
                objpList.setChannelId(rs.getString(1));
                objpList.setPlanType(rs.getString(2));
                objpList.setZone(rs.getString(3));   
                objpList.setProductId(rs.getString(4));
                objpList.setCommissionType(rs.getString(5));
                objpList.setValidFrom(rs.getString(6));
                objpList.setValidTo(rs.getString(7));
                objpList.setPlanName(rs.getString(8));
                objpList.setStatus(rs.getString(9));
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
    public boolean updateCommissionRule(Connection con,CommissionPlanVO objCommVO)throws SQLException, Exception{
        List objList = null;
        boolean status = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        con.setAutoCommit(false);
        
        //"Plan selected for City"+objCommVO.getCity()+",Zone"+objCommVO.getZone()+"for salespersonnel type"
        try{
            //long key = getMaxCommRuleKey(con);
            //String[] stepid = objCommVO.getStepId();
            String[] elfrom = objCommVO.getEligibilityFrom();
            String[] elto = objCommVO.getEligibilityTo();
            String[] amount = objCommVO.getAmount();
            String[] ruleid=objCommVO.getRuleId().split("[*]");
            String[] rule_detailid=objCommVO.getRule_Detail_Id().split("[*]");
            
            pstmt = con.prepareStatement("Update COMMISSION_RULE_TBL set TARGET_MEASURE=?,COMMISSION_TYPE=?,ELIGIBILITY_PERIOD=?,ELIGIBILITY_TARGET=?,MIN_ELIGIBILITY=?,ELIGIBILITY_MEASURE=? where id=?"); 
            if(!(objCommVO.getCommissionType().equalsIgnoreCase("Deduction"))){
             //   pstmt.setLong(1,key);
            //    pstmt.setString(2,objCommVO.getPlanId());
                if(objCommVO.getCommissionType().equalsIgnoreCase("Retention")){
                    pstmt.setString(1,"2");
                } else {
                    pstmt.setString(1,objCommVO.getTargetMeasure());
                }
                pstmt.setString(2,objCommVO.getCommType());
                if(objCommVO.getEligibilityPeriod() != ""){
                    pstmt.setString(3,objCommVO.getEligibilityPeriod());
                } else {
                    pstmt.setString(3,"-1");
                }
                if(objCommVO.getCommissionType().equalsIgnoreCase("Retention")){
                    pstmt.setString(4,"-1");
                    pstmt.setString(5,"-1");
                } else {
                    pstmt.setString(4,objCommVO.getEligibilityTarget());
                    pstmt.setString(5,objCommVO.getMinEligibility());
                }
          //      pstmt.setString(6,objCommVO.getPaymentId());
                if(objCommVO.getCommissionType().equalsIgnoreCase("Basic") || objCommVO.getCommissionType().equalsIgnoreCase("Promotional")){
                    pstmt.setString(6,objCommVO.getEligibilityMeasurePer());
                } else {
                    pstmt.setString(6,objCommVO.getEligibilityMeasure());
                }
                pstmt.setString(7,ruleid[0]);
            } else {
       //         pstmt.setLong(1,key);
       //         pstmt.setString(2,objCommVO.getPlanId());
       //         pstmt.setString(1,"4");
                pstmt.setString(1,"-1");
                pstmt.setString(2,"-1");
                pstmt.setString(3,objCommVO.getEligibilityPeriod());
                pstmt.setString(4,"-1");
                pstmt.setString(5,"-1");
       //         pstmt.setString(9,objCommVO.getPaymentId());
                pstmt.setString(6,objCommVO.getEligibilityMeasure());
                pstmt.setString(7,ruleid[0]);
            }
            if(pstmt.executeUpdate()>0){
                status = true;
            }
            if(!(objCommVO.getCommissionType().equalsIgnoreCase("Deduction"))){
                if(status){
                    int count = 1;
                    for(int i=0;i<rule_detailid.length;i++){
                        
                        if(elfrom[i] != ""){
                            
                            pstmt = con.prepareStatement("update COMMISSION_RULE_DETAIL_TBL set ELIGIBILITY_FROM=?,ELIGIBILITY_TO=?,AMOUNT=? where id=?");
                            
//                            pstmt.setLong(1,key);
//                            pstmt.setInt(2,count);
                            pstmt.setString(1,elfrom[i]);
                            if(elto[i].equals("")){
                                pstmt.setString(2,"99999");
                            } else {
                                pstmt.setString(2,elto[i]);
                            }
                            pstmt.setString(3,amount[i]);
                            pstmt.setString(4,rule_detailid[i]);
                            if(pstmt.executeUpdate()>0){
                                status = true;
                            }
                            count = count + 1;
                        }
                        
                    }
                    if(rule_detailid.length<elfrom.length)
                    {
                       for(int i=rule_detailid.length;i<elfrom.length;i++){
                        if(elfrom[i] != ""){
                            pstmt = con.prepareStatement("insert into COMMISSION_RULE_DETAIL_TBL (ID,RULE_ID,STEP_ID,ELIGIBILITY_FROM, ELIGIBILITY_TO ,AMOUNT ) "+
                                    " values (COMMISSION_RULE_DETAIL_SEQ.NEXTVAL,?,?,?,?,?)");
                            
                            pstmt.setString(1,ruleid[0]);
                            pstmt.setInt(2,count);
                            pstmt.setString(3,elfrom[i]);
                            if(elto[i].equals("")){
                                pstmt.setString(4,"99999");
                            } else {
                                pstmt.setString(4,elto[i]);
                            }
                            pstmt.setString(5,amount[i]);
                            if(pstmt.executeUpdate()>0){
                                status = true;
                            }                            

                            count = count + 1;
                        }
                        
                    }

                    }    
                }
            }
            con.commit();
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            con.rollback();
            throw e;
        } finally{
            con.commit();
            con.setAutoCommit(true);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
    }
    
}
