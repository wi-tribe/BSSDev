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

if($_POST['SE']==null || $_POST['SE']=="")
     {
        ?>
     Retailer:<select name="retailer_id" id="retailer_id" class="selectbox" >
                                <option value="">Select Retailer</option>
                                </select>  <?
     }   else
         {

if(($data = $user->getSubordinates($_POST['SE']))==false)
{
    ?>
       Retailer:<select name="retailer_id" id="retailer_id" class="selectbox" >
                                <option value="">No Retailer Assigned</option>
                                </select> <?
}
else
{
 ?>

Retailer:<select name="Retailer" id="Retailer" class="selectbox" onchange="javascript:resetOrderEntries();">
<option value="">Select Retailer</option>
<?php
foreach($data as $arr) { 
    if($arr['status']==1){?>
}
<option value="<?=$arr['user_id']?>"><?=$arr['channel_name']?>:<?=$arr['channel_id']?>:<?=$arr['first_name']?> <?=$arr['last_name']?></option>
<? } } } }?>
</select>