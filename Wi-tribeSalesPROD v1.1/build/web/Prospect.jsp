<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<div id="inner-content">
       <form action="./Prospect.do" method="post">
              <input type="hidden" name="page" />
              <input type="hidden" name="flag" />
              <logic:notEmpty name="objProspectList">
                                <div class="scrollable">
                                <table width="679" border="0" cellspacing="0" cellpadding="0"  class="bill-listing">
                                
                                <tr>
                                    <th valign="middle" width="60" align="left">Lead ID</th>
                                    <th valign="middle" width="100" align="left">Name</th>
                                    <th valign="middle" width="100" align="left">Contact</th>
                                    <th valign="middle" width="110" align="left">Reason</th>
                                    <th valign="middle" width="100" align="left">Sub Zone</th>
                                    <th valign="middle" width="100" align="left">Zone(s)</th>
                                    <th valign="middle" width="65" align="left">City(s)</td>
                                </tr>
                                
                                <logic:iterate id="prospectobj" name="objProspectList" type="com.witribe.vo.LeadVO" scope="request">
                                    <tr><td valign="middle" width="60"><a href="./ViewLead.do?leadId=${prospectobj.leadId}"><u>${prospectobj.leadId}</u></a></td>
                                <td valign="middle" width="100">${prospectobj.firstname} ${prospectobj.lastname}</td>
                                <td valign="middle" width="100">${prospectobj.contactnumber}</td>
                                <td valign="middle" width="110">${prospectobj.reason}</td>
                                <td valign="middle" width="65">${prospectobj.subzone}</td>
                                <td valign="middle" width="65">${prospectobj.zone}</td>
                                <td valign="middle" width="65">${prospectobj.city}</td>
                                </tr>
                                </logic:iterate> 
                                </table></div>
                                <table border="0" align="right" width="150">
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
                             </table>
                       
      </logic:notEmpty>
     <logic:empty name="objProspectList">
                                                <tr><td colspan="2"><b>There are no Prospects!</b></td>
                                                </tr>
                                            </logic:empty></div>
         </form>   
             
    
    
   