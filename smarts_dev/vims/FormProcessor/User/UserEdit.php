<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/User.php");


//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'edituser'))
{
	echo "Permission denied";
	exit;
}


if($_USER->updateUser())
   {
        echo $_USER->LastMsg.="User Successfully Updated!";
   }
   else
       {
           echo $_USER->LastMsg.="Error while updating user";
       }

?>
