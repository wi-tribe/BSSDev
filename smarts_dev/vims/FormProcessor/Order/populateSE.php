<?
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/User.php");


//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'processretailerorder'))
{
	echo "Permission denied";
	exit;
}
$ROLE = new Role();
$user = new User();


if($_POST['TeamLead']==null || $_POST['TeamLead']=="")
     {
        ?>
     Sales Executive:<select name="SE" id="SE" class="selectbox">
    <option value="">Sales Executive</option>
        </select>
    <?
     }   else
         {

if(($data = $user->getSubordinates($_POST['TeamLead']))==false)
{
    ?>
       Sales Executive:<select name="SE" id="SE" class="selectbox">
                                <option value="">NONE</option>
                                </select> <?
}
else
{
 ?>

Sales Executive: <select name="SE" id="SE" class="selectbox" onchange="javascript:processForm( 'ProcessRetailerOrder','FormProcessor/Order/populateRetailer.php','RetailerDiv' ); resetOrderEntries();">
    <option value="">Sales Executive</option>
<?php
foreach($data as $arr) { 
    ?>
<option value="<?=$arr['user_id']?>"><?=$arr['first_name']?> <?=$arr['last_name']?>:<?=$arr['channel_name']?></option>
<? } } }?>
</select>