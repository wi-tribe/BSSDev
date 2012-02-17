<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Retailer.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Logging.php");


//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'editretailer'))
{
	echo "Permission denied";
	exit;
}

$retailer_obj = new Retailer();
$log_obj = new Logging();

if($retailer_obj->updateRetailer())
   {
        echo $_USER->LastMsg.="User Successfully Updated!";
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO ".$_SESSION['username']." successfuly updated retailerOwnerID :".$_POST['user_id']);
   }
   else
       {
           echo $_USER->LastMsg.="Error while updating user";
           $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." unable to update retailer, retailerOwnerID :".$_POST['user_id'].", $_USER->LastMsg");
       }

?>
<script>
 window.opener.animatedAjaxCall( 'Pages/User/listRetailers.php','FormDiv' );
</script>