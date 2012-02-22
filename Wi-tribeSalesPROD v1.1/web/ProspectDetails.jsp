<%@page contentType="text/html" import="com.witribe.vo.ProspectVO"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
    <head>
        <script language="javascript">
            function bodyload(){
            //document.getElementById("textid").style.display="none";
            var index = document.getElementById('reasonId').options[document.getElementById('reasonId').selectedIndex].value;
            callajaxReason(index);
            }
           /* function getReason()
            {
            var Index = document.getElementById("reasonid").selectedIndex;
            var type=document.getElementById("reasonid").options[Index].text;
          <!-- document.getElementById("textid").style.display="none";-->
            if(type == "other"){
            document.getElementById("textid").style.display="";
            } else {
            document.getElementById("textid").style.display="none";
            }*/
            
          
           
        </script>
    </head>
     <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
    <body onload="javascript:bodyload();">
      <div id="inner-content">
       <div class="shadow-box">
            <div class="shadow-box-inner">
            <form  action="./RegisterProspect.do" method="Post" onsubmit="javascript: return checkReason(this);">
            <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                
                <tr><td><label>Reason</label><span class="mandatory">*</span></td>
                    <%--<option value="No Interest">No Interest</option>
                    <%if(request.getParameter("coveragestatus") != null && 
                            request.getParameter("coveragestatus").equalsIgnoreCase("Not Available")) {%>
                    <option value="No Coverage" selected>No Coverage</option>
                    <%} else {%>
                    <option value="No Coverage" >No Coverage</option>
                    <%}%>
                    <option value="other">other</option>--%>
                    
                    <logic:notEmpty name="objReasonList">
                        <td><select id="reasonId" name="reasonId" onchange="callajaxReason(this.options[this.selectedIndex].value);">
                                <logic:iterate id="objreason" name="objReasonList" type="com.witribe.vo.ProspectVO" scope="request"> 
                                    <option value="${objreason.reasonId}">${objreason.reasonCode}</option>
                            </logic:iterate></select>
                <p class="validationmsg"></p></td></logic:notEmpty></tr>
                <tr><td><label>Transition State</label></td>
                <td><input type="text" id= "transitionState" name="transitionState" readonly/></td></tr>
               
                <tr id="textid"><td><label>Comment</label><span class="mandatory">*</span></td>
                    <td>
                        <textarea name="reasonComment"  maxlength="20" class="txtxx"></textarea><p class="validationmsg"></p>
                  </td></tr>
                <input type="hidden" name="leadId" value="<%=request.getParameter("leadId")%>">
                <tr> <TD>&nbsp;</TD>
                      <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD></TR>
                </tr>
        </table></form>
      </div>
      </div>
      </div>
    </body>
