<?
session_start("VIMS");
set_time_limit(700);

include('../../vims_config.php');
include_once(CLASSDIR."/Replacement.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'repoverride'))
{
	echo "Permission denied";
	exit;
}
$rep_obj = new Replacement();
$ch_obj = new Channel();
$user_obj = new User();
$rep_id = $_POST['rep_id'];
$data = $rep_obj->getrepoveridePendings($rep_id);
$channel_name = $ch_obj->getChannelName($data[0]['by_channel']);
$user = $user_obj->getUserInfo($data[0]['raised_by']);

$vouch_obj = new Voucher();
//$status = $vouch_obj->getVouchStatus($data[0]['voucher_serial']);

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
				<span class="textboxBur"  align="center" style="font-size:16px" > <strong>Replacement Details</strong> </span>

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
						<form name="Repoverride" id="Repoverride" target="_blank" method="post" action="dashboard/test.php">
							<input name ="rep_id" id ="rep_id" type="hidden" value="<?=$rep_id?>" />
                                                        <input name ="voucher_id" id ="voucher_id" type="hidden" value="<?=$data[0]['voucher_id']?>" />
							<table width="100%"  border="0" cellpading="0" cellspacing="0" >
								<tr>
									<td width="30%" class='orangeText'>Voucher ID</td>
									<td width="70%" > <?=$data[0]['voucher_id']?></td>
								</tr>
								<tr>
									<td class='orangeText'>Voucher Serial</td>
									<td> <?=$data[0]['voucher_serial']?></td>
								</tr>
                                                                <tr>
									<td class='orangeText'>Voucher Status</td>
									<td> <?=$status[0]['vouhcer_status']?></td>
								</tr>
								<tr>
									<td class='orangeText'>Ticket Generation Date</td>
									<td> <?=$data[0]['date_added']?></td>
								</tr><tr>
									<td class='orangeText'>Voucher Denomination</td>
									<td> <?=$data[0]['voucher_denomination']?></td>
								</tr>

								<tr>
									<td class='orangeText'>Raised By</td>
									<td> <?=$user[0]['username']?></td>
								</tr>
								<tr>
									<td class='orangeText'>By Channel</td>
									<td> <?=$channel_name?></td>
								</tr>
								<tr>
									<td></td>
									<td align='right' width="100%"><input type="button" value="Allow Replacement"  onclick="javascript:processForm( 'Repoverride','FormProcessor/POS/allowreplacement.php','MsgDiv' );"/>
										<input type="button" value="Decline Replacement"  onclick="javascript:processForm( 'Repoverride','FormProcessor/POS/declinereplacement.php','MsgDiv' );"/>
									</td>
								</tr>
							</table>
						</form>
					</div>
				<td>
			</tr>
			<tr><td><div  id ="MsgDiv" ></div></td>
			</tr>
			</table >
	<!--------------------------------THE END------------------------------------->
		  </td>
      </tr>
    </table>
    <br></td>
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



