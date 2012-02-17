<%@page contentType="text/html" import="com.witribe.vo.LeadAccntVO,com.witribe.bo.WitribeBO,com.witribe.constants.WitribeConstants,java.util.ArrayList,java.util.List"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
     function trim(str) {
        return str.replace(/^\s+|\s+$/g,"");
    }
    function SearchAcc(){
     document.forms[1].searchByValue.value = trim(document.forms[1].searchByValue.value);
    if(document.forms[1].searchByValue.value == ''){
        $('p.validationmsg').hide();
        showMessage(document.forms[1].searchByValue, 'Please Enter data before search');
        return false;
    } if(document.forms[1].searchby.value == 'ACCOUNT_NO'){
        $('p.validationmsg').hide();
        //var val = document.forms[1].search.value;
        if(!IsNumber(document.forms[1].searchByValue)){
        showMessage(document.forms[1].searchByValue, 'Please Enter Valid Account number');
        return false;
        }
    } if(document.forms[1].searchby.value == 'CNIC_ID'){
        $('p.validationmsg').hide();
        //var val = document.forms[1].search.value;
        if(!IsNumber(document.forms[1].searchByValue)){
        showMessage(document.forms[1].searchByValue, 'Please Enter Valid CNIC ID number');
        return false;
        }
    } else {
           document.forms[1].action="./ViewAccountDetails.do";
           document.forms[1].submit();
            return true;
         }
        
    }
</script>
<logic:notEmpty name="objleads" >
    <p><b>Account Details</b></p>
</logic:notEmpty>
<logic:empty name="objleads">
    <div id="inner-content">
        <form method="Post">
            <% if(request.getAttribute(WitribeConstants.ERR_VAR) != null) { %>
       <P><font color="red"><b><%=request.getAttribute(WitribeConstants.ERR_VAR)%></b></font></P>
        <%}%>
            <!--<input type="hidden" name="page" />
    <input type="hidden" name="flag" />-->
            <!--<label>Please enter Name or LeadId or CNIC Number or Email-Id or&nbsp;Contact Number or Subzone or Zone or City&nbsp;or&nbsp;Province or Lead Status to Search.</label><br><br>-->
            <table border="0" cellspacing="0" cellpadding="0" class="info-listing">
                
                <tr>
                    <td><label>Search By</label>&nbsp;
                        <select name="searchby">
                            
                            <c:choose>
                                <c:when test="${searchby == 'CNIC_ID'}">
                                    <option value="CNIC_ID" selected>CNIC ID</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="CNIC_ID">CNIC ID</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${searchby == 'ACCOUNT_NO'}">
                                    <option value="ACCOUNT_NO" selected>Account No</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="ACCOUNT_NO">Account No</option>
                                </c:otherwise>
                            </c:choose>
                            
                            
                    </select></td>
                    
                </tr>
                
                
                <tr><td><input type="text" name="searchByValue" width="120" value="${searchByValue}"/><p class="validationmsg"></p></td> 
                <td><input type="submit" name="search" value="Search"  onclick="return SearchAcc();"/></td></tr>   
    </table></form></div>
</logic:empty>
<logic:notEmpty name="objleads" >
   
<div id="inner-content">
  <table width="520" border="0" cellspacing="0" cellpadding="0" class="profile-listing">
                        <bean:define id="objleads"  name="objleads" type="com.witribe.vo.LeadAccntVO" />
                            <tr><td valign="top" width="235" class="label">Billed Amount</td>
                                <td><c:choose><c:when test="${objleads.billAmount!= null}">
                                        <bean:write name="objleads" property="billAmount"/>
                                    </c:when>
                                    <c:otherwise>
                                        0.0
                                    </c:otherwise>
                                </c:choose>PKR
                        </td></tr>
                        <tr><td valign="top" class="label">Unbilled Amount</label></td>
                             <td><c:choose><c:when test="${objleads.unBillAmount!= null}">
                                        <bean:write name="objleads" property="unBillAmount"/>
                                    </c:when>
                                    <c:otherwise>
                                        0.0
                                    </c:otherwise>
                                </c:choose>PKR
                        </td></tr>
                             <tr><td valign="top" class="label">Overdue</label></td>
                            <td><c:choose><c:when test="${objleads.overDueAmount!= null}">
                                        <bean:write name="objleads" property="overDueAmount"/>
                                    </c:when>
                                    <c:otherwise>
                                        0.0
                                    </c:otherwise>
                                </c:choose>PKR
                        </td></tr>
                          <tr><td valign="top" class="label">Last amount paid</label></td>
                            <td> <c:choose><c:when test="${objleads.lastPaidAmt!= null}">
                                        <bean:write name="objleads" property="lastPaidAmt"/>
                                    </c:when>
                                    <c:otherwise>
                                        0.0
                                    </c:otherwise>
                                </c:choose>PKR
                        </td></tr>
                        <tr><td valign="top" class="label">Total Balance</label></td>
                            <td> <c:choose><c:when test="${objleads.totalBalance!= null}">
                                        <bean:write name="objleads" property="totalBalance"/>
                                    </c:when>
                                    <c:otherwise>
                                        0.0
                                    </c:otherwise>
                                </c:choose>PKR
                        </td></tr>
                        <tr><td valign="top" class="label">Volume consumed in MB</label></td>
                            <td> <c:choose><c:when test="${objleads.currentBal!= null}">
                                        <bean:write name="objleads" property="currentBal"/>
                                    </c:when>
                                    <c:otherwise>
                                        0.0
                                    </c:otherwise>
                                </c:choose>PKR
                        </td></tr>
                            <tr><td valign="top" class="label">Volume consumed in % </label></td>
                           <td> <c:choose><c:when test="${objleads.volumeConsumedInPercent!= null}">
                                        <bean:write name="objleads" property="volumeConsumedInPercent"/>
                                    </c:when>
                                    <c:otherwise>
                                        0.0
                                    </c:otherwise>
                                </c:choose>PKR
                        </td></tr>        
                   </table></div>
     </logic:notEmpty>         