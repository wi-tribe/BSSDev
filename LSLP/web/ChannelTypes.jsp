<%@ page contentType="text/html" import="com.witribe.dao.ChannelList, com.witribe.vo.SalesChannel,java.util.*,java.sql.SQLException"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <body>
    <%
        String exception = "";
        try {
            ChannelList cho = new ChannelList();
            List chArray = cho.getChannels();
            if(chArray.size() > 0) {
                for(int i=0; i< chArray.size();i++) {
                    SalesChannel sc = (SalesChannel)chArray.get(i);
                    out.print("<pre>" + sc.getChannelId() + "--" + sc.getChannelName() +"</pre>");
                }
            }
        } catch(SQLException ex) {
            exception = "Exception while loading the data";
        } catch(Exception ex) {
            exception = "Exception while loading the data";
        }
    %>
    <%--
    This example uses JSTL, uncomment the taglib directive above.
    To test, display the page like this: index.jsp?sayHello=true&name=Murphy
    --%>
    <%--
    <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>
    --%>
    
    </body>

