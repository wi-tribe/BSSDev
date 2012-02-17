<%@page contentType="text/html" import="com.witribe.constants.WitribeConstants"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
var http;
    function getHTTPObject()
{
    var xmlHttp;
    if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
       } else if (window.ActiveXObject) {
        isIE = true;
       // alert("ie");
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    return xmlHttp;
 }   
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function ltrim(stringToTrim) {
	return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
	return stringToTrim.replace(/\s+$/,"");
}

function checkCoverage(url) {
    document.getElementById('coverage').style.display="none";
    document.getElementById('loading').style.display = "";
    document.getElementById('checkcoverage').style.display = "";
    http= getHTTPObject();    
    http.onreadystatechange = checkCoverageResponse;
    http.open("POST", url , true);       
    http.send(null);
}
function checkCoverageResponse() {
if(http.readyState == 4 && http.status == 200) {
        var status = http.responseText;
        status = trim(status);        
        document.getElementById('loading').style.display = "none";
        document.getElementById('checkcoverage').style.display = "none";
        document.getElementById('coverage').style.display="";
        //alert(status);
        if(rtrim(ltrim(status)) == "No Coverage" || status == "null,0.0,0.0"){
        document.getElementById('coverage').innerHTML = "No Coverage";
        document.getElementById('sCoverage').value = "Not Covered";
        document.getElementById('cCheck').innerHTML = "Not Covered";
        document.getElementById('cCheckFlag').value = "Not Covered";
        document.getElementById('ConvertToAccount').style.display = "none";
        }
        else{
        //document.getElementById('coverage').innerHTML = status;
       /* var coveragestat = status.split('=');        
        var cover1 = coveragestat[1].split('%');
        var cover2 = coveragestat[2].split('%');
        var cover3 = coveragestat[3].split('%');*/
        var coveragestat = status.split(',');
        var statusdisplay = "<table><tr><td><B>Plot Coverage</B></td></tr><tr><td>USB Coverage ="+coveragestat[1]+"%</td></tr><tr><td>Indoor Coverage ="+coveragestat[2]+"%</td></tr><tr><td>Outdoor Coverage ="+coveragestat[3]+"%</td></tr><tr><td>Latitude ="+coveragestat[4]+"</td></tr><tr><td>Longitude ="+coveragestat[5]+"</td></tr></table>";
        document.getElementById('coverage').innerHTML = statusdisplay;
       // if(cover1[0] > 25 || cover2[0] > 25 || cover3[0] >25){
       if(coveragestat[1] > 25 || coveragestat[2] > 25 || coveragestat[3] >25){
         document.getElementById('sCoverage').value = "Covered";
         document.getElementById('cCheck').innerHTML = "Covered";
         document.getElementById('cCheckFlag').value = "Covered";
         document.getElementById('sLatitude').value = coveragestat[4];
         document.getElementById('sLongitude').value =coveragestat[5] ;
        }
        else{     
            document.getElementById('sCoverage').value = "Not Covered";
            document.getElementById('cCheck').innerHTML = "Not Covered";
            document.getElementById('cCheckFlag').value = "Not Covered";
            document.getElementById('ConvertToAccount').style.display = "none";         
        }
     
      }   
   }
}

