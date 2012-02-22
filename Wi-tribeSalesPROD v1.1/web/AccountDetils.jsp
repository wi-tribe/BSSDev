<%@page contentType="text/html" import="java.util.Properties"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <%Properties props = new Properties();
            props.load(this.getClass().getClassLoader().getResourceAsStream("Lslp.properties"));
            String scUrl = props.getProperty("sc.url");
           %>
          <!-- <form  name="leadform" action="<%=scUrl%>" method="Post"> -->
          <form  name="leadform" action="./LeadAccountCreationAction.do" method="Post">
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                        <bean:define id="objLeads"  name="objLead" type="com.witribe.vo.LeadVO" scope="request"/>
                            <tr><td><label>Account Type</label><span class="mandatory">*</span></td>
                            <td><select name="business_type">
                                         <option value=1>Individual</option>
                                                            <!--<option value=2>Corporate</option>-->
                                                            <option value=3>Affinity Child</option>
							    <option value=4>Affinity</option>
                                                            <option value=5>FUT</option>
                                                            <option value=8>Employee</option>
                                                            <option value=9>Test</option>
                            </select></td> </tr>
                            <tr><td><label>Service Type</label><span class="mandatory">*</span></td>
                            <td><select name="accountservicetype1">
                                        <option value=1>Pre-Paid</option>
                                        <option value=2>Post paid</option>

                            </select></td></tr>
                  
                       <tr><td>&nbsp;</td>
                           <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD></TR>
               
                         <input type="hidden" name="salesweb" value="SALES" />
                         <input type="hidden" name="salesId" value="${SALES_ID}" />
                        <input type="hidden" name="leadId" value="${objLeads.leadId}" />
                         <input type="hidden" name="salute" value="${objLeads.salutation}" />
                        <input type="hidden" name="firstname" value="${objLeads.firstname}" />
                        <input type="hidden" name="lastname" value="${objLeads.lastname}"/>
                        <input type="hidden" name="identity" value="${objLeads.CNICid}"/>
                        <input type="hidden" name="title" value="${objLeads.jobtitle}" />
                        <input type="hidden" name="email" value="${objLeads.email}" />
                        <input type="hidden" name="company" value="${objLeads.occupation}" />
                        <input type="hidden" name="1" value="${objLeads.contactnumber}"/>
                        <input type="hidden" name="2" value="" />
                        <input type="hidden" name="3" value="" />
                        <input type="hidden" name="5" value="" />
                        <input type="hidden" name="city" value="${objLeads.city}"/>
                        <input type="hidden" name="state" value="${objLeads.province}" />
                        <input type="hidden" name="zip" value="${objLeads.zip}" />
                        <input type="hidden" name="sHNo" value="${objLeads.plot}" />
                        <input type="hidden" name="sStreet" value="${objLeads.street}" />
                        <input type="hidden" name="sRoad" value="${objLeads.subzone}" />
                        <input type="hidden" name="sSector" value="${objLeads.zone}" />
                        <input type="hidden" name="country" value="${objLeads.country}" />
                        <input type="hidden" name="sCoverage" value="${sCoverage}" class="txtxx" maxlength="15" />
                        <input type="hidden" name="sla" value="${sla}" class="txtxx" maxlength="15" />
                        <input type="hidden" name="sLongitude" value="${sLongitude}" class="txtxx" maxlength="15" />
                        <input type="hidden" name="sLatitude" value="${sLatitude}" class="txtxx" maxlength="15" />
                        <input type="hidden" name="referredBy" value="<%=request.getParameter("referredBy")%>" class="txtxx" />
        </table></form>
        </div>
        </div>
        </div>
   
