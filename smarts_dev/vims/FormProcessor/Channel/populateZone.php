<?
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Channel.php");

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addretailerchannel'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$channel=new Channel();


$city_id = $_POST['city_id'];

 if($city_id==null || $city_id=="")
     {
        ?>
    <select name="city_id" id="city_id">
            <option value="">Select a Zone</option>
             </select> <?
     }
     else
     {

            $zone_names= $channel->getCityZones($city_id);
        //    $data = $ROLE->getHeads($region_id);

            if($zone_names==null)
            {
                ?>
                    <select name="zone_id" id="zone_id" onchange="javascript:processForm( 'ChannelAdd','FormProcessor/Channel/populateParentChannel.php','parent_channel_Div' );">
                    <option value="">NONE</option>
                    </select>
                <?
            }
            else
            {
                ?>
                    <select name="zone_id" id="zone_id" onchange="javascript:processForm( 'ChannelAdd','FormProcessor/Channel/populateParentChannel.php','parent_channel_Div' );">
                    <option value="">Select a Zone</option>
                <?php
                    foreach($zone_names as $arr)
                    {
                ?>
                  //  }
                    <option value="<?=$arr['location_id']?>"><?=$arr['location_name']?></option>
                <?
                    }
            }
    }

                ?>

</select>
