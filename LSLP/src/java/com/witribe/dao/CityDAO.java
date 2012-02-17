/*
 * CityDAO.java
 *
 * Created on February 4, 2009, 8:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.dao;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.util.DBUtil;
import com.witribe.vo.AddressMappingVO;
import com.witribe.vo.CityVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.varia.NullAppender;
/**
 *
 * @author HY27465
 */
public class CityDAO {
    
    /** Creates a new instance of CityDAO */
    public CityDAO() {
    }
    
    public boolean createAddressMapping(AddressMappingVO addrVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        try{
            con.setAutoCommit(false);
            String type = null;
            String address = null;
            String subAddress = null;
        /*if("Add New".equalsIgnoreCase(addrVO.getProvince())){
            type = "1";
            address = addrVO.getCountry();
            subAddress = addrVO.getNewprovince();
            addAddressMapping(type,address,subAddress,con);
        } if("Add New".equalsIgnoreCase(addrVO.getCity())){
            type = "2";
             address = addrVO.getProvince();
             if("Add New".equalsIgnoreCase(address)){
               address =   addrVO.getNewprovince();
             }
             subAddress = addrVO.getNewcity();
             addAddressMapping(type,address,subAddress,con);
        } */
            if("Add New".equalsIgnoreCase(addrVO.getZone()) && "Add New".equalsIgnoreCase(addrVO.getSubzone())){
                if("Add New".equalsIgnoreCase(addrVO.getZone())){
                    type = "3";
                    address =   addrVO.getCity();
                    subAddress = addrVO.getNewzone();
                    status = addAddressMapping(type,address,subAddress,con);
                }
                if("Add New".equalsIgnoreCase(addrVO.getSubzone())){
                    type = "4";
                    address =   addrVO.getNewzone();
                    subAddress = addrVO.getNewsubzone();
                    status = addAddressMapping(type,address,subAddress,con);
                }
            }
            if("Add New".equalsIgnoreCase(addrVO.getZone())){
                type = "3";
                address = addrVO.getCity();
                subAddress = addrVO.getNewzone();
                if("Add New".equalsIgnoreCase(addrVO.getZone())){
                    //address =   addrVO.getNewcity();
                    status = addAddressMapping(type,address,subAddress,con);
                }
                
                
            } if("Add New".equalsIgnoreCase(addrVO.getSubzone())){
                type = "4";
                address = addrVO.getZone();
                subAddress = addrVO.getNewsubzone();
                if("Add New".equalsIgnoreCase(addrVO.getSubzone())){
                    //address =   addrVO.getNewzone();
                    status = addAddressMapping(type,address,subAddress,con);
                }
                
            }
            con.commit();
        } finally {
            con.setAutoCommit(true);
        }
        return status;
        
    }
    
    public boolean checkDuplicate(AddressMappingVO addrVO,Connection con)  throws SQLException,Exception  {
        boolean status = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String zone = addrVO.getZone();
        String subzone = addrVO.getSubzone();
        String newZone = addrVO.getNewzone();
        String newsubzone = addrVO.getNewsubzone();
        String city = addrVO.getCity();
        try{
            
            pstmt = con.prepareStatement(
                    "select type,address,sub_address from address_mapping where UPPER(sub_address) = UPPER(?) and UPPER(address) = UPPER(?)");
            
            if("Add new".equalsIgnoreCase(subzone)){
                pstmt.setString(1,newsubzone);
                pstmt.setString(2,zone);
            } else if("Add new".equalsIgnoreCase(zone)){
                pstmt.setString(1,newZone);
                pstmt.setString(2,city);
            } else if(!"Add New".equalsIgnoreCase(zone)){
                pstmt.setString(1,zone);
                pstmt.setString(2,city);
            } else if(!"Add new".equalsIgnoreCase(subzone)){
                pstmt.setString(1,subzone);
                pstmt.setString(2,zone);
            } else if(!"Add New".equalsIgnoreCase(zone) && "Add new".equalsIgnoreCase(subzone)){
                pstmt.setString(1,newsubzone);
                pstmt.setString(2,zone);
            }
            
            rs = pstmt.executeQuery();
            if(rs.next()){
                status = true;
            }
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        }  finally{
            
            DBUtil.closeStatement(pstmt);
        }
        return status;
    }
    public boolean addAddressMapping(String type,String address,String subAddress,Connection con)  throws SQLException,Exception  {
        boolean status = false;
        PreparedStatement pstmt = null;
        try{
            if(subAddress != null || subAddress != ""){
                if(!address.equalsIgnoreCase(subAddress)){
                    pstmt = con.prepareStatement(
                            "INSERT INTO ADDRESS_MAPPING (TYPE,ADDRESS,SUB_ADDRESS,DISPLAY_VALUE)" +
                            "VALUES " +
                            "(?,?,?,1)");
                } else if(address.equalsIgnoreCase(subAddress)){
                    pstmt = con.prepareStatement(
                            "INSERT INTO ADDRESS_MAPPING (TYPE,ADDRESS,SUB_ADDRESS,DISPLAY_VALUE)" +
                            "VALUES " +
                            "(?,?,?,0)");
                }
            }
            pstmt.setString(1,type);
            pstmt.setString(2,address);
            pstmt.setString(3,subAddress);
            if(pstmt.executeUpdate()>0){
                status = true;
            }
            
            
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        }  finally{
            
            DBUtil.closeStatement(pstmt);
        }
        return status;
    }
    public List getCities(Connection con)throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            pstmt = con.prepareStatement("SELECT ADDR_CITY,ADDR_PROVINCE FROM CITY");
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            
            
