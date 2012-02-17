<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'poslist'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$pos=new POS();

$vData_=false;


//get wh data
if(($vData=$pos->myVouchers()))
{
	$whData_=true;
}

?>
<table width="75%" border="0" cellspacing="1" cellpadding="2">
			<tr>
              <td width="13%" align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td bgcolor="#EFEFEF">Voucher (Serial #s)</td>
              <td bgcolor="#EFEFEF">&nbsp;</td>
              <td bgcolor="#EFEFEF">Voucher (Serial #s)</td></tr>
              <tr>
              <td><input type="checkbox" name="Check_All" value="Check_All" onClick="CheckAll(document.POSForm.voucher_serial)"></td>
              <td bgcolor="#EFEFEF">Check All Vouchers</td>
               <td width="20%" align="center" bgcolor="#EFEFEF">Selected Voucher Counter</td>
              <td bgcolor="#EFEFEF"><div id="vcount"></div></td>
            </tr>
<?php  if(!empty($vData)) { for($i=0; $i< sizeof($vData);$i++) { ?>
            <tr>
              <td width="13%" align="center">
              <input type="checkbox" name="voucher_serial[]" id="voucher_serial" value="<?=$vData[$i]['voucher_serial']?>" onclick="anyCheck()" /></td>
              <td width="37%" align="left"><?=$vData[$i++]['voucher_serial']?></td>
              <td width="13%" align="center">
              <? if(!empty($vData[$i]['voucher_serial'])) { ?>
              <input type="checkbox" name="voucher_serial[]" id="voucher_serial" value="<?=$vData[$i]['voucher_serial']?>" onclick="anyCheck()" /><? } ?></td>
              <td width="37%" align="left"><?=$vData[$i]['voucher_serial']?></td>
            </tr>
            <?php } } ?>
</table>

<script type="text/JavaScript">
function anyCheck()
{
 var total=0;
var max = POSForm.voucher_serial.length;
for(var idx = 0; idx < max; idx++)
{
if(eval("document.POSForm.voucher_serial[" + idx + "].checked") == true)
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
if(document.POSForm.Check_All.checked==false)
  {
    for (i = 0; i < chk.length; i++)
    {
        chk[i].checked = false ;
    }
    checkon=0;
  }

  if(document.POSForm.Check_All.checked==true)
  {
    for (i = 0; i < chk.length; i++)
    {
        chk[i].checked = true ;
        checkon +=1;
    }
  }
  document.getElementById('vcount').innerHTML= checkon+' vouchers';
}

function confirmmarkSold()
{
var r=confirm("Press OK to Sell / Press Cancel !");
if (r==true)
  {
   javascript:processForm( 'POSForm','FormProcessor/POS/pos.php','MsgDiv' ); 
  }
}


</script>