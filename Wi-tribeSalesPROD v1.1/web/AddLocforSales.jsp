<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.sales.bo.WitribeSalesBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">    
    function dispsales(sales){
    document.getElementById('location').options.length = 0;
    if(sales == 2){
            document.getElementById('city').value = "";
            document.getElementById('zone').value = "";            
            document.getElementById('subzone').value = "";
        document.getElementById('trcity').style.display = "none";
        document.getElementById('trzone').style.display = "none";
        document.getElementById('rsm').style.display = "";
        if(document.getElementById('tl'))
            document.getElementById('tl').style.display = "none";
        if(document.getElementById('cse'))
            document.getElementById('cse').style.display = "none";
        if(document.getElementById('empty'))
            document.getElementById('empty').style.display = "none";
    } else if(sales == 3) {
        document.getElementById('city').value = "";
            document.getElementById('zone').value = "";            
            document.getElementById('subzone').value = "";
        document.getElementById('trcity').style.display = "";
        document.getElementById('trzone').style.display = "none";
        document.getElementById('tl').style.display = "";
        if(document.getElementById('cse'))
            document.getElementById('cse').style.display = "none";
        if(document.getElementById('rsm'))
            document.getElementById('rsm').style.display = "none";
        if(document.getElementById('empty'))
            document.getElementById('empty').style.display = "none";
    } else if (sales == 4) {
    document.getElementById('city').value = "";
            document.getElementById('zone').value = "";            
            document.getElementById('subzone').value = "";
        document.getElementById('trcity').style.display = "";
        document.getElementById('trzone').style.display = "";
        document.getElementById('cse').style.display = "";
        if(document.getElementById('tl'))
            document.getElementById('tl').style.display = "none";
        if(document.getElementById('rsm'))
            document.getElementById('rsm').style.display = "none";
        if(document.getElementById('empty'))
            document.getElementById('empty').style.display = "none";
    } else {
        document.getElementById('city').value = "";
            document.getElementById('zone').value = "";            
            document.getElementById('subzone').value = "";
        document.getElementById('trcity').style.display = "none";
        document.getElementById('trzone').style.display = "none";
        if(document.getElementById('cse'))
            document.getElementById('cse').style.display = "none";
       if(document.getElementById('tl'))
            document.getElementById('tl').style.display = "none";
        if(document.getElementById('rsm'))
            document.getElementById('rsm').style.display = "none";
        if(document.getElementById('location'))
            document.getElementById('location').length = 1;
        if(document.getElementById('empty'))
            document.getElementById('empty').style.display = "";
    }
    }
    function callajaxLocation(salesid,address){       
        var addr = address.split('*'); 
        var city = addr[0];
        var zone = addr[1];
        var subzone = addr[2];
        document.getElementById('city').value = addr[0];
        document.getElementById('zone').value = addr[1];
        document.getElementById('subzone').value = addr[2];
       
       if(salesid == "")
       {
            document.getElementById('city').value = "";
            document.getElementById('zone').value = "";
            document.getElementById('location').value = "";
       }
        /*var type1 = document.getElementById('salestype').value;        
        var zone = document.getElementById('zone').value;
        var subzone = document.getElementById('subzone').value;
        var city = document.getElementById('city').value;
        alert(type1+zone+subzone+city);*/       
        document.getElementById('location').options.length = 0;
       // document.getElementById('location').options.length = 1;
       callAjaxForLocation(salesid,city, zone,subzone);
    }
     function callAjaxForLocation(salesid,city,zone,subzone) {
    //alert(salesid);
    xmlHttp = getHTTPObject();
    xmlHttp.onreadystatechange = processResponseforLocation;        
    var type = document.getElementById('salestype').value;
    /*var zone = document.getElementById('zone').value;
    var subzone = document.getElementById('subzone').value;
    var city = document.getElementById('city').value;*/
    //alert(zone+city+subzone);        
    var url ="AjaxAction.do?fun=getUnAssignedLocationList&type="+type+"&zone="+zone+"&subzone="+subzone+"&city="+city+"&salesid="+salesid;
    //alert("tom1");
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);     
 } 

function processResponseforLocation() {
    if(xmlHttp.readyState == 4)
    {
        var results = xmlHttp.responseText; 
        var theOptionschild = "";      
	var childid ="";
        childid =document.getElementById('location');
        //childid.length = 0;
	if(results)
	{
		theOptionschild = results.split(',');           
		if(theOptionschild.length > 0)
		{
                        
			for(i = 0; i < theOptionschild.length; i++) {           
			   var nOption = document.createElement('option'); 
			   var isText = document.createTextNode(theOptionschild[i]);
			   nOption.setAttribute('value',theOptionschild[i]);
			   nOption.appendChild(isText); 
			   childid.appendChild(nOption);    
			 }
		}        
	}
           
       
   }
}
function checkSelected(){
       var selectedIndex = document.getElementById('location').selectedIndex;
       if(selectedIndex == -1)
          {
          alert("select atleast one location to proceed");
          return false;
          } else {
          return true;
          }
      }
   
</script>

        <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>

