<%@page contentType="text/html" import="com.witribe.vo.LeadVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
         
   <DIV class=more-width id=col2>            
    <TABLE class=form cellSpacing=0 cellPadding=0  border=0>
        <TBODY>
            <TR height=19>
            <TD width=12>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD width=15>&nbsp;</TD>
            <TR>
                <TD>&nbsp;</TD>
                <TD> 
                <form action="./UserPage.do" method="Post">
                     <input type="hidden" name="page" />
                     <input type="hidden" name="flag" />
                    <div id="inner-content">
                                <table width="679" border="0" cellspacing="0" cellpadding="0" class="bill-listing">
                                    <tr>
                                        <td width="10%"><font color="#c60651">Lead ID</font></td>
                                        <td width="10%"><font color="#c60651">Name</font></td>
                                        <td width="10%"><font color="#c60651">Contact</font></td>
                                        <td width="10%"><font color="#c60651">Area</font></td>
                                        <td width="10%"><font color="#c60651">City(s)</font></td>
                                    </tr>
                                    
                                    <logic:iterate id="objUser" name="objUserList" type="com.witribe.vo.UserRegVO" scope="request">
                                        <tr><td width="10%" align="left"><a href="./ViewUsers.do?userId=<bean:write name="objUser" property="userId" />" ><bean:write name="objUser" property="userId" /></a></td>
                                            <td width="10%" align="left"><bean:write name="objUser" property="name"/></td>
                                            <td width="10%" align="left"><bean:write name="objUser" property="email"/></td>
                                            <td width="10%" align="left"><bean:write name="objUser" property="mobile"/></td>
                                            <td width="10%" align="left"><bean:write name="objUser" property="area"/></td>
                                        </tr>
                                    </logic:iterate> 
                                </table>
                                 <table border="0" align="right" width="150">
                             <tr>
                                 <%String pageCount =(String) request.getAttribute("page");
                                 String nextEnable = (String) request.getAttribute("next");%>
                                 <td>
                                 <%if(!"0".equals(pageCount)) {%>
                                 <a href="javascript:prevUserPageClick(${page})">Previous</a>&nbsp;
                                 <%} 
                                 int pageNo = Integer.parseInt(pageCount)+1;%>
                                 <%=pageNo%>
                                  <%if(("true".equalsIgnoreCase(nextEnable))) {%>
                                 <a href="javascript:nextUserPageClick(${page})">Next</a>
                                 <%}%>
                                 </td>
                             </tr>
                         </table>
                                
                            </tbody>
                </table></div></form>
    <TD vAlign=top>&nbsp;</TD></TR>
            <TR height=17>
                <TD>&nbsp;</TD>
                <TD>&nbsp;</TD>
                <TD>&nbsp;</TD></TR>
        </TBODY>
    </TABLE>
</DIV>

              