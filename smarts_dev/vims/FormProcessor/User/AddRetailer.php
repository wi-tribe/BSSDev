<?
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/User.php");


//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addretailer'))
{
	echo "Permission denied";
	exit;
}

$usr = new User();
if($usr->addUser())
   {
        echo $usr->LastMsg."Retailer Successfully Added!";
   }
   else
       {
       print_r($usr->LastMsg."Error while adding new retailer");
       }
?>