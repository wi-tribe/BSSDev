<?
session_start("VIMS");
set_time_limit(700);

include('../../vims_config.php');
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Discount.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'discountadd'))
{
	echo "Permission denied";
	exit;
}

$channel_obj = new Channel();
$dis_obj = new Discount();
$discount_types = $dis_obj->getalldiscTypes();
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
    
<script type="text/JavaScript">
  function showhidefield(id1,id2)
  {
      document.getElementById(id1).style.visibility = "visible";
      document.getElementById(id2).style.visibility = "visible";
  }
</script>
    
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <table width="600" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7">

		<table width="600" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
			<tr>
				<td height="30" colspan="2" background="../images/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;">
				<span class="textboxBur"  align="center" style="font-size:16px" > <strong>Discount Rule Addition</strong> </span>

				</td>
			</tr>
		</table>

    <table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                          <form name="discountAdd" id="discountAdd"  method="post" action="FormProcessor/Discounts/ajax_discountAdd.php">
                            <table width="447" border="0" cellspacing=0" cellpadding="0">

                            <tr>
                                 <td >Discount Name: </td>
				 <td ><input type='text' name='discount_name' id='discount_name' /></td>
                            </tr>
                            <TD><PRE> </PRE></TD>
                            <tr>
                                <td width="145"><label> Select Channel Type:</label></td>
                              <td width="302"><label>
                                  <select name='channel_type_id' id='channel_type_id'class="selectbox" onchange="showhidefield('region_text','region_id')" >
				 <option value=""> -- Channel Type -- </option>
                                 <? foreach($geotypes as $arr) {?>
                                 <option value="<?=$arr['channel_type_id']?>"><?=$arr['channel_type_name']?></option>
                                 <? } ?>
                                </select>
                              </label></td>
                            </tr>
                            <TD><PRE> </PRE></TD>
                            <tr>
                              <td><label>Discount Type:</label>
                              &nbsp;</td>
                              <td><label>
                              <select name='discount_type_id' id='discount_type_id' class="selectbox" onchange="javascript:processForm( 'discountAdd','FormProcessor/Discounts/populatediscounts.php','discountdiv' );"  >
						<option value=""> --Discount Type-- </option>
                                                <? foreach($discount_types as $arr) { ?>
						<option value="<?=$arr['discount_type_id']?>"><?=$arr['discount_type_name']?> </option>
						<?  } ?>
					  </select>
                              </label></td>
                            </tr>
                            <TD><PRE> </PRE></TD>
                            <tr>
                              <td><label>Discount Value:</label>
                              &nbsp;</td>
                              <td><div name="discountdiv" id="discountdiv">
                              <select name='discount' id='discount' class="selectbox">
				<option value=""> --Discount-- </option>
                              </select></div>
                              </td>
                            </tr>
                            
                           <TD><PRE> </PRE></TD>
                             <tr>
                                 <td id="region_text" style="visibility: hidden">Region:</td>
                              <td>
                                  <select name="region_id" id="region_id" class="selectbox" style="visibility: hidden"  onchange="javascript:processForm( 'discountAdd','FormProcessor/Discounts/populatecity.php','citydiv' );" onclick="showhidefield('city_text','city_id')" >
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
                                 <td id="city_text" style="visibility: hidden"> City:</td>
                             <td><div name="citydiv" id="citydiv">
                                 <select name="city_id" id="city_id" class="selectbox" style="visibility: hidden" onchange="javascript:processForm( 'discountAdd','FormProcessor/Discounts/populatezone.php','zonediv' );" onclick="showhidefield('zone_text','zone_id')" >
				<option value="">--Select City--</option>
                                </select>
                                      </div>
                              </td>
                            </tr>
                            <TD><PRE> </PRE></TD>
                             <tr>
                                 <td id="zone_text" style="visibility: hidden"> Zone:</td>
                                 <td><div name="zonediv" id="zonediv">
                                 <select name="zone_id" id="zone_id" class="selectbox" style="visibility: hidden" onchange="javascript:processForm( 'discountAdd','FormProcessor/Discounts/populatechannel.php','channeldiv' );" onclick="showhidefield('channel_text','channel_id')" >
				<option value="">--Select Zone--</option>
                                </select>
                                      </div>
                              </td>
                            </tr>
                            <TD><PRE> </PRE></TD>
                             <tr>
                                 <td id="channel_text" style="visibility: hidden"> Channel:</td>
                                 <td><div name="channeldiv" id="channeldiv">
                                 <select name="channel_id" id="channel_id" class="selectbox" style="visibility: hidden"  >
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





