<%@ page contentType="text/html" import="java.sql.*,com.witribe.bo.WitribeBO,com.witribe.inventory.bo.WitribeInventoryBO,com.witribe.dao.ChannelList,com.witribe.vo.commissionRule,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO,com.witribe.constants.WitribeConstants"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <% 
                String PlanId=request.getParameter("plan_id");
		String comtype=request.getAttribute("CommissionType").toString();
                String exception = "";
                String ruleids="";
                String ruletype=""; //---Basic,Retention.....
                String elg_from="";
                String elg_to="";
                String amounts="";
                String commission_mode="";
                String eligibility_target="";
                String min_eligibility="";
                String target_measure="";
                String commission_type="";
                String eligibility_period="";
                String rule_detail_ids="";
                String eligibility_measure="";
                String rule_update="2";
				if(comtype==null)
				{
					comtype="";
				}

        if (PlanId==null)
        {
            PlanId="";
        }
        if(PlanId.length()>0)
        {
               try {
                ChannelList cho = new ChannelList();
                List rulelist = cho.getCommissionRule(PlanId); 
             
           if(rulelist.size() > 0) {
                for(int i=0; i< rulelist.size();i++) {
                     commissionRule Crulelist = (commissionRule)rulelist.get(i);
					 if(Crulelist.getRuleType().equals(comtype))
					 {
							 ruleids=ruleids+Crulelist.getRuleId()+"*";
							 rule_detail_ids= rule_detail_ids+Crulelist.getRule_Detail_Id()+"*";
							 ruletype=ruletype+Crulelist.getRuleType()+"*";
							 elg_from=elg_from+Crulelist.getEligibility_From()+"*";
							 elg_to=elg_to+Crulelist.getEligibility_To()+"*";
							 amounts=amounts+Crulelist.getAmount()+"*";
							 commission_mode=commission_mode+Crulelist.getCommission_Mode()+"*";
							 commission_type=commission_type+Crulelist.getCommission_Type()+"*";
							 eligibility_target=eligibility_target+Crulelist.getEligibility_Target()+"*";
							 min_eligibility=min_eligibility+Crulelist.getMinEligibility()+"*";
							 target_measure=target_measure+Crulelist.getTargetMeasure()+"*";
							 eligibility_period=eligibility_period+Crulelist.getEligibility_Period()+"*";
							 eligibility_measure=eligibility_measure+Crulelist.getEligibilty_Measure()+"*";
					 }	
                     //elgTarget=elgTarget+Crulelist.
                    // out.print("<tr><td>"+Crulelist.getRuleType()+"</td><td>"+Crulelist.getEligibility_From()+"</td><td>"+Crulelist.getEligibility_To()+"</td><td colspan=5>"+Crulelist.getAmount()+"</td></tr>");   
            }
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
        var ruleids="<%=ruleids%>";
        var ruletype="<%=ruletype%>";
        var elg_from="<%=elg_from%>";
        var elg_to="<%=elg_to%>";
        var amounts="<%=amounts%>";
   	    commission_mode="<%=commission_mode%>";
		eligibility_target="<%=eligibility_target%>";
		min_eligibility="<%=min_eligibility%>";
		target_measure="<%=target_measure%>";
		commission_type="<%=commission_type%>";
		eligibility_period="<%=eligibility_period%>";
                rule_detail_ids="<%=rule_detail_ids%>";
                eligibility_measure="<%=eligibility_measure%>"; 
                comtype="<%=comtype%>";
         document.getElementById('backbtn').style.display = "none";                
         if(planid!="")  ///---------In case of Edit loading stored valies
         {
            
            var temp=ruletype.split("*");
            var temp1=elg_from.split("*");
            var temp2=elg_to.split("*");
            var temp3=amounts.split("*");
            var temp4=target_measure.split("*");
            var temp5=commission_type.split("*");
            var temp6=eligibility_target.split("*");
            var temp7=min_eligibility.split("*");
            var temp8=eligibility_period.split("*");
            var temp9=rule_detail_ids.split("*");
            var temp10=eligibility_measure.split("*");
            document.getElementById('backbtn').style.display = "";
            if(temp[0]=="")
               document.getElementById('commissionType').value=comtype;
            else   
               document.getElementById('commissionType').value= temp[0];
             
            if(document.getElementById('commissionType').value == 'Basic'){
				document.getElementById('elgFromId1').value = temp1[0];
				setSelected(document.getElementById('targetMeasure'),temp4[0]);
				setSelected(document.getElementById('commId1'),temp5[0]);
				document.getElementById('eligibilityTarget').value=temp6[0];
				document.getElementById('minEligibility').value=temp7[0];

            } else if(document.getElementById('commissionType').value == 'Retention') {
	            document.getElementById('elgFromId2').value = temp1[0];								setSelected(document.getElementById('targetMeasure1'),temp4[0]);
				setSelected(document.getElementById('commId2'),temp5[0]);	
				//document.getElementById('eligibilityFrom').value=temp6[0];
            }
            document.getElementById('elgToId1').value = temp2[0];
            document.getElementById('amount').value = temp3[0];
           if(document.getElementById('commissionType').value == 'Basic' || document.getElementById('commissionType').value== "Retention")
            for (var i=1;i<temp1.length-1;i++)
            {
                  AddRows_Modify(temp1[i],temp2[i],temp3[i]);

            }
            else
            {
                document.forms[1].eligibilityPeriod.value = temp8[0];
                setSelected(document.getElementById('elmeasure'), temp10[0]);                
            }
            
          }  
//       alert(document.getElementById('commissionType').value);
	 var commtype = document.getElementById('commissionType').value;
         document.getElementById('amt').style.display = "";
         document.getElementById('per').style.display = "none";
        if(commtype == "Retention"){
        document.getElementById('elgtarid').style.display = "none";
        document.getElementById('minelgid').style.display = "none";
        document.getElementById('retentionid1').style.display = "none";
        document.getElementById('retentionid2').style.display = "";
        document.getElementById('commtypeid2').style.display = "";
        document.getElementById('commtypeid1').style.display = "none";
        document.getElementById('elperiod').style.display = "none";
        document.getElementById('elgFromId1').disabled = true;
        document.getElementById('elgreadonly').style.display = "none";
        document.getElementById('elgnoreadonly').style.display = "";
        document.getElementById('elgFromId2').disabled = false;
        } else {
        document.getElementById('elgtarid').style.display = "";
        document.getElementById('minelgid').style.display = "";
        
       
        }
        var val = document.getElementById('targetMeasure1').options[document.getElementById('targetMeasure1').selectedIndex].value;
        //document.getElementById('commtypeid2').style.display = "none";
       
        if(commtype == "Basic" || commtype == "Promotional"){
        document.getElementById('elmeasure2').style.display = "";
        document.getElementById('elmeasure1').style.display = "none";
        document.getElementById('elgtarid').style.display = "";
        document.getElementById('minelgid').style.display = "";
        document.getElementById('elperiod').style.display = "none";
        document.getElementById('slabs').style.display = "";
        document.getElementById('commtypeid').style.display = "";
        document.getElementById('periodid').style.display = "";
        document.getElementById('targmesid').style.display = "";
         document.getElementById('commtypeid2').style.display = "none";
        document.getElementById('commtypeid1').style.display = "";
        document.getElementById('retentionid1').style.display = "";
        document.getElementById('retentionid2').style.display = "none";
        document.getElementById('elgreadonly').style.display = "";
        document.getElementById('elgFromId2').disabled = true;
        document.getElementById('elgFromId1').disabled = false;
        document.getElementById('elgnoreadonly').style.display = "none";
        
        } else {
        document.getElementById('elmeasure1').style.display = "";
        document.getElementById('elmeasure2').style.display = "none";
        }
        if(commtype == "Deduction"){
        document.getElementById('elperiod').style.display = "";
        document.getElementById('slabs').style.display = "none";
        document.getElementById('commtypeid').style.display = "none";
        document.getElementById('periodid').style.display = "none";
        document.getElementById('elgtarid').style.display = "none";
        document.getElementById('minelgid').style.display = "none";
        document.getElementById('targmesid').style.display = "none";
        }
       
        document.getElementById('pkr').style.display = "none";
        document.getElementById('pkr1').style.display = "none";
        //document.getElementById('retentionid1').style.display = "";
        
        
        }
              function setSelected(sel, valu) {
                var ctlname=sel.name;
                for(i=0;i<sel.options.length;i++) {
                    if(sel.options[i].value == valu) {
                            sel.options[i].selected = true;
                            
                         }
                }
            }
      function targetMeasureChange(tarval){
      if(tarval == 1){
      document.getElementById('commtypeid1').style.display = "";
      document.getElementById('commtypeid2').style.display = "none";
      document.getElementById('pkr').style.display = "none";
      document.getElementById('units').style.display = "";
      document.getElementById('pkr1').style.display = "none";
      document.getElementById('units1').style.display = "";
      } else {
      document.getElementById('commtypeid2').style.display = "";
      document.getElementById('commtypeid1').style.display = "none";
      document.getElementById('pkr').style.display = "";
      document.getElementById('units').style.display = "none";
      document.getElementById('pkr1').style.display = "";
      document.getElementById('units1').style.display = "none";
      }
      if(document.getElementById('commtypeid1').style.display == ""){
      var val = document.getElementById('commId1').options[document.getElementById('commId1').selectedIndex].value;
      } else {
       var val = document.getElementById('commId2').options[document.getElementById('commId2').selectedIndex].value;
      }
      if((((tarval == 1 && val == 2) || (tarval == 1 && val == 4)) || ((tarval == 2 && val == 2) || (tarval == 2 && val == 4)))){
      document.getElementById('per').style.display = "";
      document.getElementById('amt').style.display = "none";
      } else {
      document.getElementById('amt').style.display = "";
      document.getElementById('per').style.display = "none";
      }  
      }
      var rowCount = 0;
      function checkIsNumber(form){
      
       var len = rowCount;
       $('p.validationmsg').hide();
      if(document.getElementById('elperiod').style.display != "none"){
     
      if(document.forms[1].eligibilityPeriod.value == ''){
            showMessage(document.forms[1].eligibilityPeriod, 'Please Enter Eligibility Period');
            return(false);
      }
      if(!IsNumber(document.forms[1].eligibilityPeriod)){
            showMessage(document.forms[1].eligibilityPeriod, 'Please Enter Numbers Only');
            return(false);
        }
       
        } else {
         var commtype = document.getElementById('commissionType').value;
         if(commtype == "Basic" || commtype == "Promotional"){
        if(document.getElementById('eligibilityTarget').value == ''){
                     showMessage(document.getElementById('eligibilityTarget'), 'Please Enter Eligibility Target');
                    return(false);
                }
              if(!IsNumber(document.getElementById('eligibilityTarget'))){
                    showMessage(document.getElementById('eligibilityTarget'), 'Please Enter Numbers Only');
                    return(false);
                    }
        if(document.getElementById('eligibilityTarget').value <= 0){
                     showMessage(document.getElementById('eligibilityTarget'), 'Eligibility Target should not be 0');
                    return(false);
                }    
        if(document.getElementById('minEligibility').value == ''){
                     showMessage(document.getElementById('minEligibility'), 'Please Enter Minimum Eligibility');
                    return(false);
                }
              if(!IsNumber(document.getElementById('minEligibility'))){
                    showMessage(document.getElementById('minEligibility'), 'Please Enter Numbers Only');
                    return(false);
               }
        }
        if(document.getElementById('amount').value == ''){
        
                showMessage(document.getElementById('amount'), 'Please Enter Amount');
                return(false);
              }
              if(!IsNumber(document.getElementById('amount'))){
                    showMessage(document.getElementById('amount'), 'Please Enter Numbers Only');
                    return(false);
                }
                if(document.getElementById('elgreadonly').style.display == ""){
                  if(document.getElementById('elgFromId1').value == ''){

                    showMessage(document.getElementById('elgFromId1'), 'Please Enter Eligibility From');
                    return(false);
                  }
                  if(!IsNumber(document.getElementById('elgFromId1'))){
                        showMessage(document.getElementById('elgFromId1'), 'Please Enter Numbers Only');
                        return(false);
                    }
                } else if(document.getElementById('elgnoreadonly').style.display == ""){
                    if(document.getElementById('elgFromId2').value == ''){

                        showMessage(document.getElementById('elgFromId2'), 'Please Enter Eligibility From');
                        return(false);
                      }
                  if(!IsNumber(document.getElementById('elgFromId2'))){
                        showMessage(document.getElementById('elgFromId2'), 'Please Enter Numbers Only');
                        return(false);
                    }
                }
                
                 if(document.getElementById('elgToId1').value == ''){

                        showMessage(document.getElementById('elgToId1'), 'Please Enter Eligibility To');
                        return(false);
                      }
                  if(!IsNumber(document.getElementById('elgToId1'))){
                        showMessage(document.getElementById('elgToId1'), 'Please Enter Numbers Only');
                        return(false);
                    }
              if(document.getElementById('minEligibility').value == ''){
                     showMessage(document.getElementById('minEligibility'), 'Please Enter Minimum Eligibility');
                    return(false);
                }
              if(!IsNumber(document.getElementById('minEligibility'))){
                    showMessage(document.getElementById('minEligibility'), 'Please Enter Numbers Only');
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
       //} 
        return true;
     }
    function checkPreviousTo(toval){
      
      }
     function checkPrevious(val){
          var elgTo = document.getElementById('elgToId').value;
         if(parseInt(val) < parseInt(elgTo)){
            alert("EligibilityFrom Should be Greater than eligibilityTo");
         }
         }
      function checkAmountValue(val){
     
         var amount = val.value;
         if(amount == ''){
         showMessage(val, 'Please Enter Amount');
            return(false);
         }
         if(!IsNumber(val)){
         
            showMessage(val, 'Please Enter Numbers Only');
            return(false);
        }
         
         }
         
       function AddRows(){
       $('p.validationmsg').hide();
            if(document.getElementById('slabs').style.display == ""){
             var elgTo = document.getElementById('elgToId1').value;
             if(document.getElementById('elgreadonly').style.display == ""){
             var elgFrom = document.getElementById('elgFromId1').value;
             } else {
             var elgFrom = document.getElementById('elgFromId2').value;
             }
             if(parseInt(elgTo) <= parseInt(elgFrom)){
             alert("EligibilityTo Value should be greater than eligibilityFrom");
             return false;
             }
             if(document.getElementById('amount').value == ''){
        
                showMessage(document.getElementById('amount'), 'Please Enter Amount');
                return(false);
              }
              if(!IsNumber(document.getElementById('amount'))){
                    showMessage(document.getElementById('amount'), 'Please Enter Numbers Only');
                    return(false);
                }
                if(document.getElementById('elgreadonly').style.display == ""){
                  if(document.getElementById('elgFromId1').value == ''){

                    showMessage(document.getElementById('elgFromId1'), 'Please Enter Eligibility From');
                    return(false);
                  }
                  if(!IsNumber(document.getElementById('elgFromId1'))){
                        showMessage(document.getElementById('elgFromId1'), 'Please Enter Numbers Only');
                        return(false);
                    }
                } else if(document.getElementById('elgnoreadonly').style.display == ""){
                    if(document.getElementById('elgFromId2').value == ''){

                        showMessage(document.getElementById('elgFromId2'), 'Please Enter Eligibility From');
                        return(false);
                      }
                  if(!IsNumber(document.getElementById('elgFromId2'))){
                        showMessage(document.getElementById('elgFromId2'), 'Please Enter Numbers Only');
                        return(false);
                    }
                }
                
                 if(document.getElementById('elgToId1').value == ''){

                        showMessage(document.getElementById('elgToId1'), 'Please Enter Eligibility To');
                        return(false);
                      }
                  if(!IsNumber(document.getElementById('elgToId1'))){
                        showMessage(document.getElementById('elgToId1'), 'Please Enter Numbers Only');
                        return(false);
                    }
             }
         if (!document.getElementsByTagName) return;
           
             tabBody=document.getElementsByTagName("TBODY").item(1);
             divid1 = document.getElementById('divid');
             row=document.createElement("TR");
             cell1 = document.createElement("TD");
             cell1.innerHTML = '<input type="text" name="eligibilityFrom" id=elgFromId maxlength="5" onblur="checkPrevious(this.value)"/><p class="validationmsg"></p>';
             //cell1.setAttribute("colspan",3);
             row.appendChild(cell1);
             cell2 = document.createElement("TD");
             cell2.innerHTML = '<input type="text" name="eligibilityTo" id=elgToId maxlength="5" onblur="checkPreviousTo(this.value)"/><p class="validationmsg"></p>';
             //cell2.setAttribute("colspan","3");
             row.appendChild(cell2);
             cell3 = document.createElement("TD");
             //cell3.setAttribute("colspan","3");
             cell3.innerHTML = '<input type="text" name="amount" id = amt1 maxlength="7" onblur="return checkAmountValue(this);"/><p class="validationmsg"></p>';
             row.appendChild(cell3);
             //divid1.appendChild(row);
             tabBody.appendChild(row);
             
             rowCount=rowCount+1;
      }
      function calElgFrom(){
      var elgTarget = document.getElementById('eligibilityTarget').value;
      var minElg = document.getElementById('minEligibility').value;
      if((elgTarget != '' && minElg != '')){
        if(parseInt(minElg) <= parseInt(elgTarget)){
            document.getElementById('elgFromId1').value = Math.floor((parseInt(minElg)/parseInt(elgTarget))*100);
          } else {
            alert("Minimum Eligibility Should be less than Eligibility Target");
          }
          } /*else {
            alert("please Enter both Values");
            }*/
     
     
      }
      function onTypeChange(val){
      if(document.getElementById('targmesid').style.display == ""){
      var tarmes = document.getElementById('targetMeasure').options[document.getElementById('targetMeasure').selectedIndex].value;
      } else {
      var tarmes = document.getElementById('targetMeasure1').options[document.getElementById('targetMeasure1').selectedIndex].value;
      }
      if((((tarmes == 1 && val == 2) || (tarmes == 1 && val == 4)) || ((tarmes == 2 && val == 2) || (tarmes == 2 && val == 4)))){
      document.getElementById('per').style.display = "";
      document.getElementById('amt').style.display = "none";
      } else {
      document.getElementById('amt').style.display = "";
      document.getElementById('per').style.display = "none";
      }      
      }
       function AddRows_Modify(val1,val2,val3){
        if (!document.getElementsByTagName) return;
             tabBody=document.getElementsByTagName("TBODY").item(1);
             divid1 = document.getElementById('divid');
             row=document.createElement("TR");
             cell1 = document.createElement("TD");
             cell1.innerHTML = '<input type="text" name="eligibilityFrom" id=elgFromId maxlength="5" onblur="checkPrevious(this.value)" value="'+val1+'"/><p class="validationmsg"></p>';
             //cell1.setAttribute("colspan",3);
             row.appendChild(cell1);
             cell2 = document.createElement("TD");
             cell2.innerHTML = '<input type="text" name="eligibilityTo" id=elgToId maxlength="5" onblur="checkPreviousTo(this.value)" value="'+val2+'"/><p class="validationmsg"></p>';
             //cell2.setAttribute("colspan","3");
             row.appendChild(cell2);
             cell3 = document.createElement("TD");
             //cell3.setAttribute("colspan","3");
             cell3.innerHTML = '<input type="text" name="amount" id = amt1 maxlength="7" onblur="return checkAmountValue(this);" value="'+val3+'"/><p class="validationmsg"></p>';
             row.appendChild(cell3);
             //divid1.appendChild(row);
             tabBody.appendChild(row);
             
             rowCount=rowCount+1;
            
      }
    </script>

<body onload="javascript:bodyload();">
     <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
        <div id="inner-content">
            <div class="shadow-box">
                <div class="shadow-box-inner">
                    <form name=frmCreatePlan action="./CommissionRule.do" method="Post" onsubmit="return checkIsNumber(this);">
                         <% if(request.getAttribute(WitribeConstants.ERROR_STRING) != null) { %>
                        <P><font color="red"><b><%=request.getAttribute(WitribeConstants.ERROR_STRING)%></b></font></P>
                        <%}%>
                        <table width="442" border="0" cellspacing="0" cellpadding="0" class="info-listing" id="myTable">
                            <tbody>
                                <tr><td><label>Rule Type</label></td>
                                <td><input type="text" name="commissionType" id="commissionType" value="<%=request.getAttribute("CommissionType")%>" readonly/></td></tr>
                                <tr id="targmesid"><td><label>Target Measure</label></td>
                                    <td id="retentionid1"><select name = "targetMeasure" id ="targetMeasure" onchange="targetMeasureChange(this.options[this.selectedIndex].value);">
                                            <option value="1">Sales Target</option>
                                            <option value="2">Revenue</option>
                                </select></td>
                            <td id="retentionid2"><select name = "targetMeasure" id ="targetMeasure1" onchange="targetMeasureChange(this.options[this.selectedIndex].value);">
                                           <!--<option value="1">Sales Target</option>-->
                                            <option value="2">Revenue</option>
                                </select></td></tr>
                                <tr id="commtypeid"><td><label>Commission Type</label></td>
                                    <td id="commtypeid1"><select name="commType" id="commId1" onchange="onTypeChange(this.options[this.selectedIndex].value)">
                                            <option value="1">Normal Flat</option>
                                            <option value="2">Normal Percentage</option>
                                            <option value="3">Tiered Flat</option>
                                            <option value="4">Tiered Percentage</option>
                                    </select></td>
                                    <td id="commtypeid2"><select name="commType" id="commId2" onchange="onTypeChange(this.options[this.selectedIndex].value)">
                                            <option value="1">Normal Flat</option>
                                            <option value="2">Normal Percentage</option>
                                </select></td></tr>
                                <tr id="elperiod"><td><label>Eligibility Period</label></td>
                                <td><input type="text" name="eligibilityPeriod" maxlength="3"/><p class="validationmsg"></p></td></tr>
                                <tr id="elgtarid"><td><label>Eligibility Target</label></td>
                                    <td><input type = "text" name="eligibilityTarget" id="eligibilityTarget" maxlength="5" onblur="calElgFrom();"/><p class="validationmsg"></p></td>
                                <td id="pkr"><label>PKR</label></td><td id="units"><label>Units</label></td></tr>
                                
                                <tr id="minelgid"><td><label>Minimum Eligibility</label></td>
                                    <td><input type = "text" name="minEligibility" maxlength="5" id="minEligibility" onblur="calElgFrom();"/><p class="validationmsg"></p></td>
                                <td id="pkr1"><label>PKR</label></td><td id="units1"><label>Units</label></td></tr>
                                <tr id="elgmesid"><td><label>Eligibility Measure</label></td>
                                    <td id="elmeasure1"><select name="eligibilityMeasure" id="elmeasure">
                                            <option value="1">Days</option>
                                            <option value="2">Months</option>
                                            <option value="3">Years</option>
                                    </select></td>
                                    <td id="elmeasure2"><select name="eligibilityMeasurePer">
                                            <option value="4">Percentage</option>
                                </select></td></tr>
                                <tr id="slabs">
                                    <th><label>Eligibility From</label></th>
                                    <th><label>Eligibility To</label></th>
                                <th id="amt"><label>Amount</label></th>
                                <th id="per"><label>Percentage</label></th></tr>
                                <!-- <input type=hidden name="stepId" id="stepId" value="1"/>-->
                                 <input type=hidden name="ruleids" value="<%=ruleids%>">
                                <input type=hidden name="rule_detail_ids" value="<%=rule_detail_ids%>">                                 
                                    <tr id="periodid">
                                        <td id="elgreadonly"><input type="text" name="eligibilityFrom" id="elgFromId1" maxlength="5" readonly/><p class="validationmsg"></p></td>
                                        <td id="elgnoreadonly"><input type="text" name="eligibilityFrom" id="elgFromId2" maxlength="5"/><p class="validationmsg"></p></td>
                                        <td><input type="text" name="eligibilityTo" id="elgToId1" maxlength="5"><p class="validationmsg"></p></td>
                                        <td><input type="text" name="amount" id="amount" maxlength="7"/><p class="validationmsg"></p></td>
                                        <td><input type="button" name="Add" value="Add" onclick = "return AddRows();"/></td>
                                    </tr>
                                    
                                 </tbody>  
                                 <tbody>
                                   
                                 </tbody>
                                 
                                
                           
                        <TR>
                            <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD>
                                       <TD id="backbtn"><!--<a class=submit-btn href="./PaymentRule.do?plan_id=<%=PlanId%>" onmouseover="this.src='/images/icon-back-active.gif'" onmouseout="this.src='images/icon-back-active_1.gif'"><img src="images/icon-back-active.gif"></a>--></TD>                               
                                  
                                  </TR>
                                      
                   </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
 <!--callAJAXZoneList(this.options[this.selectedIndex].value) && -->

