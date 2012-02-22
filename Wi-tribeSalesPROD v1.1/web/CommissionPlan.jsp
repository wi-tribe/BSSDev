<%@ page contentType="text/html" import="java.sql.*,com.witribe.bo.WitribeBO,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO,com.witribe.constants.WitribeConstants,com.witribe.commission.vo.*, com.witribe.commission.DAO.*"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
        String exception = "";
        String PlanId= "";
        String Channel_Id="";
        String plan_type="";
        String City_Zone_Id="";
        String Product_Id="";
        String rule_type="";
        String valid_from="";
        String valid_to="";
        String plan_name="";
        String planstatus="";
        
       if(request.getQueryString() != null)
       {    
        PlanId=request.getParameter("PlanId");
           try {
                CommissionPlanDAO cplanDAO = new CommissionPlanDAO();
                 List Plan = cplanDAO.getPlan(PlanId); 

               if(Plan.size() > 0) {
                         CommissionPlanVO plist = (CommissionPlanVO)Plan.get(0);
                         Channel_Id=plist.getChannelId();
                         plan_type=plist.getPlanType();
                         City_Zone_Id=plist.getZone();
                         Product_Id=plist.getProductId();
                         rule_type=plist.getCommissionType();
                         valid_from=plist.getValidFrom();
                         valid_to=plist.getValidTo();
                         plan_name=plist.getPlanName();
                         planstatus=plist.getStatus();
                         //out.print(plan_name);
  
               }     
            } catch(SQLException ex) {
                exception = "SQL Exception";
                out.print(exception+":"+ex);
            } catch(Exception ex) {
                exception = "Other Than SQL Exception";
                out.print(exception+":"+ex);
            }
        }
    %>
    <script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
    <script language="javascript" type="text/javascript">
       var product_id='';
    function bodyload(){
            /*document.getElementById('typerouter').style.display = "none";
            document.getElementById('mrouter').style.display = "none";*/
            var planid="<%=(String)request.getParameter("PlanId")%>";
            var channel_id="<%=Channel_Id%>";
            product_id="<%=Product_Id%>"
            var city_id="<%=City_Zone_Id%>";
            var plan_type="<%=plan_type%>";
            var rule_type="<%=rule_type%>";
            var valid_from="<%=valid_from%>";
            var valid_to="<%=valid_to%>";            
            var planname="<%=plan_name%>";
            var planstatus="<%=planstatus%>";
            var salestype = document.getElementById('salesType').options[document.getElementById('salesType').selectedIndex].value;
            if(salestype == "1"){
            document.getElementById('channelid').style.display = "";
            }
            var city = document.getElementById('city').options[document.getElementById('city').selectedIndex].value;

 //         callAJAXZoneList(city);            
            callAJAXProductList(city, product_id);
            changeForm();
            
            if (planid != "null")
            {
                setSelected(document.getElementById('city') , city_id);
                setSelected(document.getElementById('comType') , rule_type);
            //  document.getElementById('comType').disabled=true;
                setSelected(document.getElementById('plantype') , plan_type); 
                document.getElementById('plantype').disabled=true;
                document.getElementById('planname').value=planname;
                document.getElementById('planname').disabled=true;
                document.getElementById('valid_from').value=valid_from;
                document.getElementById('valid_to').value=valid_to;
                if(parseInt(plan_type)==2)
                    document.getElementById('cityrow').style.display = "none";
                if(parseInt(plan_type)==1)
                    document.getElementById('productdispid').style.display = "none";
                else
                    document.getElementById('productdispid').style.display = "";
                if((parseInt(channel_id)>=1 && parseInt(channel_id)<=6) || parseInt(channel_id)>9 ) 
                {
                    setSelected(document.getElementById('salesType'), 1);
                    document.getElementById('salestype').value=1;
                    document.getElementById('salesType').disabled=true;
                    setSelected(document.getElementById('ctype') , channel_id);   
                    document.getElementById('channelid').style.display = "";
                }   
                else
                {
                    setSelected(document.getElementById('salesType'), channel_id);
                    document.getElementById('salestype').value=channel_id;
                    document.getElementById('salesType').disabled=true;
                    document.getElementById('channelid').style.display = "none";
                }
              if(planstatus==2)
                  document.getElementById('planstatus').checked=false;
            }

            }
            
            function setSelected(sel, valu) {
                var ctlname=sel.name;
                for(i=0;i<sel.options.length;i++) {
                    if(sel.options[i].value == valu) {
                            sel.options[i].selected = true;
                         }
                }
            }
    function changeChannel(val){
       if(val != "1"){
           document.getElementById('channelid').style.display = "none";
           } else {
           document.getElementById('channelid').style.display = "";
           }
           }
    function getValues(city)
            {
  //          callAJAXZoneList(city);

              
              callAJAXProductList(city,product_id);
              
            }
    function checkRuleType(){
            var ruletype = document.getElementById('comType').options[document.getElementById('comType').selectedIndex].value;
            if(ruletype == 1){
            document.getElementById('commissionType').value = "Basic";
            } else if(ruletype == 2){
            document.getElementById('commissionType').value = "Promotional";
            } else if(ruletype == 3) {
            document.getElementById('commissionType').value = "Retention";
            } else if(ruletype == 4){
            document.getElementById('commissionType').value = "Deduction";
            }
          //  alert(document.getElementById('salestype').value)
          //  alert(document.getElementById('salesType').options[document.getElementById('salesType').selectedIndex].value)
            //-----Validations.....      
            if (document.getElementById('planname').value=="")
            {
                alert("Plananame should not be null");
                document.getElementById('planname').focus();
                return false;
            }     
            if(CompareDates()){
            return true;
            } else {
            return false;
            }
            
            }
        function checkZoneVal(val){
        if(val == "All"){
        document.getElementById('productdispid').style.display = "none";
        } else {
        document.getElementById('productdispid').style.display = "";
        }
        }
        function changeForm()
        {
          var commission_mode=document.getElementById('plantype').options[document.getElementById('plantype').selectedIndex].value;
          if (commission_mode=="1") 
                document.getElementById('productdispid').style.display = "none";
          else 
          {
                document.getElementById('productdispid').style.display = ""
          }
            if (commission_mode=="1" || commission_mode=="3") 
            {
                All_Option(1);//--------To Remove
            }
            if (commission_mode=="2") 
            {
                document.getElementById('cityrow').style.display = "none";
                All_Option(2);//-------City Value in case Product Wise Plan
                
            }
            else
                document.getElementById('cityrow').style.display = "";
        }
    function All_Option(flagval)
    {
        ctrl=document.getElementById('city');
        for(i=0;i<ctrl.options.length;i++)
        {
            if(ctrl.options[i].text=="All" && flagval=="1")
            {
                ctrl.remove(i);
                break;
            }   
            else if(ctrl.options[i].text=="All" && flagval=="2")
            {
                ctrl.options[i].selected=true;
                
            }
            
        }
    }
    function CompareDates() 
        { 
            var str1 = document.getElementById('valid_from').value;
            var str2 = document.getElementById('valid_to').value;
            var dt1  = parseInt(str1.substring(0,2),10); 
            var mon1 = parseInt(str1.substring(3,5),10);
            var yr1  = parseInt(str1.substring(6,10),10); 
            var dt2  = parseInt(str2.substring(0,2),10); 
            var mon2 = parseInt(str2.substring(3,5),10); 
            var yr2  = parseInt(str2.substring(6,10),10); 
            var date1 = new Date(yr1, mon1, dt1); 
            var date2 = new Date(yr2, mon2, dt2); 
            if(document.getElementById('valid_from').value=="" || document.getElementById('valid_to').value=="")
            {
                alert("Valid Dates should not be Empty");
                return false; 
             }   
            if(date2 < date1)
            {
                alert("To date cannot be less than from date");
                return false; 
            } else{
            return true;
            }
        }
    </script>
    <script type="text/javascript" charset="utf-8">
			$(function()
				{
                                        Date.format = 'dd/mm/yyyy';
                                        var D=new Date(),Y=D.getFullYear(),M=D.getMonth()+1;
                                        var dt=D.getDate();
                                        var curDate=dt+"/"+M+"/"+Y;
                                        curDate="01/01/2006"+"/"+M+"/"+Y;
					//$('.date-pick').datePicker({startDate:curDate});
                                        $('.date-pick').datePicker({startDate:curDate});
			});
		</script>

