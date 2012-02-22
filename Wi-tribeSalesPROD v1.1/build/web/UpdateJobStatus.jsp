<%@page pageEncoding="UTF-8" import="com.witribe.vo.ProspectVO,com.witribe.vo.LeadVO"%>
<%@page contentType="text/html" import="com.witribe.constants.WitribeConstants"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
    function Validateform(formObj){
            if(formObj.amount) {
               $('p.validationmsg').hide();
		if(formObj.amount.value == '') {
			showMessage(formObj.amount, 'Please Enter Amount');
			return(false);
		}
                if(!IsNumber(formObj.amount)){
                        showMessage(formObj.amount, 'Please Enter Numbers Only');
                        return(false);
                }
            }
            
            document.getElementById('date1').value = document.getElementById('date12').value+" "+document.getElementById('hours').options[document.getElementById('hours').selectedIndex].value+":"+document.getElementById('mm').options[document.getElementById('mm').selectedIndex].value;
            return true;
          }
            function bodyload(){
            /*document.getElementById("textid").style.display="none";
            var index = document.getElementById('reasonId').options[document.getElementById('reasonId').selectedIndex].value;
            callajaxReason(index);*/
            datepick();
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   <body>
       <c:set var="status" value="<%=request.getParameter(WitribeConstants.LEAD_STATUS)%>" />
       <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
       <div id="inner-content">
           <div class="shadow-box">
               <div class="shadow-box-inner">
                   <form action="./UpdateStatus.do" method="Post" onsubmit="return (Validateform(this) && checkReason(this));">
                      
                       <input type="hidden" name="leadStatushidden" value="${status}" class="txtxx" />
                       <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                           <c:choose>
                               <c:when test="${status == WitribeConstants.CUSTOMER_INITIATED}">
                                   <tr><td><label>Lead Id</label></td>
                                   <td><%=request.getParameter(WitribeConstants.LEAD_ID)%> </td></tr>
                                   <tr><td><label>Current Status</label></td>
                                   <td>${status}</td></tr>
                                   <c:if test="${status == WitribeConstants.CUSTOMER_INITIATED}">
                                       <tr><td><label>Job Id</label></td>
                                       <td><%=request.getParameter("orderId")%> </td></tr>
                                       
                                       <tr><td><label>Account Number</label></td>
                                       <td><%=request.getParameter("accountNo")%></td></tr>
                                       
                                       <tr><td><label>Amount Collected</label><span class="mandatory">*</span></td>
                                       <td><input type="text" name="amount" value="" class="txtxx" maxlength = "10" /><p class="validationmsg"></p></td></tr>
                                   </c:if>
                                   <tr><td><label>Job Status</label><span class="mandatory">*</span></td>
                                       <td><select name="leadStatus" >
                                               <c:choose>
                                                   <c:when test="${status != WitribeConstants.CUSTOMER_INITIATED}">
                                                       <option value="${WitribeConstants.SCHEDULED}" selected>${WitribeConstants.SCHEDULED}</option>
                                                       <option value="${WitribeConstants.ON_HOLD}" >${WitribeConstants.ON_HOLD}</option>    
                                                       <option value="${WitribeConstants.WIP}" >${WitribeConstants.WIP}</option> 
                                                   </c:when>
                                                   <c:otherwise>
                                                       <option value="${WitribeConstants.ACCOUNT_CREATED}">${WitribeConstants.ACCOUNT_CREATED}</option>
                                                   </c:otherwise>
                                               </c:choose>
                                               
                                               
                                   </select></td></tr>
                                   
                               </c:when>
                               <c:otherwise>
                                   
                                   <tr><td><label>Lead Id</label></td>
                                   <td><%=request.getParameter(WitribeConstants.LEAD_ID)%> </td></tr>
                                   <%--<c:if test="${status == WitribeConstants.CUSTOMER_INITIATED}">
                                <tr><td><label>Job Id</label></td>
                                <td><%=request.getParameter("orderId")%> </td></tr>
                                
                                <tr><td><label>Account Number</label></td>
                                <td><%=request.getParameter("accountNo")%></td></tr>
                                
                                <tr><td><label>Amount Collected</label><span class="mandatory">*</span></td>
                                <td><input type="text" name="amount" value="" class="txtxx" maxlength = "10" /><p class="validationmsg"></p></td></tr>
                            </c:if>--%>
                           <c:set var="newjobstatus" value='<%=request.getParameter("reasonCode")%>'/>
                               <tr><td><label>Job Status</label><span class="mandatory">*</span></td>
                                       <logic:notEmpty name="objReasonList">
                                           <td><select id="reasonId" name="reasonId" onchange="callajaxReason(this.options[this.selectedIndex].value);">
                                                   <logic:iterate id="objreason" name="objReasonList" type="com.witribe.vo.ProspectVO" scope="request"> 
                                                       <c:choose>
                                                           <c:when test="${newjobstatus == objreason.reasonCode}">
                                                               <option value="${objreason.reasonId}" selected>${objreason.reasonCode}</option>
                                                           </c:when>
                                                           <c:otherwise>
                                                               <option value="${objreason.reasonId}">${objreason.reasonCode}</option>
                                                           </c:otherwise>
                                                       </c:choose>
                                               </logic:iterate></select>
                                   <p class="validationmsg"></p></td></logic:notEmpty></tr>
                                   
                                   <tr><td><label>Lead State</label></td>
                                   <% if(request.getAttribute("transState").equals("")) { %>
                                   <td><input type="text" name= "transitionState" id="transitionState"  value='Qualified Lead' readonly/></td></tr>
                                    <% } else { %>
                                    <td><input type="text" name= "transitionState" id="transitionState"  value='<%=request.getAttribute("transState")%>' readonly/></td></tr>
                                    <% } %>
                                   <!--bhawna 15th sep, 2009-->
                                   <tr>
                                    <td><label for="date1">Follow Up date</label></td>
                                    <td coslpan=2><table border=0 cellpadding=0 cellspacing=0><tr><td><input type="text" class="txtxx date-pick dp-applied" name="followUpDate1" id="date12"  readonly/>&nbsp;&nbsp;<p class="validationmsg"></p></td>
                                    <td><select name="hours" id="hours">
                                           <option  value="00" selected>HH</option>
                                                <% for(int i = 0;i<24;i++) {%>
                                                <option value="<%=i%>"><%=i%></option>
                                                <%}%>
                                    </select></td><td><select name="mm" id="mm">
                                        <option  value="00" selected>Min</option>
                                                <% for(int j = 0;j<60;j++) {%>
                                                <option value="<%=j%>"><%=j%></option>
                                                <%}%>
                                    </select></td></tr></table></td>
                                     
                                  </tr> 
                                   
                                   <!--bhawna 15th sep, 2009--> 
                                   <tr id="textid"><td><label>Comment</label><span class="mandatory">*</span></td>
                                       <td>
                                           <textarea name="reasonComment"  maxlength="20" class="txtxx"></textarea><p class="validationmsg"></p>
                                   </td></tr>
                               </c:otherwise>
                           </c:choose> 
                           
                           <input type="hidden" name="leadId" value="<%=request.getParameter(WitribeConstants.LEAD_ID)%>" /> 
                           <input type="hidden" name="followUpDate" id="date1" readonly/>&nbsp;&nbsp;<p class="validationmsg"></p>
                           <TR>
                               <td>&nbsp;</td>
                               <TD align=right><INPUT class=submit-btn 
                                                          onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                          onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                          src="images/btn-submit-thickbox.gif" 
                                                  name=input> </TD></TR>
                           
                           
                   </table></form>
               </div>
           </div>
   </div></body>
   
