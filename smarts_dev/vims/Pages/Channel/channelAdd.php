<?

session_start("VIMS");
set_time_limit(700);
//ob_start();
include_once("../../vims_config.php");

$conf=new config();

include_once(CLASSDIR."/Channel.php");

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addchannel'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$channel=new Channel();

if(!$data=$channel->getAllChannelTypes())
{
	echo $channel->LastMsg."Cant not Get All Channels Type Name!";
}

//if(!$result=$channel->getAllChannels())
//{
//	echo $channel->LastMsg."Can not Get All Channels Name!";
//}

if(!$regions=$channel->getLocationDetails(3))
{
	echo $channel->LastMsg."Can not Get All Channels Name!";
}

//if(!$cities=$channel->getLocationDetails(4))
//{
//	echo $channel->LastMsg."Can not Get All Channels Name!";
//}
//
//if(!$zones=$channel->getLocationDetails(5))
//{
//	echo $channel->LastMsg."Can not Get All Channels Name!";
//}






$role = new Role();
$userrole = $role->getRoleDetails($_SESSION['user_role_id']);
//print_r($userrole[0]);
$text="";
if($userrole[0]['role_name']=='admin' || $userrole[0]['role_name']=='Sales Executive')
{
   $index = 0;
   for(; $index < sizeof($data); $index++)
   {
       if($data[$index]['channel_type_name']=='WareHouse')
       {
           $warehouse []= $data[$index];
       }
   }
   $data = $warehouse;
   $text="Retailer ";

   

}
//else if($userrole[0]['role_name']=='TeamLead')
//{
//   $index = 0;
//   for(; $index < sizeof($data); $index++)
//   {
//       if($data[$index]['channel_type_name']=='Retailer')
//       {
//           $retailer []= $data[$index];
//       }
//   }
//   $data = $retailer;
//}

//print_r($channel);

?>

<table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                        <form name="ChannelAdd" id="ChannelAdd" method="post" action="">

                          <table width="447" border="0" cellspacing=0" cellpadding="0">
                            <tr>
                              <td width="145"><label> Channel Name:</label></td>
                              <td width="302"><label>
                                <input type="text" name="channel_name" id="channel_name">
                              </label></td>
                            </tr>
                            <tr>
                              <td><label>Channel Type :</label>&nbsp;</td>
                              <td><label>
                                <select name="channel_type_id" id="channel_type_id">
                                <option value="">Select Channel Type</option>
                                    <?php

                                    foreach($data as $arr) {
                                      ?>
                                    <option value="<?=$arr['channel_type_id']?>"> <?=$text.$arr['channel_type_name']?> </option>

                                    <? } ?>

                                </select>
                              </label></td>
                            </tr>
                            <tr>
                              <td><label><label>Address:</label></label>&nbsp;</td>
                              <td><label>
                                <input type="text" name="address" id="address">
                              </label></td>
                            </tr>
                            <tr>
                              <td><label>Owner  Name:</label>&nbsp;</td>
                              <td><label>
                                <input type="text" name="owner_name" id="owner_name">
                              </label></td>
                            </tr>
                            <tr>
                              <td><label>Region:</label>
                              &nbsp;</td>
                              <td><label>
                                <select name="region_id" id="region_id" onchange="javascript:processForm( 'ChannelAdd','FormProcessor/Channel/populateCity.php','city_Div' );">

				<option value="">Select a Region</option>
				<?php
                                   //   print_r($result);
                                    foreach($regions as $arr) { ?>

                                    <option value="<?=$arr['location_id']?>"><?=$arr['location_name']?></option>

                                    <? } ?>
                                </select>
                              </label></td>
                            </tr>

                            <tr>
                              <td><label> City:</label>&nbsp;</td>
                              <td> <div name="city_Div" id="city_Div">
                                    <select name="city_id" id="city_id" onchange="javascript:processForm( 'ChannelAdd','FormProcessor/Channel/populateZone.php','zone_Div' );">
                                    <option value="">Select a City</option>
                                    </select>
                                  </div>
                              </td>
                            </tr>

                            <tr>
                              <td><label> Zone:</label>&nbsp;</td>
                              <td> <div name="zone_Div" id="zone_Div">
                                    <select name="zone_id" id="zone_id" onchange="javascript:processForm( 'ChannelAdd','FormProcessor/Channel/populateParentChannel.php','parent_channel_Div' );">
                                    <option value="">Select a Zone</option>
                                    </select>
                                  </div>
                              </td>
                            </tr>

                            <tr>
                              <td><label> Parent Channel Name:</label>&nbsp;</td>
                              <td> <div name="parent_channel_Div" id="parent_channel_Div">
                                    <select name="parent_channel_id" id="parent_channel_id">
                                    <option value="">None</option>
                                    </select>
                                  </div>
                              </td>
                            </tr>

                            
                          </table>
                        </form>
<table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align='right'><input name="FormAction" type='button' value='Add' onclick="javascript:processForm( 'ChannelAdd','FormProcessor/Channel/addChannel.php','MsgDiv' );"></td>
				</tr>
			  </table>

              <!--------------------------------THE END------------------------------------->
			</td>
		</tr>
	</table>