<body onload="javascript:bodyload();">
     <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
        <div id="inner-content">
            <div class="shadow-box">
                <div class="shadow-box-inner">
                    <form name=frmCreatePlan action="./CommissionPlan.do" method="Post" onsubmit="javascript:return checkRuleType();">
                         <% if(request.getAttribute(WitribeConstants.ERROR_STRING) != null) { %>
                        <P><font color="red"><b><%=request.getAttribute(WitribeConstants.ERROR_STRING)%></b></font></P>
                        <%}%>
                        <table width="442" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                            <tr><td><label>Plan Name</label><span class="mandatory">*</span></td><td><input type="text" id="planname" name="planname" value="<%=plan_name%>"></td></tr>
                            <tr><td><label>Sales Type</label><span class="mandatory">*</span></td>
                                <td><select name="salesType" id ="salesType" onchange="changeChannel(this.options[this.selectedIndex].value);">
                                        <option value="1">CSE</option>
                                        <option value="7">TL</option>
                                        <option value="8">RSM</option>
                                        <option value="9">SD</option>
                            </select></td></tr>
                            <tr><td><label>Plan Type</label><span class="mandatory">*</span></td>
                                <td><select name="plantype" id ="plantype" onchange="changeForm()">
                                        <option value="1">Market Wise</option>
                                        <option value="2">Product Wise</option>
                                        <option value="3">Market+Product Wise</option>
                             </select></td></tr>
                            <tr id="channelid"><td><label>Channel Type</label><span class="mandatory">*</span></td>
                                <logic:notEmpty name="ChannelList">
                                <td><select name="channelId"  id="ctype">
                                        <logic:iterate name="ChannelList" id="ctype" type="com.witribe.commission.vo.CommissionPlanVO" scope="request">
                                            <option value="${ctype.channelId}">${ctype.channelType}</option>
                                            
                                        </logic:iterate>
                            </select></td>
                                </logic:notEmpty></tr>
                                <logic:empty name = "ChannelList">
                                    <td>There are no channel Available to View</td>
                                </logic:empty>
                                <tr id="cityrow"><td><label>City</label><span class="mandatory">*</span></td>
                                    <logic:notEmpty name="CitylList">
                                        <td><select name="city" id="city" onchange= "getValues(this.options[this.selectedIndex].value);">
                                                <logic:iterate name="CitylList" id="cityid" type="com.witribe.commission.vo.CommissionPlanVO" scope="request">
                                                    <option value="${cityid.city}">${cityid.city}</option>
                                                    
                                                </logic:iterate>   
                                        </select></td>
                                    </logic:notEmpty>
                              </tr>
                              <!--<tr><td><lable>Zone</lable><span class="mandatory">*</span></td>
                                  <td><select name = "zone" id="zoneid" onchange = "checkZoneVal(this.options[this.selectedIndex].value);">
                                          
                                      </select>
                                     
                                      
                              </td></tr>-->
                           <tr id="productdispid"><td><lable>Product</lable><span class="mandatory">*</span></td>
                                  <td width="50"><select name ="productId" id="productid" class="txtxx" >
                                          
                                      </select>

                              </td></tr> 
                              <tr><td><label>CommissionRule Type</label><span class="mandatory">*</span></td>
                                <td><select name="commType"  id="comType">
                                        <option value="1">Basic</option>
                                  <!--  <option value="2">Promotional</option>-->
                                        <option value="3">Retention</option>
                                         <option value="4">Deduction</option>
                            </select></td></tr>
                                       <tr>
                                    <td><label for="valid_from">Valid From the Month</label><span class="mandatory">*</span></td>
                                    <td><input type="text" class="txtxx date-pick dp-applied" name="valid_from" id="valid_from"  readonly/>&nbsp;&nbsp;<p class="validationmsg"></p></td>
                                    <td>&nbsp;</td>
                                  </tr>  
                              <tr>
                                <td><label for="valid_to">Valid To</label><span class="mandatory">*</span></td>
                                <td><input type="text" class="txtxx date-pick dp-applied" name="valid_to" id="valid_to"  readonly/>&nbsp;&nbsp;<p class="validationmsg"></p></td>
                                <td>&nbsp;</td>
                              </tr> 
                              <tr>
                                <td><label>Plan Status</label></td>
                                <td><input type="checkbox"  name="planstatus" id="planstatus" value="1" onchange="setPlanStatus()" checked/><p class="validationmsg"></p></td>
                                <td>&nbsp;</td>
                              </tr> 
  
                        <input type=hidden name="plan_id" value="<%=PlanId%>"/>
                        <input type=hidden name="commissionType" id = "commissionType"/>
                        <input type=hidden name="salestype" id = "salestype"/>
                        <input type=hidden name="plan_type" id = "plan_type" value="<%=plan_type%>"/>
                        <TR>
                            <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                                            name=input> </TD>
                                                            </TR>
                                      
                   </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
 <!--callAJAXZoneList(this.options[this.selectedIndex].value) && -->

