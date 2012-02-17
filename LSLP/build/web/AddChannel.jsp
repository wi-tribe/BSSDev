    <%@page contentType="text/html" import="com.witribe.constants.WitribeConstants,com.witribe.bo.WitribeBO,com.witribe.sales.vo.SalesPersonnelVO,java.util.ArrayList,java.util.List, com.witribe.sales.bo.WitribeSalesBO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <form action="./AddChannel.do" method="Post">
                <input type="hidden" name="page" />
                <input type="hidden" name="flag" />
               
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                        <tr><td><label>Current Channels</label></td>
                            <logic:notEmpty name="objChannelList">
                            <td><select name="channelId" id="channelId">   
                                    
                                    <logic:iterate id="objchannel" name="objChannelList" type="com.witribe.sales.vo.ChannelVO" scope="request">
                                                                           
                                        <option value="${objchannel.channelId}">${objchannel.channelId}-${objchannel.channelName}</option> 
                                      
                             </logic:iterate>  
                             <option value="-1">Add New</option> 
                        </select></td></tr>
                        </logic:notEmpty>
                       <tr><td><label>New Channel Type</label></td>
                       <td><input type="text" name = "channelName" id="channelName" class=txtxx size="50"/></td></tr>
                        <tr><td></td><TD align=left>
                                     
                                          <INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" id="submitbutton" 
                                      name=input>  </TD></TR>
                                               
            </table></form>
        </div>
    </div>
</div>
                        
                        
   
            


