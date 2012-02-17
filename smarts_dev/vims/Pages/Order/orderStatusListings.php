<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

session_start("VIMS");
set_time_limit(700);
include_once('../../vims_config.php');
include_once(CLASSDIR."/Order.php");

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'listorders'))
{
	echo "Permission denied";
	exit;
}

$order_obj = new Order();
$orderdetail = $order_obj->getOrdersByChannel($_SESSION['channel_id']);

foreach($orderdetail as $arr)
    {
        $statusdesc[] = $order_obj->getOrderstatusdesc($arr['order_status_id']);
    }
?>

<table width="100%">
  <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>Order Status</strong></td>
  </tr>
  <tr>
	<td  class="textboxBur">S/N</td>
    <td class="textboxBur" align="center" ><strong>Order ID</strong></td>
    <td class="textboxBur" align="center"><strong>Order By</strong></td>
    <td class="textboxBur" align="center"><strong>Order Date</strong></td>
	<td class="textboxBur" align="center"><strong>Order Denomination</strong></td>
	<td class="textboxBur" align="center"><strong>Total Items</strong></td>
        <td class="textboxBur" align="center"><strong>Total Price</strong></td>
	<td class="textboxBur" align="center"><strong>Order Status</strong></td>
  </tr>

    <?	$index=1;
        $array = 0;
		foreach($orderdetail as $arr)
		{ ?>
          <tr>
			<td><?=$index++?></td>
			<td class="textboxGray"><?=$arr['order_id']?></td>
			<td class="textboxGray"><?=$arr['order_by']?></td>
			<td class="textboxGray"><?=$arr['order_date']?></td>
			<td class="textboxGray"><?=$arr['order_denomination']?></td>
			<td class="textboxGray"><?=$arr['total_items']?> </td>
			<td class="textboxGray"><?=$arr['total_price']?> </td>
            <td class="textboxGray"><?=$statusdesc[$array][0]['order_status_name']?></td>
			<td class="textboxGray" align="center">
				<a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Order/orderDetails.php','order_id=<?=$arr['order_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Details</a>
			</td>
          </tr>
  <? $array++;

  ?>
  <? } ?>

	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>

