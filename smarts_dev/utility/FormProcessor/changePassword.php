<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once('../util_config.php');
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Logging.php");
$log_obj = new Logging();

////permission check
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'changepassword'))
//{
//	echo "Permission denied";
//	exit;
//}


if($_USER->changePassword())
   {
        echo $_USER->LastMsg."Password Successfully Changed!";
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Successfuly changed password ");
   }
   else
       {
        echo $_USER->LastMsg;
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." unable to change password ,".$_USER->LastMsg);
       }

?>
