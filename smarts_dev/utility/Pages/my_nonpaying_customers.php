<?php
	session_start("VIMS");
        set_time_limit(200);

        //ob_start();
        include_once('../util_config.php');
	$conf=new config();
        include_once(UTIL_CLASSDIR."/SmartsUtil.php");

        $smartsUtil = new SmartsUtil();

        $sales = $smartsUtil->getSalesBySalesRepID($_SESSION['brm_csr_id']);
        //print_R($sales[0]['START_MONTH']);


	

	//PRINT_R($sales);
    //<a href="select_offer.php?keepThis=true&TB_iframe=true&height=540&width=481" class="email thickbox"><img src="images/select_offer.gif" 
	//				alt="View All Offers" title="View All Offers" onmouseout="this.src='images/select_offer.gif'" 
	//				onmouseover="this.src='images/select_offer_on.gif'" border="0"/></a>
 ?>

<table width="100%">
  <tr>
    <td colspan="11" align="center" style="font-size:20px"><strong>Customer Payables</strong></td>
  </tr>
  <tr></tr>
  <tr>
  <td class="textboxBur" ><strong>Starting Month:</strong></td>
  <td class="textboxGray"><?=$sales[0]['START_MONTH']?></td>
  
  <td class="textboxBur" ><strong>Ending Month:</strong></td>
  <td class="textboxGray" ><?=$sales[0]['END_MONTH']?></td>
  </tr>
  <tr></tr>
  <tr>
	<td  class="textboxBur">S/N</td>
	<td  class="textboxBur">Profile</td>
    <td class="textboxBur" align="center" ><strong>Customer ID</strong></td>
    <td class="textboxBur" align="center"><strong>Customer Name</strong></td>
    <td class="textboxBur" align="center"><strong>Customer Status</strong></td>
    <td class="textboxBur" align="center"><strong>Month</strong></td>
    <td class="textboxBur" align="center"><strong>Times Called</strong></td>
    <td class="textboxBur" align="center">Last Called On</td>
	<td class="textboxBur" align="center"><strong>Last Reason Code</strong></td>
	 <td class="textboxBur" align="center"><strong>DUE NOW</strong></td>
         <td class="textboxBur" align="center"><strong>Payment Status</strong></td>


  </tr>
  <?	$index=1;
		foreach($sales as $sale)
		{ ?>
          <tr>
		  <td><?=$index++?></td>
			<td class="textboxGray"><?=$sale['CUSTOMER_PROFILE']?></td>
            <td class="textboxGray"><?=$sale['CUSTOMER_ID']?></td>
            <td class="textboxGray"><?=$sale['FIRST_NAME']?> <?=$sale['LAST_NAME']?></td>
            <td class="textboxGray"><?=$sale['CUSTOMER_STATUS']?> </td>
             <td class="textboxGray"><strong><?=$sale['CURRENT_MONTH']?></strong></td>
            <td class="textboxGray"><?=$sale['TIMES_CALLED']?></td>
            <td class="textboxGray"><?=$sale['LAST_CALLED_ON']?></td>
			<td class="textboxGray"><?=$sale['LAST_REASON_CODE']?></td>
			 <td class="textboxGray"><strong><?=$sale['TOTAL_DUE']?></strong></td>
                         <td class="textboxGray"><strong><?=$sale['PAYMENT_STATUS']?></strong></td>
          <td class="textboxGray" align="center">
				<a href="#" class= "options" onclick="javascript:window.open('./FormProcessor/customer_details.php?rec_id=<?=$sale['REC_ID']?>', '_blank','top=100, left=100,height=520, width=1100, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no');"><img src='../images/icon-details-red.jpg' /></a>
			</td>
          </tr>
  <? } ?>
</table>
 