<P><font color="red"><b>${errorString}</b></font></P>
        <div id="inner-content">
            <div class="shadow-box">
                <div class="shadow-box-inner">
                  <form action="./AddRsmToCity.do" method="Post" name="addform" onsubmit="return checkSelected();">
                        <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">                            
                            <tr><td><label>Select Type</label><span class="mandatory">*</span></td>
                                <td><select name="salestype" id="salestype" onchange="dispsales(this.options[this.selectedIndex].value);">                                 
                                            <option value=""></option>           
                                           <c:if test="${role == WitribeConstants.TYPE_ADMIN}">
                                            <option value="2">RSM</option>
                                            <option value="3">TL</option>
                                            <option value="4">CSE</option>
                                            </c:if>
                                             <c:if test="${role == WitribeConstants.TYPE_NSM}">
                                            <option value="2">RSM</option>
                                            <option value="3">TL</option>
                                            <option value="4">CSE</option>
                                            </c:if>                                             
                                             <c:if test="${role == WitribeConstants.TYPE_RSM}">                                            
                                            <option value="3">TL</option>
                                            <option value="4">CSE</option>
                                            </c:if>
                                             <c:if test="${role == WitribeConstants.TYPE_TL}">                                                                                        
                                            <option value="4">CSE</option>
                                            </c:if>
                                 
                            </select></td></tr>
                            <tr id="empty" style="{display:none}" ><td><label>Select Sales Person</label><span class="mandatory">*</span></td> 
                                <td><select name="salesId" >
                                        <option value=""></option>                                 
                                    </select></td></tr>
                            <logic:notEmpty name="objCseList">
                                <tr id="cse" style="display:none"><td><label>Select Sales Person</label><span class="mandatory">*</span></td> 
                                <!--<td><select name="salesIdCSE" onchange="document.addform.city.value = this.options[this.selectedIndex].city;document.addform.zone.value = this.options[this.selectedIndex].zone;document.addform.subzone.value = this.options[this.selectedIndex].subzone;callajaxLocation(this.options[this.selectedIndex].value);">-->
                                <td><select name="salesIdCSE" onchange="document.addform.city.value = this.options[this.selectedIndex].title;callajaxLocation(this.options[this.selectedIndex].value,this.options[this.selectedIndex].title);">
                                 <option ></option>   
                                  <logic:iterate id="objCse" name="objCseList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                     <option title="${objCse.city}*${objCse.zone}*${objCse.subzone}" value="${objCse.salesId}">${objCse.salesId}-${objCse.fullname}</option>
                                 </logic:iterate>
                            </select></td></tr>
                            </logic:notEmpty>
                            <logic:empty name="objCseList">
                                <tr id="cse" style="display:none"><td colspan="2">No CSEs are Available to Assign Subzone</td></tr>
                            </logic:empty>
                            <logic:notEmpty name="objTlList">
                                 <tr id="tl" style="display:none"><td><label>Select Sales Person</label><span class="mandatory">*</span></td>
                                <td><select name="salesIdTL" onchange="callajaxLocation(this.options[this.selectedIndex].value,this.options[this.selectedIndex].title);">
                                  <option ></option>
                                    <logic:iterate id="objTl" name="objTlList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                       <option title="${objCse.city}*${objCse.zone}*${objCse.subzone}" value="${objTl.salesId}">${objTl.salesId}-${objTl.fullname}</option>
                                 </logic:iterate>
                            </select></td></tr>
                            </logic:notEmpty>
                            <logic:empty name="objTlList">
                                <tr id="tl" style="{display:none}"><td colspan="2">No Tls are Available to Assign Zone</td></tr>
                            </logic:empty>
                            <logic:notEmpty name="objRsmList">
                            
                            <tr  id="rsm" style="{display:none}"><td><label>Select Sales Person</label><span class="mandatory">*</span></td>
                                <td><select name="salesIdRSM" onchange="callajaxLocation(this.options[this.selectedIndex].value,this.options[this.selectedIndex].title);">
                                  <option ></option>
                                    <logic:iterate id="objRsm" name="objRsmList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                                       
                                            <option title="${objCse.city}*${objCse.zone}*${objCse.subzone}" value="${objRsm.salesId}">${objRsm.salesId}-${objRsm.fullname}</option>
                                 </logic:iterate>
                            </select></td></tr>
                            <tr id="trcity" style="{display:none}"><td><label>City</label></td><td><input name="city" id="city" class=txtxx value="" readonly  disabled /></td> </tr> 
                            <tr id="trzone" style="{display:none}"><td><label>Zone</label></td><td><input name="zone" id="zone"  class=txtxx readonly  disabled  /></td> </tr> 
                           <!-- <tr id="trsubzone" style="{display:none}"><td><label>SubZone</label></td>--><input type="hidden" name="subzone" id="subzone"  />
                            </logic:notEmpty>
                            <logic:empty name="objRsmList">
                                <tr id="rsm" style="display:none"><td colspan="2">No RSMs are Available to Assign City</td></tr>
                            </logic:empty>
                          <tr><td><label>Select Location</label><span class="mandatory">*</span></td>
                                <td><select name="location" id="location" multiple="true" >                                        
                               
                            </select></td></tr>
                            
                            
                        <TR>
                            <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD></TR>
                                       
                                                                                
                   </table>
                    </form>
                </div>
            </div>
        </div>
  
 

