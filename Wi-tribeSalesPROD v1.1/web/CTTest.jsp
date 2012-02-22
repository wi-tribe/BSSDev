<%@page contentType="text/html"%>
<%@page import="com.witribe.ctutil.*"%>
<%@page pageEncoding="UTF-8"%>
<%
String endpointString = "http://124.109.63.244/WiTribeGCService/Service.asmx?WSDL"; 
String strHouseNo = request.getParameter("HouseNo");
String strStreet = request.getParameter("Street");
String strZone = request.getParameter("Zone");
String strCity = request.getParameter("City");
String strTask = request.getParameter("TASK");

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
try {
	com.witribe.ctutil.Service service = new com.witribe.ctutil.Service();
	com.witribe.ctutil.ServiceSoap svsPort = service.getServiceSoap();
	//out.print("<BR>" + strHouseNo + ":" + strStreet + ":" +  strZone + ":" + strCity);
	sResult1 = svsPort.geoCodeAddress(strHouseNo, strStreet, strZone, strCity);

	if(sResult1 !=null && strTask == null)
	{
		arrCoOrdinates = sResult1.split(";");
		sx = arrCoOrdinates[0];
		sy = arrCoOrdinates[1];
		//out.print("in first loop");
		x = Double.parseDouble(sx);
		y = Double.parseDouble(sy);
		// Plot, USB Coverage, Indoor Coverage, Outdoor coverage , xx
		sResult2 = svsPort.isAreaCoveredByLatLon(x, y);
		arrResult2 = sResult2.split(",");
		susbCoverage = arrResult2[1].trim();
		sindoorCoverage = arrResult2[2].trim();
		soutdoorCoverage = arrResult2[3].trim();
		cvgType = arrResult2[0].trim();
		usbCoverage = Double.parseDouble(susbCoverage);
		indoorCoverage = Double.parseDouble(sindoorCoverage);
		outdoorCoverage = Double.parseDouble(soutdoorCoverage);
		//out.print("<br>" +sResult2);
	}
	else {
		arrCoOrdinates = new String[3];

		if(strTask != null && strTask.equals("QUERY")) {
			sx = request.getParameter("x");
			sy = request.getParameter("y");
			//out.print("<BR>" + sx + ":" + sy+ "<br>");
			arrCoOrdinates[0] = sx;
			arrCoOrdinates[1] = sy;
			arrCoOrdinates[2] = strHouseNo + ", " + strStreet + ", " + strZone + ", " + strCity;
		}
		x = Double.parseDouble(sx);
		y = Double.parseDouble(sy);
		//out.print("in second loop");
		// Plot, USB Coverage, Indoor Coverage, Outdoor coverage , xx
		sResult2 = svsPort.isAreaCoveredByLatLon(x, y);
		//out.print("---" + sResult2 + "---");
		arrResult2 = sResult2.split(",");
		if(arrResult2.length > 2) {
		susbCoverage = arrResult2[1].trim();
		sindoorCoverage = arrResult2[2].trim();
		soutdoorCoverage = arrResult2[3].trim();
		cvgType = arrResult2[0].trim();
		usbCoverage = Double.parseDouble(susbCoverage);
		indoorCoverage = Double.parseDouble(sindoorCoverage);
		outdoorCoverage = Double.parseDouble(soutdoorCoverage);
		}
	}
	service = null;
	svsPort = null;

} catch (Exception e) {
	out.print("<!--" + e.toString() + "-->");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD><TITLE>wi-tribe - Customer Care Portal</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/CTstyle.css" rel="stylesheet" type="text/css">
<STYLE type=text/css>
	body {
	background-color:#CCCCCC ;/*#5F5D60*/
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	}
</STYLE>
<link href="css/Stylesstyle.css" rel="stylesheet" type="text/css">
<link href="js/jquery/ui.datepicker.css" rel="stylesheet" type="text/css">
<script src="js/jquery/jquery.js" type="text/javascript" language="javascript"></script>
<script src="js/jquery/ui.core.js" type="text/javascript" language="javascript"></script>
<script src="js/jquery/ui.datepicker.js" type="text/javascript" language="javascript"></script>
<script src="js/jquery/jquery.validate.js" type="text/javascript" language="javascript"></script>
<script language="javascript" src="js/xmlGet.js" type="text/javascript"></script>
<script language="javascript" src="js/xmlPost.js" type="text/javascript"></script>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAaNcmKM0QttFysMz8uw35sxTG8J352D2Eb8T5S8c8GFOi8BatshQBuLvpfzx3pN_6ZYPzMjiklU8fGg" type="text/javascript"></script>
<script src="http://serverapi.arcgisonline.com/jsapi/gmaps/?v=1.3" type="text/javascript" ></script>
<SCRIPT>
function mcallBack()
{
	gcallbackfunc=null;
	content=null;
}
function search_address()
{
	content="search_results_div";
	clearGetSpan();
	gcallbackfunc="mcallBack()";
	customGetText(" searching address ...");
	plot_no=document.address.house_no.value;
	road_no=document.address.road_street.value;
	sector=document.address.area.value;
	city=document.address.city.value;
	getstr = "?house_no="+plot_no+"&road_street="+road_no+"&area="+sector+"&city="+city;
}

function locate()
{
	content="locate_div";
	clearGetSpan();
	gcallbackfunc="show_map()";
	customGetText(" locating address ...");
	plot_no=document.address.house_no.value;
	road_no=document.address.road_street.value;
	sector=document.address.area.value;
	city=document.address.city.value;
	gc = plot_no+","+road_no+","+sector+","+city;
	//alert(gc);
	getstr = "?house_no="+plot_no+"&street="+road_no+"&zone="+sector+"&city="+city;
}

function locate_poi()
{
    content="locate_div";
	clearGetSpan();
	gcallbackfunc="mcallBack()";
    customGetText(" locating address ...");
	gc = plot_no+","+road_no+","+sector+","+city;
    getstr = "?address="+gc;
}

var x,y,gcG;
var map="nullV";
var urlAd;

function show_map()
{
	gc=document.address.house_no.value+","+document.address.road_street.value+","+document.address.area.value+","+document.address.city.value;
	coord = document.locater.coordinates.value;
	coverage_value = document.locater.coverage_value.value;
	xy = coord.split(',');
	x = xy[0];
	y = xy[1];
	gcG = gc;
	initialize();
}

function swapKML()
{
    var dynamicMap = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/Coverage/MapServer",null,0.45,dynmapcallback);
}

function swapUSBCoverage()
{
    var dynamicMap15 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/USBCoverage/MapServer",null,0.45,dynmapcallback15);
}

function swapOutDoorCoverage()
{
    var dynamicMap5 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/OutDoorCoverage/MapServer",null,0.45,dynmapcallback5);
}

function swapInDoorCoverage()
{
    var dynamicMap9 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/InDoorCoverage/MapServer",null,0.45,dynmapcallback9);
}

function swapRoadsKML()
{
    var dynamicMap2 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/IsbRoads/MapServer",null,0.80,dynmapcallback2);
    var dynamicMap3 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/PakistanRoads/MapServer",null,0.80,dynmapcallback3);
}

function swapZonesKML()
{
    var dynamicMap4 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/PakistanZones/MapServer",null,1.0,dynmapcallback4);
}

function swapSitePoints() 
{ 
	var dynamicPoint =  new  esri.arcgis.gmaps.DynamicMapServiceLayer("http://115.167.72.11/ArcGIS/rest/services/CoverageSitePoints/MapServer",null,1.0,dynmapCallSite); 
} 

function initialize()
{
    //alert('Called');
 
	if (GBrowserIsCompatible())
	{
		//alert(map);
		if(map=="nullV"){
		map = new GMap2(document.getElementById("map_canvas"));
			map.enableScrollWheelZoom();
		
		GEvent.addListener(map,'zoomend',function(){
 
				zooming();
 
			});
		
		var zoomlvl = 18;
		if(document.address.area.value==""){
			zoomlvl=13;
		}else if(document.address.road_street.value==""){
			zoomlvl=15;		
		}else if(document.address.house_no.value==""){
			zoomlvl=16;
		}else{
			zoomlvl=18;
		}
		
		
		
         	var givenmaptypes = map.getMapTypes();
         	map.setMapType(givenmaptypes[2]);
        	//alert("create controller");
		var mapControl = new GMapTypeControl();
		map.addControl(mapControl);      	
		map.addControl(new GLargeMapControl());
        	//alert("create controller-1");		
		var ovCont = new GOverviewMapControl();
		ovCont.setMapType(givenmaptypes[2]);
		map.addControl(ovCont);
		}
		//alert("adding listener");
		map.setCenter(new GLatLng(y, x), zoomlvl);
		var point = new GLatLng( y, x);
		var gmarker = new GMarker(point);
        	//alert("create controller-3");
		map.addOverlay(gmarker);
		swapSitePoints();
 
    }
}




var dynMapOv;
var flag0=0;
var flag=0;
var flag1=0;
var flag2=0;
var dynMapOv2;
var dynMapOv3;
var dynMapOv4;

var dynMapOv5;
var dynMapOv9;
var dynMapOv15;
var dynMap; 



function dynmapcallback(groundov)
{
    if(document.kmlBoxes.C.checked)
    {
        map.addOverlay(groundov);
        flag0=1;
        dynMapOv = groundov;
    }else{
        map.removeOverlay(dynMapOv);
        flag0=0;
    }
}


function dynmapCallSite(groundov) { 

      //Add groundoverlay to map using map.addOverlay() 
	  map.addOverlay(groundov); 
	  dynMap= groundov; 
 } 
 
function dynmapcallback15(groundov)
{
    //Add groundoverlay to map using map.addOverlay()
	if(document.kmlBoxes2.U.checked)
    {
        map.addOverlay(groundov);
        dynMapOv15 = groundov;
    }else{
        map.removeOverlay(dynMapOv15);
		
	}
}

function dynmapcallback9(groundov)
{
    //Add groundoverlay to map using map.addOverlay()
	if(document.kmlBoxes2.I.checked)
    {
        map.addOverlay(groundov);
        dynMapOv9 = groundov;
    }else
    {
        map.removeOverlay(dynMapOv9);
    }
}
function dynmapcallback5(groundov)
{
    //Add groundoverlay to map using map.addOverlay()
	if(document.kmlBoxes2.O.checked)
    {
        map.addOverlay(groundov);
        dynMapOv5 = groundov;
	}else
    {
        map.removeOverlay(dynMapOv5);
    }
}

function zooming()
{
    if (map.getZoom()<16)
	{
		if(flag==1)
		{
			map.removeOverlay(dynMapOv2);
			map.removeOverlay(dynMapOv3);
			flag=0;
		}
		if(flag1==0)
		{swapZonesKML();}
	}
	else
	{
	   if(flag1==1)
		{
			map.removeOverlay(dynMapOv4);
			flag1=0;
		}
        if(flag==0)
		{swapRoadsKML();}
	}
}


function dynmapcallback2(groundov)
{
    //Add groundoverlay to map using map.addOverlay()
	if(document.kmlBoxes.R.checked && map.getZoom()>=16)
	{
        map.addOverlay(groundov);
        flag=1;
        dynMapOv2 = groundov;
    }else
    {
        map.removeOverlay(dynMapOv2);
        flag=0;
    }
}

function dynmapcallback3(groundov) 
{
    //Add groundoverlay to map using map.addOverlay()
    if(document.kmlBoxes.R.checked  && map.getZoom()>=16)
    {
        map.addOverlay(groundov);
        flag=1;
        dynMapOv3 = groundov;
    }
    else
    {
        map.removeOverlay(dynMapOv3);
        flag=0;
    }
}

function dynmapcallback4(groundov)
{
    //	alert(map.getZoom());
    //	Add groundoverlay to map using map.addOverlay()
    if(document.kmlBoxes.Z.checked  && map.getZoom()<16)
    {
        map.addOverlay(groundov);
        flag1=1;
        dynMapOv4 = groundov;
    }else {
        map.removeOverlay(dynMapOv4);
        flag1=0;
    }
}

function swapInput()
{
	if(document.kmlBoxes.ad_input.checked == true)
	{
		document.getElementById("drill").style.display= "none";
		document.getElementById("manual").style.display = 'inline';
	}else
	{
		document.getElementById("drill").style.display= "inline";
		document.getElementById("manual").style.display = 'none';
	}
}

function submitManual()
{
	var form = document.coverage_info;
	
	validated =true;
	errors="";
	if($.trim(form.x.value)=="")
    {  
		validated= false;
		errors+="Please Enter latitude<BR>";
    }
	
	if($.trim(form.y.value)=="")
    {	
		validated= false;
    	errors+="Please Enter longitude<BR>";
    }
	if(!validated)
	{
		document.getElementById("manual_error").innerHTML = errors;
		return false;
	}
	
	var house_no=$.trim(form.house_no.value.replace(/,/g," "));
	var road_street=$.trim(form.road_street.value.replace(/,/g," "));
	var area=$.trim(form.area.value.replace(/,/g," "));
	form.gc.value = house_no+","+road_street+","+area+","+form.city.value;
	form.submit();
	return true;

}
</SCRIPT>

<META name=GENERATOR content="MSHTML 8.00.6001.18702"></HEAD>
<BODY leftMargin=0 topMargin=0 marginheight="0" marginwidth="0">
<TABLE cellSpacing=0 cellPadding=0 width=1000 align=center>
  <TBODY>

  <TR>
    <TD><BR>
      <TABLE class=td border=0 cellSpacing=0 borderColor=#cccccc cellPadding=0  border=2
      width="100%" bgColor=#f7f7f7 align=center>
        <TBODY>
        <TR>
          <TD>
            <TABLE border=0 cellSpacing=0 cellPadding=0 width="98%" align=center>
              <TBODY>
              <TR>
                <TD vAlign=top width=5 align=left><IMG  src="images/left_table_curve.gif" width=5 height=23></TD>
                <TD class=whiteHeading background=images/pink_line.gif>Coverage Tool</TD>
                <TD vAlign=top width=5 align=right><IMG src="images/right_table_curve.gif" width=5 height=23></TD>
				</TR>
              <TR>
                <TD vAlign=top background=images/table_left_line.gif align=left></TD>
                <TD vAlign=top align=middle>
                  <TABLE width="100%">
                    <TBODY>
                    <TR>
                      <TD colSpan=4>
                        <TABLE width="100%">
                          <TBODY>
                          <TR>
                            <TD align=middle>
							<!-- Important Div tag do not delete - Start -->
							<DIV style="WIDTH: 800px; HEIGHT: 600px" id=map_canvas></DIV>
							<!-- Important Div tag do not delete - End-->
							</TD></TR></TBODY></TABLE></TD></TR>
                    <TR>
                      <TD vAlign=top  width="40%"><SPAN 
                        style="COLOR: #c60551; FONT-SIZE: 14px"><STRONG>Address 
                        </STRONG></SPAN>
						<!-- Address form Starts here -->
                        <FORM name=address>
                        <DIV id=drill>
                        <TABLE width="100%">
                          <TBODY>
                          <TR>
                            <TD class=textboxBur height=22 align=left>City</TD>
                            <TD align=left><DIV id=zone_div class=normalGrayFont><%=strCity%></div><input type=hidden  name=city value="<%=strCity%>"> </TD></TR>
                          <TR>
                            <TD class=textboxBur align=left>Sector.</TD>
                            <TD align=left><DIV id=zone_div class=normalGrayFont><%=strZone%><input type=hidden name=area value="<%=strZone%>"></DIV></TD>
							</TR>
                          <TR>
                            <TD class=textboxBur align=left>Street / Category</TD>
                            <TD class=normalColoredFont align=left>
                              <DIV id=street_div class=normalGrayFont><%=strStreet%><input type=hidden name=road_street value="<%=strStreet%>"></DIV></TD></TR>
                          <TR>
                            <TD class=textboxBur align=left>Plot No/Name.</TD>
                            <TD class=normalColoredFont align=left>
                              <DIV id=house_div class=normalGrayFont><%=strHouseNo%><input type=hidden name=house_no value="<%=strHouseNo%>"></DIV></TD></TR>
                          <TR>
                            <TD></TD>
                            <TD align=left>
				&nbsp;
			</TD></TR></TBODY></TABLE></DIV></FORM>
<!--   Form for manual entry -- Star here -->                        
<FORM method=post name=coverage_info action=CTTest.jsp>
	<DIV style="DISPLAY: none" id=manual>
		<INPUT type=hidden name=gc> 
		<INPUT type=hidden name=coverage> 
			<TABLE width="100%">
				<TBODY>
					<TR>
						<TD class=textboxBur></TD>
							<TD class=normalColoredFont><DIV id=manual_error></DIV></TD>
						</TR>
                        <TR>
							<TD class=textboxBur>Plot No.</TD>
                            <TD class=normalColoredFont><INPUT class=textboxGray readonly type=text name=HouseNo  value="<%=strHouseNo%>"></TD>
						</TR>
                        <TR>
                            <TD class=textboxBur>Street / Road</TD>
							<TD class=normalColoredFont><INPUT readonly class=textboxGray type=text name=Street value="<%=strStreet%>"></TD>
						</TR>
                        <TR>
                            <TD class=textboxBur>Sector.</TD>
                            <TD class=normalColoredFont><INPUT class=textboxGray readonly type=text name=Zone value="<%=strZone%>"> <SPAN class=mandatoryRed>(e.g. F-8/4 ) </SPAN></TD>
						</TR>
                        <TR>
                            <TD class=textboxBur height=22>City</TD>
                            <TD><input type=text  class=textboxGray readonly name=City value="<%=strCity%>"></TD>
						</TR>
                        <TR>
                            <TD class=textboxBur>latitude</TD>
                            <TD class=normalColoredFont><INPUT class=textboxGray type=text name=y></TD>
						</TR>
                        <TR>
                            <TD class=textboxBur>longitude</TD>
                            <TD class=normalColoredFont><INPUT class=textboxGray type=text name=x></TD>
						</TR>
                        <TR>
                            <TD></TD>
                            <TD><INPUT value=QUERY type=hidden name=TASK>
				<INPUT class=button  value=Search type=submit>
                            </TD>
						</TR>
					</TBODY>
				</TABLE>
			</DIV>
		</FORM>
		<!-- form for manual entry  -- Ends here -->
		</TD>
<!-- Coverage layers section -->
<TD class=normalGrayFont vAlign=top width="25%">
	<SPAN style="COLOR: #c60551; FONT-SIZE: 14px"><STRONG>Coverage                Layers </STRONG></SPAN>
	<DIV id=kml_div2 class=textboxGray>
		<FORM name=kmlBoxes2>
			<INPUT onclick=javascript:swapUSBCoverage(); value=USB type=checkbox name=U>USB<BR>
			<INPUT onclick=javascript:swapInDoorCoverage(); value=InDoor type=checkbox name=I>In Door<BR>
			<INPUT onclick=javascript:swapOutDoorCoverage(); value=OutDoor type=checkbox name=O>Out Door<BR>
		</FORM>
	</DIV>
	<SPAN style="COLOR: #c60551; FONT-SIZE: 14px"><STRONG>Digital Maps</STRONG></SPAN> 
	<DIV id=kml_div class=textboxGray>
		<FORM name=kmlBoxes>
			<!--<input type="checkbox" name="C" value="Coverage" onClick="javascript:swapKML();"> Coverage<br>-->
			<INPUT onclick="" value=Parcels type=checkbox name=P> Parcels<BR>
			<INPUT onclick=javascript:swapRoadsKML(); value=Roads type=checkbox name=R> Roads<BR>
			<INPUT onclick=javascript:swapZonesKML(); value=Zones type=checkbox name=Z> Zones<BR>
			<INPUT onclick=javascript:swapInput(); value=ad_input type=checkbox name=ad_input> select if address is not found in list<BR>
		</FORM>
	</DIV>
</TD>
<!--  Coverage layers section ends here  -->

<TD valign=top class=normalGrayFont width="35%">
	<FORM class=bigBoldFont method=post name=locater action=check_coverage.php>
		<DIV id=locate_div class=normalGrayFont align=center>
			<!-- Generated out put  for http://oe-fut.wi-tribe.net.pk/dev/coverage/search_gc.php?house_no=145&street=11&zone=MARGALLA TOWN&city=Islamabad-->
<!-- Retun output format structure begin-->
<%
		if(usbCoverage < 25 && indoorCoverage < 25 &&  outdoorCoverage < 25) {
				sCVGStatus = "No Coverage";
				dCVGStatus = "not covered";
			} else {
				sCVGStatus = "Covered";
				dCVGStatus = "covered";
			}
		%>
<table  width="100%">
<tr>
<td class="textboxBur" width="40%" >Address</td>
<td colspan="2" class="textboxGray">&nbsp; <%=arrCoOrdinates[2]%></td></tr>
<tr><td class="textboxBur" width="40%" >Coverage Info</td><td colspan="2" class="textboxGray"><%=dCVGStatus%></td></tr> <tr>
        	<td colspan="3" ><strong><span style="color:#c60651; font-size:18px"><%=cvgType%> </span>Coverage Info</strong></td>
   	   </tr>
       <tr>
        	<td class="textboxBur">USB:</td>
        	<td align="center" class="textboxGray"><%=susbCoverage%>%</td>
        	<td class="textboxGray"><%=((usbCoverage < 25) ? "Not Covered" : "Covered" )%></td>
    	</tr>
         <tr>
        	<td class="textboxBur">Indoor: </td>
        	<td align="center" class="textboxGray"><%=sindoorCoverage%>%</td>
        	<td class="textboxGray"><%=((indoorCoverage < 25) ? "Not Covered" : "Covered" )%></td>
    	</tr>
         <tr>
        	<td class="textboxBur">Outdoor: </td>
        	<td align="center" class="textboxGray"><%=soutdoorCoverage%>%</td>
        	<td class="textboxGray"><%=((outdoorCoverage < 25) ? "Not Covered" : "Covered" )%></td>
    	</tr>
    </table>
<!-- REturn output format structure - ends -->
<input type=hidden name=cvgUSB value="<%=susbCoverage%>">
<input type=hidden name=cvgIndoor value="<%=sindoorCoverage%>">
<input type=hidden name=cvgOutdoor value="<%=soutdoorCoverage%>">
<input type=hidden name=cvgStatus value="<%=sCVGStatus%>">
<input type='hidden' name='coverage' value="<%=sResult2%>" />
<input id="coordinates" type="hidden" value="<%out.print(sx + "," + sy);%>">	
    <input id="gcSer" type="hidden" value="<%=arrCoOrdinates[2]%>">
    <input id="coverage_value" type="hidden" value="<%=sResult2%>">
    <input type='hidden' name='x' value="<%=sx%>"/>
    <input type='hidden' name='y' value="<%=sy%>"/>
    <input type='hidden' name='gc' value="<%=arrCoOrdinates[2]%>"/>
	<input type=hidden name=returnHTMLVal value= "<table  width='100%'><tr><td class='textboxBur' width='40%' >Address</td><td colspan='2' class='textboxGray'>&nbsp; <%=arrCoOrdinates[2]%></td></tr><tr><td class='textboxBur' width='40%' >Coverage Info</td><td colspan='2' class='textboxGray'><%=dCVGStatus%></td></tr> <tr>        	<td colspan='3' ><strong><span style='color:#c60651; font-size:18px'><%=cvgType%> </span>Coverage Info</strong></td></tr><tr><td class='textboxBur'>USB:</td><td align='center' class='textboxGray'><%=susbCoverage%>%</td><td class='textboxGray'><%=((usbCoverage < 25) ? "Not Covered" : "Covered" )%></td></tr><tr><td class='textboxBur'>Indoor: </td><td align='center' class='textboxGray'><%=sindoorCoverage%>%</td><td class='textboxGray'><%=((indoorCoverage < 25) ? "Not Covered" : "Covered" )%></td></tr><tr><td class='textboxBur'>Outdoor: </td><td align='center' class='textboxGray'><%=soutdoorCoverage%>%</td><td class='textboxGray'><%=((outdoorCoverage < 25) ? "Not Covered" : "Covered" )%></td></tr></table>">
    <table>
      <tr><td align='center'>&nbsp;</td></tr> 
      <tr><td align='center'><input class='button' type=button name=btnProceed value="Proceed" onclick='closeme()'></td></tr>
    </table>
    <script language='javascript'>
	function closeme()
	{
		var val = window.opener.getCoverageDataFromChild(); 
		window.close();
	}
    </script>
<!--  Generated output ends here -->
		</DIV>
	</FORM>
</TD></TR></TBODY></TABLE></TD>
<TD background=images/table_right_line.gif>&nbsp;</TD></TR>
<TR>          <TD height=1 vAlign=top background=images/pink_line.gif colSpan=3 align=left>
<IMG src="images/pink_line.gif" wdth=1 height=1></TD></TR>
<TR>
<SCRIPT language="javascript">
	//x="73.03311243";
	//y="33.68435332";
	//initialize(x,y);

</SCRIPT>
</TR></TBODY></TABLE>
</TD></TR></TBODY></TABLE><BR></TD></TR>
  <TR>
    <TD></TD></TR><tr>
		<td>  <table width="999" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
  <tr>
    <td align="center" valign="middle" background="images/nav-rep.png" style=" background-repeat:repeat-x; height:50px;"><a href="#">&copy; wi-tribe Pakistan Limited. All Rights Reserved &reg;</a></td>
  </tr>
  </table>		</td>
	</tr>
</TBODY></TABLE>
<script language=javascript>
	show_map();
</script>
</BODY></HTML>
