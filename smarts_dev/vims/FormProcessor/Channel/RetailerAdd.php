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

if($channel->addChannel())
{
	echo $channel->LastMsg."Channel have been added successfully";
} else
	echo $channel->LastMsg;
 
?>