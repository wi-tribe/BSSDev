<%@page import="com.witribe.ctutil.*"%>
<%
String endpointString = "http://124.109.63.244/WiTribeGCService/Service.asmx?WSDL";
String strHouseNo = request.getParameter ("HouseNo");
String strStreet = request.getParameter ("Street");
String strZone = request.getParameter ("Zone");
String strCity = request.getParameter ("City");
String strTask = request.getParameter ("TASK");
String strQuery = request.getParameter ("query");
if(strQuery == null) {
    strQuery = "";
}
String sResult1 = "";
String sResult2 = "";
String strLongitude = "";
String strLatitude = "";
String covStatus = "";
String sla = "";
String scoOrdinates = "";
double x=0.00;
double y=0.00;
String sx = "";
String sy = "";
if(strQuery.equalsIgnoreCase ("manual")) {
    sx = request.getParameter ("x");
    sy = request.getParameter ("y");
}
boolean proceed = false;
double usbCoverage = 0.0;
double indoorCoverage = 0.0;
double outdoorCoverage = 0.0;
String susbCoverage = "";
String sindoorCoverage = "";
String soutdoorCoverage = "";
String arrCoOrdinates[] = null;
String arrResult2[] = null;
String cvgType = "";
String result;
String sCVGStatus = "";
String dCVGStatus = "";
String sPointCoverage = "";
sPointCoverage = "not covered";
if (strStreet.equalsIgnoreCase ("No Names"))
{
    strStreet = "";
}

