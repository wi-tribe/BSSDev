<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Voucher.php");
//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'viewLostInventory'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$voucher=new Voucher();
$channel_obj=new Channel();

$shops = $channel_obj->getAllLostInventoryShops();

?>
<script type="text/javascript">
function getLostVouchers()
{
    var shop = document.getElementById('LostShop').value;
    var serial_search_start = document.getElementById('serial_search_start').value;
    var serial_search_end = document.getElementById('serial_search_end').value;
     var post_func = function()
				{
					var effect = function(data){ $("#Vouchers").html(data).show("normal"); 	};
					$.post('FormProcessor/Voucher/getLostVouchers.php',{shop:shop,serial_search_start:serial_search_start,serial_search_end:serial_search_end} ,effect);
				}
	$("#Vouchers").html("<img src='../../images/loading.gif' /> Loading...");
	$("#Vouchers").show("normal",post_func);
}    
</script>
	<table align="center" width="90%" border=0>
		<tr valign='top' class='textbox' >
			<td>
			  <table width="100%" border="0" cellspacing="1" cellpadding="1">
				  <tr>
				    <td colspan="2" align="center" class="higlight">View Lost Inventory</td>
			    </tr>
		    </table></td>
		</tr>
		<tr valign='top' class='textbox' >
		  <td align="center">
              <form name='LostVouchers' id='LostVouchers' method='post' action=''>
          <!-- List vouchers -->
          <table width="75%" border="0" cellspacing="1" cellpadding="2">
            <tr>
               <td align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td bgcolor="#EFEFEF">Lost Inventory Shops
                <select name="LostShop" id="LostShop" class="selectbox" onchange="getLostVouchers();">
                <option value="">Select Lost Shop</option>
                <?PHP if(!empty($shops)) { for($i=0; $i< sizeof($shops);$i++) { ?>
                <option value="<?=$shops[$i]['channel_id']?>"><?=$shops[$i]['channel_name']?></option>
				<? } } ?>
              </select>
              </td>
              <td bgcolor="#EFEFEF">&nbsp;</td>
            </tr>
            <tr><td align="center" bgcolor="#EFEFEF">&nbsp;</td>
                <td bgcolor="#EFEFEF">Serial Start#:
                <input class="textboxBur" type="text" name="serial_search_start" id="serial_search_start" />
                <td bgcolor="#EFEFEF">Serial End#:
                <input class="textboxBur" type="text" name="serial_search_end" id="serial_search_end" />
                <input type="button" name="button" id="button" value="Go" onClick="getLostVouchers();"/></td>
            </tr>

          </table>
          <div id="Vouchers"></div>
          <!-- end listing vouchers -->
		  </form></td>
  </tr>
	</table>

