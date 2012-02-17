<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO,com.witribe.dao.ChannelList,java.sql.*,com.witribe.vo.paymentRule,com.witribe.constants.WitribeConstants"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
        String PlanId=request.getParameter("plan_id");
	String ruletype=request.getAttribute("CommissionType").toString();
        String duralist="";
        String percentlist="";
        String ruleids="";
        String durameasure="";
        String exception = "";
        if(ruletype==null)
        {
                ruletype="";
        }

        if(ruletype.equals("Basic"))
        {
                ruletype="1";

        }
        else if(ruletype.equals("Retention"))
        {
                ruletype="2";
        }
        else if(ruletype.equals("Deduction"))
        {
                ruletype="3";
        }       
        if (PlanId==null)
        {
            PlanId="";
        }
        if(PlanId.length()>0)
        {
           try {
             ChannelList cho = new ChannelList();   
             List payrule = cho.getPayRule(PlanId); 
           if(payrule.size() > 0) {
                
                for(int i=0; i< payrule.size();i++) {
                     paymentRule paymentrule = (paymentRule)payrule.get(i);
					 if(paymentrule.getRuleType().equals(ruletype))
					 {	
							 duralist=duralist+paymentrule.getDuration()+"*";
							 percentlist=percentlist+paymentrule.getAmout()+"*";
							 ruleids=ruleids+paymentrule.getPayruleId()+"*";
							 durameasure=paymentrule.getDuration_Type();
					 }		 

            }  
               // ruleids=ruleids.substring(0,ruleids.length()-1);
                
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
     function bodyload(){
         var planid="<%=PlanId%>";
         var durameasure="<%=durameasure%>";
         var duralist="<%=duralist%>";
         var percentlist="<%=percentlist%>";
         var ruleids="<%=ruleids%>";         
         document.getElementById('dur2').disabled = true;
         document.getElementById('dur3').disabled = true;
         document.getElementById('monthid').style.display = "none";
         document.getElementById('yearid').style.display = "none";
         document.getElementById('backbtn').style.display = "none";
         if(planid!="")
         {

            var temp1=duralist.split("*");
            var temp2=percentlist.split("*");
            document.getElementById('backbtn').style.display = "";
            if(temp1.length>0)
            {
               if(durameasure!="") 
               {
                setSelected(document.getElementById('durationMeasure'),durameasure);
                document.getElementById('durationMeasure').disabled=true;
               } 
                document.getElementById('ruleids').value=ruleids;
                if(document.getElementById('dur1').disabled==false)
                    document.getElementById('dur1').value=temp1[0];
                if(document.getElementById('dur2').disabled==false)
                    document.getElementById('dur2').value=temp1[0];
                if(document.getElementById('dur3').disabled==false)
                    document.getElementById('dur3').value=temp1[0];

                document.getElementById('paymentAmount').value=temp2[0];
                for(i=1;i<temp1.length-1;i++)
                {
                    AddRows_Modify(temp1[i],temp2[i]);

                }
            }
            
         }

      }
      function setSelected(sel, valu) {
                var ctlname=sel.name;
                for(i=0;i<sel.options.length;i++) {
                    if(sel.options[i].text == valu) {
                            sel.options[i].selected = true;
                            durationMeasureChange(sel.options[i].value);
                         }
                }
            }
      function durationMeasureChange(val){
      if(val == 1){
            document.getElementById('dur1').disabled = false;
            document.getElementById('dur2').disabled = true;
            document.getElementById('dur3').disabled = true;
            document.getElementById('monthid').style.display = "none";
            document.getElementById('yearid').style.display = "none";
            document.getElementById('daysid').style.display = "";
            
     } else if(val == 2){
            document.getElementById('dur1').disabled = true;
            document.getElementById('dur2').disabled = false;
            document.getElementById('dur3').disabled = true;
            document.getElementById('monthid').style.display = "";
            document.getElementById('yearid').style.display = "none";
            document.getElementById('daysid').style.display = "none";
      } else {
            document.getElementById('dur1').disabled = true;
            document.getElementById('dur2').disabled = true;
            document.getElementById('dur3').disabled = false;
            document.getElementById('monthid').style.display = "none";
            document.getElementById('yearid').style.display = "";
            document.getElementById('daysid').style.display = "none";
     }
   }
     
      function checkTotal(val){
         $('p.validationmsg').hide();
          var payement = val.value;
            if(payement == ''){
                showMessage(val, 'Please Enter Percentage of Amount');
                return false;
             }
             if(!IsNumber(val)){
                showMessage(val, 'Please Enter Numbers Only');
                return false;
        }
      }
      function checkvalue(val){
      
      $('p.validationmsg').hide();
      var dur = val.value;
        if(dur == ''){
        showMessage(val, 'Please Enter Duration');
        return false;
        }
        if(!IsNumber(val)){
        showMessage(val, 'Please Enter Numbers Only');
        return false;
        }
      }
      function checkValidation(){
               $('p.validationmsg').hide();
               
               
              if(document.getElementById('daysid').style.display != "none"){ 
                var dur = document.getElementById('dur1');
                //var duration = document.getElementById('dur1').value;
                    if(dur.value == ''){
                             showMessage(dur, 'Please Enter Duration');
                            return(false);
                        }
                      if(!IsNumber(dur)){
                            showMessage(dur, 'Please Enter Numbers Only');
                            return(false);
                            }

              } else if(document.getElementById('monthid').style.display != "none"){
              var dur = document.getElementById('dur2');
                //var duration =document.getElementById('dur2').value;
                 if(dur.value == ''){
                             showMessage(dur, 'Please Enter Duration');
                            return(false);
                        }
                      if(!IsNumber(dur)){
                            showMessage(dur, 'Please Enter Numbers Only');
                            return(false);
                            }
              } else if(document.getElementById('yearid').style.display != "none"){
              var dur = document.getElementById('dur3');
                //var duration = document.getElementById('dur3').value;
                 if(dur.value == ''){
                             showMessage(dur, 'Please Enter Duration');
                            return(false);
                        }
                      if(!IsNumber(dur)){
                            showMessage(dur, 'Please Enter Numbers Only');
                            return(false);
                            }
              }
              
              if(document.getElementById('paymentAmount').value == ''){
                             showMessage(document.getElementById('paymentAmount'), 'Please Enter percentage of Amount');
                            return(false);
                        }
                      if(!IsNumber(document.getElementById('paymentAmount'))){
                            showMessage(document.getElementById('paymentAmount'), 'Please Enter Numbers Only');
                            return(false);
          }
          var tbody = document.getElementsByTagName("TBODY").item(1);
            //var divobj = tbody.childNodes;
            var divobj = tbody.getElementsByTagName("TR");
            for(var i=0;i<divobj.length;i++){
                //var row = divobj[i].childNodes;
                var row = divobj[i].getElementsByTagName("TD");
                for(r=0;r<row.length;r++){
                        var items = row[r].childNodes;
                   
                for(ind=0;ind<items.length;ind++){		 
                  if(items[ind].type && items[ind].type == 'text') {

                    if(items[ind].value == ''){
                         showMessage((items[ind]), 'Please Enter value');
                        return(false);
                    }
                  if(!IsNumber(items[ind])){
                        showMessage((items[ind]), 'Please Enter Numbers Only');
                        return(false);
                        }
              
                }
                }
                
		}
            }
      
      }
    function AddRows_Modify(val1,val2){
    $('p.validationmsg').hide();
      if(document.getElementById('daysid').style.display == ""){ 
        var dur = document.getElementById('dur1');
        var duration = document.getElementById('dur1').value;
        
      } else if(document.getElementById('monthid').style.display == ""){
      var dur = document.getElementById('dur2');
        var duration =document.getElementById('dur2').value;
      } else if(document.getElementById('yearid').style.display == ""){
      var dur = document.getElementById('dur3');
        var duration = document.getElementById('dur3').value;
      }
      var totalamt = document.getElementById('totalamt').value
      var payAmount = document.getElementById('paymentAmount').value;
      if(duration != "" && duration != null) {
      
        if(payAmount != ''){
         if(parseInt(payAmount) < 100){
            if (!document.getElementsByTagName) return;
             tabBody=document.getElementsByTagName("TBODY").item(1);
             row=document.createElement("TR");
             cell1 = document.createElement("TD");
             cell2 = document.createElement("TD");
             cell1.innerHTML = '<input type="text" name="duration" id = "duration1" maxlength="3" onblur="checkvalue(this);" value="'+val1+'"><p class="validationmsg"></p>';
             cell2.innerHTML = '<input type="text" name="paymentAmount" id = "paymentAmount1" maxlength="7" onblur="checkTotal(this);" value="'+val2+'"><p class="validationmsg"></p>';
             row.appendChild(cell1);
             row.appendChild(cell2);
             tabBody.appendChild(row);
             
             } else {
             alert("Payement Amount should not be greater than 100");
             return false;
            } 
            } else {
            showMessage(document.getElementById('paymentAmount'), 'Please enter Payment Amount');
            return false;
             }
      } else {
      showMessage(dur, 'Please Enter Duration');
      return false;
      }
      return true;
      }
 function AddRows(){
    $('p.validationmsg').hide();
      if(document.getElementById('daysid').style.display == ""){ 
        var dur = document.getElementById('dur1');
        var duration = document.getElementById('dur1').value;
        
      } else if(document.getElementById('monthid').style.display == ""){
      var dur = document.getElementById('dur2');
        var duration =document.getElementById('dur2').value;
      } else if(document.getElementById('yearid').style.display == ""){
      var dur = document.getElementById('dur3');
        var duration = document.getElementById('dur3').value;
      }
      var totalamt = document.getElementById('totalamt').value
      var payAmount = document.getElementById('paymentAmount').value;
      if(duration != "" && duration != null) {
      
        if(payAmount != ''){
         if(parseInt(payAmount) < 100){
            if (!document.getElementsByTagName) return;
             tabBody=document.getElementsByTagName("TBODY").item(1);
             row=document.createElement("TR");
             cell1 = document.createElement("TD");
             cell2 = document.createElement("TD");
             cell1.innerHTML = '<input type="text" name="duration" id = "duration1" maxlength="3" onblur="checkvalue(this);"/><p class="validationmsg"></p>';
             cell2.innerHTML = '<input type="text" name="paymentAmount" id = "paymentAmount1" maxlength="7" onblur="checkTotal(this);"/><p class="validationmsg"></p>';
             row.appendChild(cell1);
             row.appendChild(cell2);
             tabBody.appendChild(row);
             
             } else {
             alert("Payement Amount should not be greater than 100");
             return false;
            } 
            } else {
            showMessage(document.getElementById('paymentAmount'), 'Please enter Payment Amount');
            return false;
             }
      } else {
      showMessage(dur, 'Please Enter Duration');
      return false;
      }
      return true;
      }

    </script>

<body onload="javascript:bodyload();">
     <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
        <div id="inner-content">
            <div class="shadow-box">
                <div class="shadow-box-inner">
                    <form action="./PaymentRule.do" method="Post" onsubmit="return checkValidation();">
                        <% if(request.getAttribute(WitribeConstants.ERROR_STRING) != null) { %>
                        <P><font color="red"><b><%=request.getAttribute(WitribeConstants.ERROR_STRING)%></b></font></P>
                        <%}%>
                        <table width="442" border="0" cellspacing="0" cellpadding="0" class="info-listing" id='mytable'>
                            <tbody>
                                <tr><td><label>Duration Measure</label></td>
                                    <td><select name = "durationMeasure" id ="durationMeasure" onchange="durationMeasureChange(this.options[this.selectedIndex].value);">
                                            <option value="1">Days</option>
                                            <option value="2">Months</option>
                                            <option value="3">Years</option>
                                </select></td></tr>
                                 <%--<td  id="monthid<%=j%>"><select name="duration" id="dur2<%=j%>">
                                            <% for(int i=1;i<=12;i++) { %>
                                            <option value="<%=i%>"><%=i%></option>
                                            <%}%>
                                    </select></td>
                                    <td  id="yearid<%=j%>"><input type="text" name="duration"  id="dur3<%=j%>" maxlength="3"/></td>--%>
                                <tr><th>Duration</th>
                                <th>Percentage of Amount</th></tr>
                                
                                <tr>
                                    <td  id="monthid"><input type="text" name="duration"  id="dur2" maxlength="3"/><p class="validationmsg"></p></td>
                                    <td  id="yearid"><input type="text" name="duration"  id="dur3" maxlength="3"/><p class="validationmsg"></p></td>
                                    <td id="daysid"><input type="text" name="duration" id="dur1" maxlength="3"/><p class="validationmsg"></p></td>
                                    <td id="pamount"><input type="text" name="paymentAmount" id = "paymentAmount" maxlength = "7"/><p class="validationmsg"></p></td>
                                   
                                <td><input type="button" name="Add" value="Add" onclick = "return AddRows();"/></td></tr>
                                 
                                
                                </tbody>
                                <tbody></tbody>
                                <input type=hidden name="commissionType" id = "commissionType" value="<%=request.getAttribute("CommissionType")%>"/>
                                <input type="hidden" name="totalamt" id = "totalamt"/>
                                <input type="hidden" name="ruleids" id = "ruleids" value="<%=ruleids%>"/>
                                <input type=hidden name="plan_id" value="<%=PlanId%>"/>
                                <TR>
                                    <TD>&nbsp;</TD>
                                    <TD align=left><INPUT class=submit-btn 
                                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                              src="images/btn-submit-thickbox.gif" 
                                                      name=input> </TD>
                                   <TD id="backbtn"><!-- <a class=submit-btn href="./CommissionPlan.do?PlanId=<%=PlanId%>" onmouseover="this.src='/images/icon-back-active.gif'" onmouseout="this.src='images/icon-back-active_1.gif'"><img src="images/icon-back-active.gif"></a>--></TD>
                                                  </TR>
                    </table>
                    </form>
                    <!--<script language='javascript'>
                         bodyload();
                    </script>-->
                </div>
            </div>
        </div>
    </body>
 <!--callAJAXZoneList(this.options[this.selectedIndex].value) && -->