if(strZone.indexOf (" - ") > -1)
{
    String[] tmpZone = strZone.split (" - ");
    strZone = "";
    int arrLength = tmpZone.length;
    for(int i=0; i<arrLength; i++)
    {
        String sTemp = tmpZone[arrLength - 1 - i];
        strZone = strZone + "," + sTemp.trim ();
    }
    strZone = strZone.substring (1);
    strZone = strZone.trim ();
}
//out.print(strHouseNo + ":" + strStreet + ":" + strZone + ":"+ strCity + ":");
try
{
    com.witribe.ctutil.Service service = new com.witribe.ctutil.Service();
    com.witribe.ctutil.ServiceSoap svsPort = service.getServiceSoap();
    if(strQuery.equalsIgnoreCase ("manual")) {
        //out.print("<br>called manual <br>");
        sResult1 = sx + ";" + sy + ";  " + strHouseNo +",  " + strStreet + ",  " + strZone + ",  " + strCity;
    } else {
        //out.print("<br>called without manula <br>");
        sResult1 = svsPort.geoCodeAddress (strHouseNo, strStreet, strZone, strCity);
    }
    //out.print("<br>" + sResult1 + "<BR>");
    if(sResult1 !=null )
    {
        arrCoOrdinates = sResult1.split (";");
        sx = arrCoOrdinates[0];
        sy = arrCoOrdinates[1];
//out.print("in first loop");
        x = Double.parseDouble (sx);
        y = Double.parseDouble (sy);
// Plot, USB Coverage, Indoor Coverage, Outdoor coverage , xx
        if(strStreet.equals ("") && strHouseNo.equals ("") && (!strZone.equals ("")))
        {
            //out.print ("<BR>called is zone covered by lanlon -conv " + Double.toString (x) + "::" + Double.toString (y) + "<BR>");
            //out.print ("<br>" + arrCoOrdinates[2] + "<BR>");
            sResult2 = svsPort.isZoneCoveredByLatLon (x, y);
        }
        else
        {
            //out.print ("<BR>called is area covered by lanlon -  conv " + Double.toString (x) + "::" + Double.toString (y) + "<BR>");
           // out.print ("<br>" + arrCoOrdinates[2] + "<BR>");
            sResult2 = svsPort.isAreaCoveredByLatLon (x, y);
        }
        //out.print ("<BR>Result 2:" + sResult2 + "<br>");
        arrResult2 = sResult2.split (",");
        if(arrResult2.length > 0) 
        {
            susbCoverage = arrResult2[1].trim ();
            sindoorCoverage = arrResult2[2].trim ();
            //out.print("<br>" +sResult2);
            soutdoorCoverage = arrResult2[3].trim ();
            cvgType = arrResult2[0].trim ();
            usbCoverage = Double.parseDouble (susbCoverage);
            indoorCoverage = Double.parseDouble (sindoorCoverage);
            outdoorCoverage = Double.parseDouble (soutdoorCoverage);
            if(arrResult2[0].trim ().equalsIgnoreCase ("Zone"))
            {
                 sPointCoverage = "not covered";
                if(arrResult2.length > 4) {
                    if(arrResult2[4].trim ().equals ("1"))
                    {
                        sPointCoverage = "covered";
                    }
                    else
                    {
                        sPointCoverage = "not covered";
                    }
                }
            } else {
                if(usbCoverage > 25 || indoorCoverage > 25 || outdoorCoverage > 25 ) {
                    sPointCoverage = "covered";
                } else {
                    sPointCoverage = "not covered";
                }
            }
        }
    } else {
    sResult1 = "--No Match Found--";
    sResult2 = "--No Match Found--";
    }
    service = null;
    svsPort = null;
    
}
catch (Exception e)
{
    sResult1 = "Error: Try again";
    sResult2 = "Error: Try again";
    //out.print(e.toString());
}
%>
<% if(!sResult1.equals ("Error: Try again") || !(sResult1.equals ("--No Match Found--")))
{ %>
<table align="center" border="0"  class=ct-info>
    <tr>
        <td class="textboxBur" >Address</td>
        <td colspan="2" class="textboxGray"><label>&nbsp;<%=arrCoOrdinates[2]%></label></td>
    </tr> 
    <%	 //if(arrResult2[0].trim ().equalsIgnoreCase ("Zone"))  {	%>
    <tr>
        <td class="textboxBur">Location coverage:</td>
        <td class="textboxGray" colspan="2"><label>&nbsp;<%=sPointCoverage%></label></td>
    </tr>
    <%	//}	%>
    <tr>
        <td colspan="3" align=center ><strong><span style="color:#c60651; font-size:13px"><%=arrResult2[0].trim ()%> </span>Coverage Info</strong></td>
    </tr>
    <tr>
        <td class="textboxBur">USB:</td>
        <td align="right" class="textboxGray"><label><%=susbCoverage%>%</label></td>
        <td class="textboxGray"><label><%=((usbCoverage < 25) ? "Not Covered" : "Covered" )%></label></td>
    </tr>
    <tr>
        <td class="textboxBur">Indoor: </td>
        <td align="right" class="textboxGray"><label><%=sindoorCoverage%>%</label></td>
        <td class="textboxGray"><label><%=((indoorCoverage < 25) ? "Not Covered" : "Covered" )%></label></td>
    </tr>
    <tr>
        <td class="textboxBur">Outdoor: </td>
        <td align="right" class="textboxGray"><label><%=soutdoorCoverage%>%</label></td>
        <td class="textboxGray"><label><%=((outdoorCoverage < 25) ? "Not Covered" : "Covered" )%></label>
    <input type='hidden' name='coverage' value="<%=sResult2%>" />
<input id="coordinates" type="hidden" value="<%out.print(sx + "," + sy);%>">	
<input id="gcSer" type="hidden" value="<%=arrCoOrdinates[2]%>">
<input id="coverage_value" type="hidden" value="<%=sResult2%>">
<input type='hidden' name='x' value="<%=sx%>"/>
<input type='hidden' name='y' value="<%=sy%>"/>
<input type='hidden' name='gc' value="<%=arrCoOrdinates[2]%>"/>
<input type=hidden name=cvgchkstatue value="<%=sPointCoverage%>">

    </td>
    </tr>
                                                           
 <tr><td align="right"> <input type="button" value="Update&nbsp;Status" name="ConvertToProspect" onclick="javascript:formSubmit1('./GetReason.do?leadId=<%=request.getParameter("leadId")%>')"/></td>
                                    <td></td><td align="right"> <input type="button" value="Convert&nbsp;to&nbsp;Account" name="ConvertToAccount" onclick="javascript:formSubmitAccount1('./GetMore.do');" /></td></tr>

</table>
<%  }
else
{
    out.print (sResult1);
    }
%>