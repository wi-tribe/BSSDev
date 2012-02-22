<%@page contentType="text/html" import="com.witribe.constants.WitribeConstants,java.io.*, java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 
response.setHeader( "Cache-Control", "no-store, no-cache"); //HTTP 1.1 
response.setHeader("Pragma","no-cache"); //HTTP 1.0 
response.setDateHeader ("Expires", -1); //prevents caching at the proxy server 

//${pageContext.session.maxInactiveInterval}


%>
<SCRIPT LANGUAGE=javascript type="text/javascript">
    window.history.forward(1);
</SCRIPT>
<meta http-equiv="refresh" content="18000;url=./Logout.do?timeout=true">
<jsp:useBean class="com.witribe.constants.WitribeConstants" id="WitribeConstants" scope="application"/>
<!--<div id="searchform">
<label><a href="#"><img src="images/txt-arabic.gif" alt="Arabic" title="Arabic" onmouseout="this.src='images/txt-arabic.gif'" onmouseover="this.src='images/txt-arabic-on.gif'" border="0" /></a></label>-->
<form method="get" action="#" class="search" id="search" onsubmit="return searchboxValidate();">
<!--<input type="text" value="Enter Keyword..." onfocus="toggleTextIn(this,'Enter Keyword...')" onblur="toggleTextOut(this,'Enter Keyword...')" name="s" class="txt-search" id='txtsearch'/><input type="image" onmouseout="this.src='images/btn-search.gif'" onmouseover="this.src='images/btn-search.gif'" alt="Search" src="images/btn-search.gif" title="Search" class="btn-search"/>				-->
</form>
<!--</div>-->
<div id="login-info">
<strong>Welcome!</strong> ${name}
</div>
<!--<ul id="nav-top">
    <li><a href="#"><img src="images/txt-about-us.gif" alt="About us" title="About us" onmouseout="this.src='images/txt-about-us.gif'" onmouseover="this.src='images/txt-about-us-on.gif'" border="0" /></a></li>
    <li><a href="#"><img src="images/txt-products-services.gif" alt="Products &amp; Services" title="Products &amp; Services" onmouseout="this.src='images/txt-products-services.gif'" onmouseover="this.src='images/txt-products-services-on.gif'" border="0" /></a></li>
    <li><a href="#"><img src="images/txt-our-coverage.gif" alt="Our Coverage" title="Our Coverage" onmouseout="this.src='images/txt-our-coverage.gif'" onmouseover="this.src='images/txt-our-coverage-on.gif'" border="0" /></a></li>
    <li><a href="#"><img src="images/txt-media-center.gif" alt="Media Center" title="Media Center" onmouseout="this.src='images/txt-media-center.gif'" onmouseover="this.src='images/txt-media-center-on.gif'" border="0" /></a></li>
    <li><a href="#"><img src="images/txt-careers-support.gif" alt="Careers Support" title="Careers Support" onmouseout="this.src='images/txt-careers-support.gif'" onmouseover="this.src='images/txt-careers-support-on.gif'" border="0" /></a></li>
</ul>-->
<%
String imgpath =  "/images/banner/";
String sTempLocationPath = application.getRealPath( imgpath);
//out.print(sTempLocationPath);
File oFileList = new File(sTempLocationPath);
File [] oFileArray = oFileList.listFiles();
int imgindex = -1;
/*for (int i = 0;i < oFileArray.length; i++) {
	File oFile = oFileArray[i];
	out.print("<br>" + oFile.getName());
}
*/
//out.print("<br>file length:" + oFileArray.length);

File oFile = null;
while (imgindex < 0 || imgindex >= oFileArray.length) {
	imgindex = (int) (Math.random() * 9) ;
	if(imgindex < oFileArray.length) {
	//out.print("<BR>imgindex : " + imgindex);
	oFile = oFileArray[imgindex]; 
	if(oFile.getName().trim().equals("Thumbs.db")) {
		imgindex = -1;
	}
	}
	else { 
		imgindex = -1;
	}
}
 
%>
<div class="my-wi-tribe">
<a href="#"><img src="images/txt-my-wi-tribe.gif" alt="my wi-tribe" title="my wi-tribe" onmouseout="this.src='images/txt-my-wi-tribe.gif'" onmouseover="this.src='images/txt-my-wi-tribe-on.gif'" border="0" /></a>
</div>
<div id="top-banner">
<a href="#"><img src="images/banner/<%=oFile.getName()%>" alt="Advertisment" title="Advertisment" border="0" /></a>
</div>
