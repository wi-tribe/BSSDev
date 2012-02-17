<%@page contentType="text/html" import="com.witribe.constants.WitribeConstants,com.witribe.bo.WitribeBO,java.util.ArrayList,java.util.List, com.witribe.vo.CityVO, com.witribe.vo.ProspectVO, com.witribe.vo.LeadSourceVO, com.witribe.vo.AddressMappingVO, com.witribe.vo.ZoneVO, com.witribe.vo.SubzoneVO, com.witribe.vo.LeadISPVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
   <script language="javascript" type="text/javascript">
    function bodyload(){
            //document.getElementById('broadbandid').style.display = "none";
            document.getElementById('dialupid').style.display = "none";
            document.getElementById('CNIC').style.display = "none";
            document.getElementById('Passport').style.display = "none";
            document.getElementById('otherzone').style.display = "none";
            document.getElementById('othersubzone').style.display = "none";
            document.getElementById('otherzone1').style.display = "none";
            document.getElementById('othersubzone1').style.display = "none";
            var index = document.getElementById('reasonId').options[document.getElementById('reasonId').selectedIndex].value;
            callajaxReason(index);
            }
        
   function changeidentitylabel(sel) {
             if(sel.options[sel.selectedIndex].value == "1") {
                document.getElementById('CNIC').style.display = "";
                document.getElementById('Passport').style.display = "none";
             }
             else {
                 document.getElementById('CNIC').style.display = "none";
                document.getElementById('Passport').style.display = "";
             }
             if(sel.options[sel.selectedIndex].value == ""){
                 if(document.getElementById('CNIC').style.display == ""){
                    document.getElementById('CNIC').style.display = "none";
                 } else if(document.getElementById('Passport').style.display == ""){
                    document.getElementById('Passport').style.display = "none";
                 }             
             }
     }       
     function limit(form)
          {
             
             if (form.leadaddress.value.length > 200)
               {
                form.leadaddress.value=  form.leadaddress.value.substring(0, 200);
                $('p.validationmsg').hide();
                    showMessage(form.leadaddress, 'only 200 characters can be entered');
                      return false; 
                   }
          }        
    </script>
