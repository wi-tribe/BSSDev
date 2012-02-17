<?
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/User.php");


//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'adduser'))
{
	echo "Permission denied";
	exit;
}


$ROLE = new Role();
$role_id = $_POST['user_role_id'];

 if($role_id==null || $role_id=="")
     {
        ?>
    <select name="parent_user_id" id="parent_user_id">
            <option value="">Select Head</option>
             </select> <?
     }
     else
         {

$p_role= $ROLE->checkParentRoleExists($role_id);
$data = $ROLE->getHeads($role_id);

if($p_role==false || $data==null)
    {
    ?>
    <select name="parent_user_id" id="parent_user_id">
            <option value="">NONE</option>
             </select> <?
    }
    else
   {
?>
<select name="parent_user_id" id="parent_user_id">
<option value="">Select Head</option>
<?php
foreach($data as $arr) { ?>
<option value="<?=$arr['user_id']?>"><?=$arr['first_name']?>-<?=$arr['last_name']?>---<?=$arr['channel_name']?></option>
<? } } }?>
</select>
