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


if($_USER->addUser())
   {
        echo $_USER->LastMsg."User Successfully Added!";
   }
   else
       {
       print_r($_USER->LastMsg."Error while adding new user");
       }
?>