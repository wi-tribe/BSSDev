<%@page contentType="text/html" import="com.witribe.vo.LeadVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script>
    function SearchLead()
    {
        document.forms[1].action="./LeadAccount.do";
           document.forms[1].submit();
            return true;
    }
    function checkDateRange(){
  
        var D=new Date();
        var Y=D.getFullYear();
        var M=D.getMonth()+1;
        var dt=D.getDate();
        var curDate=dt+"/"+M+"/"+Y; 
        //var curDate=dt+"/"+M+"/"+Y; 
        if(document.getElementById('date1').value == '' || document.getElementById('date2').value == ''){
        alert("Please enter both dates to proceed search");
        return false;
        }
        var fromDate = document.getElementById('date1').value;
        var toDate = document.getElementById('date2').value;
        var dt1   = parseInt(fromDate.substring(0,2),10); 
        var mon1  = parseInt(fromDate.substring(3,5),10);
        var yr1   = parseInt(fromDate.substring(6,10),10); 
        var dt2   = parseInt(toDate.substring(0,2),10); 
        var mon2  = parseInt(toDate.substring(3,5),10); 
        var yr2   = parseInt(toDate.substring(6,10),10); 
        var d1 = new Date(yr1, mon1-1, dt1); 
        var d2 = new Date(yr2, mon2-1, dt2); 
        var d3 = new Date(Y, M-1, dt); 
        
        if(d2 < d1)
           {
            $('p.validationmsg').hide();
            showMessage(document.forms[1].fromDate, 'From Date can not be greater than To Date');
            return false;
             
           }
       if(d2 > d3) {
            $('p.validationmsg').hide();
            showMessage(document.forms[1].toDate, 'TO Date cannot be greater than Current Date');
            return false;
        }
          
        document.forms[1].action="./LeadAccount.do";
        document.forms[1].submit();
        return true;
        
            
    }  
   

</script>
<script type="text/javascript" charset="utf-8">
			$(function()
				{
                                        Date.format = 'dd/mm/yyyy';
                                        var D=new Date(),Y=D.getFullYear(),M=D.getMonth()+1;
                                        var dt=D.getDate();
                                        var curDate=dt+"/"+M+"/"+Y;
					$('.date-pick').datePicker({startDate:'01/01/1900'});
                                                                           
                                        
			});
		</script>
