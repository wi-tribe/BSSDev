<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>


<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <div id="inner-content">
        <div class="shadow-box">
            <div class="shadow-box-inner">
<body>
    <form  name="search" method="POST" action="AccountSearch.do?parameter=searchAccountDetails">
        <table width="440" border="0" cellspacing="0" cellpadding="0" class="info-listing">
            <tr>
                <td align="left"><label>Account No</label></td>
                <td align="left"><input type="text" name="accountNo" id="accountNo" width="120" value=""/></td>
            </tr>
            <tr>
                <td><label>User Id</label></td>
                <td><input type="text" name="CNICid" id="CNICid" width="120" value=""/></td>
            </tr>
            <tr>
                <td><label>Mobile No</label></td>
                <td><input type="text" name="firstname" id="firstname" width="120" value=""/></td>
            </tr>   
            <tr>
                <td><input class=submit-btn type="submit" name="search" value="Search" />
                </td>
            </tr>   
        </table>
    </form>
</body>

</div>
        </div>
        </div>
    