<body onload="javascript:bodyload();">
    <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
    <%--<P><font color="red"><b>${errorString}</b></font></P>--%>
    <% if(request.getAttribute(WitribeConstants.ERROR_STRING) != null) { %>
       <P><font color="red"><b><%=request.getAttribute(WitribeConstants.ERROR_STRING)%></b></font></P>
        <%}%>
    <div class="shadow-box">
    <div class="shadow-box-inner">
    <form  action="./OnlineReg.do?create=yes" method="Post" name="leadform" onsubmit="return ValidateInformationform(this);" >
    <%WitribeBO wbo = new WitribeBO();
    String prov = null;%>
    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
        <tr><td><label>Channel</label></td>
             <td><input type= text name=channeltype class="txtxx" value="Online Registration" readonly></td>
        <td><label>Salutation</label></td>
            <td><select name="salutation">
                    <option value=""></option>
                    <option value="Mr.">Mr.</option>
                    <option value="Ms.">Ms.</option>
                    <option value="Mrs.">Mrs.</option>
                    <option value="M/s.">M/s.</option>
        </select></td></tr>
        <tr><td width="126"><label>FirstName</label><span class="mandatory">*</span></td>
        <td width="200"><input type="text" class="txtxx" name="firstname" value="" maxlength="30" /><p class="validationmsg"></p></td>
        <td width="134"><label>LastName</label><span class="mandatory">*</span></td>
        <td width="180"><input type="text" class="txtxx" name="lastname" value="" maxlength="30"/><p class="validationmsg"></p></td></tr>
        <tr class='info-listing'><td  width="126"><label>Nationality</label></td>
        <td width="200"><select name=nationality onchange='changeidentitylabel(this)'>
            <option value=""></option>
            <option value=1>Pakistani</option>
            <option value=2>Foreigner</option>
        </select>
        </td></tr>
        <tr id="CNIC"><td width="134"><label>CNIC Id</label></td>
        <td width="180"><input type="text" name="CNICid" value="" class="txtxx" maxlength="20"/><p class="validationmsg"></p></td>
        <tr id="Passport"><td width="126"><label>Passport</label></td>
        <td width="200"><input type="text" name="Passport" value="" class="txtxx" maxlength="20"/><p class="validationmsg"></p></td></tr>
        <tr><td width="134"><label>Occupation/ Degree</label></td>
        <td width="180"><input type="text" name="jobtitle" value="" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td>
        <td width="126"><label>Organization</label></td>
        <td width="200"><input type="text" name="occupation" value="" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>
        <tr><td width="134"><label>Email</label><span class="mandatory">*</span></td>
        <td width="180"><input type="text" name="email" value="" class="txtxx" maxlength="60"/><p class="validationmsg"></p></td>
        <td width="126"><label>Contact</label><span class="mandatory">*</span></td>
        <td width="200"><input type="text" class="txtxx" name="contactnumber" value="" maxlength="16"/><p class="validationmsg"></p></td></tr>
        <!--<tr><td width="134"><label>House/Plot</label><span class="mandatory">*</span></td>
        <td width="180"><input type="text" class="txtxx" name="plot" value="" maxlength="30"/><p class="validationmsg"></p></td>
        <td width="126"><label>Street</label><span class="mandatory">*</span></td>
        <td width="200"><input type="text" name="street" value="" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>-->
        <tr><td width="140"><label>Lead Address</label><span class="mandatory">*</span></td>
        <td colspan="1"><textarea name="leadaddress" cols="27" rows="3" value="" class="txtxx" maxlength="200" onkeypress="javascript: return limit(this.form);"></textarea><p class="validationmsg"></p></td></tr>
        <tr><td></td></tr>
        <tr><td colspan="4" align="left"><b><h4><font color="#c60651" height=29px style="{normal 12px Verdana, Arial, Helvetica, sans-serif}">SALES AREA</h4></b></td></tr>
        <tr><td colspan="4"><table class="info-listing">
            <tr><td width="134"><label>Country</label><span class="mandatory">*</span></td>
                <td width="180"><input type="text" name="country" value="${WitribeConstants.COUNTRY}" class="txtxx" maxlength="30"  readonly/><p class="validationmsg"></p></td>
                <td width="126"><label>Province</label><span class="mandatory">*</span></td>
                <%--   <td width="200">
                <select name="province">
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
                        <%try{
                        List objList1 = new ArrayList(); 
                        objList1 = wbo.getSubAddress("1","Pakistan","1");
                        int listSize1 = objList1.size();                               
                        AddressMappingVO objAddressVO = new AddressMappingVO();
                        %>
                        <td width="200">
                            <select id="province" name="province" onchange="fetchSubAddressLead(2,this.options[this.selectedIndex].value);">
                                <option></option> 
                                <%  for (int i=0;i<listSize1;i++)                                   
                                {
                                objAddressVO = (AddressMappingVO)objList1.get(i);                                
                                String pro = objAddressVO.getSubAddress();                                
                                %> 
                                <option value="<%=pro%>"><%=pro%></option> 
                                <% } }catch(Exception e){}%>
                    </select><p class="validationmsg"></p></td></tr>
                    <!--<td width="200"><input type="text" name="province" value="<=prov%>" class="txtxx" maxlength="30" readonly/><p class="validationmsg"></p></td></tr>-->
                    <tr><td width="134"><label>City</label><span class="mandatory">*</span></td>
                        <td width="180"><!--<select name="city" onchange="document.leadform.province.value = this.options[this.selectedIndex].province;">-->
                            <%/*try{
                            List objList = new ArrayList(); 
                            objList = wbo.getCities();
                            int listSize = objList.size();
                            CityVO objCityVO = new CityVO();
                            for (int i=0;i<listSize;i++)                                   
                            {
                            objCityVO = (CityVO)objList.get(i);
                            String city = objCityVO.getAddr_city();
                            String pro = objCityVO.getAddr_province();                    
                            if(prov==null){
                            prov = pro;
                            }*/
                            %> 
                            <select name="city" id="city" onchange="fetchSubAddressLead(3,this.options[this.selectedIndex].value);">
                                <option ></option>
                                <!--<option province="<=pro%>" value="<=city%>"><=city%></option> -->
                                <%/* } }catch(Exception e){}*/%>
                                <!--  <td><select name="city">
                             <option value=""></option>
                             <option value="Hyderabad">Hyderabad</option>
                             <option value="Khammam">Khammam</option>
                             <option value="Warangal">Warangal</option>-->
                        </select><p class="validationmsg"></p></td>
                        <td width="126"><label>Zip</label></td>
                    <td width="200"><input type="text" name="zip" value="" class="txtxx" maxlength="10" /><p class="validationmsg"></p></td></tr>
                        <tr><td width="134"><label>Zone</label><span class="mandatory">*</span></td>
                            <td width="180"><select name="zone" id="zone" onchange="fetchSubAddressLead(4,this.options[this.selectedIndex].value);">
                                    <option ></option>
                        </select><p class="validationmsg"></p></td>
                        <td id="otherzone" width="126"><label>Other Zone</label><span class="mandatory">*</span></td>
                        <td id="otherzone1" width="200"><input type=text name="otherzone" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td>
                        </tr>
                        <tr><td width="134"><label>Subzone</label><span class="mandatory">*</span></td>
                            <td width="180" colspan="4"><select name="subzone" id="subzone" onchange="checkOtherSubZone(this.options[this.selectedIndex].value);">
                                    <option ></option>
                        </select><p class="validationmsg"></p></td></tr>
                    <!--<input type="text" name="zone" value="" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td>-->
                       <tr><td id="othersubzone" width="126"><label>Other SubZone</label><span class="mandatory">*</span></td>
                    <td id="othersubzone1" width="200"><input type=text name="othersubzone" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>
        </table></td></tr>
        <tr class=''>
        <td colspan="4" align=right width="65%"><div class=normalGrayFont align=center></div></td>
        </tr>
        <!--<input type="text" name="subzone" value="" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td>-->
        <tr><td width="134"><label>Lead source</label></td>
            <!--<td width="180"><select name="leadsource">
                    <option value=""></option>
                    <option value="TV">TV</option>
                    <option value="Radio">Radio</option>
                    <option value="News paper/Magazine">News paper/Magazine</option>
                    <option value="Billboard">Billboard</option>
                    <option value="Internet">Internet</option>
                    <option value="Road show">Road show</option>
                    <option value="Streamer">Streamer</option>
                    <option value="Event">Event</option>
                    <option value="Word of mouth">Word of mouth</option>
                    <option value="Email">Email</option>
                    <option value="Other">Other</option>
        </select><p class="validationmsg"></p></td>-->
    <logic:notEmpty name="LeadSourceList">
            <td width="180"><select name="leadsource">
                    <logic:iterate id="objleadsource" name="LeadSourceList" type="com.witribe.vo.LeadSourceVO" scope="request"> 
                        <option value="${objleadsource.leadSourceName}">${objleadsource.leadSourceName}</option>
                </logic:iterate></select>
        <p class="validationmsg"></p></td></logic:notEmpty>
    </tr>
        <tr><td width="126"><label>Package<br></label></td>
        <td width="200"><input type="text" name="packageinfo" value="" class="txtxx" maxlength="10" /><p class="validationmsg"></p></td></tr>
        <tr><td width="134"><label>Name of current ISP</label></td>
        <td width="180"><input type="text" name="nameofISP" value="" class="txtxx" maxlength="30"/><p class="validationmsg"></p></td></tr>
         <!--<tr><td width="134"><label>Required ISP</label></td></tr>-->
        <tr><td width="126"><label>Type of service</label></td>
                    <!--<option value=""></option>
                    <option value="Broadband">Broadband</option>
                    <option value="Dial-up">Dial-up</option>-->
      <logic:notEmpty name="LeadISPList">
            <td width="180" colspan="3"><select id="service" name="servicetype" onchange="javascript: getServicetype();">
             <logic:iterate id="objleadisp" name="LeadISPList" type="com.witribe.vo.LeadISPVO" scope="request"> 
                        <option value="${objleadisp.reqISP}">${objleadisp.reqISP}</option>
                </logic:iterate></select>
        <p class="validationmsg"></p></td></logic:notEmpty></tr>

        <tr id="dialupid"><td width="134"><label>Willing to pay</label></td>
        <td width="180"><input type="text" name="willingPay" value="" class="txtxx" maxlength="15" /><p class="validationmsg"></p></td></tr>
        <!--<tr id="broadbandid"><td width="126"><label>Broadband Type </label></td>
            <td width="200">     
                <select name="broadbandtype">
                    <option></option>
                    <option value="DSL">DSL</option>
                    <option value="Cable">Cable</option>
                    <option value="WiMax">WiMax</option>
                    <option value="EVDO">EVDO</option>
                    <option value="Others">Others</option>
        </select><p class="validationmsg"></p></td></tr>-->
        <tr><td width="134"><label>Current speed<br>(In Kbps)</label></td>
        <td width="180"><input type="text" name="speed" value="" class="txtxx" maxlength="15" /><p class="validationmsg"></p></td></tr>
        <tr><td ><label>Current volume limit(In Kbps)</label></td>
        <td><input type="text" name="volumelimit" value="" class="txtxx" maxlength="15" /><p class="validationmsg"></p></td></tr>
        <tr><td><label>Monthly spend on internet</label></td>
        <td><input type="text" name="monthlyspend" value="" class="txtxx" maxlength="15" /><p class="validationmsg"></p></td></tr>
         <tr><td><label>Job Status</label><span class="mandatory">*</span></td>
                 <logic:notEmpty name="objReasonList">
                        <td colspan="3"><select id="reasonId" name="reasonId" onchange="callajaxReason(this.options[this.selectedIndex].value);">
                                <logic:iterate id="objreason" name="objReasonList" type="com.witribe.vo.ProspectVO" scope="request"> 
                                    <option value="${objreason.reasonId}">${objreason.reasonCode}</option>
                            </logic:iterate></select>
                <p class="validationmsg"></p></td></logic:notEmpty></tr>
                <tr><td><label>Lead State</label></td>
                <td><input type="text" id= "transitionState" name="transitionState" class="txtxx" readonly/></td></tr>
                 <tr><td><label>Referred By</label></td>
        <td><input type="text" name="referredBy" value="" class="txtxx" maxlength="50" /><p class="validationmsg"></p></td></tr>
        <tr><td width="126"><label>Query</label></td>
        <td colspan="3"><textarea name="query" cols="80" rows="3"></textarea><p class="validationmsg"></p></td></tr>
       <tr><td width="134"><label>Priority</label></td>
        <td width="180"><input type="checkbox" name="priority" value="ON"/></td></tr>
        <TR>
                    <TD>&nbsp;</TD>
                    <TD>&nbsp;</TD>
                    <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD><td>  <a href="./Home.do"> <h5>Cancel</h5></a></td></TR>
       
    </table></div></form>
    </div>
    </div>
      </body>
    
