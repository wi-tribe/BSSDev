<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.sales.bo.WitribeSalesBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
 <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner"> 
          
            <form action="./SumbitSubShop.do" method="post">
                <input type="hidden" name="page" />
                <input type="hidden" name="flag" />
                <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                   
                        <tr><td><label>Shop</label><span class="mandatory">*</span></td>
                            <logic:notEmpty name="objShopsList">
                            <td><select name="shopId">   
                                    <logic:iterate id="objShop" name="objShopsList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        
                                        <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option> 
                                        
                                    </logic:iterate>  
                                    
                        </select></td></tr>
                    </logic:notEmpty>
                       <logic:empty name="objShopsList">
                        <td><label>No shop's available for mapping.</label></td>
                    </logic:empty>
                    <tr><td><label>Kiosk</label><span class="mandatory">*</span></td>
                        <logic:notEmpty name="objkShopsList">
                        <td><select name="subShopId">
                                <logic:iterate id="objShops" name="objkShopsList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                    
                                    <option value="${objShops.shopId}">${objShops.shopId}-${objShops.shopname}</option> 
                                    
                                </logic:iterate>  
                    </select></td></tr> 
                    </logic:notEmpty>
                    <logic:empty name="objkShopsList">
                        <td><label>No Kiosks available for mapping.</label></td>
                    </logic:empty>
                    <logic:notEmpty name="objShopsList">  
                          <logic:notEmpty name="objkShopsList">
                 
                    <TR>
                            <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD></TR>
                    </logic:notEmpty>  
                     </logic:notEmpty> 
                    <%--<%}//if
                                else{%>
                                  <td><label>No child's availabe for hierarchy</label></td>  
                                <%}//else
                                }//try
                                catch(Exception e){}%>--%>
                    <!-- <option value="1">1</option>
                                        <option value="2" selected>2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>-->
                    
                 
                     
                    
                </table>
                
        </form></div> 
    </div>
</div>



