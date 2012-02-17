<%@ page contentType="text/html;charset=UTF-8" language="java"  import="
com.portal.pcm.fields.FldInvInfo, Wtb.MyAccount.*,com.portal.web.comp.*, com.portal.web.*, java.net.*, com.portal.pcm.fields.* ,customfields.*,com.portal.bas.*, com.portal.bas.comp.* , java.text.*, com.portal.web.ServletUtil, com.portal.pfc.util.PI18nHelper, java.util.*, com.portal.pcm.*, com.portal.app.ccare.comp.PBillInfoBean,java.math.BigDecimal" %>
<%@ include file="includes/constants.jsp"%>
<%
//System.out.println("get special numbers");
Locale locale = (Locale) session.getAttribute(ServletUtil.LOCALE);
PPooledConnectionClientServices pCS = (PPooledConnectionClientServices) application.getAttribute("CREATE_CONNECTION");
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
Properties props = pCS.getDefaultProperties();
if(request.getParameter("WANAddr") != null) {
String wanaddr="";
wanaddr = request.getParameter("WANAddr");
SpecialNumbers wtbUtil = (SpecialNumbers) pCS.createController("Wtb.MyAccount.SpecialNumbers"); 
String sCity=request.getParameter("City");
String service=request.getParameter("ServiceType");
int serviceType=Integer.parseInt(service);
//System.out.println("Password. "+wanaddr+"Service Type "+serviceType+"City "+sCity);
wtbUtil.setWanAdrr(wanaddr);
out.print(wtbUtil.getSpecialNumbers(sCity,serviceType));
}
 else {
    //System.out.println("Else part in getSpecialNumbers JSP...");
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
}
%>