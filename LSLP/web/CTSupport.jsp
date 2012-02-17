<%@page import="com.witribe.ctutil.*"%>
<%
String sReqFor="";
String sCity = "";
String sZone = "";
String sStreet = "";
String sHouseNo = "";
String sResult = "";
sReqFor = request.getParameter("ReqFor");
sCity = request.getParameter("City");
sZone = request.getParameter ("Zone");
sStreet = request.getParameter ("Street");
sHouseNo = request.getParameter ("HouseNo");
try {
	com.witribe.ctutil.Service service = new com.witribe.ctutil.Service();
	com.witribe.ctutil.ServiceSoap svsPort = service.getServiceSoap();
	
	switch(Integer.valueOf (sReqFor)) {
            case 1 : sResult = svsPort.getZones(sCity);  break;
            case 2 :  sResult = svsPort.getStreetFromZones( sCity, sZone);  break;
            case 3 :  sResult = svsPort.getHouseFromStreetZone(sCity, sZone, sStreet);  break;
            default : sResult = "Exception:";  break;
        }
} catch (Exception e) {
        out.print(e.toString());
	sResult = "Exception:";
}
out.print (sResult);
%>