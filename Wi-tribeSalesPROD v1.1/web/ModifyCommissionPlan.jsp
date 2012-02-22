<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List,com.witribe.commission.vo.CommissionPlanVO,com.witribe.sales.vo.ShopsVO,com.witribe.constants.WitribeConstants"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
    <script language="javascript" type="text/javascript">
    function bodyload(){
            /*document.getElementById('typerouter').style.display = "none";
            document.getElementById('mrouter').style.display = "none";*/
            document.getElementById('cityid2').style.display = "none";
            document.getElementById('zoneid2').style.display = "none";
            var salestype = document.getElementById('salesType').options[document.getElementById('salesType').selectedIndex].value;
            if(salestype == "1"){
            document.getElementById('channelid').style.display = "";
            }
            var city = document.getElementById('city').options[document.getElementById('city').selectedIndex].value;
            callAJAXZoneList(city);            
            callAJAXProductList(city);
            }
    function changeChannel(val){
       if(val != "1"){
           document.getElementById('channelid').style.display = "none";
           } else {
           document.getElementById('channelid').style.display = "";
           }
           }
    function getValues(city)
            {
            callAJAXZoneList(city);
            callAJAXProductList(city);
            //document.frmCreatePlan.getElementByName
            }
    function checkRuleType(){
            var ruletype = document.getElementById('comType').options[document.getElementById('comType').selectedIndex].value;
            if(ruletype == 1){
            document.getElementById('commissionType').value = "Basic";
            } else if(ruletype == 2){
            document.getElementById('commissionType').value = "Promotional";
            } else if(ruletype == 3) {
            document.getElementById('commissionType').value = "Retention";
            } else if(ruletype == 4){
            document.getElementById('commissionType').value = "Deduction";
            }
            if(document.getElementById('check').checked){
                document.getElementById('planStatus').value=1;
                } else {
                document.getElementById('planStatus').value=2;
                }
            }
        function checkZoneVal(val){
        if(val == "All"){
        document.getElementById('productdispid').style.display = "none";
        } else {
        document.getElementById('productdispid').style.display = "";
        }
        }
        function checkCommissionMode(val){
        if(val == 1){
        document.getElementById('cityid1').style.display = "";
        document.getElementById('cityid2').style.display = "none";
        document.getElementById('zoneid1').style.display = "";
        document.getElementById('zoneid2').style.display = "none";
        } else if(val == 2) {
        document.getElementById('cityid1').style.display = "none";
        document.getElementById('cityid2').style.display = "";
        document.getElementById('zoneid1').style.display = "none";
        document.getElementById('zoneid2').style.display = "";
        } else {
        document.getElementById('cityid1').style.display = "";
        document.getElementById('cityid2').style.display = "none";
        document.getElementById('zoneid1').style.display = "none";
        document.getElementById('zoneid2').style.display = "";
        }
        }
        function planLimit(form){
        if (form.planDesc.value.length > 90)
               {
                form.planDesc.value=  form.planDesc.value.substring(0, 90);
                $('p.validationmsg').hide();
                    showMessage(form.planDesc, 'only 90 characters can be entered');
                      return false; 
                   }
        }
    </script>
<script type="text/javascript" charset="utf-8">
			$(function()
				{
                                        Date.format = 'dd/mm/yyyy';
                                        var D=new Date(),Y=D.getFullYear(),M=D.getMonth()+1;
                                        var dt=D.getDate();
                                        var curDate=dt+"/"+M+"/"+Y;
					$('.date-pick').datePicker({startDate:curDate});
			});
		</script>
