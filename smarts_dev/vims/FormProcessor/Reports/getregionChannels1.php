<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

//check permission
if(!$_POST['permission']=='NWD')
    {
        echo "Permission Denied";
        exit();
    }


$ch_obj = new Channel();
if($_POST['permission']=='Region')
   {
    if($_POST['channel_type']==null){print("No Channel Type Selected"); exit();}
    $channels = $ch_obj->getregionChannels($_SESSION['region'], $_POST['channel_type']);
    $_SESSION['reporttype'] = 'collection';
   }

if($_POST['permission']=='NWD')
 {
    if($_POST['region']==null) {
    print( "No Region selected!!!");
    exit();
    }
    $channels = $ch_obj->getregionChannels($_POST['region'], $_POST['channel_type']);
    $_SESSION['reporttype'] = 'collection';
 }
?>
<? if($channels==null)
       { ?> <script>disableDisplayReport();</script>
    <? echo "No Channel Found!";
        exit();} ?>

        <td>
        <select name='channel' id='channel' class="selectbox" style="visibility: visible" onchange="javascript:processForm( 'collection_date','FormProcessor/Reports/getusers.php','userdiv' ); showhidefield('supervisor_text');">
        <option value=""> -- Channel -- </option>
         <?
        foreach($channels as $arr) { ?>
        <option value="<?=$arr['channel_id']?>"><?=$arr['channel_name']?> </option>
        <? } ?>
         </select>
        </td>
        <td><div name="userdiv" id="userdiv">
         <select name='user' id='user' style="visibility:hidden" class="selectbox" >
        <option value=""> -- Channel Type -- </option>
    </select>
     </div>
        </td>
