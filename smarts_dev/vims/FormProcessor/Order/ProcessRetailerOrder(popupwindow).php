<?
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

////check permission
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'poslist'))
//{
//	echo "Permission denied";
//	exit;
//}

//creating new object
$pos=new POS();
$vouchers= new Voucher();
$users = new User();
$channels = new channel();

$vData_=false;

if($_POST['se']==null)
{
    print("Please Select an SE First");
    exit();
}
if(($vData=$vouchers->getVoucherDenominations()))
{
	$whData_=true;
}


//print_r();



//get wh data
//$vData=$vouchers->getUserVouchers($_POST['se'],$_SESSION['channel_id'],$_POST['voucher_denomination'],$_POST['voucher_serial_search']);
//if($vData)
//{
//	$whData_=true;
//}

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
				<span class="textboxBur"  align="center" style="font-size:16px" > <strong>USER VOUCHERS</strong> </span>
				</td>
			</tr>
		</table>

    <table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                        <form name="UserVouchersPopup" id="UserVouchersPopup" method="post" action="FormProcessor/User/UserEdit.php">
                            <input type="hidden" name="se" id="se" value="<?=$_POST[se]?>"/>
                            <input type="hidden" name="se_channel_id" id="se_channel_id" value="<?=$se_details[0]['user_channel_id']?>"/>
                            
                          <table width="447" border="0" cellspacing=0" cellpadding="0">
                               <tr>
              <td align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td colspan="3" align="center" bgcolor="#EFEFEF">Face Value
                <select class="selectbox" name="voucher_denomination" id="voucher_denomination" onchange="javascript:processForm( 'UserVouchersPopup','Pages/Order/ajax_processRetailerOrder.php','POS' );">
                <option value="">Select Face Value</option>
                <?PHP if(!empty($vData)) { for($i=0; $i< sizeof($vData);$i++) { ?>
                <option value="<?=$vData[$i]['voucher_denomination']?>"><?=$vData[$i]['voucher_denomination']?></option>
				<? } } ?>
              </select>
              </td>
            </tr>
            </table>
        </form>
              <div id="POS"></div>
              </td>
		</tr>
	</table>
    </td>
</tr>
</table>

</body>
</html>



