<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
        include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
	$sales_obj = new SalesPersonnel();
        $promos = $sales_obj->getCurrentExistingPromos();
        $PaymentStats = $sales_obj->getMyPaidUnpaidCustStats($_SESSION['brm_csr_id']);
?>
<script type="text/javascript">
 $(function()
{  var post_func = function()
				{

					var effect = function(data){ $("#verification_info").html(data).show("normal"); };
					$.post('FormProcessor/ajax_my_nonpaying_customers.php',effect);
				}
	$("#verification_info").html("<img src='../../images/loading.gif' /> Loading...");
	$("#verification_info").show("normal",post_func);
});
function getFilteredData()
{
     var SearchOption = $("#SearchOption").val();
     var SearchText = $("#SearchText").val();
     var LastReasonCode = $("#LastReasonCode").val();
     var paymentstatus = $("#paymentstatus").val();
     var Promos = $("#Promos").val();
     
     document.getElementById('LastReasonCode').disabled=false;
     document.getElementById('paymentstatus').disabled=false;
     document.getElementById('SearchText').disabled=false;
     document.getElementById('Promos').disabled=false;

     var post_func = function()
				{
					var effect = function(data){ $("#verification_info").html(data).show("normal"); 	};
					$.post('FormProcessor/ajax_my_nonpaying_customers.php',{SearchOption:SearchOption,SearchText:SearchText,LastReasonCode:LastReasonCode,paymentstatus:paymentstatus,Promos:Promos} ,effect);
				}
	$("#verification_info").html("<img src='../../images/loading.gif' /> Loading...");
	$("#verification_info").show("normal",post_func);
}

        
function EnableDisable()
{
    var SearchOption = $("#SearchOption").val();

     if(SearchOption=='')
         {
            document.getElementById('LastReasonCode').disabled=false;
            document.getElementById('paymentstatus').disabled=false;
            document.getElementById('SearchText').disabled=false;
            document.getElementById('LastReasonCode').selectedIndex = 0;
            document.getElementById('Promos').selectedIndex = 0;
            document.getElementById('paymentstatus').selectedIndex = 0;
            document.getElementById('SearchText').value='';
         }
     if(SearchOption=='CustomerID' || SearchOption=='CustomerName' || SearchOption=='TimesCalled' || SearchOption=='LastCalledOn' || SearchOption=='DueAmnt')
     {
         if(SearchOption=='LastCalledOn')
         {
          alert(" Provide Date in this Format: Year-Month-Date e.g: 2011-04-15 ");
         }
         document.getElementById('LastReasonCode').disabled=true;
         document.getElementById('paymentstatus').disabled=true;
         document.getElementById('SearchText').disabled=false;
         document.getElementById('LastReasonCode').selectedIndex = 0;
         document.getElementById('paymentstatus').selectedIndex = 0;
          document.getElementById('Promos').selectedIndex = 0;
	 document.getElementById('Promos').disabled=true;
         document.getElementById('SearchText').value='';
         document.getElementById('SearchText').focus();
     }

     if(SearchOption=='LastReasonCode')
     {
         document.getElementById('LastReasonCode').disabled=false;
         document.getElementById('paymentstatus').disabled=true;
         document.getElementById('SearchText').disabled=true;
          document.getElementById('Promos').selectedIndex = 0;
	 document.getElementById('Promos').disabled=true;
         document.getElementById('LastReasonCode').selectedIndex = 0;
         document.getElementById('paymentstatus').selectedIndex = 0;
         document.getElementById('LastReasonCode').focus();
     }

     if(SearchOption=='PaymentStatus')
     {
         document.getElementById('paymentstatus').disabled=false;
         document.getElementById('SearchText').disabled=true;
         document.getElementById('LastReasonCode').selectedIndex = 0;
	 document.getElementById('LastReasonCode').disabled=true;
         document.getElementById('paymentstatus').selectedIndex = 0;
         document.getElementById('paymentstatus').focus();
         document.getElementById('Promos').selectedIndex = 0;
	 document.getElementById('Promos').disabled=true;
     }
     if(SearchOption=='Promo')
     {
         document.getElementById('paymentstatus').disabled=true;
	 document.getElementById('SearchText').disabled=true;
         document.getElementById('LastReasonCode').selectedIndex = 0;
	 document.getElementById('LastReasonCode').disabled=true;
         document.getElementById('paymentstatus').selectedIndex = 0;
         document.getElementById('Promos').disabled=false;
         document.getElementById('Promos').focus();
     }
}
</script>
<table width="100%">
  <tr>
    <td colspan="7" align="center" style="font-size:20px"><strong>Customer Payables</strong></td>
  </tr>
