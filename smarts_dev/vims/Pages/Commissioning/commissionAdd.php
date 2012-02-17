<?
session_start("VIMS");
set_time_limit(700);

include('../../vims_config.php');
include_once(CLASSDIR."/Channel.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'commissionadd'))
{
	echo "Permission denied";
	exit;
}

$channel_obj = new Channel();
$geotypes = $channel_obj->getAllChannelTypes();
$regions = $channel_obj->getRegions();

 
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
				<span class="textboxBur"  align="center" style="font-size:16px" > <strong>Commision Rule Addition</strong> </span>

				</td>
			</tr>
		</table>

    <table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                          <form name="commissionAdd" id="commissionAdd"  method="post" action="FormProcessor/Commissioning/ajax_commissionAdd.php">
                            <table width="447" border="0" cellspacing=0" cellpadding="0">
                            <tr>
                                <td width="145"><label> Select Channel Type:</label></td>
                              <td width="302"><label>
                                  <select name='channel_type_id' id='channel_type_id'class="selectbox" >
				 <option value=""> -- Channel Type -- </option>
                                 <? foreach($geotypes as $arr) { if($arr['channel_type_name']=='Retailer') {?>
                                 <option value="<?=$arr['channel_type_id']?>"><?=$arr['channel_type_name']?></option>
                                 <? } }?>
                                </select>
                              </label></td>
                            </tr>
                            <TD><PRE> </PRE></TD>
                            <tr>
                              <td><label>Commission Value:</label>
                              &nbsp;</td>
                              <td><label>
                              <select name='commission' id='commission' class="selectbox">
						<option value=""> --Commission-- </option>
                                                <? $granule = 0.5; $comm=1;?>
						<? while($comm <=50.00){  ?>
						<option value='<?=($comm)?>'><?=$comm?>%</option>
						<?  $comm=$comm+$granule; }?>
					  </select>
                              </label></td>
                            </tr>
                            <TD><PRE> </PRE></TD>
                             <tr>
                                 <td id="region_text">Region:</td>
                              <td>
                                  <select name="region_id" id="region_id" class="selectbox"  onchange="javascript:processForm( 'commissionAdd','FormProcessor/Commissioning/populatecity.php','citydiv' );" >
				<option value="">--Select Region--</option>
				<?php
                                    foreach($regions as $arr) { ?>
                                    <option value="<?=$arr['location_id']?>"><?=$arr['location_name']?> </option>
                                    <? } ?>
                                </select>
                              </td>
                            </tr>
                            <TD><PRE> </PRE></TD>
                             <tr>
                                 <td id="city_text" > City:</td>
                             <td><div name="citydiv" id="citydiv">
                                 <select name="city_id" id="city_id" class="selectbox"  onchange="javascript:processForm( 'commissionAdd','FormProcessor/Commissioning/populatezone.php','zonediv' );"  >
				<option value="">--Select City--</option>
                                </select>
                                      </div>
                              </td>
                            </tr>
                            <TD><PRE> </PRE></TD>
                             <tr>
                                 <td id="zone_text"> Zone:</td>
                                 <td><div name="zonediv" id="zonediv">
                                 <select name="zone_id" id="zone_id" class="selectbox"  onchange="javascript:processForm( 'commissionAdd','FormProcessor/Commissioning/populatechannel.php','channeldiv' );" >
				<option value="">--Select Zone--</option>
                                </select>
                                      </div>
                              </td>
                            </tr>
                            <TD><PRE> </PRE></TD>
                             <tr>
                                 <td id="channel_text" > Channel:</td>
                                 <td><div name="channeldiv" id="channeldiv">
                                 <select name="channel_id" id="channel_id" class="selectbox"  >
				<option value="">--Select Channel--</option>
                                </select>
                                      </div>
                              </td>
                            </tr>
                          </table>
                            <table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align='right'>
                                  <input name="action" type='submit' value='Add' /></td>
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





