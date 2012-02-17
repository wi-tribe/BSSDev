    <%@page contentType="text/html" import="com.witribe.constants.WitribeConstants,com.witribe.sales.vo.ChannelVO,com.witribe.sales.vo.SalesPersonnelVO,java.util.ArrayList,java.util.List, com.witribe.sales.bo.WitribeSalesBO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
    function CallModifyAction()
    {
    var res=document.getElementById('salesIdChannel').value;    
     if(res !="")
      {
        var res1= res.split('-');
       //alert(res1[0]+res1[1]);
        var dis;
        if(res1[1] == 1)       
            dis="Direct Sale"
        if(res1[1] == 2)       
            dis="Shop-Sales Executive"
        if(res1[1] == 3)       
            dis="BD-Business development"
        if(res1[1] == 4)       
            dis="Kiosks"
        if(res1[1] == 5)       
            dis="BO"
        if(res1[1] == 6)       
            dis="NBO"
        if(res1[1] == 7)
            dis="CSO"
       document.getElementById('currentchannel').value=dis; 
       document.getElementById('submitbutton').style.display = "";
     //document.forms[1].action="./ModifyChannel.do";
    // document.forms[1].submit();
    }
    else{
        document.getElementById('currentchannel').value="";
        document.getElementById('submitbutton').style.display = "none";
        }
    }
    
</script>

<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <form action="./SubmitChannelType.do" method="Post">
                <input type="hidden" name="page" />
                <input type="hidden" name="flag" />
               
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                        <tr><td><label>CSE</label></td>
                            <logic:notEmpty name="objCSEList">
                            <td><select name="salesIdChannel" id="salesIdChannel" onchange="CallModifyAction();">   
                                    <option></option> 
                                    <logic:iterate id="objchannel" name="objCSEList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                        <c:set var = "channelid" value="${objchannel.salesId}"/>                                      
                                        <option value="${objchannel.salesId}-${objchannel.channeltype}">${objchannel.salesId}-${objchannel.fullname}</option> 
                                      
                             </logic:iterate>         
                        </select></td></tr>
                        </logic:notEmpty>
                        <tr><td><label>Current Channel Type</label></td>
                        <td><input type="text" id="currentchannel" class=txtxx size="25"  readonly disabled /></td></tr>
                       <tr><td><label>New Channel Type</label></td>
                            <td><select name="channeltype" >
                                <%
                                    try
                                    {
                                    List chnList = new ArrayList();
                                    WitribeSalesBO objBO = new WitribeSalesBO();
                                    ChannelVO chnVO = new ChannelVO();
                                    chnList = objBO.getNewChannelList(chnVO);
                                    int chnsize = chnList.size();
                                    %>
                                     <%  for (int i=0;i<chnsize;i++)                                   
                                        {
                                        chnVO = (ChannelVO)chnList.get(i);                                
                                        String chnId = chnVO.getChannelId();   
                                        String name = chnVO.getChannelName();

                                        %> 
                                <option value="<%=chnId%>"><%=name%></option> 
                                <% } }catch(Exception e){}%>
                                    <%--<option value="${WitribeConstants.CSE_DIRECT_SALE}">Direct Sale</option>
                                    <option value="${WitribeConstants.CSE_SHOP_SALES_EXECUTIVE}">Shop-Sales Executive</option>
                                    <option value="${WitribeConstants.CSE_BD}">BD-Business development</option>
                                    <option value="${WitribeConstants.CSE_KIOSKS}">Kiosks</option>
                                    <option value="${WitribeConstants.CSE_BO}">BO</option>
                                    <option value="${WitribeConstants.CSE_NBO}">NBO</option>--%>
                        </select></td></tr>
                        
                        <tr><td></td><TD align=left>
                                     
                                          <INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" id="submitbutton" STYLE="{display:none}"
                                      name=input>  </TD></TR>
                                               
            </table></form>
        </div>
    </div>
</div>
                        
                        
   
            


