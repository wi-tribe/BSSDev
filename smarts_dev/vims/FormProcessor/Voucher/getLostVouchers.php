<?php
session_start("VIMS");
set_time_limit(2000);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Voucher.php");

$voucher_obj = new Voucher();

$shop = $_POST['shop'];
$serial_search['serial_search_start'] = $_POST['serial_search_start'];
$serial_search['serial_search_end'] = $_POST['serial_search_end'];

$vouchers = $voucher_obj->getLostShopInventory($shop,$serial_search);
?>

<?php  if(!empty($vouchers)) { ?>
<table width="75%" border="0" cellspacing="1" cellpadding="2">
			<tr>
              <td width="13%" align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td bgcolor="#EFEFEF">Voucher (Serial #s)</td>
              <td bgcolor="#EFEFEF">&nbsp;</td>
              <td bgcolor="#EFEFEF">Voucher (Serial #s)</td></tr>
              <tr>
              <td><input type="checkbox" name="Check_All" value="Check_All" onClick="CheckAll(document.LostVouchers.voucher_serial)"></td>
              <td bgcolor="#EFEFEF">Check All Vouchers</td>
               <td width="20%" align="center" bgcolor="#EFEFEF">Selected Voucher Counter</td>
              <td bgcolor="#EFEFEF"><div id="vcount"></div></td>
            </tr>
<? for($i=0; $i< sizeof($vouchers);$i++) { ?>
            <tr>
              <td width="13%" align="center">
              <input type="checkbox" name="voucher_serial[]" id="voucher_serial" value="<?=$vouchers[$i]['voucher_serial']?>" onclick="anyCheck()" /></td>
              <td width="37%" align="left"><?=$vouchers[$i++]['voucher_serial']?></td>
              <td width="13%" align="center">
              <? if(!empty($vouchers[$i]['voucher_serial'])) { ?>
              <input type="checkbox" name="voucher_serial[]" id="voucher_serial" value="<?=$vouchers[$i]['voucher_serial']?>" onclick="anyCheck()" /><? } ?></td>
              <td width="37%" align="left"><?=$vouchers[$i]['voucher_serial']?></td>
            </tr>
            <?php } }
            else
                if($vouchers==null)
                echo "No Inventory found!!";
                exit();?>
</table>

<script type="text/JavaScript">
function anyCheck()
{
 var total=0;
var max = LostVouchers.voucher_serial.length;
for(var idx = 0; idx < max; idx++)
{
if(eval("document.LostVouchers.voucher_serial[" + idx + "].checked") == true)
{
total += 1;
}
}
if(total<2)
    document.getElementById('vcount').innerHTML=total+' voucher';
else
    document.getElementById('vcount').innerHTML=total+' vouchers';
}

function CheckAll(chk)
{
var checkon = 0;
if(document.LostVouchers.Check_All.checked==false)
  {
    for (i = 0; i < chk.length; i++)
    {
        chk[i].checked = false ;
    }
    checkon=0;
  }

  if(document.LostVouchers.Check_All.checked==true)
  {
    for (i = 0; i < chk.length; i++)
    {
        chk[i].checked = true ;
        checkon +=1;
    }
  }
  document.getElementById('vcount').innerHTML= checkon+' vouchers';
}
</script>