<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.sales.bo.WitribeSalesBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.ShopsVO"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<P><font color="red"><b>${errorString}</b></font></P>
<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <form action="./AddZonetoShop.do" method="Post">
                <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                    <logic:notEmpty name="objshopslist">
                        <tr><td>Select Shop ID</td>
                            <td><select name="shopId">
                                    <logic:iterate id="objshop" name="objshopslist" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                      <option value="${objshop.shopId}">${objshop.shopId}</option>
                                 </logic:iterate>
                        </select></td></tr>
                        <tr><td>Zone</td>
                        <td><input type="text" name="zone" value="" class="txtxx"/></td></tr>                                      
                        <TR>
                            <TD>&nbsp;</TD>
                            <TD align=left><INPUT class=submit-btn 
                                                      onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                      onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                      src="images/btn-submit-thickbox.gif" 
                                              name=input> </TD></TR>
                    </logic:notEmpty>
                    <logic:empty name="objshopslist">
                        <tr><td colspan="2">No Shops Available</td>
                        </tr>
                    </logic:empty>
                </table>
            </form>
        </div>
    </div>
</div>