</table>
<form name='searchbyOption' id='searchbyOption' method='post' action="">
<table width="80%">
             <tr>
                   <td>
                       <strong><font size="2" color="brown">Paid / Unpaid Summary: </font></strong>
                   </td>
               </tr>
               <tr>
                   <? foreach ($PaymentStats as $payment) {?>
                   <td>
                       <font size="2" >Total Assigned: <?=$payment[TOTAL]?> </font>
                       , <font size="2" >Paid: <?=$payment[PAID]?> </font>
                       , <font size="2" >UnPaid: <?=$payment[UNPAID]?> </font>
                   </td>
                   <? }?>
               </tr>
               <tr>
                   <td>
                       &nbsp;
                   </td>
               </tr>
            <tr>
                <td>Search By : <select name="SearchOption" id="SearchOption" class="selectbox" onchange="EnableDisable();">
                  <option value=""> --- ALL ---</option>
                  <option value="CustomerID">Customer ID</option>
                  <option value="CustomerName">Customer Name</option>
                  <option value="TimesCalled">Times Called</option>
                  <option value="LastCalledOn">Last Called On</option>
                  <option value="LastReasonCode">Last Reason Code</option>
                  <option value="DueAmnt">Due Amount</option>
                  <option value="PaymentStatus">Payment Status</option>
                   <option value="Promo">Promo</option>
                </select></td>

        <td>Search Text:
            <input type="text" id="SearchText" name="SearchText" value="" /><div id="ExampleText"></div>
        </td>

       </tr>
         <tr>
                 <td>Payment Status:
        <select name="paymentstatus" id="paymentstatus" class="selectbox">
                  <option value=""> --- ALL ---</option>
                  <option value="0">Unpaid</option>
                  <option value="1">Paid</option>
                </select>
                 </td>
                 <td>Reson Code:
        <select name="LastReasonCode" id="LastReasonCode" class="selectbox" >
                  <option value=""> --- ALL ---</option>
                  <option value="Call not contacted/busy/switched off">Call not contacted/busy/switched off</option>
                  <option value="Customer not available">Customer not available</option>
                  <option value="Device already returned">Device already returned</option>
		  <option value="Did not use - Waiver Request">Did not use - Waiver Request</option>
		  <option value="Fake Sales">Fake Sales</option>
                  <option value="Incorrect customer details">Incorrect customer details</option>
                  <option value="Out of Station">Out of Station</option>
		  <option value="Safe Custody / Freeze Account">Safe Custody / Freeze Account</option>
		  <option value="Troubleshooting request">Troubleshooting request</option>
		  <option value="Troubleshooting request - Waiver">Troubleshooting request - Waiver</option>
		  <option value="Will not pay - Sales Return Request">Will not pay - Sales Return Request</option>
		  <option value="Will Pay - Home Collection ">Will Pay - Home Collection </option>
		  <option value="Will Pay - Themselves">Will Pay - Themselves</option>
                </select>
             </td>
             </tr>
           <tr> <td>Promos Offered:
                <select name="Promos" id="Promos" class="selectbox">
                   <option value=""> -- ALL --</option>
                <?  foreach($promos as $promo)
		{ if($promo[PROMO]!=null) {?>
                <option value="<?=$promo[PROMO]?>"> <?=$promo[PROMO]?></option>
                <? } }?>
                </select></td>
                <td>
        <input type="button" class="button" align="right" name="Searchfilter" id="Searchfilter" value="Search" onclick="getFilteredData()"/></td>
               </tr>
              
</table>
    <div id="verification_info">
                                </div>
</form>
