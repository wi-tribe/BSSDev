<?php

	include_once('../util_config.php');	
	include_once(UTIL_CLASSDIR."/SmartsUtil.php");
	$smarts_obj = new SmartsUtil();
	

	$csr_id = $_SESSION['brm_csr_id'];
//	print_r($_POST);
	$from_date = $_POST['from_date'];
	$to_date = $_POST['to_date'];
	
	$accounts = $smarts_obj->getBRMCurrentMonthSales($csr_id, $from_date, $to_date);

 ?>
 <table width="100%">
 	<tr>
    	<td colspan="7" align="center" style="font-size:20px"><strong>My Sales</strong></td>
    </tr>
	<tr>
    	<td class="textboxBur" ><strong>Customer ID</strong></td>
        <td class="textboxBur"><strong>Customer Name</strong></td>
        <td class="textboxBur"><strong>Contact Number</strong></td> 
        <td class="textboxBur"><strong>Email Address</strong></td>
        <td class="textboxBur"><strong>Status</strong></td>
        <td class="textboxBur">&nbsp;</td>
    </tr>
	<?	foreach($accounts as $sale)
		{ 
			?>
            <tr>
                <td class="textboxGray"><?=$sale['CUSTOMER_ID']?></td>
                <td class="textboxGray"><?=$sale['FIRST_NAME']?> <?=$sale['LAST_NAME']?></td>
                <td class="textboxGray"><?=$sale['TELEPHONE_NUMBER']?></td>
                <td class="textboxGray"><?=$sale['EMAIL_ADDRESS']?></td>
                <td class="textboxGray"><?=$sale['CUSTOMER_STATUS']?></td>
                <td class="textboxGray" align="center">
                    <a href="#" class= "options" onclick="javascript:window.open('Pages/customerDetails.php?acct_id=<?=$sale['CUSTOMER_ID']?>', '_blank','top=100, left=100,height=520, width=1100, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no');"><img src='../images/icon-details-red.jpg' /></a>
				</td>
            </tr>
            
    <? } ?>
	
 </table>
 <form name="ticket_info" target="_blank" method="post" action="dashboard/tickets.php">
 	<input name="csr_username" type="hidden" value="<?=$sales_rep[0]['csr_username']?>" />
    <input name ="customer_id" type="hidden" value="" />
 </form>