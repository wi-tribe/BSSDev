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
 if($_POST['region']==null)
 {
   print("No Region Selected!!!");
   exit();
 }
 
$ch_obj = new Channel();
if($_POST['permission']=='Region')
   {
    $channels = $ch_obj->getuserregionShops($_SESSION['user_id']);
    $_SESSION['reporttype']='receipt';
   }

if($_POST['permission']=='NWD')
 {
    $channels = $ch_obj->getregionshopids($_POST['region']);
    $_SESSION['reporttype']='receipt';
 }
?>
<? if($channels==null)
       { ?> <script>disableDisplayReport();</script>
    <? echo "No Channel Found!";
        exit();} ?>
       <? if($_POST['formname']=='receipt_summary') {?>
        <td>
        <select name='channel' id='channel' class="selectbox" style="visibility: visible" onchange="javascript:processForm( 'receipt_summary_report','FormProcessor/Reports/getusers.php','userdiv' ); showhidefield('supervisor_text');">
        <option value=""> -- Channel -- </option>
         <?
        foreach($channels as $arr) { ?>
         <option value="<?=$arr['shop_id']?>"><?=$arr['shop_id']?>--<?=$arr['shop_name']?></option>
        <? } ?>
        </select>
        </td>
     <? }
         else { ?>
        <td>
        <select name='channel' id='channel' class="selectbox" style="visibility: visible" onchange="javascript:processForm( 'receipt_report','FormProcessor/Reports/getusers.php','userdiv' ); showhidefield('supervisor_text');">
        <option value=""> -- Channel -- </option>
         <?
        foreach($channels as $arr) { ?>
         <option value="<?=$arr['shop_id']?>"><?=$arr['shop_id']?>--<?=$arr['shop_name']?></option>
        <? } } ?>
        </select>
        </td>
        
        <td><div name="userdiv" id="userdiv">
         <select name='user' id='user' style="visibility:hidden" class="selectbox" >
        <option value=""> -- Users -- </option>
    </select>
     </div>
        </td>
