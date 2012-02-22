<%@page import="com.witribe.ctutil.*"%>
<%
String strHouseNo = request.getParameter ("HouseNo").trim();
String strStreet = request.getParameter ("Street").trim();
String strSubZone = request.getParameter ("SubZone").trim();
String strZone = request.getParameter ("Zone").trim();
String strCity = request.getParameter ("City").trim();
String strUsb = request.getParameter("USB").trim();
String strInDoor = request.getParameter("INDOOR").trim();
String strOutDoor = request.getParameter("OUTDOOR").trim();
String sx = request.getParameter("x").trim();
String sy = request.getParameter("y").trim();
//out.print(sx+"</br>");
//out.print(sy);
String sResult1 = "";
String sResult2 = "";
String strLongitude = "";
String strLatitude = "";
String covStatus = "";
String sla = "";
String scoOrdinates = "";
double x=0.00;
double y=0.00;


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

/*
 *67.075352578;24.8474560820001;BLOCK T,GULBERG 1,LAHORE

Zone,23,27,49,0

 */

//out.print(strHouseNo + ":" + strStreet + ":" + strZone + ":"+ strCity + ":");
try
{
    sResult1 = sx + ";" + sy + ";" + strHouseNo + "," + strStreet+ "," + strSubZone + "," + strZone + "," + strCity;
     
    if(sResult1 !=null )
    {
        arrCoOrdinates = sResult1.split (";");
        sx = arrCoOrdinates[0];
        sy = arrCoOrdinates[1];
//out.print("in first loop");
        x = Double.parseDouble (sx);
        y = Double.parseDouble (sy);
// Plot, USB Coverage, Indoor Coverage, Outdoor coverage , xx
        sResult2 = "plot," + strUsb + "," +  strInDoor + "," + strOutDoor + ",0";
        arrResult2 = sResult2.split (",");
        
      
        if(arrResult2.length > 0) 
        {
            susbCoverage = arrResult2[1].trim ();
            sindoorCoverage = arrResult2[2].trim ();
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


                                                           <tr><td align="right" colspan=2>&nbsp;</td><td><input type="image" name="submit" src="images/btn-go.gif" alt="Request Change" title="Request Change" 
                                                               onmouseover="this.src='images/btn-go-on.gif'" onmouseout="this.src='images/btn-go.gif'" />
</td></tr> 

</table>
<%  }
else
{
    out.print (sResult1);
    }
%>