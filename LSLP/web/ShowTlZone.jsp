<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.sales.bo.WitribeSalesBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<logic:notEmpty name="objTlList">
    <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
</logic:notEmpty>
<P><font color="red"><b>${errorString}</b></font></P>
        <div id="inner-content">
            <div class="shadow-box">
                <div class="shadow-box-inner">
                  <form action="./AddTlZone.do" method="Post" onsubmit="javascript: return ValidateZone(this)">
                        <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                             <logic:notEmpty name="objTlList">
                                 <tr><td><label>Select TL</label><span class="mandatory">*</span></td>
                                <td><select name="salesId">
                                  <logic:iterate id="objTl" name="objTlList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                      <option value="${objTl.salesId}">${objTl.salesId}-${objTl.fullname}</option>
                                 </logic:iterate>
                            </select></td></tr>
                           
                            <tr><td><label>Zone</label><span class="mandatory">*</span></td>
                            <td><input type="text" name="zone" value="" maxlength="30" class="txtxx"/><p class="validationmsg"></p></td></tr>
                      
                       
                        <TR>
                            <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD></TR>
                                       </logic:notEmpty>
                                        <logic:empty name="objTlList">
                                                <tr><td colspan="2">No TLs Available</td>
                                                </tr>
                                            </logic:empty>
                   </table>
                    </form>
                </div>
            </div>
        </div>
  
 

