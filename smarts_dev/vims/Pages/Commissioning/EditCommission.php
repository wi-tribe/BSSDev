<?
session_start("VIMS");
set_time_limit(700);

include('../../vims_config.php');
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Commission.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'commissionadd'))
{
	echo "Permission denied";
	exit;
}

//$channel_obj = new Channel();
//$geotypes = $channel_obj->getAllChannelTypes();
//$regions = $channel_obj->getRegions();


$commission_obj = new Commission();
//$commission_id =
$commission_details = $commission_obj->getcommissionDetails($_POST['commission_id']);
$channel_obj = new Channel();

////*********** Getting Current Rule Related Information *******************//
$channel_type_info = $channel_obj->getChannelTypeDetail($commission_details[0]['channel_type_id']);

if($commission_details[0]['channel_id']!=null)
{
 $rule_channel = $channel_obj->getChannelDetails($commission_details[0]['channel_id']);
}

if($commission_details[0]['zone_id']!=null)
{
    $zone = $channel_obj->getLocationDetails($commission_details[0]['zone_id']);
}
if($commission_details[0]['city_id']!=null)
{
    $city = $channel_obj->getLocationDetails($commission_details[0]['city_id']);
}
if($commission_details[0]['region_id']!=null)
{
    $region = $channel_obj->getLocationDetails($commission_details[0]['region_id']);
}
 
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
				<span class="textboxBur"  align="center" style="font-size:16px" > <strong>Commision Rule Update</strong> </span>
				</td>
			</tr>
		</table>

    <table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                          <form name="commissionEdit" id="commissionEdit"  method="post" action="FormProcessor/Commissioning/EditCommission.php">
                            <table width="200" border="0" cellspacing=0" cellpadding="0">
                                <input type="hidden" name="commission_id" id="commission_id" value="<?=$_POST['commission_id']?>"/>
                            <tr>
                              <td> Channel Type:</td>
                              <td>
                                 <?=$channel_type_info[0]['channel_type_name']?>
                              </td>
                            </tr>
                            <tr>
                              <td>Commission Value:</td>
                              <td><?=$commission_details[0]['value']." %"?></td>
                            </tr>
                            <tr>
                              <td>Region:</td>
                              <td><?=$region[0]['location_name']?></td>
                            </tr>
                             <tr>
                                 <td> City:</td>
                             <td><?=$city[0]['location_name']?> </td>
                            </tr>
                             <tr>
                                 <td> Zone:</td>
                                 <td><?=$zone[0]['location_name']?> </td>
                            </tr>
                             <tr>
                                 <td> Channel:</td>
                                 <td><?=$rule_channel[0]['channel_name']?>    </td>
                            </tr>
                                <tr>
                                    <td>Status:</td>
                                    <td>
                                  <select name="status" id="status" class="selectbox" >
                                 <?php
                                    foreach($commission_details as $arr) {
                                        if($arr['status']==STATUS_ACTIVE) {?>
                                    <option selected="selected" value="<?=$arr['status']?>">Active</option>
                                    <? } ?>
                                    <option value="<?=STATUS_INACTIVE?>">InActive</option>
                                    <? } ?>
                                </select>
                                    </td>
                                </tr>
                          </table>
                            <table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align='right'>
                                  <input name="action" type='submit' value='Update' /></td>
				</tr>
			  </table>
                        </form>
              </td>
		</tr>
	</table>
    </td>
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





