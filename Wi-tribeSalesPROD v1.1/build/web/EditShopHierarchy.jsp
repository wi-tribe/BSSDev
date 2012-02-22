<%@page contentType="text/html" import="com.witribe.sales.vo.ShopsVO,com.witribe.bo.WitribeBO,com.witribe.sales.vo.SalesPersonnelVO,java.util.ArrayList,java.util.List, com.witribe.sales.bo.WitribeSalesBO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>

<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <form action="./UpdateShopHierarchy.do" method="Post">
                <input type="hidden" name="page" />
                <input type="hidden" name="flag" />
                <div id="inner-content">
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                        <tr><td width="10%" align="left"><label>Kiosk Id</label></td>
                            <%try{String[] arr = request.getParameterValues("check");
                            String subshopid = arr[0];%>
                           <td width="10%" align="left"><select name="subShopId">
                                    <option value="<%=subshopid%>"><%=subshopid%></option> 
                                <!--<input type = text name="child_salespersonnel_id" disabled="disabled" value="childid">-->
                            <%}catch(Exception e){}%>
                        </tr> 
                        <tr><td><label>Shop</label></td>
                            <logic:notEmpty name="objShopsList">
                            <td><select name="shopId">   
                                    <logic:iterate id="objShop" name="objShopsList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        
                                        <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}-${objShop.shopType}</option> 
                                        
                                    </logic:iterate>  
                                    
                        </select></td></tr>
                    </logic:notEmpty>
                        
                        <tr><td></td><TD align=left>
                                     
                                          <INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input>  </TD></TR>
                                               
            </table></div></form>
        </div>
    </div>
</div>
   
            


