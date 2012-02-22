<%@page contentType="text/html" import="com.witribe.bo.WitribeBO"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<script language="javascript">
            function formSubmit(actionName){
                document.leadform.action=actionName;
                document.leadform.submit();
            }
          
        </script>
        <logic:notEmpty name="salesList" >
             <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
        </logic:notEmpty>
 <div id="inner-content">
       <div class="shadow-box">
            <div class="shadow-box-inner">
          <form  name="leadform" action="./AssignCSE.do" method="Post" >
                    
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                            <logic:notEmpty name="salesList" >
                            <% WitribeBO wb = new WitribeBO();%>
                            
                            <tr><td><label>Lead Id</label></td>
                                <td>${leadId} </td></tr>
                                <c:if test="${not empty salesObj}" >
                                      <tr><td><label>Current SalesId</label></td>
                                      <c:set var="salesType" value="${salesObj.salestype}"/>
                                    <%String salesTypeName =  wb.SalesType((String)pageContext.getAttribute("salesType"));%>
                                      <td>${salesObj.salesId}-${salesObj.fullname}-<%=salesTypeName%></td></tr>
                                </c:if>
                            <tr><td><label>Assign To</label><span class="mandatory">*</span></td>
                                <td><select name="assignedCSE" >
                                <logic:iterate id="objSales" name="salesList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                    <c:set var="salesType" value="${objSales.salestype}"/>
                                    <%String salesTypeName =  wb.SalesType((String)pageContext.getAttribute("salesType"));%>
                                       <option value="${objSales.salesId}">${objSales.salesId}-${objSales.fullname}-<%=salesTypeName%></option>
                                 </logic:iterate> 
                             
                          </select></td></tr>
                         <input type="hidden" name="leadId" value="${leadId}" />  
                        <TR>
                            <td>&nbsp;</td>
                        <TD align=right><INPUT class=submit-btn 
                                                  onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                  onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                  src="images/btn-submit-thickbox.gif" 
                                          name=input> </TD></TR>
                        
                           </logic:notEmpty >
                           <logic:empty name="salesList">
                                <tr><td colspan="3"><label>Lead Address not matching with any CSE or any one in the hierachy !</label></td>
                            </tr>
                             <tr><td colspan="2">&nbsp;</td>
                            </tr>
                             <tr><td colspan="2">&nbsp;</td>
                            </tr>
                             <tr><td colspan="2">&nbsp;</td>
                            </tr>
                            <tr><td colspan="2">&nbsp;</td>
                            </tr>
                           </logic:empty>
        </table></form>
        </div>
        </div>
        </div>
   
