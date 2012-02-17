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
             <form  action="./ModifyShop.do" method="Post" onsubmit="javascript: return ValidateShopForm(this);">
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                        <bean:define id="objShops"  name="objShop" type="com.witribe.sales.vo.ShopsVO" scope="request"/>
                              <tr><td><input type="hidden" name="shopId" value="${objShops.shopId}"></td></tr>
                              <tr><td width="126"><label>Shop Name</label><span class="mandatory">*</span></td>
                            <td width="200"><input type="text" class=txtxx name="shopname"  maxlength="30" value="${objShops.shopname}"/><p class="validationmsg"></p></td>
                            <td width="134"><label>Type</label></td>
                           <c:set var="shopType" value="${objShops.shopType}"/>
                             <td width="180"><%--<select name="shopType" disabled>
                                            <c:choose>
                                                <c:when test="${shopType == '1'}">
                                                    <option value="1" selected>Wi-Tribe owned</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="1">Wi-Tribe owned</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${shopType == '2'}">
                                                    <option value="2" selected>Kiosks</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="2">Kiosks</option>
                                                </c:otherwise>
                                            </c:choose>
                                            
                                </select>--%>
                            <c:if test="${shopType == '1'}">
                              Wi-Tribe owned
                             </c:if>
                            <c:if test="${shopType == '2'}">
                             Kiosks
                             </c:if></td></tr>
                            <tr><td width="126"><label>House/Plot</label><span class="mandatory">*</span></td>
                            <td width="200"><input type="text"  class=txtxx name="plot" value="${objShops.plot}"  maxlength="30" class=txtxx/><p class="validationmsg"></p></td>
                            <td width="134"><label>Street</label><span class="mandatory">*</span></td>
                            <td width="180"><input type="text" class=txtxx name="street" value="${objShops.street}" maxlength="30"/><p class="validationmsg"></p></td></tr>
                            <tr><td width="126"><label>SubZone</label></td>
                            <td width="200"><input type="text"  class=txtxx name="subzone" value="${objShops.subzone}" class=txtxx maxlength="30" disabled/><p class="validationmsg"></p></td>
                            <td width="134"><label>Zone</label></td>
                            <td width="180"><input type="text"  class=txtxx name="zone" value="${objShops.zone}" class=txtxx maxlength="30" disabled/><p class="validationmsg"></p></td></tr>
                          
                             <tr><td width="126"><label>City</label></td>
                             
                                <td width="200"><input type="text"  class=txtxx name="city" value="${objShops.city}" class=txtxx maxlength="30" disabled/></td>
                            
                            <td width="134"><label>Province</label></td>
                             <td width="200"><input type="text"  class=txtxx name="province" value="${objShops.province}" class=txtxx maxlength="30" disabled/></td>
                            <!--<td width="180"><input type="text" name="province" class=txtxx value="" class=txtxx maxlength="60"/><p class="validationmsg"></p></td></tr>-->
                            <tr><td width="126"><label>Country</label></td>
                            <td width="200"><input type="text" class=txtxx name="country" value="${objShops.country}" maxlength="30"disabled/><p class="validationmsg" disabled></p></td>
                            <td width="134"><label>Zip</label></td>
                            <td width="180"><input type="text" class=txtxx name="zip" value="${objShops.zip}" maxlength="30" disabled/><p class="validationmsg"></p></td></tr>
          <tr><TD>&nbsp;</TD>
                                    <TD>&nbsp;</TD>
                        <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD>
                        
        </table></form></div>
        </div>
        </div>
    
