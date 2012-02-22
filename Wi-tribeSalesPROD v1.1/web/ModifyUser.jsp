<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
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

<DIV class=more-width id=col2>            
    <TABLE class=form cellSpacing=0 cellPadding=0  border=0 
           background="images/table_bg.gif">
        <TBODY>
            <TR height=19>
            <TD width=12>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD width=15 background="./images/up_right.gif">&nbsp;</TD>
            <TR>
                <TD>&nbsp;</TD>
                <TD> 
          <form  action="./UserDataSave.do?userId=<%=request.getParameter("userId")%>" method="Post">
                    
                    <table align="center">
                        <bean:define id="objUsers"  name="objuser" type="com.witribe.vo.UserRegVO" scope="request"/>
                            <tr><td><label>Name</label></td>
                            <td><bean:write name="objUsers" property="name"/></tr>
                            <tr><td><label>Email</label></td>
                            <td><bean:write name="objUsers" property="email"/></td></tr>
                            <tr><td><label>Contact </label></td>
                            <td><bean:write name="objUsers" property="mobile"/></td></tr>
                            <tr><td><label>Sector/area</label></td>
                            <td><bean:write name="objUsers" property="area"/></tr>
                        <tr><td align="right"><input type="submit" value="ModifyUser" name="ModifyUser"/></td></tr>
                        
        </table></div></form>
    <TD vAlign=top 
                background="images/border_pixel.gif">&nbsp;</TD></TR>
            <TR height=17>
                <TD background="./images/bottom_left.gif">&nbsp;</TD>
                <TD background="./images/border_pixel.gif">&nbsp;</TD>
                <TD 
                background="images/border_pixel.gif">&nbsp;</TD></TR>
        </TBODY>
    </TABLE>
</DIV>