<div id="inner-content">
    <form method="Post" onsubmit="return checkDateRange();">
        <table border="0" cellspacing="0" cellpadding="0" class="info-listing">
        <tr id="bydate">
            <td><label for="date1">From Date</label><span class="mandatory">*</span></td>
            <td><input type="text" class="txtxx date-pick dp-applied" name="fromDate" id="date1" value="${fromDate}"  readonly/>&nbsp;&nbsp;<p class="validationmsg"></p></td>
            <td><label for="date1">To Date</label><span class="mandatory">*</span></td>
            <td><input type="text" class="txtxx date-pick dp-applied" name="toDate" id="date2" value="${toDate}"  readonly/>&nbsp;&nbsp;<p class="validationmsg"></p></td>
            <!--<td><input type="submit" name="search" value="Search"  onclick="return SearchLead();"/></td></tr>   -->
        </tr>
        <tr><td><input type="submit" name="search" value="Search" /></td></tr>   
    </table>
    <!--<input type="hidden" name="page" />
    <input type="hidden" name="flag" />-->
    <logic:notEmpty name="objLeadList" >
    <div class="scrollable">
       <table width="679" border="1" cellspacing="0" cellpadding="0" class="bill-listing">	
        <tr>
            <th valign="middle" width="131" align="left">Lead ID</td>
            <th valign="middle" width="125" align="left">Name</td>
            <th valign="middle" width="162" align="left">Contact</td>
            <th valign="middle" width="192" align="left">Lead Status</td>
            <th valign="middle" width="100" align="left">Assigned To CSE-Name</th>
            <th valign="middle" width="100" align="left">Assigned To TL/RSM-Name</th>
            <th valign="middle" width="100" align="left">Sub Zone</th>
            <th valign="middle" width="100" align="left">Zone(S)</th>
            <th valign="middle" width="65" align="left">City(s)</th>
            <th valign="middle" width="65" align="left">Lead Generated Date</th>
            <th valign="middle" width="65" align="left">Account Number</th>
            <th valign="middle" width="65" align="left">Account Date</th>
            <th valign="middle" width="65" align="left">Created By</th>
            <th valign="middle" width="65" align="left">Channel Type</th>
            
        </tr>

        <logic:iterate id="objLead" name="objLeadList" type="com.witribe.vo.LeadVO" scope="request">
            <tr><td valign="middle" width="131"><a href="./ViewLead.do?leadId=<bean:write name='objLead' property='leadId' />"><u><bean:write name="objLead" property="leadId" /></u></td>
            <td valign="middle" width="125"><bean:write name="objLead" property="firstname"/> <bean:write name="objLead" property="lastname"/></td>
            <td valign="middle" width="162"><bean:write name="objLead" property="contactnumber"/></td>
            <td valign="middle" width="192"><bean:write name="objLead" property="leadStatus"/></td>
            <td valign="middle" width="192">
            <c:choose><c:when test="${objLead.assignedCSE != null}">
                                    <bean:write name="objLead" property="assignedCSE"/>
                                    <c:if test="${objLead.csename != null}">
                                        -<bean:write name="objLead" property="csename"/>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    --
                                </c:otherwise>
                        </c:choose></td>
                        <td valign="middle" width="192">
                            <c:choose><c:when test="${objLead.assignedTL != null}">
                                    <bean:write name="objLead" property="assignedTL"/>
                                    <c:if test="${objLead.tlname != null}">
                                        -<bean:write name="objLead" property="tlname"/>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    --
                                </c:otherwise>
                        </c:choose></td>
            <td valign="middle" width="192"><bean:write name="objLead" property="subzone"/></td>
            <td valign="middle" width="192"><bean:write name="objLead" property="zone"/></td>
            <td valign="middle" width="65"><bean:write name="objLead" property="city"/></td>
            <td valign="middle" width="192"><bean:write name="objLead" property="createDate"/></td>
            <td valign="middle" width="192">
            
            <c:choose><c:when test="${objLead.accountNo != null}">
                  <bean:write name="objLead" property="accountNo"/>
                  </c:when>
                 <c:otherwise>
                     --
                 </c:otherwise>
                 </c:choose></td>
            <td valign="middle" width="65">
               <c:choose><c:when test="${objLead.acctDate != null}">
                   <bean:write name="objLead" property="acctDate"/>
                  </c:when>
                 <c:otherwise>
                     --
                 </c:otherwise>
                 </c:choose>
            </td>
            <td valign="middle" width="65">                        
                        <c:choose><c:when test="${objLead.salesId != null}">
                                    <bean:write name="objLead" property="salesId"/>
                                    <c:if test="${objLead.fullname != null}">
                                        -<bean:write name="objLead" property="fullname"/>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    --
                                </c:otherwise>
                        </c:choose></td>
                        <td valign="middle" width="65">
                        <c:choose><c:when test="${objLead.channeltype != null}">
                                    <bean:write name="objLead" property="channeltype"/>
                                </c:when>
                                <c:otherwise>
                                    --
                                </c:otherwise>
                        </c:choose></td>
                        
        </tr>
        </logic:iterate> 
    </table>
    </div>
    <%--<table border="0" align="right" width="150">
        <tr>
            <%String pageCount =(String) request.getAttribute("page");
            String nextEnable = (String) request.getAttribute("next");%>
            <td>
                <%if(!"0".equals(pageCount)) {%>
                <a href="javascript:prevPageClick(${page})">Previous</a>&nbsp;
                <%} 
                int pageNo = Integer.parseInt(pageCount)+1;%>
                <%=pageNo%>
                <% if(("true".equalsIgnoreCase(nextEnable))) {%>
                <a href="javascript:nextPageClick(${page})">Next</a>
                <%}%>
            </td>
        </tr>
    </table>--%>
    </logic:notEmpty >
    <logic:empty name="objLeadList">
        <tr>
            <td colspan="4"><b>There are no Leads for the selected search !</b></td>
        </tr>
    </logic:empty>
   </form> </div>




