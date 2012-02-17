<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,java.util.ArrayList,java.util.List, com.witribe.vo.CityVO, com.witribe.vo.AddressMappingVO,com.witribe.vo.ZoneVO, com.witribe.vo.SubzoneVO,com.witribe.sales.bo.WitribeSalesBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO,com.witribe.constants.WitribeConstants"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
    function bodyload(){
            //document.getElementById('newprovince').style.display = "none";
            //document.getElementById('newcity').style.display = "none";
            document.getElementById('newzone').style.display = "none";
            document.getElementById('newsubzone').style.display = "none";
            //alert("hi"+document.getElementById('country').value);
            //AddNewZoneSubzone(1,document.getElementById('country').value);
            
            }
</script>
<body onload="bodyload()">
    <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
    <% if(request.getAttribute(WitribeConstants.ERROR_STRING) != null) { %>
       <P><font color="red"><b><%=request.getAttribute(WitribeConstants.ERROR_STRING)%></b></font></P>
        <%}%>
    <%--<P><font color="red"><b>${errorString}</b></font></P>--%>
    <div id="inner-content">
        <div class="shadow-box">
            <div class="shadow-box-inner">
                <form action="./SubmitZoneSubzone.do" method="Post" name="addform" onsubmit="return ValidateZoneSubzone(this);">
                  
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">                            
                        <tr><td width="126"><label>Country</label> <span class="mandatory" id="companyspanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="countryspan" >*</span><!--<img id="countryspan" class="mandatory"  type=image src="images/key1 .gif"></img>--></td>
                        <td width="200"><input type="text" class=txtxx name="country" id="country" value="${WitribeConstants.COUNTRY}" maxlength="30" onblur="javascript: return checkDuplicate();" readonly/><p class="validationmsg"></p></td></tr>
                                  
                                      <c:if test="${role == WitribeConstants.TYPE_ADMIN || role == WitribeConstants.TYPE_NSM}">
                                          <logic:notEmpty name="addressList">   
                                          <td width="126"><label>Province</label><span class="mandatory" id="provincespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="provincespan" >*</span></td>
                                          <td width="200">
                                              <select id="province" name="province" onchange="AddNewZoneSubzone(2,this.options[this.selectedIndex].value);">
                                                  <option></option>
                                                  <logic:iterate id="objaddr" name="addressList" type="com.witribe.vo.AddressMappingVO" scope="request">
                                                      <option value="${objaddr.subAddress}">${objaddr.subAddress}</option>
                                                  </logic:iterate>
                                          </select><p class="validationmsg"></p></td> 
                                          </tr>
                                         <!-- <tr id="newprovince"><td  width="126"><label>Add Province</label><span class="mandatory">*</span></td>
                                          <td  width="200"><input type=text name="newprovince" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>-->
                                          <tr>
                                              <td width="126"><label>City</label><span class="mandatory" id="cityspanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="cityspan" >*</span></td>                        
                                              <td width="200">
                                                  <select name="city" id="city" onchange="return (checkDuplicate && AddNewZoneSubzone(3,this.options[this.selectedIndex].value));">
                                                      <option ></option>
                                                     <logic:iterate id="objCity" name="objCityList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                                    <option value="${objCity.city}">${objCity.city}</option>
                                                    </logic:iterate>
                                              </select><p class="validationmsg"></p></td>
                                              
                                          </tr>
                                          <!--<tr id="newcity"><td  width="126"><label>Add City</label><span class="mandatory">*</span></td>
                                          <td  width="200"><input type=text name="newcity" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>     -->
                                          <tr><td width="134"><label>Zone</label><span class="mandatory" id="zonespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;" id="zonespan" >*</span></td>
                                              <td width="180">
                                                  <select name="zone" id="zone" onchange="AddNewZoneSubzone(4,this.options[this.selectedIndex].value);">
                                                      <option ></option>
                                              </select><p class="validationmsg"></p></td>
                                          </tr>
                                          <tr id="newzone"><td  width="126"><label>Add Zone</label><span class="mandatory">*</span></td>
                                          <td  width="200"><input type=text name="newzone" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>
                                          <!--<input type="text"  name="zone" value="" class=txtxx maxlength="30"/><p class="validationmsg"></p></td>-->
                                          <tr><td width="126"><label>SubZone</label><span class="mandatory"id="subzonespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="subzonespan" >*</span></td>
                                              <td width="200" colspan="4"><!--<input type="text"  name="subzone" value="" class=txtxx maxlength="30"/><p class="validationmsg"></p></td>-->
                                                  <select name="subzone" id="subzone" onchange="CheckAddSubzone(this.options[this.selectedIndex].value);">
                                                      <option ></option>
                                              </select><p class="validationmsg"></p></td>
                                          </tr>
                                          <tr id="newsubzone"><td  width="126"><label>Add Subzone</label><span class="mandatory">*</span></td>
                                          <td  width="200"><input type=text name="newsubzone" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr> 
                                      </logic:notEmpty>         
                                </c:if>
                                <c:if test="${role == WitribeConstants.TYPE_RSM}">
                                     <logic:notEmpty name="objProvList"> 
                                         <logic:notEmpty name="objCityList"> 
                                             <td width="126"><label>Province</label><span class="mandatory" id="provincespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="provincespan" >*</span></td>
                                    <td width="200">
                                        <select id="province" name="province">
                                            <logic:iterate id="objProv" name="objProvList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                                <option value="${objProv.address}">${objProv.address}</option>
                                            </logic:iterate>
                                    </select><p class="validationmsg"></p></td> 
                                    </tr>
                                    <!--<tr id="newprovince"><td  width="126"><label>Add Province</label><span class="mandatory">*</span></td>
                                    <td  width="200"><input type=text name="newprovince" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>-->
                                    <tr>
                                        <td width="126"><label>City</label><span class="mandatory" id="cityspanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="cityspan" >*</span></td>                        
                                        <td width="200">
                                            <select name="city" id="city" onchange="return (AddNewZoneSubzone(3,this.options[this.selectedIndex].value));">
                                                <option ></option>
                                                <logic:iterate id="objCity" name="objCityList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                                    <option value="${objCity.city}">${objCity.city}</option>
                                                </logic:iterate>
                                        </select><p class="validationmsg"></p></td>
                                        
                                    </tr>
                                    <!--<tr id="newcity"><td  width="126"><label>Add City</label><span class="mandatory">*</span></td>
                                    <td  width="200"><input type=text name="newcity" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr> -->    
                                    <tr><td width="134"><label>Zone</label><span class="mandatory" id="zonespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;" id="zonespan" >*</span></td>
                                        <td width="180">
                                            <select name="zone" id="zone" onchange="AddNewZoneSubzone(4,this.options[this.selectedIndex].value);">
                                                <option ></option>
                                         </select><p class="validationmsg"></p></td>
                                    </tr>
                                    <tr id="newzone"><td  width="126"><label>Add Zone</label><span class="mandatory">*</span></td>
                                    <td  width="200"><input type=text name="newzone" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>
                                    <!--<input type="text"  name="zone" value="" class=txtxx maxlength="30"/><p class="validationmsg"></p></td>-->
                                    <tr><td width="126"><label>SubZone</label><span class="mandatory"id="subzonespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="subzonespan" >*</span></td>
                                        <td width="200" colspan="4"><!--<input type="text"  name="subzone" value="" class=txtxx maxlength="30"/><p class="validationmsg"></p></td>-->
                                            <select name="subzone" id="subzone" onchange="CheckAddSubzone(this.options[this.selectedIndex].value);">
                                                <option ></option>
                                        </select><p class="validationmsg"></p></td>
                                    </tr>
                                    <tr id="newsubzone"><td  width="126"><label>Add Subzone</label><span class="mandatory">*</span></td>
                                    <td  width="200"><input type=text name="newsubzone" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>               
                                    </logic:notEmpty>
                                     </logic:notEmpty>
                                </c:if> 
                                <c:if test="${role == WitribeConstants.TYPE_TL}">
                                <logic:notEmpty name="objProvList"> 
                                    <logic:notEmpty name="objCityList"> 
                                        <logic:notEmpty name="objZoneList"> 
                                            <td width="126"><label>Province</label><span class="mandatory" id="provincespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="provincespan" >*</span></td>
                                            <td width="200">
                                                <select id="province" name="province">
                                                    <logic:iterate id="objProv" name="objProvList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                                        <option value="${objProv.address}">${objProv.address}</option>
                                                    </logic:iterate>
                                            </select><p class="validationmsg"></p></td> 
                                            </tr>
                                            <!--<tr id="newprovince"><td  width="126"><label>Add Province</label><span class="mandatory">*</span></td>
                                            <td  width="200"><input type=text name="newprovince" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>-->
                                            <tr>
                                                <td width="126"><label>City</label><span class="mandatory" id="cityspanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="cityspan" >*</span></td>                        
                                                <td width="200">
                                                    <select name="city" id="city">
                                                       
                                                        <logic:iterate id="objCity" name="objCityList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                                            <option value="${objCity.address}">${objCity.address}</option>
                                                        </logic:iterate>
                                                </select><p class="validationmsg"></p></td>
                                                
                                            </tr>
                                            <!--<tr id="newcity"><td  width="126"><label>Add City</label><span class="mandatory">*</span></td>
                                            <td  width="200"><input type=text name="newcity" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>-->     
                                            <tr><td width="134"><label>Zone</label><span class="mandatory" id="zonespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;" id="zonespan" >*</span></td>
                                                <td width="180">
                                                    <select name="zone" id="zone" onchange="AddNewZoneSubzone(4,this.options[this.selectedIndex].value);">
                                                        <option ></option>
                                                        <logic:iterate id="objZone" name="objZoneList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                                            <option value="${objZone.zone}">${objZone.zone}</option>
                                                        </logic:iterate>
                                                </select><p class="validationmsg"></p></td>
                                            </tr>
                                            <tr id="newzone"><td  width="126"><label>Add Zone</label><span class="mandatory">*</span></td>
                                            <td  width="200"><input type=text name="newzone" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>
                                            <!--<input type="text"  name="zone" value="" class=txtxx maxlength="30"/><p class="validationmsg"></p></td>-->
                                            <tr><td width="126"><label>SubZone</label><span class="mandatory"id="subzonespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="subzonespan" >*</span></td>
                                                <td width="200" colspan="4"><!--<input type="text"  name="subzone" value="" class=txtxx maxlength="30"/><p class="validationmsg"></p></td>-->
                                                    <select name="subzone" id="subzone" onchange="CheckAddSubzone(this.options[this.selectedIndex].value);">
                                                        <option ></option>
                                                </select><p class="validationmsg"></p></td>
                                            </tr>
                                            <tr id="newsubzone"><td  width="126"><label>Add Subzone</label><span class="mandatory">*</span></td>
                                            <td  width="200"><input type=text name="newsubzone" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>               
                                        </logic:notEmpty>
                                    </logic:notEmpty>
                                </logic:notEmpty>
                                </c:if> 
                      
                        <TR>
                            <TD>&nbsp;</TD>
                            <TD align=left><INPUT class=submit-btn 
                                                      onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                      onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                      src="images/btn-submit-thickbox.gif" 
                                              name=input> </TD></TR>
                           <!-- <tr><td colspan="1"><input type="button" name="Add" value="Add" onclick="return CommonAdd('./SubmitZoneSubzone.do');"/></td>
                         <td colspan="1"><input type="button" name="Delete" value="Delete" onclick="return CommonDelete('./DeleteZoneSubzone.do');"/></td></tr>-->
                        
                        
                    </table>
                </form>
            </div>
        </div>
    </div>
</body>
 