<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'processretailerorder'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$pos=new POS();
$vouchers = new Voucher();

$users = new User();
$vData_=false;
$marked_series;
$vcount=0;
//print_r($_POST);

$marked_series = $_POST['manual_series_show'];
$denomination = $_POST['denomination_'.$marked_series];
$voucher_serial = "voucher_serial_".$marked_series;

    if($_POST['SE']!=null)
    {
    $se_details = $users->getUserDetailInfo($_POST['SE']);
    $vData=$vouchers->getUserVouchers($_POST['SE'],$se_details[0]['user_channel_id'],$_POST['denomination_'.$marked_series]);

    if($vData==null)
     {
        Print("No Vouchers Found!!!");
        exit();
     }
    }

if($vData)
{
	$whData_=true;
}
else
    return false;
?>
<table width="100%">
<tr>
              <td bgcolor="#EFEFEF">&nbsp;</td>
              <td bgcolor="#EFEFEF">Voucher Denomination:<?=$denomination?></td>

                <td bgcolor="#EFEFEF">Selected Voucher Counter</td>
              <td bgcolor="#EFEFEF"><div id="vcount_<?=$marked_series?>"></div></td>
</tr>
			<tr>
              <td bgcolor="#EFEFEF">&nbsp;</td>
              <td bgcolor="#EFEFEF">Voucher (Serial #s)</td>
              <td bgcolor="#EFEFEF">&nbsp;</td>
              <td bgcolor="#EFEFEF">Voucher (Serial #s)</td></tr>
                                      
<?php  if(!empty($vData)) { for($i=0; $i< sizeof($vData);$i++) { ?>
            <tr>
              <td bgcolor="#EFEFEF">
              <input type="checkbox" name="<?=$voucher_serial?>[]" id="<?=$voucher_serial?>" value="<?=$vData[$i]['voucher_serial']?>" onclick="anyCheck(this.id)" /></td>
              <td ><?=$vData[$i++]['voucher_serial']?></td>

              <td align="right">
              <? if(!empty($vData[$i]['voucher_serial'])) { ?>
              <input type="checkbox" name="<?=$voucher_serial?>[]" id="<?=$voucher_serial?>" value="<?=$vData[$i]['voucher_serial']?>" onclick="anyCheck(this.id)" />
                  <? } ?></td>
              <td ><?=$vData[$i]['voucher_serial']?></td>
            </tr>
            <?php } } ?>
</table>