<body onload="javascript:bodyload();">
     <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
        <div id="inner-content">
            <div class="shadow-box">
                <div class="shadow-box-inner">
                    <form name=frmCreatePlan action="./ModifyPayment.do" method="Post" onsubmit="checkRuleType();">
                         <% if(request.getAttribute(WitribeConstants.ERROR_STRING) != null) { %>
                        <P><font color="red"><b><%=request.getAttribute(WitribeConstants.ERROR_STRING)%></b></font></P>
                        <%}%>
                        <table width="442" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                             <bean:define id="objCom"  name="objCommission" type="com.witribe.commission.vo.CommissionPlanVO" scope="request"/>
                            <tr><td><label>Sales Type</label><span class="mandatory">*</span></td>
                            <c:set var="salesType" value="${objCom.salesType}"/>
                                <td><select name="salesType" id ="salesType" onchange="changeChannel(this.options[this.selectedIndex].value);">
                                  <c:choose>
                                             <c:when test="${salesType == 9}">
                                                    <option value="9" selected>SDr</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="9">SD</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${salesType == 8}">
                                                    <option value="8" selected>RSM</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="8">RSM</option>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${salesType == 7}">
                                                    <option value="7" selected>TL</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="7">TL</option>
                                                </c:otherwise>
                                            </c:choose>
                                           <c:choose>
                                                <c:when test="${salesType == 1}">
                                                    <option value="1" selected>CSE</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="1">CSE</option>
                                                </c:otherwise>
                                            </c:choose>
                                       <!-- <option value="1">CSE</option>
                                        <option value="7">TL</option>
                                        <option value="8">RSM</option>
                                        <option value="9">SD</option>-->
                            </select></td></tr>
                            <tr><td><label>Commission Mode</label><span class="mandatory">*</span></td>
                            <c:set var="commissionMode" value="${objCom.commissionMode}"/>
                                <td><select name="commissionMode" id ="commissionMode" onchange="checkCommissionMode(this.options[this.selectedIndex].value)">
                                        <c:choose>
                                                <c:when test="${commissionMode == 1}">
                                                    <option value="1" selected>Market</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="1">Market</option>
                                                </c:otherwise>
                                            </c:choose>  
                                            <c:choose>
                                                <c:when test="${commissionMode == 2}">
                                                    <option value="2" selected>Product</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="2">Product</option>
                                                </c:otherwise>
                                            </c:choose>  
                                            <c:choose>
                                                <c:when test="${commissionMode == 1}">
                                                    <option value="3" selected>MarketProduct</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="3">MarketProduct</option>
                                                </c:otherwise>
                                            </c:choose>  
                                    <!--<option value="1">Market</option>
                                        <option value="2">Product</option>
                                        <option value="3">MarketProduct</option>-->
                            </select></td></tr>
                            <tr><td><label>Plan Name</label><span class="mandatory">*</span></td>
                                <td>
                                    <textarea name="planDesc" class="txtxx" maxlength="90" value="${objCom.planDesc}"></textarea>
                                </td>
                            </tr>
                            <tr id="channelid"><td><label>Channel Type</label><span class="mandatory">*</span></td>
                            <c:set var="channelType" value="${objCom.channelType}"/>
                                <logic:notEmpty name="ChannelList">
                                <td><select name="channelId"  id="ctype" onchange="ChangeType()">
                                        <logic:iterate name="ChannelList" id="ctype" type="com.witribe.commission.vo.CommissionPlanVO" scope="request">
                                                    <option value="${ctype.channelId}">${ctype.channelType}</option>
                                        </logic:iterate>
                            </select></td>
                                </logic:notEmpty></tr>
                                <logic:empty name = "ChannelList">
                                    <td>There are no channel Available to View</td>
                                </logic:empty>
                                <tr><td><label>City</label><span class="mandatory">*</span></td>
                                    <logic:notEmpty name="CitylList">
                                        <td id="cityid1"><select name="city" id="city" onchange= "getValues(this.options[this.selectedIndex].value);">
                                                <logic:iterate name="CitylList" id="cityid" type="com.witribe.commission.vo.CommissionPlanVO" scope="request">
                                                    <option value="${cityid.city}">${cityid.city}</option>
                                                    
                                                </logic:iterate>   
                                        </select></td>
                                    </logic:notEmpty>
                                    <td id="cityid2"><select name="city" id="city" onchange= "getValues(this.options[this.selectedIndex].value);">
                                                    <option value="All">All</option>
                                        </select></td>
                              </tr>
                              <tr><td><label>Zone</label><span class="mandatory">*</span></td>
                                  <td id="zoneid1"><select name = "zone" id="zoneid">
                                          
                                      </select>
                                     
                                      
                              </td>
                          <td id="zoneid2"><select name = "zone" id="zoneidall">
                                          <option value="All">All</option>  
                                      </select>
                                     
                                      
                              </td></tr>
                           <tr id="productdispid"><td><label>Product</label><span class="mandatory">*</span></td>
                                  <td width="50"><select name ="productId" id="productid" class="txtxx">
                                          <option value="-2">All</option>    
                                      </select>
                              </td></tr> 
                               <tr>
                                    <td><label for="date1">Start Date</label><span class="mandatory">*</span></td>
                                    <td><input type="text" class="txtxx date-pick dp-applied" name="startDate" id="startDate"  readonly/>&nbsp;&nbsp;<p class="validationmsg"></p></td>
                                    <td>&nbsp;</td>
                                  </tr>
                               <tr>
                                    <td><label for="date1">End Date</label><span class="mandatory">*</span></td>
                                    <td><input type="text" class="txtxx date-pick dp-applied" name="endDate" id="endDate"  readonly/>&nbsp;&nbsp;<p class="validationmsg"></p></td>
                                    <td>&nbsp;</td>
                                  </tr>
                                  <tr>
                                  <td><label>Status</label></td>
                                  <c:set var="planstatus" value="${objCom.planStatus}"/>
                                  <td>
                                  <c:choose>
                                                <c:when test="${planstatus == 1}">
                                                   <input id="check" type=checkbox name="check" selected/><lable>Active</lable></td></tr>  
                                                </c:when>
                                                <c:otherwise>
                                                     <input id="check" type=checkbox name="check" selected/><lable>Active</lable></td></tr>
                                                </c:otherwise>
                                            </c:choose>
                                    
                                  
                                  <input type="hidden" name = "planStatus" id="planStatus"/>
                              <tr><td><label>CommissionRule Type</label><span class="mandatory">*</span></td>
                              <c:set var = "CommType" value="${objCom.commType}"/>
                                <td><select name="commType"  id="comType">
                                    
                                        <c:choose>
                                                <c:when test="${CommType == 1}">
                                                    <option value="1" selected>Basic</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="1">Basic</option>
                                                </c:otherwise>
                                            </c:choose> 
                                            <c:choose>
                                                <c:when test="${CommType == 2}">
                                                    <option value="2" selected>Promotional</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="2">Promotional</option>
                                                </c:otherwise>
                                            </c:choose> 
                                            <c:choose>
                                                <c:when test="${CommType == 3}">
                                                    <option value="3" selected>Retention</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="3">Retention</option>
                                                </c:otherwise>
                                            </c:choose> 
                                            <c:choose>
                                                <c:when test="${CommType == 4}">
                                                    <option value="4" selected>Deduction</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="4">Deduction</option>
                                                </c:otherwise>
                                            </c:choose> 
                                        <!--<option value="1">Basic</option>
                                        <option value="2">Promotional</option>
                                        <option value="3">Retention</option>
                                         <option value="4">Deduction</option>-->
                            </select></td></tr>
                            
                        <input type=hidden name="commissionType" id = "commissionType"/>
                        <TR>
                            <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD></TR>
                                      
                   </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
 <!--callAJAXZoneList(this.options[this.selectedIndex].value) && -->

