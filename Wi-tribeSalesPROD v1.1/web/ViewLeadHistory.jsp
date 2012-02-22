<%@page contentType="text/html" import="com.witribe.vo.LeadHistoryVO,com.witribe.constants.WitribeConstants"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>

<div id="inner-content">
    <a href="./ViewLead.do?leadId=<%=request.getParameter("leadId")%>"><u>Back to Show Lead</u></a><br></br>
    <logic:notEmpty name="objLeadHistory" >
    <div class="scrollable">
        
          <table width="679" border="1" cellspacing="0" cellpadding="0" class="bill-listing">	
   <logic:iterate id="objHistory" name="objLeadHistory" type="com.witribe.vo.LeadHistoryVO" scope="request">
            <table border="1" cellspacing="0" cellpadding="0" class="bill-listing">
                
                <tr>
                    <th valign="middle" width="131" align="left">Sales Id</th>
                    <td valign="middle" width="131">${objHistory.salesPersonID}</td>
                    <th valign="middle" width="125" align="left">Date</th>
                    <td valign="middle" width="131">${objHistory.modifiedDate}</td>
                    <th valign="middle" width="65" align="left">Job Status</th>
                    <td valign="middle" width="131">${objHistory.status}</td>
                    <th valign="middle" width="65" align="left">Lead State</th>
                    <td valign="middle" width="131">${objHistory.transitionState}</td> 
                </tr><tr >
                    <th valign="middle" width="65" align="left">COMMENT</th>
                    <td valign="middle" width="100" colspan=7 >${objHistory.comments}</td>
                    
                </tr>
                
            </table>
            <tr><td>&nbsp;</td></tr>
        </logic:iterate>
    </table>
    </div>
    
    </logic:notEmpty >
   
    <logic:empty name="objLeadHistory">
        <tr>
            <td colspan="4">There is no status changed history for this lead</td>
        </tr>
    </logic:empty>
   </div>






