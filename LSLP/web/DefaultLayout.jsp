<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>

<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <TITLE><tiles:getAsString name="title" ignore="true"/></TITLE>
<tiles:insert attribute="clientcode"/>
</head>
<body>
<div id="outer-container">
<div id="container">
<div id="header">
<h1 id="logo"><a href="#" title="Wi-Tribe">Wi-Tribe<img src="images/logo-wi-tribe.gif" title="Wi-Tribe" alt="Wi-Tribe" border="0" /></a></h1>
<tiles:insert attribute="header"/>
 </div>
        <div id="wrap">
            <div id="wrap-inner">
                    <tiles:insert attribute="leftmenu"/>
                
                    <!-- bdy -->
                    <div id="content">
                    <h2 ><tiles:getAsString name="title" ignore="true"/></h2>
                    <div id="inner-content">
                        <div id="inner-content">
                   <tiles:insert attribute="body"/>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<tiles:insert attribute="footer"/>
</div>
</body>
</html>