</script>

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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

 <div id="inner-content">
          <form  name="leadform" action="./GetReason.do?leadId=<%=request.getParameter("leadId")%>" method="Post">
                    <!--<h3 class="heading"><font color="red">Lead Details</font></h3>-->
                    <table width="520" border="0" cellspacing="0" cellpadding="0" class="profile-listing">
                        <bean:define id="objLeads"  name="objLead" type="com.witribe.vo.LeadVO" />
                            <tr><td valign="top" width="235" class="label">Full Name</td>
                            <td valign="top"><bean:write name="objLeads" property="firstname"/> <bean:write name="objLeads" property="lastname"/> </td></tr>
                            <tr><td valign="top" class="label">Email</label></td>
                            <td valign="top"><bean:write name="objLeads" property="email"/></td></tr>
                            <tr><td valign="top" class="label">Contact</label></td>
                            <td valign="top"><bean:write name="objLeads" property="contactnumber"/></td></tr>
                            <%--<tr><td valign="top" class="label">House/Plot</label></td>
                            <td valign="top"><bean:write name="objLeads" property="plot"/></tr>
                            <tr><td valign="top" class="label">Street</label></td>
                            <td valign="top"><bean:write name="objLeads" property="street"/></tr>--%>
                            <tr><td valign="top" class="label">Subzone</label></td>
                            <td valign="top"><bean:write name="objLeads" property="subzone"/></tr>
                            <tr><td valign="top" class="label">Zone</label></td>
                            <td valign="top"><bean:write name="objLeads" property="zone"/></tr>
                            <tr><td valign="top" class="label">City</label></td>
                            <td valign="top"><bean:write name="objLeads" property="city"/></td></tr>
                            <tr><td valign="top" class="label">Province</label></td>
                            <td valign="top"><bean:write name="objLeads" property="province"/></tr>
                            <tr><td valign="top" class="label">Country</label></td>
                            <td valign="top"><bean:write name="objLeads" property="country"/></tr>
                            <tr><td valign="top" class="label">Lead source</label></td>
                                <td valign="top">
                                    <c:choose><c:when test="${objLeads.leadsource != null}">
                                                     <bean:write name="objLeads" property="leadsource"/>
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                                    
                                </td></tr>
                            <tr><td valign="top" class="label">Job Status</label></td>
                                <td valign="top"><bean:write name="objLeads" property="reasonCode"/></td></tr>
                            <tr><td valign="top" class="label">FollowUp Date</label></td>
                                <td valign="top">                                    
                                    <c:choose><c:when test="${objLeads.followUpDate != null}">
                                                     <bean:write name="objLeads" property="followUpDate"/>
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                                </td></tr>
                            <tr><td valign="top" class="label">Package Info</label></td>
                                <td valign="top">
                                    <c:choose><c:when test="${objLeads.packageinfo != null}">
                                                     <bean:write name="objLeads" property="packageinfo"/>
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                                    
                                </td></tr>
                            <tr><td valign="top" class="label">Type of service</label></td>
                            <td valign="top">
                                <c:choose><c:when test="${objLeads.servicetype != null}">
                                                     <bean:write name="objLeads" property="servicetype"/>
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                                
                            </td></tr>
                            <c:if test="${ objLead.servicetype != 'Dial-up'}">
                            <tr><td valign="top" class="label">Broadband Type </label></td>
                            <td valign="top">
                                <c:choose><c:when test="${objLeads.broadbandtype != null}">
                                                      <bean:write name="objLeads" property="broadbandtype"/>
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                               
                            </td></tr>
                            </c:if>
                            <input type="hidden" name="sCoverage" value="" class="txtxx" maxlength="15" />  
                             <tr><td valign="top" class="label">Coverage Status</label></td>
                             <!--checkCoverage('coveragecheck.jsp?plot={objLeads.plot}&street={objLeads.street}&zone={objLeads.zone}&city={objLeads.city}');-->
                             <td valign="top"><span id="cCheck" >Not Checked</span></td></tr>
                        <input type="hidden" name="cCheckFlag" value="Not Checked" class="txtxx" />
                        <input type="hidden" name="leadStatus" value="${objLead.leadStatus}" class="txtxx" />
                        <input type="hidden" name="reasonCode" value="${objLead.reasonCode}" class="txtxx" />
                        <input type="hidden" name="transitionState" value="${objLead.transitionState}" class="txtxx" />
                        <input type="hidden" name="orderId" value="${objLead.orderId}" class="txtxx" />
                        <input type="hidden" name="accountNo" value="${objLead.accountNo}" class="txtxx" />
                        <input type="hidden" name="referredBy" value="${objLead.referredBy}" class="txtxx" />
                        <input type="hidden" name="sla" value="" class="txtxx" maxlength="15" />
                        <input type="hidden" name="sLongitude" value="" class="txtxx" maxlength="15" />
                        <input type="hidden" name="sLatitude" value="" class="txtxx" maxlength="15" />
                        </table>                        
                        
                        <table width="520" border="0" cellspacing="0" cellpadding="0" class="profile-listing">
                                <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td id="checkcoverage" style="display:none"><B>Checking Coverage</B></span><br></td>
                                <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td><span id=loading colspan="2" style="display:none" ><img  src="images/loadingAnimation.gif" ></span><br></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                <!--<td></td>--> 
                                <td id=coverage ></td></tr>
                            <br></table>
                            <table width="520" border="0" cellspacing="0" cellpadding="0" class="profile-listing">
                        <tr>
                            
                                <td align="right"><input type="button" value="Show History" name="Show History" onclick="javascript:formSubmit('./LeadHistory.do')" /></td>
                         
                            <c:if test="${objLead.leadStatus != WitribeConstants.ACCOUNT_CREATED}">
                                <td align="right"><input type="button" value="Update Status" name="Update Status" onclick="javascript:formSubmit('./GetJobStatus.do')" /></td>
                                <!--<td><input type="button" value="Update Reason" name="Update Reason" onclick="javascript:formSubmit('./UpdateReason.do')"/></td> -->   
                           </c:if>               
                            <!--<:if test="{objLead.leadStatus != WitribeConstants.CUSTOMER_INITIATED}"  >
                                   <:if test="{objLead.leadStatus != WitribeConstants.PROSPECT_CREATED}">
                                   <td align="right"> <input type="button" value="ConvertToProspect" name="ConvertToProspect" onclick="javascript:formSubmit('./GetReason.do?leadId=<%=request.getParameter("leadId")%>')"/></td>                                    
                                   </:if>
                             </:if>-->
                             <c:if test="${objLead.leadStatus != WitribeConstants.CUSTOMER_INITIATED && objLead.leadStatus != WitribeConstants.ACCOUNT_CREATED}" >
                             <td><input type="button" value="Check Coverage" name="Check Coverage" onclick="javascript:formSubmit('./CoverageCheck.do')"/></td>    
                             <!--<td align="right"> <input type="button" value="ConvertToAccount" name="ConvertToAccount" onclick="javascript:formSubmitAccount('./GetMore.do')" /></td>-->
                             </c:if>

                             
                        </tr>
                        <input type="hidden" name="leadId" value="<%=request.getParameter("leadId")%>" />
                      
                                              
        </table></form></div>
        
