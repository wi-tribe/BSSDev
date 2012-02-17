<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

//check permission
//if(!$_POST['permission']=='NWD')
//    {
//        echo "Permission Denied";
//        exit();
//    }
$ch_obj = new Channel();
if($_POST['channel_type']==null) {
    print( "No Channel Type Selected!!!");
    exit();
    }
    else
        {
            $Regions = $ch_obj->getRegions();
        }
?>
                     <td>
                         <select name='region' id='region' class="selectbox" style="visibility: visible" onchange="javascript:processForm( 'collection_date','FormProcessor/Reports/getregionChannels.php','channeldiv' ); showhidefield('channel_text'); ">
                            <option value=""> -- Region -- </option>
                                <?
                                foreach($Regions as $arr) { ?>
                                <option value="<?=$arr['location_id']?>"><?=$arr['location_name']?> </option>
                                <? } ?>
                        </select>
                    </td>
<td></td>
<td id="channel_text" style="visibility: hidden"> Channel: </td>
<td><div name="channeldiv" id="channeldiv">
<select name='channel' id='channel' class="selectbox" style="visibility: hidden">
        <option value=""> -- Channel -- </option>
</select>
    </div>
</td>