            while(rs.next()) {
                CityVO objCityVO= new CityVO();
                //objCityVO.setAddr_city_code(rs.getInt(1));
                objCityVO.setAddr_city(rs.getString(1));
                objCityVO.setAddr_province(rs.getString(2));
                objList.add(objCityVO);
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
    
    public List getSubAddress(Connection con, String type, String address,String flag)throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String queryCont = "";
        if(Integer.parseInt(flag)==1){
            queryCont =" and display_value = 1";
        }
        
        try{
            // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            pstmt = con.prepareStatement("SELECT SUB_ADDRESS FROM ADDRESS_MAPPING WHERE UPPER(TYPE) =UPPER(?) AND UPPER(ADDRESS)=UPPER(?)"+queryCont);
            pstmt.setString(1,type);
            pstmt.setString(2,address);
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            
            
            while(rs.next()) {
                AddressMappingVO objAddressVO= new AddressMappingVO();
                //objCityVO.setAddr_city_code(rs.getInt(1));
                objAddressVO.setSubAddress(rs.getString(1));
                objList.add(objAddressVO);
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
    
    public List getSubAddressByRole(Connection con, String type,String flag,List objlst,SalesPersonnelVO objsaleVO)throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = null;
        String queryCont = "";
        String role = objsaleVO.getSalestype();
        StringBuffer inString = null;
        SalesPersonnelVO objsale = null;
        
        if(Integer.parseInt(flag)==1){
            queryCont =" and display_value = 1";
        }
        
        try{
            // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            
            inString = new StringBuffer("(");
            
            if(objlst != null && objlst.size() > WitribeConstants.ZERO) {
                if(objlst.size()>1) {
                    for(int i=0;i<objlst.size()-1;i++){
                        objsale = (SalesPersonnelVO)objlst.get(i);
                        inString.append("'");
                        if(role.equals(WitribeConstants.TYPE_RSM)){
                            inString.append(objsale.getCity());
                        } else if(role.equals(WitribeConstants.TYPE_TL)){
                            if(!(type.equals("2"))){
                                inString.append(objsale.getZone());
                            } else {
                                inString.append(objsale.getAddress());
                            }
                        }
                        inString.append("'");
                        inString.append(",");
                    }
                    objsale = (SalesPersonnelVO)objlst.get(objlst.size()-1);
                    inString.append("'");
                    if(role.equals(WitribeConstants.TYPE_RSM)){
                        inString.append(objsale.getCity());
                    } else if(role.equals(WitribeConstants.TYPE_TL)){
                        if(!(type.equals("2"))){
                            inString.append(objsale.getZone());
                        } else {
                            inString.append(objsale.getAddress());
                        }
                    } else if(role.equals(WitribeConstants.TYPE_CSE)){
                        if(type.equals("4")){
                            inString.append(objsale.getSubzone());
                        } else {
                            inString.append(objsale.getAddress());
                        }
                    }
                    inString.append("'");
                } else {
                    objsale = (SalesPersonnelVO)objlst.get(0);
                    inString.append("'");
                    if(role.equals(WitribeConstants.TYPE_RSM)){
                        inString.append(objsale.getCity());
                    } else if(role.equals(WitribeConstants.TYPE_TL)){
                        if(!(type.equals("2"))){
                            inString.append(objsale.getZone());
                        } else {
                            inString.append(objsale.getAddress());
                        }
                    } else if(role.equals(WitribeConstants.TYPE_CSE)){
                        if(type.equals("4")){
                            inString.append(objsale.getSubzone());
                        } else {
                            inString.append(objsale.getAddress());
                        }
                    }
                    inString.append("'");
                }
                inString.append(")");
            }
            query = "select ADDRESS FROM ADDRESS_MAPPING  where UPPER(TYPE) = UPPER(?) AND UPPER(SUB_ADDRESS) in "+inString.toString().toUpperCase()+queryCont;
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,type);
            // pstmt.setString(2,salesId);
            
            
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            String name = null;
            
            while(rs.next()) {
                SalesPersonnelVO objAddressVO= new SalesPersonnelVO();
                
                //objCityVO.setAddr_city_code(rs.getInt(1));
                if(!(rs.getString(1).equalsIgnoreCase(name))){
                    objAddressVO.setAddress(rs.getString(1));
                    name = objAddressVO.getAddress();
                    objList.add(objAddressVO);
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
    
    
    public List getCityList(Connection con, SalesPersonnelVO objsalesVO) throws SQLException,Exception {
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int role = Integer.parseInt(objsalesVO.getSalestype());
        String query = null;
        String country = objsalesVO.getCountry();
        String salesId = objsalesVO.getSalesId();
        String city = objsalesVO.getCity();
        String zone = objsalesVO.getZone();
        try{
            // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            pstmt = con.prepareStatement("select UPPER(location) from sales_location where salespersonnel_id = ?");
            pstmt.setString(1,salesId);
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            
            
            while(rs.next()) {
                SalesPersonnelVO objsales = new SalesPersonnelVO();
                objsales.setCity(rs.getString(1));
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
    public List getZoneList(Connection con,SalesPersonnelVO objsalesVO) throws SQLException,Exception {
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String queryCont = "";
        int role = Integer.parseInt(objsalesVO.getSalestype());
        String query = null;
        String country = objsalesVO.getCountry();
        String salesId = objsalesVO.getSalesId();
        String city = objsalesVO.getCity();
        String zone = objsalesVO.getZone();
        try{
            // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            pstmt = con.prepareStatement("select UPPER(location) from sales_location where salespersonnel_id = ?");
            pstmt.setString(1,salesId);
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            
            
            while(rs.next()) {
                SalesPersonnelVO objsale = new SalesPersonnelVO();
                objsale.setZone(rs.getString(1));
                objList.add(objsale);
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
    public List getSubzoneList(Connection con,SalesPersonnelVO objsalesVO) throws SQLException,Exception {
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String queryCont = "";
        int role = Integer.parseInt(objsalesVO.getSalestype());
        String query = null;
        String country = objsalesVO.getCountry();
        String salesId = objsalesVO.getSalesId();
        String city = objsalesVO.getCity();
        String zone = objsalesVO.getZone();
        try{
            // pstmt = con.prepareStatement("SELECT ADDR_CITY_CODE, ADDR_CITY, ADDR_PROVINCE FROM CITY");
            pstmt = con.prepareStatement("select UPPER(location) from sales_location where salespersonnel_id = ?");
            pstmt.setString(1,salesId);
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            
            
            while(rs.next()) {
                SalesPersonnelVO objsales = new SalesPersonnelVO();
                objsales.setSubzone(rs.getString(1));
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
    public boolean checkAssigned(AddressMappingVO addrVO,Connection con) throws SQLException,Exception {
        boolean status = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String zone = addrVO.getZone();
        String subzone = addrVO.getSubzone();
        
        String city = addrVO.getCity();
        try{
            
            pstmt = con.prepareStatement(
                    "select SALESPERSONNEL_ID, LOCATION, CREATE_DATE from SALES_LOCATION where UPPER(LOCATION) in (UPPER(?),UPPER(?))");
            
            pstmt.setString(1,zone);
            pstmt.setString(2,subzone);
            rs = pstmt.executeQuery();
            if(rs.next()){
                status = true;
            }
            if(!status){
                pstmt = con.prepareStatement(
                        "select sd.ADDR_ZONE,sd.ADDR_SUBZONE from  SHOP_SALESID_MAPPING sp,SHOP_DETAILS sd where sd.SHOP_ID = sp.SHOP_ID "+
                        " and UPPER(sd.ADDR_ZONE) = UPPER(?) and UPPER(sd.ADDR_SUBZONE) = UPPER(?)");
                
                pstmt.setString(1,zone);
                pstmt.setString(2,subzone);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    status = true;
                }
            }
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        }  finally{
            
            DBUtil.closeStatement(pstmt);
        }
        return status;
    }
    public boolean deleteAddressMapping(AddressMappingVO addrVO,Connection con) throws SQLException,Exception {
        boolean status = false;
        PreparedStatement pstmt = null;
        String zone = addrVO.getZone();
        String subzone = addrVO.getSubzone();
        String city = addrVO.getCity();
        con.setAutoCommit(false);
        try{
            pstmt = con.prepareStatement("Delete from ADDRESS_MAPPING where UPPER(address) = UPPER(?) and UPPER(sub_address) = UPPER(?)");
            pstmt.setString(1,city);
            pstmt.setString(2,zone);
            if(pstmt.executeUpdate()>0){
                status = true;
            }
            if(status && subzone !=""){
             pstmt = con.prepareStatement("Delete from ADDRESS_MAPPING where UPPER(address) = UPPER(?) and UPPER(sub_address) = UPPER(?)");
            pstmt.setString(1,zone);
            pstmt.setString(2,subzone);
            if(pstmt.executeUpdate()>0){
                status = true;
            }   
            }
            con.commit();
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        }  finally{
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        return status;
    }
    
}


