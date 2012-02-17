<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%> 
<script language="javascript" type="text/javascript">
  function checkMailSending(radioObj){
  var len = radioObj.length;
  for(var i = 0; i < len; i++) {
		if(radioObj[i].checked) {
			if(radioObj[i].value == 1){
                        document.leadform.mailSending.value = "true";
                        } else if(radioObj[i].value == 2){
                        document.leadform.mailSending.value = "false";
                        }
                        
		}
	}

  }

    
    
</script>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   <div class="shadow-box">
   <div class="shadow-box-inner">
       <form  action="./MailSending.do" method="Post" name="leadform" onsubmit="checkMailSending((this.elements['radio1']));">
           <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
              
               <TR>
                   
               <TD><input type=radio name=radio1 id="yes" value = "1" />Enable Mail Sending</TD>
                           
               </TR>
               <tr><TD><input type=radio name=radio1 id="no" value = "2"/>Disable Mail Sending</TD>
               </tr>
               <input type="hidden" name="mailSending" id="mailSending"/>
              
               <tr><td>&nbsp;</td>
                           <TD align=center><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD></TR>
       </table></form>
   </div>
   </div>
        
   
   
