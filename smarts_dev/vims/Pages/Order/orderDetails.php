<?
session_start("VIMS");
set_time_limit(700);

	include('../../vims_config.php');
	$order = new Order();
	$order_id = $_POST['order_id'];
	$orderDetails = $order->getOrder($order_id);
	$orderSeries = $order->getOrderVouchers($order_id);

	if(!$order)
	{
		print $order->error;
		exit;
	}
	$orderDetails = $orderDetails[0];
	
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
    <td><br>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7" class="td">
      <tr>
        <td><p>&nbsp;</p>
              <!-----------------------------THIS PAGE------------------------------------->
			<table width="100%" border="0" cellpading="0" cellspacing="0">
			<tr><td> <div id="FormDiv">
						<form name="CancelOrder" id="CancelOrder" target="_blank" method="post" action="dashboard/test.php">
							<input name ="order_id" id ="order_id" type="hidden" value="<?=$order_id?>" />
							<input name ="order_denomination" id ="order_denomination" type="hidden" value="<?=$orderDetails['order_denomination']?>" />
							<input name ="total_items" id ="total_items" type="hidden" value="<?=$orderDetails['total_items']?>" />			
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
								</tr>
								<tr>
									<td class='orangeText'>Order By</td>
									<td> <?=$orderDetails['username']?></td>
								</tr>
								<tr>
									<td class='orangeText'>Card Price</td>
									<td> <?=$orderDetails['order_denomination']?></td>
								</tr>
								<tr>
									<td class='orangeText'>Total Items</td>
									<td><?=$orderDetails['total_items']?></td>
								</tr>
								<tr>
									<td class='orangeText'>Total Price</td>
									<td> <?=$orderDetails['total_price']?></td>
								</tr>
								<tr>
									<td colspan="2"><span class="textboxBur"  align="center" style="font-size:16px" > <strong>Allocated Voucher Series</strong> </span></td>
								</tr>
								<tr>
									  <td class='orangeText' >Voucher Serial Number Start</td>
									  <td ><?=$orderSeries[0]['voucher_serial']?></td>  
								</tr>
								<tr>
									<td class='orangeText' >Voucher Serial Number End</td>
									<td ><?=$orderSeries[count($orderSeries)-1]['voucher_serial']?></td>
								</tr>
								<tr>
									<td class='orangeText' >Total Items allocated</td>
                                                                        <? if($orderSeries!=null){ ?>
									<td > <?=count($orderSeries) ?> </td>
                                                                        <?}?>
								</tr>
								<tr>
									<td class='orangeText' >Order Status</td>
									<td ><?=$orderDetails['status']?></td>
								</tr>
								<?if( $orderDetails['order_status_id'] == ORDER_REQUEST && $orderDetails['order_from_channel'] = $_SESSION['channel_id'] ){
								?>
								<tr>
									<td></td>
                                                                        <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'cancelorder')) { ?>
									<td align='right' width="100%">
										<input type="button" value="Cancel Order"  onclick="javascript:processForm( 'CancelOrder','FormProcessor/Order/cancelOrder.php','MsgDiv' );"/> 
									</td>
                                                                        <?}?>
								</tr>
								<?}?>
							</table>
						</form>	
					</div>
				</td>
			</tr>
			<tr><td><div  id ="MsgDiv" ></div></td> 
			</tr>
			</table >            
	<!--------------------------------THE END------------------------------------->
		  </td>
      </tr>
    </table>
    </br></td>
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
	
	
	
