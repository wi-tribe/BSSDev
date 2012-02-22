<%@page contentType="text/html" import="com.witribe.bo.WitribeBO"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <style type="text/css">@import "css/style.css";</style>
        <script language="JavaScript" >-->

/*function setMaster() {
     var mainForm = opener.document.leadform;
     var popForm = document.coverageform;
     var status = popForm.coverage.value;
     if(status == "null"){
        opener.document.getElementById('coverage').innerHTML = "No Coverage";
     }
     else{
        opener.document.getElementById('coverage').innerHTML = status;
     }       
     if(status == "null")
     {     
        mainForm.sCoverage.value = "Not Covered";
        opener.document.getElementById('cCheck').innerHTML = "Not Covered";
        opener.document.getElementById('cCheckFlag').value = "Not Covered";
        opener.document.getElementById('ConvertToAccount').style.display = "none";
        if (opener && !opener.closed) opener.focus();
        self.close();
        return false();
     }
     
     //alert(status);
     var coveragestat = status.split('=');
     //alert(coveragestat[0]+"cov1");
     //alert(coveragestat[1]+"cov2");
     //alert(coveragestat[2]+"cov3");
     //alert(coveragestat[3]+"cov4");
     var cover1 = coveragestat[1].split('%');
     var cover2 = coveragestat[2].split('%');
     var cover3 = coveragestat[3].split('%');
     //alert(cover1[0]+"   a"+cover2[0]+" b"+cover3[0]);
     if(cover1[0] > 25 || cover2[0] > 25 || cover3[0] >25)
     {
     mainForm.sCoverage.value = "Covered";
     opener.document.getElementById('cCheck').innerHTML = "Covered";
     opener.document.getElementById('cCheckFlag').value = "Covered";
     if (opener && !opener.closed) opener.focus();
     
     }
     else{     
     mainForm.sCoverage.value = "Not Covered";
     opener.document.getElementById('cCheck').innerHTML = "Not Covered";
     opener.document.getElementById('cCheckFlag').value = "Not Covered";
     opener.document.getElementById('ConvertToAccount').style.display = "none";
     if (opener && !opener.closed) opener.focus();
      
      }
     self.close();
   /* var mainForm = opener.document.leadform;
    var popForm = document.coverageform;
    var status = popForm.sCoverage.value;
    mainForm.sCoverage.value = status;
    opener.document.getElementById('cCheck').innerHTML = status;
    opener.document.getElementById('cCheckFlag').value = status;
    if(status == 'Not Available') {
        if (opener && !opener.closed) opener.focus();
        self.close();
    } else {
        var sla = popForm.sla.value;
        var sLongitude = popForm.sLongitude.value;
        var sLatitude = popForm.sLatitude.value;
        
        if(sla == '' || sLongitude == '' || sLatitude == '') {
             alert("Please enter all the values in case of Indoor and Outdoor Coverage !");
        } else {
            mainForm.sla.value = sla;
            mainForm.sLongitude.value = sLongitude;
            mainForm.sLatitude.value = sLatitude;
            if (opener && !opener.closed) opener.focus();
            self.close();
        }
    }
}*/

<!--</script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Coverage Check</title>
    </head>
    <body>
      
        <div class="shadow-box">
            <div class="shadow-box-inner">
                <form  name="coverageform" onSubmit="setMaster()">-->
                    <% WitribeBO bo = new WitribeBO();%>
                    <%String coverage = bo.CheckCoverage(request.getParameter("plot"),request.getParameter("street"),request.getParameter("zone"),request.getParameter("city"));
                    if(coverage == null)
                        out.write("No Coverage");
                        else
                    out.write(coverage);%>
                  <!--  <table>
                    <tr><td><input type="hidden" name="coverage" value= "<%=coverage%>"  /></td></tr>                    
                       
                        <!--<tr><td><label>SLA :</label></td>
                        <td><input type="text" name="sla" value="" class="txtxx" maxlength="15" size="12" onkeypress="javascript: return numbersonly(event);"/></td></tr>
                        
                        <tr><td><label>Longitude :</label></td>
                        <td><input type="text" name="sLongitude" value="" class="txtxx" maxlength="15" size="12" onkeypress="javascript: return numbersonly(event);" /></td></tr>
                        <tr><td><label>Latitude :</label></td>
                        <td><input type="text" name="sLatitude" value="" class="txtxx" maxlength="15" size="12" onkeypress="javascript: return numbersonly(event);" /></td></tr>
                    <tr> <td colspan=2 align="center"><a href="#" onClick="setMaster()">Submit & Close</a></td></tr></table>                    
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    
                </form>
        </div></div>        
    </body>
</html>-->
