<?
session_start("VIMS");
set_time_limit(2000);

	include('../../vims_config.php');
        include_once(CLASSDIR."/Discount.php");
        include_once(CLASSDIR."/Commission.php");
	$order = new Order();
        $dis_obj = new Discount();
        $comm_obj= new Commission();
        
        //permission check
        
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'processorders'))
{
	echo "Permission denied";
	exit;
}


	$order_id = $_POST['order_id'];
	$orderDetails = $order->getOrder($order_id);
        
//	print_r($orderDetails);
	if(!$order)
	{
		print $order->error;
		exit;
	}
	$orderDetails = $orderDetails[0];
        $applied_to;
        $commissions = $comm_obj->getcommissionValue($orderDetails['channel_id'],$orderDetails['channel_type_id'],&$applied_to);
        $discounts = $dis_obj->getallDiscounts($orderDetails['channel_type_id']);
      //  print($applied_to);

        //******************************* Calculating Commission ****************************//
        $comm_amnt = 0;
        if($commissions!=null)
        {
            $comm_amnt = ($orderDetails['total_price']*($commissions[0]['value'])/100);
        }
        
        //***********************************************************************************//
       // print_r($discounts);
        
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<?=$_HTML_LIBS?>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="600" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7">
		<table width="600" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
			<tr>
				<td height="30" colspan="2" background="../images/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;">
				<span class="textboxBur"  align="center" style="font-size:16px" > <strong>ORDER DETAILS</strong> </span>
				
				</td>
			</tr>
		</table>
	</td>
  </tr>
  <tr>
    <td>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7" class="td">
      <tr>
        <td><p>&nbsp;</p>
              <!-----------------------------THIS PAGE------------------------------------->
			<table width="100%" border="0" cellpading="0" cellspacing="0">
			<tr> <!--<div id="FormDiv"> !-->
						<form name="ProcessOrder" id="ProcessOrder" target="_blank" method="post" action="dashboard/test.php">
							<input name ="order_id" id ="order_id" type="hidden" value="<?=$order_id?>" />
							<input name ="order_denomination" id ="order_denomination" type="hidden" value="<?=$orderDetails['order_denomination']?>" />
							<input name ="total_items" id ="total_items" type="hidden" value="<?=$orderDetails['total_items']?>" />
                                                        <input name="commission_id" id="commission_id" type="hidden" value="<?=$commissions[0]['commission_id']?>" />
                                                        <input name="order_type_name" id="order_type_name" type="hidden" value="<?=$orderDetails['order_type_name']?>" />
                                                        <input name="order_date" id="order_date" type="hidden" value="<?=$orderDetails['order_date']?>" />
                                                        <input name="from_channel" id="from_channel" type="hidden" value="<?=$orderDetails['channel_name']?>" />
                                                        <input name="order_by" id="order_by" type="hidden" value="<?=$orderDetails['user_id']?>" />
                                                        <input name="total_price" id="total_price" type="hidden" value="<?=$orderDetails['total_price']?>" />
                                                        <input name="commisssion_value" id="commisssion_value" type="hidden" value="<?=$commissions[0]['value']?>" />
                                                        <input name="commisssion_amount" id="commisssion_amount" type="hidden" value="<?=$comm_amnt?>" />

							<table width="100%"  border="0" cellpading="0" cellspacing="0" >
								<tr>
									<td width="30%" class='orangeText'>Order ID</td>
									<td width="70%" > <?=$orderDetails['order_id']?></td>
								</tr>
								<tr>
									<td class='orangeText'>Order Type</td>
									<td> <?=$orderDetails['order_type_name']?></td>
								</tr>
								<tr>
									<td class='orangeText'>Order Date</td>
									<td> <?=$orderDetails['order_date']?></td>
								</tr><tr>
									<td class='orangeText'>From Channel</td>
									<td> <?=$orderDetails['channel_name']?></td>
								</tr>
								
								<tr>
									<td class='orangeText'>Order By</td>
									<td> <?=$orderDetails['username']?></td>
								</tr>
								<tr>
									<td class='orangeText'>Card Price</td>
									<td> <?=$orderDetails['order_denomination']?> PKR</td>
								</tr>
								<tr>
									<td class='orangeText'>Total Items</td>
									<td> <?=$orderDetails['total_items']?></td>
								</tr>

								<tr>
									<td class='orangeText'>Total Price</td>
									<td> <?=$orderDetails['total_price']?> PKR</td>
								</tr>
								<tr>
									<td class='orangeText'>Commission Amount</td>
									<td> <?=$comm_amnt?> PKR 
										<? if($commissions!=null)
										{ 
										  print "( Rule:".$applied_to." - Value:".$commissions[0]['value']."% )";
										} ?>
									 </td>
								</tr> 
								<tr>
										<td class='orangeText'>Discount</td>
										<td>
										<select name='discount' id='discount' class="selectbox" onchange="javascript:processForm( 'ProcessOrder','FormProcessor/Discounts/calculatediscount.php','discountdiv' ); "  >
												<option value=""> -- Select Discount -- </option>
												<? if($discounts!=null) { foreach($discounts as $arr) { ?>
												<option value="<?=$arr['discount_id']?>"><?=$arr['discount_name']?></option>
												<?  } } ?>
										</select>
										</td>
                                                                                
										
								</tr>
								<tr>
                                                                   <td class='orangeText'>Discount amount</td>
									<td>
										<span name="discountdiv" id="discountdiv">0</span> PKR
									</td>
								</tr>
                                     
                                                                <tr>
                                                                        <td class='orangeText'>Net Amount</td>
									<td> <span name="netamount" id="netamount">0</span> PKR</td>
                                                                </tr>
                                                                 <tr>
                                                                            <td class='orangeText'>Payment Mode</td>
                                                                            <td>
										<select name='payment_mode' id='payment_mode' class="selectbox" >
												<option value=""> -- Select Payment  Mode-- </option>
												<option value="CASH">CASH</option>
												<option value="CHEQUE">CHEQUE</option>
										</select>
										</td>
								</tr>
                                                            <tr>
                                                                         <td class='orangeText' >Payment Discription</td>
                                                                         <td><textarea name="payment_descrition" id="payment_descrition"></textarea></td>
                                                            </tr>
								<tr>
									<td colspan="2"><span class="textboxBur"  align="center" style="font-size:16px" > <strong>ASSIGN VOUCHERS</strong> </span></td>
								</tr>
								<tr>
									  <td class='orangeText' >Voucher Serial Number Start</td>
									  <td ><input class='textboxBur' type='text' name='vouch_serial_start' id='vouch_serial_start' /></td>
									  
								</tr>
								<tr>
									<td class='orangeText' >Voucher Serial Number End</td>
									<td ><input class='textboxBur' type='text' name='vouch_serial_end' id='vouch_serial_end' /></td>
								</tr>
								<tr>
									<td></td>
									<td align='right' width="100%"><input type="button" value="Process Order"  onclick="javascript:processForm( 'ProcessOrder','FormProcessor/Order/processOrder.php','MsgDiv' );"/>
										<input type="button" value="Reject Order"  onclick="javascript:processForm( 'ProcessOrder','FormProcessor/Order/rejectOrder.php','MsgDiv' );"/> 
									</td>
								</tr>
							</table>
						</form>	
					<!--</div>!-->
			</tr>
			<tr><td><div  id ="MsgDiv" ></div></td> 
			</tr>
			</table >            
	<!--------------------------------THE END------------------------------------->
		  </td>
      </tr>
    </table>
    </td>
  </tr>
  	<tr>
		<td>
		 <table width="600" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
		  <tr>
			<td align="center" valign="middle" background="../images/nav-rep.png" style=" background-repeat:repeat-x; height:50px;"><a href="#">&copy; wi-tribe Pakistan Limited. All Rights Reserved &reg;</a></td>
		  </tr>
		  </table>
		</td>
	</tr>
</table>
</body>
</html>	

<script>

  function calculateNetAmount()
  {
   $('#netamount').text(<?=$orderDetails['total_price']?> - <?=$comm_amnt?> - $('#discountdiv').text());
  }
  calculateNetAmount();
</script>



	
