<?
session_start("VIMS");
set_time_limit(700);
	include_once('../../vims_config.php');
	$order = new Order();

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'processorders'))
{
	echo "Permission denied";
	exit;
}

	$status_list[0] = ORDER_REQUEST;
	$status_list[1] = ORDER_TYPE_RETURN;
        $status_list[2] = ORDER_REJECT;
	$requests = $order->getOrdersToChannel($_SESSION['channel_id'],$_SESSION['user_id'],$status_list);
?>	

<script>

	
</script>
<table width="100%">
  <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>Requested Inventory</strong></td>
  </tr>
  <tr>
	<td  class="textboxBur">S/N</td>
    <td class="textboxBur" align="center" ><strong>Order ID</strong></td>
    <td class="textboxBur" align="center"><strong>order Date</strong></td>
    <td class="textboxBur" align="center"><strong>Order By</strong></td>
     <td class="textboxBur" align="center"><strong>Order Status</strong></td>
    <td class="textboxBur" align="center"><strong>Order Type</strong></td>
	<td class="textboxBur" align="center"><strong>Quantity</strong></td>
	<td class="textboxBur" align="center"><strong>Card Price</strong></td>
	<td class="textboxBur" align="center">Total Price</td>
	<td class="textboxBur" align="center"><strong>Description</strong></td>


  </tr>
  <?	$index=1;
		foreach($requests as $order)
		{ ?>
        <? if($order['order_status_id']==ORDER_REQUEST && $order['order_type']=='Inventory Request') {?>
          <tr>
		  <td><?=$index++?></td>
            <td class="textboxGray"><?=$order['order_id']?></td>
            <td class="textboxGray"><?=$order['order_date']?></td>
            <td class="textboxGray"><?=$order['order_by']?></td>
             <td class="textboxGray"><?=$order['order_status']?></td>
	      <td class="textboxGray"><?=$order['order_type']?></td>
			<td class="textboxGray"><?=$order['total_items']?></td>
			 <td class="textboxGray"><strong><?=$order['order_denomination']?> PKR</strong></td>
			 <td class="textboxGray"><?=$order['total_price']?> PKR</td>
			 <td class="textboxGray"><?=$order['order_desc']?></td>
            <td class="textboxGray" align="center">
                                    <a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Order/processOrder.php','order_id=<?=$order['order_id']?>','top=100, left=100,height=500, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Details</a>
                    </td>
          </tr>
            <? }
                   else if(($order['order_status_id']==ORDER_REJECT && $order['last_updated_by']!=$_SESSION['user_id']) ||$order['order_type']=='Inventory Return') {?>
            <tr>
		  <td><?=$index++?></td>
                  <td class="textboxGray"><?=$order['order_id']?></td>
                  <td class="textboxGray"><?=$order['order_date']?></td>
                  <td class="textboxGray"><?=$order['order_by']?></td>
                  <td class="textboxGray"><?=$order['order_status']?></td>
                  <td class="textboxGray"><?=$order['order_type']?></td>
                  <td class="textboxGray"><?=$order['total_items']?></td>
                  <td class="textboxGray"><strong><?=$order['order_denomination']?> PKR</strong></td>
                  <td class="textboxGray"><?=$order['total_price']?> PKR</td>
                  <td class="textboxGray"><?=$order['order_desc']?></td>
                  <td class="textboxGray" align="center">
                                    <a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Order/recieveOrder.php','order_id=<?=$order['order_id']?>','top=100, left=100,height=500, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Details</a>
                                    </td>
          </tr>
                                <? } ?>
		

  <? } ?>
  
	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>

