<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
  <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
  <div id="inner-content">
   <div class="shadow-box">
       <div class="shadow-box-inner">
        <form  action="./ModifySalesPersonnel.do" method="Post" onsubmit="javascript: return ValidateSalesForm(this);">
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                        <bean:define id="objSales"  name="objSale" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request"/>
                              <tr><td><input type="hidden" name="salesId" value="${objSales.salesId}"></td></tr>
									<input type="hidden" name="salestype" value="${objSales.salestype}">
                              <tr><td width="126"><label>Type</label></td>
                            <c:set var="salesType" value="${objSales.salestype}"/>
                             <td width="200"><%--Dont remove<select name="salestype" disabled>
                                    <c:choose>
                                             <c:when test="${salesType == WitribeConstants.TYPE_NSM}">
                                                    <option value="${WitribeConstants.TYPE_NSM}" selected>Sales Director</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${WitribeConstants.TYPE_NSM}">Sales Director</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${salesType == WitribeConstants.TYPE_RSM}">
                                                    <option value="${WitribeConstants.TYPE_RSM}" selected>RSM</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${WitribeConstants.TYPE_RSM}">RSM</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${salesType == WitribeConstants.TYPE_TL}">
                                                    <option value="${WitribeConstants.TYPE_TL}" selected>TL</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${WitribeConstants.TYPE_TL}">TL</option>
                                                </c:otherwise>
                                            </c:choose>
                                           <c:choose>
                                                <c:when test="${salesType == WitribeConstants.TYPE_CSE}">
                                                    <option value="${WitribeConstants.TYPE_CSE}" selected>CSE</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${WitribeConstants.TYPE_CSE}">CSE</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${salesType == WitribeConstants.TYPE_BO}">
                                                    <option value="${WitribeConstants.TYPE_BO}" selected>BO-Dealer</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${WitribeConstants.TYPE_BO}">BO-Dealer</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${salesType == WitribeConstants.TYPE_NBO}">
                                                    <option value="${WitribeConstants.TYPE_NBO}" selected>NBO-Dealer</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${WitribeConstants.TYPE_NBO}">NBO-Dealer</option>
                                                </c:otherwise>
                                            </c:choose>
                                            
                                            
                                            

                                </select>--%>
                            
                                        
                                             <c:if test="${salesType == WitribeConstants.TYPE_NSM}">
                                                    Sales Director
                                               </c:if>
                                               <c:if test="${salesType == WitribeConstants.TYPE_RSM}">
                                                    RSM
                                                </c:if>
                                                <c:if test="${salesType == WitribeConstants.TYPE_TL}">
                                                    TL
                                                </c:if>
                                                <c:if test="${salesType == WitribeConstants.TYPE_CSE}">
                                                   CSE
                                                </c:if>
                                                 <c:if test="${salesType == WitribeConstants.TYPE_CSR}">
                                                   CSR
                                                </c:if>
                                                </td>
                            <td width="134"><label>First Name</label><span class="mandatory">*</span></td>
                            <td width="180"><input type="text" class=txtxx name="firstname"  maxlength="30" value="${objSales.firstname}"/><p class="validationmsg"></p></td></tr>
                            <tr><td width="126"><label>Last Name</label><span class="mandatory">*</span></td>
                            <td width="200"><input type="text" class=txtxx name="lastname"  maxlength="29" value="${objSales.lastname}"/><p class="validationmsg"></p></td>
                            <td width="134"><label>Contact</label><span class="mandatory">*</span></td>
                            <td width="180"><input type="text" class=txtxx name="contactnumber" value="${objSales.contactnumber}" maxlength="16" onkeypress="javascript: return numbersonly(event);"/><p class="validationmsg"></p> </td></tr>
                            <tr><td width="126"><label>Email</label><span class="mandatory">*</span></td>
                            <td width="200"><input type="text"  class=txtxx name="email" value="${objSales.email}" maxlength="60"/><p class="validationmsg"></p></td>
                           <!-- <td width="134"><label>UserId</label></td>
                            <td width="180"><input type="text"  class=txtxx name="userId" value="${objSales.userId}" maxlength="15"/><p class="validationmsg"></p></td></tr>
                             <tr><td width="126"><label>Password</label></td>
                            <td width="200"><input type="password"  class=txtxx name="password" value="${objSales.password}" maxlength="15"/><p class="validationmsg"></p></td>-->
                            <td width="134"><label>House/Plot</label><span class="mandatory">*</span></td>
                            <td width="180"><input type="text"  class=txtxx name="plot" value="${objSales.plot}"  maxlength="30" class=txtxx/><p class="validationmsg"></p></td></tr>
                            <tr><td width="126"><label>Street</label><span class="mandatory">*</span></td>
                            <td width="200"><input type="text" class=txtxx name="street" value="${objSales.street}" maxlength="30"/><p class="validationmsg"></p></td>
                            <td width="134"><label>SubZone</label></td>
                            <td width="180"><input type="text"  class=txtxx name="subzone" value="${objSales.subzone}" class=txtxx maxlength="30" readonly/><p class="validationmsg"></p></td></tr>
                            <tr><td width="126"><label>Zone</label></td>
                            <td width="200"><input type="text"  class=txtxx name="zone" value="${objSales.zone}" class=txtxx maxlength="30" readonly /><p class="validationmsg"></p></td>
                            
                            <td width="134"><label>City</label></td>
                                 
                                <td width="180"><input type="text"  class=txtxx name="city" value="${objSales.city}" class=txtxx maxlength="30" readonly/></td></tr>

                            <c:set var="province" value="${objSales.province}"/>
                            <tr><td width="126"><label>Province</label></td>
								<td width="200"><input type="text"  class=txtxx name="province" value="${objSales.province}" class=txtxx maxlength="30" readonly/></td>
                            
                            <td width="134"><label>Country</label></td>
                            <td width="180"><input type="text" class=txtxx name="country" value="${objSales.country}" maxlength="30" readonly/><p class="validationmsg"></p></td></tr>
                            <tr><td ><label>Zip</label></td>
                            <td><input type="text" class=txtxx name="zip" value="${objSales.zip}" maxlength="10" onkeypress="javascript: return numbersonly(event);" readonly/><p class="validationmsg"></p></td>
                            <tr>
                                 <TD>&nbsp;</TD>
                                    <TD>&nbsp;</TD>
                                <TD align="left" ><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD> </tr>
        </table></form>
        </div>
        </div>
        </div>
        
   

                            
