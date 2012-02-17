<SCRIPT language=javascript src="js/validations.js" type=text/javascript></SCRIPT>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<SCRIPT LANGUAGE=javascript type="text/javascript">
function initcursor()
 	{
		document.cu_login.username.focus();
	} 
</SCRIPT>
<div id="inner-content">
<!-- Inner content starts here -->
<P>Fields marked with an asterisk (<SPAN class=mandatory>*</SPAN>) are 
Mandatory.</P>
<div class="shadow-box">
    <div class="shadow-box-inner">
        <BODY onLoad="initcursor()">
        <FORM id=cu_login name=cu_login 
              action="./SubmitForgotPwd.do" method="post" onsubmit="javascript: return validateLoginForm(this)">
            <table width="520" border="0" cellspacing="0" cellpadding="0" class="profile-listing">
                <TR>
                    <TD width="31%">&nbsp;</TD>
                    <TD class=error colspan=2>
                        <%--  <logic:notEmpty name="errorList">
                                      <ul>
                                  <logic:iterate id="error" name="errorList">
                                      <b> ${error}</b><br>
                                  </logic:iterate> 
                                  </ul>
                                  </logic:notEmpty> --%>
                                  <font color="red">${errorString}</font>${infoString}
                </TD></TR>
                <TR>
                    <TD align=right>Enter Username<SPAN class=mandatory>*</SPAN></TD>
                    <TD align=middle>&nbsp;</TD>
                    <TD align=left><INPUT class=txtxx id=username name=username> 
                <P class=validationmsg></P></TD></TR>
                 <!-- hidden -->
                 
                <input type=hidden name="service" value="ip">
                <INPUT Type="hidden" NAME="page" VALUE="login_verify">
                <INPUT Type="hidden" NAME="sessionState" VALUE="start">
                <INPUT Type="hidden" NAME="Component" VALUE="com.portal.web.comp.PLoginBeanImpl">
                <!-- hidden -->
                <TR>
                    <TD>&nbsp;</TD>
                    <TD>&nbsp;</TD>
                    <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD>
                                  
                                <td>  <a href="./Home.do"> <h5>Back to Main Page</h5></a></td></TR>
                <tr><td colspan=2><b>You'll get Password to Your Email-Id</b></td></tr>
                <TR>
                    <TD>
                        <P>&nbsp;</P>
                        <P>&nbsp;</P>
                        <P>&nbsp;</P>
                <P>&nbsp;</P></TD></TR>
                
                <!-- Form end -->

               
        </table></form></BODY>
    </div>
</div>
</div>