<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.sales.bo.WitribeSalesBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<logic:notEmpty name="objRsmList">
    <logic:notEmpty name="objCityList">
        <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
</logic:notEmpty></logic:notEmpty>
<P><font color="red"><b>${errorString}</b></font></P>
        <div id="inner-content">
            <div class="shadow-box">
                <div class="shadow-box-inner">
                  <form action="./AddRsmToCity.do" method="Post">
                        <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                            <logic:notEmpty name="objRsmList">
                            <logic:notEmpty name="objCityList">
                            <tr><td><label>Select RSM</label><span class="mandatory">*</span></td>
                                <td><select name="salesId">
                                  <logic:iterate id="objRsm" name="objRsmList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                                       
                                            <option value="${objRsm.salesId}">${objRsm.salesId}-${objRsm.fullname}</option>
                                 </logic:iterate>
                            </select></td></tr>
                            
                            <tr><td><label>Select City</label><span class="mandatory">*</span></td>
                                <td><select name="city" >
                                       <logic:iterate id="objCity" name="objCityList" type="com.witribe.vo.CityVO" scope="request">
                                                  
                                            <option value="${objCity.addr_city}">${objCity.addr_city}</option>
                                 </logic:iterate>
                            </select></td></tr>
                          
                       
                        <TR>
                            <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD></TR>
                                       </logic:notEmpty>
                                       </logic:notEmpty>
                                            <logic:empty name="objRsmList">
                                                <tr><td colspan="2">No RSMs are Available to Assign City</td>
                                                </tr>
                                            </logic:empty>
                                            <logic:empty name="objCityList"> 
                                             <tr><td colspan="2">No Cities are Available to Assign To RSM</td>
                                                </tr>   
                                            </logic:empty>
                   </table>
                    </form>
                </div>
            </div>
        </div>
  
 

