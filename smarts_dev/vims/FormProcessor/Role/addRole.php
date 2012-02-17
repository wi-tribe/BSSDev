<?php
session_start("VIMS");
set_time_limit(700);

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addrole'))
{
	echo "Permission denied";
	exit;
}

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Role.php");

//creating new object
$role=new Role();

if($role->addRole())
{
	echo $channel->LastMsg."New Role Successfully Added------!";
}
else
{
 echo $channel->LastMsg."Error While adding new role!";
}
?>
