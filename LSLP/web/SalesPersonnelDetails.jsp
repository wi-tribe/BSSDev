<%@page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.sales.vo.ChannelVO,java.util.ArrayList,java.util.List, com.witribe.vo.CityVO, com.witribe.vo.AddressMappingVO,com.witribe.vo.ZoneVO, com.witribe.vo.SubzoneVO,com.witribe.sales.bo.WitribeSalesBO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<body>
    <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.<br> Fields marked with hash (<span class="mandatory" style="font-size:12px;">#</span>) are mandatory and key fields for Sales Personnel.</p>
    <P><font color="red"><b>${errorString}</b></font></P>
    <div id="inner-content">
        <div class="shadow-box">
            <div class="shadow-box-inner">
                <form id="salesform" name="salesform" action="./SalesPersonnel.do" method="post" onsubmit="javascript: return ValidateSalesForm(this);">
                    <%WitribeBO wbo = new WitribeBO();
                    String prov = null;
                    //String province[] = null;%>
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                        <tr id="salestypeid"><td><label>Sales Type</label><span class="mandatory">*</span></td>
                            <td><select name="salestype" id="salestype" onchange="javascript: return checkDuplicate();">
                                    <c:if test="${role == WitribeConstants.TYPE_ADMIN}">
                                        <option value="${WitribeConstants.TYPE_CSR}">CSR</option>
                                        <c:if test="${NSM_PRESENT == 'false'}">
                                            <option value="${WitribeConstants.TYPE_NSM}">Sales Director</option>
                                        </c:if>
                                        <option value="${WitribeConstants.TYPE_RSM}">RSM</option>
                                        <option value="${WitribeConstants.TYPE_TL}">TL</option>
                                    </c:if> 
                                    <c:if test="${role == WitribeConstants.TYPE_NSM}">
                                        <option value="${WitribeConstants.TYPE_RSM}">RSM</option>
                                        <option value="${WitribeConstants.TYPE_TL}">TL</option>
                                    </c:if> 
                                    <c:if test="${role == WitribeConstants.TYPE_RSM}">
                                        <option value="${WitribeConstants.TYPE_TL}">TL</option>
                                    </c:if>
                                    <option value="${WitribeConstants.TYPE_CSE}" selected>CSE</option>
                                    <!--<option value="${WitribeConstants.TYPE_BO}">BO-Dealer</option>
                                    <option value="${WitribeConstants.TYPE_NBO}">NBO-Dealer</option>-->
                                      
                        </select><p class="validationmsg"></p></td></tr>
                        <tr id="channeltypeid"><td><label>Channel Type</label><span class="mandatory">*</span></td>
                            <td colspan="4"><select name="channeltype" id="channeltype">
                                     <%
                                    try
                                    {
                                    List chnList = new ArrayList();
                                    WitribeSalesBO objBO = new WitribeSalesBO();
                                    ChannelVO chnVO = new ChannelVO();
                                    chnList = objBO.getNewChannelList(chnVO);
                                    int chnsize = chnList.size();
                                    %>
                                     <%  for (int i=0;i<chnsize;i++)                                   
                                        {
                                        chnVO = (ChannelVO)chnList.get(i);                                
                                        String chnId = chnVO.getChannelId();   
                                        String name = chnVO.getChannelName();

                                        %> 
                                <option value="<%=chnId%>"><%=name%></option> 
                                <% } }catch(Exception e){}%>
                                    <%--<option value="${WitribeConstants.CSE_DIRECT_SALE}">Direct Sale</option>
                                    <option value="${WitribeConstants.CSE_SHOP_SALES_EXECUTIVE}">Shop-Sales Executive</option>
                                    <option value="${WitribeConstants.CSE_BD}">BD-Business development</option>
                                    <option value="${WitribeConstants.CSE_KIOSKS}">Kiosks</option>
                                    <option value="${WitribeConstants.CSE_BO}">BO</option>
                                    <option value="${WitribeConstants.CSE_NBO}">NBO</option>--%>
                        </select></td></tr>
                        <tr><td width="126"><label>First Name</label><span class="mandatory">*</span></td>
                            <td width="200"><input type="text" class=txtxx name="firstname" value="" maxlength="30"/><p class="validationmsg"></p></td>
                            <td width="134"><label>Last Name</label><span class="mandatory">*</span></td>
                        <td width="180"><input type="text" class=txtxx name="lastname" value="" maxlength="29"/><p class="validationmsg"></p></td></tr>
                        <tr><td width="126"><label>Contact</label><span class="mandatory">*</span></td>
                            <td width="200"><input type="text" class=txtxx name="contactnumber" value="" maxlength="16" /><p class="validationmsg"></p> </td>
                            <td width="134"><label>Email</label><span class="mandatory">*</span></td>
                        <td width="180"><input type="text"  class=txtxx name="email" value="" maxlength="60"/><p class="validationmsg"></p></td></tr>
                        
                        <tr><td width="126"><label>House/Plot</label><span class="mandatory">*</span></td>
                            <td width="200"><input type="text"  class=txtxx name="plot" value="" class=txtxx maxlength="30"/><p class="validationmsg"></p></td>
                            <td width="134"><label>Street</label><span class="mandatory">*</span></td>
                        <td width="180"><input type="text" class=txtxx name="street" value="" maxlength="30"/><p class="validationmsg"></p></td></tr>
                         <tr><td width="126"><label>Country</label> <span class="mandatory" id="companyspanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="countryspan" >#</span><!--<img id="countryspan" class="mandatory"  type=image src="images/key1 .gif"></img>--></td>
                            <td width="200"><input type="text" class=txtxx name="country" id="country" value="${WitribeConstants.COUNTRY}" maxlength="30" onblur="javascript: return checkDuplicate();" readonly/><p class="validationmsg"></p></td>
                            <td width="134"><label>Province</label><span class="mandatory">*</span></td>
                        
                        <%try{
                                List objList1 = new ArrayList(); 
                                objList1 = wbo.getSubAddress("1","Pakistan","0");
                                int listSize1 = objList1.size();                               
                                AddressMappingVO objAddressVO = new AddressMappingVO();
                                
                                %>
                                 <td width="200">
                                <select id="province" name="province" onchange="fetchSubAddress(2,this.options[this.selectedIndex].value);">
                                    <option></option> 
                                <%  for (int i=0;i<listSize1;i++)                                   
                                {
                                objAddressVO = (AddressMappingVO)objList1.get(i);                                
                                String pro = objAddressVO.getSubAddress();                                
                                
                                %> 
                                <option value="<%=pro%>"><%=pro%></option> 
                                <% } }catch(Exception e){}%>
                                </select><p class="validationmsg"></p></td>
                            </tr>
                        <tr>
                                <td width="126"><label>City</label><span class="mandatory" id="cityspanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="cityspan" >#</span></td>                        
                                <%/*try{
                                List objList = new ArrayList(); 
                                objList = wbo.getCities();
                                int listSize = objList.size();
                                
                                CityVO objCityVO = new CityVO();
                                */
                                %>
                                <td width="200">
                                    <select name="city" id="city" onchange="return (checkDuplicate() && fetchSubAddress(3,this.options[this.selectedIndex].value));">
                                        <option ></option>
                              <% /* for (int i=0;i<listSize;i++)                                   
                                {
                                objCityVO = (CityVO)objList.get(i);
                                String city = objCityVO.getAddr_city();                                
                                String pro = objCityVO.getAddr_province();
                                
                                if(prov==null){
                                    prov = pro;
                                }*/
                                %> 
                               <!-- <option province="<=pro%>" value="<=city%>"><=city%></option> -->
                                <%/* } }catch(Exception e){}*/%>
                        </select><p class="validationmsg"></p></td>
                        <td width="134"><label>Zip</label></td>
                        <td width="180"><input type="text" class=txtxx name="zip" value="" maxlength="10" /><p class="validationmsg"></p></td>
                            </tr>
                        <%--<td width="180"><select name="province">
                                <option value=""></option>
                                <%try{
                                List objList = new ArrayList();
                                objList = wbo.getProvinces();
                                int listSize = objList.size();
                                for (int i=0;i<listSize;i++)                                   
                                {
                                String province = (String)objList.get(i);
                                %> 
                                <option value="<%=province%>"><%=province%></option>
                                <% } }catch(Exception e){}%>

                        </select></td><p class="validationmsg"></p>--%>
                        <!--<td width="180"><input type="text" id="province" name="province" class=txtxx value="<=prov%>" maxlength = "30" readonly/><p class="validationmsg"></p></td>-->
                            <tr><td width="134"><label>Zone</label><span class="mandatory" id="zonespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;" id="zonespan" >#</span></td>
                                <td width="180">
                                    <select name="zone" id="zone" onchange="fetchSubAddress(4,this.options[this.selectedIndex].value);">
                                        <option ></option>
                            </select><p class="validationmsg"></p></td></tr>
                            <!--<input type="text"  name="zone" value="" class=txtxx maxlength="30"/><p class="validationmsg"></p></td>-->
                            <tr><td width="126"><label>SubZone</label><span class="mandatory"id="subzonespanstar" style="display:none">*</span><span class ="mandatory" style="font-size:12px;"  id="subzonespan" >#</span></td>
                            <td width="200" colspan="4"><!--<input type="text"  name="subzone" value="" class=txtxx maxlength="30"/><p class="validationmsg"></p></td>-->
                             <select name="subzone" id="subzone">
                                                <option ></option>
                                        </select><p class="validationmsg"></p></td>
                        </tr>
                       
                        <tr><td width="126"><label>UserId</label><span class="mandatory">*</span></td>
                            <td width="200"><input type="text"  class=txtxx name="userId" value="" maxlength="15"/><p class="validationmsg"></p></td>
                            <td width="134"><label>Password</label><span class="mandatory">*</span></td>
                        <td width="180"><input type="password"  class=txtxx name="password" value="" maxlength="30"/><p class="validationmsg"></p></td></tr>
                        <tr><td width="134"><label>Confirm&nbsp;Password</label><span class="mandatory">*</span></td>
                        <td width="180"><input type="password"  class=txtxx name="password1" value="" maxlength="30"/><p class="validationmsg"></p></td></tr>
                        <TR>
                            <TD>&nbsp;</TD>
                            <TD>&nbsp;</TD>
                            <TD align=left><INPUT class=submit-btn 
                                                      onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                      onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                      src="images/btn-submit-thickbox.gif" 
                                              name=input> </TD></TR>
                        
                        
                    </table>
</form></div></div></div> </body>
   
