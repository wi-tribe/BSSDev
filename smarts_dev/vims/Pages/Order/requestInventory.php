<?
session_start("VIMS");
set_time_limit(700);

	include_once('../../vims_config.php');
        include_once(CLASSDIR."/Channel.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'reqinventory'))
{
	echo "Permission denied";
	exit;
}

	$order = new Order();
        $channel = new Channel();
	$prices = $order->getCardPrices();
	$channel_name = $channel->getChannelName($_SESSION['channel_id']);
	
?>	
	<table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
               <form name="RequestInventory" id="RequestInventory" method="post" action="">
                  <table width="320" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>Order Date : </td>
                      <td><?=date('Y-d-m')?></td>
                    </tr>
                    <tr>
                      <td>Order By</td>
                      <td><?=$_SESSION['username']?></td>
                    </tr>
                    <tr>
                      <td>Channel Name</td>
                      <td><?=$channel_name?></td>
                    </tr>
					<tr>
                      <td>Card Price (Denomination)</td>
                      <td>
					  <select name='v_price' id='v_price' onchange="calculate_price()">
						<option value=""> --select-- </option>
						<?foreach($prices as $price){?>
						<option value='<?=$price['voucher_denomination']?>'><?=$price['voucher_denomination']?></option>
						<?}?>
					  </select> PKR
                    </tr>
		     <tr>
                      <td>Quantity</td>
                      <? $granule = 10; $items=$granule;?>
                      <td><select name='total_items' id='total_items' onchange="calculate_price()">
						<option value=""> --Quantity-- </option>
						<? while($items <=10000){  ?>
						<option value='<?=$items?>'><?=$items?></option>
						<?  $items=$items+$granule; }?>
					  </select>
                      </td>
                    </tr>
                    <tr>
                      <td>Total Price</td>
                      <td><div id="t_price"></div></td>
                    </tr>
                    <tr>
                      <td>Order Description</td>
                      <td><textarea name="order_desc" id="order_desc"></textarea></td>
                    </tr>
                    <tr>
                      <td></td>
                      <td><input name="FormAction" type='button' value='Place Order' onclick="javascript:processForm( 'RequestInventory','FormProcessor/Order/requestInventory.php','MsgDiv' );"></td>
                    </tr>
                  </table>
                </form>
               <!--------------------------------THE END------------------------------------->
			</td>
  </tr>
	</table>

<script type="text/javascript">

function calculate_price()
{
    var price=0; var items=0; var total=0;
    var vp = document.getElementById("v_price");
    price = vp.options[vp.selectedIndex].value;

    var t_items = document.getElementById("total_items");
    items = t_items.options[t_items.selectedIndex].value;

    total = items * price;
    
    document.getElementById('t_price').innerHTML= total;
}

</script>