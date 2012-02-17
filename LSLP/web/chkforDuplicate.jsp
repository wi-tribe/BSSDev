<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="error.jsp" import="
com.portal.pcm.fields.FldInvInfo, Wtb.MyAccount.MyAcctUtility,com.portal.web.comp.*, com.portal.web.*, java.net.*, com.portal.pcm.fields.* ,customfields.*,com.portal.bas.*, com.portal.bas.comp.* , java.text.*, com.portal.web.ServletUtil, com.portal.pfc.util.PI18nHelper, java.util.*, com.portal.pcm.*, com.portal.app.ccare.comp.PBillInfoBean,java.math.BigDecimal" %>
<%@ include file="includes/constants.jsp"%>
<%
MyAcctUtility wtbUtil = null;
Locale locale = (Locale) session.getAttribute(ServletUtil.LOCALE);
PPooledConnectionClientServices pCS = (PPooledConnectionClientServices) application.getAttribute("CREATE_CONNECTION");
//Create the connection if the connection does not exists.
if (pCS == null) {
// Create a new client service
    pCS =  new PPooledConnectionClientServices( (PClientServices) application.getAttribute(ServletUtil.PARENT_SERVICE));
    
// Setup a connection for this transaction
    Object o = ServletUtil.logIn(pCS);
    application.setAttribute("CREATE_CONNECTION", pCS);
    
// Initialize the listener with the client services object
// so it can handle return of the connection when session expires
    ConnectionListener listener = new ConnectionListener(session.getCreationTime() , pCS);
    application.setAttribute("CREATE_LISTENER", listener);
    
// since controller is New we need to grab the http headers
// to determine the locale etc.
    ServletUtil.saveLocaleInfo(request);
    pCS.registerApp("CONTENT_APP_NAME", (Locale) session.getAttribute(ServletUtil.LOCALE), null);
}
wtbUtil =(MyAcctUtility) pCS.createController("Wtb.MyAccount.MyAcctUtility");
Properties props = pCS.getDefaultProperties();
int iMaxCNIC =  0;
if(props.getProperty("maxDuplicateCNIC") == null) {
    iMaxCNIC = 1;
} else {
    iMaxCNIC = Integer.parseInt(props.getProperty("maxDuplicateCNIC"));
}
String cnicvalue = request.getParameter("identity");
wtbUtil.setMaxCNIC(iMaxCNIC);
wtbUtil.setCNIC(cnicvalue);
if(cnicvalue != null) {
    out.print(wtbUtil.checkDuplicate());
} else {	//nothing to show
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
}
wtbUtil = null;
pCS = null;
locale = null;
%>
